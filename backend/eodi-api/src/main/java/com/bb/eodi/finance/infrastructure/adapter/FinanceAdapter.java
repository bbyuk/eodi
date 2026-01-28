package com.bb.eodi.finance.infrastructure.adapter;

import com.bb.eodi.deal.application.port.FinancePort;
import org.springframework.stereotype.Component;

/**
 * 대출 정책 관련 로직 처리 구현 adapter
 */
@Component
public class FinanceAdapter implements FinancePort {

    @Override
    public int calculateAvailableMortgageLoanAmount(int annualIncomeAmount, int monthlyPaymentAmount) {
        return 0;
    }

    @Override
    public int calculateAvailableDepositLoanAmount(int cash) {
        return 0;
    }
}
