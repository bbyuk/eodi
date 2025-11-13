package com.bb.eodi.batch.job.address.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "land_lot_address")
public class LandLotAddress {

    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "legal_dong_code", length = 10, nullable = false)
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

    @Column(name = "land_lot_sub_no")
    private Integer landLotSubNo;

    @Column(name = "land_lot_seq")
    private Long landLotSeq;

    @Column(name = "road_name_code", length = 12)
    private String roadNameCode;

    @Column(name = "is_underground", length = 1)
    private String isUnderground;

    @Column(name = "building_main_no")
    private Integer buildingMainNo;

    @Column(name = "building_sub_no")
    private Integer buildingSubNo;

    @Column(name = "change_reason_code", length = 2)
    private String changeReasonCode;
}
