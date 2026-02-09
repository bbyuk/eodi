import {useSearchStore} from "@/app/search/store/searchStore";
import {useMemo} from "react";

/**
 * 매매 <-> 임대차 탭 처리 로직 hook
 * @returns {*}
 */
export function useDealTabs() {
  const { selectedSellRegions, selectedLeaseRegions } = useSearchStore();

  return useMemo(() => {
    return [
      { code: "sell", displayName: "매매" },
      { code: "lease", displayName: "임대차" },
    ];
  }, [selectedSellRegions, selectedLeaseRegions]);
}
