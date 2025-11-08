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

// url: "https://new.land.naver.com/complexes?ms=노원구 중계동 아파트",
const id = "result";

export default function DealListPage() {
  const title = "선택한 지역의 실거래 내역을 찾았어요";
  const description = ["최근 3개월간의 실거래 데이터를 기준으로 표시됩니다."];
  const pageSize = 30;

  const [sellTotalCount, setSellTotalCount] = useState(0);
  const [sellTotalPages, setSellTotalPages] = useState(0);
  const [sellPage, setSellPage] = useState(0);
  const [sellList, setSellList] = useState([]);

  const {
    setCurrentContext,
    cash,
    selectedHousingTypes,
    selectedSellRegions,
    selectedLeaseRegions,
  } = useSearchStore();

  const [isFloatingCardOpen, setIsFloatingCardOpen] = useState(false);

  useEffect(() => {
    setCurrentContext(context[id]);

    console.log(Array.from(selectedSellRegions));
    api
      .get("/real-estate/recommendation/sells", {
        cash: cash,
        targetRegionIds: Array.from(selectedSellRegions).map((region) => region.id),
        targetHousingTypes: Array.from(selectedHousingTypes),
      })
      .then((res) => {
        console.log(res);
        setSellList(res.content);
        setSellTotalCount(res.totalElements);
        setSellPage(res.page);
      });
  }, []);

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

      {/* Grid */}
      <ResultGrid>
        {sellList.map((sell) => (
          <ResultCard key={sell.id} data={sell} />
        ))}
      </ResultGrid>
    </main>
  );
}
