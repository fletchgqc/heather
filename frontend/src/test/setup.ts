import '@testing-library/jest-dom'
import { cleanup } from '@testing-library/react'
import { afterEach } from 'vitest'

it("should calculate 2 + 2 correctly", async () => {
  expect(2 + 2).equals(4)
})

afterEach(() => {
  cleanup()
})
