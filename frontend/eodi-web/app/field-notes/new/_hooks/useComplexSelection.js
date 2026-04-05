"use client";

import { useEffect, useMemo, useState } from "react";
import {
  FLATTENED_COMPLEXES,
  mapComplexToSheetItem,
  RECOMMENDED_REGION_VALUES,
  REGION_OPTIONS,
} from "@/app/field-notes/new/_data/fieldNoteOptions";
import useDebouncedValue from "@/app/field-notes/new/_hooks/useDebouncedValue";

export default function useComplexSelection({ recentRegionValues = [], onSelectComplex } = {}) {
  const [selectedComplex, setSelectedComplex] = useState(null);
  const [isComplexSheetOpen, setIsComplexSheetOpen] = useState(false);
  const [complexSearchQuery, setComplexSearchQuery] = useState("");
  const [complexSearchResults, setComplexSearchResults] = useState([]);
  const [isComplexSearching, setIsComplexSearching] = useState(false);
  const [recentComplexIds, setRecentComplexIds] = useState([]);

  const debouncedComplexSearchQuery = useDebouncedValue(complexSearchQuery, 260);

  const selectedComplexSheetItem = useMemo(
    () => (selectedComplex ? mapComplexToSheetItem(selectedComplex) : null),
    [selectedComplex]
  );

  const recentComplexes = useMemo(
    () =>
      recentComplexIds
        .map((id) => FLATTENED_COMPLEXES.find((complex) => complex.id === id))
        .filter(Boolean)
        .map(mapComplexToSheetItem),
    [recentComplexIds]
  );

  const recommendedComplexes = useMemo(() => {
    const priorityRegionValues =
      recentRegionValues.length > 0 ? recentRegionValues : RECOMMENDED_REGION_VALUES;

    return FLATTENED_COMPLEXES.filter((complex) => priorityRegionValues.includes(complex.regionValue))
      .filter((complex) => !recentComplexIds.includes(complex.id))
      .slice(0, 6)
      .map(mapComplexToSheetItem);
  }, [recentComplexIds, recentRegionValues]);

  useEffect(() => {
    const keyword = debouncedComplexSearchQuery.trim().toLowerCase();

    if (!keyword) {
      setComplexSearchResults([]);
      setIsComplexSearching(false);
      return undefined;
    }

    setIsComplexSearching(true);

    const timer = window.setTimeout(() => {
      const nextResults = FLATTENED_COMPLEXES.filter((complex) => {
        const searchable = [complex.name, complex.address, complex.regionLabel, complex.meta]
          .filter(Boolean)
          .join(" ")
          .toLowerCase();

        return searchable.includes(keyword);
      }).map(mapComplexToSheetItem);

      setComplexSearchResults(nextResults);
      setIsComplexSearching(false);
    }, 140);

    return () => {
      window.clearTimeout(timer);
    };
  }, [debouncedComplexSearchQuery]);

  const rememberComplex = (complexId) => {
    setRecentComplexIds((prev) =>
      [complexId, ...prev.filter((item) => item !== complexId)].slice(0, 4)
    );
  };

  const resetComplexSearch = () => {
    setComplexSearchQuery("");
    setComplexSearchResults([]);
    setIsComplexSearching(false);
  };

  const openComplexSheet = () => {
    resetComplexSearch();
    setIsComplexSheetOpen(true);
  };

  const closeComplexSheet = () => {
    setIsComplexSheetOpen(false);
  };

  const selectComplex = (item) => {
    const complex = item.raw;
    const matchedRegion =
      REGION_OPTIONS.find((option) => option.value === complex.regionValue) ?? null;

    setSelectedComplex(complex);
    rememberComplex(complex.id);
    setIsComplexSheetOpen(false);
    resetComplexSearch();
    onSelectComplex?.(complex, matchedRegion);
  };

  const clearComplexSelection = () => {
    setSelectedComplex(null);
    resetComplexSearch();
  };

  return {
    selectedComplex,
    selectedComplexSheetItem,
    recentComplexes,
    recommendedComplexes,
    complexSearchQuery,
    setComplexSearchQuery,
    complexSearchResults,
    isComplexSearching,
    isComplexSheetOpen,
    openComplexSheet,
    closeComplexSheet,
    selectComplex,
    clearComplexSelection,
  };
}
