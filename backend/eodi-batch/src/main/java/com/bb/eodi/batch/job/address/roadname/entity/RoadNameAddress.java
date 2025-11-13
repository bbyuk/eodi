package com.bb.eodi.batch.job.address.roadname.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

/**
 * 도로명 주소 address 도메인 entity
 */
@Data
@Builder
public class RoadNameAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "road_name_code", length = 12, nullable = false)
    private String roadNameCode;

    @Column(name = "sigungu_code", length = 5, nullable = false)
    private String sigunguCode;

    @Column(name = "road_name_no", length = 7, nullable = false)
    private String roadNameNo;

    @Column(name = "road_name", length = 80, nullable = false)
    private String roadName;
    private String engRoadName;
    private String umdSeq;
    private String sidoName;
    private String sigunguName;
    private String umdGb;
    private String umdCode;
    private String umdName;
    private String parentRoadNameNo;
    private String parentRoadName;
    private String useYn;
    private String changeHistoryReason;
    private String changeHistoryInfo;
    private String engSidoName;
    private String engSigunguName;
    private String engUmdName;
    private String announcementDate;
    private String expirationDate;
}
