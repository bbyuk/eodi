package com.bb.eodi.finance.domain.vo;

/**
 * 보유 대출
 */
public class ExistingLoan {

    // 월 상환액
    private final Integer monthlyPayment;

    private ExistingLoan(Integer monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public static ExistingLoan none() {
        return new ExistingLoan(0);
    }

    public static ExistingLoan of(Integer monthlyPayment) {
        if (monthlyPayment == null || monthlyPayment <= 0) {
            throw new IllegalArgumentException("대출의 월 상환액은 0보다 커야합니다.");
        }
        return new ExistingLoan(monthlyPayment);
    }

    public Integer annualPayment() {
        return 12 * monthlyPayment;
    }

}
