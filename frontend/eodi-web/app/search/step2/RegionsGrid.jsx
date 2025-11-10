"use client";

import { useEffect, useState } from "react";
import PageHeader from "@/components/ui/PageHeader";
import CategoryTab from "@/components/ui/input/CategoryTab";
import MultiButtonSelectGrid from "@/app/search/_components/MultiButtonSelectGrid";
import GridGroup from "@/app/search/_components/GridGroup";
import { redirect } from "next/navigation";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";
import { formatWon } from "@/app/search/_util/util";
import { api } from "@/lib/apiClient";
import HorizontalSwipeContainer from "@/components/ui/container/HorizontalSwipeContainer";
import FloatingContainer from "@/components/ui/container/floating/FloatingContainer";
import { CheckCircle2, CheckSquare } from "lucide-react";
import SelectedRegionsCardContents from "@/app/search/step2/_components/SelectedRegionsCardContents";
import { definedHousingType } from "@/const/code";

const id = "region";
export default function RegionsGrid() {
  const title = "살펴볼 만한 지역을 찾았어요";
  const description = [
    "입력하신 예산을 참고해 최근 실거래 데이터를 기반으로 산출한 결과이며,",
    "실제 매물 상황이나 시세는 시점에 따라 달라질 수 있습니다.",
  ];
  const {
    cash,
    setCurrentContext,
    resetStep2,
    inquiredHousingTypes,
    setInquiredHousingTypes,
    selectedSellRegions,
    toggleSellRegion,
    selectedLeaseRegions,
    toggleLeaseRegion,
  } = useSearchStore();

  const [selectedHousingTypes, setSelectedHousingTypes] = useState(new Set(["AP", "OF"]));
  const toggleHousingType = (value) => {
    const next = new Set(selectedHousingTypes);
    next.has(value) ? next.delete(value) : next.add(value);
    setSelectedHousingTypes(next);
  };
  const [isHousingTypeChanged, setIsHousingTypeChanged] = useState(false);

  const [isFloatingCardOpen, setIsFloatingCardOpen] = useState(false);
  const [sellRegionGroups, setSellRegionGroups] = useState({});
  const [sellRegions, setSellRegions] = useState([]);

  const [leaseRegionGroups, setLeaseRegionGroups] = useState({});
  const [leaseRegions, setLeaseRegions] = useState([]);

  const [selectedSellRegionGroup, setSelectedSellRegionGroup] = useState();
  const [selectedLeaseRegionGroup, setSelectedLeaseRegionGroup] = useState();

  const housingTypes = Object.entries(definedHousingType)
    .filter(([code, value]) => value.param)
    .map(([code, info]) => {
      return {
        code: code,
        displayName: info.name,
        icon: info.icon,
      };
    });

  const findRecommendedRegions = () => {
    api
      .get("/real-estate/recommendation/region", {
        cash: cash,
        housingTypes: Array.from(selectedHousingTypes),
      })
      .then((res) => {
        setSellRegionGroups(res.sellRegionGroups);
        setSellRegions(res.sellRegions);
        setLeaseRegionGroups(res.leaseRegionGroups);
        setLeaseRegions(res.leaseRegions);

        setInquiredHousingTypes(Array.from(selectedHousingTypes));
        setIsHousingTypeChanged(false);
      });
  };

  useEffect(() => {
    if (sellRegionGroups.length === 0) {
      return;
    }

    setSelectedSellRegionGroup(Object.keys(sellRegionGroups)[0]);
  }, [sellRegionGroups]);

  useEffect(() => {
    if (leaseRegionGroups.length === 0) {
      return;
    }

    setSelectedLeaseRegionGroup(Object.keys(leaseRegionGroups)[0]);
  }, [leaseRegionGroups]);

  useEffect(() => {
    if (!cash || cash === 0) {
      redirect("/search");
    }
    resetStep2();
    setCurrentContext(context[id]);

    findRecommendedRegions();
  }, []);

  useEffect(() => {
    setIsHousingTypeChanged(
      selectedHousingTypes.size !== inquiredHousingTypes.length ||
        inquiredHousingTypes.filter((type) => selectedHousingTypes.has(type)).length !==
          inquiredHousingTypes.length
    );
  }, [selectedHousingTypes]);

  const selectedCount = selectedSellRegions.size + selectedLeaseRegions.size;

  return (
    <section className="w-full px-8 pt-[1vh] pb-[5vh] overflow-x-hidden">
      <FloatingContainer
        isOpen={isFloatingCardOpen}
        close={() => setIsFloatingCardOpen(false)}
        open={() => setIsFloatingCardOpen(true)}
        buttonLabel={`선택 ${selectedCount}개`}
        buttonIcon={<CheckCircle2 size={16} />}
        cardLabel={"선택지역"}
        cardIcon={<CheckSquare size={16} className="text-primary" />}
      >
        <SelectedRegionsCardContents close={() => setIsFloatingCardOpen(false)} />
      </FloatingContainer>

      <PageHeader title={title} description={description}>
        <p className="text-base text-text-secondary mt-4">
          입력 예산:{" "}
          <span className="font-semibold text-text-primary">
            {cash ? `${formatWon(Number(cash)).toLocaleString()}` : "-"}
          </span>
        </p>
      </PageHeader>

      {/* 주택 유형 선택 영역 */}
      <GridGroup title={"주택 유형 선택"}>
        <CategoryTab
          iconClassName={"w-5 h-5"}
          list={housingTypes}
          value={selectedHousingTypes}
          type={"select"}
          onSelect={(value) => {
            if (selectedHousingTypes.size === 1 && selectedHousingTypes.has(value)) {
              return;
            }
            toggleHousingType(value);
          }}
        />

        {/* 재조회 버튼 (선택 변경 시만 등장) */}
        <div
          className={`transition-all duration-300 ${
            isHousingTypeChanged
              ? "opacity-100 translate-y-0"
              : "opacity-0 -translate-y-2 pointer-events-none"
          }`}
        >
          <button
            className="mt-3 inline-flex items-center gap-1 text-primary font-medium hover:text-primary/80"
            onClick={findRecommendedRegions}
          >
            이 유형으로 다시 조회하기
          </button>
        </div>
      </GridGroup>

      <GridGroup title={"최근 매매 거래 이력이 있는 지역"}>
        <HorizontalSwipeContainer fadeColor="#ffffff">
          <CategoryTab
            list={Object.values(sellRegionGroups)}
            value={selectedSellRegionGroup}
            onSelect={setSelectedSellRegionGroup}
          />
        </HorizontalSwipeContainer>
        <MultiButtonSelectGrid
          list={sellRegions[selectedSellRegionGroup]}
          selected={selectedSellRegions}
          onSelect={toggleSellRegion}
          placeholder={"예산에 맞는 지역을 찾지 못했어요."}
        />
      </GridGroup>

      <GridGroup title={"최근 임대차 거래 이력이 있는 지역"}>
        <HorizontalSwipeContainer fadeColor="#ffffff">
          <CategoryTab
            list={Object.values(leaseRegionGroups)}
            value={selectedLeaseRegionGroup}
            onSelect={setSelectedLeaseRegionGroup}
          />
        </HorizontalSwipeContainer>
        <MultiButtonSelectGrid
          list={leaseRegions[selectedLeaseRegionGroup]}
          selected={selectedLeaseRegions}
          onSelect={toggleLeaseRegion}
          placeholder={"예산에 맞는 지역을 찾지 못했어요."}
        />
      </GridGroup>
    </section>
  );
}
