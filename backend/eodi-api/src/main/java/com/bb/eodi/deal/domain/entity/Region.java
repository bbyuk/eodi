package com.bb.eodi.deal.domain.entity;

import lombok.*;

/**
 * 부동산 거래 지역
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Region {
    // 법정동 테이블 key
    private Long id;
    // 법정동 코드
    private String code;
    // 법정동 명
    private String name;
    // 법정동 서열
    private int legalDongOrder;
    // 최상위 법정동 id
    private Long rootId;
    // 차상위 법정동 id
    private Long secondId;
}
