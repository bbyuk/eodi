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

  const [selected, setSelected] = useState(new Set());

  const toggleRegion = (name) => {
    setSelected((prev) => {
      const next = new Set(prev);
      next.has(name) ? next.delete(name) : next.add(name);
      return next;
    });
  };

  const renderGrid = (regions) => (
    <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
      {regions.map((name) => {
        const isActive = selected.has(name);
        return (
          <button
            key={name}
            type="button"
            onClick={() => toggleRegion(name)}
            className={`w-full px-4 py-3 rounded-lg border text-left transition-all duration-200
              ${
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

  const selectedCount = selected.size;

  return (
    <div className="space-y-14 pb-24">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          {/* 🔹 메인 타이틀 */}
          <h2 className="text-3xl font-semibold text-text-primary">
            최근 실거래 데이터를 기반으로 살펴볼 만한 지역이에요.
          </h2>

          {/* 🔹 서브 설명 */}
          <p className="text-text-secondary mt-2">
            입력하신 예산을 참고해 산출한 결과이며,
            <br />
            실제 매물 상황이나 시세는 시점에 따라 달라질 수 있습니다.
          </p>

          {/* 🔹 예산 표시 */}
          <p className="mt-3 text-text-secondary">
            입력 예산:{" "}
            <span className="font-semibold text-text-primary">
              {cash ? `${Number(cash).toLocaleString()} 만 원` : "-"}
            </span>
          </p>
        </div>

        <button
          onClick={onBack}
          className="px-4 py-2 rounded-lg border border-border text-sm text-text-secondary hover:bg-primary-bg transition-colors"
        >
          이전 단계
        </button>
      </div>

      {/* 매수 가능 */}
      <section>
        <h3 className="text-xl font-semibold mb-4 text-text-primary">매수 기준 지역</h3>
        {renderGrid(sellRegions)}
      </section>

      {/* 전월세 가능 */}
      <section>
        <h3 className="text-xl font-semibold mb-4 text-text-primary">전월세 기준 지역</h3>
        {renderGrid(rentRegions)}
      </section>

      {/* 선택 결과 (퍼블용) */}
      {selectedCount > 0 && (
        <div className="text-sm text-text-secondary">
          선택된 지역: <span className="font-medium">{[...selected].join(", ")}</span>
        </div>
      )}

      {/* ✅ 하단 고정 Next 버튼 */}
      <div className="fixed bottom-6 left-0 w-full px-6 flex justify-center">
        <button
          type="button"
          onClick={() => onNext && onNext([...selected])}
          disabled={selectedCount === 0}
          className={`w-full max-w-md py-3 rounded-xl font-semibold text-white shadow-md transition-all duration-200
            ${
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
    </div>
  );
}
