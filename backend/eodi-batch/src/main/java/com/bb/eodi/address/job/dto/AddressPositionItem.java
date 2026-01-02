package com.bb.eodi.address.job.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 주소위치정보 전체분 및 변동분 Item
 */
@Data
@Builder
public class AddressPositionItem {

    // 도로명주소관리번호 (PK)
    private String manageNo;

    // 법정동코드
    private String legalDongCode;

    // 시도명
    private String sidoName;

    // 시군구명
    private String sigunguName;

    // 읍면동명
    private String umdName;

    // 리명
    private String riName;

    // 도로명코드(PK)
    private String roadNameCode;

    // 도로명
    private String roadName;

    // 지하여부 (PK)
    private String isUnderground;

    // 건물본번 (PK)
    private String buildingMainNo;

    // 건물부번 (PK)
    private String buildingSubNo;

    // 기초구역번호
    private String basicDistrictNo;

    // 효력발생일
    private String effectStartDate;

    // 이동사유코드 (31: 신규 / 34: 수정 / 63: 폐지)
    private String updateReasonCode;

    // 출입구일련번호
    private String entranceSeq;

    // 출입구구분 (RM: 주출입구)
    private String entranceCode;

    // 출입구유형 (01: 공용 / 02: 차량용)
    private String entranceType;

    // 출입구좌표X
    private String xPos;

    // 출입구좌표Y
    private String yPos;


}
