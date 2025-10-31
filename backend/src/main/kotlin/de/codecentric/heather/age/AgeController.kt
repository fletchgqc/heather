package de.codecentric.heather.age

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class AgeController(
    private val ageCalculator: AgeCalculator,
) {

    @GetMapping("/api/age")
    @Operation(
        summary = "Calculate age and estimated time remaining",
        description = "Calculates current age and estimated years/days remaining " +
            "based on birthdate and gender-specific life expectancy",
    )
    fun calculateAge(
        @Parameter(description = "Date of birth in ISO format (YYYY-MM-DD)", required = true)
        @RequestParam birthdate: LocalDate,
        @Parameter(description = "Gender identity (MALE, FEMALE, OTHER)", required = true)
        @RequestParam gender: Gender,
    ): ResponseEntity<Any> {
        if (birthdate.isAfter(LocalDate.now())) {
            return ResponseEntity.badRequest().body(
                mapOf("error" to "Birthdate cannot be in the future"),
            )
        }

        val ageData = ageCalculator.calculateAgeWithTimeRemaining(birthdate, gender)
        return ResponseEntity.ok(
            mapOf(
                "age" to ageData.age,
                "timeRemaining" to mapOf(
                    "years" to ageData.timeRemaining.years,
                    "days" to ageData.timeRemaining.days,
                ),
            ),
        )
    }
}
