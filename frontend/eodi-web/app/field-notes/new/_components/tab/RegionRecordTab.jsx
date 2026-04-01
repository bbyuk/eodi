"use client";

import SearchField from "@/components/ui/SearchField";

export default function RegionRecordTab({
  selectedRegionLabel,
  onOpenRegionSheet,
  regionMemo,
  onChangeRegionMemo,
}) {
  return (
    <div className="space-y-6">
      <div className="space-y-3">
        <label className="text-sm font-semibold text-slate-900">지역 검색</label>
        <SearchField
          value={selectedRegionLabel ?? ""}
          placeholder="지역명을 검색해보세요"
          readOnly
          onClick={onOpenRegionSheet}
        />
      </div>

      {selectedRegionLabel ? (
        <div className="rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
          <div className="space-y-1">
            <p className="text-sm font-semibold text-slate-900">지역 메모 폼</p>
            <p className="text-sm text-slate-600">{selectedRegionLabel}</p>
          </div>

          <div className="mt-4 space-y-3">
            <label className="text-sm font-semibold text-slate-900">메모</label>
            <textarea
              value={regionMemo}
              onChange={(event) => onChangeRegionMemo(event.target.value)}
              placeholder="지역에 대해 남기고 싶은 내용을 적어보세요"
              className="min-h-32 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
            />
          </div>
        </div>
      ) : null}
    </div>
  );
}
