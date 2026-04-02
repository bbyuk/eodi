"use client";

import { useEffect } from "react";
import { Check, Clock3, LoaderCircle, Search, Sparkles, X } from "lucide-react";
import SearchField from "@/components/ui/SearchField";

function SelectionItemCard({ item, active, onSelect }) {
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

        <div
          className={`flex h-8 w-8 shrink-0 items-center justify-center rounded-full border ${
            active
              ? "border-[var(--picker-item-badge-selected-border)] bg-[var(--picker-item-badge-selected-bg)] text-[var(--picker-item-badge-selected-icon)]"
              : "border-[var(--picker-item-badge-border)] bg-[var(--picker-item-badge-bg)] text-[var(--picker-item-badge-icon)]"
          }`}
        >
          {active ? <Check className="h-4 w-4" /> : <Search className="h-4 w-4" />}
        </div>
      </div>
    </button>
  );
}

function SectionHeader({ icon: Icon, label, count }) {
  return (
    <div className="flex items-center justify-between gap-3 px-1">
      <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
        {Icon ? <Icon className="h-4 w-4 text-slate-400" /> : null}
        <span>{label}</span>
      </div>
      {typeof count === "number" ? <span className="text-xs font-medium text-slate-500">{count}개</span> : null}
    </div>
  );
}

export default function SelectionSearchSheet({
  open = false,
  title = "",
  description = "",
  searchValue = "",
  onSearchChange,
  searchPlaceholder = "",
  searchLabel = "검색",
  recentTitle = "최근 선택",
  recentItems = [],
  recommendedTitle = "추천 항목",
  recommendedItems = [],
  searchResults = [],
  isSearching = false,
  emptyTitle = "검색 결과가 없어요",
  emptyDescription = "다른 검색어로 다시 찾아보세요.",
  selectedKey,
  onSelect,
  onClose,
  closeLabel = "닫기",
}) {
  useEffect(() => {
    if (!open) {
      return undefined;
    }

    const handleEscape = (event) => {
      if (event.key === "Escape") {
        onClose?.();
      }
    };

    document.body.style.overflow = "hidden";
    window.addEventListener("keydown", handleEscape);

    return () => {
      document.body.style.overflow = "";
      window.removeEventListener("keydown", handleEscape);
    };
  }, [open, onClose]);

  if (!open) {
    return null;
  }

  const hasSearchValue = searchValue.trim().length > 0;
  const showDefaultSections = !hasSearchValue;
  const showEmptyState = hasSearchValue && !isSearching && searchResults.length === 0;

  return (
    <div className="fixed inset-0 z-[70]">
      <button
        type="button"
        aria-label={closeLabel}
        onClick={onClose}
        className="absolute inset-0 bg-slate-950/38 backdrop-blur-[2px]"
      />

      <div className="absolute inset-x-0 bottom-0 max-h-[85vh] rounded-t-[2rem] bg-white px-5 pb-8 pt-4 shadow-[0_-24px_80px_rgba(15,23,42,0.22)]">
        <div className="mx-auto mb-4 h-1.5 w-12 rounded-full bg-slate-200" />

        <div className="flex items-start justify-between gap-4">
          <div className="space-y-1">
            <h3 className="text-lg font-semibold text-slate-950">{title}</h3>
            {description ? <p className="text-sm leading-6 text-slate-600">{description}</p> : null}
          </div>
          <button
            type="button"
            onClick={onClose}
            aria-label={closeLabel}
            className="rounded-full p-2 text-slate-400 transition hover:bg-slate-100 hover:text-slate-700"
          >
            <X className="h-4 w-4" />
          </button>
        </div>

        <div className="mt-5 max-h-[calc(85vh-6.5rem)] space-y-5 overflow-y-auto pb-[calc(env(safe-area-inset-bottom)+0.5rem)]">
          <div className="space-y-2">
            <p className="text-sm font-semibold text-slate-900">{searchLabel}</p>
            <SearchField
              value={searchValue}
              onChange={onSearchChange}
              placeholder={searchPlaceholder}
            />
          </div>

          {isSearching ? (
            <section className="space-y-3 rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
              <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
                <LoaderCircle className="h-4 w-4 animate-spin text-slate-500" />
                검색 중
              </div>
              <div className="space-y-2">
                {[0, 1, 2].map((item) => (
                  <div key={item} className="rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4">
                    <div className="h-4 w-24 animate-pulse rounded bg-slate-200" />
                    <div className="mt-3 h-3 w-2/3 animate-pulse rounded bg-slate-100" />
                  </div>
                ))}
              </div>
            </section>
          ) : null}

          {showDefaultSections && recentItems.length > 0 ? (
            <section className="space-y-3">
              <SectionHeader icon={Clock3} label={recentTitle} />
              <div className="space-y-2">
                {recentItems.map((item) => (
                  <SelectionItemCard
                    key={item.key}
                    item={item}
                    active={item.key === selectedKey}
                    onSelect={onSelect}
                  />
                ))}
              </div>
            </section>
          ) : null}

          {showDefaultSections && recommendedItems.length > 0 ? (
            <section className="space-y-3">
              <SectionHeader icon={Sparkles} label={recommendedTitle} />
              <div className="space-y-2">
                {recommendedItems.map((item) => (
                  <SelectionItemCard
                    key={item.key}
                    item={item}
                    active={item.key === selectedKey}
                    onSelect={onSelect}
                  />
                ))}
              </div>
            </section>
          ) : null}

          {hasSearchValue && !isSearching && searchResults.length > 0 ? (
            <section className="space-y-3">
              <SectionHeader label="검색 결과" count={searchResults.length} />
              <div className="space-y-2">
                {searchResults.map((item) => (
                  <SelectionItemCard
                    key={item.key}
                    item={item}
                    active={item.key === selectedKey}
                    onSelect={onSelect}
                  />
                ))}
              </div>
            </section>
          ) : null}

          {showEmptyState ? (
            <section className="rounded-[1.5rem] border border-dashed border-slate-200 bg-slate-50 px-4 py-5">
              <p className="text-sm font-semibold text-slate-900">{emptyTitle}</p>
              <p className="mt-1 text-sm leading-6 text-slate-500">{emptyDescription}</p>
            </section>
          ) : null}
        </div>
      </div>
    </div>
  );
}
