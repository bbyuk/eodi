"use client";

import { useEffect, useState } from "react";
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
  const [settledSearchValue, setSettledSearchValue] = useState("");
  const [resolvedSearchValue, setResolvedSearchValue] = useState("");
  const [displayedResults, setDisplayedResults] = useState(searchResults);

  useEffect(() => {
    if (!open) {
      setSettledSearchValue("");
      setResolvedSearchValue("");
      setDisplayedResults([]);
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

  useEffect(() => {
    const trimmedValue = searchValue.trim();

    if (!trimmedValue) {
      setSettledSearchValue("");
      setResolvedSearchValue("");
      setDisplayedResults([]);
      return undefined;
    }

    const timer = window.setTimeout(() => {
      setSettledSearchValue(trimmedValue);
    }, 260);

    return () => {
      window.clearTimeout(timer);
    };
  }, [searchValue]);

  useEffect(() => {
    if (!settledSearchValue || isSearching) {
      return;
    }

    setDisplayedResults(searchResults);
    setResolvedSearchValue(settledSearchValue);
  }, [isSearching, searchResults, settledSearchValue]);

  if (!open) {
    return null;
  }

  const hasCommittedSearch = settledSearchValue.length > 0;
  const hasResolvedSearch = hasCommittedSearch && resolvedSearchValue === settledSearchValue && !isSearching;
  const hasVisibleResults = displayedResults.length > 0;
  const showIdlePanel = !hasCommittedSearch;
  const showLoadingState = hasCommittedSearch && isSearching;
  const showLoadingSkeleton = showLoadingState && !hasVisibleResults;
  const showLoadingOverlay = showLoadingState && hasVisibleResults;
  const showSearchResults = hasVisibleResults && (hasResolvedSearch || showLoadingOverlay);
  const showEmptyState = hasResolvedSearch && !hasVisibleResults;
  const showAuxiliarySections = !hasCommittedSearch || showLoadingState || showEmptyState;

  return (
    <div className="fixed inset-0 z-[70]">
      <button
        type="button"
        aria-label={closeLabel}
        onClick={onClose}
        className="absolute inset-0 bg-slate-950/38 backdrop-blur-[2px]"
      />

      <div className="absolute inset-x-0 bottom-0 flex h-[85vh] flex-col rounded-t-[2rem] bg-white px-5 pb-8 pt-4 shadow-[0_-24px_80px_rgba(15,23,42,0.22)]">
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

        <div className="mt-5 flex min-h-0 flex-1 flex-col">
          <div className="space-y-2">
            <p className="text-sm font-semibold text-slate-900">{searchLabel}</p>
            <SearchField
              value={searchValue}
              onChange={onSearchChange}
              placeholder={searchPlaceholder}
            />
          </div>

          <div
            className="mt-5 min-h-0 flex-1 overflow-y-auto pb-[calc(env(safe-area-inset-bottom)+0.5rem)]"
            style={{ scrollbarGutter: "stable" }}
          >
            <div className="space-y-5">
              <div
                className={`grid overflow-hidden transition-[grid-template-rows,opacity,margin] duration-200 ${
                  showAuxiliarySections ? "grid-rows-[1fr] opacity-100" : "grid-rows-[0fr] opacity-0"
                }`}
              >
                <div className="min-h-0 overflow-hidden">
                  <div className="space-y-5 pb-1">
                    {recentItems.length > 0 ? (
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

                    {recommendedItems.length > 0 ? (
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
                  </div>
                </div>
              </div>

              <section className="min-h-[18rem] rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
                <SectionHeader
                  label="검색 결과"
                  count={showSearchResults && !showLoadingOverlay ? displayedResults.length : undefined}
                />

                <div className="relative mt-3 min-h-[14rem]">
                  {showIdlePanel ? (
                    <div className="flex min-h-[14rem] items-center justify-center rounded-[1.25rem] border border-dashed border-slate-200 bg-white px-4 py-5">
                      <p className="text-sm font-medium text-slate-500">
                        검색하면 결과가 여기에 표시돼요
                      </p>
                    </div>
                  ) : null}

                  {showLoadingSkeleton ? (
                    <div className="space-y-2">
                      {[0, 1, 2].map((item) => (
                        <div
                          key={item}
                          className="rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4"
                        >
                          <div className="h-4 w-24 animate-pulse rounded bg-slate-200" />
                          <div className="mt-3 h-3 w-2/3 animate-pulse rounded bg-slate-100" />
                        </div>
                      ))}
                    </div>
                  ) : null}

                  {showSearchResults ? (
                    <div className={`space-y-2 transition-opacity ${showLoadingOverlay ? "opacity-60" : "opacity-100"}`}>
                      {displayedResults.map((item) => (
                        <SelectionItemCard
                          key={item.key}
                          item={item}
                          active={item.key === selectedKey}
                          onSelect={onSelect}
                        />
                      ))}
                    </div>
                  ) : null}

                  {showEmptyState ? (
                    <div className="flex min-h-[14rem] items-center rounded-[1.25rem] border border-dashed border-slate-200 bg-white px-4 py-5">
                      <div>
                        <p className="text-sm font-semibold text-slate-900">{emptyTitle}</p>
                        <p className="mt-1 text-sm leading-6 text-slate-500">{emptyDescription}</p>
                      </div>
                    </div>
                  ) : null}

                  {showLoadingOverlay ? (
                    <div className="pointer-events-none absolute inset-x-0 top-3 flex justify-center">
                      <div className="inline-flex items-center gap-2 rounded-full border border-slate-200 bg-white/95 px-3 py-2 text-xs font-semibold text-slate-600 shadow-sm backdrop-blur">
                        <LoaderCircle className="h-3.5 w-3.5 animate-spin" />
                        검색 결과를 업데이트하는 중
                      </div>
                    </div>
                  ) : null}
                </div>
              </section>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
