import { useCallback, useMemo, useRef, useState } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { api } from "@/lib/apiClient";
import { useInfiniteScroll } from "@/hooks/useInfiniteScroll";

/**
 * 실거래 데이터 조회 Query hook
 * - API 호출
 * - pagination
 * - infinite scroll
 * - loading 상태
 */
export function useDealSearchQuery({ dealType, enabled }) {
  const pageSize = 30;
  const { cash, inquiredHousingTypes } = useSearchStore();

  const [info, setInfo] = useState({
    page: 0,
    totalPages: 0,
    hasNext: false,
    nextId: null,
    totalCount: 0,
    data: [],
    isLoading: false,
    isFetching: false,
    isFetchingMore: false,
  });

  const criteriaRef = useRef({
    targetRegions: [],
    filterParam: {},
  });

  const apiInfo = useMemo(
    () => ({
      sell: {
        url: "/real-estate/recommendation/sells",
      },
      lease: {
        url: "/real-estate/recommendation/leases",
      },
    }),
    []
  );

  const fetchData = useCallback(
    async ({ reset = false, targetRegions, filterParam } = {}) => {
      if (!enabled) return;
      if (info.isLoading) return;

      const nextCriteria = {
        targetRegions:
          targetRegions !== undefined ? targetRegions : (criteriaRef.current.targetRegions ?? []),
        filterParam:
          filterParam !== undefined ? filterParam : (criteriaRef.current.filterParam ?? {}),
      };

      criteriaRef.current = nextCriteria;

      setInfo((prev) => ({
        ...(reset
          ? {
              page: 0,
              totalPages: 0,
              hasNext: false,
              nextId: null,
              totalCount: 0,
              data: [],
            }
          : prev),
        isLoading: true,
        isFetching: reset,
        isFetchingMore: !reset,
      }));

      try {
        const res = await api.get(apiInfo[dealType].url, {
          ...nextCriteria.filterParam,
          cash,
          targetRegionIds: nextCriteria.targetRegions,
          targetHousingTypes: Array.from(inquiredHousingTypes),
          nextId: reset ? null : info.nextId,
          size: pageSize,
          page: reset ? 0 : info.page,
        });

        setInfo((prev) => ({
          ...prev,
          page: (res.page ?? 0) + 1,
          hasNext: Boolean(res.hasNext),
          nextId: res.nextId ?? null,
          totalPages: res.totalPages ?? 0,
          totalCount: res.totalElements ?? 0,
          data: reset ? (res.content ?? []) : [...prev.data, ...(res.content ?? [])],
          isLoading: false,
          isFetching: false,
          isFetchingMore: false,
        }));

        return res;
      } catch (error) {
        setInfo((prev) => ({
          ...prev,
          isLoading: false,
          isFetching: false,
          isFetchingMore: false,
        }));
        throw error;
      }
    },
    [apiInfo, cash, dealType, enabled, inquiredHousingTypes, info.isLoading, info.nextId, info.page]
  );

  const search = useCallback(
    async ({ targetRegions = [], filterParam = {} } = {}) => {
      return fetchData({
        reset: true,
        targetRegions,
        filterParam,
      });
    },
    [fetchData]
  );

  const fetchMore = useCallback(async () => {
    return fetchData({ reset: false });
  }, [fetchData]);

  const loadMoreRef = useInfiniteScroll(fetchMore, enabled && info.hasNext && !info.isLoading);

  return {
    info,
    search,
    fetchMore,
    loadMoreRef,
  };
}
