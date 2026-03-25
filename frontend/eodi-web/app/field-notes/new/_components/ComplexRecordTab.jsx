"use client";

import { useEffect, useMemo, useState } from "react";
import AutocompleteField from "@/components/ui/input/AutocompleteField";
import { useToast } from "@/components/ui/container/ToastProvider";
import SelectedComplexCard from "./SelectedComplexCard";
import RegionInfo from "./RegionInfo";
import FloorTypeField from "./FloorTypeField";
import AskingPriceField from "./AskingPriceField";
import MemoField from "./MemoField";
import DetailSectionToggle from "./DetailSectionToggle";
import DetailRecordFields from "./DetailRecordFields";
import SaveButtonBar from "./SaveButtonBar";

const INITIAL_FORM = {
  floorType: null,
  floorValue: "",
  askingPrice: "",
  memo: "",
  managementStatus: null,
  noiseLevel: null,
  parkingStatus: null,
  commercialAreaStatus: null,
  agencyName: "",
};

export default function ComplexRecordTab({
  complexQuery,
  onChangeComplexQuery,
  complexSuggestions,
  onSelectComplexSuggestion,
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
    <div className="space-y-6 pb-24">
      <div className="space-y-3">
        <label className="text-sm font-semibold text-slate-900">단지 검색</label>
        <AutocompleteField
          value={complexQuery}
          onChange={onChangeComplexQuery}
          placeholder="단지명을 검색해보세요"
          suggestions={complexSuggestions}
          onSelectSuggestion={onSelectComplexSuggestion}
          helperText="선택한 단지의 지역은 자동으로 채워져요"
        />
      </div>

      {selectedComplex ? (
        <>
          <SelectedComplexCard
            complexName={selectedComplex.name}
            regionLabel={selectedRegion?.label ?? selectedComplex.regionLabel}
          />

          <RegionInfo value={selectedRegion?.label ?? selectedComplex.regionLabel} />

          <section className="rounded-[1.5rem] border border-slate-200 bg-slate-50 p-4 shadow-[0_18px_40px_rgba(15,23,42,0.04)]">
            <div className="space-y-1">
              <p className="text-sm font-semibold text-slate-900">기본 기록</p>
              <p className="text-xs font-medium text-slate-500">자주 남기는 항목만 먼저 보여줍니다</p>
            </div>

            <div className="mt-5 space-y-5">
              <FloorTypeField
                floorType={form.floorType}
                floorValue={form.floorValue}
                onChangeFloorType={handleChangeFloorType}
                onChangeFloorValue={(value) => handleChangeField("floorValue", value)}
                errorMessage={floorErrorMessage}
              />
              <AskingPriceField
                askingPrice={form.askingPrice}
                onChangeAskingPrice={(value) => handleChangeField("askingPrice", value)}
              />
              <MemoField memo={form.memo} onChangeMemo={(value) => handleChangeField("memo", value)} />
            </div>
          </section>

          <DetailSectionToggle
            isOpen={isDetailOpen}
            onToggle={() => setIsDetailOpen((prev) => !prev)}
          />

          {isDetailOpen ? (
            <DetailRecordFields form={form} onChangeField={handleChangeField} />
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
