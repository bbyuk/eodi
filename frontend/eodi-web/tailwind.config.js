// tailwind.config.js
module.exports = {
  content: ["./app/**/*.{js,ts,jsx,tsx}", "./components/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      fontFamily: {
        sans: ['"Pretendard"', "ui-sans-serif", "system-ui", "sans-serif"],
      },
      colors: {
        primary: {
          DEFAULT: "#2563EB", // blue-600
          hover: "#1E40AF", // blue-800
          light: "#60A5FA", // blue-400
          bg: "#EFF6FF", // blue-50
        },
        text: {
          primary: "#171717",
          secondary: "#6B7280",
        },
        border: {
          DEFAULT: "#E5E7EB",
          focus: "#93C5FD",
        },
      },
    },
  },
  plugins: [],
};
