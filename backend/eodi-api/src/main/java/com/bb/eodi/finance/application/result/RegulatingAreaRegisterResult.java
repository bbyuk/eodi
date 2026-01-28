package com.bb.eodi.finance.application.result;

import lombok.Builder;
import lombok.Data;

/**
 * 부동산 규제지역 등록 application result
 */
@Data
@Builder
public class RegulatingAreaRegisterResult {
    private int count;
}
