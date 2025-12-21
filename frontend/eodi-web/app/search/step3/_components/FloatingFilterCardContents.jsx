"use client";

import SliderInput from "@/components/ui/input/SliderInput";
import { useState } from "react";
import { formatWon } from "@/app/search/_util/util";

export default function FloatingFilterCardContents({ close, tradeType }) {
  const priceStep = 5000;
  const [enablePriceFilter, setEnablePriceFilter] = useState(false);
  const [enablePriceMin, setEnablePriceMin] = useState(true);
  const [enablePriceMax, setEnablePriceMax] = useState(true);
  const [priceMin, setPriceMin] = useState(50_000);
  const [priceMax, setPriceMax] = useState(100_000);

  return (
    <div className="p-1 space-y-4 max-h-[60vh] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent">
      <FilterInput label={"가격"} enable={enablePriceFilter} changeEnable={setEnablePriceFilter}>
        {/* 금액 표시 */}
        <SliderInput
          step={priceStep}
          enableMin={enablePriceMin}
          onEnableMinChange={setEnablePriceMin}
          onEnableMaxChange={setEnablePriceMax}
          enableMax={enablePriceMax}
          min={0}
          minValue={priceMin}
          max={200_000}
          maxValue={priceMax}
          valueFormatter={formatWon}
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

function FilterInput({ label, enable, changeEnable, children }) {
  return (
    <div className="space-y-2">
      {/* Header */}
      <div className="flex items-center justify-between">
        <label className="text-xs font-medium text-gray-600">{label}</label>

        {enable ? (
          <button onClick={() => changeEnable(false)} className="text-xs text-gray-400">
            해제
          </button>
        ) : (
          <button onClick={() => changeEnable(true)} className="text-xs text-primary">
            +추가
          </button>
        )}
      </div>

      {/* Content */}
      {enable && <div className="pt-1">{children}</div>}
    </div>
  );
}
