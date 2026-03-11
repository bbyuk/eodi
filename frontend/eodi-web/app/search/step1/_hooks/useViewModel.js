import { useState } from "react";

export default function useViewModel() {
  const [annualIncome, setAnnualIncome] = useState();
  const [mortgageLoanInterestRate, setMortgageLoanInterestRate] = useState();
  const [mortgageLoanPeriod, setMortgageLoanPeriod] = useState();

  return {
    state: {
      annualIncome,
      mortgageLoanInterestRate,
      mortgageLoanPeriod,
    },
    derived: {
      pageHeaderTitle: "예산을 입력해주세요",
      pageHeaderDescription: ["입력한 금액으로 매수, 전·월세가 가능한 지역을 바로 찾아드릴게요."],
    },
    actions: {
      setAnnualIncome,
      setMortgageLoanInterestRate,
      setMortgageLoanPeriod,
    },
  };
}
