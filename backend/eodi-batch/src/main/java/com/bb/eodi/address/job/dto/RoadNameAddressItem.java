package com.bb.eodi.address.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * 도로명 주소 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 건물DB
 * https://business.juso.go.kr/addrlink/main.do
 */
@Getter
@Builder
@ToString
@AllArgsConstructor
public class RoadNameAddressItem {

    // 관리번호
    private String manageNo;

    // 도로명코드
    private String roadNameCode;

    // 읍면동일련번호
    private String umdSeq;

    // 지하여부
    private String isUnderground;

    // 건물본번
    private String buildingMainNo;

    // 건물부번
    private String buildingSubNo;

    // 기초구역번호
    private String basicDistrictNo;

    // 고시일자
    private String entranceDate;

    // 변경사유코드
    private String changeReasonCode;

    // 변경전도로명주소
    private String beforeChangeRoadNameAddress;

    // 상세주소부여여부
    private String hasDetailAddress;

}
