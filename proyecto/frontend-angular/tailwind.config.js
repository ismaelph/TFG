/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#1F192F',
        secondary: '#2D6073',
        accent: '#65B8A6',
        light: '#B5E8C3',
        background: '#F0F7DA',
      },
    },
  },
  plugins: [],
}

