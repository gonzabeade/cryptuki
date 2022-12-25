/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {

        polar: '#3B4252',
        polard: '#2E3440',
        polarl: '#434C5E',
        polarlr: '#4C566A',

        storm: '#E5E9F0',
        stormd: '#D8DEE9',
        storml: '#ECEFF4',

        frostl: '#8FBCBB',
        frost: '#88C0D0',
        frostd: '#81A1C1',
        frostdr: '#5E81AC',

        nred: '#BF616A',
        norange: '#D08770',
        nyellow: '#EBCB8B',
        ngreen: '#A3BE8C',
        npurple: '#B48EAD'
      },
      fontFamily: {
        sans: ['Roboto', 'sans-serif'],
        serif: ['serif'],
      }
    },
  },
  plugins: [],
}