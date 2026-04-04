"use client";

import { useRef } from "react";

function sanitizeNumber(raw, { allowDecimal, decimalScale, allowNegative }) {
  let value = raw;

  // 허용 문자만 남기기
  const allowedRegex = allowDecimal
    ? allowNegative
      ? /[^0-9.-]/g
      : /[^0-9.]/g
    : allowNegative
      ? /[^0-9-]/g
      : /[^0-9]/g;

  value = value.replace(allowedRegex, "");

  // 마이너스는 맨 앞 하나만 허용
  if (allowNegative) {
    const isNegative = value.startsWith("-");
    value = value.replace(/-/g, "");
    if (isNegative) value = `-${value}`;
  } else {
    value = value.replace(/-/g, "");
  }

  // 소수점 하나만 허용
  if (allowDecimal) {
    const negative = value.startsWith("-");
    const body = negative ? value.slice(1) : value;

    const parts = body.split(".");
    const integerPart = parts[0] ?? "";
    const decimalPart = parts.slice(1).join("");

    value = negative ? `-${integerPart}` : integerPart;

    if (body.includes(".")) {
      value += ".";
      if (decimalScale >= 0) {
        value += decimalPart.slice(0, decimalScale);
      } else {
        value += decimalPart;
      }
    }
  } else {
    value = value.replace(/\./g, "");
  }

  return value;
}

export default function NumberInput({
  label,
  unit,
  value,
  onChange,
  onEnter,
  formatter,
  maxValue,
  minValue,
  decimalScale = 0,
  allowNegative = false,
  className = "",
  ...props
}) {
  const inputRef = useRef(null);
  const allowDecimal = decimalScale > 0;

  const handleChange = (e) => {
    const input = e.target;
    const raw = input.value;
    const pos = input.selectionStart ?? raw.length;

    const cleaned = sanitizeNumber(raw, {
      allowDecimal,
      decimalScale,
      allowNegative,
    });

    // 숫자로 해석 가능한 경우만 min/max 검사
    const parsed = cleaned === "" || cleaned === "-" || cleaned === "." ? null : Number(cleaned);

    if (parsed !== null) {
      if (Number.isNaN(parsed)) return;
      if (maxValue != null && parsed > maxValue) return;
      if (minValue != null && parsed < minValue) return;
    }

    const diff = raw.length - cleaned.length;
    const nextPos = pos - diff;

    requestAnimationFrame(() => {
      if (!inputRef.current) return;
      const fixedPos = Math.max(0, Math.min(nextPos, cleaned.length));
      inputRef.current.setSelectionRange(fixedPos, fixedPos);
    });

    onChange?.(cleaned);
  };

  return (
    <section className={`flex flex-col gap-2 ${className}`}>
      {(label || formatter) && (
        <div className="flex justify-between items-end">
          {label ? <label className="text-sm font-medium text-text-secondary">{label}</label> : <span />}
          {formatter && <span className="text-xs text-text-secondary">{formatter(value)}</span>}
        </div>
      )}

      <div className="relative">
        <input
          ref={inputRef}
          type="text"
          inputMode={allowDecimal ? "decimal" : "numeric"}
          value={value}
          onChange={handleChange}
          onKeyUp={(e) => {
            if (e.code === "Enter") onEnter?.();
          }}
          className={`w-full px-4 py-3 border border-border rounded-lg text-right text-text-primary
            placeholder:text-text-secondary focus:ring-2 focus:ring-[var(--input-focus-ring)]
            focus:border-[var(--input-focus-border)] focus:outline-none transition
            ${unit ? "pr-16" : "pr-4"}`}
          {...props}
        />

        {unit && (
          <span className="absolute right-4 top-1/2 -translate-y-1/2 text-text-secondary text-sm">
            {unit}
          </span>
        )}
      </div>
    </section>
  );
}
