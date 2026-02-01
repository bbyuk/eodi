package com.bb.eodi.finance.infrastructure.adapter;

import com.bb.eodi.deal.application.port.DealFinancePort;
import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;
import com.bb.eodi.finance.application.result.MortgageLoanCalculateResult;
import com.bb.eodi.finance.application.service.MortgageLoanCalculateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 대출 정책 관련 로직 처리 구현 adapter
 */
@Component
@RequiredArgsConstructor
public class DealFinanceAdapter implements DealFinancePort {

    private final MortgageLoanCalculateService mortgageLoanCalculateService;

    @Override
    public int calculateAvailableMortgageLoanAmount(
            int annualIncomeAmount,
            int monthlyPaymentAmount,
            boolean isFirstTimeBuyer,
            long legalDongId,
            int price) {

        MortgageLoanLimitCalculateInput.PersonInfo personInfo = MortgageLoanLimitCalculateInput.PersonInfo.builder()
                .annualIncome(annualIncomeAmount)
                .monthlyPayment(monthlyPaymentAmount)
                .isFirstTimeBuyer(isFirstTimeBuyer)
                .build();
        MortgageLoanLimitCalculateInput.HouseInfo houseInfo = MortgageLoanLimitCalculateInput.HouseInfo.builder()
                .legalDongId(legalDongId)
                .price(price)
                .build();

        MortgageLoanLimitCalculateInput input = MortgageLoanLimitCalculateInput.builder()
                .personInfo(personInfo)
                .houseInfo(houseInfo)
                .build();

        MortgageLoanCalculateResult result = mortgageLoanCalculateService.calculateMortgageLoanLimit(input);

        int calculatedLtvLoanAmount = price * result.getLtv() / 100;
        return result.hasLimitAmount()
                ? Math.min(result.getLimitAmount(), calculatedLtvLoanAmount)
                : calculatedLtvLoanAmount;
    }

    @Override
    public int calculateMaximumMortgageLoanAmount(int cash) {
        return mortgageLoanCalculateService.calculateMaximumMortgageLoanAmount(cash);
    }

    @Override
    public int calculateAvailableDepositLoanAmount(int cash) {
        return 0;
    }
}
