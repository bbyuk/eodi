"use client";

import { Check, Compass } from "lucide-react";

export default function CompassDirectionInput({ value, options, onChange }) {
  return (
    <div className="rounded-[1.5rem] border border-slate-200 bg-[linear-gradient(180deg,#ffffff_0%,#f8fafc_100%)] p-3 shadow-[0_12px_30px_rgba(15,23,42,0.04)]">
      <div className="grid grid-cols-3 grid-rows-3 gap-2">
        {options.map((option) => {
          const active = value === option.value;

          return (
            <button
              key={option.value}
              type="button"
              onClick={() => onChange(option.value)}
              aria-pressed={active}
              style={{ gridRow: option.row, gridColumn: option.col }}
              className={`relative flex min-h-14 items-center justify-center rounded-[1.1rem] border px-2 py-3 text-center text-sm font-semibold transition duration-200 active:scale-[0.98] ${
                active
                  ? "border-[var(--choice-chip-selected-border)] bg-[var(--picker-item-selected-bg)] text-[var(--choice-chip-selected-bg)] shadow-[0_14px_28px_rgba(71,85,105,0.14)]"
                  : "border-slate-200 bg-white text-slate-700 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
              }`}
            >
              <span
                className={`absolute inset-x-3 top-0 h-1 rounded-b-full transition ${
                  active ? "bg-[var(--choice-chip-selected-bg)]" : "bg-transparent"
                }`}
                aria-hidden="true"
              />
              <span>{option.label}</span>
              {active ? (
                <span className="absolute right-2 top-2 inline-flex h-5 w-5 items-center justify-center rounded-full bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)]">
                  <Check className="h-3 w-3" />
                </span>
              ) : null}
            </button>
          );
        })}

        <div className="flex items-center justify-center rounded-[1.25rem] border border-slate-200 bg-slate-50 text-slate-500 shadow-inner">
          <div className="flex flex-col items-center gap-1">
            <Compass className="h-5 w-5 text-[var(--choice-chip-selected-bg)]" />
            <span className="text-xs font-semibold tracking-[0.02em]">향</span>
          </div>
        </div>
      </div>
    </div>
  );
}
