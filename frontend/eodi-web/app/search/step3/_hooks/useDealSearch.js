import { useSearchStore } from "@/app/search/store/searchStore";
import { api } from "@/lib/apiClient";
import { useInfiniteScroll } from "@/hooks/useInfiniteScroll";
import { useState } from "react";

/**
 * 실거래 데이터 조회 hook
 * @param dealType 거래 유형 'sell' || 'lease'
 * @param enabled
 * @returns {{info: *, fetchInit: (function(): void), loadMoreRef}}
 */
export function useDealSearch({ dealType, enabled }) {
  const pageSize = 30;
  const { cash, inquiredHousingTypes, selectedSellRegions, selectedLeaseRegions } =
    useSearchStore();

  const [info, setInfo] = useState({
    page: 0,
    totalPages: 0,
    hasNext: false,
    nextId: null,
    totalCount: 0,
    data: [],
    isLoading: false,
  });
  const [filterParam, setFilterParam] = useState({});

  const fetchData = (init = false, newFilterParam) => {
    if (!enabled || info.isLoading) return;
    setInfo((prev) => ({ ...(init ? { page: 0, data: [] } : prev), isLoading: true }));

    const apiInfo = {
      sell: {
        url: "/real-estate/recommendation/sells",
        targetRegions: selectedSellRegions,
      },
      lease: {
        url: "/real-estate/recommendation/leases",
        targetRegions: selectedLeaseRegions,
      },
    };

    if (init && newFilterParam) {
      setFilterParam(newFilterParam);
    }

    const currentFilterParam = newFilterParam ? newFilterParam : filterParam;

    // console.log(init && newFilterParam);
    api
      .get(apiInfo[dealType].url, {
        ...currentFilterParam,
        cash,
        targetRegionIds: Array.from(apiInfo[dealType].targetRegions).map((region) => region.id),
        targetHousingTypes: Array.from(inquiredHousingTypes),
        nextId: info.nextId,
        hasNext: info.hasNext,
        size: pageSize,
        page: init ? 0 : info.page,
      })
      .then((res) => {
        setInfo((prev) => ({
          ...prev,
          page: res.page + 1,
          hasNext: res.hasNext,
          nextId: res.nextId,
          totalPages: res.totalPages,
          totalCount: res.totalElements,
          data: init ? res.content : [...prev.data, ...res.content],
        }));
      })
      .finally(() => setInfo((prev) => ({ ...prev, isLoading: false })));
  };

  const loadMoreRef = useInfiniteScroll(fetchData, info.hasNext);

  return {
    info,
    fetchInit: () => fetchData(true),
    fetchWithFilter: (filterParam) => fetchData(true, filterParam),
    loadMoreRef,
  };
}
