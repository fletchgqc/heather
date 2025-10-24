import { useState } from 'react'
import './App.css'

function App() {
  const [birthdate, setBirthdate] = useState('')
  const [age, setAge] = useState<number | null>(null)
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)
  const [lifeLeft, setLifeLeft] = useState<number | null>(null)
  const [gender, setGender] = useState<'male' | 'female'>('male')

  const calculateAge = async () => {
    if (!birthdate) {
      setError('Please enter a birthdate')
      return
    }

    setLoading(true)
    setError(null)

    try {
      const apiBaseUrl = import.meta.env.VITE_API_BASE_URL || ''
      const response = await fetch(`${apiBaseUrl}/api/age?birthdate=${birthdate}`)
      const data = await response.json()

      if (!response.ok) {
        setError(data.error || 'Failed to calculate age')
        setAge(null)
      } else {
        setAge(data.age)
        setError(null)
      }
    } catch {
      setError('Failed to connect to backend')
      setAge(null)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="App">
      <header className="App-header">
        <h1>Hello World - Technical Walking Skeleton</h1>
        <p>This is a minimal application demonstrating frontend-backend integration.</p>

        <div className="age-calculator">
          <h2>Age Calculator</h2>
          <div className="form-group">
            <label htmlFor="gender">Gender:</label>
            <select
              id="gender"
              value={gender}
              onChange={(e) => setGender(e.target.value as 'male' | 'female')}
              data-testid="gender-select"
            >
              <option value="male">Male</option>
              <option value="female">Female</option>
            </select>
          </div>
          <div className="form-group">
            <label htmlFor="birthdate">Enter your birthdate:</label>
            <input
              id="birthdate"
              type="date"
              value={birthdate}
              onChange={(e) => setBirthdate(e.target.value)}
              max={new Date().toISOString().split('T')[0]}
              data-testid="birthdate-input"
            />
          </div>

          <button onClick={calculateAge} disabled={loading || !birthdate} data-testid="calculate-age-button">
            {loading ? 'Calculating...' : 'Calculate Age'}
          </button>

          <button onClick={() => {
            const lifeExpectancy = gender === 'female' ? 85 : 75
            setLifeLeft(lifeExpectancy - (age || 0))
          }} disabled={!birthdate} data-testid="life-left-button">
            Life left
          </button>

          {lifeLeft !== null && (
            <div className="result success" data-testid="life-left-result">
              <p>
                <strong>{lifeLeft} years left</strong>
              </p>
            </div>
          )}

          {age !== null && (
            <div className="result success" data-testid="age-result">
              <p>
                Your age is: <strong>{age} years</strong>
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
