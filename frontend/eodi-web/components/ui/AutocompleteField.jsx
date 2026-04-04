"use client";

import { Search } from "lucide-react";

export default function AutocompleteField({
  value,
  onChange,
  placeholder,
  disabled = false,
  suggestions = [],
  onSelectSuggestion,
  helperText,
}) {
  const shouldShowSuggestions = !disabled && suggestions.length > 0;

  return (
    <div className="space-y-3">
      <div
        className={`flex min-h-14 items-center gap-3 rounded-[1.25rem] border border-slate-200 px-4 ${
          disabled ? "bg-slate-50 text-slate-400" : "bg-white text-slate-700"
        }`}
      >
        <Search className="h-4 w-4 shrink-0 text-slate-400" />
        <input
          type="text"
          value={value}
          onChange={(e) => onChange?.(e.target.value)}
          placeholder={placeholder}
          disabled={disabled}
          className="w-full bg-transparent text-sm font-medium text-slate-900 placeholder:text-slate-400 focus:outline-none disabled:cursor-not-allowed"
        />
      </div>

      {helperText ? <p className="text-xs font-medium text-slate-500">{helperText}</p> : null}

      {shouldShowSuggestions ? (
        <div className="space-y-2">
          {suggestions.map((suggestion) => (
            <button
              key={suggestion.id}
              type="button"
              onClick={() => onSelectSuggestion?.(suggestion)}
              className="flex w-full items-center justify-between rounded-2xl border border-slate-200 bg-slate-50 px-4 py-3 text-left transition hover:border-slate-300 hover:bg-white"
            >
              <div>
                <p className="text-sm font-semibold text-slate-900">{suggestion.name}</p>
                {suggestion.meta ? (
                  <p className="mt-1 text-xs font-medium text-slate-500">{suggestion.meta}</p>
                ) : null}
              </div>
              <span className="text-xs font-semibold text-slate-400">선택</span>
            </button>
          ))}
        </div>
      ) : null}
    </div>
  );
}

