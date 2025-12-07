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

    @Column(name = "manage_no", length = 25, nullable = false)
    private String manageNo;

    @Column(name = "seq", nullable = false)
    private Integer seq;

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

    @Column(name = "is_representative", length = 1)
    private String isRepresentative;

    // ========= 부가정보 소스
    @Column(name = "building_name")
    private String buildingName;

    /**
     * 건물명을 update한다.
     * @param newBuildingName 새로운 건물명
     */
    public void updateBuildingName(String newBuildingName) {
        this.buildingName = newBuildingName;
    }
}
