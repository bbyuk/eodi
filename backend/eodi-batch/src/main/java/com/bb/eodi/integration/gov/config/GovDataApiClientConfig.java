package com.bb.eodi.integration.gov.config;

import com.bb.eodi.integration.gov.deal.DealDataApi;
import com.bb.eodi.integration.gov.legaldong.LegalDongDataApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GovDataApiClientConfig {

    private final GovernmentDataApiProperties governmentDataApiProperties;


    /**
     * 공공데이터 포털 API RestClient
     * @param builder 기본 RestClient.Builder bean
     * @return 공공데이터 포털 API RestClient
     */
    @Bean
    public RestClient govDataRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl(governmentDataApiProperties.baseUrl())
                .defaultHeader(HttpHeaders.USER_AGENT, "eodi-batch")
                .requestInterceptor((request, body, execution) -> {
                    // 기존 URI
                    URI originUri = request.getURI();

                    // 기존 쿼리 파라미터 + serviceKey 붙이기
                    URI newUri = UriComponentsBuilder.fromUri(originUri)
                            .queryParam("serviceKey", governmentDataApiProperties.key())
                            .build(true)
                            .toUri();

                    // 요청 로그
                    if (log.isDebugEnabled()) {
                        log.debug("=== [HTTP REQUEST] ===");
                        log.debug("{} {}", request.getMethod(), newUri);
                        request.getHeaders().forEach((k, v) ->
                                log.debug("{}: {}", k, String.join(", ", v))
                        );
                        if (body.length > 0) {
                            log.debug("-- Body --\n{}", new String(body, StandardCharsets.UTF_8));
                        }
                        log.debug("=======================");
                    }

                    // 실제 요청 실행
                    var response = execution.execute(new HttpRequestWrapper(request) {
                        @Override
                        public URI getURI() {
                            return newUri;
                        }
                    }, body);

                    // 응답 로그
                    if (log.isDebugEnabled()) {
                        log.debug("=== [HTTP RESPONSE] ===");
                        log.debug("Status: {}", response.getStatusCode());
                        response.getHeaders().forEach((k, v) ->
                                log.debug("{}: {}", k, String.join(", ", v))
                        );
                        log.debug("=======================");
                    }

                    return response;
                })
                .build();
    }


    /**
     * 실거래가 데이터 API HttpServiceProxy bean
     * @param govDataRestClient 공공데이터 포털 API RestClient
     * @return 실거래가 데이터 API HttpServiceProxy bean
     */
    @Bean
    public DealDataApi dealDataApi(RestClient govDataRestClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(govDataRestClient))
                .build()
                .createClient(DealDataApi.class);
    }

    /**
     * 법정동 데이터 API HttpServiceProxy bean
     * @param govDataRestClient 공공데이터 포털 API RestClient
     * @return 법정동 데이터 API HttpServiceProxy bean
     */
    @Bean
    public LegalDongDataApi legalDongDataApi(RestClient govDataRestClient) {
        return HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(govDataRestClient))
                .build()
                .createClient(LegalDongDataApi.class);
    }

}
