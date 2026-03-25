"use client";

import { useMemo, useState } from "react";
import BottomSheetSelect from "@/components/ui/input/BottomSheetSelect";
import ComplexRecordTab from "./_components/ComplexRecordTab";
import RegionRecordTab from "./_components/RegionRecordTab";

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

export default function NewFieldNoteForm() {
  const [recordType, setRecordType] = useState("complex");
  const [isRegionSheetOpen, setIsRegionSheetOpen] = useState(false);
  const [selectedRegion, setSelectedRegion] = useState("");
  const [complexQuery, setComplexQuery] = useState("");
  const [selectedComplex, setSelectedComplex] = useState(null);
  const [floor, setFloor] = useState("");
  const [askingPrice, setAskingPrice] = useState("");
  const [complexMemo, setComplexMemo] = useState("");
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
        <section className="space-y-2">
          <h1 className="text-3xl font-semibold tracking-tight text-slate-950 sm:text-4xl">
            무엇을 기록할까요?
          </h1>
        </section>

        <section className="rounded-[2rem] border border-slate-200 bg-white p-5 shadow-[0_24px_60px_rgba(15,23,42,0.06)] sm:p-6">
          <div className="space-y-6">
            <div className="grid grid-cols-2 gap-3">
              <button
                type="button"
                onClick={() => {
                  setRecordType("complex");
                  setSelectedRegion("");
                  setComplexQuery("");
                  setSelectedComplex(null);
                  setFloor("");
                  setAskingPrice("");
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
                  setComplexMemo("");
                  setFloor("");
                  setAskingPrice("");
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
                selectedRegionLabel={selectedRegionOption?.label ?? ""}
                floor={floor}
                onChangeFloor={setFloor}
                askingPrice={askingPrice}
                onChangeAskingPrice={setAskingPrice}
                complexMemo={complexMemo}
                onChangeComplexMemo={setComplexMemo}
              />
            ) : (
              <RegionRecordTab
                selectedRegionLabel={selectedRegionOption?.label ?? ""}
                onOpenRegionSheet={() => setIsRegionSheetOpen(true)}
                regionMemo={regionMemo}
                onChangeRegionMemo={setRegionMemo}
              />
            )}
          </div>
        </section>
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
