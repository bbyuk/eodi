"use client";

import NumberInput from "@/components/ui/input/NumberInput";
import PageHeader from "@/components/ui/PageHeader";

export default function StepCash({ cash, onChangeCash, onNext, unit }) {
  const title = "예산을 입력해주세요";
  const description = ["입력한 금액으로 매수, 전·월세가 가능한 지역을 바로 찾아드릴게요."];

  return (
    <section className="max-w-5xl mx-auto px-6 pt-[1vh] pb-[5vh]">
      <PageHeader title={title} description={description} />

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
