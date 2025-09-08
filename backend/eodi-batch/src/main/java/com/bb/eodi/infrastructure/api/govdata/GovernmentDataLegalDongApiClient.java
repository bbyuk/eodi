package com.bb.eodi.infrastructure.api.govdata;

import com.bb.eodi.batch.legaldong.load.api.LegalDongApiClient;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponse;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

/**
 * 공공 데이터 법정동 API 클라이언트
 */
@Slf4j
@Component
public class GovernmentDataLegalDongApiClient implements LegalDongApiClient {

    private final ObjectMapper objectMapper;
    private final GovernmentDataApiProperties governmentDataApiProperties;
    private final WebClient webClient;


    public GovernmentDataLegalDongApiClient(GovernmentDataApiProperties governmentDataApiProperties, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.governmentDataApiProperties = governmentDataApiProperties;
        this.webClient = WebClient.builder()
                .baseUrl(governmentDataApiProperties.baseUrl())
                .build();
    }

    private LegalDongApiResponse callApiWithRegionParameter(String targetRegion, int pageSize) {
        String responseBody = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(governmentDataApiProperties.table()
                                .get("legal-dong")
                                .path()
                        )
                        .queryParam(governmentDataApiProperties.keyParameterName(), governmentDataApiProperties.key())
                        .queryParam("numOfRows", pageSize)
                        .queryParam("locatadd_nm", targetRegion)
                        .queryParam("pageNo", 1)
                        .queryParam("type", "json")
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        /**
         * 공공 데이터 API가 http status 표준을 따르지 않음.
         */
        LegalDongApiResponse legalDongApiResponse;
        try {
            legalDongApiResponse = objectMapper.readValue(responseBody, LegalDongApiResponse.class);
        }
        catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        if (!legalDongApiResponse.isSuccess()) {
            throw new IllegalStateException("공공 데이터 API 요청에 실패했습니다.");
        }

        return legalDongApiResponse;
    }


    @Override
    public int getTotalCount(String targetRegion) {
        // 전체 count를 가져오기 위한 init api 요청
        LegalDongApiResponse legalDongApiResponse = callApiWithRegionParameter(targetRegion, 1);

        /**
         * head 파싱해 totalCount get
         */
        List<Map<String, Object>> heads = (List) legalDongApiResponse.StanReginCd().get(0).get("head");
        return (int) heads.get(0).get("totalCount");
    }

    @Override
    public List<LegalDongApiResponseRow> findByRegion(String targetRegion) {
        LegalDongApiResponse legalDongApiResponse = callApiWithRegionParameter(targetRegion, governmentDataApiProperties.pageSize());
        JavaType type = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, LegalDongApiResponseRow.class);
        return objectMapper.convertValue(legalDongApiResponse.StanReginCd().get(1).get("row"), type);
    }



}
