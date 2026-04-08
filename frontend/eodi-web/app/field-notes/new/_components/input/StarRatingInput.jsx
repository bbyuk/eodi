"use client";

import { Star } from "lucide-react";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

const DEFAULT_SCORE_LABELS = {
  1: "아쉬움",
  2: "조금 아쉬움",
  3: "보통",
  4: "좋음",
  5: "매우 좋음",
};

export default function StarRatingInput({
  value,
  onChange,
  max = 5,
  minLabel,
  maxLabel,
  scoreLabels = DEFAULT_SCORE_LABELS,
}) {
  const resolvedMinLabel = minLabel ?? scoreLabels?.[1] ?? "아쉬움";
  const resolvedMaxLabel = maxLabel ?? scoreLabels?.[max] ?? "좋음";

  return (
    <div className="space-y-3">
      <div
        className={`flex items-center gap-2 border border-slate-200 bg-white px-3 py-3 ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
      >
        {Array.from({ length: max }, (_, index) => {
          const score = index + 1;
          const active = Number(value) >= score;

          return (
            <button
              key={score}
              type="button"
              onClick={() => onChange(value === score ? null : score)}
              aria-pressed={value === score}
              aria-label={`${score}점 선택`}
              className={`flex h-11 flex-1 items-center justify-center rounded-full border transition ${
                active
                  ? "border-[var(--picker-item-selected-border)] bg-[var(--picker-item-selected-bg)] text-[var(--choice-chip-selected-bg)] shadow-[var(--choice-chip-selected-shadow)]"
                  : "border-slate-200 bg-white text-slate-300 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
              }`}
            >
              <Star className={`h-5 w-5 ${active ? "fill-current" : ""}`} />
            </button>
          );
        })}
      </div>

      <div className="grid min-h-5 grid-cols-[1fr_auto_1fr] items-center gap-3 px-1">
        <span className="text-xs font-medium text-slate-500">{resolvedMinLabel}</span>
        <span className="min-w-16 text-center text-sm font-semibold leading-5 text-slate-900">
          {value ? (scoreLabels[value] ?? "") : "\u00a0"}
        </span>
        <span className="text-right text-xs font-medium text-slate-500">{resolvedMaxLabel}</span>
      </div>
    </div>
  );
}
