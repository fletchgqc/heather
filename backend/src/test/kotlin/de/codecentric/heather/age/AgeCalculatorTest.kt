package de.codecentric.heather.age

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AgeCalculatorTest {

    private val lifeExpectancyCalculator = LifeExpectancyCalculator()
    private val subject = AgeCalculator(lifeExpectancyCalculator)

    @Test
    fun `should calculate age correctly`() {
        val birthdate = LocalDate.now().minusYears(25)
        val age = subject.calculateAge(birthdate)
        age shouldBe 25
    }

    @Test
    fun `should calculate age with time remaining for male`() {
        val birthdate = LocalDate.now().minusYears(30)
        val ageData = subject.calculateAgeWithTimeRemaining(birthdate, Gender.MALE)
        ageData.age shouldBe 30
        ageData.timeRemaining.years shouldBe 55
    }

    @Test
    fun `should calculate age with time remaining for female`() {
        val birthdate = LocalDate.now().minusYears(30)
        val ageData = subject.calculateAgeWithTimeRemaining(birthdate, Gender.FEMALE)
        ageData.age shouldBe 30
        ageData.timeRemaining.years shouldBe 60
    }

    @Test
    fun `should calculate age with time remaining for other gender`() {
        val birthdate = LocalDate.now().minusYears(30)
        val ageData = subject.calculateAgeWithTimeRemaining(birthdate, Gender.OTHER)
        ageData.age shouldBe 30
        ageData.timeRemaining.years shouldBe 57
    }
}
