package com.bb.eodi.batch.job.address.roadname.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 도로명 주소 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 건물DB
 * https://business.juso.go.kr/addrlink/main.do
 */
@Builder
@AllArgsConstructor
public class RoadNameAddressItem {
    // 도로명코드
    private String roadNameCode;
    // 시군구코드
    private String sigunguCode;
    // 도로명번호
    private String roadNameNo;
    // 도로명
    private String roadName;
    // 영문도로명
    private String engRoadName;
    // 읍면동일련번호
    private String umdSeq;
    // 시도명
    private String sidoName;
    // 시군구명
    private String sigunguName;
    // 읍면동구분
    private String umdGb;
    // 읍면동코드
    private String umdCode;
    // 읍면동명
    private String umdName;
    // 상위도로명번호
    private String parentRoadNameNo;
    // 상위도로명
    private String parentRoadName;
    // 사용여부
    private String useYn;
    // 변경이력사유
    private String changeHistoryReason;
    // 변경이력정보
    private String changeHistoryInfo;
    // 영문시도명
    private String engSidoName;
    // 영문시군구명
    private String engSigunguName;
    // 영문읍면동명
    private String engUmdName;
    // 고시일자
    private String announcementDate;
    // 말소일자
    private String expirationDate;
}
