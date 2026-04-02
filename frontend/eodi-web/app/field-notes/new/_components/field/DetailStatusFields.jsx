"use client";

function OptionField({ label, value, options, onChange }) {
  return (
    <section className="space-y-3">
      <label className="text-sm font-semibold text-slate-900">{label}</label>
      <div className="grid grid-cols-3 gap-2">
        {options.map((option) => {
          const active = value === option.value;

          return (
            <button
              key={option.value}
              type="button"
              onClick={() => onChange(option.value)}
              className={`rounded-full border px-3 py-3 text-sm font-semibold transition ${
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
    </section>
  );
}

const STATUS_OPTIONS = [
  { label: "좋음", value: "GOOD" },
  { label: "보통", value: "NORMAL" },
  { label: "아쉬움", value: "BAD" },
];

export default function DetailStatusFields({ form, onChangeField }) {
  return (
    <>
      <OptionField
        label="주차"
        value={form.parkingStatus}
        options={STATUS_OPTIONS}
        onChange={(value) => onChangeField("parkingStatus", value)}
      />
      <OptionField
        label="채광"
        value={form.sunlightStatus}
        options={STATUS_OPTIONS}
        onChange={(value) => onChangeField("sunlightStatus", value)}
      />
      <OptionField
        label="상권"
        value={form.commercialAreaStatus}
        options={STATUS_OPTIONS}
        onChange={(value) => onChangeField("commercialAreaStatus", value)}
      />
    </>
  );
}
