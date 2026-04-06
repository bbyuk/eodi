"use client";

import { Star } from "lucide-react";

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
  minLabel = "아쉬움",
  maxLabel = "좋음",
  scoreLabels = DEFAULT_SCORE_LABELS,
}) {
  return (
    <div className="space-y-3">
      <div className="flex items-center gap-2 rounded-[1.25rem] border border-slate-200 bg-white px-3 py-3">
        {Array.from({ length: max }, (_, index) => {
          const score = index + 1;
          const active = Number(value) >= score;

          return (
            <button
              key={score}
              type="button"
              onClick={() => onChange(score)}
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

      <div className="flex items-center justify-between gap-3 px-1">
        <span className="text-xs font-medium text-slate-500">{minLabel}</span>
        <span className="text-sm font-semibold text-slate-900">
          {value ? `${value}점 · ${scoreLabels[value] ?? ""}` : "점수를 선택하세요"}
        </span>
        <span className="text-xs font-medium text-slate-500">{maxLabel}</span>
      </div>
    </div>
  );
}
