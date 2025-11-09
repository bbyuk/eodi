"use client";

import CashInput from "@/components/ui/input/CashInput";
import PageHeader from "@/components/ui/PageHeader";
import { useEffect, useState } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";
import { formatWon } from "@/app/search/_util/util";
import { useSearchContext } from "@/app/search/layout";

const id = "cash";
export default function StepCash() {
  const title = "예산을 입력해주세요";
  const description = ["입력한 금액으로 매수, 전·월세가 가능한 지역을 바로 찾아드릴게요."];
  const unit = "만 원";

  const { cash, setCash, resetStep1, setCurrentContext } = useSearchStore();
  const { goNext } = useSearchContext();

  useEffect(() => {
    setCurrentContext(context[id]);
    resetStep1();
  }, []);

  return (
    <section className="w-full px-8 pt-[1vh] pb-[5vh] overflow-x-hidden">
      <PageHeader title={title} description={description} />

      <CashInput
        label={"보유 예산 (만 원 단위)"}
        placeholder={"예: 50000"}
        onChange={(value) => setCash(value)}
        onEnter={goNext}
        value={cash}
        unit={unit}
        formatter={formatWon}
      />
    </section>
  );
}
