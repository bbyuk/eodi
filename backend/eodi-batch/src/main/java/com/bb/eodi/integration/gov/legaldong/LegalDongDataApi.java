package com.bb.eodi.integration.gov.legaldong;

import com.bb.eodi.integration.gov.legaldong.dto.LegalDongApiResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * 법정동 데이터 외부 API 스펙
 */
@HttpExchange(
        url = "/1741000"
)
public interface LegalDongDataApi {

    /**
     * 지역별 법정동 조회 - 지역명 조회
     * @param pageNum 현재 페이지
     * @param pageSize 페이지 크기
     * @param targetRegion 대상 지역명
     * @param type API type
     * @return 법정동 조회 API 응답
     */
    @GetExchange("/StanReginCd/getStanReginCdList")
    String getLegalDong(
            @RequestParam("pageNo") int pageNum,
            @RequestParam("numOfRows") int pageSize,
            @RequestParam("locatadd_nm") String targetRegion,
            @RequestParam("type") String type
    );
}
