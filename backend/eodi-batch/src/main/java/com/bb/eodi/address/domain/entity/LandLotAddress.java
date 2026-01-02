package com.bb.eodi.address.domain.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 지번주소 address 도메인 entity
 */
@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "land_lot_address")
public class LandLotAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "manage_no", length = 26, nullable = false)
    private String manageNo;

    @Column(name = "legal_dong_code", length = 10, nullable = false)
    private String legalDongCode;

    @Column(name = "sido_name", length = 40)
    private String sidoName;

    @Column(name = "sigungu_name", length = 40)
    private String sigunguName;

    @Column(name = "umd_name", length = 40)
    private String umdName;

    @Column(name = "ri_name", length = 40)
    private String riName;

    @Column(name = "is_mountain", length = 1, nullable = false)
    private String isMountain;

    @Column(name = "land_lot_main_no", nullable = false)
    private Integer landLotMainNo;

    @Column(name = "land_lot_sub_no", nullable = false)
    private Integer landLotSubNo;

    @Column(name = "road_name_code", length = 12, nullable = false)
    private String roadNameCode;

    @Column(name = "is_underground", length = 1)
    private String isUnderground;

    @Column(name = "building_main_no")
    private Integer buildingMainNo;

    @Column(name = "building_sub_no")
    private Integer buildingSubNo;

    @Column(name = "update_reason_code")
    private String updateReasonCode;

}
