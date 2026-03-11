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
        {/* 보유 현금 */}
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

        {/* 대출 설정 여부 */}
        <section className="space-y-3">
          <SectionHeader title={"대출을 포함해 예산을 계산할까요?"} />
          <RadioGroup
            name={"include-loan"}
            value={controller.loan.withLoan}
            onChange={controller.loan.onWithLoanRadioChange}
            options={controller.loan.withLoanRadioOptions}
          />
        </section>

        {/* 대출 설정 상세 */}
        {controller.loan.withLoan && (
          <div className="space-y-6 rounded-2xl border border-slate-200 bg-white/70 p-5">
            <section className="space-y-3">
              <SectionHeader title={"주택을 소유하신 적이 있나요?"} />
              <RadioGroup
                name={"is-firsttime-buyer"}
                value={controller.loan.isFirstTimeBuyer}
                onChange={controller.loan.onIsFirstTimeBuyerRadioChange}
                options={controller.loan.isFirstTimeBuyerRadioOptions}
              />
            </section>
            <section className="space-y-3">
              <SectionHeader
                title={"연소득"}
                description={["대출 가능 금액(DSR)을 계산할 때 사용돼요"]}
              />
              <CashInput
                label={"(만 원 단위)"}
                placeholder={"예: 5000"}
                onChange={controller.loan.onAnnualIncomeChange}
                onEnter={controller.loan.onAnnualIncomeEnter}
                value={controller.loan.annualIncomeValue}
                unit={"만 원"}
                formatter={controller.loan.formatter}
              />
            </section>
          </div>
        )}
      </div>
    </section>
  );
}
