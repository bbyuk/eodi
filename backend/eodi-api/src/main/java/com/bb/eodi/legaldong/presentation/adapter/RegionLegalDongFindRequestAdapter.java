package com.bb.eodi.legaldong.presentation.adapter;

import com.bb.eodi.legaldong.application.input.RegionLegalDongFindInput;
import com.bb.eodi.legaldong.presentation.dto.request.RegionLegalDongFindRequest;
import org.springframework.stereotype.Component;

/**
 * 지역 단위 법정동 조회 API 요청 파라미터 application input adapter
 */
@Component
public class RegionLegalDongFindRequestAdapter {

    public RegionLegalDongFindInput toInput(RegionLegalDongFindRequest requestParameter) {
        return new RegionLegalDongFindInput(requestParameter.code());
    }
}
