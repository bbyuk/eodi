"use client";

import SliderInput from "@/components/ui/input/SliderInput";
import { useEffect, useRef } from "react";
import DiscreteSliderInput from "@/components/ui/input/DiscreteSliderInput";
import FilterInput from "@/app/search/step3/_components/FilterInput";

export default function FloatingFilterCardContents({ apply, filters }) {
  const initialMapRef = useRef(
    new Map(filters.map((f) => [f.key, { key: f.key, type: f.type, filter: f.filter }]))
  );
  const appliedRef = useRef(false);

  const isDirty = filters.some((cur) => {
    const initial = initialMapRef.current.get(cur.key);
    return cur.filter.dirtyChecker(cur.filter, initial?.filter);
  });

  useEffect(() => {
    return () => {
      if (appliedRef.current) return;

      filters.forEach((cur) => {
        const initial = initialMapRef.current.get(cur.key);
        if (initial) cur.setFilter(initial.filter);
      });
    };
  }, []);

  return (
    <div className="p-1 space-y-4 max-h-[60vh] overflow-y-auto scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-transparent">
      {filters.map(({ key, filter, setFilter, type }) => {
        const changeEnable = (v) => setFilter((prev) => ({ ...prev, enable: v }));

        const changeEnableMin = (v) => setFilter((prev) => ({ ...prev, enableMin: v }));

        const changeEnableMax = (v) => setFilter((prev) => ({ ...prev, enableMax: v }));

        switch (type) {
          case "slider":
            return (
              <FilterInput
                key={key}
                label={filter.label}
                enable={filter.enable}
                changeEnable={changeEnable}
              >
                {/* 금액 표시 */}
                <SliderInput
                  step={filter.step}
                  enableMin={filter.enableMin}
                  enableMax={filter.enableMax}
                  onEnableMinChange={changeEnableMin}
                  onEnableMaxChange={changeEnableMax}
                  min={filter.min}
                  minValue={filter.minValue}
                  max={filter.max}
                  maxValue={filter.maxValue}
                  valueFormatter={filter.valueFormatter}
                  onMinValueChange={(v) => setFilter((prev) => ({ ...prev, minValue: v }))}
                  onMaxValueChange={(v) => setFilter((prev) => ({ ...prev, maxValue: v }))}
                />
              </FilterInput>
            );
          case "discrete-slider":
            return (
              <FilterInput
                key={filter.key}
                label={filter.label}
                enable={filter.enable}
                changeEnable={changeEnable}
              >
                <DiscreteSliderInput
                  options={filter.options}
                  enableMin={filter.enableMin}
                  enableMax={filter.enableMax}
                  onEnableMinChange={changeEnableMin}
                  onEnableMaxChange={changeEnableMax}
                  minIndex={filter.minIndex}
                  maxIndex={filter.maxIndex}
                  onMinIndexChange={(v) => setFilter((prev) => ({ ...prev, minIndex: v }))}
                  onMaxIndexChange={(v) => setFilter((prev) => ({ ...prev, maxIndex: v }))}
                  valueFormatter={filter.valueFormatter}
                />
              </FilterInput>
            );
          default:
            return null;
        }
      })}

      <button
        onClick={() => {
          if (!isDirty) return;
          appliedRef.current = true;

          filters.forEach(({ key, filter, setFilter }) => {
            if (filter.enable && !filter.enableMin && !filter.enableMax) {
              setFilter((prev) => ({ ...prev, enable: false }));
            }
          });

          apply();
        }}
        className={`w-full mt-4 py-2 rounded-md font-medium text-sm transition ${isDirty ? "bg-primary text-white hover:bg-primary/90" : "bg-gray-200 text-gray-400 cursor-not-allowed"}`}
      >
        적용하기
      </button>
    </div>
  );
}
