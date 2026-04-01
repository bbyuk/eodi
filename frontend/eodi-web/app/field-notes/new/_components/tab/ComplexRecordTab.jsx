"use client";

import { useEffect, useMemo, useState } from "react";
import AutocompleteField from "@/components/ui/AutocompleteField";
import { useToast } from "@/components/ui/container/ToastProvider";
import SelectedComplexCard from "@/app/field-notes/new/_components/field/SelectedComplexCard";
import RegionInfo from "@/app/field-notes/new/_components/field/RegionInfo";
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

  const handleChangeField = (field, value) => {
    console.log(value);
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
            <FieldTitle main={"기본 기록"} sub={"자주 입력하는 항목부터 빠르게 남겨보세요"} />

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
              <MemoField
                memo={form.memo}
                onChangeMemo={(value) => handleChangeField("memo", value)}
              />
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
