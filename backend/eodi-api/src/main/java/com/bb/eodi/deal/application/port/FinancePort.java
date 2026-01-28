package com.bb.eodi.deal.application.port;

/**
 * 금융 및 부동산 정책 관련 계산 port
 */
public interface FinancePort {

    /**
     * 연소득, 대출 월 상환액으로 주담대 가능 금액을 계산한다. (만 원 단위)
     * @param annualIncomeAmount    연소득
     * @param monthlyPaymentAmount  대출 월 상환액
     * @return 주담대 가능 금액 (만 원 단위)
     */
    int calculateAvailableMortgageLoanAmount(int annualIncomeAmount, int monthlyPaymentAmount);

    /**
     * 전세자금대출 가능 금액을 계산한다. (만 원 단위)
     *
     * @param cash 보유 현금
     * @return 전세자금대출 가능 금액 (만 원 단위)
     */
    int calculateAvailableDepositLoanAmount(int cash);
}
