"use client";

import { useEffect, useMemo, useState } from "react";
import ComplexRecordTab from "@/app/field-notes/new/_components/tab/ComplexRecordTab";
import RegionRecordTab from "@/app/field-notes/new/_components/tab/RegionRecordTab";
import RegionSearchSheet from "@/app/field-notes/new/_components/form/RegionSearchSheet";
import FormHeader from "@/components/layout/FormHeader";
import FormContainer from "@/components/layout/FormContainer";
import Select from "@/components/ui/Select";

const REGION_OPTIONS = [
  {
    value: "nowon-sanggye",
    label: "서울 노원구 상계동",
    complexes: [
      {
        id: "sg-1",
        name: "상계주공 7단지",
        address: "서울시 노원구 동일로 1362",
        meta: "서울시 노원구 동일로 1362",
      },
      {
        id: "sg-2",
        name: "상계주공 10단지",
        address: "서울시 노원구 덕릉로 736",
        meta: "서울시 노원구 덕릉로 736",
      },
      {
        id: "sg-3",
        name: "보람아파트",
        address: "서울시 노원구 상계로 193",
        meta: "서울시 노원구 상계로 193",
      },
    ],
  },
  {
    value: "mapo-ahyeon",
    label: "서울 마포구 아현동",
    complexes: [
      {
        id: "ah-1",
        name: "마포래미안푸르지오",
        address: "서울시 마포구 마포대로 195",
        meta: "서울시 마포구 마포대로 195",
      },
      {
        id: "ah-2",
        name: "공덕자이",
        address: "서울시 마포구 백범로 152",
        meta: "서울시 마포구 백범로 152",
      },
      {
        id: "ah-3",
        name: "마포자이더센트리지",
        address: "서울시 마포구 신촌로 270",
        meta: "서울시 마포구 신촌로 270",
      },
    ],
  },
  {
    value: "suwon-yeongtong",
    label: "경기 수원시 영통구",
    complexes: [
      {
        id: "yw-1",
        name: "광교중흥S클래스",
        address: "경기도 수원시 영통구 광교호수로 300",
        meta: "경기도 수원시 영통구 광교호수로 300",
      },
      {
        id: "yw-2",
        name: "광교자연앤자이",
        address: "경기도 수원시 영통구 센트럴파크로 6",
        meta: "경기도 수원시 영통구 센트럴파크로 6",
      },
      {
        id: "yw-3",
        name: "e편한세상광교",
        address: "경기도 수원시 영통구 도청로 95",
        meta: "경기도 수원시 영통구 도청로 95",
      },
    ],
  },
];

const RECORD_TYPE_OPTIONS = [
  { value: "complex", label: "단지 기록", description: "단지 선택 후 상세 기록을 남겨요" },
  { value: "region", label: "지역 기록", description: "지역을 검색해 빠르게 메모해요" },
];

const RECOMMENDED_REGION_VALUES = ["nowon-sanggye", "mapo-ahyeon", "suwon-yeongtong"];

export default function NewFieldNoteForm() {
  const [recordType, setRecordType] = useState("complex");
  const [searchQuery, setSearchQuery] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [isSearching, setIsSearching] = useState(false);
  const [selectedComplex, setSelectedComplex] = useState(null);
  const [autoFilledRegion, setAutoFilledRegion] = useState(null);
  const [selectedRegionValue, setSelectedRegionValue] = useState("");
  const [isRegionSheetOpen, setIsRegionSheetOpen] = useState(false);
  const [regionSearchQuery, setRegionSearchQuery] = useState("");
  const [regionSearchResults, setRegionSearchResults] = useState([]);
  const [isRegionSearching, setIsRegionSearching] = useState(false);
  const [recentRegionValues, setRecentRegionValues] = useState(RECOMMENDED_REGION_VALUES.slice(0, 2));
  const [regionMemo, setRegionMemo] = useState("");

  const currentRecordType = useMemo(
    () => RECORD_TYPE_OPTIONS.find((option) => option.value === recordType) ?? RECORD_TYPE_OPTIONS[0],
    [recordType]
  );

  const selectedRegionOption = useMemo(
    () => REGION_OPTIONS.find((option) => option.value === selectedRegionValue) ?? null,
    [selectedRegionValue]
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

  const recentRegions = useMemo(
    () => recentRegionValues
      .map((value) => REGION_OPTIONS.find((option) => option.value === value))
      .filter(Boolean),
    [recentRegionValues]
  );

  const recommendedRegions = useMemo(
    () =>
      REGION_OPTIONS.filter(
        (option) =>
          RECOMMENDED_REGION_VALUES.includes(option.value) &&
          !recentRegionValues.includes(option.value)
      ),
    [recentRegionValues]
  );

  useEffect(() => {
    const keyword = searchQuery.trim().toLowerCase();

    if (!keyword || selectedComplex) {
      setSearchResults([]);
      setIsSearching(false);
      return undefined;
    }

    setIsSearching(true);

    const timer = window.setTimeout(() => {
      const nextResults = flattenedComplexes.filter(
        (complex) =>
          complex.name.toLowerCase().includes(keyword) ||
          complex.address.toLowerCase().includes(keyword) ||
          complex.regionLabel.toLowerCase().includes(keyword)
      );

      setSearchResults(nextResults);
      setIsSearching(false);
    }, 240);

    return () => {
      window.clearTimeout(timer);
    };
  }, [flattenedComplexes, searchQuery, selectedComplex]);

  useEffect(() => {
    const keyword = regionSearchQuery.trim().toLowerCase();

    if (!keyword) {
      setRegionSearchResults([]);
      setIsRegionSearching(false);
      return undefined;
    }

    setIsRegionSearching(true);

    const timer = window.setTimeout(() => {
      const nextResults = REGION_OPTIONS.filter((option) =>
        option.label.toLowerCase().includes(keyword)
      );

      setRegionSearchResults(nextResults);
      setIsRegionSearching(false);
    }, 220);

    return () => {
      window.clearTimeout(timer);
    };
  }, [regionSearchQuery]);

  const rememberRegion = (value) => {
    setRecentRegionValues((prev) => [value, ...prev.filter((item) => item !== value)].slice(0, 4));
  };

  const handleSelectComplexRecord = () => {
    setRecordType("complex");
    setSearchQuery("");
    setSearchResults([]);
    setIsSearching(false);
    setSelectedComplex(null);
    setAutoFilledRegion(null);
    setSelectedRegionValue("");
    setRegionMemo("");
  };

  const handleSelectRegionRecord = () => {
    setRecordType("region");
    setSearchQuery("");
    setSearchResults([]);
    setIsSearching(false);
    setSelectedComplex(null);
    setAutoFilledRegion(null);
    setSelectedRegionValue("");
  };

  const handleChangeRecordType = (value) => {
    if (value === "region") {
      handleSelectRegionRecord();
      return;
    }

    handleSelectComplexRecord();
  };

  const handleSelectComplexSuggestion = (suggestion) => {
    const matchedRegion = REGION_OPTIONS.find((option) => option.value === suggestion.regionValue) ?? null;

    setSelectedComplex(suggestion);
    setSearchQuery(suggestion.name);
    setSearchResults([]);
    setAutoFilledRegion(matchedRegion);
    setSelectedRegionValue(matchedRegion?.value ?? "");

    if (matchedRegion) {
      rememberRegion(matchedRegion.value);
    }
  };

  const handleResetSelectedComplex = () => {
    setSelectedComplex(null);
    setAutoFilledRegion(null);
    setSelectedRegionValue("");
    setSearchQuery("");
    setSearchResults([]);
    setIsSearching(false);
  };

  const handleOpenRegionSheet = () => {
    setRegionSearchQuery("");
    setRegionSearchResults([]);
    setIsRegionSearching(false);
    setIsRegionSheetOpen(true);
  };

  const handleSelectRegion = (value) => {
    setSelectedRegionValue(value);
    rememberRegion(value);
    setIsRegionSheetOpen(false);
    setRegionSearchQuery("");
    setRegionSearchResults([]);
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
              searchQuery={searchQuery}
              searchResults={searchResults}
              isSearching={isSearching}
              selectedComplex={selectedComplex}
              autoFilledRegion={autoFilledRegion}
              selectedRegion={selectedRegionOption}
              onChangeSearchQuery={(value) => {
                setSearchQuery(value);
                setSelectedComplex(null);
                setAutoFilledRegion(null);
                setSelectedRegionValue("");
              }}
              onSelectComplexSuggestion={handleSelectComplexSuggestion}
              onResetSelectedComplex={handleResetSelectedComplex}
              onOpenRegionSheet={handleOpenRegionSheet}
            />
          ) : (
            <RegionRecordTab
              selectedRegionLabel={selectedRegionOption?.label ?? ""}
              onOpenRegionSheet={handleOpenRegionSheet}
              regionMemo={regionMemo}
              onChangeRegionMemo={setRegionMemo}
            />
          )}
        </FormContainer>
      </div>

      <RegionSearchSheet
        open={isRegionSheetOpen}
        title={recordType === "complex" ? "지역 변경" : "지역 선택"}
        description={
          recordType === "complex"
            ? "단지를 선택하면 지역이 자동 입력됩니다. 필요할 때만 검색해서 바꿔주세요."
            : "기록할 지역명을 검색해서 빠르게 선택하세요."
        }
        searchQuery={regionSearchQuery}
        searchResults={regionSearchResults}
        isSearching={isRegionSearching}
        recentOptions={recentRegions}
        recommendedOptions={recommendedRegions}
        selectedValue={selectedRegionValue}
        onChangeSearchQuery={setRegionSearchQuery}
        onSelect={handleSelectRegion}
        onClose={() => setIsRegionSheetOpen(false)}
      />
    </>
  );
}
