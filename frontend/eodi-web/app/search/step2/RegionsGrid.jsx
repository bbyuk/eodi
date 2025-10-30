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
import {
  BuildingOffice2Icon,
  BuildingOfficeIcon,
  HomeIcon,
  HomeModernIcon,
} from "@heroicons/react/24/outline";

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

  const [selectedHousingType, setSelectedHousingType] = useState(new Set(["AP", "OF"]));

  useEffect(() => {
    if (!cash || cash === 0) {
      redirect("/search");
    }

    setCurrentContext(context[id]);
    api
      .get("/real-estate/recommendation/region", {
        cash: cash,
        housingTypes: Array.from(selectedHousingType),
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
            {
              code: "AP",
              displayName: "아파트",
              icon: <BuildingOffice2Icon className="w-5 h-5" />,
            },
            {
              code: "OF",
              displayName: "오피스텔",
              icon: <BuildingOfficeIcon className="w-5 h-5" />,
            },
            { code: "DT", displayName: "단독 주택", icon: <HomeIcon className="w-5 h-5" /> },
            {
              code: "MH",
              displayName: "연립/다세대 주택",
              icon: <HomeModernIcon className="w-5 h-5" />,
            },
          ]}
          value={selectedHousingType}
          type={"select"}
          onSelect={(value) =>
            setSelectedHousingType((prev) => {
              const next = new Set(prev);
              next.has(value) ? next.delete(value) : next.add(value);
              return next;
            })
          }
        />
      </GridGroup>

      <GridGroup title={"최근 매수 이력이 있는 지역"}>
        <CategoryTab
          list={Object.values(sellRegionGroups)}
          value={selectedSellRegionGroup}
          onSelect={setSelectedSellRegionGroup}
        />
        <MultiButtonSelectGrid
          list={sellRegions[selectedSellRegionGroup?.code]}
          selected={selectedSellRegions}
          onSelect={toggleSellRegion}
          placeholder={"예산에 맞는 지역을 찾지 못했어요."}
        />
      </GridGroup>

      <GridGroup title={"최근 전·월세 이력이 있는 지역"}>
        <CategoryTab
          list={Object.values(leaseRegionGroups)}
          value={selectedLeaseRegionGroup}
          onSelect={setSelectedLeaseRegionGroup}
        />
        <MultiButtonSelectGrid
          list={leaseRegions[selectedLeaseRegionGroup?.code]}
          selected={selectedLeaseRegions}
          onSelect={toggleLeaseRegion}
          placeholder={"예산에 맞는 지역을 찾지 못했어요."}
        />
      </GridGroup>
    </section>
  );
}
