"use client";

import { useEffect, useMemo, useState } from "react";
import { CheckCircle2, ChevronRight } from "lucide-react";
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
  complexQuery,
  onChangeComplexQuery,
  complexSuggestions,
  onSelectComplexSuggestion,
  onResetSelectedComplex,
  selectedComplex,
  selectedRegion,
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

  const hasSearchQuery = complexQuery.trim().length > 0;
  const isSelectionComplete = Boolean(selectedComplex && selectedRegion);
  const shouldShowSearchResults = hasSearchQuery && !isSelectionComplete;
  const hasSearchResults = shouldShowSearchResults && complexSuggestions.length > 0;
  const shouldShowEmptyState = shouldShowSearchResults && complexSuggestions.length === 0;

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
              ? "선택한 단지를 확인한 뒤 바로 기록을 이어가세요"
              : "기록할 단지명을 검색하고 결과에서 하나를 선택하세요"
          }
        />

        <SearchField
          value={complexQuery}
          onChange={onChangeComplexQuery}
          placeholder="단지명을 검색해보세요"
          className={isSelectionComplete ? "border-emerald-200 bg-emerald-50/50" : ""}
        />

        {!hasSearchQuery && !isSelectionComplete ? (
          <p className="px-1 text-xs font-medium text-slate-500">
            검색 후 결과 카드 하나를 탭하면 다음 입력 단계가 열립니다.
          </p>
        ) : null}
      </section>

      {hasSearchResults ? (
        <section className="space-y-3">
          <div className="flex items-center justify-between px-1">
            <p className="text-sm font-semibold text-slate-900">검색 결과</p>
            <p className="text-xs font-medium text-slate-500">{complexSuggestions.length}개</p>
          </div>

          <div className="space-y-2">
            {complexSuggestions.map((suggestion) => {
              const isActive = selectedComplex?.id === suggestion.id;

              return (
                <button
                  key={suggestion.id}
                  type="button"
                  onClick={() => onSelectComplexSuggestion?.(suggestion)}
                  className={`w-full rounded-[1.4rem] border px-4 py-4 text-left transition ${
                    isActive
                      ? "border-emerald-300 bg-emerald-50 shadow-[0_12px_30px_rgba(16,185,129,0.12)]"
                      : "border-slate-200 bg-white hover:border-slate-300 hover:bg-slate-50"
                  }`}
                >
                  <div className="flex items-start justify-between gap-3">
                    <div className="min-w-0 flex-1">
                      <p className="text-sm font-semibold text-slate-950">{suggestion.name}</p>
                      <p className="mt-1 text-sm leading-6 text-slate-600">
                        {suggestion.address ?? suggestion.meta}
                      </p>
                      <p className="mt-2 text-xs font-medium text-slate-500">
                        {suggestion.regionLabel}
                      </p>
                    </div>

                    <div
                      className={`flex h-9 w-9 shrink-0 items-center justify-center rounded-full border ${
                        isActive
                          ? "border-emerald-200 bg-emerald-100 text-emerald-600"
                          : "border-slate-200 bg-slate-50 text-slate-400"
                      }`}
                    >
                      {isActive ? (
                        <CheckCircle2 className="h-4 w-4" />
                      ) : (
                        <ChevronRight className="h-4 w-4" />
                      )}
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
            단지명 일부만 입력하거나 다른 지역명으로 다시 검색해보세요.
          </p>
        </section>
      ) : null}

      {isSelectionComplete ? (
        <>
          <SelectedComplexCard
            complexName={selectedComplex.name}
            address={selectedComplex.address ?? selectedComplex.meta}
            regionLabel={selectedRegion.label ?? selectedComplex.regionLabel}
            helperText="지역은 선택한 단지 기준으로 자동 반영됩니다"
            onAction={onResetSelectedComplex}
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
