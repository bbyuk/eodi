package com.bb.eodi.finance.application.service;

import com.bb.eodi.finance.application.input.MortgageLoanLimitCalculateInput;

/**
 * 주택담보대출 정책 인터페이스
 */
public interface MortgagePolicy {

    /**
     * 기본 LTV를 조회한다.
     * @return 기본 LTV 한도
     */
    int getDefaultLtv();

    /**
     * LTV를 계산한다.
     * @param input 주택담보대출 한도 계산 입력
     * @return LTV 한도 %
     */
    int calculateLtv(MortgageLoanLimitCalculateInput input);

    /**
     * 주택담보대출 금액 한도를 계산한다.
     * @param input 주택담보대출 한도 계산 입력
     * @return 주택담보대출 금액 한도
     */
    int calculateLimitAmount(MortgageLoanLimitCalculateInput input);


}
