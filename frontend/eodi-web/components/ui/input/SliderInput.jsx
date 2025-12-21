"use client";

import { useState } from "react";
import { formatWon } from "@/app/search/_util/util";

const MIN = 0;
const MAX = 200_000; // 단위: 만원 (0 ~ 20억)
const STEP = 500; // 5천만원

export default function SliderInput() {
  const [min, setMin] = useState(50_000);
  const [max, setMax] = useState(100_000);

  return (
    <div className="space-y-3 pt-2 pb-1">
      {/* 금액 표시 */}
      <div className="grid grid-cols-2 gap-3">
        <input
          value={formatWon(min)}
          readOnly
          className="h-11 rounded-md border px-3 text-sm bg-gray-50 text-gray-800"
        />
        <input
          value={formatWon(max)}
          readOnly
          className="h-11 rounded-md border px-3 text-sm bg-gray-50 text-gray-800"
        />
      </div>

      {/* Slider */}
      <div className="relative h-6 mt-1">
        {/* Track */}
        <div className="absolute top-1/2 h-[3px] w-full -translate-y-1/2 rounded bg-neutral-200" />

        {/* Range */}
        <div
          className="absolute top-1/2 h-[3px] -translate-y-1/2 rounded bg-primary"
          style={{
            left: `${(min / MAX) * 100}%`,
            width: `${((max - min) / MAX) * 100}%`,
          }}
        />

        <input
          type="range"
          min={MIN}
          max={MAX}
          step={STEP}
          value={min}
          onChange={(e) => setMin(Math.min(Number(e.target.value), max - STEP))}
          className="range-thumb z-10"
        />

        <input
          type="range"
          min={MIN}
          max={MAX}
          step={STEP}
          value={max}
          onChange={(e) => setMax(Math.max(Number(e.target.value), min + STEP))}
          className="range-thumb z-20"
        />
      </div>
    </div>
  );
}
