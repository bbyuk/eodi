"use client";

import { useRef, useState } from "react";

export default function SliderInput({
  min = 0,
  max = 200_000,
  step = 5000,
  minValue = 50_000,
  maxValue = 100_000,
  valueFormatter = (value) => {},
  enableMin = false,
  enableMax = false,
  onEnableMinChange = (enableMin) => {},
  onEnableMaxChange = (enableMax) => {},
  onMinValueChange = (minValue) => {},
  onMaxValueChange = (maxValue) => {},
}) {
  const baseInput =
    "relative h-11 w-full rounded-md border px-3 text-sm flex items-center text-left transition cursor-pointer";
  const enabledInput = "bg-white text-gray-900 border-gray-300";
  const disabledInput = "bg-gray-100 text-gray-400 border-dashed border-gray-300";

  const trackRef = useRef(null);
  const [active, setActive] = useState(null); // 'min' | 'max'

  const effectiveMin = enableMin ? minValue : min;
  const effectiveMax = enableMax ? maxValue : max;

  const minPct = ((effectiveMin - min) / (max - min)) * 100;
  const maxPct = ((effectiveMax - min) / (max - min)) * 100;

  const getValueFromClientX = (clientX) => {
    const rect = trackRef.current.getBoundingClientRect();

    const rawPercent = (clientX - rect.left) / rect.width;

    const percent = Math.min(1, Math.max(0, rawPercent));

    const raw = min + percent * (max - min);
    return Math.round(raw / step) * step;
  };

  const startDrag = (type) => (e) => {
    e.preventDefault();
    setActive(type);

    const move = (ev) => {
      const clientX = ev.touches ? ev.touches[0].clientX : ev.clientX;
      const value = getValueFromClientX(clientX);

      if (type === "min" && enableMin) {
        const upperBound = enableMax ? maxValue - step : max;

        const next = Math.min(Math.max(value, min), upperBound);
        onMinValueChange(next);
      }

      if (type === "max" && enableMax) {
        const lowerBound = enableMin ? minValue + step : min;

        const next = Math.max(Math.min(value, max), lowerBound);
        onMaxValueChange(next);
      }
    };

    const stop = () => {
      setActive(null);
      window.removeEventListener("mousemove", move);
      window.removeEventListener("mouseup", stop);
      window.removeEventListener("touchmove", move);
      window.removeEventListener("touchend", stop);
    };

    window.addEventListener("mousemove", move);
    window.addEventListener("mouseup", stop);
    window.addEventListener("touchmove", move);
    window.addEventListener("touchend", stop);
  };

  return (
    <>
      <div className="grid grid-cols-2 gap-3">
        <div className="flex flex-col gap-1">
          <div className="text-[11px] text-gray-500">최소</div>
          <div
            onClick={() => {
              if (!enableMin) {
                if (maxValue === min) {
                  onMaxValueChange(min + step);
                  onMinValueChange(min);
                } else if (minValue >= maxValue) {
                  onMinValueChange(maxValue - step);
                }
              }
              onEnableMinChange(!enableMin);
            }}
            className={`${baseInput} ${enableMin ? enabledInput : disabledInput}`}
          >
            <div className="flex items-center justify-between">
              <span>{enableMin ? valueFormatter(minValue) : "미적용"}</span>
              <span
                className={`absolute right-3 top-1/2 -translate-y-1/2 text-[14px] leading-none ${enableMin ? "text-gray-400" : "text-gray-500"}`}
              >
                {enableMin ? "−" : "+"}
              </span>
            </div>
          </div>
        </div>
        <div className="flex flex-col gap-1">
          <div className="text-[11px] text-gray-500">최대</div>
          <div
            onClick={() => {
              if (!enableMax) {
                if (minValue === max) {
                  onMinValueChange(max - step);
                  onMaxValueChange(max);
                } else if (minValue >= maxValue) {
                  onMaxValueChange(minValue + step);
                }
              }
              onEnableMaxChange(!enableMax);
            }}
            className={`${baseInput} ${enableMax ? enabledInput : disabledInput}`}
          >
            <div className="flex items-center justify-between">
              <span>{enableMax ? valueFormatter(maxValue) : "미적용"}</span>

              <span
                className={`absolute right-3 top-1/2 -translate-y-1/2 text-[14px] leading-none ${enableMax ? "text-gray-400" : "text-gray-500"}`}
              >
                {enableMax ? "−" : "+"}
              </span>
            </div>
          </div>
        </div>
      </div>
      <div className="pt-3">
        <div ref={trackRef} className="relative h-6 select-none">
          {/* Track */}
          <div className="absolute top-1/2 h-[3px] w-full -translate-y-1/2 rounded bg-neutral-200" />

          {/* Range */}
          {(enableMin || enableMax) && (
            <div
              className="absolute top-1/2 h-[3px] -translate-y-1/2 rounded bg-primary"
              style={{
                left: `${minPct}%`,
                width: `${maxPct - minPct}%`,
              }}
            />
          )}

          {/* Min thumb */}
          {enableMin && (
            <div
              className={`absolute top-1/2 -translate-y-1/2 h-4 w-4 rounded-full bg-white border shadow cursor-pointer ${
                active === "min" ? "z-20" : "z-10"
              }`}
              style={{ left: `calc(${minPct}% - 8px)` }}
              onMouseDown={startDrag("min")}
              onTouchStart={startDrag("min")}
            />
          )}

          {/* Max thumb */}
          {enableMax && (
            <div
              className={`absolute top-1/2 -translate-y-1/2 h-4 w-4 rounded-full bg-white border shadow cursor-pointer ${
                active === "max" ? "z-20" : "z-10"
              }`}
              style={{ left: `calc(${maxPct}% - 8px)` }}
              onMouseDown={startDrag("max")}
              onTouchStart={startDrag("max")}
            />
          )}
        </div>
      </div>
    </>
  );
}
