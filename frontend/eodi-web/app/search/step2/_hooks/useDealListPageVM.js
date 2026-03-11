"use client";

import { useMemo, useState } from "react";

export const DEAL_TABS = [
  { value: "sell", label: "매매" },
  { value: "lease", label: "임대차" },
];

export const MAX_REGION_SELECT_SIZE = 5;

export function useDealListPageVM({ createInitialFilters, buildFilterParam }) {
  const [selectedTab, setSelectedTab] = useState(DEAL_TABS[0]?.code ?? "sell");
  const [isFloatingFilterOpen, setIsFloatingFilterOpen] = useState(false);

  const [filtersByDealType, setFiltersByDealType] = useState(createInitialFilters());
  const [filterCountByDealType, setFilterCountByDealType] = useState({
    sell: 0,
    lease: 0,
  });

  const [selectedSido, setSelectedSido] = useState("all");
  const [sidoOptions, setSidoOptions] = useState([]);
  const [sigunguOptions, setSigunguOptions] = useState([]);
  const [selectedRegions, setSelectedRegions] = useState(new Set());
  const [isSigunguLoading, setIsSigunguLoading] = useState(false);
  const [housingTypeOptions, setHousingTypeOptions] = useState([]);
  const [selectedHousingTypes, setSelectedHousingTypes] = useState(new Set());

  const currentFilters = useMemo(() => {
    return filtersByDealType[selectedTab];
  }, [filtersByDealType, selectedTab]);

  const currentFilterParam = useMemo(() => {
    return buildFilterParam(currentFilters);
  }, [buildFilterParam, currentFilters]);

  const selectedRegionIds = useMemo(() => {
    if (selectedSido === "all") return [];

    if (selectedRegions.size === 0) {
      return sigunguOptions.map((item) => item.id);
    }

    return Array.from(selectedRegions);
  }, [selectedSido, selectedRegions, sigunguOptions]);

  const filterItems = useMemo(() => {
    return Object.entries(currentFilters ?? {}).map(([key, filter]) => ({
      key,
      filter,
      type: filter.type,
    }));
  }, [currentFilters]);

  const getNextSelectedRegions = (regionId) => {
    const next = new Set(selectedRegions);

    if (next.has(regionId)) {
      next.delete(regionId);
      return { next, blocked: false };
    }

    if (next.size >= MAX_REGION_SELECT_SIZE) {
      return { next: selectedRegions, blocked: true };
    }

    next.add(regionId);
    return { next, blocked: false };
  };

  const updateCurrentFilter = (filterKey, updater) => {
    setFiltersByDealType((prev) => {
      const prevFilter = prev[selectedTab][filterKey];
      const nextFilter = typeof updater === "function" ? updater(prevFilter) : updater;

      return {
        ...prev,
        [selectedTab]: {
          ...prev[selectedTab],
          [filterKey]: nextFilter,
        },
      };
    });
  };

  const setCurrentTabFilterCount = (count) => {
    setFilterCountByDealType((prev) => ({
      ...prev,
      [selectedTab]: count,
    }));
  };

  return {
    state: {
      selectedTab,
      isFloatingFilterOpen,
      filtersByDealType,
      filterCountByDealType,
      selectedSido,
      sidoOptions,
      sigunguOptions,
      selectedRegions,
      isSigunguLoading,
      housingTypeOptions,
      selectedHousingTypes,
    },

    derived: {
      currentFilters,
      currentFilterParam,
      selectedRegionIds,
      filterItems,
    },

    actions: {
      setSelectedTab,
      setIsFloatingFilterOpen,
      setFiltersByDealType,
      setFilterCountByDealType,
      setCurrentTabFilterCount,
      setSelectedSido,
      setSidoOptions,
      setSigunguOptions,
      setSelectedRegions,
      setIsSigunguLoading,
      updateCurrentFilter,
      getNextSelectedRegions,
      setHousingTypeOptions,
      setSelectedHousingTypes,
    },
  };
}
