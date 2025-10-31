package de.codecentric.heather.age

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class AgeCalculator(
    private val lifeExpectancyCalculator: LifeExpectancyCalculator,
) {

    fun calculateAge(birthdate: LocalDate): Int {
        return Period.between(birthdate, LocalDate.now()).years
    }

    fun calculateAgeWithTimeRemaining(birthdate: LocalDate, gender: Gender): AgeData {
        val age = calculateAge(birthdate)
        val timeRemaining = lifeExpectancyCalculator.calculateTimeRemaining(birthdate, gender)
        return AgeData(
            age = age,
            timeRemaining = timeRemaining,
        )
    }
}

data class AgeData(
    val age: Int,
    val timeRemaining: TimeRemaining,
)
