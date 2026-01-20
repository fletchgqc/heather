# Hourly Forecast Implementation Plan

## Overview

This plan outlines the implementation of the 24-hour hourly weather forecast feature. The implementation follows a backend-first approach to ensure type safety and proper data flow through the system.

## Implementation Steps

### Phase 1: Backend - Data Models (backend/src/main/kotlin/de/codecentric/heather/weather/WeatherModels.kt)

1. **Add HourlyForecast data class**
   - Add new `HourlyForecast` data class with fields: `timestamp: LocalDateTime`, `temperature: Int`, `condition: WeatherCondition`
   - Place it after `DailyForecast` in the file for logical grouping

2. **Update WeatherResponse**
   - Add `hourlyForecast: List<HourlyForecast>` field to existing `WeatherResponse` data class
   - This is a non-breaking change (adds new field, doesn't modify existing ones)

**Dependencies:** None - can start here
**Testing:** Backend compiles successfully

### Phase 2: Backend - Service Logic (backend/src/main/kotlin/de/codecentric/heather/weather/WeatherService.kt)

3. **Add hourly forecast generation method**
   - Create private method `generateHourlyForecast(startDateTime: LocalDateTime): List<HourlyForecast>`
   - Generate exactly 24 hourly forecasts starting from current hour
   - Use similar deterministic logic to existing forecast generation:
     - Base temperature from month (existing `getBaseTemperatureForMonth`)
     - Vary temperature slightly by hour (+/- 3 degrees in a pattern)
     - Vary conditions by hour of day (similar to `getConditionForDay` but for hours)

4. **Update getWeather() method**
   - Call `generateHourlyForecast(LocalDateTime.now())`
   - Add result to `WeatherResponse` constructor

**Dependencies:** Requires Phase 1 complete
**Testing:**
- Manual test: Run backend and verify `/api/weather` returns 24 hourly forecasts
- Verify timestamps are sequential and span exactly 24 hours
- Check temperatures are reasonable integers

### Phase 3: Backend - Verification

5. **Test the API endpoint**
   - Start backend with `./gradlew bootRun`
   - Hit `http://localhost:8080/api/weather` directly or via curl
   - Verify JSON response includes `hourlyForecast` array with 24 items
   - Confirm each item has `timestamp`, `temperature`, and `condition`

**Dependencies:** Requires Phase 1 & 2 complete
**Testing:** Integration test - full API response shape validation

### Phase 4: Frontend - Type Generation

6. **Regenerate API types**
   - Ensure backend is running (from Phase 3)
   - Run `npm run generate-api` from frontend directory
   - This updates `src/api/generated/types.ts` with new `HourlyForecast` type
   - Verify no TypeScript errors in `useWeather.ts`

**Dependencies:** Backend must be running with new API
**Testing:** Frontend builds without type errors

### Phase 5: Frontend - Component Creation

7. **Create HourlyForecast component** (frontend/src/components/HourlyForecastList.tsx)
   - Accept `hourlyForecast: HourlyForecast[] | undefined` prop
   - Map over hours and render each with:
     - Time in 12-hour format (use `date-fns` format function like `ForecastList` does)
     - Weather emoji (reuse `conditionEmojis` mapping - consider extracting to shared constant)
     - Temperature value
   - Implement horizontal scroll container
   - Return null if data is undefined/empty (graceful degradation)

**Decision Point:** Extract emoji mapping to shared constant file vs duplicate in component
- **Chosen approach:** Duplicate for now (keep component self-contained, only 6 lines)
- Can refactor later if more components need emoji mapping

8. **Add TypeScript type exports** (frontend/src/hooks/useWeather.ts)
   - Export `HourlyForecast` type from generated API types
   - Follow existing pattern: `export type HourlyForecast = components['schemas']['HourlyForecast']`

**Dependencies:** Requires Phase 4 (type generation)
**Testing:** Component renders in isolation (can manually test by temporarily adding to App.tsx)

### Phase 6: Frontend - Integration

9. **Integrate into App** (frontend/src/App.tsx)
   - Import `HourlyForecastList` component
   - Add component between `WeatherDisplay` and `ForecastList`
   - Pass `weatherData?.hourlyForecast` as prop
   - Follow same conditional rendering pattern as `ForecastList` (only show if not loading and no error)

**Decision Point:** Where to place hourly forecast in layout?
- **Chosen approach:** Between current weather and daily forecast (flows from immediate → short-term → long-term)

**Dependencies:** Requires Phase 5 complete
**Testing:** Visual inspection - component appears on page

### Phase 7: Frontend - Styling

10. **Add CSS styles** (frontend/src/App.css)
    - Create `.hourly-forecast-list` container styles (follow existing white card pattern)
    - Create `.hourly-forecast-scroll` for horizontal scroll container:
      - `display: flex` with horizontal layout
      - `overflow-x: auto` for scrolling
      - `gap` for spacing between items
      - Smooth scrolling behavior
    - Create `.hourly-forecast-item` for individual hour display:
      - Vertical flex layout (time, emoji, temp stacked)
      - Consistent padding and sizing
      - Minimum width to prevent squishing
    - Add responsive adjustments if needed for mobile

**Decision Point:** Scroll snap behavior?
- **Chosen approach:** Add `scroll-snap-type: x mandatory` for better UX on mobile
- Each item gets `scroll-snap-align: start`

**Dependencies:** Requires Phase 6 complete
**Testing:**
- Visual testing on desktop and mobile viewports
- Verify horizontal scroll works smoothly
- Check all 24 hours are accessible

## Key Decision Points

### Backend: Hourly Temperature Variation Algorithm
The hourly forecast needs realistic temperature variation throughout the day. Options considered:
- **Sine wave pattern**: More realistic diurnal temperature cycle
- **Simple variance pattern**: Similar to existing daily forecast logic

**Decision:** Use simple variance pattern (e.g., `baseTemp + (hour % 6) - 3`) to match existing service style and keep it deterministic for testing.

### Frontend: Component Placement
Where to show hourly forecast in the UI:
- Above current weather (most prominent)
- Between current and daily forecast (chronological flow)
- Below daily forecast (least prominent)

**Decision:** Between current and daily forecast - creates natural flow from "now" → "next 24h" → "next 5 days"

### Frontend: Emoji Mapping Reuse
The `ForecastList` component has a `conditionEmojis` mapping object:
- Extract to shared constants file
- Duplicate in new component

**Decision:** Duplicate for now - it's only 6 lines and keeps components independent. Can refactor if 3+ components need it.

## Testing Strategy

### Backend Testing
- **Unit-level:** Verify `generateHourlyForecast` returns exactly 24 items with sequential timestamps
- **Integration-level:** Hit API endpoint and validate response shape
- **Manual verification:** Check temperatures are reasonable and conditions vary

### Frontend Testing
- **Type safety:** Regenerate types and verify no TypeScript errors
- **Component isolation:** Manually verify component renders correctly with mock data
- **Integration:** Visual testing in running app
- **Responsive:** Test horizontal scroll on both desktop (mouse/trackpad) and mobile (touch)

### Testing Hints
- **Unit tests:** Could add Kotlin tests for `generateHourlyForecast` method (similar to existing service tests if any exist)
- **Component tests:** Could add Vitest tests for `HourlyForecastList` (similar to existing `WeatherDisplay.test.tsx`)
- **E2E consideration:** Not critical for MVP, but could add visual regression tests for scrolling behavior

## Risks and Unknowns

### Risk: API Type Generation Timing
**Issue:** Frontend type generation requires backend to be running with new changes.
**Mitigation:** Document clear steps - backend changes must be deployed/running before frontend type generation.

### Risk: Horizontal Scroll UX on Different Devices
**Issue:** Horizontal scrolling can be awkward on some devices/browsers.
**Mitigation:**
- Add scroll-snap for better mobile UX
- Ensure scroll indicators are visible (browser default scrollbars)
- Test on multiple viewport sizes
- Could add arrow buttons later if needed

### Unknown: Timezone Handling
**Issue:** Backend uses `LocalDateTime.now()` - is this the right timezone?
**Consideration:** For mock data, using server time is fine. If connecting to real weather API later, will need timezone-aware timestamps.
**Action:** Document assumption that timestamps are in server timezone. Accept for MVP.

### Risk: 24 Items in API Response Size
**Issue:** Adding 24 hourly forecasts increases API response size.
**Mitigation:** Each hourly item is small (~3 fields). Impact is minimal (~2-3KB). Monitor if performance becomes an issue.

## Implementation Order Summary

1. Backend models → 2. Backend service → 3. Backend verification
2. Frontend types → 5. Frontend component → 6. Frontend integration → 7. Frontend styling

This order ensures:
- Type safety throughout (backend types drive frontend types)
- Incremental testing at each layer
- Can stop and verify at each phase
- Frontend work only starts when backend is stable
