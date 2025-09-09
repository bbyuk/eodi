package com.bb.eodi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * eodi 서비스 공통 설정 값
 */
@ConfigurationProperties(prefix = "eodi.service")
public record EodiServiceProperties(
        List<String> locations
) {
}
