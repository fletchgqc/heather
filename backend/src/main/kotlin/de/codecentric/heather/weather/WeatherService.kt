package de.codecentric.heather.weather

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Month

/**
 * Service that generates deterministic mock weather data.
 * Weather conditions vary by day of week for demo variety.
 * Temperatures are season-aware based on the current month.
 */
@Service
class WeatherService {

    /**
     * Get weather data including current conditions and 5-day forecast.
     */
    fun getWeather(): WeatherResponse {
        val today = LocalDate.now()
        return WeatherResponse(
            current = generateCurrentWeather(today),
            forecast = generateForecast(today),
        )
    }

    private fun generateCurrentWeather(date: LocalDate): CurrentWeather {
        val condition = getConditionForDay(date.dayOfWeek.value)
        val baseTemp = getBaseTemperatureForMonth(date.month)
        return CurrentWeather(
            temperature = baseTemp,
            condition = condition,
            description = condition.displayText,
        )
    }

    private fun generateForecast(today: LocalDate): List<DailyForecast> {
        return (1..5).map { daysAhead ->
            val forecastDate = today.plusDays(daysAhead.toLong())
            val condition = getConditionForDay(forecastDate.dayOfWeek.value)
            val baseTemp = getBaseTemperatureForMonth(forecastDate.month)
            val variance = (daysAhead % 3) - 1 // -1, 0, or 1

            DailyForecast(
                date = forecastDate,
                temperatureHigh = baseTemp + 4 + variance,
                temperatureLow = baseTemp - 4 + variance,
                condition = condition,
                description = condition.displayText,
            )
        }
    }

    /**
     * Returns a deterministic weather condition based on the day of week.
     * This provides variety while remaining predictable for testing.
     */
    private fun getConditionForDay(dayOfWeek: Int): WeatherCondition {
        return when (dayOfWeek) {
            1 -> WeatherCondition.SUNNY // Monday
            2 -> WeatherCondition.PARTLY_CLOUDY // Tuesday
            3 -> WeatherCondition.CLOUDY // Wednesday
            4 -> WeatherCondition.RAINY // Thursday
            5 -> WeatherCondition.PARTLY_CLOUDY // Friday
            6 -> WeatherCondition.SUNNY // Saturday
            7 -> WeatherCondition.SUNNY // Sunday
            else -> WeatherCondition.CLOUDY
        }
    }

    /**
     * Returns a realistic base temperature in Celsius based on the month.
     * Northern hemisphere seasonal pattern.
     */
    private fun getBaseTemperatureForMonth(month: Month): Int {
        return when (month) {
            Month.JANUARY -> 2
            Month.FEBRUARY -> 4
            Month.MARCH -> 8
            Month.APRIL -> 12
            Month.MAY -> 17
            Month.JUNE -> 21
            Month.JULY -> 24
            Month.AUGUST -> 23
            Month.SEPTEMBER -> 19
            Month.OCTOBER -> 13
            Month.NOVEMBER -> 7
            Month.DECEMBER -> 3
        }
    }
}
