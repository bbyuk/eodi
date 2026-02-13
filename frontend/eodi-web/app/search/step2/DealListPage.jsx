"use client";

import { useEffect, useState } from "react";
import PageHeader from "@/components/ui/PageHeader";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { CheckCircle2, CheckSquare, SlidersHorizontal } from "lucide-react";
import FloatingFilterCardContents from "@/app/search/step2/_components/FloatingFilterCardContents";
import { context } from "@/app/search/_const/context";
import { useSearchStore } from "@/app/search/store/searchStore";
import GridGroup from "@/app/search/_components/GridGroup";
import { useSearchContext } from "@/app/search/layout";
import { useDealTabs } from "@/app/search/step2/_hooks/useDealTabs";
import { useDealSearch } from "@/app/search/step2/_hooks/useDealSearch";
import DealResultSection from "@/app/search/step2/_components/DealResultSection";
import { buildFilterParam, createInitialFilters } from "@/app/search/step2/config/dealFilterConfig";
import { api } from "@/lib/apiClient";
import Select from "@/components/ui/input/Select";
import InnerNavContainer from "@/components/layout/InnerNavContainer";
import CategoryButton from "@/components/ui/input/CategoryButton";
import ChipSelect from "@/components/ui/input/ChipSelect";
import SelectedRegionsCardContents from "@/app/search/step2/_components/SelectedRegionsCardContents";

const id = "result";
const title = "예산 기준으로 실거래 내역을 정리했어요";
const description = ["입력한 현금과 LTV를 기준으로 최근 3개월 거래를 표시합니다."];

export default function DealListPage() {
  const { goFirst } = useSearchContext();
  const { setCurrentContext, cash, selectedSellRegions, selectedLeaseRegions } = useSearchStore();

  const tabs = useDealTabs();
  const [selectedTab, setSelectedTab] = useState(tabs[0]?.code);

  const sell = useDealSearch({ dealType: "sell", enabled: selectedTab === "sell" });
  const lease = useDealSearch({ dealType: "lease", enabled: selectedTab === "lease" });

  /**
   * ============= region filter ====================
   */

  // 시도 선택 select
  const [selectedSido, setSelectedSido] = useState("all");
  const [sido, setSido] = useState([]);
  useEffect(() => {
    api.get("/legal-dong/root").then((res) => {
      setSido(res.items);
    });
  }, []);

  // 지역 선택 chip select
  const [sigungu, setSigungu] = useState([]);
  const [regionTable, setRegionTable] = useState({});
  useEffect(() => {
    api
      .get("/legal-dong/region", {
        code: selectedSido,
      })
      .then((res) => {
        setSigungu(res.items);
        setRegionTable((prev) => ({
          ...prev,
          ...Object.fromEntries(res.items.map((i) => [i.code, i])),
        }));
      });
  }, [selectedSido]);
  const [selectedRegions, setSelectedRegions] = useState(() => new Set());
  const toggleRegion = (value) => {
    setSelectedRegions((prev) => {
      const next = new Set(prev);

      if (next.has(value)) {
        next.delete(value);
      } else {
        next.add(value);
      }

      return next;
    });
  };
  useEffect(() => {
    if (selectedRegions.size === 0) {
      setIsSelectedRegionFloatingCardOpen(false);
    }
  }, [selectedRegions]);

  // 선택확인 floating container
  const [isSelectedRegionFloatingCardOpen, setIsSelectedRegionFloatingCardOpen] = useState(false);

  /**
   * ============= region filter ====================
   */

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
    if (!cash || cash === 0) {
      goFirst();
    }
  }, [cash]);

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

    if (selectedTab === "sell") {
      sell.fetchWithFilter(filterParam);
    } else if (selectedTab === "lease") {
      lease.fetchWithFilter(filterParam);
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

      {selectedRegions.size > 0 && (
        <FloatingContainer
          isOpen={isSelectedRegionFloatingCardOpen}
          close={() => setIsSelectedRegionFloatingCardOpen(false)}
          open={() => setIsSelectedRegionFloatingCardOpen(true)}
          buttonLabel={`지역 ${selectedRegions.size}개 선택`}
          buttonIcon={<CheckCircle2 size={16} />}
          cardLabel={"선택지역"}
          cardIcon={<CheckSquare size={16} className="text-primary" />}
          position={"left"}
        >
          <SelectedRegionsCardContents
            table={regionTable}
            selected={selectedRegions}
            onSelect={toggleRegion}
            close={() => setIsSelectedRegionFloatingCardOpen(false)}
          />
        </FloatingContainer>
      )}
      {/* Header */}
      <PageHeader title={title} description={description} />

      <GridGroup>
        <InnerNavContainer>
          <Select
            value={selectedSido}
            allOption
            width="w-[150px]"
            onChange={(value) => setSelectedSido(value)}
            options={sido.map((el) => ({ value: el.code, label: el.displayName }))}
          />
          {selectedSido && selectedSido !== "all" && (
            <ChipSelect
              width="w-[150px]"
              onSelect={toggleRegion}
              selected={selectedRegions}
              options={sigungu.map((el) => ({ value: el.code, label: el.displayName }))}
            />
          )}
        </InnerNavContainer>

        <InnerNavContainer>
          {tabs.map((data) => {
            const isActive = selectedTab === data.code;
            return (
              <CategoryButton
                key={data.code}
                icon={data.icon}
                onClick={() => setSelectedTab(data.code)}
                isActive={isActive}
                label={data.displayName}
              />
            );
          })}
        </InnerNavContainer>

        {selectedTab === "sell" && <DealResultSection {...sell} type={"sell"} />}
        {selectedTab === "lease" && <DealResultSection {...lease} type={"lease"} />}
      </GridGroup>
    </main>
  );
}
