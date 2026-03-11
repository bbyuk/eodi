"use client";

import CashInput from "@/components/ui/input/CashInput";
import PageHeader from "@/components/ui/header/PageHeader";
import { useEffect, useState } from "react";
import { useSearchStore } from "@/app/search/store/searchStore";
import { context } from "@/app/search/_const/context";
import { formatWon } from "@/app/search/_util/util";
import { useSearchContext } from "@/app/search/layout";
import { useController } from "@/app/search/step1/_hooks/useController";
import SectionHeader from "@/components/ui/header/SectionHeader";
import RadioGroup from "@/components/ui/input/RadioGroup";

export default function CapitalInputPage() {
  const controller = useController();

  return (
    <section className="w-full px-8 pt-[1vh] pb-[5vh] overflow-x-hidden">
      <PageHeader {...controller.page} />

      <div className="space-y-10">
        <section className="space-y-3">
          <SectionHeader
            title={"보유 현금"}
            description={["대출 금액을 제외한 가용 현금을 입력해주세요."]}
          />
          <CashInput
            label={"(만 원 단위)"}
            placeholder={"예: 50000"}
            onChange={controller.cash.onChange}
            onEnter={controller.cash.onEnter}
            value={controller.cash.value}
            unit={"만 원"}
            formatter={controller.cash.formatter}
          />
        </section>

        <section className="space-y-3">
          <SectionHeader title={"대출 금액도 함께 계산할까요?"} />
          <RadioGroup
            name={"include-loan"}
            value={controller.loan.value}
            onChange={controller.loan.onChange}
            options={controller.loan.options}
          />
        </section>
      </div>
    </section>
  );
}
