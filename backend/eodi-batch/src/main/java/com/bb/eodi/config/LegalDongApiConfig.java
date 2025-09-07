package com.bb.eodi.config;

import com.bb.eodi.infrastructure.legaldong.api.LegalDongApiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URI;

/**
 * 법정동 API 설정
 */
@Configuration
@EnableConfigurationProperties(LegalDongApiProperties.class)
public class LegalDongApiConfig {


    @Bean("legalDongApiClient")
    public WebClient legalDongApiClient(LegalDongApiProperties apiProperties) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(apiProperties.url());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        return WebClient.builder()
                .uriBuilderFactory(factory)
                .filter((request, next) -> {
                    URI uri = factory.builder().path(request.url().getPath())
                            .queryParam(apiProperties.keyParameterName(), apiProperties.key())
                            .build();
                    ClientRequest newRequest = ClientRequest.from(request).url(uri).build();
                    return next.exchange(newRequest);
                })
                .build();
    }
}
