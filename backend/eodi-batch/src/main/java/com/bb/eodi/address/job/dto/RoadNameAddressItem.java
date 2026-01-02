package com.bb.eodi.address.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 도로명 주소 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 도로명주소 한글
 * https://business.juso.go.kr/addrlink/main.do
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
public class RoadNameAddressItem {
    
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

    // 도로명
    private String roadName;

    // 지하여부
    private String isUnderground;

    // 건물본번
    private String buildingMainNo;

    // 건물부번
    private String buildingSubNo;

    // 행정동코드
    private String admDongCode;

    // 행정동명
    private String admDongName;

    // 기초구역번호
    private String basicDistrictNo;

    // 이전도로명주소
    private String beforeRoadNameAddress;

    // 효력발생일
    private String effectStartDate;

    // 공동주택구분
    private String isMulti;

    // 이동사유코드
    private String updateReasonCode;

    // 건축물대장건물명
    private String buildingName;

    // 시군구용건물명
    private String sigunguBuildingName;

    // 비고
    private String remark;

}
