"use client";

import FieldTitle from "@/app/field-notes/new/_components/FieldTitle";

export default function OptionField({ value, options, onChange, title }) {
  return (
    <section className="space-y-3">
      <FieldTitle main={title.main} sub={title.sub} />
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
