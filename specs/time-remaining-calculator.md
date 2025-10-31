# Feature: Life Expectancy Calculator

## Feature Description
Calculate and display a user's estimated time remaining to live based on their birthdate and gender identity, using statistical life expectancy averages (males: 85 years, females: 90 years, other/prefer not to say: 87 years). The feature will extend the existing age calculation functionality to provide both current age and estimated remaining time in an inclusive manner that respects all gender identities.

## User Story
As a user of the application
I want to know my estimated time left to live based on my birthdate and gender
So that I can reflect on my life journey and time remaining

## Problem Statement
The application currently only calculates a user's current age from their birthdate. Users may want additional context about their life expectancy based on demographic statistics to gain perspective on their remaining time.

## Solution Statement
Extend the existing age calculation feature by adding an inclusive gender parameter and life expectancy calculation logic. The backend will compute both current age and estimated years/days remaining based on gender-specific life expectancy averages (males: 85 years, females: 90 years, other/prefer not to say: 87 years). The frontend will be updated to include gender selection with three options and display the additional information in a clear, respectful manner that acknowledges diverse gender identities.

## Relevant Files
Use these files to implement the feature:

### Backend Files
- `backend/src/main/kotlin/de/codecentric/heather/age/AgeCalculator.kt` - Contains the age calculation logic; will be extended to calculate time remaining based on life expectancy
- `backend/src/main/kotlin/de/codecentric/heather/age/AgeController.kt` - REST endpoint for age calculation; will be updated to accept gender parameter and return time remaining data
- `backend/src/test/kotlin/de/codecentric/heather/age/AgeCalculatorTest.kt` - Unit tests for age calculator; will be expanded to test life expectancy calculations with various scenarios

### Frontend Files
- `frontend/src/App.tsx` - Main React component with age calculator UI; will be updated to include gender selection and display time remaining
- `frontend/src/App.css` - Styling for the application; may need updates for new UI elements

### New Files
- `backend/src/main/kotlin/de/codecentric/heather/age/LifeExpectancyCalculator.kt` - New service class to encapsulate life expectancy calculation logic
- `backend/src/test/kotlin/de/codecentric/heather/age/LifeExpectancyCalculatorTest.kt` - Unit tests for life expectancy calculator

## Implementation Plan
### Phase 1: Foundation
Create core life expectancy calculation logic with proper domain modeling. Define data structures for gender and life expectancy constants. Establish testing patterns for the new functionality.

### Phase 2: Core Implementation
Implement backend API changes to support gender parameter and return time remaining calculations. Update the AgeController endpoint to accept gender and leverage the new LifeExpectancyCalculator service. Ensure proper validation and error handling.

### Phase 3: Integration
Update the frontend to collect gender information and display time remaining data. Regenerate API types from OpenAPI specifications. Connect the UI to the enhanced backend endpoint and ensure proper data flow.

## Step by Step Tasks

### Create LifeExpectancyCalculator service class
- Create new Kotlin service class with gender enum (MALE, FEMALE, OTHER)
- Define life expectancy constants (85 for male, 90 for female, 87 for other/prefer not to say)
- Implement calculateTimeRemaining method that returns years and days remaining
- Handle edge cases where person has already exceeded life expectancy

### Write unit tests for LifeExpectancyCalculator
- Test male life expectancy calculation with various ages
- Test female life expectancy calculation with various ages
- Test other/prefer not to say life expectancy calculation with various ages
- Test edge case where current age exceeds life expectancy
- Test edge case where birthdate is very recent
- Verify remaining days calculation accuracy

### Update AgeCalculator to integrate with LifeExpectancyCalculator
- Add LifeExpectancyCalculator dependency injection
- Create method to calculate comprehensive age data including time remaining
- Maintain backward compatibility with existing age calculation

### Update AgeController to accept gender parameter
- Add gender query parameter to /api/age endpoint
- Make gender parameter required with validation
- Update response to include timeRemaining data structure
- Add proper error handling for invalid gender values
- Update API documentation annotations for OpenAPI

### Write integration tests for updated AgeController
- Test endpoint with male gender parameter
- Test endpoint with female gender parameter
- Test endpoint with other gender parameter
- Test validation errors for missing/invalid gender
- Test edge cases (future birthdate, exceeded life expectancy)

### Run backend tests to ensure no regressions
- Execute `cd backend && ./gradlew test` to verify all tests pass
- Fix any test failures or regressions
- Verify code coverage remains high

### Generate OpenAPI specification from updated backend
- Start backend server with `cd backend && ./gradlew bootRun`
- Wait for server to be fully running and OpenAPI endpoint available
- Execute `cd backend && ./gradlew generateOpenApiDocs` to generate OpenAPI JSON
- Verify openapi.json is created in frontend/src/api/generated/

### Regenerate frontend API types from OpenAPI
- Execute `cd frontend && npm run generate-api` to generate TypeScript types
- Verify generated types include gender parameter and timeRemaining response fields
- Check for any type generation errors or warnings

### Update frontend App.tsx to include gender selection
- Add gender state variable (male/female/other)
- Add radio buttons or dropdown for gender selection with three inclusive options
- Update calculateAge function to include gender parameter in API call
- Add state for timeRemaining data
- Update UI to display years and days remaining
- Handle edge case display when life expectancy is exceeded
- Ensure proper TypeScript typing with import type syntax for generated types

### Update frontend styling for new UI elements
- Add CSS for gender selection controls
- Add CSS for time remaining display section
- Ensure responsive design
- Maintain consistent styling with existing UI

### Write frontend tests for updated component
- Test gender selection interaction
- Test API call includes gender parameter
- Test time remaining data is displayed correctly
- Test edge case display when life expectancy exceeded
- Test error handling for API failures

### Run validation commands
- Execute `cd backend && ./gradlew test` to ensure backend tests pass
- Execute `cd frontend && npm test` to ensure frontend tests pass
- Execute `cd backend && ./gradlew bootRun` in background to start server
- Execute `cd frontend && npm run dev` to start frontend and manually test
- Manually verify the full user flow: enter birthdate, select gender, calculate, see results
- Execute `cd backend && ./gradlew detektTwice` to ensure code quality standards met
- Execute `cd frontend && npm run lint` to ensure frontend code quality

## Testing Strategy
### Unit Tests
- LifeExpectancyCalculator: Test calculation logic for all three gender options with various ages
- LifeExpectancyCalculator: Test boundary conditions (age 0, age exceeding life expectancy)
- AgeController: Test endpoint with valid inputs for male, female, and other
- AgeController: Test validation errors for missing/invalid gender parameter
- Frontend Component: Test gender selection state management for all three options
- Frontend Component: Test API integration with gender parameter

### Integration Tests
- End-to-end test: Submit birthdate and gender, verify correct time remaining calculation
- Test OpenAPI spec generation includes gender parameter
- Test TypeScript type generation creates correct types for gender enum
- Test API error responses are handled gracefully in frontend

### Edge Cases
- Birthdate resulting in age exceeding life expectancy (should show 0 or negative years)
- Birthdate very recent (newborn baby)
- Future birthdate (should be rejected by backend validation)
- Missing gender parameter (should return 400 bad request)
- Invalid gender value (should return 400 bad request)
- Leap year birthdates for accurate days calculation

## Acceptance Criteria
- Backend endpoint accepts gender parameter (male/female/other) and returns calculated time remaining
- Time remaining calculation uses correct life expectancy (male: 85, female: 90, other: 87 years)
- Response includes both years and days remaining
- Frontend displays inclusive gender selection UI with three options (radio buttons or dropdown)
- Frontend displays calculated time remaining in a clear, respectful format
- All existing functionality continues to work (current age calculation)
- All unit tests pass with >80% code coverage for new code
- Integration tests verify end-to-end flow for all three gender options
- OpenAPI specification is updated and TypeScript types generated correctly
- Code passes linting and formatting checks (detekt for backend, eslint for frontend)
- Manual testing confirms the feature works correctly in the browser for all gender options

## Validation Commands
Execute every command to validate the feature works correctly with zero regressions.

- `cd backend && ./gradlew test` - Run backend tests to validate the feature works with zero regressions
- `cd backend && ./gradlew detektTwice` - Ensure backend code meets quality standards
- `cd frontend && npm test` - Run frontend tests to validate component behavior
- `cd frontend && npm run lint` - Ensure frontend code meets quality standards
- `cd frontend && npm run typecheck` - Verify TypeScript types are correct
- `cd backend && ./gradlew bootRun` - Start backend server for manual verification (run in background)
- `cd frontend && npm run dev` - Start frontend for manual verification (run in background)
- Manual test: Open browser to http://localhost:5173, enter birthdate, select gender (male), verify years remaining calculation based on 85 year life expectancy
- Manual test: Open browser to http://localhost:5173, enter birthdate, select gender (female), verify years remaining calculation based on 90 year life expectancy
- Manual test: Open browser to http://localhost:5173, enter birthdate, select gender (other/prefer not to say), verify years remaining calculation based on 87 year life expectancy
- Manual test: Test edge cases (very old person exceeding life expectancy shows appropriate message)

## Notes
- Life expectancy values (85 for male, 90 for female, 87 for other) are configurable and could be externalized to application properties in the future
- The "other" option uses 87 years (average of male and female) as a reasonable default for those who don't identify with binary gender options or prefer not to specify
- The feature uses statistical averages and should not be presented as medical advice
- Consider adding a disclaimer that these are statistical averages and individual outcomes vary significantly
- Future enhancement: Allow customization of life expectancy values based on country/region statistics
- Future enhancement: Add more granular life expectancy data based on additional factors (health, lifestyle, etc.)
- The gender parameter includes three options (MALE, FEMALE, OTHER) to be inclusive of diverse gender identities
- UI messaging should be respectful and not overly morbid when displaying time remaining
- Consider formatting options: "X years and Y days remaining" vs "X.Y years remaining"
- The UI should label the third option thoughtfully (e.g., "Other/Prefer not to say" or "Non-binary/Other")
