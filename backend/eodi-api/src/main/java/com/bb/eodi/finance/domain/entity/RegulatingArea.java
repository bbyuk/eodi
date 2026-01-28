package com.bb.eodi.finance.domain.entity;

import lombok.*;

import java.time.LocalDate;

/**
 * 규제지역
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegulatingArea {
    // 규제지역 ID
    private Long id;

    // 법정동 ID
    private Long legalDongId;

    // 적용시작일자
    private LocalDate effectiveStartDate;

    // 적용종료일자
    private LocalDate effectiveEndDate;

    /**
     * 대상 규제지역을 폐지한다.
     */
    public void revocate() {
        this.effectiveEndDate = LocalDate.now().minusDays(1);
    }
}
