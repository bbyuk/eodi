package com.bb.eodi.finance.application.result;

import lombok.Builder;
import lombok.Data;

/**
 * 주택담보대출 계산 결과
 */
@Data
@Builder
public class MortgageLoanLimitCalculateResult {
    private int amount;
}
