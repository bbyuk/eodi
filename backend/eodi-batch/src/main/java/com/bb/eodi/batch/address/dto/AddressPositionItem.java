package com.bb.eodi.batch.address.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 주소위치정보 전체분 및 변동분 Item
 */
@Data
@Builder
public class AddressPositionItem {

    // 시군구코드
    private String sigunguCode;

    // 출입구일련번호
    private String entranceSeq;

    // 법정동코드
    private String legalDongCode;

    // 시도명
    private String sidoName;

    // 시군구명
    private String sigunguName;

    // 읍면동명
    private String umdName;

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

    // 건물명
    private String buildingName;

    // 우편번호
    private String zipNo;

    // 건물용도분류
    private String buildingType;

    // 건물군여부
    private String isBuildingGroup;

    // 관할행정동
    private String admDong;

    // X좌표
    private String xPos;

    // y좌표
    private String yPos;

    // 이동사유코드
    private String changeReasonCode;

}
