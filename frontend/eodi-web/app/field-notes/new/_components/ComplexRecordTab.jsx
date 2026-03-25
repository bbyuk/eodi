"use client";

import SearchField from "@/components/ui/input/SearchField";
import AutocompleteField from "@/components/ui/input/AutocompleteField";

export default function ComplexRecordTab({
  complexQuery,
  onChangeComplexQuery,
  complexSuggestions,
  onSelectComplexSuggestion,
  selectedComplex,
  selectedRegionLabel,
  floor,
  onChangeFloor,
  askingPrice,
  onChangeAskingPrice,
  complexMemo,
  onChangeComplexMemo,
}) {
  return (
    <div className="space-y-6">
      <div className="space-y-3">
        <label className="text-sm font-semibold text-slate-900">단지 검색</label>
        <AutocompleteField
          value={complexQuery}
          onChange={onChangeComplexQuery}
          placeholder="단지명을 검색해보세요"
          suggestions={complexSuggestions}
          onSelectSuggestion={onSelectComplexSuggestion}
        />
      </div>

      {selectedComplex ? (
        <>
          <div className="space-y-3">
            <label className="text-sm font-semibold text-slate-900">지역</label>
            <SearchField
              value={selectedRegionLabel ?? ""}
              placeholder="자동으로 표시됩니다"
              readOnly
              disabled
            />
          </div>

          <div className="rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
            <div className="space-y-1">
              <p className="text-sm font-semibold text-slate-900">단지 기록</p>
              <p className="text-sm text-slate-600">{selectedComplex.name}</p>
            </div>

            <div className="mt-4 grid gap-4 sm:grid-cols-2">
              <div className="space-y-3">
                <label className="text-sm font-semibold text-slate-900">층수</label>
                <input
                  type="text"
                  value={floor}
                  onChange={(event) => onChangeFloor(event.target.value)}
                  placeholder="예: 12층"
                  className="min-h-14 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
                />
              </div>

              <div className="space-y-3">
                <label className="text-sm font-semibold text-slate-900">호가</label>
                <input
                  type="text"
                  value={askingPrice}
                  onChange={(event) => onChangeAskingPrice(event.target.value)}
                  placeholder="예: 8억 5,000"
                  className="min-h-14 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
                />
              </div>
            </div>

            <div className="mt-4 space-y-3">
              <label className="text-sm font-semibold text-slate-900">메모</label>
              <textarea
                value={complexMemo}
                onChange={(event) => onChangeComplexMemo(event.target.value)}
                placeholder="기억해둘 내용을 적어보세요"
                className="min-h-32 w-full rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4 text-sm text-slate-900 placeholder:text-slate-400 focus:outline-none"
              />
            </div>
          </div>
        </>
      ) : null}
    </div>
  );
}

