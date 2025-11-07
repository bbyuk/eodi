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

const MOCK_DATA = [
  {
    id: 1,
    region: "강남구 삼성동",
    price: "18억 2,000만원",
    dealType: "매매",
    building: "래미안 삼성1차",
    area: "84㎡",
    floor: "15층",
    date: "2025.10.15",
    url: "https://new.land.naver.com/complexes?ms=강남구 삼성동 아파트",
  },
  {
    id: 2,
    region: "노원구 중계동",
    price: "6억 5,000만원",
    dealType: "매매",
    building: "중계주공3단지",
    area: "59㎡",
    floor: "8층",
    date: "2025.09.10",
    url: "https://new.land.naver.com/complexes?ms=노원구 중계동 아파트",
  },
];

const id = "result";

export default function DealListPage() {
  const title = "선택한 지역의 실거래 내역을 찾았어요";
  const description = ["최근 3개월간의 실거래 데이터를 기준으로 표시됩니다."];

  const { setCurrentContext } = useSearchStore();

  const [deals] = useState(MOCK_DATA);
  const [isFloatingCardOpen, setIsFloatingCardOpen] = useState(false);

  useEffect(() => {
    setCurrentContext(context[id]);
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
        {deals.map((deal) => (
          <ResultCard key={deal.id} data={deal} />
        ))}
      </ResultGrid>
    </main>
  );
}
