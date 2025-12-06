package com.bb.eodi.address.job.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 도로명코드 원천 데이터 Item
 * <p>
 * 출처 : 주소기반산업지원서비스 - 주소DB
 * https://business.juso.go.kr/addrlink/main.do
 */
@Getter
@Builder
@AllArgsConstructor
public class RoadNameCodeItem {

    // 도로명코드
    private String roadNameCode;
    // 도로명
    private String roadName;
    // 도로명로마자
    private String engRoadName;
    // 읍면동일련번호
    private String umdSeq;
    // 시도명
    private String sidoName;
    // 시도 로마자
    private String engSidoName;
    // 시군구명
    private String sigunguName;
    // 시군구 로마자
    private String engSigunguName;
    // 읍면동명
    private String umdName;
    // 읍면동 로마자
    private String engUmdName;
    // 읍면동구분
    private String umdType;
    // 읍면동코드
    private String umdCode;
    // 사용여부
    private String enabled;
    // 변경사유
    private String changeCode;
    // 변경이력정보
    private String changeLog;
    // 고시일자
    private String entranceDate;
    // 말소일자
    private String revocationDate;


}
