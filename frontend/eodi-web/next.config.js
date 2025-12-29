// next.config.js
const path = require("path");

const nextConfig = {
  experimental: {},
  webpack: (config) => {
    config.resolve.alias["@"] = path.resolve(process.cwd(), "app");
    return config;
  },
  eslint: {
    ignoreDuringBuilds: true,
  },
  output: "standalone",
};

module.exports = nextConfig;
