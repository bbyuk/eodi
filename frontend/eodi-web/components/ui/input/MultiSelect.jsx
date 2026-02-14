"use client";

import { ChevronDown } from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function MultiSelect({
  options,
  value = [],
  onChange,
  onApplied,
  placeholder = "선택하세요",
  width = "w-auto",
}) {
  const [draftValue, setDraftValue] = useState(value);

  const [open, setOpen] = useState(false);
  const containerRef = useRef(null);

  // 드롭다운 열릴 때 value 동기화
  useEffect(() => {
    setDraftValue(value);
  }, [open, value]);

  // 바깥 클릭 시 닫힘
  useEffect(() => {
    function handleClickOutside(e) {
      if (containerRef.current && !containerRef.current.contains(e.target)) {
        setOpen(false);
      }
    }

    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const toggleValue = (val) => {
    if (draftValue.includes(val)) {
      setDraftValue(draftValue.filter((v) => v !== val));
    } else {
      setDraftValue([...draftValue, val]);
    }
  };

  const selectedLabels = options.filter((o) => draftValue.includes(o.value)).map((o) => o.label);

  const label =
    selectedLabels.length === 0
      ? placeholder
      : selectedLabels.length <= 2
        ? selectedLabels.join(", ")
        : `${selectedLabels[0]} 외 ${selectedLabels.length - 1}개`;

  return (
    <div ref={containerRef} className={`relative ${width}`}>
      <button
        onClick={() => setOpen((v) => !v)}
        className="w-full h-10 flex items-center justify-between rounded-full border px-4 text-sm bg-white hover:bg-gray-50"
      >
        <span className="truncate">{label}</span>
        <ChevronDown size={16} className="shrink-0" />
      </button>

      {open && (
        <div className="absolute left-0 top-full z-50 mt-2 w-[240px] rounded-xl border bg-white shadow-xl">
          {/* 옵션 영역 */}
          <div className="max-h-[220px] overflow-y-auto p-2 space-y-1">
            {options.map((option) => (
              <label
                key={option.value}
                className="flex items-center gap-2 px-3 py-2 rounded-md text-sm hover:bg-gray-100 cursor-pointer"
              >
                <input
                  type="checkbox"
                  checked={draftValue.includes(option.value)}
                  onChange={() => toggleValue(option.value)}
                  className="accent-primary"
                />
                <span className="whitespace-nowrap">{option.label}</span>
              </label>
            ))}
          </div>

          {/* 버튼 영역 */}
          <div className="border-t p-2 bg-white rounded-b-xl">
            <div className="flex gap-2">
              <button
                onClick={() => {
                  onApplied(draftValue);
                  setOpen(false);
                }}
                className="flex-1 h-9 rounded-md bg-primary text-white text-sm hover:opacity-90 transition"
              >
                적용
              </button>

              <button
                onClick={() => {
                  setOpen(false);
                }}
                className="flex-1 h-9 rounded-md border text-sm hover:bg-gray-50 transition"
              >
                취소
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
