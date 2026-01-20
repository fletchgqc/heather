# Hourly Forecast Specification

## Overview

Add hourly weather forecast data for the next 24 hours to complement the existing daily forecast. Users should see hour-by-hour temperature and weather conditions in a scrollable, easy-to-scan format. This helps users plan activities within the current day and make more informed short-term decisions compared to the existing 5-day daily forecast.

## Key Constraints & Design Decisions

**API Design:**
- Must integrate with existing `/api/weather` endpoint without breaking current `WeatherResponse` structure
- Add new `hourlyForecast` field to `WeatherResponse` containing exactly 24 hourly data points
- Each hourly forecast must include: timestamp, temperature, and weather condition (using existing `WeatherCondition` enum)

**Data Model:**
- Create new `HourlyForecast` data class in `WeatherModels.kt` alongside existing models
- Use `LocalDateTime` for timestamps (not epoch/unix time) for consistency with existing `LocalDate` usage
- Temperature values must be integers (consistent with `CurrentWeather` and `DailyForecast`)
- Reuse existing `WeatherCondition` enum - do not create new condition types

**Frontend Design:**
- Must be visually distinct from the 5-day daily forecast display
- Should be horizontally scrollable to show all 24 hours without overwhelming the viewport
- Display time in 12-hour format (e.g., "2 PM", "11 AM") for readability
- Show temperature and condition icon for each hour (same emoji mapping as `ForecastList`)

**Performance:**
- Hourly data should not significantly increase API response time
- Frontend should handle missing hourly data gracefully (fall back to showing daily forecast only)

## Usage

**Backend API Response:**
```json
{
  "current": { ... },
  "forecast": [ ... ],
  "hourlyForecast": [
    {
      "timestamp": "2026-01-20T14:00:00",
      "temperature": 72,
      "condition": "PARTLY_CLOUDY"
    },
    {
      "timestamp": "2026-01-20T15:00:00",
      "temperature": 74,
      "condition": "SUNNY"
    }
    // ... 22 more hours
  ]
}
```

**Frontend Display:**
Users see a horizontal scrollable row beneath current weather showing:
```
2 PM  3 PM  4 PM  5 PM  6 PM  ...
☀️    ⛅    ☁️    🌧️    ⛈️
72°   74°   73°   68°   65°
```

**Interaction:**
- User can scroll/swipe horizontally to view all 24 hours
- Each hour shows time, weather icon, and temperature
- No additional click/tap interactions required

## Testing

**Success Criteria:**
- API returns exactly 24 hourly forecasts with sequential timestamps
- Frontend displays all 24 hours in scrollable horizontal layout
- Temperature and weather conditions render correctly for each hour
- Hourly forecast appears on page without breaking existing current weather or daily forecast
- Works on both desktop (mouse scroll) and mobile (touch swipe)

**Key Test Scenarios:**
1. **API Contract:** Verify `/api/weather` response includes `hourlyForecast` array with 24 items, each containing `timestamp`, `temperature`, and `condition`
2. **Timestamp Sequence:** Confirm hourly timestamps are sequential and cover exactly 24 hours from current time
3. **Visual Layout:** Verify horizontal scroll works and all 24 hours are accessible without vertical scrolling
4. **Data Display:** Check that temperatures are integers and weather icons map correctly to conditions
5. **Graceful Degradation:** If hourly data is empty/null, existing weather displays should still work

**Manual Verification:**
- Open app and confirm hourly forecast appears below current weather
- Scroll through all 24 hours and verify readability
- Check times are displayed in 12-hour format with AM/PM
- Verify no layout breaks on mobile viewport widths
