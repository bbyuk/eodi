package com.bb.eodi.batch.job.address.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "building_address")
public class BuildingAddress {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "legal_dong_code", length = 10)
    private String legalDongCode;

    @Column(name = "sido_name", length = 40)
    private String sidoName;

    @Column(name = "sigungu_name", length = 40)
    private String sigunguName;

    @Column(name = "legal_umd_name", length = 40)
    private String legalUmdName;

    @Column(name = "legal_ri_name", length = 40)
    private String legalRiName;

    @Column(name = "is_mountain", length = 1)
    private String isMountain;

    @Column(name = "land_lot_main_no")
    private Integer landLotMainNo;

    @Column(name = "lang_lot_sub_no")
    private Integer landLotSubNo;

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

    @Column(name = "building_name_detail", length = 100)
    private String buildingNameDetail;

    @Column(name = "building_manage_no", length = 25)
    private String buildingManageNo;

    @Column(name = "umd_seq", length = 2)
    private String umdSeq;

    @Column(name = "adm_dong_code", length = 10)
    private String admDongCode;

    @Column(name = "adm_dong_name", length = 20)
    private String admDongName;

    @Column(name = "zip_no", length = 5)
    private String zipNo;

    @Column(name = "zip_no_seq", length = 3)
    private String zipNoSeq;

    @Column(name = "change_reason_code", length = 2)
    private String changeReasonCode;

    @Column(name = "announcement_date", length = 8)
    private String announcementDate;

    @Column(name = "sigungu_building_name", length = 40)
    private String sigunguBuildingName;

    @Column(name = "is_complex", length = 1)
    private String isComplex;

    @Column(name = "basic_district_no", length = 5)
    private String basicDistrictNo;

    @Column(name = "has_detail_address", length = 1)
    private String hasDetailAddress;

    @Column(name = "remark_1", length = 15)
    private String remark1;

    @Column(name = "remark_2", length = 15)
    private String remark2;
}
