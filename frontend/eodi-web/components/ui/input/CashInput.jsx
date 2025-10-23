"use client";
import { useState, useEffect, useRef } from "react";

/**
 * 금액 입력 UI 컴포넌트
 * @param label 좌측 상단 label
 * @param value 값
 * @param placeholder placeholder
 * @param onChange onChange event handler
 * @param unit 단위
 */
export default function CashInput({
  label,
  unit,
  onChange,
  formatter,
  maxValue = 1000000000,
  ...props
}) {
  const inputRef = useRef(null);

  const handleChange = (e) => {
    const input = e.target;
    const raw = input.value;
    const pos = input.selectionStart;

    // 숫자만 남기기
    const cleaned = raw.replace(/[^0-9]/g, "");
    const num = Number(cleaned);

    // 최대값 제한 (초과 시 입력 무시)
    if (num > maxValue) return;

    // 삭제된 문자 수만큼 커서 위치 보정
    const diff = raw.length - cleaned.length;
    const nextPos = pos - diff;

    // 즉시 커서 복원 예약 (렌더 전에)
    requestAnimationFrame(() => {
      if (inputRef.current) {
        const fixedPos = Math.max(0, Math.min(nextPos, cleaned.length));
        inputRef.current.setSelectionRange(fixedPos, fixedPos);
      }
    });

    // 상태 반영
    onChange(cleaned);
  };

  return (
    <section className="flex flex-col gap-2">
      {/* label + formatter 한 줄 */}
      <div className="flex justify-between items-end">
        <label className="text-sm font-medium text-text-secondary">{label}</label>
        {formatter && <span className="text-xs text-text-secondary">{formatter(props.value)}</span>}
      </div>

      {/* input + unit */}
      <div className="relative">
        <input
          ref={inputRef}
          type="text"
          inputMode="numeric"
          className={`w-full px-4 py-3 border border-border rounded-lg text-right text-text-primary
                  placeholder:text-text-secondary focus:ring-2 focus:ring-primary
                  focus:border-primary focus:outline-none transition
                  ${unit ? "pr-12" : "pr-4"}`}
          onChange={handleChange}
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
