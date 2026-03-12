import { useCallback, useMemo, useRef, useState } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { api } from "@/lib/apiClient";
import { useInfiniteScroll } from "@/hooks/useInfiniteScroll";

const PAGE_SIZE = 30;

const initialCriteria = {
  targetRegionIds: [],
  targetHousingTypes: [],
};

const initialInfo = {
  page: 0,
  totalPages: 0,
  hasNext: false,
  nextId: null,
  totalCount: 0,
  data: [],
  isLoading: false,
  isFetching: false,
  isFetchingMore: false,
};

export function useDealSearchQuery({ dealType, enabled }) {
  const { cash } = useSearchStore();

  const [info, setInfo] = useState(initialInfo);
  const criteriaRef = useRef(initialCriteria);

  const apiInfo = useMemo(
    () => ({
      sell: { url: "/real-estate/recommendation/sell" },
      lease: { url: "/real-estate/recommendation/lease" },
    }),
    []
  );

  const fetchData = useCallback(
    async ({ reset = false, criteria } = {}) => {
      if (!enabled) return;
      if (info.isLoading) return;

      const currentCriteria = criteria ?? criteriaRef.current;
      criteriaRef.current = currentCriteria;

      setInfo((prev) => ({
        ...(reset
          ? {
              ...initialInfo,
            }
          : prev),
        isLoading: true,
        isFetching: reset,
        isFetchingMore: !reset,
      }));

      try {
        const res = await api.get(apiInfo[dealType].url, {
          ...currentCriteria,
          cash,
          nextId: reset ? null : info.nextId,
          size: PAGE_SIZE,
          page: reset ? 0 : info.page,
        });

        setInfo((prev) => ({
          ...prev,
          page: (res.page ?? 0) + 1,
          totalPages: res.totalPages ?? 0,
          hasNext: Boolean(res.hasNext),
          nextId: res.nextId ?? null,
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
    [apiInfo, cash, dealType, enabled, info.isLoading, info.nextId, info.page]
  );

  const search = useCallback(
    async (criteria = initialCriteria) => {
      return fetchData({
        reset: true,
        criteria,
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
