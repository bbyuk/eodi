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
          <h2 className="text-3xl font-semibold text-text-primary">Select regions</h2>
          <p className="text-text-secondary">
            Budget: {cash ? `${Number(cash).toLocaleString()} 만원` : "-"}
          </p>
        </div>
        <button
          onClick={onBack}
          className="px-4 py-2 rounded-lg border border-border text-sm text-text-secondary hover:bg-primary-bg transition-colors"
        >
          Back
        </button>
      </div>

      {/* 매수 가능 */}
      <section>
        <h3 className="text-xl font-semibold mb-4 text-text-primary">For Purchase</h3>
        {renderGrid(sellRegions)}
      </section>

      {/* 전월세 가능 */}
      <section>
        <h3 className="text-xl font-semibold mb-4 text-text-primary">For Rent</h3>
        {renderGrid(rentRegions)}
      </section>

      {/* 선택 결과 (퍼블용) */}
      {selectedCount > 0 && (
        <div className="text-sm text-text-secondary">Selected: {[...selected].join(", ")}</div>
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
          {selectedCount > 0 ? `Next (${selectedCount} selected)` : "Select at least one region"}
        </button>
      </div>
    </div>
  );
}
