package com.bb.eodi.address.job.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 부가정보 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 주소DB
 * https://business.juso.go.kr/addrlink/main.do
 */
@Data
@Builder
public class AdditionalInfoItem {

    // 관리번호
    private String manageNo;

    // 행정동코드
    private String admDongCode;

    // 행정동명
    private String admDongName;

    // 우편번호
    private String zipNo;

    // 우편번호일련번호
    private String zipNoSeq;

    // 다량배달처명
    private String multipleDeliveryName;

    // 건축물대장 건물명
    private String buildingName;

    // 시군구 건물명
    private String sigunguBuildingName;

    // 공동주택여부
    private String isMultiplex;

}
