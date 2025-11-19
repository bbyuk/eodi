package com.bb.eodi.batch.address.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * 주소위치정보 JpaEntity
 */
@Getter
@Entity
@Table(name = "address_position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AddressPosition {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sigungu_code", length = 5)
    private String sigunguCode;

    @Column(name = "entrance_seq", length = 10)
    private String entranceSeq;

    @Column(name = "legal_dong_code", length = 10)
    private String legalDongCode;

    @Column(name = "sido_name", length = 40)
    private String sidoName;

    @Column(name = "sigungu_name", length = 40)
    private String sigunguName;

    @Column(name = "umd_name", length = 40)
    private String umdName;

    @Column(name = "road_name_code", length = 12)
    private String roadNameCode;

    @Column(name = "road_name", length = 80)
    private String roadName;

    @Column(name = "is_underground", length = 1)
    private String isUnderground;

    @Column(name = "building_main_no")
    private Integer buildingMainNo;

    @Column(name = "building_sub_no")
    private Integer buildingSubNo;

    @Column(name = "building_name", length = 40)
    private String buildingName;

    @Column(name = "zip_no", length = 5)
    private String zipNo;

    @Column(name = "building_type", length = 100)
    private String buildingType;

    @Column(name = "is_building_group", length = 1)
    private String isBuildingGroup;

    @Column(name = "x_pos", precision = 15, scale = 6)
    private BigDecimal xPos;

    @Column(name = "y_pos", precision = 15, scale = 6)
    private BigDecimal yPos;
}
