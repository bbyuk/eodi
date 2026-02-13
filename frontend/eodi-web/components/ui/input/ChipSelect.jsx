"use client";

import { ChevronDown, X } from "lucide-react";
import { useEffect, useRef, useState } from "react";

export default function ChipSelect({
  options = [],
  selected = new Set(),
  onSelect,
  placeholder = "선택",
  width = "w-[120px]",
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

  return (
    <div ref={containerRef} className={`relative ${width}`}>
      {/* Select 트리거 */}
      <button
        type="button"
        onClick={() => setOpen((v) => !v)}
        className="w-full h-10 flex items-center justify-between rounded-full border px-4 text-sm bg-white hover:bg-gray-50"
      >
        <span className="truncate">
          {/*{selected.length > 0 ? `${selected.length}개 선택` : placeholder}*/}
          {placeholder}
        </span>
        <ChevronDown size={16} className="shrink-0" />
      </button>

      {/* 드롭다운 */}
      {open && (
        <div className="absolute left-0 top-full z-50 mt-2 w-full rounded-xl border bg-white shadow-xl p-2 max-h-[240px] overflow-y-auto space-y-1">
          {options.map((option) => {
            const active = selected.has(option.value);

            return (
              <button
                key={option.value}
                type="button"
                onClick={() => onSelect(option.value)}
                className={`w-full flex items-center justify-between px-3 py-2 rounded-md text-sm transition
                  ${active ? "bg-primary/10 text-primary" : "hover:bg-gray-100 text-gray-700"}
                `}
              >
                <span>{option.label}</span>

                {active && <X size={14} />}
              </button>
            );
          })}
        </div>
      )}
    </div>
  );
}
