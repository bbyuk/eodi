// /lib/apiClient.js

const BASE_URL = (() => {
  const value = process.env.NEXT_PUBLIC_API_BASE_URL;

  if (!value) throw new Error("BASE_URL environment variable is not set");

  return value;
})();

/**
 * 객체 → 쿼리스트링 변환
 * { a: 1, b: "x y" } → "?a=1&b=x%20y"
 */
function toQueryString(params) {
  if (!params || Object.keys(params).length === 0) return "";

  const parts = [];

  Object.entries(params).forEach(([key, value]) => {
    if (Array.isArray(value)) {
      value.forEach((v) => parts.push(`${key}=${encodeURIComponent(v)}`));
    } else if (typeof value === "object") {
      throw new Error("GET 파라미터로 객체를 전달할 수 없습니다.");
    } else {
      parts.push(`${key}=${encodeURIComponent(value)}`);
    }
  });

  return `?${parts.join("&")}`;
}

/**
 * fetch wrapper for unified API calls
 * - 기본 헤더, 에러 처리, JSON 자동 파싱
 * - axios처럼 get/post/put/delete 간단히 호출 가능
 */
async function request(endpoint, { method = "GET", payload, headers, ...customConfig } = {}) {
  if (payload && typeof payload !== "object") {
    throw new Error("잘못된 payload 타입입니다.");
  }

  const config = {
    method,
    headers: {
      "Content-Type": "application/json",
      ...headers,
    },
    ...customConfig,
  };
  let url = `${BASE_URL}${endpoint}`;

  if (method === "GET") {
    // payload -> parameter
    url += toQueryString(payload);
  } else {
    // payload -> request body
    config.body = JSON.stringify(payload);
  }
  const response = await fetch(url, config);

  // 에러 공통 처리
  if (!response.ok) {
    let errorMsg = "";
    try {
      const errorData = await response.json();
      errorMsg = errorData.message || JSON.stringify(errorData);
    } catch {
      errorMsg = await response.text();
    }
    throw new Error(`API Error ${response.status}: ${errorMsg}`);
  }

  // 응답이 JSON이 아닐 수도 있음 (204 등)
  const contentType = response.headers.get("content-type");
  if (contentType && contentType.includes("application/json")) {
    return response.json();
  }
  return null;
}

/**
 * axios-style wrapper
 */
export const api = {
  get: (url, payload, config) => request(url, { ...config, method: "GET", payload }),
  post: (url, payload, config) => request(url, { ...config, method: "POST", payload }),
  put: (url, payload, config) => request(url, { ...config, method: "PUT", payload }),
  patch: (url, payload, config) => request(url, { ...config, method: "PATCH", payload }),
  delete: (url, payload, config) => request(url, { ...config, method: "DELETE", payload }),
};
