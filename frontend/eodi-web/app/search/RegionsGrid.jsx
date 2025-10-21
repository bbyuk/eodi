"use client";

import { useEffect, useMemo, useState } from "react";
import PageHeader from "@/components/ui/PageHeader";
import ToggleButton from "@/components/ui/input/ToggleButton";
import CategoryButton from "@/components/ui/input/CategoryButton";

export default function RegionsGrid({ cash, onSelect }) {
  const title = "살펴볼 만한 지역을 찾았어요";
  const description = [
    "입력하신 예산을 참고해 최근 실거래 데이터를 기반으로 산출한 결과이며,",
    "실제 매물 상황이나 시세는 시점에 따라 달라질 수 있습니다.",
  ];

  const sellRegionData = DEFAULT_REGION_DATA.sell;
  const leaseRegionData = DEFAULT_REGION_DATA.lease;

  const [selectedCitySell, setSelectedCitySell] = useState(Object.keys(sellRegionData)[0]);
  const [selectedCityLease, setSelectedCityLease] = useState(Object.keys(leaseRegionData)[0]);

  const [selectedSell, setSelectedSell] = useState(new Set());
  const [selectedLease, setSelectedLease] = useState(new Set());

  const sellRegions = useMemo(() => sellRegionData[selectedCitySell] ?? [], [selectedCitySell]);
  const leaseRegions = useMemo(() => leaseRegionData[selectedCityLease] ?? [], [selectedCityLease]);

  const toggleRegion = (dealType, name) => {
    if (dealType === "sell") {
      setSelectedSell((prev) => {
        const next = new Set(prev);
        next.has(name) ? next.delete(name) : next.add(name);
        return next;
      });
    } else {
      setSelectedLease((prev) => {
        const next = new Set(prev);
        next.has(name) ? next.delete(name) : next.add(name);
        return next;
      });
    }
  };

  const handleSelectionChange = () => {
    onSelect?.(selectedSell, selectedLease);
  };

  useEffect(() => {
    if (onSelect) handleSelectionChange();
  }, [selectedSell, selectedLease]);

  /** 시/도 그룹 필터 (연한 컬러) */
  const renderCityTabs = (dealType, dataMap, selectedCity, setSelectedCity) => (
    <div className="flex flex-wrap gap-2 mb-5">
      {Object.keys(dataMap).map((city) => {
        const isActive = selectedCity === city;
        return (
          <CategoryButton
            key={city}
            onClick={() => setSelectedCity(city)}
            isActive={isActive}
            label={city}
          />
        );
      })}
    </div>
  );

  /** ✅ 시군구 grid (전체 페이지 스크롤에 맞게 확장) */
  const renderGrid = (dealType, regions) => (
    <div className="relative">
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
        {regions.map((name) => {
          const isActive =
            (dealType === "sell" && selectedSell.has(name)) ||
            (dealType === "lease" && selectedLease.has(name));
          return (
            <ToggleButton
              key={name}
              onClick={() => {
                toggleRegion(dealType, name);
                handleSelectionChange();
              }}
              size={"md"}
              isActive={isActive}
              label={name}
            />
          );
        })}

        {regions.length === 0 && (
          <div className="col-span-full py-6 text-center text-sm text-text-secondary border border-dashed border-border rounded-lg">
            선택한 시/도에 등록된 지역이 없습니다.
          </div>
        )}
      </div>
    </div>
  );

  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh] overflow-x-hidden">
      <PageHeader title={title} description={description}>
        <p className="text-base text-text-secondary mt-4">
          입력 예산:{" "}
          <span className="font-semibold text-text-primary">
            {cash ? `${Number(cash).toLocaleString()} 만 원` : "-"}
          </span>
        </p>
      </PageHeader>

      {/* 매수 가능한 지역 */}
      <section className="mb-14">
        <h2 className="text-xl font-semibold text-text-primary mb-4">매수 가능한 지역</h2>
        {renderCityTabs("sell", sellRegionData, selectedCitySell, setSelectedCitySell)}
        {renderGrid("sell", sellRegions)}
      </section>

      {/* 전월세 가능한 지역 */}
      <section>
        <h2 className="text-xl font-semibold text-text-primary mb-4">전·월세 가능한 지역</h2>
        {renderCityTabs("lease", leaseRegionData, selectedCityLease, setSelectedCityLease)}
        {renderGrid("lease", leaseRegions)}
      </section>
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
