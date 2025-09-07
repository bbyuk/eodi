package com.bb.eodi.infrastructure.legaldong.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 법정동코드 API 설정값
 */
@ConfigurationProperties(prefix = "api.legal-dong")
public record LegalDongApiProperties(
        String url,
        String key,
        String keyParameterName,
        int pageSize
) {}
