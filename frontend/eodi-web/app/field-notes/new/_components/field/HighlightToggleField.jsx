"use client";

import { Check } from "lucide-react";
import Field from "@/app/field-notes/new/_components/field/Field";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

export default function HighlightToggleField({ title, value, onChange }) {
  return (
    <Field title={title}>
      <button
        type="button"
        onClick={() => onChange(!value)}
        aria-pressed={value}
        className={`flex min-h-12 w-full items-center gap-3 border px-4 text-left text-sm font-semibold transition ${FIELD_NOTE_INPUT_RADIUS_CLASS} ${
          value
            ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)] shadow-[var(--choice-chip-selected-shadow)]"
            : "border-slate-200 bg-white text-slate-700 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
        }`}
      >
        <span
          className={`inline-flex h-5 w-5 shrink-0 items-center justify-center rounded-full border ${
            value
              ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)]"
              : "border-slate-300 bg-white"
          }`}
        >
          {value ? <Check className="h-3 w-3" /> : null}
        </span>
        {title.main}
      </button>
    </Field>
  );
}
