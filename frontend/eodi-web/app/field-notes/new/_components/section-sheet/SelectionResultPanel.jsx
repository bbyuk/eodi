import { LoaderCircle } from "lucide-react";
import SelectionSectionHeader from "@/app/field-notes/new/_components/section-sheet/SelectionSectionHeader";

export default function SelectionResultPanel({
  label = "검색 결과",
  count,
  showIdlePanel,
  showLoadingSkeleton,
  showSearchResults,
  showEmptyState,
  showLoadingOverlay,
  results = [],
  emptyTitle,
  emptyDescription,
  idleMessage = "검색하면 결과가 여기에 표시돼요",
  loadingMessage = "검색 결과를 업데이트하는 중",
  minHeightClassName = "min-h-[14rem]",
  renderItem,
}) {
  return (
    <section className="min-h-[18rem] rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
      <SelectionSectionHeader
        label={label}
        count={showSearchResults && !showLoadingOverlay ? count : undefined}
      />

      <div className={`relative mt-3 ${minHeightClassName}`}>
        {showIdlePanel ? (
          <div
            className={`flex ${minHeightClassName} items-center justify-center rounded-[1.25rem] border border-dashed border-slate-200 bg-white px-4 py-5`}
          >
            <p className="text-sm font-medium text-slate-500">{idleMessage}</p>
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
          <div
            className={`space-y-2 transition-opacity ${
              showLoadingOverlay ? "opacity-60" : "opacity-100"
            }`}
          >
            {results.map((item, index) => renderItem(item, index))}
          </div>
        ) : null}

        {showEmptyState ? (
          <div
            className={`flex ${minHeightClassName} items-center rounded-[1.25rem] border border-dashed border-slate-200 bg-white px-4 py-5`}
          >
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
              {loadingMessage}
            </div>
          </div>
        ) : null}
      </div>
    </section>
  );
}
