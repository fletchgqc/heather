import { describe, it, expect, vi } from 'vitest'
import { render, screen, waitFor } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'

describe('App', () => {
  it('should render the Life left button', () => {
    render(<App />)
    const lifeLeftButton = screen.getByTestId('life-left-button')
    expect(lifeLeftButton).toBeInTheDocument()
  })

  it('should have Life left button disabled when no date is entered', () => {
    render(<App />)
    const lifeLeftButton = screen.getByTestId('life-left-button')
    expect(lifeLeftButton).toBeDisabled()
  })

  it('should have Life left button enabled when a date is entered', async () => {
    const user = userEvent.setup()
    render(<App />)

    const birthdateInput = screen.getByTestId('birthdate-input')
    const lifeLeftButton = screen.getByTestId('life-left-button')

    await user.type(birthdateInput, '1990-01-01')

    expect(lifeLeftButton).toBeEnabled()
  })

  it('should calculate and display life left when button is clicked', async () => {
    const user = userEvent.setup()

    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ age: 45 }),
      })
    ) as unknown as typeof fetch

    render(<App />)

    const birthdateInput = screen.getByTestId('birthdate-input')
    await user.type(birthdateInput, '1980-01-01')

    const calculateButton = screen.getByTestId('calculate-age-button')
    await user.click(calculateButton)

    await waitFor(() => {
      expect(screen.getByTestId('age-result')).toBeInTheDocument()
    })

    const lifeRemainingButton = screen.getByTestId('life-left-button')
    await user.click(lifeRemainingButton)

    expect(screen.getByTestId('life-left-result')).toBeInTheDocument()
    expect(screen.getByTestId('life-left-result')).toHaveTextContent('30')
  })

  it.each([
    { gender: 'female' as const, lifeExpectancy: 85, expectedYearsLeft: 40 },
    { gender: 'male' as const, lifeExpectancy: 75, expectedYearsLeft: 30 },
  ])('should calculate life left for $gender using $lifeExpectancy years expectancy', async ({ gender, expectedYearsLeft }) => {
    const user = userEvent.setup()

    global.fetch = vi.fn(() =>
      Promise.resolve({
        ok: true,
        json: () => Promise.resolve({ age: 45 }),
      })
    ) as unknown as typeof fetch

    render(<App />)

    const genderSelect = screen.getByTestId('gender-select')
    await user.selectOptions(genderSelect, gender)

    const birthdateInput = screen.getByTestId('birthdate-input')
    await user.type(birthdateInput, '1980-01-01')

    const calculateButton = screen.getByTestId('calculate-age-button')
    await user.click(calculateButton)

    await waitFor(() => {
      expect(screen.getByTestId('age-result')).toBeInTheDocument()
    })

    const lifeRemainingButton = screen.getByTestId('life-left-button')
    await user.click(lifeRemainingButton)

    expect(screen.getByTestId('life-left-result')).toBeInTheDocument()
    expect(screen.getByTestId('life-left-result')).toHaveTextContent(expectedYearsLeft.toString())
  })
})
