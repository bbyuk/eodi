package com.bb.eodi.legaldong.domain.query;

/**
 * 법정동 도메인 조회 query
 */
public record LegalDongFindQuery(
        /**
         * 법정동 조회 scope
         */
        LegalDongScope scope,
        /**
         * 기준 법정동 ID
         */
        String baseLegalDongCode
) {
}
