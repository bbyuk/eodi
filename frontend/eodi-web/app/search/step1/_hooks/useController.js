import { useSearchStore } from "@/app/search/store/searchStore";
import { useEffect } from "react";
import { context } from "@/app/search/_const/context";
import useViewModel from "@/app/search/step1/_hooks/useViewModel";
import { formatWon } from "@/app/search/_util/util";
import { useSearchContext } from "@/app/search/layout";

export function useController() {
  const {
    resetStep1,
    cash,
    setCash,
    setCurrentContext,
    withLoan,
    setWithLoan,
    includeSell,
    setIncludeSell,
    isFirstTimeBuyer,
    setIsFirstTimeBuyer,
  } = useSearchStore();
  const { goNext } = useSearchContext();

  const {
    state: { annualIncome, mortgageLoanInterestRate, mortgageLoanPeriod },
    derived: { pageHeaderTitle, pageHeaderDescription },
    actions: { setAnnualIncome, setMortgageLoanInterestRate, setMortgageLoanPeriod },
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
    setIncludeSell(false);
    resetSellLoanForm();
  };

  const handleChangeIncludeSellRadio = (value) => {
    setIncludeSell(value);
    !value && resetSellLoanForm();
  };

  const resetSellLoanForm = () => {
    setIsFirstTimeBuyer(true);
    setAnnualIncome("");
    setMortgageLoanInterestRate("");
    setMortgageLoanPeriod("");
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
      includeSell: includeSell,
      onIncludeSellRadioChange: handleChangeIncludeSellRadio,
      includeSellRadioOptions: [
        { label: "네", value: true },
        { label: "아니오", value: false },
      ],
      annualIncomeValue: annualIncome,
      onAnnualIncomeChange: handleAnnualIncomeChange,
      onAnnualIncomeEnter: handleAnnualIncomeEnter,
      isFirstTimeBuyer: isFirstTimeBuyer,
      onIsFirstTimeBuyerRadioChange: handleIsFirstTimeBuyerRadioChange,
      isFirstTimeBuyerRadioOptions: [
        { label: "네", value: false },
        { label: "아니오", value: true },
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
