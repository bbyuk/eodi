"use client";

import { useEffect, useMemo, useRef, useState } from "react";
import { usePathname, useRouter } from "next/navigation";
import { Check, Trash2 } from "lucide-react";
import { useToast } from "@/components/ui/container/ToastProvider";
import Select from "@/components/ui/Select";
import OptionButton from "@/components/ui/OptionButton";
import {
  FACING_OPTIONS,
  RECOMMENDED_REGION_VALUES,
  RECORD_TYPE_OPTIONS,
  STAR_SCORE_LABELS,
} from "@/app/field-notes/new/_data/fieldNoteOptions";
import COPY from "@/app/field-notes/new/_data/complexRecordCopy";
import useComplexSelection from "@/app/field-notes/new/_hooks/useComplexSelection";
import Field from "@/app/field-notes/new/_components/field/Field";
import FacingField from "@/app/field-notes/new/_components/field/FacingField";
import AskingPriceField from "@/app/field-notes/new/_components/field/AskingPriceField";
import TextAreaField from "@/app/field-notes/new/_components/field/TextAreaField";
import SaveButtonBar from "../../../../../components/ui/SaveButtonBar";
import ButtonInputField from "@/app/field-notes/new/_components/field/ButtonInputField";
import FormTitle from "@/app/field-notes/new/_components/field/FormTitle";
import NumberInputField from "@/app/field-notes/new/_components/field/NumberInputField";
import StarRatingField from "@/app/field-notes/new/_components/field/StarRatingField";
import DateInputField from "@/app/field-notes/new/_components/field/DateInputField";
import CollapsibleFormSection from "@/app/field-notes/new/_components/section/CollapsibleFormSection";
import FieldNoteSection from "@/app/field-notes/new/_components/section/FieldNoteSection";
import SelectionSearchSheet from "@/app/field-notes/new/_components/section-sheet/SelectionSearchSection";
import { FIELD_NOTE_INPUT_RADIUS_CLASS } from "@/app/field-notes/new/_components/styles";

const INTEREST_OPTIONS = [
  { value: "HIGH", label: COPY.complexInterestOptionHigh },
  { value: "MEDIUM", label: COPY.complexInterestOptionMedium },
  { value: "LOW", label: COPY.complexInterestOptionLow },
];

const INITIAL_BASIC_RECORD = {
  complexMood: null,
  surroundings: null,
  parking: null,
  commonMemo: "",
};

const getTodayDateString = () => {
  const date = new Date();
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
};

const createVisitedHome = (index) => ({
  id: `visited-home-${index}`,
  building: "",
  unit: "",
  askingPrice: "",
  facing: "",
  floor: "",
  memo: "",
  isHighlighted: false,
});

const getHomeTitle = (home, index) => {
  if (home.building && home.unit) {
    return `${home.building}동 ${home.unit}호`;
  }

  if (home.building) {
    return `${home.building}동`;
  }

  return COPY.homeTitle(index + 1);
};

function ComplexRecordTab() {
  const { showToast } = useToast();
  const pathname = usePathname();
  const router = useRouter();
  const visitedHomeNextIdRef = useRef(1);
  const [visitDate, setVisitDate] = useState(() => getTodayDateString());
  const [basicRecord, setBasicRecord] = useState(INITIAL_BASIC_RECORD);
  const [complexInterest, setComplexInterest] = useState(null);
  const [isComplexInterestOpen, setIsComplexInterestOpen] = useState(true);
  const [visitedHomes, setVisitedHomes] = useState([]);
  const [openSections, setOpenSections] = useState({
    basicRecord: true,
  });
  const [openVisitedHomeIds, setOpenVisitedHomeIds] = useState({});
  const [autoFilledRegion, setAutoFilledRegion] = useState(null);
  const [recentRegionValues, setRecentRegionValues] = useState(
    RECOMMENDED_REGION_VALUES.slice(0, 2)
  );

  const recordType = pathname?.startsWith("/field-notes/new/region") ? "region" : "complex";
  const basicRecordCompletionItems = [
    { key: "complexMood", label: COPY.complexMoodLabel },
    { key: "surroundings", label: COPY.surroundingsLabel },
    { key: "parking", label: COPY.parkingLabel },
    { key: "commonMemo", label: COPY.commonMemoLabel },
  ];
  const completedBasicRecordLabels = basicRecordCompletionItems
    .filter(({ key }) => {
      if (key === "commonMemo") {
        return basicRecord.commonMemo.trim().length > 0;
      }

      return Boolean(basicRecord[key]);
    })
    .map(({ label }) => label);
  const completedBasicRecordCount = completedBasicRecordLabels.length;
  const completedBasicRecordSummary =
    completedBasicRecordCount > 2
      ? `${completedBasicRecordLabels.slice(0, 2).join(" · ")} 외 ${
          completedBasicRecordCount - 2
        }개`
      : completedBasicRecordLabels.join(" · ");
  const basicRecordStatus =
    completedBasicRecordCount === 0
      ? {
          title: COPY.basicRecordCollapsedEmpty,
          summary: "",
        }
      : completedBasicRecordCount === basicRecordCompletionItems.length
        ? {
            title: COPY.basicRecordCollapsedComplete,
            summary: COPY.basicRecordCollapsedCompleteSummary,
          }
        : {
            title: COPY.basicRecordCollapsedPartial,
            summary: completedBasicRecordSummary,
          };
  const highlightedHomesCount = visitedHomes.filter((home) => home.isHighlighted).length;
  const selectedComplexInterestLabel = INTEREST_OPTIONS.find(
    (option) => option.value === complexInterest
  )?.label;

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
    setBasicRecord(INITIAL_BASIC_RECORD);
    setComplexInterest(null);
    setIsComplexInterestOpen(true);
    setVisitedHomes([]);
    visitedHomeNextIdRef.current = 1;
    setOpenSections({
      basicRecord: true,
    });
    setOpenVisitedHomeIds({});
  }, [selectedComplex?.id]);

  const savePayload = useMemo(() => {
    if (!selectedComplex || !selectedRegion) {
      return null;
    }

    return {
      recordType,
      visitDate,
      complexId: selectedComplex.id,
      complexName: selectedComplex.name,
      regionId: selectedRegion.value,
      basicRecord,
      visitDecision: {
        complexInterest,
        highlightedHomesCount,
      },
      homes: visitedHomes.map((home, index) => ({
        sequence: index + 1,
        building: home.building,
        unit: home.unit,
        askingPrice: home.askingPrice ? Number(home.askingPrice) : null,
        facing: home.facing || null,
        floor: home.floor ? Number(home.floor) : null,
        memo: home.memo,
        isHighlighted: home.isHighlighted,
      })),
    };
  }, [
    basicRecord,
    complexInterest,
    highlightedHomesCount,
    recordType,
    selectedComplex,
    selectedRegion,
    visitDate,
    visitedHomes,
  ]);

  const handleChangeRecordType = (value) => {
    const nextOption = RECORD_TYPE_OPTIONS.find((option) => option.value === value);

    if (!nextOption || nextOption.value === recordType) {
      return;
    }

    router.push(nextOption.href);
  };

  const handleChangeBasicRecord = (field, value) => {
    setBasicRecord((prev) => ({
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
      <FieldNoteSection className="bg-slate-50 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
        <FormTitle
          main={COPY.visitInfoTitle}
          sub={COPY.visitInfoDescription}
          preserveSubSpace={false}
        />

        <div className="mt-5 space-y-5">
          <Field title={{ main: COPY.recordTypeLabel }}>
            <Select
              options={RECORD_TYPE_OPTIONS}
              value={recordType}
              onChange={handleChangeRecordType}
              placeholder={COPY.recordTypePlaceholder}
              width="w-full"
            />
          </Field>

          <DateInputField
            title={{ main: COPY.visitDateLabel, sub: COPY.visitDateDescription }}
            value={visitDate}
            onChange={setVisitDate}
          />

          <ButtonInputField
            title={{ main: COPY.complexLabel, sub: COPY.complexDescription }}
            value={selectedComplex?.name ?? ""}
            placeholder={COPY.complexPlaceholder}
            onClick={openComplexSheet}
          />
        </div>
      </FieldNoteSection>

      {selectedComplex && selectedRegion ? (
        <>
          <div className="space-y-3">
            <FormTitle
              main={COPY.basicRecordTitle}
              sub={COPY.basicRecordDescription}
              preserveSubSpace={false}
            />

            <CollapsibleFormSection
              title={basicRecordStatus.title}
              headerContent={
                <div className="flex min-w-0 items-center justify-between gap-3">
                  <p className="shrink-0 text-sm font-semibold text-slate-900">
                    {basicRecordStatus.title}
                  </p>
                  {basicRecordStatus.summary ? (
                    <p className="min-w-0 truncate text-right text-xs font-medium text-slate-500">
                      {basicRecordStatus.summary}
                    </p>
                  ) : null}
                </div>
              }
              isOpen={openSections.basicRecord}
              onToggle={() => toggleSection("basicRecord")}
              toggleOnHeader={false}
              headerActionPlacement="beforeToggle"
              className="bg-slate-50 shadow-[0_18px_40px_rgba(15,23,42,0.04)]"
            >
              <StarRatingField
                title={{ main: COPY.complexMoodLabel }}
                value={basicRecord.complexMood}
                onChange={(value) => handleChangeBasicRecord("complexMood", value)}
                scoreLabels={STAR_SCORE_LABELS.complexMood}
              />
              <StarRatingField
                title={{ main: COPY.surroundingsLabel }}
                value={basicRecord.surroundings}
                onChange={(value) => handleChangeBasicRecord("surroundings", value)}
                scoreLabels={STAR_SCORE_LABELS.surroundings}
              />
              <StarRatingField
                title={{ main: COPY.parkingLabel }}
                value={basicRecord.parking}
                onChange={(value) => handleChangeBasicRecord("parking", value)}
                scoreLabels={STAR_SCORE_LABELS.parking}
              />
              <TextAreaField
                title={{ main: COPY.commonMemoLabel }}
                value={basicRecord.commonMemo}
                onChange={(value) => handleChangeBasicRecord("commonMemo", value)}
                placeholder={COPY.commonMemoPlaceholder}
                showCount={false}
              />
            </CollapsibleFormSection>
          </div>

          <div className="space-y-3">
            <FormTitle
              main={COPY.visitDecisionTitle}
              sub={COPY.visitDecisionDescription}
              preserveSubSpace={false}
            />

            <CollapsibleFormSection
              title={selectedComplexInterestLabel ?? COPY.complexInterestLabel}
              isOpen={isComplexInterestOpen}
              onToggle={() => setIsComplexInterestOpen((prev) => !prev)}
              headerActionPlacement="beforeToggle"
              className="bg-white shadow-[0_18px_40px_rgba(15,23,42,0.04)]"
            >
              <Field>
                <div className="grid grid-cols-1 gap-2 sm:grid-cols-3">
                  {INTEREST_OPTIONS.map((option) => (
                    <OptionButton
                      key={option.value}
                      label={option.label}
                      active={complexInterest === option.value}
                      onClick={() => {
                        setComplexInterest(option.value);
                        setIsComplexInterestOpen(false);
                      }}
                    />
                  ))}
                </div>
              </Field>
            </CollapsibleFormSection>
          </div>

          <div className="space-y-3">
            <FormTitle
              main={COPY.homesTitle}
              sub={COPY.homesDescription}
              preserveSubSpace={false}
            />

            <div className="space-y-4">
              {visitedHomes.map((home, index) => {
                const homeTitle = getHomeTitle(home, index);

                return (
                  <CollapsibleFormSection
                    key={home.id}
                    title={homeTitle}
                    isOpen={Boolean(openVisitedHomeIds[home.id])}
                    onToggle={() => toggleVisitedHome(home.id)}
                    headerActionPlacement="beforeToggle"
                    headerAction={
                      <button
                        type="button"
                        onClick={() => removeVisitedHome(home.id)}
                        aria-label={`${homeTitle} 삭제`}
                        className="inline-flex h-8 w-8 items-center justify-center rounded-full border border-slate-200 text-slate-400 transition hover:border-red-200 hover:bg-red-50 hover:text-red-600 focus:outline-none focus:ring-2 focus:ring-red-100"
                      >
                        <Trash2 className="h-3.5 w-3.5" />
                      </button>
                    }
                  >
                    <div className="grid grid-cols-2 gap-3">
                      <NumberInputField
                        title={{ main: COPY.buildingLabel }}
                        value={home.building}
                        onChange={(value) => handleChangeVisitedHome(home.id, "building", value)}
                        placeholder={COPY.buildingPlaceholder}
                        maxValue={9999}
                      />
                      <NumberInputField
                        title={{ main: COPY.unitLabel }}
                        value={home.unit}
                        onChange={(value) => handleChangeVisitedHome(home.id, "unit", value)}
                        placeholder={COPY.unitPlaceholder}
                        maxValue={99999}
                      />
                    </div>

                    <AskingPriceField
                      title={{ main: COPY.priceLabel }}
                      askingPrice={home.askingPrice}
                      onChangeAskingPrice={(value) =>
                        handleChangeVisitedHome(home.id, "askingPrice", value)
                      }
                      placeholder={COPY.pricePlaceholder}
                    />

                    <FacingField
                      title={{ main: COPY.facingLabel }}
                      value={home.facing}
                      options={FACING_OPTIONS}
                      onChange={(value) => handleChangeVisitedHome(home.id, "facing", value)}
                    />

                    <NumberInputField
                      title={{ main: COPY.floorLabel }}
                      value={home.floor}
                      onChange={(value) => handleChangeVisitedHome(home.id, "floor", value)}
                      placeholder={COPY.floorPlaceholder}
                      maxValue={999}
                    />

                    <TextAreaField
                      title={{ main: COPY.homeMemoLabel }}
                      value={home.memo}
                      onChange={(value) => handleChangeVisitedHome(home.id, "memo", value)}
                      placeholder={COPY.homeMemoPlaceholder}
                      showCount={false}
                    />

                    <Field
                      title={{
                        main: COPY.highlightHomeLabel,
                        sub: COPY.highlightHomeDescription,
                      }}
                    >
                      <button
                        type="button"
                        onClick={() =>
                          handleChangeVisitedHome(home.id, "isHighlighted", !home.isHighlighted)
                        }
                        aria-pressed={home.isHighlighted}
                        className={`flex min-h-12 w-full items-center gap-3 border px-4 text-left text-sm font-semibold transition ${FIELD_NOTE_INPUT_RADIUS_CLASS} ${
                          home.isHighlighted
                            ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)] text-[var(--choice-chip-selected-text)] shadow-[var(--choice-chip-selected-shadow)]"
                            : "border-slate-200 bg-white text-slate-700 hover:border-[var(--choice-chip-hover-border)] hover:bg-[var(--choice-chip-hover-bg)]"
                        }`}
                      >
                        <span
                          className={`inline-flex h-5 w-5 shrink-0 items-center justify-center rounded-full border ${
                            home.isHighlighted
                              ? "border-[var(--choice-chip-selected-border)] bg-[var(--choice-chip-selected-bg)]"
                              : "border-slate-300 bg-white"
                          }`}
                        >
                          {home.isHighlighted ? <Check className="h-3 w-3" /> : null}
                        </span>
                        {COPY.highlightHomeLabel}
                      </button>
                    </Field>
                  </CollapsibleFormSection>
                );
              })}
            </div>

            <button
              type="button"
              onClick={addVisitedHome}
              className={`flex min-h-12 w-full items-center justify-center border border-dashed border-slate-300 bg-white text-sm font-semibold text-slate-600 transition hover:border-slate-400 hover:bg-slate-50 focus:outline-none focus:ring-2 focus:ring-slate-200 ${FIELD_NOTE_INPUT_RADIUS_CLASS}`}
            >
              {COPY.addHomeButton}
            </button>
          </div>

          <SaveButtonBar onSave={handleSave} />
        </>
      ) : null}

      <SelectionSearchSheet
        open={isComplexSheetOpen}
        title={COPY.complexLabel}
        description={COPY.complexPlaceholder}
        searchLabel="단지 검색"
        searchValue={complexSearchQuery}
        onSearchChange={setComplexSearchQuery}
        searchPlaceholder={COPY.complexPlaceholder}
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
