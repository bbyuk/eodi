import { X } from "lucide-react";

export default function SelectionPinnedItemCard({ item, onDeselect, clearLabel = "해제" }) {
  return (
    <div className="w-full rounded-[1.1rem] border border-[var(--picker-item-selected-border)] bg-[var(--picker-item-selected-bg)] px-4 py-4">
      <div className="flex items-start justify-between gap-3">
        <div className="min-w-0 flex-1">
          <p className="text-sm font-semibold text-[var(--picker-item-title)]">{item.title}</p>

          {item.subtitle ? (
            <p className="mt-1 text-sm leading-6 text-[var(--picker-item-subtitle)]">
              {item.subtitle}
            </p>
          ) : null}

          {item.meta ? (
            <p className="mt-2 text-xs font-medium text-[var(--picker-item-meta)]">{item.meta}</p>
          ) : null}
        </div>

        <button
          type="button"
          onClick={() => onDeselect?.(item)}
          aria-label={clearLabel}
          className="inline-flex h-8 shrink-0 items-center gap-1 rounded-full border border-slate-200 bg-white px-2.5 text-xs font-semibold text-slate-600 transition hover:border-slate-300 hover:bg-slate-50 hover:text-slate-900"
        >
          <X className="h-3.5 w-3.5" />
          {clearLabel}
        </button>
      </div>
    </div>
  );
}
