package com.bb.eodi.infrastructure.api;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 공공 데이터 API 설정값
 */
@ConfigurationProperties(prefix = "api.gov-data")
public record GovernmentDataApiProperties(
        String baseUrl,
        int pageSize,
        String keyParameterName,
        String key,
        Map<String, GovernmentDataApiDetailProperties> table
) {
}
