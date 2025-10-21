"use client";

import NumberInput from "@/components/ui/input/NumberInput";

export default function StepCash({ cash, onChangeCash, onNext, unit }) {
  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh]">
      {/* Header */}
      <header className="mb-14">
        <h1 className="text-3xl md:text-4xl font-semibold text-text-primary mb-3 leading-tight">
          예산을 입력해주세요
        </h1>
        <p className="text-base text-text-secondary leading-relaxed">
          입력한 금액으로 매수, 전·월세가 가능한 지역을 바로 찾아드릴게요.
        </p>
      </header>

      {/* Input Section */}
      <NumberInput
        label={"보유 예산 (만 원 단위)"}
        placeholder={"예: 50000"}
        onChange={(e) => onChangeCash(e.target.value.replace(/[^0-9]/g, ""))}
        value={cash}
        unit={"만 원"}
      />
    </section>
  );
}
