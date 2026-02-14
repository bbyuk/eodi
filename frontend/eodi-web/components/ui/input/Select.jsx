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
        onClick={() => setOpen((v) => !v)}
        className="w-full flex items-center justify-between rounded-full border px-4 py-2 text-sm hover:bg-gray-50 overflow-hidden"
      >
        <span className="truncate whitespace-nowrap">
          {selected ? selected.label : placeholder}
        </span>

        <ChevronDown size={16} className="shrink-0" />
      </button>

      {open && (
        <div className="absolute left-0 top-full z-20 mt-2 w-full min-w-[180px] max-h-[300px] overflow-y-auto rounded-xl border bg-white shadow-lg p-2">
          {mergedOptions.map((option) => (
            <button
              key={option.value}
              onClick={() => {
                onChange(option.value);
                setOpen(false);
              }}
              className={`w-full text-left whitespace-nowrap rounded-md px-3 py-2 text-sm
            ${value === option.value ? "bg-primary/10 text-primary" : "hover:bg-gray-100"}`}
            >
              {option.label}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}
