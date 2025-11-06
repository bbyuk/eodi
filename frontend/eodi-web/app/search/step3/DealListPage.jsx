"use client";

import { useState, useEffect } from "react";
import PageHeader from "@/components/ui/PageHeader";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { SlidersHorizontal } from "lucide-react";
import FloatingFilterCardContents from "@/app/search/step3/_components/FloatingFilterCardContents";
import { context } from "@/app/search/_const/context";
import { useSearchStore } from "@/app/search/store/searchStore";

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
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {deals.map((deal) => (
          <article
            key={deal.id}
            className="border border-gray-200 rounded-xl bg-white/80 shadow-sm hover:shadow-md transition-all duration-300 p-5 flex flex-col justify-between"
          >
            <div>
              <h3 className="text-lg font-semibold text-gray-900">{deal.building}</h3>
              <p className="text-sm text-gray-500">{deal.region}</p>

              <div className="mt-3 space-y-1 text-sm">
                <p>
                  <span className="font-medium text-gray-800">{deal.dealType}</span> ·{" "}
                  <span className="text-gray-600">{deal.area}</span> ·{" "}
                  <span className="text-gray-600">{deal.floor}</span>
                </p>
                <p className="text-blue-600 font-semibold">{deal.price}</p>
                <p className="text-xs text-gray-400">{deal.date} 거래</p>
              </div>
            </div>

            <a
              href={deal.url}
              target="_blank"
              rel="noopener noreferrer"
              className="mt-5 w-full text-center py-2 rounded-md bg-blue-600 text-white font-medium text-sm hover:bg-blue-700 transition"
            >
              🔍 네이버 부동산에서 보기
            </a>
          </article>
        ))}
      </div>
    </main>
  );
}
