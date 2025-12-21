"use client";

import SliderInput from "@/components/ui/input/SliderInput";
import { formatWon } from "@/app/search/_util/util";
import { useState } from "react";

export default function FloatingFilterCardContents({ close, tradeType }) {
  const [enablePriceMin, setEnablePriceMin] = useState(true);
  const [enablePriceMax, setEnablePriceMax] = useState(true);
  const [priceMin, setPriceMin] = useState(50_000);
  const [priceMax, setPriceMax] = useState(100_000);

  return (
    <div className="p-1 space-y-4 max-h-[60vh] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent">
      <FilterInput label={"가격"}>
        {/* 금액 표시 */}
        <div className="grid grid-cols-2 gap-3">
          <input
            value={enablePriceMin ? formatWon(priceMin) : ""}
            readOnly
            placeholder={"미지정"}
            className="h-11 rounded-md border px-3 text-sm bg-gray-50 text-gray-800"
          />
          <input
            value={enablePriceMax ? formatWon(priceMax) : ""}
            readOnly
            placeholder={"미지정"}
            className="h-11 rounded-md border px-3 text-sm bg-gray-50 text-gray-800"
          />
        </div>
        <SliderInput
          step={5000}
          enableMin={enablePriceMin}
          enableMax={enablePriceMax}
          min={0}
          minValue={priceMin}
          max={200_000}
          maxValue={priceMax}
          onMinValueChange={setPriceMin}
          onMaxValueChange={setPriceMax}
        />
      </FilterInput>

      <FilterInput label={"전용면적"}>
        <SliderInput />
      </FilterInput>

      <button
        onClick={close}
        className="w-full mt-4 py-2 rounded-md bg-primary text-white font-medium text-sm hover:bg-primary/90 transition"
      >
        적용하기
      </button>
    </div>
  );
}

function FilterInput({ label, children }) {
  return (
    <div>
      <label className="text-xs font-medium text-gray-600">{label}</label>
      {children}
    </div>
  );
}
