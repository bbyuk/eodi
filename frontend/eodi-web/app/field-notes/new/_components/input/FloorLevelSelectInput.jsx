"use client";

import { Check, PencilLine } from "lucide-react";

function FloorLevelIcon({ active, floorValue }) {
  const isLow = floorValue === "LOW";
  const isMid = floorValue === "MID";
  const isHigh = floorValue === "HIGH";
  const levelClassName = isLow
    ? "bottom-[0.1rem]"
    : isMid
      ? "bottom-[0.95rem]"
      : isHigh
        ? "bottom-[1.8rem]"
        : "bottom-[2.65rem]";

  return (
    <div className="relative flex h-10 w-10 items-end justify-center">
      <div className="relative h-9 w-7">
        <span
          className={`absolute inset-y-0 left-0 w-px transition ${
            active ? "bg-slate-500" : "bg-slate-400"
          }`}
        />
        <span
          className={`absolute inset-y-0 right-0 w-px transition ${
            active ? "bg-slate-500" : "bg-slate-400"
          }`}
        />
        <span
          className={`absolute inset-x-0 bottom-0 h-px transition ${
            active ? "bg-slate-500" : "bg-slate-400"
          }`}
        />

        {[0.9, 1.8, 2.7].map((offset) => (
          <span
            key={offset}
            style={{ bottom: `${offset}rem` }}
            className={`absolute inset-x-0 h-px transition ${
              active ? "bg-slate-300" : "bg-slate-200"
            }`}
          />
        ))}

        <span
          className={`absolute left-1/2 h-[0.4rem] w-5 -translate-x-1/2 transition ${levelClassName} ${
            active
              ? "bg-[var(--choice-chip-selected-bg)] shadow-[0_2px_8px_rgba(71,85,105,0.14)]"
              : "bg-slate-500"
          }`}
        />
      </div>
    </div>
  );
}

export default function FloorLevelSelectInput({
  value,
  options,
  onChange,
  directOption,
  className = "grid grid-cols-2 gap-2.5",
}) {
  return (
    <div className="space-y-3">
      <div className={className}>
        {options.map((option) => {
          const active = value === option.value;

          return (
            <button
              key={option.value}
              type="button"
              onClick={() => onChange(option.value)}
              aria-pressed={active}
              className={`group relative overflow-hidden rounded-[1.1rem] border bg-white px-3 py-2.5 text-left transition duration-200 active:scale-[0.985] ${
                active
                  ? "border-[var(--choice-chip-selected-border)] bg-[var(--picker-item-selected-bg)] shadow-[0_16px_32px_rgba(71,85,105,0.14)]"
                  : "border-slate-200 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
              }`}
            >
              <div
                className={`absolute inset-x-0 top-0 h-1.5 transition ${
                  active ? "bg-[var(--choice-chip-selected-bg)]" : "bg-slate-100"
                }`}
              />

              <div className="flex h-full min-h-[5.4rem] flex-col">
                <div className="flex items-start justify-between gap-2.5">
                  <p className="min-h-5 pr-2 text-sm font-semibold leading-5 text-slate-900">
                    {option.label}
                  </p>

                  <span
                    className={`inline-flex h-6 w-6 shrink-0 items-center justify-center rounded-full border transition ${
                      active
                        ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)] shadow-[var(--choice-chip-selected-shadow)]"
                        : "border-slate-200 bg-slate-50 text-slate-300"
                    }`}
                    aria-hidden="true"
                  >
                    <Check className="h-3.25 w-3.25" strokeWidth={2.4} />
                  </span>
                </div>

                <div className="mt-1.5 flex flex-1 items-end justify-center" aria-hidden="true">
                  <div className="flex h-10 w-10 items-end justify-center">
                    <FloorLevelIcon active={active} floorValue={option.value} />
                  </div>
                </div>
              </div>
            </button>
          );
        })}
      </div>

      {directOption ? (
        <button
          type="button"
          onClick={() => onChange(directOption.value)}
          aria-pressed={value === directOption.value}
          className={`flex min-h-10 w-full items-center justify-center gap-2 rounded-[0.95rem] border px-4 py-2 text-sm font-semibold transition ${
            value === directOption.value
              ? "border-[var(--choice-chip-selected-border)] bg-[var(--picker-item-selected-bg)] text-[var(--choice-chip-selected-bg)] shadow-[var(--choice-chip-selected-shadow)]"
              : "border-dashed border-slate-300 bg-white text-slate-600 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
          }`}
        >
          <PencilLine className="h-4 w-4" />
          {directOption.label}
        </button>
      ) : null}
    </div>
  );
}
