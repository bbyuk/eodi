package com.bb.eodi.infrastructure.api.deal;

import com.bb.eodi.infrastructure.api.GovernmentDataApiProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
                .requestInterceptor((request, body, execution) -> {
                    // 원래 URI
                    URI originalUri = request.getURI();

                    // 기존 쿼리 파라미터 + serviceKey 붙이기
                    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUri(originalUri)
                            .queryParam(
                                    governmentDataApiProperties.keyParameterName(),
                                    governmentDataApiProperties.key()
                            );

                    URI newUri = uriBuilder.build(true).toUri();

                    // 새 RequestWrapper로 URI 교체
                    HttpRequest wrapper = new HttpRequestWrapper(request) {
                        @Override
                        public URI getURI() {
                            return newUri;
                        }
                    };

                    return execution.execute(wrapper, body);
                })
                .build();

        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(DealDataApi.class);
    }
}
