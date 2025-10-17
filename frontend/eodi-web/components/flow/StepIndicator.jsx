"use client";
import { useLayoutEffect, useState } from "react";

export default function StepIndicator({ step, prevStep }) {
  const isForward = step > prevStep;

  const Dot = ({ index, label }) => {
    const isActive = step === index;
    const isDone = step > index;

    const base =
      "w-7 h-7 flex items-center justify-center rounded-full border-2 text-[12px] transition-all duration-200 shrink-0";
    const active = "bg-primary border-primary text-white font-semibold shadow-sm";
    const done = "bg-primary-bg border-primary text-primary";
    const idle = "bg-white border-border text-text-secondary";

    return (
      <div className="flex flex-col items-center gap-[2px] min-w-[1.75rem]">
        <div className={`${base} ${isActive ? active : isDone ? done : idle}`}>{index}</div>
        <span
          className={`text-[12px] ${
            step >= index ? "text-primary font-medium" : "text-text-secondary"
          }`}
        >
          {label}
        </span>
      </div>
    );
  };

  const Line = ({ lineIndex }) => {
    const filled = step > lineIndex;
    const justChanged =
      (isForward && step === lineIndex + 1) || (!isForward && prevStep === lineIndex + 1);

    const [animate, setAnimate] = useState(false);

    // useLayoutEffect: DOM 반영 직전에 트리거 (즉시 반응)
    useLayoutEffect(() => {
      if (justChanged) {
        setAnimate(false);
        setAnimate(true);
      } else {
        setAnimate(false);
      }
    }, [justChanged, step, prevStep]);

    const directionClass = isForward ? "animate-progress" : "animate-progress-reverse";
    const transformOrigin = "left";
    const initialScale = isForward ? 0 : 1;

    return (
      <div className="h-[1.5px] bg-border rounded-full relative overflow-hidden min-w-0">
        {justChanged ? (
          <div
            key={`anim-${step}-${prevStep}-${lineIndex}`}
            className={`absolute inset-0 bg-primary ${animate ? directionClass : ""}`}
            style={{
              transform: `scaleX(${initialScale})`,
              transformOrigin,
            }}
          />
        ) : (
          <div
            className="absolute inset-0 bg-primary"
            style={{
              transform: `scaleX(${filled ? 1 : 0})`,
              transformOrigin,
            }}
          />
        )}
      </div>
    );
  };

  return (
    <div className="w-full flex items-center justify-center mb-8">
      <div className="w-full max-w-3xl grid grid-cols-[auto_1fr_auto_1fr_auto] items-center gap-2">
        <Dot index={1} label="현금입력" />
        <Line lineIndex={1} />
        <Dot index={2} label="지역선택" />
        <Line lineIndex={2} />
        <Dot index={3} label="조건추가" />
      </div>
    </div>
  );
}
