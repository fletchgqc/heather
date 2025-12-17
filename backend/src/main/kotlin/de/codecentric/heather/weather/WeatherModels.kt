package de.codecentric.heather.weather

import java.time.LocalDate

/**
 * Weather condition types for current weather and forecasts.
 */
enum class WeatherCondition(val displayText: String) {
    SUNNY("Sunny"),
    PARTLY_CLOUDY("Partly Cloudy"),
    CLOUDY("Cloudy"),
    RAINY("Rainy"),
    STORMY("Stormy"),
    SNOWY("Snowy"),
}

/**
 * Current weather conditions.
 */
data class CurrentWeather(
    val temperature: Int,
    val condition: WeatherCondition,
    val description: String,
)

/**
 * Weather forecast for a single day.
 */
data class DailyForecast(
    val date: LocalDate,
    val temperatureHigh: Int,
    val temperatureLow: Int,
    val condition: WeatherCondition,
    val description: String,
)

/**
 * Root entity returned by the weather API.
 */
data class WeatherResponse(
    val current: CurrentWeather,
    val forecast: List<DailyForecast>,
)
