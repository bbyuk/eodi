"use client";

import { useEffect } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { useSearchContext } from "@/app/search/layout";
import { useToast } from "@/components/ui/container/ToastProvider";
import { api } from "@/lib/apiClient";

import { context } from "@/app/search/_const/context";
import {
  useDealListPageVM,
  DEAL_TABS,
  MAX_REGION_SELECT_SIZE,
} from "@/app/search/step2/_hooks/useDealListPageVM";
import { useDealSearchQuery } from "@/app/search/step2/_hooks/useDealSearchQuery";
import { buildFilterParam, createInitialFilters } from "@/app/search/step2/config/dealFilterConfig";

const PAGE_ID = "result";

export function useDealListPageController() {
  const { goFirst } = useSearchContext();
  const { showToast } = useToast();
  const { setCurrentContext, currentContext, cash } = useSearchStore();

  const vm = useDealListPageVM({
    createInitialFilters,
    buildFilterParam,
  });

  const {
    state: {
      // ======= 매매 / 임대 tab ======
      selectedTab,
      // ======= 매매 / 임대 tab ======

      // ======= Floating button 필터 ======
      isFloatingFilterOpen,
      filterCountByDealType,
      // ======= Floating button 필터 ======

      // ======= 지역 선택 =======
      selectedSido,
      sidoOptions,
      sigunguOptions,
      selectedRegions,
      isSigunguLoading,
      // ======= 지역 선택 =======

      // ======= 주택유형 선택 =======
      housingTypeOptions,
      selectedHousingTypes,
      // ======= 주택유형 선택 =======
    },
    derived: { currentFilters, currentFilterParam, selectedRegionIds, filterItems },
    actions: {
      // ======= 매매 / 임대 tab ======
      setSelectedTab,
      // ======= 매매 / 임대 tab ======

      // ======= Floating button 필터 ======
      setIsFloatingFilterOpen,
      setCurrentTabFilterCount,
      // ======= Floating button 필터 ======

      // ======= 지역 선택 =======
      setSelectedSido,
      setSidoOptions,
      setSigunguOptions,
      setSelectedRegions,
      setIsSigunguLoading,
      updateCurrentFilter,
      getNextSelectedRegions,
      // ======= 지역 선택 =======

      // ======= 주택유형 선택 =======
      setHousingTypeOptions,
      setSelectedHousingTypes,
      // ======= 주택유형 선택 =======
    },
  } = vm;

  /**
   * ============ Query ============
   */
  const sellQuery = useDealSearchQuery({
    dealType: "sell",
    enabled: selectedTab === "sell",
  });
  const leaseQuery = useDealSearchQuery({
    dealType: "lease",
    enabled: selectedTab === "lease",
  });
  const activeQuery = selectedTab === "sell" ? sellQuery : leaseQuery;
  /**
   * ============ Query ============
   */

  const loadSidoOptions = async () => {
    const res = await api.get("/legal-dong/root");
    setSidoOptions(res.items ?? []);
    return res;
  };
  const loadSigunguOptions = async (sidoCode) => {
    setIsSigunguLoading(true);
    try {
      const res = await api.get("/legal-dong/region", { code: sidoCode });
      setSigunguOptions(res.items ?? []);
      return res;
    } finally {
      setIsSigunguLoading(false);
    }
  };

  const searchCurrent = async (override = {}) => {
    return activeQuery.search({
      targetRegions: override.targetRegions ?? selectedRegionIds,
      filterParam: override.filterParam ?? currentFilterParam,
    });
  };

  const handleChangeSido = async (value) => {
    setSelectedSido(value);
    setSelectedRegions(new Set());

    if (value === "all") {
      setSigunguOptions([]);
      await searchCurrent({
        targetRegions: [],
      });
      return;
    }

    const res = await loadSigunguOptions(value);
    const nextRegionIds = (res?.items ?? []).map((item) => item.id);

    await searchCurrent({
      targetRegions: nextRegionIds,
    });
  };

  const handleToggleRegion = async (regionId) => {
    const { next, blocked } = getNextSelectedRegions(regionId);

    if (blocked) {
      showToast?.({
        text: `지역은 최대 ${MAX_REGION_SELECT_SIZE}개까지 선택할 수 있어요.`,
        type: "warning",
      });
      return;
    }

    setSelectedRegions(next);

    const nextRegionIds =
      next.size === 0 ? sigunguOptions.map((item) => item.id) : Array.from(next);

    await searchCurrent({
      targetRegions: selectedSido === "all" ? [] : nextRegionIds,
    });
  };

  const loadHousingTypeOptions = async () => {
    console.log("지원 주택유형 목록 조회");
  };

  const handleSelectHousingType = async (housingTypeCode) => {
    console.log(housingTypeCode);
  };

  const applyFilters = async () => {
    const enabledCount = Object.values(currentFilters ?? {}).filter(
      (filter) => filter.enable
    ).length;

    await searchCurrent();

    setCurrentTabFilterCount(enabledCount);
    setIsFloatingFilterOpen(false);
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
    loadHousingTypeOptions();
  }, []);

  useEffect(() => {
    if (!cash || cash === 0) return;
    if (isSigunguLoading) return;

    searchCurrent();
  }, [selectedTab, cash, isSigunguLoading]);

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
      filters: filterItems.map((item) => ({
        ...item,
        setFilter: (updater) => updateCurrentFilter(item.key, updater),
      })),
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
      tabs: DEAL_TABS,
      selectedTab,
      onChangeTab: setSelectedTab,
    },

    resultSection: {
      type: selectedTab,
      info: activeQuery.info,
      loadMoreRef: activeQuery.loadMoreRef,
      isInitialLoading: activeQuery.info.isFetching,
      isFetchingMore: activeQuery.info.isFetchingMore,
    },

    housingTypeFilter: {
      housingTypeOptions,
      selectedHousingTypes,
      onSelectHousingType: handleSelectHousingType,
    },
  };
}
