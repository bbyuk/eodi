"use client";
import { useState } from "react";

export default function OptionalFilters({ region, onBack, onApply }) {
  const [minArea, setMinArea] = useState(null);
  const [maxArea, setMaxArea] = useState(null);
  const areaOptions = [33, 59, 74, 84, 99, 120];

  return (
    <div className="max-w-3xl mx-auto">
      {/* 헤더 */}
      <div className="flex items-center justify-between mb-8">
        <div>
          <h2 className="text-3xl font-semibold text-text-primary">Refine your search</h2>
          <p className="text-text-secondary">
            Selected region: <span className="font-medium text-text-primary">{region || "-"}</span>
          </p>
          <p className="text-text-secondary mt-1">All fields below are optional.</p>
        </div>
        <button
          onClick={onBack}
          className="px-4 py-2 rounded-lg border border-border text-sm text-text-secondary hover:bg-primary-bg transition-colors"
        >
          Back
        </button>
      </div>

      {/* 옵션 그리드 */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-10">
        {/* Min Area */}
        <div>
          <p className="text-sm font-medium text-left text-text-secondary mb-3">
            Min area (㎡) — optional
          </p>
          <div className="flex flex-wrap gap-2">
            {areaOptions.map((val) => (
              <button
                key={`min-${val}`}
                type="button"
                onClick={() => setMinArea(val === minArea ? null : val)}
                className={`px-4 py-2 rounded-lg border text-sm transition-all duration-200
                  ${
                    minArea === val
                      ? "bg-primary text-white border-primary shadow-sm scale-[1.02]"
                      : "border-border text-text-secondary hover:bg-primary-bg hover:text-primary"
                  }`}
              >
                {val}㎡
              </button>
            ))}
          </div>
        </div>

        {/* Max Area */}
        <div>
          <p className="text-sm font-medium text-left text-text-secondary mb-3">
            Max area (㎡) — optional
          </p>
          <div className="flex flex-wrap gap-2">
            {areaOptions.map((val) => (
              <button
                key={`max-${val}`}
                type="button"
                onClick={() => setMaxArea(val === maxArea ? null : val)}
                className={`px-4 py-2 rounded-lg border text-sm transition-all duration-200
                  ${
                    maxArea === val
                      ? "bg-primary text-white border-primary shadow-sm scale-[1.02]"
                      : "border-border text-text-secondary hover:bg-primary-bg hover:text-primary"
                  }`}
              >
                {val}㎡
              </button>
            ))}
          </div>
        </div>
      </div>

      {/* Apply 버튼 */}
      <div className="flex justify-end gap-3">
        <button
          onClick={onApply}
          className="px-6 py-3 rounded-lg font-medium text-white bg-primary hover:bg-primary-hover shadow-md transition-all duration-200 hover:translate-y-[1px]"
        >
          Apply
        </button>
      </div>
    </div>
  );
}
