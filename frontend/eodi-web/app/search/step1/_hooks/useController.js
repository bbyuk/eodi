import { useSearchStore } from "@/app/search/store/searchStore";
import { useEffect } from "react";
import { context } from "@/app/search/_const/context";
import useViewModel from "@/app/search/step1/_hooks/useViewModel";
import { formatWon } from "@/app/search/_util/util";
import { useSearchContext } from "@/app/search/layout";

export function useController() {
  const { resetStep1, cash, setCash, setCurrentContext, currentContext } = useSearchStore();
  const { goNext } = useSearchContext();

  const {
    state: {
      withLoan,
      annualIncome,
      isFirstTimeBuyer,
      mortgageLoanInterestRate,
      mortgageLoanPeriod,
    },
    derived: { pageHeaderTitle, pageHeaderDescription },
    actions: {
      setWithLoan,
      setAnnualIncome,
      setIsFirstTimeBuyer,
      setMortgageLoanInterestRate,
      setMortgageLoanPeriod,
    },
  } = useViewModel();

  const handleCashInputEnter = () => {
    // 엔터키 구현
    goNext();
  };

  const handleCashInputChange = (value) => {
    setCash(value);
  };

  const handleChangeWithLoanRadio = (value) => {
    setWithLoan(value);
    !value && resetLoanForm();
  };

  const resetLoanForm = () => {
    setAnnualIncome(undefined);
  };

  const handleAnnualIncomeChange = (value) => {
    setAnnualIncome(value);
  };

  const handleAnnualIncomeEnter = () => {
    // TODO 엔터 키 구현
    console.log("연소득 인풋 엔터");
  };

  const handleIsFirstTimeBuyerRadioChange = (value) => {
    setIsFirstTimeBuyer(value);
  };

  const handleMortgageLoanInterestRateChange = (value) => {
    setMortgageLoanInterestRate(value);
  };

  const handleMortgageLoanInterestRateEnter = () => {
    // TODO 엔터키 기능 구현
    console.log("주담대 금리 인풋 엔터");
  };

  const handleMortgageLoanPeriodChange = (value) => {
    setMortgageLoanPeriod(value);
  };

  const handleMortgageLoanPeriodEnter = () => {
    console.log("주담대 기간 인풋 엔터");
  };

  useEffect(() => {
    setCurrentContext(context.cash);
  }, [setCurrentContext]);

  useEffect(() => {
    resetStep1();
  }, []);

  return {
    page: {
      title: pageHeaderTitle,
      description: pageHeaderDescription,
    },
    cash: {
      value: cash,
      formatter: formatWon,
      onEnter: handleCashInputEnter,
      onChange: handleCashInputChange,
    },
    loan: {
      withLoan: withLoan,
      formatter: formatWon,
      onWithLoanRadioChange: handleChangeWithLoanRadio,
      withLoanRadioOptions: [
        { label: "네, 함께 볼게요", value: true },
        { label: "아니요, 대출 없이 볼게요", value: false },
      ],
      annualIncomeValue: annualIncome,
      onAnnualIncomeChange: handleAnnualIncomeChange,
      onAnnualIncomeEnter: handleAnnualIncomeEnter,
      isFirstTimeBuyer: isFirstTimeBuyer,
      onIsFirstTimeBuyerRadioChange: handleIsFirstTimeBuyerRadioChange,
      isFirstTimeBuyerRadioOptions: [
        { label: "네", value: true },
        { label: "아니오", value: false },
      ],
      mortgageLoanInterestRate: mortgageLoanInterestRate,
      onMortgageLoanInterestRateChange: handleMortgageLoanInterestRateChange,
      onMortgageLoanInterestRateEnter: handleMortgageLoanInterestRateEnter,
      mortgageLoanPeriod: mortgageLoanPeriod,
      onMortgageLoanPeriodChange: handleMortgageLoanPeriodChange,
      onMortgageLoanPeriodEnter: handleMortgageLoanPeriodEnter,
    },
  };
}
