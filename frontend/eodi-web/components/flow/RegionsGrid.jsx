/**
 * Step 2 - 지역 선택
 */
"use client";

import { useState } from "react";

export default function RegionsGrid({ cash, onSelect }) {
  const sellRegions = [
    "Lorem-dong A",
    "Lorem-dong B",
    "Lorem-dong C",
    "Lorem-dong D",
    "Lorem-dong E",
    "Lorem-dong F",
  ];
  const rentRegions = [
    "Ipsum-dong A",
    "Ipsum-dong B",
    "Ipsum-dong C",
    "Ipsum-dong D",
    "Ipsum-dong E",
    "Ipsum-dong F",
  ];

  const [selectedSell, setSelectedSell] = useState(new Set());
  const [selectedLease, setSelectedLease] = useState(new Set());

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

  // 선택 변경 시 부모로 통지
  const handleSelectionChange = () => {
    onSelect?.(selectedSell, selectedLease);
  };

  // 선택 변화 감지 (렌더 후 콜백)
  // useEffect로 호출해도 되지만 단순한 구조라 직접 핸들링 가능
  if (onSelect) handleSelectionChange();

  const renderGrid = (dealType, regions) => (
    <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
      {regions.map((name) => {
        const isActive =
          (dealType === "sell" && selectedSell.has(name)) ||
          (dealType === "lease" && selectedLease.has(name));
        return (
          <button
            key={name}
            type="button"
            onClick={() => {
              toggleRegion(dealType, name);
              handleSelectionChange();
            }}
            className={`w-full px-4 py-3 rounded-lg border text-left transition-all duration-200 ${
              isActive
                ? "bg-primary text-white border-primary shadow-md scale-[1.02]"
                : "border-border hover:bg-primary-bg hover:border-primary-light hover:text-primary"
            }`}
          >
            {name}
          </button>
        );
      })}
    </div>
  );

  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh]">
      {/* Header */}
      <header className="mb-14">
        <div className="max-w-3xl">
          <h1 className="text-3xl md:text-4xl font-semibold text-text-primary mb-3 leading-tight">
            최근 실거래 데이터를 기반으로 살펴볼 만한 지역이에요
          </h1>
          <p className="text-base text-text-secondary leading-relaxed">
            입력하신 예산을 참고해 산출한 결과이며,
            <br className="hidden sm:block" />
            실제 매물 상황이나 시세는 시점에 따라 달라질 수 있습니다.
          </p>
          <p className="text-base text-text-secondary mt-4">
            입력 예산:{" "}
            <span className="font-semibold text-text-primary">
              {cash ? `${Number(cash).toLocaleString()} 만 원` : "-"}
            </span>
          </p>
        </div>
      </header>

      {/* 매수 가능 */}
      <section className="mb-14">
        <h2 className="text-xl font-semibold text-text-primary mb-4">매수 가능한 지역</h2>
        {renderGrid("sell", sellRegions)}
      </section>

      {/* 전월세 가능 */}
      <section>
        <h2 className="text-xl font-semibold text-text-primary mb-4">전·월세 가능한 지역</h2>
        {renderGrid("lease", rentRegions)}
      </section>
    </section>
  );
}
