"use client";

import { useEffect, useMemo, useState } from "react";
import {
  mapRegionToSheetItem,
  RECOMMENDED_REGION_VALUES,
  REGION_OPTIONS,
} from "@/app/field-notes/new/_data/fieldNoteOptions";
import useDebouncedValue from "@/app/field-notes/new/_hooks/useDebouncedValue";

export default function useRegionSelection({ onSelectRegion, onClearRegion } = {}) {
  const [selectedRegionValue, setSelectedRegionValue] = useState("");
  const [isRegionSheetOpen, setIsRegionSheetOpen] = useState(false);
  const [regionSearchQuery, setRegionSearchQuery] = useState("");
  const [regionSearchResults, setRegionSearchResults] = useState([]);
  const [isRegionSearching, setIsRegionSearching] = useState(false);
  const [recentRegionValues, setRecentRegionValues] = useState(
    RECOMMENDED_REGION_VALUES.slice(0, 2)
  );

  const debouncedRegionSearchQuery = useDebouncedValue(regionSearchQuery, 260);

  const selectedRegionOption = useMemo(
    () => REGION_OPTIONS.find((option) => option.value === selectedRegionValue) ?? null,
    [selectedRegionValue]
  );

  const selectedRegionSheetItem = useMemo(
    () => (selectedRegionOption ? mapRegionToSheetItem(selectedRegionOption) : null),
    [selectedRegionOption]
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

  const rememberRegion = (value) => {
    setRecentRegionValues((prev) => [value, ...prev.filter((item) => item !== value)].slice(0, 4));
  };

  const resetRegionSearch = () => {
    setRegionSearchQuery("");
    setRegionSearchResults([]);
    setIsRegionSearching(false);
  };

  const openRegionSheet = () => {
    resetRegionSearch();
    setIsRegionSheetOpen(true);
  };

  const closeRegionSheet = () => {
    setIsRegionSheetOpen(false);
  };

  const selectRegion = (item) => {
    const region = item.raw;

    setSelectedRegionValue(region.value);
    rememberRegion(region.value);
    setIsRegionSheetOpen(false);
    resetRegionSearch();
    onSelectRegion?.(region);
  };

  const clearRegionSelection = () => {
    setSelectedRegionValue("");
    resetRegionSearch();
    onClearRegion?.();
  };

  return {
    selectedRegionValue,
    selectedRegionOption,
    selectedRegionSheetItem,
    recentRegionValues,
    recentRegions,
    recommendedRegions,
    regionSearchQuery,
    setRegionSearchQuery,
    regionSearchResults,
    isRegionSearching,
    isRegionSheetOpen,
    openRegionSheet,
    closeRegionSheet,
    selectRegion,
    clearRegionSelection,
    rememberRegion,
    setSelectedRegionValue,
  };
}
