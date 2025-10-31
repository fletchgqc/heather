package de.codecentric.heather.age

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

@WebMvcTest(AgeController::class)
@Import(AgeCalculator::class, LifeExpectancyCalculator::class)
class AgeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return age and time remaining for male gender`() {
        val birthdate = LocalDate.now().minusYears(30).toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
            param("gender", "MALE")
        }.andExpect {
            status { isOk() }
            jsonPath("$.age") { value(30) }
            jsonPath("$.timeRemaining.years") { value(55) }
        }
    }

    @Test
    fun `should return age and time remaining for female gender`() {
        val birthdate = LocalDate.now().minusYears(40).toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
            param("gender", "FEMALE")
        }.andExpect {
            status { isOk() }
            jsonPath("$.age") { value(40) }
            jsonPath("$.timeRemaining.years") { value(50) }
        }
    }

    @Test
    fun `should return age and time remaining for other gender`() {
        val birthdate = LocalDate.now().minusYears(25).toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
            param("gender", "OTHER")
        }.andExpect {
            status { isOk() }
            jsonPath("$.age") { value(25) }
            jsonPath("$.timeRemaining.years") { value(62) }
        }
    }

    @Test
    fun `should return bad request for future birthdate`() {
        val futureBirthdate = LocalDate.now().plusDays(1).toString()

        mockMvc.get("/api/age") {
            param("birthdate", futureBirthdate)
            param("gender", "MALE")
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.error") { value("Birthdate cannot be in the future") }
        }
    }

    @Test
    fun `should return bad request for missing gender parameter`() {
        val birthdate = LocalDate.now().minusYears(30).toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should return bad request for invalid gender value`() {
        val birthdate = LocalDate.now().minusYears(30).toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
            param("gender", "INVALID")
        }.andExpect {
            status { isBadRequest() }
        }
    }

    @Test
    fun `should handle person exceeding life expectancy`() {
        val birthdate = LocalDate.now().minusYears(95).toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
            param("gender", "MALE")
        }.andExpect {
            status { isOk() }
            jsonPath("$.age") { value(95) }
            jsonPath("$.timeRemaining.years") { value(0) }
            jsonPath("$.timeRemaining.days") { value(0) }
        }
    }

    @Test
    fun `should handle newborn baby`() {
        val birthdate = LocalDate.now().toString()

        mockMvc.get("/api/age") {
            param("birthdate", birthdate)
            param("gender", "FEMALE")
        }.andExpect {
            status { isOk() }
            jsonPath("$.age") { value(0) }
            jsonPath("$.timeRemaining.years") { value(90) }
        }
    }
}
