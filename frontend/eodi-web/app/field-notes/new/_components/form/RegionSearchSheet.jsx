"use client";

import { useEffect } from "react";
import { Check, Clock3, LoaderCircle, MapPin, Sparkles, X } from "lucide-react";
import SearchField from "@/components/ui/SearchField";

function RegionOptionCard({ option, selectedValue, onSelect }) {
  const active = option.value === selectedValue;

  return (
    <button
      type="button"
      onClick={() => onSelect?.(option.value)}
      className={`w-full rounded-[1.35rem] border px-4 py-4 text-left transition active:scale-[0.99] ${
        active
          ? "border-emerald-300 bg-emerald-50 text-emerald-950"
          : "border-slate-200 bg-white text-slate-800 hover:border-slate-300 hover:bg-slate-50"
      }`}
    >
      <div className="flex items-center justify-between gap-3">
        <div className="min-w-0">
          <p className="text-sm font-semibold">{option.label}</p>
        </div>
        <div
          className={`flex h-8 w-8 shrink-0 items-center justify-center rounded-full border ${
            active
              ? "border-emerald-200 bg-emerald-100 text-emerald-700"
              : "border-slate-200 bg-slate-50 text-slate-400"
          }`}
        >
          {active ? <Check className="h-4 w-4" /> : <MapPin className="h-4 w-4" />}
        </div>
      </div>
    </button>
  );
}

export default function RegionSearchSheet({
  open = false,
  title,
  description,
  searchQuery,
  searchResults,
  isSearching,
  recentOptions = [],
  recommendedOptions = [],
  selectedValue,
  onChangeSearchQuery,
  onSelect,
  onClose,
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

  const hasSearchQuery = searchQuery.trim().length > 0;
  const showDefaultSections = !hasSearchQuery;
  const showEmptyState = hasSearchQuery && !isSearching && searchResults.length === 0;

  return (
    <div className="fixed inset-0 z-[70]">
      <button
        type="button"
        aria-label="닫기"
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
            aria-label="닫기"
            className="rounded-full p-2 text-slate-400 transition hover:bg-slate-100 hover:text-slate-700"
          >
            <X className="h-4 w-4" />
          </button>
        </div>

        <div className="mt-5 max-h-[calc(85vh-6.5rem)] space-y-5 overflow-y-auto pb-[calc(env(safe-area-inset-bottom)+0.5rem)]">
          <div className="space-y-2">
            <p className="text-sm font-semibold text-slate-900">지역 검색</p>
            <SearchField
              value={searchQuery}
              onChange={onChangeSearchQuery}
              placeholder="지역명을 입력하세요"
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

          {showDefaultSections && recentOptions.length > 0 ? (
            <section className="space-y-3">
              <div className="flex items-center gap-2 px-1 text-sm font-semibold text-slate-900">
                <Clock3 className="h-4 w-4 text-slate-400" />
                최근 선택 지역
              </div>
              <div className="space-y-2">
                {recentOptions.map((option) => (
                  <RegionOptionCard
                    key={option.value}
                    option={option}
                    selectedValue={selectedValue}
                    onSelect={onSelect}
                  />
                ))}
              </div>
            </section>
          ) : null}

          {showDefaultSections ? (
            <section className="space-y-3">
              <div className="flex items-center gap-2 px-1 text-sm font-semibold text-slate-900">
                <Sparkles className="h-4 w-4 text-slate-400" />
                추천 지역
              </div>
              <div className="space-y-2">
                {recommendedOptions.map((option) => (
                  <RegionOptionCard
                    key={option.value}
                    option={option}
                    selectedValue={selectedValue}
                    onSelect={onSelect}
                  />
                ))}
              </div>
            </section>
          ) : null}

          {hasSearchQuery && !isSearching && searchResults.length > 0 ? (
            <section className="space-y-3">
              <div className="flex items-center justify-between px-1">
                <p className="text-sm font-semibold text-slate-900">검색 결과</p>
                <p className="text-xs font-medium text-slate-500">{searchResults.length}개</p>
              </div>
              <div className="space-y-2">
                {searchResults.map((option) => (
                  <RegionOptionCard
                    key={option.value}
                    option={option}
                    selectedValue={selectedValue}
                    onSelect={onSelect}
                  />
                ))}
              </div>
            </section>
          ) : null}

          {showEmptyState ? (
            <section className="rounded-[1.5rem] border border-dashed border-slate-200 bg-slate-50 px-4 py-5">
              <p className="text-sm font-semibold text-slate-900">검색 결과가 없어요</p>
              <p className="mt-1 text-sm leading-6 text-slate-500">
                시/구/동 이름 일부를 입력해서 다시 찾아보세요.
              </p>
            </section>
          ) : null}
        </div>
      </div>
    </div>
  );
}
