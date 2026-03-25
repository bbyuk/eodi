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
  { label: "적음", value: "LOW" },
  { label: "보통", value: "NORMAL" },
  { label: "큼", value: "HIGH" },
];

export default function DetailRecordFields({ form, onChangeField }) {
  return (
    <section className="rounded-[1.5rem] border border-slate-200 bg-white p-4 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
      <div className="space-y-1">
        <p className="text-sm font-semibold text-slate-900">상세 기록</p>
        <p className="text-xs font-medium text-slate-500">나중에 비교할 때 필요한 정보를 더 남길 수 있어요</p>
      </div>

      <div className="mt-5 space-y-5">
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
        <OptionField
          label="주차"
          value={form.parkingStatus}
          options={STATUS_OPTIONS}
          onChange={(value) => onChangeField("parkingStatus", value)}
        />
        <OptionField
          label="상권"
          value={form.commercialAreaStatus}
          options={STATUS_OPTIONS}
          onChange={(value) => onChangeField("commercialAreaStatus", value)}
        />

        <section className="space-y-3">
          <label className="text-sm font-semibold text-slate-900">본 부동산명</label>
          <input
            type="text"
            value={form.agencyName}
            onChange={(event) => onChangeField("agencyName", event.target.value)}
            placeholder="예: OO공인중개사"
            className="min-h-14 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
          />
        </section>
      </div>
    </section>
  );
}

