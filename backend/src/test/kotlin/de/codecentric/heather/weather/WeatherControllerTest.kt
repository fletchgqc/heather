package de.codecentric.heather.weather

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class WeatherControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `GET api weather returns 200 with weather response`() {
        mockMvc.perform(get("/api/weather").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.current").exists())
            .andExpect(jsonPath("$.current.temperature").isNumber)
            .andExpect(jsonPath("$.current.condition").isString)
            .andExpect(jsonPath("$.current.description").isString)
            .andExpect(jsonPath("$.forecast").isArray)
            .andExpect(jsonPath("$.forecast.length()").value(5))
    }

    @Test
    fun `GET api weather returns current weather with valid condition`() {
        val validConditions = listOf("SUNNY", "PARTLY_CLOUDY", "CLOUDY", "RAINY", "STORMY", "SNOWY")

        val result = mockMvc.perform(get("/api/weather").accept(MediaType.APPLICATION_JSON))
            .andReturn()

        val responseBody = result.response.contentAsString
        responseBody shouldNotBe null

        val containsValidCondition = validConditions.any { responseBody.contains("\"condition\":\"$it\"") }
        containsValidCondition shouldBe true
    }

    @Test
    fun `GET api weather returns forecast with correct structure`() {
        mockMvc.perform(get("/api/weather").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.forecast[0].date").isString)
            .andExpect(jsonPath("$.forecast[0].temperatureHigh").isNumber)
            .andExpect(jsonPath("$.forecast[0].temperatureLow").isNumber)
            .andExpect(jsonPath("$.forecast[0].condition").isString)
            .andExpect(jsonPath("$.forecast[0].description").isString)
    }

    @Test
    fun `GET api weather returns temperature in valid range`() {
        val result = mockMvc.perform(get("/api/weather").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andReturn()

        val responseBody = result.response.contentAsString
        val temperatureRegex = """"temperature":(-?\d+)""".toRegex()
        val match = temperatureRegex.find(responseBody)
        val temperature = match?.groupValues?.get(1)?.toIntOrNull()

        temperature shouldNotBe null
        (temperature!! >= -50 && temperature <= 50) shouldBe true
    }
}
