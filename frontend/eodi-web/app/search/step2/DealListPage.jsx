"use client";

import PageHeader from "@/components/ui/PageHeader";
import GridGroup from "@/app/search/_components/GridGroup";
import InnerNavContainer from "@/components/layout/InnerNavContainer";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { SlidersHorizontal } from "lucide-react";

import RegionFilterBar from "@/app/search/step2/_components/RegionFilterBar";
import DealTypeTabs from "@/app/search/step2/_components/DealTypeTabs";
import DealResultSection from "@/app/search/step2/_components/DealResultSection";
import FloatingFilterCardContents from "@/app/search/step2/_components/FloatingFilterCardContents";

const title = "예산 기준으로 실거래 내역을 정리했어요";
const description = ["입력한 현금과 LTV를 기준으로 최근 3개월 거래를 표시합니다."];

export default function DealListPage() {
  return (
    <main className="min-h-[80vh] max-w-6xl mx-auto px-6 py-12 relative">
      <FloatingContainer
        isOpen={false}
        open={() => {}}
        close={() => {}}
        buttonLabel="필터"
        buttonIcon={<SlidersHorizontal size={16} />}
        cardLabel="필터"
        cardIcon={<SlidersHorizontal size={16} className="text-primary" />}
        activeCount={0}
      >
        <FloatingFilterCardContents close={() => {}} apply={() => {}} filters={[]} />
      </FloatingContainer>

      <PageHeader title={title} description={description} />

      <GridGroup>
        <InnerNavContainer>
          <RegionFilterBar
            selectedSido="all"
            sidoOptions={[]}
            sigunguOptions={[]}
            selectedRegions={new Set()}
            isSigunguLoading={false}
            onChangeSido={() => {}}
            onToggleRegion={() => {}}
          />
        </InnerNavContainer>

        <InnerNavContainer>
          <DealTypeTabs tabs={[]} selectedTab="sell" onChangeTab={() => {}} />
        </InnerNavContainer>

        <DealResultSection
          type="sell"
          items={[]}
          isInitialLoading={false}
          isFetchingMore={false}
          isEmpty={false}
          loadMoreRef={null}
        />
      </GridGroup>
    </main>
  );
}
