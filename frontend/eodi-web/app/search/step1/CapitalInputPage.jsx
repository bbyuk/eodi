"use client";

import PageHeader from "@/components/ui/header/PageHeader";
import { useController } from "@/app/search/step1/_hooks/useController";
import SectionHeader from "@/components/ui/header/SectionHeader";
import RadioGroup from "@/components/ui/input/RadioGroup";
import NumberInput from "@/components/ui/input/NumberInput";

export default function CapitalInputPage() {
  const controller = useController();

  /**
   * 이후에 사용할 DSR form 렌더함수
   * @returns {JSX.Element}
   */
  const renderDSRForm = () => {
    return (
      <>
        <section className="space-y-3">
          <SectionHeader title={"연소득"} />
          <NumberInput
            label={"(단위: 만 원)"}
            placeholder={"예: 5000"}
            onChange={controller.loan.onAnnualIncomeChange}
            onEnter={controller.loan.onAnnualIncomeEnter}
            value={controller.loan.annualIncomeValue}
            unit={"만 원"}
            formatter={controller.loan.formatter}
            maxValue={99999999}
          />
        </section>

        <section className="space-y-3">
          <SectionHeader title={"주택담보대출 금리"} />
          <NumberInput
            label="(단위: %)"
            placeholder="예: 4.5"
            value={controller.loan.mortgageLoanInterestRate}
            onChange={controller.loan.onMortgageLoanInterestRateChange}
            onEnter={controller.loan.onMortgageLoanInterestRateEnter}
            unit="%"
            decimalScale={2}
            maxValue={100}
          />
        </section>

        <section className="space-y-3">
          <SectionHeader title={"주택담보대출 기간"} />
          <NumberInput
            label="(단위: 년)"
            placeholder="예: 30"
            value={controller.loan.mortgageLoanPeriod}
            onChange={controller.loan.onMortgageLoanPeriodChange}
            onEnter={controller.loan.onMortgageLoanPeriodEnter}
            unit="년"
            maxValue={50}
          />
        </section>
      </>
    );
  };

  return (
    <section className="mx-auto w-full max-w-3xl overflow-x-hidden px-0 pb-6">
      <PageHeader {...controller.page} />

      <div className="space-y-8 sm:space-y-10">
        <section className="space-y-3">
          <SectionHeader
            title={"보유 현금"}
            description={["대출 금액을 제외한 가용 현금을 입력해주세요"]}
          />
          <NumberInput
            label={"(단위: 만 원)"}
            placeholder={"예: 50000"}
            onChange={controller.cash.onChange}
            onEnter={controller.cash.onEnter}
            value={controller.cash.value}
            unit={"만 원"}
            formatter={controller.cash.formatter}
            maxValue={99999999}
          />
        </section>

        <section className="space-y-3">
          <SectionHeader title={"대출을 포함해 예산을 계산할까요?"} />
          <RadioGroup
            name={"include-loan"}
            value={controller.loan.withLoan}
            onChange={controller.loan.onWithLoanRadioChange}
            options={controller.loan.withLoanRadioOptions}
          />
        </section>

        {controller.loan.withLoan && (
          <div className="space-y-6 rounded-2xl border border-slate-200 bg-white/70 p-4 sm:p-5">
            <section className="space-y-3">
              <SectionHeader title={"매매를 고려중이신가요?"} />
              <RadioGroup
                name={"include-sell"}
                value={controller.loan.includeSell}
                onChange={controller.loan.onIncludeSellRadioChange}
                options={controller.loan.includeSellRadioOptions}
              />
            </section>

            {controller.loan.includeSell && (
              <>
                <section className="space-y-3">
                  <SectionHeader title={"주택을 소유하신 적이 있나요?"} />
                  <RadioGroup
                    name={"is-firsttime-buyer"}
                    value={controller.loan.isFirstTimeBuyer}
                    onChange={controller.loan.onIsFirstTimeBuyerRadioChange}
                    options={controller.loan.isFirstTimeBuyerRadioOptions}
                  />
                </section>
              </>
            )}
          </div>
        )}
      </div>
    </section>
  );
}
