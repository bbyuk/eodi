"use client";

import { ChevronDown } from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function Select({
  options,
  value,
  onChange,
  allOption = false,
  placeholder = "선택하세요",
  width = "w-auto",
}) {
  const [open, setOpen] = useState(false);
  const containerRef = useRef(null);

  const mergedOptions = allOption ? [{ value: "all", label: "전체" }, ...options] : options;
  const selected = mergedOptions.find((o) => o.value === value);

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

  return (
    <div ref={containerRef} className={`relative ${width}`}>
      <button
        type="button"
        onClick={() => setOpen((v) => !v)}
        className={`flex h-11 w-full items-center justify-between overflow-hidden rounded-full border px-4 py-2 text-sm transition focus:outline-none focus-visible:ring-4 ${
          open
            ? "border-[var(--select-trigger-open-border)] bg-[var(--select-trigger-open-bg)] text-[var(--select-trigger-text)] ring-[var(--select-trigger-focus)]"
            : "border-[var(--select-trigger-border)] bg-[var(--select-trigger-bg)] text-[var(--select-trigger-text)] hover:border-[var(--select-trigger-hover-border)] hover:bg-[var(--select-trigger-open-bg)] focus-visible:border-[var(--select-trigger-open-border)] focus-visible:ring-[var(--select-trigger-focus)]"
        }`}
        aria-expanded={open}
      >
        <span
          className={`truncate whitespace-nowrap ${selected ? "text-[var(--select-trigger-text)]" : "text-[var(--select-trigger-placeholder)]"}`}
        >
          {selected ? selected.label : placeholder}
        </span>

        <ChevronDown
          size={16}
          className={`shrink-0 transition ${open ? "rotate-180 text-slate-600" : "text-slate-400"}`}
        />
      </button>

      {open && (
        <div className="absolute left-0 top-full z-20 mt-2 w-full min-w-[180px] max-h-[300px] overflow-y-auto rounded-[1.1rem] border border-[var(--select-panel-border)] bg-[var(--select-panel-bg)] p-2 shadow-[var(--select-panel-shadow)] backdrop-blur-sm">
          {mergedOptions.map((option) => (
            <button
              key={option.value}
              type="button"
              onClick={() => {
                onChange(option.value);
                setOpen(false);
              }}
              className={`w-full whitespace-nowrap rounded-[0.9rem] border px-3 py-2.5 text-left text-sm font-medium transition focus:outline-none ${
                value === option.value
                  ? "border-[var(--select-option-selected-border)] bg-[var(--select-option-selected-bg)] text-[var(--select-option-selected-text)]"
                  : "border-transparent text-[var(--select-option-text)] hover:bg-[var(--select-option-hover-bg)] active:bg-[var(--select-option-active-bg)]"
              }`}
            >
              {option.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}
