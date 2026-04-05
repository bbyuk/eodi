"use client";

export default function ChoiceChipInput({
  value,
  options,
  onChange,
  className = "grid grid-cols-2 gap-2 sm:grid-cols-3",
}) {
  return (
    <div className={className}>
      {options.map((option) => {
        const active = value === option.value;

        return (
          <button
            key={option.value}
            type="button"
            onClick={() => onChange(option.value)}
            className={`rounded-full border px-4 py-3 text-sm font-semibold transition ${
              active
                ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)] shadow-[var(--choice-chip-selected-shadow)]"
                : "border-slate-200 bg-white text-slate-700 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
            }`}
          >
            {option.label}
          </button>
        );
      })}
    </div>
  );
}
