import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import {VitestReporter} from "tdd-guard-vitest";

export default defineConfig({
  plugins: [react()],
  test: {
    reporters: ['default',new VitestReporter('/Users/benny/opt/ai-coding-tryout/heather')],
    globals: true,
    environment: 'jsdom',
    setupFiles: './src/test/setup.ts',
    coverage: {
      reporter: ['text', 'json', 'html'],
      exclude: [
        'node_modules/',
        'src/test/',
        '*.config.ts',
        '*.config.js',
      ],
    },
  },
});
