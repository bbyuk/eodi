import path from "path";

const nextConfig = {
  experimental: {
    disableTurbopack: true, // ✅ Webpack 사용
  },
  webpack: (config) => {
    config.resolve.alias["@"] = path.resolve(process.cwd(), "app");
    return config;
  },
  eslint: {
    ignoreDuringBuilds: true,
  },
};

export default nextConfig;
