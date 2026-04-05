"use client";

import InputButtonField from "@/app/field-notes/new/_components/field/InputButtonField";
import FieldTitle from "@/app/field-notes/new/_components/field/FieldTitle";

export default function RegionRecordTab({
  selectedRegionLabel,
  onOpenRegionSheet,
  regionMemo,
  onChangeRegionMemo,
}) {
  return (
    <div className="space-y-6">
      {/* 지역 선택 필드 */}
      <InputButtonField
        title={{ main: "지역 선택", sub: "지역명을 검색해 선택하세요" }}
        value={selectedRegionLabel ?? ""}
        placeholder="지역명을 검색해 선택하세요"
        onClick={onOpenRegionSheet}
      />

      {selectedRegionLabel ? (
        <div className="rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
          <FieldTitle main={"지역 메모"} sub={selectedRegionLabel} />

          <div className="mt-4 space-y-3">
            <FieldTitle main={"메모"} />
            <textarea
              value={regionMemo}
              onChange={(event) => onChangeRegionMemo(event.target.value)}
              placeholder="이 지역에 대한 기록을 남겨보세요"
              className="min-h-32 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
            />
          </div>
        </div>
      ) : null}
    </div>
  );
}
