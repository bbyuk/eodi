"use client";

import { useEffect, useMemo, useState } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { useSearchContext } from "@/app/search/layout";
import { useToast } from "@/components/ui/container/ToastProvider";
import { api } from "@/lib/apiClient";

import { context } from "@/app/search/_const/context";
import { useDealTabs } from "@/app/search/step2/_hooks/useDealTabs";
import { useDealSearch } from "@/app/search/step2/_hooks/useDealSearch";
import { buildFilterParam, createInitialFilters } from "@/app/search/step2/config/dealFilterConfig";

const PAGE_ID = "result";
const MAX_REGION_SELECT_SIZE = 5;

export function useDealListPageVM() {
  const { goFirst } = useSearchContext();
  const { showToast } = useToast();
  const { setCurrentContext, currentContext, cash } = useSearchStore();

  const tabs = useDealTabs();

  const [selectedTab, setSelectedTab] = useState(tabs[0]?.code ?? "sell");
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

  const sellSearch = useDealSearch({
    dealType: "sell",
    enabled: selectedTab === "sell",
  });

  const leaseSearch = useDealSearch({
    dealType: "lease",
    enabled: selectedTab === "lease",
  });

  const activeSearch = selectedTab === "sell" ? sellSearch : leaseSearch;
  const currentFilters = filtersByDealType[selectedTab];

  const currentFilterParam = useMemo(() => {
    return buildFilterParam(currentFilters);
  }, [currentFilters]);

  const selectedRegionIds = useMemo(() => {
    if (selectedSido === "all") return [];

    if (selectedRegions.size === 0) {
      return sigunguOptions.map((item) => item.id);
    }

    return Array.from(selectedRegions);
  }, [selectedSido, selectedRegions, sigunguOptions]);

  const loadSidoOptions = async () => {
    const res = await api.get("/legal-dong/root");
    setSidoOptions(res.items ?? []);
  };

  const loadSigunguOptions = async (sidoCode) => {
    setIsSigunguLoading(true);
    try {
      const res = await api.get("/legal-dong/region", { code: sidoCode });
      setSigunguOptions(res.items ?? []);
    } finally {
      setIsSigunguLoading(false);
    }
  };

  const handleChangeSido = async (value) => {
    setSelectedSido(value);
    setSelectedRegions(new Set());

    if (value === "all") {
      setSigunguOptions([]);
      return;
    }

    await loadSigunguOptions(value);
  };

  const handleToggleRegion = (regionId) => {
    setSelectedRegions((prev) => {
      const next = new Set(prev);

      if (next.has(regionId)) {
        next.delete(regionId);
        return next;
      }

      if (next.size >= MAX_REGION_SELECT_SIZE) {
        showToast?.(
          null,
          `지역은 최대 ${MAX_REGION_SELECT_SIZE}개까지 선택할 수 있어요.`,
          "warning"
        );
        return prev;
      }

      next.add(regionId);
      return next;
    });
  };

  const updateFilter = (filterKey, updater) => {
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

  const applyFilters = async () => {
    const enabledCount = Object.values(currentFilters ?? {}).filter(
      (filter) => filter.enable
    ).length;

    await activeSearch.fetchWithFilter(selectedRegionIds, currentFilterParam);

    setFilterCountByDealType((prev) => ({
      ...prev,
      [selectedTab]: enabledCount,
    }));

    setIsFloatingFilterOpen(false);
  };

  const fetchCurrentTab = async () => {
    await activeSearch.fetchWithFilter(selectedRegionIds, currentFilterParam);
  };

  useEffect(() => {
    setCurrentContext(context[PAGE_ID]);
  }, [setCurrentContext]);

  useEffect(() => {
    if ((!cash || cash === 0) && currentContext !== context.cash) {
      goFirst();
    }
  }, [cash, currentContext, goFirst]);

  useEffect(() => {
    loadSidoOptions();
  }, []);

  useEffect(() => {
    if (!cash || cash === 0) return;
    if (isSigunguLoading) return;

    fetchCurrentTab();
  }, [selectedTab, cash, isSigunguLoading]);

  const filterItems = Object.entries(currentFilters ?? {}).map(([key, filter]) => ({
    key,
    filter,
    setFilter: (updater) => updateFilter(key, updater),
    type: filter.type,
  }));

  return {
    page: {
      title: "예산 기준으로 실거래 내역을 정리했어요",
      description: ["입력한 현금과 LTV를 기준으로 최근 3개월 거래를 표시합니다."],
    },

    floatingFilter: {
      isOpen: isFloatingFilterOpen,
      open: () => setIsFloatingFilterOpen(true),
      close: () => setIsFloatingFilterOpen(false),
      activeCount: filterCountByDealType[selectedTab],
    },

    filterPanel: {
      close: () => setIsFloatingFilterOpen(false),
      apply: applyFilters,
      filters: filterItems,
    },

    regionFilter: {
      selectedSido,
      sidoOptions,
      sigunguOptions,
      selectedRegions,
      isSigunguLoading,
      onChangeSido: handleChangeSido,
      onToggleRegion: handleToggleRegion,
    },

    tabs: {
      tabs,
      selectedTab,
      onChangeTab: setSelectedTab,
    },

    resultSection: {
      type: selectedTab,
      info: activeSearch.info,
      loadMoreRef: activeSearch.loadMoreRef,
      isInitialLoading: activeSearch.info.isFetching,
      isFetchingMore: activeSearch.info.isFetchingMore,
    },
  };
}
