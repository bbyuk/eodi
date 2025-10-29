"use client";

import { useEffect, useMemo, useState } from "react";
import PageHeader from "@/components/ui/PageHeader";
import CategoryTab from "@/components/ui/input/CategoryTab";
import MultiButtonSelectGrid from "@/app/search/_components/MultiButtonSelectGrid";
import GridGroup from "@/app/search/_components/GridGroup";
import { redirect } from "next/navigation";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";
import { formatWon } from "@/app/search/_util/util";
import { api } from "@/lib/apiClient";

const id = "region";
export default function RegionsGrid({ onSelect }) {
  const title = "살펴볼 만한 지역을 찾았어요";
  const description = [
    "입력하신 예산을 참고해 최근 실거래 데이터를 기반으로 산출한 결과이며,",
    "실제 매물 상황이나 시세는 시점에 따라 달라질 수 있습니다.",
  ];
  const {
    cash,
    setCurrentContext,
    selectedSellRegions,
    toggleSellRegion,
    selectedLeaseRegions,
    toggleLeaseRegion,
  } = useSearchStore();

  const [sellRegionGroups, setSellRegionGroups] = useState({});
  const [sellRegions, setSellRegions] = useState([]);

  const [leaseRegionGroups, setLeaseRegionGroups] = useState({});
  const [leaseRegions, setLeaseRegions] = useState([]);

  const [selectedSellRegionGroup, setSelectedSellRegionGroup] = useState();
  const [selectedLeaseRegionGroup, setSelectedLeaseRegionGroup] = useState();

  const [selectedHousingType, setSelectedHousingType] = useState("아파트");

  useEffect(() => {
    if (!cash || cash === 0) {
      redirect("/search");
    }

    setCurrentContext(context[id]);
    api
      .get("/real-estate/recommendation/region", {
        cash: cash,
      })
      .then((res) => {
        console.log(res);
        setSellRegionGroups(res.sellRegionGroups);
        setSellRegions(res.sellRegions);
        setLeaseRegionGroups(res.leaseRegionGroups);
        setLeaseRegions(res.leaseRegions);
      });
  }, []);

  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh] overflow-x-hidden">
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
          list={[
            { displayName: "아파트", icon: "🏢" },
            { displayName: "오피스텔", icon: "🏬" },
            { displayName: "단독주택", icon: "🏠" },
            { displayName: "다가구", icon: "🏡" },
            { displayName: "연립·빌라", icon: "🏘️" },
          ]}
          value={selectedHousingType}
          onSelect={setSelectedHousingType}
        />
      </GridGroup>

      <GridGroup title={"최근 매수 이력이 있는 지역"}>
        <CategoryTab
          list={Object.values(sellRegionGroups)}
          value={selectedSellRegionGroup}
          onSelect={setSelectedSellRegionGroup}
        />
        {sellRegions[selectedSellRegionGroup?.code] && (
          <MultiButtonSelectGrid
            list={sellRegions[selectedSellRegionGroup?.code]}
            selected={selectedSellRegions}
            onSelect={toggleSellRegion}
          />
        )}
      </GridGroup>

      <GridGroup title={"최근 전·월세 이력이 있는 지역"}>
        <CategoryTab
          list={Object.values(leaseRegionGroups)}
          value={selectedLeaseRegionGroup}
          onSelect={setSelectedLeaseRegionGroup}
        />
        {leaseRegions[selectedSellRegionGroup?.code] && (
          <MultiButtonSelectGrid
            list={leaseRegions[selectedLeaseRegionGroup?.code]}
            selected={selectedLeaseRegions}
            onSelect={toggleLeaseRegion}
          />
        )}
      </GridGroup>
    </section>
  );
}
