"use client";

import SliderInput from "@/components/ui/input/SliderInput";
import { useState } from "react";
import { formatWon } from "@/app/search/_util/util";
import DiscreteSliderInput from "@/components/ui/input/DiscreteSliderInput";

export default function FloatingFilterCardContents({
  close,
  priceFilter,
  setPriceFilter,
  netLeasableFilter,
  setNetLeasableFilter,
}) {
  return (
    <div className="p-1 space-y-4 max-h-[60vh] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent">
      <FilterInput
        label={priceFilter.label}
        enable={priceFilter.enable}
        changeEnable={(v) => setPriceFilter((prev) => ({ ...prev, enable: v }))}
      >
        {/* 금액 표시 */}
        <SliderInput
          step={priceFilter.step}
          enableMin={priceFilter.enableMin}
          enableMax={priceFilter.enableMax}
          onEnableMinChange={(v) => setPriceFilter((prev) => ({ ...prev, enableMin: v }))}
          onEnableMaxChange={(v) => setPriceFilter((prev) => ({ ...prev, enableMax: v }))}
          min={priceFilter.min}
          minValue={priceFilter.minValue}
          max={priceFilter.max}
          maxValue={priceFilter.maxValue}
          valueFormatter={formatWon}
          onMinValueChange={(v) => setPriceFilter((prev) => ({ ...prev, minValue: v }))}
          onMaxValueChange={(v) => setPriceFilter((prev) => ({ ...prev, maxValue: v }))}
        />
      </FilterInput>

      <FilterInput
        label={netLeasableFilter.label}
        enable={netLeasableFilter.enable}
        changeEnable={(v) => setNetLeasableFilter((prev) => ({ ...prev, enable: v }))}
      >
        <DiscreteSliderInput
          options={netLeasableFilter.options}
          enableMin={netLeasableFilter.enableMin}
          enableMax={netLeasableFilter.enableMax}
          onEnableMinChange={(v) => setNetLeasableFilter((prev) => ({ ...prev, enableMin: v }))}
          onEnableMaxChange={(v) => setNetLeasableFilter((prev) => ({ ...prev, enableMax: v }))}
          minIndex={netLeasableFilter.minIndex}
          maxIndex={netLeasableFilter.maxIndex}
          onMinIndexChange={(v) => setNetLeasableFilter((prev) => ({ ...prev, minIndex: v }))}
          onMaxIndexChange={(v) => setNetLeasableFilter((prev) => ({ ...prev, maxIndex: v }))}
          valueFormatter={(value) => `${value}㎡`}
        />
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
