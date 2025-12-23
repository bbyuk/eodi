"use client";

import { useEffect, useState } from "react";
import PageHeader from "@/components/ui/PageHeader";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { SlidersHorizontal } from "lucide-react";
import FloatingFilterCardContents from "@/app/search/step3/_components/FloatingFilterCardContents";
import { context } from "@/app/search/_const/context";
import { useSearchStore } from "@/app/search/store/searchStore";
import GridGroup from "@/app/search/_components/GridGroup";
import CategoryTab from "@/components/ui/input/CategoryTab";
import { useSearchContext } from "@/app/search/layout";
import { useDealTabs } from "@/app/search/step3/_hooks/useDealTabs";
import { useDealSearch } from "@/app/search/step3/_hooks/useDealSearch";
import DealResultSection from "@/app/search/step3/_components/DealResultSection";
import { buildFilterParam, createInitialFilters } from "@/app/search/step3/config/dealFilterConfig";

const id = "result";
const title = "선택한 지역의 실거래 내역을 찾았어요";
const description = ["최근 3개월간의 실거래 데이터를 기준으로 표시됩니다."];

export default function DealListPage() {
  const { goFirst } = useSearchContext();
  const { setCurrentContext, cash, selectedSellRegions, selectedLeaseRegions } = useSearchStore();

  const tabs = useDealTabs();
  const [selectedTab, setSelectedTab] = useState(tabs[0]?.code);

  const sell = useDealSearch({ dealType: "sell", enabled: selectedTab === "sell" });
  const lease = useDealSearch({ dealType: "lease", enabled: selectedTab === "lease" });

  /**
   * ============= Floating Filter ==================
   */
  const [isFloatingFilterCardOpen, setIsFloatingFilterCardOpen] = useState(false);

  const [filtersByDealType, setFiltersByDealType] = useState(createInitialFilters());
  const currentFilters = filtersByDealType[selectedTab];
  const updateFilter = (dealType, filterKey, updater) => {
    setFiltersByDealType((prev) => {
      const prevFilter = prev[dealType][filterKey];
      const nextFilter = typeof updater === "function" ? updater(prevFilter) : updater;

      return {
        ...prev,
        [dealType]: {
          ...prev[dealType],
          [filterKey]: nextFilter,
        },
      };
    });
  };

  const [filterApplied, setFilterApplied] = useState({ sell: true, lease: true });
  const [filterCount, setFilterCount] = useState({ sell: 0, lease: 0 });
  /**
   * ============= Floating Filter ==================
   */

  useEffect(() => {
    selectedTab === "sell" && sell.fetchInit();
    selectedTab === "lease" && lease.fetchInit();
  }, [selectedTab]);
  useEffect(() => {
    setCurrentContext(context[id]);
  }, []);
  useEffect(() => {
    if (
      !cash ||
      cash === 0 ||
      (selectedSellRegions.size === 0 && selectedLeaseRegions.size === 0)
    ) {
      goFirst();
    }
  }, [cash, selectedSellRegions, selectedLeaseRegions]);

  useEffect(() => {
    if (filterApplied[selectedTab]) return;

    applyFilter();
  }, [filterApplied[selectedTab]]);

  // 필터 처리
  const applyFilter = () => {
    const count = currentFilters
      ? Object.values(filtersByDealType[selectedTab]).filter((f) => f.enable).length
      : 0;

    const filterParam = buildFilterParam(currentFilters);

    console.log(filterParam);

    if (selectedTab === "sell") {
    } else if (selectedTab === "lease") {
      console.log(currentFilters);
    }

    setFilterCount((prev) => ({ ...prev, [selectedTab]: count }));
    setFilterApplied((prev) => ({ ...prev, [selectedTab]: true }));
  };

  return (
    <main className="min-h-[80vh] max-w-6xl mx-auto px-6 py-12 relative">
      <FloatingContainer
        isOpen={isFloatingFilterCardOpen}
        open={() => setIsFloatingFilterCardOpen(true)}
        close={() => setIsFloatingFilterCardOpen(false)}
        buttonLabel={"필터"}
        buttonIcon={<SlidersHorizontal size={16} />}
        cardLabel={"필터"}
        cardIcon={<SlidersHorizontal size={16} className="text-primary" />}
        activeCount={filterCount[selectedTab]}
      >
        <FloatingFilterCardContents
          close={() => setIsFloatingFilterCardOpen(false)}
          apply={() => {
            setFilterApplied((prev) => ({ ...prev, [selectedTab]: false }));
            setIsFloatingFilterCardOpen(false);
          }}
          filters={
            currentFilters &&
            Object.entries(currentFilters).map(([key, filter]) => ({
              key,
              filter,
              setFilter: (updater) => updateFilter(selectedTab, key, updater),
              type: filter.type,
            }))
          }
        />
      </FloatingContainer>

      {/* Header */}
      <PageHeader title={title} description={description} />

      <GridGroup>
        <CategoryTab list={tabs} type={"toggle"} value={selectedTab} onSelect={setSelectedTab} />
        {selectedTab === "sell" && <DealResultSection {...sell} type={"sell"} />}
        {selectedTab === "lease" && <DealResultSection {...lease} type={"lease"} />}
      </GridGroup>
    </main>
  );
}
