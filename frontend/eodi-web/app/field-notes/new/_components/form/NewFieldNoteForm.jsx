"use client";

import { useEffect, useMemo, useState } from "react";
import ComplexRecordTab from "@/app/field-notes/new/_components/tab/ComplexRecordTab";
import RegionRecordTab from "@/app/field-notes/new/_components/tab/RegionRecordTab";
import FormHeader from "@/components/layout/FormHeader";
import FormContainer from "@/components/layout/FormContainer";
import Select from "@/components/ui/Select";
import SelectionSearchSheet from "@/app/field-notes/new/_components/section-sheet/SelectionSearchSection";

const REGION_OPTIONS = [
  {
    value: "nowon-sanggye",
    label: "서울 노원구 상계동",
    title: "상계동",
    subtitle: "서울 노원구",
    meta: "서울 노원구 상계동",
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
    title: "아현동",
    subtitle: "서울 마포구",
    meta: "서울 마포구 아현동",
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
    title: "영통구",
    subtitle: "경기 수원시",
    meta: "경기 수원시 영통구",
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
  { value: "complex", label: "단지 기록", description: "단지를 선택하고 핵심 기록을 남겨요" },
  { value: "region", label: "지역 기록", description: "지역을 선택하고 메모를 남겨요" },
];

const RECOMMENDED_REGION_VALUES = ["nowon-sanggye", "mapo-ahyeon", "suwon-yeongtong"];

function mapRegionToSheetItem(region) {
  return {
    key: region.value,
    title: region.title ?? region.label,
    subtitle: region.subtitle ?? "",
    meta: region.meta ?? region.label,
    raw: region,
  };
}

function mapComplexToSheetItem(complex) {
  return {
    key: complex.id,
    title: complex.name,
    subtitle: complex.address,
    meta: complex.regionLabel || complex.meta || "",
    raw: complex,
  };
}

function useDebouncedValue(value, delay) {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    if (!value.trim()) {
      setDebouncedValue("");
      return undefined;
    }

    const timer = window.setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    return () => {
      window.clearTimeout(timer);
    };
  }, [delay, value]);

  return debouncedValue;
}

export default function NewFieldNoteForm() {
  const [recordType, setRecordType] = useState("complex");
  const [selectedComplex, setSelectedComplex] = useState(null);
  const [autoFilledRegion, setAutoFilledRegion] = useState(null);
  const [selectedRegionValue, setSelectedRegionValue] = useState("");
  const [regionMemo, setRegionMemo] = useState("");
  const [isRegionSheetOpen, setIsRegionSheetOpen] = useState(false);
  const [isComplexSheetOpen, setIsComplexSheetOpen] = useState(false);
  const [regionSearchQuery, setRegionSearchQuery] = useState("");
  const [complexSearchQuery, setComplexSearchQuery] = useState("");
  const [regionSearchResults, setRegionSearchResults] = useState([]);
  const [complexSearchResults, setComplexSearchResults] = useState([]);
  const [isRegionSearching, setIsRegionSearching] = useState(false);
  const [isComplexSearching, setIsComplexSearching] = useState(false);
  const [recentRegionValues, setRecentRegionValues] = useState(
    RECOMMENDED_REGION_VALUES.slice(0, 2)
  );
  const [recentComplexIds, setRecentComplexIds] = useState([]);

  const debouncedRegionSearchQuery = useDebouncedValue(regionSearchQuery, 260);
  const debouncedComplexSearchQuery = useDebouncedValue(complexSearchQuery, 260);

  const currentRecordType = useMemo(
    () =>
      RECORD_TYPE_OPTIONS.find((option) => option.value === recordType) ?? RECORD_TYPE_OPTIONS[0],
    [recordType]
  );

  const selectedRegionOption = useMemo(
    () => REGION_OPTIONS.find((option) => option.value === selectedRegionValue) ?? null,
    [selectedRegionValue]
  );

  const selectedRegionSheetItem = useMemo(
    () => (selectedRegionOption ? mapRegionToSheetItem(selectedRegionOption) : null),
    [selectedRegionOption]
  );

  const flattenedComplexes = useMemo(
    () =>
      REGION_OPTIONS.flatMap((region) =>
        region.complexes.map((complex) => ({
          ...complex,
          regionValue: region.value,
          regionLabel: region.meta ?? region.label,
        }))
      ),
    []
  );

  const selectedComplexSheetItem = useMemo(
    () => (selectedComplex ? mapComplexToSheetItem(selectedComplex) : null),
    [selectedComplex]
  );

  const recentRegions = useMemo(
    () =>
      recentRegionValues
        .map((value) => REGION_OPTIONS.find((option) => option.value === value))
        .filter(Boolean)
        .map(mapRegionToSheetItem),
    [recentRegionValues]
  );

  const recommendedRegions = useMemo(
    () =>
      REGION_OPTIONS.filter(
        (option) =>
          RECOMMENDED_REGION_VALUES.includes(option.value) &&
          !recentRegionValues.includes(option.value)
      ).map(mapRegionToSheetItem),
    [recentRegionValues]
  );

  const recentComplexes = useMemo(
    () =>
      recentComplexIds
        .map((id) => flattenedComplexes.find((complex) => complex.id === id))
        .filter(Boolean)
        .map(mapComplexToSheetItem),
    [flattenedComplexes, recentComplexIds]
  );

  const recommendedComplexes = useMemo(() => {
    const priorityRegionValues =
      recentRegionValues.length > 0 ? recentRegionValues : RECOMMENDED_REGION_VALUES;

    return flattenedComplexes
      .filter((complex) => priorityRegionValues.includes(complex.regionValue))
      .filter((complex) => !recentComplexIds.includes(complex.id))
      .slice(0, 6)
      .map(mapComplexToSheetItem);
  }, [flattenedComplexes, recentComplexIds, recentRegionValues]);

  useEffect(() => {
    const keyword = debouncedRegionSearchQuery.trim().toLowerCase();

    if (!keyword) {
      setRegionSearchResults([]);
      setIsRegionSearching(false);
      return undefined;
    }

    setIsRegionSearching(true);

    const timer = window.setTimeout(() => {
      const nextResults = REGION_OPTIONS.filter((option) => {
        const searchable = [option.label, option.title, option.subtitle, option.meta]
          .filter(Boolean)
          .join(" ")
          .toLowerCase();

        return searchable.includes(keyword);
      }).map(mapRegionToSheetItem);

      setRegionSearchResults(nextResults);
      setIsRegionSearching(false);
    }, 140);

    return () => {
      window.clearTimeout(timer);
    };
  }, [debouncedRegionSearchQuery]);

  useEffect(() => {
    const keyword = debouncedComplexSearchQuery.trim().toLowerCase();

    if (!keyword) {
      setComplexSearchResults([]);
      setIsComplexSearching(false);
      return undefined;
    }

    setIsComplexSearching(true);

    const timer = window.setTimeout(() => {
      const nextResults = flattenedComplexes
        .filter((complex) => {
          const searchable = [complex.name, complex.address, complex.regionLabel, complex.meta]
            .filter(Boolean)
            .join(" ")
            .toLowerCase();

          return searchable.includes(keyword);
        })
        .map(mapComplexToSheetItem);

      setComplexSearchResults(nextResults);
      setIsComplexSearching(false);
    }, 140);

    return () => {
      window.clearTimeout(timer);
    };
  }, [debouncedComplexSearchQuery, flattenedComplexes]);

  const rememberRegion = (value) => {
    setRecentRegionValues((prev) => [value, ...prev.filter((item) => item !== value)].slice(0, 4));
  };

  const rememberComplex = (complexId) => {
    setRecentComplexIds((prev) =>
      [complexId, ...prev.filter((item) => item !== complexId)].slice(0, 4)
    );
  };

  const resetComplexSelection = () => {
    setSelectedComplex(null);
    setAutoFilledRegion(null);
    setSelectedRegionValue("");
    setComplexSearchQuery("");
    setComplexSearchResults([]);
    setIsComplexSearching(false);
  };

  const handleClearRegion = () => {
    setSelectedRegionValue("");
    setAutoFilledRegion(null);
    setRegionSearchQuery("");
    setRegionSearchResults([]);
    setIsRegionSearching(false);
  };

  const handleClearComplex = () => {
    setSelectedComplex(null);
    setAutoFilledRegion(null);
    setSelectedRegionValue("");
    setComplexSearchQuery("");
    setComplexSearchResults([]);
    setIsComplexSearching(false);
  };

  const handleSelectComplexRecord = () => {
    setRecordType("complex");
    resetComplexSelection();
    setRegionMemo("");
  };

  const handleSelectRegionRecord = () => {
    setRecordType("region");
    resetComplexSelection();
    setSelectedRegionValue("");
    setAutoFilledRegion(null);
  };

  const handleChangeRecordType = (value) => {
    if (value === "region") {
      handleSelectRegionRecord();
      return;
    }

    handleSelectComplexRecord();
  };

  const handleOpenRegionSheet = () => {
    setRegionSearchQuery("");
    setRegionSearchResults([]);
    setIsRegionSearching(false);
    setIsRegionSheetOpen(true);
  };

  const handleOpenComplexSheet = () => {
    setComplexSearchQuery("");
    setComplexSearchResults([]);
    setIsComplexSearching(false);
    setIsComplexSheetOpen(true);
  };

  const handleSelectRegion = (item) => {
    const region = item.raw;

    setSelectedRegionValue(region.value);
    setAutoFilledRegion(null);
    rememberRegion(region.value);
    setIsRegionSheetOpen(false);
    setRegionSearchQuery("");
    setRegionSearchResults([]);
    setIsRegionSearching(false);
  };

  const handleSelectComplex = (item) => {
    const complex = item.raw;
    const matchedRegion =
      REGION_OPTIONS.find((option) => option.value === complex.regionValue) ?? null;

    setSelectedComplex(complex);
    setSelectedRegionValue(matchedRegion?.value ?? "");
    setAutoFilledRegion(matchedRegion);
    rememberComplex(complex.id);

    if (matchedRegion) {
      rememberRegion(matchedRegion.value);
    }

    setIsComplexSheetOpen(false);
    setComplexSearchQuery("");
    setComplexSearchResults([]);
    setIsComplexSearching(false);
  };

  return (
    <>
      <div className="mx-auto flex w-full max-w-3xl flex-col gap-8 px-4 pb-16 pt-24 sm:px-6 sm:pt-28 lg:px-8 lg:pt-32">
        <FormHeader value="무엇을 기록할까요?" />

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
              selectedComplex={selectedComplex}
              selectedRegion={autoFilledRegion ?? selectedRegionOption}
              onOpenComplexSheet={handleOpenComplexSheet}
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

      <SelectionSearchSheet
        open={isRegionSheetOpen}
        title="지역 선택"
        description="지역명을 검색해 선택하세요"
        searchLabel="지역 검색"
        searchValue={regionSearchQuery}
        onSearchChange={setRegionSearchQuery}
        searchPlaceholder="지역명을 입력하세요"
        recentTitle="최근 선택 지역"
        recentItems={recentRegions}
        recommendedTitle="추천 지역"
        recommendedItems={recommendedRegions}
        searchResults={regionSearchResults}
        isSearching={isRegionSearching}
        emptyDescription="시, 구, 동 이름으로 다시 검색해보세요."
        selectedKey={selectedRegionValue}
        selectedItem={selectedRegionSheetItem}
        onSelect={handleSelectRegion}
        onDeselect={handleClearRegion}
        onClose={() => setIsRegionSheetOpen(false)}
      />

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
        onSelect={handleSelectComplex}
        onDeselect={handleClearComplex}
        onClose={() => setIsComplexSheetOpen(false)}
      />
    </>
  );
}
