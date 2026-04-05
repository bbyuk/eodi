"use client";

import { useEffect, useState } from "react";

export default function useDebouncedValue(value, delay) {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    if (!value.trim()) {
      setDebouncedValue("");
      return undefined;
    }

    const timer = window.setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    return () => {
      window.clearTimeout(timer);
    };
  }, [delay, value]);

  return debouncedValue;
}
