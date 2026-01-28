package com.bb.eodi.finance.application.input;

import lombok.Data;

/**
 * 주택담보대출 한도 계산 Input
 */
@Data
public class MortgageLoanLimitCalculateInput {

    // 연소득
    private int annualIncome;

    // 월상환액
    private int monthlyPayment;
}
