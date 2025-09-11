package com.bb.eodi.infrastructure.http.deal;

import com.bb.eodi.infrastructure.api.govdata.GovernmentDataApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@RequiredArgsConstructor
public class DealDataApiClientConfig {

    private final GovernmentDataApiProperties governmentDataApiProperties;
    @Bean
    public DealDataApi dealDataApi(RestClient.Builder builder) {
        // HttpExchange의 url과 합쳐짐
        RestClient restClient = builder
                .baseUrl(governmentDataApiProperties.baseUrl())
                .defaultHeader(HttpHeaders.USER_AGENT, "eodi-batch")
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(DealDataApi.class);
    }
}
