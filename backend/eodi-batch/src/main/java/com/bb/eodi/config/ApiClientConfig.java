package com.bb.eodi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

/**
 * 공통 API Client 설정
 */
@Configuration
public class ApiClientConfig {

    @Bean
    public RestClient syncApiClient(RestClient.Builder builder) {
        return builder
                .defaultHeader(HttpHeaders.USER_AGENT, "eodi-batch/1.0")
                .build();
    }
}
