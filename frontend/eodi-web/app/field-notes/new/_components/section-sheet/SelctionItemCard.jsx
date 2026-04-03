import { Check, Circle } from "lucide-react";

export default function SelectionItemCard({
  item,
  active = false,
  onSelect,
  showSelectionIndicator = true,
}) {
  return (
    <button
      type="button"
      onClick={() => onSelect?.(item)}
      className={`w-full rounded-[1.35rem] border px-4 py-4 text-left transition active:scale-[0.99] ${
        active
          ? "border-[var(--picker-item-selected-border)] bg-[var(--picker-item-selected-bg)]"
          : "border-[var(--picker-item-border)] bg-[var(--picker-item-bg)] hover:border-[var(--picker-item-hover-border)] hover:bg-[var(--picker-item-hover-bg)] active:bg-[var(--picker-item-active-bg)]"
      }`}
    >
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

        {showSelectionIndicator ? (
          <div
            className={`flex h-8 w-8 shrink-0 items-center justify-center rounded-full border ${
              active
                ? "border-[var(--picker-item-badge-selected-border)] bg-[var(--picker-item-badge-selected-bg)] text-[var(--picker-item-badge-selected-icon)]"
                : "border-[var(--picker-item-badge-border)] bg-[var(--picker-item-badge-bg)] text-[var(--picker-item-badge-icon)]"
            }`}
          >
            {active ? <Check className="h-4 w-4" /> : <Circle className="h-4 w-4" />}
          </div>
        ) : null}
      </div>
    </button>
  );
}
