"use client";
import { useState } from "react";

export default function OptionalFilters({ region, onBack, onApply }) {
  const [minArea, setMinArea] = useState(null);
  const [maxArea, setMaxArea] = useState(null);
  const areaOptions = [33, 59, 74, 84, 99, 120];

  return (
    <div className="max-w-3xl mx-auto">
      <div className="flex items-center justify-between mb-8">
        <div>
          <h2 className="text-3xl font-semibold">Refine your search</h2>
          <p className="text-slate-500">
            Selected region: <span className="font-medium text-slate-800">{region || "-"}</span>
          </p>
          <p className="text-slate-500 mt-1">All fields below are optional.</p>
        </div>
        <button
          onClick={onBack}
          className="px-4 py-2 rounded-lg border border-slate-300 hover:bg-slate-50 text-sm"
        >
          Back
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mb-10">
        <div>
          <p className="text-sm font-medium text-left text-slate-600 mb-3">
            Min area (㎡) — optional
          </p>
          <div className="flex flex-wrap gap-2">
            {areaOptions.map((val) => (
              <button
                key={`min-${val}`}
                type="button"
                onClick={() => setMinArea(val === minArea ? null : val)}
                className={`px-4 py-2 rounded-lg border text-sm transition ${
                  minArea === val
                    ? "bg-blue-600 text-white border-blue-600"
                    : "border-slate-300 text-slate-600 hover:bg-slate-50"
                }`}
              >
                {val}㎡
              </button>
            ))}
          </div>
        </div>

        <div>
          <p className="text-sm font-medium text-left text-slate-600 mb-3">
            Max area (㎡) — optional
          </p>
          <div className="flex flex-wrap gap-2">
            {areaOptions.map((val) => (
              <button
                key={`max-${val}`}
                type="button"
                onClick={() => setMaxArea(val === maxArea ? null : val)}
                className={`px-4 py-2 rounded-lg border text-sm transition ${
                  maxArea === val
                    ? "bg-blue-600 text-white border-blue-600"
                    : "border-slate-300 text-slate-600 hover:bg-slate-50"
                }`}
              >
                {val}㎡
              </button>
            ))}
          </div>
        </div>
      </div>

      <div className="flex justify-end gap-3">
        <button
          onClick={onApply}
          className="px-6 py-3 rounded-lg bg-blue-600 hover:bg-blue-700 text-white font-medium"
        >
          Apply
        </button>
      </div>
    </div>
  );
}
