package com.bb.eodi.integration.gov.legaldong;

import com.bb.eodi.integration.gov.config.GovernmentDataApiProperties;
import com.bb.eodi.integration.gov.legaldong.dto.LegalDongApiResponse;
import com.bb.eodi.integration.gov.legaldong.dto.LegalDongApiResponseRow;
import com.bb.eodi.legaldong.domain.port.LegalDongDataPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 공공 데이터 법정동 API 클라이언트
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LegalDongDataApiClient implements LegalDongDataPort {

    private final ObjectMapper objectMapper;
    private final LegalDongDataApi legalDongDataApi;
    private final GovernmentDataApiProperties governmentDataApiProperties;
    private static final String API_TYPE = "json";

    /**
     * 대상 지역의 전체 법정동 수를 조회한다.
     *
     * @param targetRegion 대상 지역명
     * @return 대상 지역에 속한 전체 법정동 수
     */
    @Override
    public int getTotalCount(String targetRegion) {
        // 전체 count를 가져오기 위한 init api 요청
        LegalDongApiResponse legalDongApiResponse;

        try {
            legalDongApiResponse = objectMapper.readValue(legalDongDataApi.getLegalDong(
                    0,
                    governmentDataApiProperties.pageSize(),
                    targetRegion,
                    API_TYPE
            ), LegalDongApiResponse.class);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        if (!legalDongApiResponse.isSuccess()) {
            throw new IllegalStateException("공공 데이터 API 요청에 실패했습니다.");
        }

        // head 파싱해 totalCount get
        List<Map<String, Object>> heads = (List) legalDongApiResponse.StanReginCd().get(0).get("head");
        return (int) heads.get(0).get("totalCount");
    }

    /**
     * 대상 지역명으로 공공데이터 포털 API를 요청해 페이지 조회한다.
     *
     * @param targetRegion 대상 지역명
     * @param pageNum      현재 페이지
     * @return 대상지역에 속한 법정동 API 응답 목록
     */
    @Override
    public List<LegalDongApiResponseRow> findByRegion(String targetRegion, int pageNum) {
        LegalDongApiResponse legalDongApiResponse;

        try {
            legalDongApiResponse = objectMapper.readValue(legalDongDataApi.getLegalDong(
                    pageNum,
                    governmentDataApiProperties.pageSize(),
                    targetRegion,
                    API_TYPE
            ), LegalDongApiResponse.class);
        } catch (JsonMappingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        if (!legalDongApiResponse.isSuccess()) {
            throw new IllegalStateException("공공 데이터 API 요청에 실패했습니다.");
        }
        JavaType type = objectMapper
                .getTypeFactory()
                .constructCollectionType(List.class, LegalDongApiResponseRow.class);
        return objectMapper.convertValue(legalDongApiResponse.StanReginCd().get(1).get("row"), type);
    }

    /**
     * API 요청시 파라미터로 넘기는 페이지 크기를 리턴한다.
     *
     * @return 페이지 크기
     */
    @Override
    public int getPageSize() {
        return governmentDataApiProperties.pageSize();
    }
}
