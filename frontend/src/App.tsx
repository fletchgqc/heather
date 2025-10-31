import React, { useState } from 'react'
import './App.css'

interface TimeRemaining {
  years: number
  days: number
}

interface AgeResponse {
  age: number
  timeRemaining: TimeRemaining
}

function App() {
  const [birthdate, setBirthdate] = useState('')
  const [gender, setGender] = useState<'MALE' | 'FEMALE' | 'OTHER'>('MALE')
  const [age, setAge] = useState<number | null>(null)
  const [timeRemaining, setTimeRemaining] = useState<TimeRemaining | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const calculateAge = async () => {
    if (!birthdate) {
      setError('Please enter a birthdate')
      return
    }

    setLoading(true)
    setError(null)

    try {
      const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || ''
      const response = await fetch(`${apiBaseUrl}/api/age?birthdate=${birthdate}&gender=${gender}`)
      const data: AgeResponse = await response.json()

      if (!response.ok) {
        const errorData = data as unknown as { error?: string }
        setError(errorData.error || 'Failed to calculate age')
        setAge(null)
        setTimeRemaining(null)
      } else {
        setAge(data.age)
        setTimeRemaining(data.timeRemaining)
        setError(null)
      }
    } catch {
      setError('Failed to connect to backend')
      setAge(null)
      setTimeRemaining(null)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="App">
      <header className="App-header">
        <h1>Life Expectancy Calculator</h1>
        <p>Calculate your current age and estimated time remaining based on statistical averages.</p>

        <div className="age-calculator">
          <h2>Enter Your Information</h2>
          <div className="form-group">
            <label htmlFor="birthdate">Enter your birthdate:</label>
            <input
              id="birthdate"
              type="date"
              value={birthdate}
              onChange={(e) => setBirthdate(e.target.value)}
              max={new Date().toISOString().split('T')[0]}
            />
          </div>

          <div className="form-group">
            <label htmlFor="gender">Gender:</label>
            <div className="gender-options">
              <label className="gender-option">
                <input
                  type="radio"
                  name="gender"
                  value="MALE"
                  checked={gender === 'MALE'}
                  onChange={(e) => setGender(e.target.value as 'MALE' | 'FEMALE' | 'OTHER')}
                />
                <span>Male</span>
              </label>
              <label className="gender-option">
                <input
                  type="radio"
                  name="gender"
                  value="FEMALE"
                  checked={gender === 'FEMALE'}
                  onChange={(e) => setGender(e.target.value as 'MALE' | 'FEMALE' | 'OTHER')}
                />
                <span>Female</span>
              </label>
              <label className="gender-option">
                <input
                  type="radio"
                  name="gender"
                  value="OTHER"
                  checked={gender === 'OTHER'}
                  onChange={(e) => setGender(e.target.value as 'MALE' | 'FEMALE' | 'OTHER')}
                />
                <span>Other/Prefer not to say</span>
              </label>
            </div>
          </div>

          <button onClick={calculateAge} disabled={loading || !birthdate}>
            {loading ? 'Calculating...' : 'Calculate'}
          </button>

          {age !== null && timeRemaining !== null && (
            <div className="result success">
              <p>
                Your age: <strong>{age} years</strong>
              </p>
              {timeRemaining.years > 0 ? (
                <p className="time-remaining">
                  Estimated time remaining: <strong>{timeRemaining.years} years and {timeRemaining.days} days</strong>
                </p>
              ) : (
                <p className="time-remaining">
                  You have exceeded the statistical life expectancy. Every day is a gift!
                </p>
              )}
              <p className="disclaimer">
                <em>Note: These are statistical averages and individual outcomes vary significantly.</em>
              </p>
            </div>
          )}

          {error && (
            <div className="result error">
              <p>Error: {error}</p>
            </div>
          )}
        </div>
      </header>
    </div>
  )
}

export default App
