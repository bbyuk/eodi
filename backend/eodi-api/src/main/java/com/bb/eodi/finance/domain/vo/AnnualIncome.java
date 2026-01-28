package com.bb.eodi.finance.domain.vo;

/**
 * 연 소득
 */
public class AnnualIncome {

    // 값
    private final Integer amount;

    public AnnualIncome(Integer amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("연 소득은 0보다 커야합니다.");
        }

        this.amount = amount;
    }

}
