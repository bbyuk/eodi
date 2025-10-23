"use client";
import { Fragment } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";

export default function StepIndicator() {
  const { currentContext } = useSearchStore();

  const Dot = ({ index, label }) => {
    const isActive = currentContext.step === index;
    const isDone = currentContext.step > index;

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
            currentContext.step >= index ? "text-primary font-medium" : "text-text-secondary"
          }`}
        >
          {label}
        </span>
      </div>
    );
  };

  const Line = ({ lineIndex }) => {
    const filled = currentContext.step > lineIndex;

    return (
      <div className="h-[1.5px] bg-border rounded-full relative overflow-hidden min-w-0">
        <div
          className="absolute inset-0 bg-primary"
          style={{
            transform: `scaleX(${filled ? 1 : 0})`,
          }}
        />
      </div>
    );
  };

  return (
    <div className="w-full flex items-center justify-center mb-8">
      <div className="w-full max-w-3xl grid grid-cols-[auto_1fr_auto_1fr_auto] items-center gap-2">
        {Object.values(context).map((value, index) => (
          <Fragment key={`step-indicator-${index}`}>
            <Dot index={value.step} label={value.name} />
            {index < Object.values(context).length - 1 ? <Line lineIndex={value.step} /> : <></>}
          </Fragment>
        ))}
      </div>
    </div>
  );
}
