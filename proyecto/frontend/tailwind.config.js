/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#1E1E1E',           // Negro elegante
        secondary: '#3A5A74',         // Azul corporativo sobrio
        accent: '#6BB2A7',            // Verde suave
        'accent-dark': '#417B73',     // Verde más oscuro para títulos
        'secondary-light': '#6B7280', // Gris para texto secundario
        background: '#F5F5F5',        // Gris muy claro para fondo
      },
    },
  },
  plugins: [],
}
