"use client";

import { useEffect, useState } from "react";
import PageHeader from "@/components/ui/PageHeader";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { SlidersHorizontal } from "lucide-react";
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
import { useToast } from "@/components/ui/container/ToastProvider";

const id = "result";
const title = "예산 기준으로 실거래 내역을 정리했어요";
const description = ["입력한 현금과 LTV를 기준으로 최근 3개월 거래를 표시합니다."];
const MAX_REGION_SELECT_SIZE = 5;
const limitWarnMessage = `이미 ${MAX_REGION_SELECT_SIZE}개의 지역을 모두 선택했어요.`;

export default function DealListPage() {
  const { goFirst } = useSearchContext();
  const { showToast } = useToast();
  const { setCurrentContext, cash } = useSearchStore();

  const tabs = useDealTabs();
  const [selectedTab, setSelectedTab] = useState(tabs[0]?.code);

  const sell = useDealSearch({ dealType: "sell", enabled: selectedTab === "sell" });
  const lease = useDealSearch({ dealType: "lease", enabled: selectedTab === "lease" });

  /**
   * ============= 편의 메서드 =================
   */
  const fetchDeals = () => {
    selectedTab === "sell" && sell.fetchInit(getSelectedRegions());
    selectedTab === "lease" && lease.fetchInit(getSelectedRegions());
  };

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
  const [sigunguLoad, setSigunguLoad] = useState(false);
  const [regionTable, setRegionTable] = useState({});
  useEffect(() => {
    // sido 변경시 기존 선택된 지역 목록 초기화
    setSigunguLoad(true);
    setSelectedRegions(() => new Set());
    // sido 변경시마다 sido에 포함된 시군구 조회
    api
      .get("/legal-dong/region", {
        code: selectedSido,
      })
      .then((res) => {
        setSigungu(res.items);
        setSigunguLoad(false);
        setRegionTable((prev) => ({
          ...prev,
          ...Object.fromEntries(res.items.map((i) => [i.code, i])),
        }));
      });
  }, [selectedSido]);

  const [selectedRegions, setSelectedRegions] = useState(() => new Set());

  const getSelectedRegions = () => {
    if (selectedRegions.size === 0) {
      return sigungu.map((elem) => elem.id);
    } else {
      return [...selectedRegions];
    }
  };

  useEffect(() => {
    !sigunguLoad && fetchDeals();
  }, [sigunguLoad, selectedRegions]);

  const toggleRegion = (value, e) => {
    setSelectedRegions((prev) => {
      const next = new Set(prev);

      if (next.has(value)) {
        next.delete(value);
      } else {
        if (next.size < MAX_REGION_SELECT_SIZE) {
          next.add(value);
        } else {
          showToast({ text: limitWarnMessage, type: "warning" });
        }
      }

      return next;
    });
  };

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
    fetchDeals();
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
      sell.fetchWithFilter(getSelectedRegions(), filterParam);
    } else if (selectedTab === "lease") {
      lease.fetchWithFilter(getSelectedRegions(), filterParam);
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
              onSelect={(value, e) => toggleRegion(value, e)}
              selected={selectedRegions}
              options={sigungu.map((el) => ({ value: el.id, label: el.displayName }))}
              placeholder={"전체"}
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
