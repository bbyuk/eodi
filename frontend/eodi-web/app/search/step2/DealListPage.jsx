"use client";

import PageHeader from "@/components/layout/PageHeader";
import GridGroup from "@/app/search/_components/GridGroup";
import InnerNavContainer from "@/components/layout/InnerNavContainer";
import FloatingContainer from "@/components/ui/container/FloatingContainer";
import { SlidersHorizontal } from "lucide-react";

import RegionFilterBar from "@/app/search/step2/_components/RegionFilterBar";
import DealResultSection from "@/app/search/step2/_components/DealResultSection";
import FloatingFilterCardContents from "@/app/search/step2/_components/FloatingFilterCardContents";
import { useDealListPageController } from "@/app/search/step2/_hooks/useDealListPageController";
import HousingTypeFilterBar from "@/app/search/step2/_components/HousingTypeFilterBar";
import RadioGroup from "@/components/ui/RadioGroup";

export default function DealListPage() {
  const controller = useDealListPageController();
  return (
    <main className="relative mx-auto min-h-[80vh] max-w-6xl px-0 py-0">
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
          <RadioGroup
            name={"deal-tabs"}
            value={controller.tabs.selectedTab}
            onChange={controller.tabs.onChangeTab}
            options={controller.tabs.tabs}
          />
        </InnerNavContainer>

        <InnerNavContainer>
          <HousingTypeFilterBar {...controller.housingTypeFilter} />
        </InnerNavContainer>

        <DealResultSection {...controller.resultSection} />
      </GridGroup>
    </main>
  );
}
