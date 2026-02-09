package com.bb.eodi.deal.application.contract;

import java.util.Set;

/**
 * 크로스도메인 법정동 정보
 */
public record DealLegalDongInfo(
        // 법정동 ID
        Long id,
        // 법정동 코드
        String code,
        // 법정동 명
        String name,
        // 법정동 서열
        int order,
        // 최상위 법정동(시/도) ID
        Long rootId,
        // 차상위 법정동(시/군/구) ID
        Long secondId,
        // 상위 법정동 ID
        Long parentId,
        // 하위 법정동 ID Set
        Set<DealLegalDongInfo> children
) {

}
