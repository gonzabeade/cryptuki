/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      screens: {
        'tablet': '960px',
        'desktop': '1248px',
      },
      colors: {

        polar: '#3B4252',
        polard: '#2E3440',
        polarl: '#434C5E',
        polarlr: '#4C566A',

        stormg: '#7386a2',
        storm: '#E5E9F0',
        stormd: '#D8DEE9',
        storml: '#ECEFF4',


        frostl: '#8FBCBB',
        frost: '#88C0D0',
        frostd: '#81A1C1',
        frostdr: '#5E81AC',

        nred: '#BF616A',
        nredd:'#ad2b36',
        norange: '#D08770',
        noranged:'#944933',
        nyellow: '#EBCB8B',
        nyellowd:'#936f26',
        ngreen: '#A3BE8C',
        ngreend:'#47523e',
      },
      boxShadows: {
        sm: '0px 2px 4px 0px rgba(11, 10, 55, 0.15)',
        lg: '0px 8px 20px Opx rgba(18, 16, 99, 0.06)'
      },
      fontFamily: {
        roboto: ['Roboto', 'sans-serif'],
        lato: ['Lato', 'sans-serif'],
      },
      fontSize: {
        xs: ['14px', {
          lineHeight: '24px',
          letterSpacing: '-0.03em'
        }],
        sm: ['16px', {
          lineHeight: '28px',
          letterSpacing: '-0.03em'
        }],
        lg: ['18px', {
          lineHeight: '28px',
          letterSpacing: '-0.03em'
        }],
        xl: ['24px', {
          lineHeight: '36px',
          letterSpacing: '-0.03em'
        }],
        '2xl': ['36px', {
          lineHeight: '48px',
          letterSpacing: '-0.032em'
        }],
        '3xl': ['48px', {
          lineHeight: '56px',
          letterSpacing: '-0.032em'
        }],
        '4xl': ['56px', {
          lineHeight: '64px',
          letterSpacing: '-0.032em'
        }],
        '5xl': ['80px', {
          lineHeight: '80px',
          letterSpacing: '-0.032em'
        }]
      },
    },
  },
  plugins: [],
}