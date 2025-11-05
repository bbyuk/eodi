"use client";
import { useSearchStore } from "@/app/search/store/searchStore";

export default function SelectedRegionsCardContents({ close }) {
  const {
    toggleSellRegion,
    toggleLeaseRegion,
    selectedSellRegions = new Set(),
    selectedLeaseRegions = new Set(),
  } = useSearchStore();

  const hasSell = selectedSellRegions.size > 0;
  const hasLease = selectedLeaseRegions.size > 0;

  return (
    <div
      className="flex-1 overflow-y-auto px-3 py-3 space-y-3
                   scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent"
    >
      {/* 매매 섹션 */}
      {hasSell && (
        <section>
          <h5 className="text-xs font-semibold text-gray-500 mb-1">매매</h5>
          <div className="space-y-1.5">
            {Array.from(selectedSellRegions).map((region) => (
              <div
                key={`sell-${region.code}`}
                className="flex items-center justify-between px-2.5 py-1.5 rounded-md border border-border/50
                             bg-primary-bg/40 hover:bg-primary-bg/60 transition text-sm"
              >
                <span className="truncate">{region.name}</span>
                <button
                  onClick={() => toggleSellRegion(region)}
                  className="text-primary text-xs hover:underline"
                >
                  삭제
                </button>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* 임대차 섹션 */}
      {hasLease && (
        <section>
          <h5 className="text-xs font-semibold text-gray-500 mb-1">임대차</h5>
          <div className="space-y-1.5">
            {Array.from(selectedLeaseRegions).map((region) => (
              <div
                key={`lease-${region.code}`}
                className="flex items-center justify-between px-2.5 py-1.5 rounded-md border border-border/50
                             bg-primary-bg/40 hover:bg-primary-bg/60 transition text-sm"
              >
                <span className="truncate">{region.name}</span>
                <button
                  onClick={() => toggleLeaseRegion(region)}
                  className="text-primary text-xs hover:underline"
                >
                  삭제
                </button>
              </div>
            ))}
          </div>
        </section>
      )}

      {/* 아무 것도 없을 때 */}
      {!hasSell && !hasLease && (
        <p className="text-center text-xs text-gray-400 py-6">선택된 지역이 없습니다.</p>
      )}
    </div>
  );
}
