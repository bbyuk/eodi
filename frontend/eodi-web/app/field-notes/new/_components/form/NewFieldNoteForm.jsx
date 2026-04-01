"use client";

import { useMemo, useState } from "react";
import BottomSheetSelect from "@/components/ui/BottomSheetSelect";
import ComplexRecordTab from "@/app/field-notes/new/_components/tab/ComplexRecordTab";
import RegionRecordTab from "@/app/field-notes/new/_components/tab/RegionRecordTab";
import FormHeader from "@/components/layout/FormHeader";
import FormContainer from "@/components/layout/FormContainer";

const REGION_OPTIONS = [
  {
    value: "nowon-sanggye",
    label: "서울 노원구 상계동",
    complexes: [
      { id: "sg-1", name: "상계주공 7단지", meta: "서울 노원구 상계동" },
      { id: "sg-2", name: "상계주공 10단지", meta: "서울 노원구 상계동" },
      { id: "sg-3", name: "보람아파트", meta: "서울 노원구 상계동" },
    ],
  },
  {
    value: "mapo-ahyeon",
    label: "서울 마포구 아현동",
    complexes: [
      { id: "ah-1", name: "마포래미안푸르지오", meta: "서울 마포구 아현동" },
      { id: "ah-2", name: "공덕자이", meta: "서울 마포구 아현동" },
      { id: "ah-3", name: "마포자이더센트리지", meta: "서울 마포구 아현동" },
    ],
  },
  {
    value: "suwon-yeongtong",
    label: "경기 수원시 영통구",
    complexes: [
      { id: "yw-1", name: "광교중흥S클래스", meta: "경기 수원시 영통구" },
      { id: "yw-2", name: "광교자연앤자이", meta: "경기 수원시 영통구" },
      { id: "yw-3", name: "e편한세상광교", meta: "경기 수원시 영통구" },
    ],
  },
];

const DEFAULT_SELECTED_REGION = REGION_OPTIONS[0];
const DEFAULT_SELECTED_COMPLEX = {
  ...DEFAULT_SELECTED_REGION.complexes[0],
  regionValue: DEFAULT_SELECTED_REGION.value,
  regionLabel: DEFAULT_SELECTED_REGION.label,
};

export default function NewFieldNoteForm() {
  const [recordType, setRecordType] = useState("complex");
  const [isRegionSheetOpen, setIsRegionSheetOpen] = useState(false);
  const [selectedRegion, setSelectedRegion] = useState(DEFAULT_SELECTED_REGION.value);
  const [complexQuery, setComplexQuery] = useState(DEFAULT_SELECTED_COMPLEX.name);
  const [selectedComplex, setSelectedComplex] = useState(DEFAULT_SELECTED_COMPLEX);
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

  return (
    <>
      <div className="mx-auto flex w-full max-w-3xl flex-col gap-8 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
        <FormHeader value={"무엇을 기록할까요?"} />

        <FormContainer>
          <div className="grid grid-cols-2 gap-3">
            <button
              type="button"
              onClick={() => {
                setRecordType("complex");
                setSelectedRegion(DEFAULT_SELECTED_REGION.value);
                setComplexQuery(DEFAULT_SELECTED_COMPLEX.name);
                setSelectedComplex(DEFAULT_SELECTED_COMPLEX);
                setRegionMemo("");
              }}
              className={`rounded-[1.25rem] border px-4 py-4 text-sm font-semibold transition ${
                recordType === "complex"
                  ? "border-slate-950 bg-slate-950 text-white"
                  : "border-slate-200 bg-slate-50 text-slate-700 hover:border-slate-300 hover:bg-white"
              }`}
            >
              단지 기록
            </button>
            <button
              type="button"
              onClick={() => {
                setRecordType("region");
                setSelectedRegion("");
                setComplexQuery("");
                setSelectedComplex(null);
              }}
              className={`rounded-[1.25rem] border px-4 py-4 text-sm font-semibold transition ${
                recordType === "region"
                  ? "border-slate-950 bg-slate-950 text-white"
                  : "border-slate-200 bg-slate-50 text-slate-700 hover:border-slate-300 hover:bg-white"
              }`}
            >
              지역 기록
            </button>
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
