import { useSearchStore } from "@/app/search/store/searchStore";
import { useMemo } from "react";

/**
 * 매매 <-> 임대차 탭 처리 로직 hook
 * @returns {*}
 */
export function useDealTabs() {
  const { selectedSellRegions, selectedLeaseRegions } = useSearchStore();

  return useMemo(() => {
    const tabs = [];
    if (selectedSellRegions.size > 0) {
      tabs.push({ code: "sell", displayName: "매매" });
    }
    if (selectedLeaseRegions.size > 0) {
      tabs.push({ code: "lease", displayName: "임대차" });
    }

    return tabs;
  }, [selectedSellRegions, selectedLeaseRegions]);
}
