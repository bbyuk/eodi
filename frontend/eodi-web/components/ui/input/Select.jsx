"use client";

import { ChevronDown } from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function Select({ options, value, onChange, placeholder = "선택하세요" }) {
  const [open, setOpen] = useState(false);
  const containerRef = useRef(null);

  const selected = options.find((o) => o.value === value);

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
    <div ref={containerRef} className="relative inline-block">
      <button
        onClick={() => setOpen((v) => !v)}
        className="flex items-center gap-2 rounded-full border px-4 py-2 text-sm hover:bg-gray-50"
      >
        <span>{selected ? selected.label : placeholder}</span>
        <ChevronDown size={16} />
      </button>

      {open && (
        <div className="absolute left-0 top-full z-20 mt-2 w-[220px] max-h-[300px] overflow-y-auto rounded-xl border bg-white shadow-lg p-2">
          {options.map((option) => (
            <button
              key={option.value}
              onClick={() => {
                onChange(option.value);
                setOpen(false);
              }}
              className={`w-full text-left rounded-md px-3 py-2 text-sm
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
