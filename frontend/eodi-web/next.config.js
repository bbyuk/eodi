// next.config.js
const path = require("path");

const nextConfig = {
  experimental: {
    disableTurbopack: true, // Turbopack 비활성화 (Webpack 사용)
  },
  webpack: (config) => {
    config.resolve.alias["@"] = path.resolve(process.cwd(), "app");
    return config;
  },
  eslint: {
    ignoreDuringBuilds: true,
  },
};

module.exports = nextConfig;
