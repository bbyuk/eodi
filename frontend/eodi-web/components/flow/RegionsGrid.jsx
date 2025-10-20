/**
 * Step 2 - 지역 선택
 */
"use client";

import { useState } from "react";

export default function RegionsGrid({ cash, onBack, onNext }) {
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
            onClick={() => toggleRegion(dealType, name)}
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

  const selectedCount = selectedSell.size + selectedLease.size;

  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh]">
      {/* Header */}
      <header className="mb-14">
        <div className="flex items-start justify-between">
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
          <button
            onClick={onBack}
            className="h-fit px-4 py-2 rounded-lg border border-border text-sm text-text-secondary hover:bg-primary-bg transition-colors"
          >
            이전 단계
          </button>
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

      {/* Next Button */}
      <div className="fixed bottom-6 left-0 w-full px-6 flex justify-center">
        <button
          type="button"
          onClick={() => onNext && onNext(selectedSell, selectedLease)}
          disabled={selectedCount === 0}
          className={`w-full max-w-md py-3 rounded-xl font-semibold text-white shadow-md transition-all duration-200 ${
            selectedCount > 0
              ? "bg-primary hover:bg-primary-hover hover:translate-y-[1px]"
              : "bg-border cursor-not-allowed text-text-secondary"
          }`}
        >
          {selectedCount > 0
            ? `다음 (${selectedCount}개 선택됨)`
            : "지역을 한 곳 이상 선택해주세요"}
        </button>
      </div>
    </section>
  );
}
