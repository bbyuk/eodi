package com.bb.eodi.batch.job.address.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 도로명 주소 address 도메인 entity
 */
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "road_name_address")
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

    @Column(name = "eng_road_name", length = 80)
    private String engRoadName;

    @Column(name = "umd_seq", length = 2, nullable = false)
    private String umdSeq;

    @Column(name = "sido_name", length = 40)
    private String sidoName;

    @Column(name = "sigungu_name", length = 40)
    private String sigunguName;

    @Column(name = "umd_gb", length = 1)
    private String umdGb;

    @Column(name = "umd_code", length = 3)
    private String umdCode;

    @Column(name = "umd_name", length = 40)
    private String umdName;

    @Column(name = "parent_road_name_no", length = 7)
    private String parentRoadNameNo;

    @Column(name = "parent_road_name", length =80)
    private String parentRoadName;

    @Column(name = "use_yn", length = 1)
    private String useYn;

    @Column(name = "change_history_reason", length = 1)
    private String changeHistoryReason;

    @Column(name = "change_history_info", length = 14)
    private String changeHistoryInfo;

    @Column(name = "eng_sido_name", length = 40)
    private String engSidoName;

    @Column(name= "eng_sigungu_name", length = 40)
    private String engSigunguName;

    @Column(name = "eng_umd_name", length = 8)
    private String engUmdName;

    @Column(name = "announcement_date", length = 8)
    private String announcementDate;

    @Column(name = "expiration_date", length = 8)
    private String expirationDate;
}
