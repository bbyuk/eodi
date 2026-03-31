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
                  ? "border-slate-950 bg-slate-950 text-white"
                  : "border-slate-200 bg-white text-slate-700 hover:border-slate-300 hover:bg-slate-50"
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

const NOISE_OPTIONS = [
  { label: "조용함", value: "LOW" },
  { label: "보통", value: "NORMAL" },
  { label: "시끄러움", value: "HIGH" },
];

export default function BasicStatusFields({ form, onChangeField }) {
  return (
    <>
      <OptionField
        label="관리 상태"
        value={form.managementStatus}
        options={STATUS_OPTIONS}
        onChange={(value) => onChangeField("managementStatus", value)}
      />
      <OptionField
        label="소음"
        value={form.noiseLevel}
        options={NOISE_OPTIONS}
        onChange={(value) => onChangeField("noiseLevel", value)}
      />
    </>
  );
}
