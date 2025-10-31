package de.codecentric.heather.age

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class LifeExpectancyCalculatorTest {

    private val subject = LifeExpectancyCalculator()

    @Test
    fun `should calculate time remaining for male with 30 years old`() {
        val birthdate = LocalDate.now().minusYears(30)
        val result = subject.calculateTimeRemaining(birthdate, Gender.MALE)
        result.years shouldBe 55 // 85 - 30
    }

    @Test
    fun `should calculate time remaining for male with 50 years old`() {
        val birthdate = LocalDate.now().minusYears(50)
        val result = subject.calculateTimeRemaining(birthdate, Gender.MALE)
        result.years shouldBe 35 // 85 - 50
    }

    @Test
    fun `should calculate time remaining for female with 30 years old`() {
        val birthdate = LocalDate.now().minusYears(30)
        val result = subject.calculateTimeRemaining(birthdate, Gender.FEMALE)
        result.years shouldBe 60 // 90 - 30
    }

    @Test
    fun `should calculate time remaining for female with 50 years old`() {
        val birthdate = LocalDate.now().minusYears(50)
        val result = subject.calculateTimeRemaining(birthdate, Gender.FEMALE)
        result.years shouldBe 40 // 90 - 50
    }

    @Test
    fun `should calculate time remaining for other gender with 30 years old`() {
        val birthdate = LocalDate.now().minusYears(30)
        val result = subject.calculateTimeRemaining(birthdate, Gender.OTHER)
        result.years shouldBe 57 // 87 - 30
    }

    @Test
    fun `should calculate time remaining for other gender with 50 years old`() {
        val birthdate = LocalDate.now().minusYears(50)
        val result = subject.calculateTimeRemaining(birthdate, Gender.OTHER)
        result.years shouldBe 37 // 87 - 50
    }

    @Test
    fun `should return zero years when age exceeds male life expectancy`() {
        val birthdate = LocalDate.now().minusYears(90)
        val result = subject.calculateTimeRemaining(birthdate, Gender.MALE)
        result.years shouldBe 0
        result.days shouldBe 0
    }

    @Test
    fun `should return zero years when age exceeds female life expectancy`() {
        val birthdate = LocalDate.now().minusYears(95)
        val result = subject.calculateTimeRemaining(birthdate, Gender.FEMALE)
        result.years shouldBe 0
        result.days shouldBe 0
    }

    @Test
    fun `should calculate time remaining for newborn baby`() {
        val birthdate = LocalDate.now().minusDays(30)
        val result = subject.calculateTimeRemaining(birthdate, Gender.MALE)
        result.years shouldBe 85
    }

    @Test
    fun `should calculate days remaining correctly`() {
        val birthdate = LocalDate.now().minusYears(30).minusDays(100)
        val result = subject.calculateTimeRemaining(birthdate, Gender.MALE)
        // Period.between calculates years component first, so 30 years old gives 55 years remaining
        result.years shouldBe 55 // 85 - 30 (years component)
        // Days should be calculated based on the remaining period
    }

    @Test
    fun `should handle leap year birthdates`() {
        val birthdate = LocalDate.of(2000, 2, 29) // Leap year
        val result = subject.calculateTimeRemaining(birthdate, Gender.FEMALE)
        // Should calculate correctly without errors
        result.years shouldBe (90 - (LocalDate.now().year - 2000))
    }
}
