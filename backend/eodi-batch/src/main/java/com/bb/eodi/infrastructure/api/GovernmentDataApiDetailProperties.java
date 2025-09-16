package com.bb.eodi.infrastructure.api;

import java.time.LocalDate;

/**
 * 공공 데이터 API 상세 정보
 */
public record GovernmentDataApiDetailProperties(
        String phase,
        String path,
        LocalDate expiration
) {}
