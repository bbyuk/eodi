package com.bb.eodi.deal.application.port;

/**
 * 금융 및 부동산 정책 관련 계산 port
 */
public interface DealFinancePort {

    /**
     * 연소득, 대출 월 상환액으로 주담대 가능 금액을 계산한다. (만 원 단위)
     * @param annualIncomeAmount    연소득
     * @param monthlyPaymentAmount  대출 월 상환액
     * @param isFirstTimeBuyer      생애최초 구매여부
     * @param legalDongId           대상 물건 법정동 ID
     * @param price                 대상 물건가격
     * @return 주담대 가능 금액 (만 원 단위)
     */
    int calculateAvailableMortgageLoanAmount(
            int annualIncomeAmount,
            int monthlyPaymentAmount,
            boolean isFirstTimeBuyer,
            long legalDongId,
            int price
    );

    /**
     * 현재 보유한 현금 기준으로 기본 LTV 한도만 적용해 대출가능한 최대 금액을 계산한다.
     * @param cash 보유한 현금
     * @return 대출 가능 최대금액
     */
    int calculateMaximumMortgageLoanAmount(int cash);

    /**
     * 전세자금대출 가능 금액을 계산한다. (만 원 단위)
     *
     * @param cash 보유 현금
     * @return 전세자금대출 가능 금액 (만 원 단위)
     */
    int calculateAvailableDepositLoanAmount(int cash);
}
