"use client";

import { useEffect, useMemo, useState } from "react";
import { useToast } from "@/components/ui/container/ToastProvider";
import {
  FACING_OPTIONS,
  RECOMMENDED_REGION_VALUES,
  STAR_SCORE_LABELS,
} from "@/app/field-notes/new/_data/fieldNoteOptions";
import useComplexSelection from "@/app/field-notes/new/_hooks/useComplexSelection";
import FacingField from "@/app/field-notes/new/_components/field/FacingField";
import FloorTypeField from "@/app/field-notes/new/_components/field/FloorTypeField";
import AskingPriceField from "@/app/field-notes/new/_components/field/AskingPriceField";
import TextAreaField from "@/app/field-notes/new/_components/field/TextAreaField";
import SaveButtonBar from "../../../../../components/ui/SaveButtonBar";
import StarRatingField from "@/app/field-notes/new/_components/field/StarRatingField";
import ButtonInputField from "@/app/field-notes/new/_components/field/ButtonInputField";
import CollapsibleFormSection from "@/app/field-notes/new/_components/section/CollapsibleFormSection";
import SelectionSearchSheet from "@/app/field-notes/new/_components/section-sheet/SelectionSearchSection";
import TextInputField from "@/app/field-notes/new/_components/field/TextInputField";

const INITIAL_FORM = {
  floorType: null,
  floorValue: "",
  facing: "",
  askingPrice: "",
  memo: "",
  managementStatus: null,
  noiseLevel: null,
  parkingStatus: null,
  sunlightStatus: null,
  commercialAreaStatus: null,
  agencyName: "",
};

function ComplexRecordTab() {
  const { showToast } = useToast();
  const [form, setForm] = useState(INITIAL_FORM);
  const [openSections, setOpenSections] = useState({
    basicInfo: true,
    buildingStatus: false,
    householdExperience: false,
    memo: false,
  });
  const [autoFilledRegion, setAutoFilledRegion] = useState(null);
  const [recentRegionValues, setRecentRegionValues] = useState(
    RECOMMENDED_REGION_VALUES.slice(0, 2)
  );

  const rememberRegion = (value) => {
    if (!value) {
      return;
    }

    setRecentRegionValues((prev) => [value, ...prev.filter((item) => item !== value)].slice(0, 4));
  };

  const {
    selectedComplex,
    selectedComplexSheetItem,
    recentComplexes,
    recommendedComplexes,
    complexSearchQuery,
    setComplexSearchQuery,
    complexSearchResults,
    isComplexSearching,
    isComplexSheetOpen,
    openComplexSheet,
    closeComplexSheet,
    selectComplex,
    clearComplexSelection,
  } = useComplexSelection({
    recentRegionValues,
    onSelectComplex: (_complex, matchedRegion) => {
      setAutoFilledRegion(matchedRegion);
      rememberRegion(matchedRegion?.value);
    },
  });

  const selectedRegion = autoFilledRegion;

  useEffect(() => {
    setForm(INITIAL_FORM);
    setOpenSections({
      basicInfo: true,
      buildingStatus: false,
      householdExperience: false,
      memo: false,
    });
  }, [selectedComplex?.id]);

  const savePayload = useMemo(() => {
    if (!selectedComplex || !selectedRegion) {
      return null;
    }

    return {
      complexId: selectedComplex.id,
      regionId: selectedRegion.value,
      floorType: form.floorType,
      floorValue: form.floorType === "DIRECT" && form.floorValue ? Number(form.floorValue) : null,
      facing: form.facing || null,
      askingPrice: form.askingPrice ? Number(form.askingPrice) : null,
      memo: form.memo,
      managementStatus: form.managementStatus,
      noiseLevel: form.noiseLevel,
      parkingStatus: form.parkingStatus,
      sunlightStatus: form.sunlightStatus,
      commercialAreaStatus: form.commercialAreaStatus,
      agencyName: form.agencyName,
    };
  }, [form, selectedComplex, selectedRegion]);

  const floorErrorMessage =
    form.floorType === "DIRECT" && !form.floorValue ? "직접 입력 층수를 입력해주세요" : "";

  const handleChangeField = (field, value) => {
    setForm((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleChangeFloorType = (value) => {
    setForm((prev) => ({
      ...prev,
      floorType: value,
      floorValue: value === "DIRECT" ? prev.floorValue : "",
    }));
  };

  const toggleSection = (sectionKey) => {
    setOpenSections((prev) => ({
      ...prev,
      [sectionKey]: !prev[sectionKey],
    }));
  };

  const handleClearComplex = () => {
    clearComplexSelection();
    setAutoFilledRegion(null);
  };

  const handleSave = () => {
    if (!savePayload) {
      return;
    }

    if (floorErrorMessage) {
      showToast({ text: floorErrorMessage, type: "warning" });
      return;
    }

    console.log("complex-field-note-payload", savePayload);
    showToast({ text: "단지 임장 기록을 저장했어요", type: "success" });
  };

  return (
    <div className="space-y-6 pb-32 [padding-bottom:calc(env(safe-area-inset-bottom)+8.5rem)]">
      {/* 단지 선택 필드 */}
      <ButtonInputField
        title={{ main: "단지 선택", sub: "단지명을 검색해 선택하세요" }}
        value={selectedComplex?.name ?? ""}
        placeholder="단지명을 검색해 선택하세요"
        onClick={openComplexSheet}
      />

      {selectedComplex && selectedRegion ? (
        <>
          <CollapsibleFormSection
            title="기본정보"
            description="기본 정보부터 빠르게 남겨보세요"
            isOpen={openSections.basicInfo}
            onToggle={() => toggleSection("basicInfo")}
            className="bg-slate-50 shadow-[0_18px_40px_rgba(15,23,42,0.04)]"
          >
            <AskingPriceField
              title={{ main: "호가" }}
              askingPrice={form.askingPrice}
              onChangeAskingPrice={(value) => handleChangeField("askingPrice", value)}
            />

            <FloorTypeField
              floorType={form.floorType}
              floorValue={form.floorValue}
              onChangeFloorType={handleChangeFloorType}
              onChangeFloorValue={(value) => handleChangeField("floorValue", value)}
              errorMessage={floorErrorMessage}
              title={{ main: "층수" }}
            />

            <FacingField
              title={{ main: "향" }}
              value={form.facing}
              options={FACING_OPTIONS}
              onChange={(value) => handleChangeField("facing", value)}
            />

            <TextInputField
              title={{ main: "부동산명" }}
              value={form.agencyName}
              onChange={(event) => handleChangeField("agencyName", event.target.value)}
              placeholder={"공인중개사"}
            />
          </CollapsibleFormSection>

          <CollapsibleFormSection
            title="건물 / 단지 / 주변"
            description="건물과 주변 환경 전반의 상태를 정리해보세요"
            isOpen={openSections.buildingStatus}
            onToggle={() => toggleSection("buildingStatus")}
          >
            <StarRatingField
              title={{ main: "관리 상태" }}
              value={form.managementStatus}
              scoreLabels={STAR_SCORE_LABELS.management}
              onChange={(value) => handleChangeField("managementStatus", value)}
            />
            <StarRatingField
              title={{ main: "주차" }}
              value={form.parkingStatus}
              scoreLabels={STAR_SCORE_LABELS.parking}
              onChange={(value) => handleChangeField("parkingStatus", value)}
            />
            <StarRatingField
              title={{ main: "상권" }}
              value={form.commercialAreaStatus}
              scoreLabels={STAR_SCORE_LABELS.commercialArea}
              onChange={(value) => handleChangeField("commercialAreaStatus", value)}
            />
          </CollapsibleFormSection>

          <CollapsibleFormSection
            title="세대 내 체감"
            description="실내에서 직접 느낀 요소를 남겨보세요"
            isOpen={openSections.householdExperience}
            onToggle={() => toggleSection("householdExperience")}
          >
            <StarRatingField
              title={{ main: "채광" }}
              value={form.sunlightStatus}
              scoreLabels={STAR_SCORE_LABELS.sunlight}
              onChange={(value) => handleChangeField("sunlightStatus", value)}
            />
            <StarRatingField
              title={{ main: "소음" }}
              value={form.noiseLevel}
              scoreLabels={STAR_SCORE_LABELS.noise}
              onChange={(value) => handleChangeField("noiseLevel", value)}
            />
          </CollapsibleFormSection>

          <CollapsibleFormSection
            title="메모"
            description="자유롭게 남겨둘 내용을 정리해보세요"
            isOpen={openSections.memo}
            onToggle={() => toggleSection("memo")}
          >
            <TextAreaField
              value={form.memo}
              onChange={(value) => handleChangeField("memo", value)}
            />
          </CollapsibleFormSection>

          {/* 저장 버튼 필드 */}
          <SaveButtonBar
            disabled={Boolean(floorErrorMessage)}
            helperText={floorErrorMessage}
            onSave={handleSave}
          />
        </>
      ) : null}

      <SelectionSearchSheet
        open={isComplexSheetOpen}
        title="단지 선택"
        description="단지명을 검색해 선택하세요"
        searchLabel="단지 검색"
        searchValue={complexSearchQuery}
        onSearchChange={setComplexSearchQuery}
        searchPlaceholder="단지명이나 지역명을 입력하세요"
        recentTitle="최근 선택 단지"
        recentItems={recentComplexes}
        recommendedTitle="추천 단지"
        recommendedItems={recommendedComplexes}
        searchResults={complexSearchResults}
        isSearching={isComplexSearching}
        emptyDescription="단지명이나 지역명으로 다시 검색해보세요."
        selectedKey={selectedComplex?.id}
        selectedItem={selectedComplexSheetItem}
        onSelect={selectComplex}
        onDeselect={handleClearComplex}
        onClose={closeComplexSheet}
      />
    </div>
  );
}

export default ComplexRecordTab;
