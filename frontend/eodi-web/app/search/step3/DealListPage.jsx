"use client";

import { useState, useEffect } from "react";
import PageHeader from "@/components/ui/PageHeader";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { SlidersHorizontal } from "lucide-react";
import FloatingFilterCardContents from "@/app/search/step3/_components/FloatingFilterCardContents";
import { context } from "@/app/search/_const/context";
import { useSearchStore } from "@/app/search/store/searchStore";
import ResultGrid from "@/app/search/step3/_components/ResultGrid";
import ResultCard from "@/app/search/step3/_components/ResultCard";
import { api } from "@/lib/apiClient";
import { useInfiniteScroll } from "@/hooks/useInfiniteScroll";
import GridGroup from "@/app/search/_components/GridGroup";
import CategoryTab from "@/components/ui/input/CategoryTab";
import { useSearchContext } from "@/app/search/layout";

// url: "https://new.land.naver.com/complexes?ms=노원구 중계동 아파트",
const id = "result";

export default function DealListPage() {
  const title = "선택한 지역의 실거래 내역을 찾았어요";
  const description = ["최근 3개월간의 실거래 데이터를 기준으로 표시됩니다."];
  const pageSize = 30;
  const { goFirst } = useSearchContext();

  const [tabList, setTabList] = useState([]);

  const initialDealInfo = {
    totalCount: 0,
    totalPages: 0,
    page: 0,
    data: [],
    isLoading: false,
  };
  const [sellInfo, setSellInfo] = useState(initialDealInfo);

  const [leaseInfo, setLeaseInfo] = useState(initialDealInfo);

  const {
    setCurrentContext,
    cash,
    inquiredHousingTypes,
    selectedSellRegions,
    selectedLeaseRegions,
  } = useSearchStore();

  const [isFloatingCardOpen, setIsFloatingCardOpen] = useState(false);
  const [selectedTab, setSelectedTab] = useState(null);

  const findSell = (init) => {
    if (sellInfo.isLoading) {
      return;
    }

    if (init) {
      setSellInfo({ ...initialDealInfo, isLoading: true });
      window.scrollTo({top: 0})
    }
    else {
      setSellInfo((prev) => ({ ...prev, isLoading: true }));
    }

    api
      .get("/real-estate/recommendation/sells", {
        cash: cash,
        targetRegionIds: Array.from(selectedSellRegions).map((region) => region.id),
        targetHousingTypes: Array.from(inquiredHousingTypes),
        size: pageSize,
        page: init ? 0 : sellInfo.page,
      })
      .then((res) => {
        setSellInfo((prev) => ({
          ...prev,
          totalCount: res.totalElements,
          totalPages: res.totalPages,
          page: res.page + 1,
          data: init ? res.content : [...prev.data, ...res.content],
        }));
      })
      .finally(() => setSellInfo((prev) => ({ ...prev, isLoading: false })));
  };

  const findLease = (init) => {
    if (leaseInfo.isLoading) {
      return;
    }
    if (init) {
      setLeaseInfo({...initialDealInfo, isLoading: true});
      window.scrollTo({top: 0})
    }
    else {
      setLeaseInfo((prev) => ({ ...prev, isLoading: true }));
    }

    api
      .get("/real-estate/recommendation/leases", {
        cash: cash,
        targetRegionIds: Array.from(selectedLeaseRegions).map((region) => region.id),
        targetHousingTypes: Array.from(inquiredHousingTypes),
        size: pageSize,
        page: init ? 0 : leaseInfo.page,
      })
      .then((res) => {
        setLeaseInfo((prev) => ({
          ...prev,
          totalCount: res.totalElements,
          totalPages: res.totalPages,
          page: res.page + 1,
          data: init ? res.content : [...prev.data, ...res.content],
        }));
      })
      .finally(() => setLeaseInfo((prev) => ({ ...prev, isLoading: false })));
  };

  useEffect(() => {
    setCurrentContext(context[id]);
  }, []);

  useEffect(() => {
    if (!cash || cash === 0 || (selectedSellRegions.size === 0 && selectedLeaseRegions === 0)) {
      goFirst();
      return;
    }

    let tempTabs = [];

    if (selectedSellRegions.size > 0) {
      tempTabs.push({
        code: "sell",
        displayName: "매매",
      });
    }
    if (selectedLeaseRegions.size > 0) {
      tempTabs.push({
        code: "lease",
        displayName: "임대차",
      });
    }

    setTabList(tempTabs);
    setSelectedTab(tempTabs[0].code);
  }, [cash, selectedSellRegions, selectedLeaseRegions]);

  useEffect(() => {
    if (selectedTab === "sell") {
      findSell(true);
    } else if (selectedTab === "lease") {
      findLease(true);
    }
  }, [selectedTab]);

  const sellLoadMoreRef = useInfiniteScroll(findSell, sellInfo.page < sellInfo.totalPages);
  const leaseLoadMoreRef = useInfiniteScroll(findLease, leaseInfo.page < leaseInfo.totalPages);

  return (
    <main className="min-h-[80vh] max-w-6xl mx-auto px-6 py-12 relative">
      <FloatingContainer
        isOpen={isFloatingCardOpen}
        open={() => setIsFloatingCardOpen(true)}
        close={() => setIsFloatingCardOpen(false)}
        buttonLabel={"필터"}
        buttonIcon={<SlidersHorizontal size={16} />}
        cardLabel={"추가조건"}
        cardIcon={<SlidersHorizontal size={16} className="text-primary" />}
      >
        <FloatingFilterCardContents close={() => setIsFloatingCardOpen(false)} />
      </FloatingContainer>

      {/* Header */}
      <PageHeader title={title} description={description} />

      <GridGroup>
        <CategoryTab list={tabList} type={"toggle"} value={selectedTab} onSelect={setSelectedTab} />
        {/* Grid */}
        {selectedTab === "sell" ? (
          <ResultGrid>
            {sellInfo.data.map((sell) => (
              <ResultCard key={sell.id} data={sell} dealType={{ code: "sell", label: "매매" }} />
            ))}
            <div ref={sellLoadMoreRef} className={"h-6"} />
          </ResultGrid>
        ) : selectedTab === "lease" ? (
          <ResultGrid>
            {leaseInfo.data.map((lease) => (
              <ResultCard
                key={lease.id}
                data={lease}
                dealType={{
                  code: lease.monthlyRent === 0 ? "charter" : "monthly-rent",
                  label: lease.monthlyRent === 0 ? "전세" : "월세",
                }}
              />
            ))}
            <div ref={leaseLoadMoreRef} className={"h-6"} />
          </ResultGrid>
        ) : (
          <ResultGrid></ResultGrid>
        )}
      </GridGroup>
    </main>
  );
}
