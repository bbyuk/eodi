"use client";

import { useEffect, useState } from "react";
import { Clock3, Sparkles, X } from "lucide-react";
import SearchField from "@/components/ui/SearchField";
import SelectionAuxiliarySections from "@/app/field-notes/new/_components/section-sheet/SelectionAuxiliarySections";
import SelectionResultPanel from "@/app/field-notes/new/_components/section-sheet/SelectionResultPanel";
import SelectionItemCard from "@/app/field-notes/new/_components/section-sheet/SelctionItemCard";

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
  const hasResolvedSearch =
    hasCommittedSearch && resolvedSearchValue === settledSearchValue && !isSearching;
  const hasVisibleResults = displayedResults.length > 0;
  const showIdlePanel = !hasCommittedSearch;
  const showLoadingState = hasCommittedSearch && isSearching;
  const showLoadingSkeleton = showLoadingState && !hasVisibleResults;
  const showLoadingOverlay = showLoadingState && hasVisibleResults;
  const showSearchResults = hasVisibleResults && (hasResolvedSearch || showLoadingOverlay);
  const showEmptyState = hasResolvedSearch && !hasVisibleResults;
  const showAuxiliarySections = !hasCommittedSearch || showLoadingState || showEmptyState;

  const auxiliarySections = [
    {
      name: "recent",
      icon: Clock3,
      label: recentTitle,
      items: recentItems,
      selectedKey,
      onSelect,
    },
    {
      name: "recommended",
      icon: Sparkles,
      label: recommendedTitle,
      items: recommendedItems,
      selectedKey,
      onSelect,
    },
  ];

  return (
    <div className="fixed inset-0 z-[70]">
      <button
        type="button"
        aria-label={closeLabel}
        onClick={onClose}
        className="absolute inset-0 bg-slate-950/50 backdrop-blur-sm"
      />

      <div className="absolute inset-x-0 bottom-0 top-3 flex flex-col rounded-t-[2rem] bg-white px-5 pb-8 pt-4 shadow-[0_-24px_80px_rgba(15,23,42,0.22)]">
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
              <SelectionResultPanel
                label="검색 결과"
                count={displayedResults.length}
                showIdlePanel={showIdlePanel}
                showLoadingSkeleton={showLoadingSkeleton}
                showSearchResults={showSearchResults}
                showEmptyState={showEmptyState}
                showLoadingOverlay={showLoadingOverlay}
                results={displayedResults}
                emptyTitle={emptyTitle}
                emptyDescription={emptyDescription}
                renderItem={(item) => (
                  <SelectionItemCard
                    key={item.key}
                    item={item}
                    active={item.key === selectedKey}
                    onSelect={onSelect}
                  />
                )}
              />
              {/*<SelectionAuxiliarySections*/}
              {/*  open={showAuxiliarySections}*/}
              {/*  sections={auxiliarySections}*/}
              {/*/>*/}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
