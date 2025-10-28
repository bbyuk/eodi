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

  const sellRegionData = DEFAULT_REGION_DATA.sell;
  const leaseRegionData = DEFAULT_REGION_DATA.lease;

  const [selectedCitySell, setSelectedCitySell] = useState(Object.keys(sellRegionData)[0]);
  const [selectedCityLease, setSelectedCityLease] = useState(Object.keys(leaseRegionData)[0]);

  useEffect(() => {
    if (!cash || cash === 0) {
      redirect("/search");
    }

    setCurrentContext(context[id]);
    api
      .get("/real-estate/recommendation/region", {
        cash: cash,
      })
      .then((res) => console.log(res));
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

      <GridGroup title={"매수 가능한 지역"}>
        <CategoryTab
          list={sellRegionData}
          value={selectedCitySell}
          onSelect={setSelectedCitySell}
        />
        <MultiButtonSelectGrid
          list={sellRegionData[selectedCitySell]}
          selected={selectedSellRegions}
          onSelect={toggleSellRegion}
        />
      </GridGroup>

      <GridGroup title={"전·월세 가능한 지역"}>
        <CategoryTab
          list={leaseRegionData}
          value={selectedCityLease}
          onSelect={setSelectedCityLease}
        />
        <MultiButtonSelectGrid
          list={leaseRegionData[selectedCityLease]}
          selected={selectedLeaseRegions}
          onSelect={toggleLeaseRegion}
        />
      </GridGroup>
    </section>
  );
}

/** 테스트용 기본 데이터 */
const DEFAULT_REGION_DATA = {
  sell: {
    서울특별시: [
      "강남구",
      "송파구",
      "마포구",
      "용산구",
      "성동구",
      "광진구",
      "동대문구",
      "노원구",
      "도봉구",
      "은평구",
      "서초구",
      "강서구",
      "양천구",
      "중랑구",
      "구로구",
      "관악구",
      "금천구",
      "영등포구",
    ],
    부산광역시: ["해운대구", "수영구", "남구", "북구", "사하구", "연제구", "금정구", "동래구"],
    경기도: ["성남시", "용인시", "수원시", "화성시", "평택시", "안양시", "고양시", "남양주시"],
  },
  lease: {
    서울특별시: [
      "강남구",
      "성동구",
      "광진구",
      "은평구",
      "강서구",
      "영등포구",
      "서초구",
      "마포구",
      "송파구",
      "관악구",
      "중랑구",
    ],
    부산광역시: ["해운대구", "동래구", "북구", "남구", "사하구", "연제구", "금정구"],
    대구광역시: ["수성구", "중구", "달서구", "북구", "서구", "동구", "남구"],
  },
};
