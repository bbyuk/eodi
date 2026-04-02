"use client";

import { useMemo, useState } from "react";
import BottomSheetSelect from "@/components/ui/BottomSheetSelect";
import Select from "@/components/ui/Select";
import ComplexRecordTab from "@/app/field-notes/new/_components/tab/ComplexRecordTab";
import RegionRecordTab from "@/app/field-notes/new/_components/tab/RegionRecordTab";
import FormHeader from "@/components/layout/FormHeader";
import FormContainer from "@/components/layout/FormContainer";

const REGION_OPTIONS = [
  {
    value: "nowon-sanggye",
    label: "서울 노원구 상계동",
    complexes: [
      { id: "sg-1", name: "상계주공 7단지", address: "서울시 노원구 동일로 1362", meta: "서울시 노원구 동일로 1362" },
      { id: "sg-2", name: "상계주공 10단지", address: "서울시 노원구 덕릉로 736", meta: "서울시 노원구 덕릉로 736" },
      { id: "sg-3", name: "보람아파트", address: "서울시 노원구 상계로 193", meta: "서울시 노원구 상계로 193" },
    ],
  },
  {
    value: "mapo-ahyeon",
    label: "서울 마포구 아현동",
    complexes: [
      { id: "ah-1", name: "마포래미안푸르지오", address: "서울시 마포구 마포대로 195", meta: "서울시 마포구 마포대로 195" },
      { id: "ah-2", name: "공덕자이", address: "서울시 마포구 백범로 152", meta: "서울시 마포구 백범로 152" },
      { id: "ah-3", name: "마포자이더센트리지", address: "서울시 마포구 신촌로 270", meta: "서울시 마포구 신촌로 270" },
    ],
  },
  {
    value: "suwon-yeongtong",
    label: "경기 수원시 영통구",
    complexes: [
      { id: "yw-1", name: "광교중흥S클래스", address: "경기도 수원시 영통구 광교호수로 300", meta: "경기도 수원시 영통구 광교호수로 300" },
      { id: "yw-2", name: "광교자연앤자이", address: "경기도 수원시 영통구 센트럴파크로 6", meta: "경기도 수원시 영통구 센트럴파크로 6" },
      { id: "yw-3", name: "e편한세상광교", address: "경기도 수원시 영통구 도청로 95", meta: "경기도 수원시 영통구 도청로 95" },
    ],
  },
];

const RECORD_TYPE_OPTIONS = [
  { value: "complex", label: "단지 기록", description: "단지명을 검색해 세부 기록을 남겨요" },
  { value: "region", label: "지역 기록", description: "지역을 선택해 빠르게 메모해요" },
];

export default function NewFieldNoteForm() {
  const [recordType, setRecordType] = useState("complex");
  const [isRegionSheetOpen, setIsRegionSheetOpen] = useState(false);
  const [selectedRegion, setSelectedRegion] = useState("");
  const [complexQuery, setComplexQuery] = useState("");
  const [selectedComplex, setSelectedComplex] = useState(null);
  const [regionMemo, setRegionMemo] = useState("");

  const selectedRegionOption = useMemo(
    () => REGION_OPTIONS.find((option) => option.value === selectedRegion),
    [selectedRegion]
  );

  const flattenedComplexes = useMemo(
    () =>
      REGION_OPTIONS.flatMap((region) =>
        region.complexes.map((complex) => ({
          ...complex,
          regionValue: region.value,
          regionLabel: region.label,
        }))
      ),
    []
  );

  const complexSuggestions = useMemo(() => {
    if (!complexQuery.trim()) {
      return flattenedComplexes;
    }

    const keyword = complexQuery.trim().toLowerCase();

    return flattenedComplexes.filter(
      (complex) =>
        complex.name.toLowerCase().includes(keyword) ||
        complex.regionLabel.toLowerCase().includes(keyword)
    );
  }, [complexQuery, flattenedComplexes]);

  const currentRecordType = useMemo(
    () => RECORD_TYPE_OPTIONS.find((option) => option.value === recordType) ?? RECORD_TYPE_OPTIONS[0],
    [recordType]
  );

  const handleSelectComplexRecord = () => {
    setRecordType("complex");
    setSelectedRegion("");
    setComplexQuery("");
    setSelectedComplex(null);
    setRegionMemo("");
  };

  const handleSelectRegionRecord = () => {
    setRecordType("region");
    setSelectedRegion("");
    setComplexQuery("");
    setSelectedComplex(null);
  };

  const handleChangeRecordType = (value) => {
    if (value === "region") {
      handleSelectRegionRecord();
      return;
    }

    handleSelectComplexRecord();
  };

  return (
    <>
      <div className="mx-auto flex w-full max-w-3xl flex-col gap-8 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
        <FormHeader value={"무엇을 기록할까요?"} />

        <FormContainer>
          <div className="space-y-2">
            <div className="flex items-center justify-between gap-3">
              <p className="text-sm font-semibold text-slate-900">기록 유형</p>
              <p className="text-xs font-medium text-slate-500">{currentRecordType.description}</p>
            </div>

            <Select
              options={RECORD_TYPE_OPTIONS}
              value={recordType}
              onChange={handleChangeRecordType}
              width="w-full"
            />
          </div>

          {recordType === "complex" ? (
            <ComplexRecordTab
              complexQuery={complexQuery}
              onChangeComplexQuery={(value) => {
                setComplexQuery(value);
                setSelectedComplex(null);
                setSelectedRegion("");
              }}
              complexSuggestions={complexSuggestions}
              onSelectComplexSuggestion={(suggestion) => {
                setComplexQuery(suggestion.name);
                setSelectedComplex(suggestion);
                setSelectedRegion(suggestion.regionValue);
              }}
              onResetSelectedComplex={() => {
                setComplexQuery("");
                setSelectedComplex(null);
                setSelectedRegion("");
              }}
              selectedComplex={selectedComplex}
              selectedRegion={selectedRegionOption}
            />
          ) : (
            <RegionRecordTab
              selectedRegionLabel={selectedRegionOption?.label ?? ""}
              onOpenRegionSheet={() => setIsRegionSheetOpen(true)}
              regionMemo={regionMemo}
              onChangeRegionMemo={setRegionMemo}
            />
          )}
        </FormContainer>
      </div>

      <BottomSheetSelect
        open={isRegionSheetOpen}
        title="지역 선택"
        description="기록할 지역을 선택해주세요"
        options={REGION_OPTIONS}
        value={selectedRegion}
        closeLabel="닫기"
        onSelect={(value) => {
          setSelectedRegion(value);
          setSelectedComplex(null);
          setComplexQuery("");
        }}
        onClose={() => setIsRegionSheetOpen(false)}
      />
    </>
  );
}
