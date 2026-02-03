package com.bb.eodi.finance.application.input;

import java.time.LocalDate;
import java.util.List;

/**
 * 규제지역 등록 application input
 */
public record RegulatingAreaRegisterInput(
        List<String> targetRegionNames,
        LocalDate effectiveStartDate,
        LocalDate effectiveEndDate
) {
}
