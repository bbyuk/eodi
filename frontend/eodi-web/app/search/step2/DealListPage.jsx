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

export default function DealListPage() {
  const vm = useDealListPageController();
  return (
    <main className="min-h-[80vh] max-w-6xl mx-auto px-6 py-12 relative">
      <FloatingContainer
        isOpen={vm.floatingFilter.isOpen}
        open={vm.floatingFilter.open}
        close={vm.floatingFilter.close}
        buttonLabel="필터"
        buttonIcon={<SlidersHorizontal size={16} />}
        cardLabel="필터"
        cardIcon={<SlidersHorizontal size={16} className="text-primary" />}
        activeCount={vm.floatingFilter.activeCount}
      >
        <FloatingFilterCardContents {...vm.filterPanel} />
      </FloatingContainer>

      <PageHeader {...vm.page} />

      <GridGroup>
        <InnerNavContainer>
          <RegionFilterBar {...vm.regionFilter} />
        </InnerNavContainer>

        <InnerNavContainer>
          <DealTypeTabs {...vm.tabs} />
        </InnerNavContainer>

        <DealResultSection {...vm.resultSection} />
      </GridGroup>
    </main>
  );
}
