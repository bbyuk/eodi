package com.bb.eodi.address.job.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 지번 주소 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 도로명주소 한글 관련지번
 * https://business.juso.go.kr/addrlink/main.do
 */
@Builder
@Getter
@AllArgsConstructor
public class LandLotAddressItem {

    // 도로명주소관리번호
    private String manageNo;

    // 법정동코드
    private String legalDongCode;

    // 시도명
    private String sidoName;

    // 시군구명
    private String sigunguName;

    // 법정읍면동명
    private String umdName;

    // 법정리명
    private String riName;

    // 산여부
    private String isMountain;

    // 지번본번
    private String landLotMainNo;

    // 지번부번
    private String landLotSubNo;

    // 도로명코드
    private String roadNameCode;

    // 지하여부
    private String isUnderground;

    // 건물본번
    private String buildingMainNo;

    // 건물부번
    private String buildingSubNo;

    // 이동사유코드
    private String updateReasonCode;

}
