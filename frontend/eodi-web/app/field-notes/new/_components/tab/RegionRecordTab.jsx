"use client";

import { useState } from "react";
import ButtonInputField from "@/app/field-notes/new/_components/field/ButtonInputField";
import TextAreaField from "@/app/field-notes/new/_components/field/TextAreaField";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import FieldNoteSection from "@/app/field-notes/new/_components/section/FieldNoteSection";
import SelectionSearchSheet from "@/app/field-notes/new/_components/section-sheet/SelectionSearchSection";
import useRegionSelection from "@/app/field-notes/new/_hooks/useRegionSelection";

export default function RegionRecordTab() {
  const [regionMemo, setRegionMemo] = useState("");
  const {
    selectedRegionValue,
    selectedRegionOption,
    selectedRegionSheetItem,
    recentRegions,
    recommendedRegions,
    regionSearchQuery,
    setRegionSearchQuery,
    regionSearchResults,
    isRegionSearching,
    isRegionSheetOpen,
    openRegionSheet,
    closeRegionSheet,
    selectRegion,
    clearRegionSelection,
  } = useRegionSelection();

  return (
    <div className="space-y-6">
      {/* 지역 선택 필드 */}
      <ButtonInputField
        title={{ main: "지역 선택", sub: "지역명을 검색해 선택하세요" }}
        value={selectedRegionOption?.label ?? ""}
        placeholder="지역명을 검색해 선택하세요"
        onClick={openRegionSheet}
      />

      {selectedRegionOption ? (
        <FieldNoteSection className="bg-slate-50">
          <FormTitle main="지역 메모" sub={selectedRegionOption.label} />

          <div className="mt-4 space-y-3">
            <TextAreaField
              title={{ main: "메모" }}
              value={regionMemo}
              onChange={setRegionMemo}
              placeholder="이 지역에 대한 기록을 남겨보세요"
              showCount={false}
            />
          </div>
        </FieldNoteSection>
      ) : null}

      <SelectionSearchSheet
        open={isRegionSheetOpen}
        title="지역 선택"
        description="지역명을 검색해 선택하세요"
        searchLabel="지역 검색"
        searchValue={regionSearchQuery}
        onSearchChange={setRegionSearchQuery}
        searchPlaceholder="지역명을 입력하세요"
        recentTitle="최근 선택 지역"
        recentItems={recentRegions}
        recommendedTitle="추천 지역"
        recommendedItems={recommendedRegions}
        searchResults={regionSearchResults}
        isSearching={isRegionSearching}
        emptyDescription="시, 구, 동 이름으로 다시 검색해보세요."
        selectedKey={selectedRegionValue}
        selectedItem={selectedRegionSheetItem}
        onSelect={selectRegion}
        onDeselect={clearRegionSelection}
        onClose={closeRegionSheet}
      />
    </div>
  );
}
