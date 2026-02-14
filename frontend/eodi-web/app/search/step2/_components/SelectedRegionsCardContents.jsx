"use client";
import { useSearchStore } from "@/app/search/store/searchStore";

export default function SelectedRegionsCardContents({ close, table, selected, onSelect }) {
  return (
    <div
      className="flex-1 overflow-y-auto px-3 py-3 space-y-3
                   scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent"
    >
      {selected.size > 0 ? (
        <section>
          <div className="space-y-1.5">
            {Array.from(selected).map((region) => (
              <div
                key={`sell-${table[region].code}`}
                className="flex items-center justify-between px-2.5 py-1.5 rounded-md border border-border/50
                             bg-primary-bg/40 hover:bg-primary-bg/60 transition text-sm"
              >
                <span className="truncate">{table[region].name}</span>
                <button
                  onClick={() => onSelect(region)}
                  className="text-primary text-xs hover:underline"
                >
                  삭제
                </button>
              </div>
            ))}
          </div>
        </section>
      ) : (
        <p className="text-center text-xs text-gray-400 py-6">선택된 지역이 없습니다.</p>
      )}
    </div>
  );
}
