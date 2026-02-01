package com.bb.eodi.finance.application.result;

import lombok.Builder;
import lombok.Data;

/**
 * 주택담보대출 계산 결과
 */
@Data
@Builder
public class MortgageLoanCalculateResult {

    // 한도 금액 제한 여부
    private boolean hasLimitAmount = false;
    // ltv
    private int ltv;
    // 한도 금액
    private long limitAmount;

    public boolean hasLimitAmount() {
        return hasLimitAmount;
    }

    /**
     * remove lombok wrong getter
     */
    private boolean isHasLimitAmount() {
        return hasLimitAmount;
    }
}
