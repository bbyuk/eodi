"use client";

import { Search } from "lucide-react";

export default function SearchField({
  value,
  onChange,
  placeholder,
  disabled = false,
  readOnly = false,
  onClick,
  className = "",
  trailing,
}) {
  return (
    <div
      className={[
        "flex min-h-14 items-center gap-3 rounded-[1.25rem] border border-slate-200 bg-white px-4",
        disabled ? "bg-slate-50 text-slate-400" : "text-slate-700",
        className,
      ]
        .filter(Boolean)
        .join(" ")}
    >
      <Search className="h-4 w-4 shrink-0 text-slate-400" />
      <input
        type="text"
        value={value}
        onChange={(e) => onChange?.(e.target.value)}
        placeholder={placeholder}
        disabled={disabled}
        readOnly={readOnly}
        onClick={onClick}
        className="w-full bg-transparent text-sm font-medium text-slate-900 placeholder:text-slate-400 focus:outline-none disabled:cursor-not-allowed"
      />
      {trailing}
    </div>
  );
}

