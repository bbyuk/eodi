"use client";

import SliderInput from "@/components/ui/input/SliderInput";
import { useEffect, useRef, useState } from "react";
import { formatWon } from "@/app/search/_util/util";
import DiscreteSliderInput from "@/components/ui/input/DiscreteSliderInput";
import FilterInput from "@/app/search/step3/_components/FilterInput";

export default function FloatingFilterCardContents({
  close,
  apply,
  priceFilter,
  setPriceFilter,
  netLeasableFilter,
  setNetLeasableFilter,
}) {
  const initialPriceFilter = useRef(priceFilter);
  const initialNetLeasableFilter = useRef(netLeasableFilter);
  const appliedRef = useRef(false);
  const [canApplyFilters, setCanApplyFilters] = useState(false);

  const isPriceFilterDirty = (current, initial) => {
    return (
      current.enable !== initial.enable ||
      current.enableMin !== initial.enableMin ||
      current.enableMax !== initial.enableMax ||
      current.minValue !== initial.minValue ||
      current.maxValue !== initial.maxValue
    );
  };
  const isNetLeasableFilterDirty = (current, initial) => {
    return (
      current.enable !== initial.enable ||
      current.enableMin !== initial.enableMin ||
      current.enableMax !== initial.enableMax ||
      current.maxIndex !== initial.maxIndex ||
      current.minIndex !== initial.minIndex
    );
  };

  useEffect(() => {
    return () => {
      if (appliedRef.current) return;

      setPriceFilter(initialPriceFilter.current);
      setNetLeasableFilter(initialNetLeasableFilter.current);
    };
  }, []);
  useEffect(() => {
    setCanApplyFilters(
      isPriceFilterDirty(priceFilter, initialPriceFilter.current) ||
        isNetLeasableFilterDirty(netLeasableFilter, initialNetLeasableFilter.current)
    );
  }, [priceFilter, netLeasableFilter]);

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
        onClick={() => {
          if (!canApplyFilters) return;
          appliedRef.current = true;

          setPriceFilter((prev) => {
            if (prev.enable && !prev.enableMin && !prev.enableMax) {
              return { ...prev, enable: false };
            }
            return prev;
          });

          setNetLeasableFilter((prev) => {
            if (prev.enable && !prev.enableMin && !prev.enableMax) {
              return { ...prev, enable: false };
            }
            return prev;
          });

          apply();
        }}
        className={`w-full mt-4 py-2 rounded-md font-medium text-sm transition ${canApplyFilters ? "bg-primary text-white hover:bg-primary/90" : "bg-gray-200 text-gray-400 cursor-not-allowed"}`}
      >
        적용하기
      </button>
    </div>
  );
}
