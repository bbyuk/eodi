import { useState } from "react";

export default function useViewModel() {
  const [withLoan, setWithLoan] = useState(false);
  const [annualIncome, setAnnualIncome] = useState();
  const [isFirstTimeBuyer, setIsFirstTimeBuyer] = useState(false);
  const [mortgageLoanInterestRate, setMortgageLoanInterestRate] = useState();
  const [mortgageLoanPeriod, setMortgageLoanPeriod] = useState();

  return {
    state: {
      withLoan,
      annualIncome,
      isFirstTimeBuyer,
      mortgageLoanInterestRate,
      mortgageLoanPeriod,
    },
    derived: {
      pageHeaderTitle: "예산을 입력해주세요",
      pageHeaderDescription: ["입력한 금액으로 매수, 전·월세가 가능한 지역을 바로 찾아드릴게요."],
    },
    actions: {
      setWithLoan,
      setAnnualIncome,
      setIsFirstTimeBuyer,
      setMortgageLoanInterestRate,
      setMortgageLoanPeriod,
    },
  };
}
