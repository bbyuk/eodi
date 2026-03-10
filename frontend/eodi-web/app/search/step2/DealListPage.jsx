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
import { useDealListPageController } from "@/app/search/step2/_hooks/useDealListPageController";
import HousingTypeFilterBar from "@/app/search/step2/_components/HousingTypeFilterBar";

export default function DealListPage() {
  const controller = useDealListPageController();
  return (
    <main className="min-h-[80vh] max-w-6xl mx-auto px-6 py-12 relative">
      <FloatingContainer
        isOpen={controller.floatingFilter.isOpen}
        open={controller.floatingFilter.open}
        close={controller.floatingFilter.close}
        buttonLabel="필터"
        buttonIcon={<SlidersHorizontal size={16} />}
        cardLabel="필터"
        cardIcon={<SlidersHorizontal size={16} className="text-primary" />}
        activeCount={controller.floatingFilter.activeCount}
      >
        <FloatingFilterCardContents {...controller.filterPanel} />
      </FloatingContainer>

      <PageHeader {...controller.page} />

      <GridGroup>
        <InnerNavContainer>
          <RegionFilterBar {...controller.regionFilter} />
        </InnerNavContainer>

        <InnerNavContainer>
          <HousingTypeFilterBar {...controller.housingTypeFilter} />
        </InnerNavContainer>

        <InnerNavContainer>
          <DealTypeTabs {...controller.tabs} />
        </InnerNavContainer>

        <DealResultSection {...controller.resultSection} />
      </GridGroup>
    </main>
  );
}
