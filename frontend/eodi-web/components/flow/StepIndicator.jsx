"use client";

export default function StepIndicator({ step }) {
  const Dot = ({ index, label, trigger }) => {
    const isActive = step === index;
    const isDone = step > index;

    const baseDot =
      "w-7 h-7 flex items-center justify-center rounded-full border-2 text-[12px] transition-all duration-200 shrink-0";
    const active = "bg-primary border-primary text-white font-semibold shadow-sm";
    const done = "bg-primary-bg border-primary text-primary";
    const idle = "bg-white border-border text-text-secondary";

    return (
      <div className="flex flex-col items-center gap-[2px] min-w-[1.75rem]">
        <div
          // step이 바뀔 때 pop 애니메이션 재생되도록 key로 리마운트 트릭
          key={`dot-${index}-${trigger}-${isActive}`}
          className={`${baseDot} ${isActive ? active : isDone ? done : idle} ${isActive ? "animate-dot" : ""}`}
        >
          {index}
        </div>
        <span
          className={`text-[12px] transition-colors duration-200 ${
            step >= index ? "text-primary font-medium" : "text-text-secondary"
          }`}
        >
          {label}
        </span>
      </div>
    );
  };

  const Line = ({ filled, trigger }) => (
    <div className="h-[1.5px] bg-border rounded-full relative overflow-hidden min-w-0">
      <div
        // filled가 변할 때마다 진행선 애니메이션 재생
        key={`line-${trigger}-${filled}`}
        className={`absolute inset-0 bg-primary ${filled ? "animate-progress" : "w-0"}`}
      />
    </div>
  );

  return (
    <div className="w-full flex items-center justify-center mb-8">
      <div className="w-full max-w-3xl grid grid-cols-[auto_1fr_auto_1fr_auto] items-center gap-2">
        <Dot index={1} label="현금입력" trigger={step} />
        <Line filled={step > 1} trigger={step} />
        <Dot index={2} label="지역선택" trigger={step} />
        <Line filled={step > 2} trigger={step} />
        <Dot index={3} label="조건추가" trigger={step} />
      </div>
    </div>
  );
}
