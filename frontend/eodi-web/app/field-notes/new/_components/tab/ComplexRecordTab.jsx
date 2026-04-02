"use client";

import { useEffect, useMemo, useState } from "react";
import { CheckCircle2, ChevronRight, LoaderCircle } from "lucide-react";
import SearchField from "@/components/ui/SearchField";
import { useToast } from "@/components/ui/container/ToastProvider";
import SelectedComplexCard from "@/app/field-notes/new/_components/field/SelectedComplexCard";
import FloorTypeField from "@/app/field-notes/new/_components/field/FloorTypeField";
import AskingPriceField from "@/app/field-notes/new/_components/field/AskingPriceField";
import MemoField from "@/app/field-notes/new/_components/field/MemoField";
import DetailSectionToggle from "../../../../../components/ui/DetailSectionToggle";
import DetailRecordFields from "@/app/field-notes/new/_components/field/DetailRecordFields";
import SaveButtonBar from "../../../../../components/ui/SaveButtonBar";
import FieldTitle from "@/app/field-notes/new/_components/field/FieldTitle";
import OptionField from "@/app/field-notes/new/_components/field/OptionField";

const INITIAL_FORM = {
  floorType: null,
  floorValue: "",
  askingPrice: "",
  memo: "",
  managementStatus: null,
  noiseLevel: null,
  parkingStatus: null,
  sunlightStatus: null,
  commercialAreaStatus: null,
  agencyName: "",
};

export default function ComplexRecordTab({
  searchQuery,
  searchResults,
  isSearching,
  selectedComplex,
  autoFilledRegion,
  selectedRegion,
  onChangeSearchQuery,
  onSelectComplexSuggestion,
  onResetSelectedComplex,
  onOpenRegionSheet,
}) {
  const { showToast } = useToast();
  const [form, setForm] = useState(INITIAL_FORM);
  const [isDetailOpen, setIsDetailOpen] = useState(false);

  useEffect(() => {
    setForm(INITIAL_FORM);
    setIsDetailOpen(false);
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

  const hasSearchQuery = searchQuery.trim().length > 0;
  const isSelectionComplete = Boolean(selectedComplex && selectedRegion);
  const hasSearchResults = hasSearchQuery && !isSearching && searchResults.length > 0 && !isSelectionComplete;
  const shouldShowIdleState = !hasSearchQuery && !isSelectionComplete;
  const shouldShowEmptyState = hasSearchQuery && !isSearching && searchResults.length === 0 && !isSelectionComplete;
  const hasManualRegionChange = Boolean(
    autoFilledRegion && selectedRegion && autoFilledRegion.value !== selectedRegion.value
  );

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
      <section className="space-y-3">
        <FieldTitle
          main={"단지 선택"}
          sub={
            isSelectionComplete
              ? "선택한 단지를 확인하고 바로 기록을 이어가세요"
              : "단지명을 검색하고 결과 카드 전체를 눌러 선택하세요"
          }
        />

        {!isSelectionComplete ? (
          <SearchField
            value={searchQuery}
            onChange={onChangeSearchQuery}
            placeholder="단지명을 입력해 검색하세요"
          />
        ) : (
          <div className="rounded-[1.25rem] border border-emerald-200 bg-emerald-50/70 px-4 py-3">
            <p className="text-sm font-semibold text-emerald-800">단지 선택이 완료됐어요</p>
            <p className="mt-1 text-xs font-medium text-emerald-700/80">
              자동 입력된 지역을 확인하고 필요한 경우만 변경하세요.
            </p>
          </div>
        )}

        {shouldShowIdleState ? (
          <p className="px-1 text-xs font-medium text-slate-500">
            단지명을 입력하면 검색 결과가 바로 아래 카드 리스트로 나타납니다.
          </p>
        ) : null}
      </section>

      {isSearching && !isSelectionComplete ? (
        <section className="space-y-3 rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4">
          <div className="flex items-center gap-2 text-sm font-semibold text-slate-900">
            <LoaderCircle className="h-4 w-4 animate-spin text-slate-500" />
            검색 중
          </div>
          <div className="space-y-2">
            {[0, 1, 2].map((item) => (
              <div key={item} className="rounded-[1.25rem] border border-slate-200 bg-white px-4 py-4">
                <div className="h-4 w-28 animate-pulse rounded bg-slate-200" />
                <div className="mt-3 h-3 w-full animate-pulse rounded bg-slate-100" />
                <div className="mt-2 h-3 w-2/3 animate-pulse rounded bg-slate-100" />
              </div>
            ))}
          </div>
        </section>
      ) : null}

      {hasSearchResults ? (
        <section className="space-y-3">
          <div className="flex items-center justify-between px-1">
            <p className="text-sm font-semibold text-slate-900">검색 결과</p>
            <p className="text-xs font-medium text-slate-500">{searchResults.length}개</p>
          </div>

          <div className="space-y-2">
            {searchResults.map((suggestion) => {
              const locationDetail = suggestion.address?.includes(suggestion.regionLabel)
                ? null
                : suggestion.regionLabel;

              return (
                <button
                  key={suggestion.id}
                  type="button"
                  onClick={() => onSelectComplexSuggestion?.(suggestion)}
                  className="group w-full rounded-[1.45rem] border border-slate-200 bg-white px-4 py-4 text-left transition hover:border-slate-300 hover:bg-slate-50 active:scale-[0.99] active:border-slate-400"
                >
                  <div className="flex items-start justify-between gap-3">
                    <div className="min-w-0 flex-1">
                      <p className="text-base font-semibold tracking-tight text-slate-950">
                        {suggestion.name}
                      </p>
                      <p className="mt-1 text-sm leading-6 text-slate-600">{suggestion.address}</p>
                      {locationDetail ? (
                        <p className="mt-2 text-xs font-medium text-slate-400">{locationDetail}</p>
                      ) : null}
                    </div>

                    <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full border border-slate-200 bg-slate-50 text-slate-400 transition group-hover:border-slate-300 group-hover:bg-white group-hover:text-slate-600 group-active:border-slate-400">
                      <ChevronRight className="h-4 w-4" />
                    </div>
                  </div>
                </button>
              );
            })}
          </div>
        </section>
      ) : null}

      {shouldShowEmptyState ? (
        <section className="rounded-[1.5rem] border border-dashed border-slate-200 bg-slate-50 px-4 py-5">
          <p className="text-sm font-semibold text-slate-900">검색 결과가 없어요</p>
          <p className="mt-1 text-sm leading-6 text-slate-500">
            단지명 일부만 입력하거나 지역명으로 다시 검색해보세요.
          </p>
        </section>
      ) : null}

      {isSelectionComplete ? (
        <>
          <SelectedComplexCard
            complexName={selectedComplex.name}
            address={selectedComplex.address ?? selectedComplex.meta}
            regionLabel={selectedRegion.label}
            helperText={
              hasManualRegionChange
                ? "자동 입력된 지역을 수동으로 변경했어요"
                : "단지 선택과 함께 지역이 자동 입력됐어요"
            }
            statusLabel={hasManualRegionChange ? "지역 변경됨" : "선택 완료"}
            regionTagLabel={hasManualRegionChange ? "수동 변경" : "자동 입력"}
            actionLabel="단지 다시 선택"
            onAction={onResetSelectedComplex}
            onChangeRegion={onOpenRegionSheet}
          />

          <section className="rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
            <FieldTitle main={"기본 기록"} sub={"선택한 단지 기준으로 바로 기록을 남겨보세요"} />

            <div className="mt-5 space-y-5">
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
                title={{ main: "층수", sub: "옵션을 선택하거나 직접 입력해보세요" }}
              />

              <OptionField
                options={[
                  { label: "좋음", value: "GOOD" },
                  { label: "보통", value: "NORMAL" },
                  { label: "아쉬움", value: "BAD" },
                ]}
                onChange={(value) => handleChangeField("managementStatus", value)}
                title={{ main: "관리 상태" }}
                value={form.managementStatus}
              />

              <OptionField
                options={[
                  { label: "조용함", value: "LOW" },
                  { label: "보통", value: "NORMAL" },
                  { label: "시끄러움", value: "HIGH" },
                ]}
                onChange={(value) => handleChangeField("noiseLevel", value)}
                title={{ main: "소음" }}
                value={form.noiseLevel}
              />
            </div>
          </section>

          <DetailSectionToggle
            isOpen={isDetailOpen}
            onToggle={() => setIsDetailOpen((prev) => !prev)}
          />

          {isDetailOpen ? (
            <DetailRecordFields form={form} onChangeField={handleChangeField}>
              <MemoField memo={form.memo} onChangeMemo={(value) => handleChangeField("memo", value)} />
            </DetailRecordFields>
          ) : null}

          <SaveButtonBar
            disabled={Boolean(floorErrorMessage)}
            helperText={floorErrorMessage}
            onSave={handleSave}
          />
        </>
      ) : null}
    </div>
  );
}
