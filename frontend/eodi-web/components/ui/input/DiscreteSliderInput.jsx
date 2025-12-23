"use client";

import { useRef, useState } from "react";

export default function DiscreteSliderInput({
  options = [], // ex) [10, 15, 20, 25, 30, 40, 60, 85]
  valueFormatter = (v) => v,
  enableMin = false,
  enableMax = false,
  minIndex = 0,
  maxIndex = options.length - 1,
  onEnableMinChange = (v) => {},
  onEnableMaxChange = (v) => {},
  onMinIndexChange = (v) => {},
  onMaxIndexChange = (v) => {},
}) {
  const baseInput =
    "relative h-11 w-full rounded-md border px-3 text-sm flex items-center text-left transition cursor-pointer";
  const enabledInput = "bg-white text-gray-900 border-gray-300";
  const disabledInput = "bg-gray-100 text-gray-400 border-dashed border-gray-300";

  const trackRef = useRef(null);
  const [active, setActive] = useState(null); // 'min' | 'max'

  const min = 0;
  const max = options.length - 1;
  const step = 1;

  const effectiveMin = enableMin ? minIndex : min;
  const effectiveMax = enableMax ? maxIndex : max;

  const minPct = (effectiveMin / max) * 100;
  const maxPct = (effectiveMax / max) * 100;

  const getIndexFromClientX = (clientX) => {
    const rect = trackRef.current.getBoundingClientRect();
    const percent = Math.min(1, Math.max(0, (clientX - rect.left) / rect.width));
    return Math.round(percent * max);
  };

  const startDrag = (type) => (e) => {
    e.preventDefault();
    setActive(type);

    const move = (ev) => {
      const clientX = ev.touches ? ev.touches[0].clientX : ev.clientX;
      const index = getIndexFromClientX(clientX);

      if (type === "min" && enableMin) {
        const upper = enableMax ? maxIndex - step : max;
        onMinIndexChange(Math.min(Math.max(index, min), upper));
      }

      if (type === "max" && enableMax) {
        const lower = enableMin ? minIndex + step : min;
        onMaxIndexChange(Math.max(Math.min(index, max), lower));
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
      {/* 입력부 */}
      <div className="grid grid-cols-2 gap-3">
        {/* 최소 */}
        <div className="flex flex-col gap-1">
          <div className="text-[11px] text-gray-500">최소</div>
          <div
            onClick={() => {
              if (!enableMin) {
                if (maxIndex === 0) {
                  onMaxIndexChange(1);
                  onMinIndexChange(0);
                } else if (minIndex >= maxIndex) {
                  onMinIndexChange(maxIndex - 1);
                }
              }
              onEnableMinChange(!enableMin);
            }}
            className={`${baseInput} ${enableMin ? enabledInput : disabledInput}`}
          >
            <span>{enableMin ? valueFormatter(options[minIndex]) : "미적용"}</span>
            <span className="absolute right-3 top-1/2 -translate-y-1/2">
              {enableMin ? "−" : "+"}
            </span>
          </div>
        </div>

        {/* 최대 */}
        <div className="flex flex-col gap-1">
          <div className="text-[11px] text-gray-500">최대</div>
          <div
            onClick={() => {
              if (!enableMax) {
                if (minIndex === options.length - 1) {
                  onMinIndexChange(options.length - 2);
                  onMaxIndexChange(options.length - 1);
                } else if (minIndex >= maxIndex) {
                  onMaxIndexChange(minIndex + 1);
                }
              }
              onEnableMaxChange(!enableMax);
            }}
            className={`${baseInput} ${enableMax ? enabledInput : disabledInput}`}
          >
            <span>{enableMax ? valueFormatter(options[maxIndex]) : "미적용"}</span>
            <span className="absolute right-3 top-1/2 -translate-y-1/2">
              {enableMax ? "−" : "+"}
            </span>
          </div>
        </div>
      </div>

      {/* 슬라이더 */}
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
