package com.bb.eodi.finance.application.service;

import org.springframework.stereotype.Component;

/**
 * 전세자금대출 정책 2026
 */
@Component
public class LeaseLoanPolicy20260101 implements LeaseLoanPolicy {

    @Override
    public int getLimitPercent() {
        return 80;
    }
}
