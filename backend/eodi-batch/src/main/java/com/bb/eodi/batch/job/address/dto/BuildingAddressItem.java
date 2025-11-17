package com.bb.eodi.batch.job.address.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 건물 주소 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 건물DB
 * https://business.juso.go.kr/addrlink/main.do
 */
@Builder
@Getter
@AllArgsConstructor
public class BuildingAddressItem {
    // 법정동코드
    private String legalDongCode;
    // 시도명
    private String sidoName;
    // 시군구명
    private String sigunguName;
    // 법정읍면동명
    private String legalUmdName;
    // 법정리명
    private String legalRiName;
    // 산여부
    private String isMountain;
    // 지번본번(번지)
    private String landLotMainNo;
    // 지번부번(호)
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
    // 건축물대장건물명
    private String buildingName;
    // 상세건물명
    private String buildingNameDetail;
    // 건물관리번호
    private String buildingManageNo;
    // 읍면동일련번호
    private String umdSeq;
    // 행정동코드
    private String admDongCode;
    // 행정동명
    private String admDongName;
    // 우편번호
    private String zipNo;
    // 우편번호일련번호
    private String zipNoSeq;
    // 이동사유코드
    private String changeReasonCode;
    // 고시일자
    private String announcementDate;
    // 시군구용건물명
    private String sigunguBuildingName;
    // 공동주택여부
    private String isComplex;
    // 기초구역번호
    private String basicDistrictNo;
    // 상세주소여부
    private String hasDetailAddress;
    // 비고1
    private String remark1;
    // 비고2
    private String remark2;

}
