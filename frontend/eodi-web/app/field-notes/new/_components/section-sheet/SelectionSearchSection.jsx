"use client";

import { useEffect, useMemo, useState } from "react";
import { Clock3, Sparkles, X } from "lucide-react";
import SearchField from "@/components/ui/SearchField";
import SelectionAuxiliarySections from "@/app/field-notes/new/_components/section-sheet/SelectionAuxiliarySections";
import SelectionResultPanel from "@/app/field-notes/new/_components/section-sheet/SelectionResultPanel";
import SelectionItemCard from "@/app/field-notes/new/_components/section-sheet/SelctionItemCard";
import SelectionPinnedItemCard from "@/app/field-notes/new/_components/section-sheet/SelectionPinnedItemCard";
import SelectionSectionHeader from "@/app/field-notes/new/_components/section-sheet/SelectionSectionHeader";

function normalizeSheetItem(item) {
  if (!item) {
    return null;
  }

  return {
    key: item.key ?? item.id ?? item.value ?? "",
    title: item.title ?? item.name ?? item.label ?? "",
    subtitle: item.subtitle ?? item.address ?? "",
    meta: item.meta ?? "",
    raw: item.raw ?? item,
  };
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
  selectedItem = null,
  selectedSectionLabel = "현재 선택",
  selectedEmptyMessage = "선택한 항목이 여기에 표시돼요.",
  onSelect,
  onDeselect,
  onClose,
  closeLabel = "닫기",
  deselectLabel = "해제",
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

  const resolvedSelectedItem = useMemo(() => {
    if (selectedItem) {
      return normalizeSheetItem(selectedItem);
    }

    if (selectedKey == null) {
      return null;
    }

    const allItems = [...searchResults, ...recentItems, ...recommendedItems];
    const matchedItem = allItems.find((item) => item.key === selectedKey);

    return normalizeSheetItem(matchedItem);
  }, [selectedItem, selectedKey, searchResults, recentItems, recommendedItems]);

  const filteredResults = useMemo(() => {
    if (selectedKey == null) {
      return displayedResults;
    }

    return displayedResults.filter((item) => item.key !== selectedKey);
  }, [displayedResults, selectedKey]);

  const auxiliarySections = useMemo(() => {
    const filterItems = (items = []) => {
      if (selectedKey == null) {
        return items;
      }

      return items.filter((item) => item.key !== selectedKey);
    };

    return [
      {
        name: "recent",
        icon: Clock3,
        label: recentTitle,
        items: filterItems(recentItems),
        selectedKey,
        onSelect,
      },
      {
        name: "recommended",
        icon: Sparkles,
        label: recommendedTitle,
        items: filterItems(recommendedItems),
        selectedKey,
        onSelect,
      },
    ];
  }, [recentTitle, recentItems, recommendedTitle, recommendedItems, selectedKey, onSelect]);

  if (!open) {
    return null;
  }

  const hasCommittedSearch = settledSearchValue.length > 0;
  const hasResolvedSearch =
    hasCommittedSearch && resolvedSearchValue === settledSearchValue && !isSearching;
  const hasVisibleResults = filteredResults.length > 0;
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

          <div className="mt-4 rounded-[1.5rem] border border-slate-200 bg-slate-50 p-3.5">
            <SelectionSectionHeader label={selectedSectionLabel} />

            <div className="mt-3 min-h-[5.75rem]">
              {resolvedSelectedItem ? (
                <SelectionPinnedItemCard
                  item={resolvedSelectedItem}
                  onDeselect={onDeselect}
                  clearLabel={deselectLabel}
                />
              ) : (
                <div className="flex min-h-[5.75rem] items-center justify-center rounded-[1.1rem] border border-dashed border-slate-200 bg-white px-4 py-4 text-center">
                  <p className="text-sm font-medium text-slate-500">{selectedEmptyMessage}</p>
                </div>
              )}
            </div>
          </div>

          <div
            className="mt-5 min-h-0 flex-1 overflow-y-auto pb-[calc(env(safe-area-inset-bottom)+0.5rem)]"
            style={{ scrollbarGutter: "stable" }}
          >
            <div className="space-y-5">
              <SelectionResultPanel
                label="검색 결과"
                count={filteredResults.length}
                showIdlePanel={showIdlePanel}
                showLoadingSkeleton={showLoadingSkeleton}
                showSearchResults={showSearchResults}
                showEmptyState={showEmptyState}
                showLoadingOverlay={showLoadingOverlay}
                results={filteredResults}
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
