"use client";

import { useEffect, useMemo, useRef, useState } from "react";
import { useToast } from "@/components/ui/container/ToastProvider";
import {
  FACING_OPTIONS,
  RECOMMENDED_REGION_VALUES,
  STAR_SCORE_LABELS,
} from "@/app/field-notes/new/_data/fieldNoteOptions";
import { Trash2 } from "lucide-react";
import useComplexSelection from "@/app/field-notes/new/_hooks/useComplexSelection";
import FacingField from "@/app/field-notes/new/_components/field/FacingField";
import AskingPriceField from "@/app/field-notes/new/_components/field/AskingPriceField";
import TextAreaField from "@/app/field-notes/new/_components/field/TextAreaField";
import SaveButtonBar from "../../../../../components/ui/SaveButtonBar";
import StarRatingField from "@/app/field-notes/new/_components/field/StarRatingField";
import ButtonInputField from "@/app/field-notes/new/_components/field/ButtonInputField";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import NumberInputField from "@/app/field-notes/new/_components/field/NumberInputField";
import CollapsibleFormSection from "@/app/field-notes/new/_components/section/CollapsibleFormSection";
import SelectionSearchSheet from "@/app/field-notes/new/_components/section-sheet/SelectionSearchSection";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";
import TextInputField from "@/app/field-notes/new/_components/field/TextInputField";

const INITIAL_COMPLEX_RECORD = {
  managementStatus: null,
  parkingStatus: null,
  transportStatus: null,
  commercialAreaStatus: null,
};

const COPY = {
  complexSectionTitle: "기본 기록",
  complexSectionDescription: "단지 분위기와 주변 환경을 먼저 기록해보세요",

  homesTitle: "세대 기록",
  homesDescription: "내부를 확인한 세대만 추가해 기록해보세요",

  homeTitle: (index) => `세대 ${index}`,
  homeDescription: "동·호수와 내부에서 느낀 점을 남겨보세요",

  addHome: "+ 본 집 추가",

  saveSuccess: "임장 기록을 저장했어요",

  agencyLabel: "중개사무소",
  agencyPlaceholder: "예: ○○공인중개사",

  memoPlaceholder: "특이사항이나 다시 확인할 점을 적어보세요",
};

const createVisitedHome = (index) => ({
  id: `visited-home-${index}`,
  dong: "",
  unit: "",
  facing: "",
  askingPrice: "",
  memo: "",
  noiseLevel: null,
  sunlightStatus: null,
  agencyName: "",
});

function ComplexRecordTab() {
  const { showToast } = useToast();
  const visitedHomeNextIdRef = useRef(1);
  const [complexRecord, setComplexRecord] = useState(INITIAL_COMPLEX_RECORD);
  const [visitedHomes, setVisitedHomes] = useState([]);
  const [openSections, setOpenSections] = useState({
    complexRecord: true,
  });
  const [openVisitedHomeIds, setOpenVisitedHomeIds] = useState({});
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
    setComplexRecord(INITIAL_COMPLEX_RECORD);
    setVisitedHomes([]);
    visitedHomeNextIdRef.current = 1;
    setOpenSections({
      complexRecord: true,
    });
    setOpenVisitedHomeIds({});
  }, [selectedComplex?.id]);

  const savePayload = useMemo(() => {
    if (!selectedComplex || !selectedRegion) {
      return null;
    }

    return {
      complexId: selectedComplex.id,
      regionId: selectedRegion.value,
      complexRecord: {
        managementStatus: complexRecord.managementStatus,
        parkingStatus: complexRecord.parkingStatus,
        transportStatus: complexRecord.transportStatus,
        commercialAreaStatus: complexRecord.commercialAreaStatus,
      },
      visitedHomes: visitedHomes.map((home, index) => ({
        sequence: index + 1,
        dong: home.dong,
        unit: home.unit,
        facing: home.facing || null,
        askingPrice: home.askingPrice ? Number(home.askingPrice) : null,
        memo: home.memo,
        noiseLevel: home.noiseLevel,
        sunlightStatus: home.sunlightStatus,
        agencyName: home.agencyName,
      })),
    };
  }, [complexRecord, selectedComplex, selectedRegion, visitedHomes]);

  const handleChangeComplexRecord = (field, value) => {
    setComplexRecord((prev) => ({
      ...prev,
      [field]: value,
    }));
  };

  const handleChangeVisitedHome = (homeId, field, value) => {
    setVisitedHomes((prev) =>
      prev.map((home) => (home.id === homeId ? { ...home, [field]: value } : home))
    );
  };

  const toggleSection = (sectionKey) => {
    setOpenSections((prev) => ({
      ...prev,
      [sectionKey]: !prev[sectionKey],
    }));
  };

  const toggleVisitedHome = (homeId) => {
    setOpenVisitedHomeIds((prev) => ({
      ...prev,
      [homeId]: !prev[homeId],
    }));
  };

  const addVisitedHome = () => {
    const nextHome = createVisitedHome(visitedHomeNextIdRef.current);
    visitedHomeNextIdRef.current += 1;

    setVisitedHomes((prev) => [...prev, nextHome]);
    setOpenVisitedHomeIds((prev) => ({
      ...prev,
      [nextHome.id]: true,
    }));
  };

  const removeVisitedHome = (homeId) => {
    setVisitedHomes((prev) => prev.filter((home) => home.id !== homeId));
    setOpenVisitedHomeIds((prev) => {
      const { [homeId]: _removed, ...next } = prev;
      return next;
    });
  };

  const handleClearComplex = () => {
    clearComplexSelection();
    setAutoFilledRegion(null);
  };

  const handleSave = () => {
    if (!savePayload) {
      return;
    }

    console.log("complex-field-note-payload", savePayload);
    showToast({ text: COPY.saveSuccess, type: "success" });
  };

  return (
    <div className="space-y-6 pb-32 [padding-bottom:calc(env(safe-area-inset-bottom)+8.5rem)]">
      {/* 단지 선택 필드 */}
      <ButtonInputField
        title={{ main: "단지 선택", sub: "어느 단지를 보고 왔는지 선택해주세요" }}
        value={selectedComplex?.name ?? ""}
        placeholder="검색"
        onClick={openComplexSheet}
      />

      {selectedComplex && selectedRegion ? (
        <>
          <CollapsibleFormSection
            title={COPY.complexSectionTitle}
            description={COPY.complexSectionDescription}
            isOpen={openSections.complexRecord}
            onToggle={() => toggleSection("complexRecord")}
            className="bg-slate-50 shadow-[0_18px_40px_rgba(15,23,42,0.04)]"
          >
            <StarRatingField
              title={{ main: "관리 상태" }}
              value={complexRecord.managementStatus}
              scoreLabels={STAR_SCORE_LABELS.management}
              onChange={(value) => handleChangeComplexRecord("managementStatus", value)}
            />
            <StarRatingField
              title={{ main: "주차" }}
              value={complexRecord.parkingStatus}
              scoreLabels={STAR_SCORE_LABELS.parking}
              onChange={(value) => handleChangeComplexRecord("parkingStatus", value)}
            />
            <StarRatingField
              title={{ main: "교통 편의" }}
              value={complexRecord.transportStatus}
              scoreLabels={STAR_SCORE_LABELS.transport}
              onChange={(value) => handleChangeComplexRecord("transportStatus", value)}
            />
            <StarRatingField
              title={{ main: "생활 편의" }}
              value={complexRecord.commercialAreaStatus}
              scoreLabels={STAR_SCORE_LABELS.commercialArea}
              onChange={(value) => handleChangeComplexRecord("commercialAreaStatus", value)}
            />
          </CollapsibleFormSection>

          <div className="space-y-3">
            <FormTitle
              main={COPY.homesTitle}
              sub={COPY.homesDescription}
              preserveSubSpace={false}
            />

            <div className="space-y-4">
              {visitedHomes.map((home, index) => (
                <CollapsibleFormSection
                  key={home.id}
                  title={COPY.homeTitle(index + 1)}
                  description={COPY.homeDescription}
                  isOpen={Boolean(openVisitedHomeIds[home.id])}
                  onToggle={() => toggleVisitedHome(home.id)}
                  headerActionPlacement="beforeToggle"
                  headerAction={
                    <button
                      type="button"
                      onClick={() => removeVisitedHome(home.id)}
                      aria-label={`${COPY.homeTitle(index + 1)} 삭제`}
                      className="inline-flex h-8 w-8 items-center justify-center rounded-full border border-slate-200 text-slate-400 transition hover:border-red-200 hover:bg-red-50 hover:text-red-600 focus:outline-none focus:ring-2 focus:ring-red-100"
                    >
                      <Trash2 className="h-3.5 w-3.5" />
                    </button>
                  }
                >
                  <div className="grid grid-cols-2 gap-3">
                    <NumberInputField
                      title={{ main: "동" }}
                      value={home.dong}
                      onChange={(value) => handleChangeVisitedHome(home.id, "dong", value)}
                      placeholder="101"
                      maxValue={9999}
                    />
                    <NumberInputField
                      title={{ main: "호수" }}
                      value={home.unit}
                      onChange={(value) => handleChangeVisitedHome(home.id, "unit", value)}
                      placeholder="1203"
                      maxValue={99999}
                    />
                  </div>

                  <AskingPriceField
                    title={{ main: "호가" }}
                    askingPrice={home.askingPrice}
                    onChangeAskingPrice={(value) =>
                      handleChangeVisitedHome(home.id, "askingPrice", value)
                    }
                  />

                  <FacingField
                    title={{ main: "향" }}
                    value={home.facing}
                    options={FACING_OPTIONS}
                    onChange={(value) => handleChangeVisitedHome(home.id, "facing", value)}
                  />

                  <TextInputField
                    title={{ main: COPY.agencyLabel }}
                    value={home.agencyName}
                    onChange={(event) =>
                      handleChangeVisitedHome(home.id, "agencyName", event.target.value)
                    }
                    placeholder={COPY.agencyPlaceholder}
                  />

                  <StarRatingField
                    title={{ main: "채광" }}
                    value={home.sunlightStatus}
                    scoreLabels={STAR_SCORE_LABELS.sunlight}
                    onChange={(value) => handleChangeVisitedHome(home.id, "sunlightStatus", value)}
                  />
                  <StarRatingField
                    title={{ main: "소음" }}
                    value={home.noiseLevel}
                    scoreLabels={STAR_SCORE_LABELS.noise}
                    onChange={(value) => handleChangeVisitedHome(home.id, "noiseLevel", value)}
                  />

                  <TextAreaField
                    value={home.memo}
                    onChange={(value) => handleChangeVisitedHome(home.id, "memo", value)}
                    placeholder={COPY.memoPlaceholder}
                  />
                </CollapsibleFormSection>
              ))}
            </div>

            <button
              type="button"
              onClick={addVisitedHome}
              className={`flex min-h-12 w-full items-center justify-center border border-dashed border-slate-300 bg-white text-sm font-semibold text-slate-600 transition hover:border-slate-400 hover:bg-slate-50 focus:outline-none focus:ring-2 focus:ring-slate-200 ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
            >
              {COPY.addHome}
            </button>
          </div>

          {/* 저장 버튼 필드 */}
          <SaveButtonBar onSave={handleSave} />
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
