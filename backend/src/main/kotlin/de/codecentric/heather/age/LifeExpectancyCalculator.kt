package de.codecentric.heather.age

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Period

@Service
class LifeExpectancyCalculator {

    fun calculateTimeRemaining(birthdate: LocalDate, gender: Gender): TimeRemaining {
        val currentAge = Period.between(birthdate, LocalDate.now())
        val totalAgeInYears = currentAge.years

        val lifeExpectancy = when (gender) {
            Gender.MALE -> 85
            Gender.FEMALE -> 90
            Gender.OTHER -> 87
        }

        val yearsRemaining = lifeExpectancy - totalAgeInYears

        // Calculate the exact remaining time including days
        val lifeExpectancyDate = birthdate.plusYears(lifeExpectancy.toLong())
        val remainingPeriod = Period.between(LocalDate.now(), lifeExpectancyDate)

        val daysRemaining = if (yearsRemaining >= 0) {
            remainingPeriod.days
        } else {
            0
        }

        return TimeRemaining(
            years = if (yearsRemaining >= 0) yearsRemaining else 0,
            days = daysRemaining,
        )
    }
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER,
}

data class TimeRemaining(
    val years: Int,
    val days: Int,
)
