"use client";

import { useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import Select from "@/components/ui/Select";
import ButtonInputField from "@/app/field-notes/new/_components/field/ButtonInputField";
import TextAreaField from "@/app/field-notes/new/_components/field/TextAreaField";
import Field from "@/app/field-notes/new/_components/field/Field";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import FieldNoteSection from "@/app/field-notes/new/_components/section/FieldNoteSection";
import SelectionSearchSheet from "@/app/field-notes/new/_components/section-sheet/SelectionSearchSection";
import useRegionSelection from "@/app/field-notes/new/_hooks/useRegionSelection";
import { RECORD_TYPE_OPTIONS } from "@/app/field-notes/new/_data/fieldNoteOptions";

export default function RegionRecordTab() {
  const pathname = usePathname();
  const router = useRouter();
  const [regionMemo, setRegionMemo] = useState("");
  const recordType = pathname?.startsWith("/field-notes/new/region") ? "region" : "complex";
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

  const handleChangeRecordType = (value) => {
    const nextOption = RECORD_TYPE_OPTIONS.find((option) => option.value === value);

    if (!nextOption || nextOption.value === recordType) {
      return;
    }

    router.push(nextOption.href);
  };

  return (
    <div className="space-y-6">
      <FieldNoteSection className="bg-slate-50 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
        <FormTitle
          main="방문 정보"
          sub="이번 기록의 기본 정보를 먼저 확인해주세요"
          preserveSubSpace={false}
        />

        <div className="mt-5">
          <Field title={{ main: "기록 유형" }}>
            <Select
              options={RECORD_TYPE_OPTIONS}
              value={recordType}
              onChange={handleChangeRecordType}
              placeholder="기록 유형을 선택해주세요"
              width="w-full"
            />
          </Field>
        </div>
      </FieldNoteSection>

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
