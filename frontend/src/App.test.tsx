import { describe, it, expect, vi, beforeEach } from 'vitest'
import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import App from './App'

describe('App', () => {
  beforeEach(() => {
    global.fetch = vi.fn()
  })

  it('should render the life expectancy calculator', () => {
    render(<App />)
    expect(screen.getByText('Life Expectancy Calculator')).toBeInTheDocument()
    expect(screen.getByLabelText('Enter your birthdate:')).toBeInTheDocument()
    expect(screen.getByText('Gender:')).toBeInTheDocument()
  })

  it('should render all three gender options', () => {
    render(<App />)
    expect(screen.getByText('Male')).toBeInTheDocument()
    expect(screen.getByText('Female')).toBeInTheDocument()
    expect(screen.getByText('Other/Prefer not to say')).toBeInTheDocument()
  })

  it('should have male selected by default', () => {
    render(<App />)
    const maleRadio = screen.getByDisplayValue('MALE')
    expect(maleRadio).toBeChecked()
  })

  it('should allow gender selection', () => {
    render(<App />)
    const femaleRadio = screen.getByDisplayValue('FEMALE')
    fireEvent.click(femaleRadio)
    expect(femaleRadio).toBeChecked()
  })

  it('should call API with birthdate and gender parameters', async () => {
    const mockResponse = {
      age: 30,
      timeRemaining: {
        years: 55,
        days: 100,
      },
    }

    global.fetch = vi.fn().mockResolvedValueOnce({
      ok: true,
      json: async () => mockResponse,
    })

    render(<App />)
    const birthdateInput = screen.getByLabelText('Enter your birthdate:')
    const maleRadio = screen.getByDisplayValue('MALE')
    const button = screen.getByRole('button', { name: /calculate/i })

    fireEvent.change(birthdateInput, { target: { value: '1993-01-01' } })
    fireEvent.click(maleRadio)
    fireEvent.click(button)

    await waitFor(() => {
      expect(global.fetch).toHaveBeenCalledWith(
        expect.stringContaining('/api/age?birthdate=1993-01-01&gender=MALE'),
      )
    })
  })

  it('should display age and time remaining after successful calculation', async () => {
    const mockResponse = {
      age: 30,
      timeRemaining: {
        years: 55,
        days: 100,
      },
    }

    global.fetch = vi.fn().mockResolvedValueOnce({
      ok: true,
      json: async () => mockResponse,
    })

    render(<App />)
    const birthdateInput = screen.getByLabelText('Enter your birthdate:')
    const button = screen.getByRole('button', { name: /calculate/i })

    fireEvent.change(birthdateInput, { target: { value: '1993-01-01' } })
    fireEvent.click(button)

    await waitFor(() => {
      expect(screen.getByText(/your age:/i)).toBeInTheDocument()
      expect(screen.getByText(/30 years/i)).toBeInTheDocument()
      expect(screen.getByText(/55 years and 100 days/i)).toBeInTheDocument()
    })
  })

  it('should display special message when life expectancy is exceeded', async () => {
    const mockResponse = {
      age: 95,
      timeRemaining: {
        years: 0,
        days: 0,
      },
    }

    global.fetch = vi.fn().mockResolvedValueOnce({
      ok: true,
      json: async () => mockResponse,
    })

    render(<App />)
    const birthdateInput = screen.getByLabelText('Enter your birthdate:')
    const button = screen.getByRole('button', { name: /calculate/i })

    fireEvent.change(birthdateInput, { target: { value: '1930-01-01' } })
    fireEvent.click(button)

    await waitFor(() => {
      expect(
        screen.getByText(/you have exceeded the statistical life expectancy/i),
      ).toBeInTheDocument()
    })
  })

  it('should display disclaimer about statistical averages', async () => {
    const mockResponse = {
      age: 30,
      timeRemaining: {
        years: 55,
        days: 100,
      },
    }

    global.fetch = vi.fn().mockResolvedValueOnce({
      ok: true,
      json: async () => mockResponse,
    })

    render(<App />)
    const birthdateInput = screen.getByLabelText('Enter your birthdate:')
    const button = screen.getByRole('button', { name: /calculate/i })

    fireEvent.change(birthdateInput, { target: { value: '1993-01-01' } })
    fireEvent.click(button)

    await waitFor(() => {
      expect(
        screen.getByText(/these are statistical averages and individual outcomes vary/i),
      ).toBeInTheDocument()
    })
  })

  it('should handle API error responses', async () => {
    global.fetch = vi.fn().mockResolvedValueOnce({
      ok: false,
      json: async () => ({ error: 'Birthdate cannot be in the future' }),
    })

    render(<App />)
    const birthdateInput = screen.getByLabelText('Enter your birthdate:')
    const button = screen.getByRole('button', { name: /calculate/i })

    fireEvent.change(birthdateInput, { target: { value: '2030-01-01' } })
    fireEvent.click(button)

    await waitFor(() => {
      expect(screen.getByText(/birthdate cannot be in the future/i)).toBeInTheDocument()
    })
  })

  it('should handle network errors', async () => {
    global.fetch = vi.fn().mockRejectedValueOnce(new Error('Network error'))

    render(<App />)
    const birthdateInput = screen.getByLabelText('Enter your birthdate:')
    const button = screen.getByRole('button', { name: /calculate/i })

    fireEvent.change(birthdateInput, { target: { value: '1993-01-01' } })
    fireEvent.click(button)

    await waitFor(() => {
      expect(screen.getByText(/failed to connect to backend/i)).toBeInTheDocument()
    })
  })
})
