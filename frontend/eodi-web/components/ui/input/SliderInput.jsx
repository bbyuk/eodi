"use client";

import { useState, useRef } from "react";

export default function DualThumbSliderInput({
  min = 0,
  max = 200_000,
  step = 5000,
  minValue,
  maxValue,
  onMinValueChange,
  onMaxValueChange,
}) {
  const trackRef = useRef(null);
  const [active, setActive] = useState(null); // 'min' | 'max'

  const minPct = (minValue / max) * 100;
  const maxPct = (maxValue / max) * 100;

  const getValueFromClientX = (clientX) => {
    const rect = trackRef.current.getBoundingClientRect();
    const percent = (clientX - rect.left) / rect.width;
    const raw = min + percent * (max - min);
    return Math.round(raw / step) * step;
  };

  const startDrag = (type) => (e) => {
    e.preventDefault();
    setActive(type);

    const move = (ev) => {
      const clientX = ev.touches ? ev.touches[0].clientX : ev.clientX;
      const value = getValueFromClientX(clientX);

      if (type === "min") {
        onMinValueChange(Math.min(value, maxValue - step));
      } else {
        onMaxValueChange(Math.max(value, minValue + step));
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
    <div className="pt-3">
      <div ref={trackRef} className="relative h-6 select-none">
        {/* Track */}
        <div className="absolute top-1/2 h-[3px] w-full -translate-y-1/2 rounded bg-neutral-200" />

        {/* Range */}
        <div
          className="absolute top-1/2 h-[3px] -translate-y-1/2 rounded bg-primary"
          style={{
            left: `${minPct}%`,
            width: `${maxPct - minPct}%`,
          }}
        />

        {/* Min thumb */}
        <div
          className={`absolute top-1/2 -translate-y-1/2 h-4 w-4 rounded-full bg-white border shadow cursor-pointer ${
            active === "min" ? "z-20" : "z-10"
          }`}
          style={{ left: `calc(${minPct}% - 8px)` }}
          onMouseDown={startDrag("min")}
          onTouchStart={startDrag("min")}
        />

        {/* Max thumb */}
        <div
          className={`absolute top-1/2 -translate-y-1/2 h-4 w-4 rounded-full bg-white border shadow cursor-pointer ${
            active === "max" ? "z-20" : "z-10"
          }`}
          style={{ left: `calc(${maxPct}% - 8px)` }}
          onMouseDown={startDrag("max")}
          onTouchStart={startDrag("max")}
        />
      </div>
    </div>
  );
}
