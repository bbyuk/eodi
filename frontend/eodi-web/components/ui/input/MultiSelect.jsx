"use client";

import { ChevronDown } from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function MultiSelect({
  options,
  value = [],
  onChange,
  placeholder = "선택하세요",
  width = "w-auto",
}) {
  const [open, setOpen] = useState(false);
  const containerRef = useRef(null);

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
    if (value.includes(val)) {
      onChange(value.filter((v) => v !== val));
    } else {
      onChange([...value, val]);
    }
  };

  const selectedLabels = options.filter((o) => value.includes(o.value)).map((o) => o.label);

  const label =
    selectedLabels.length === 0
      ? placeholder
      : selectedLabels.length <= 2
        ? selectedLabels.join(", ")
        : `${selectedLabels[0]} 외 ${selectedLabels.length - 1}개`;

  return (
    <div ref={containerRef} className={`relative inline-block ${width}`}>
      <button
        onClick={() => setOpen((v) => !v)}
        className="flex items-center gap-2 rounded-full border px-4 py-2 text-sm hover:bg-gray-50"
      >
        <span>{label}</span>
        <ChevronDown size={16} />
      </button>

      {open && (
        <div className="absolute left-0 top-full z-20 mt-2 w-[220px] max-h-[280px] overflow-y-auto rounded-xl border bg-white shadow-lg p-2 space-y-1">
          {options.map((option) => (
            <label
              key={option.value}
              className="flex items-center gap-2 px-2 py-2 rounded-md text-sm hover:bg-gray-100 cursor-pointer"
            >
              <input
                type="checkbox"
                checked={value.includes(option.value)}
                onChange={() => toggleValue(option.value)}
                className="accent-primary"
              />
              <span>{option.label}</span>
            </label>
          ))}
        </div>
      )}
    </div>
  );
}
