package com.bb.eodi.address.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

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

    @Column(name = "manage_no", length = 26, nullable = false)
    private String manageNo;

    @Column(name = "legal_dong_code", length = 10)
    private String legalDongCode;

    @Column(name = "sido_name", length = 40)
    private String sidoName;

    @Column(name = "sigungu_name", length = 40)
    private String sigunguName;

    @Column(name = "umd_name", length = 40)
    private String umdName;

    @Column(name = "ri_name", length = 40)
    private String riName;

    @Column(name = "is_mountain", length = 1)
    private String isMountain;

    @Column(name = "land_lot_main_no")
    private Integer landLotMainNo;

    @Column(name = "land_lot_sub_no")
    private Integer landLotSubNo;

    @Column(name = "road_name_code", length = 12, nullable = false)
    private String roadNameCode;

    @Column(name = "road_name", length = 80)
    private String roadName;

    @Column(name = "is_underground", length = 1, nullable = false)
    private String isUnderground;

    @Column(name = "building_main_no", nullable = false)
    private Integer buildingMainNo;

    @Column(name = "building_sub_no", nullable = false)
    private Integer buildingSubNo;

    @Column(name = "adm_dong_code", length = 60)
    private String admDongCode;

    @Column(name = "adm_dong_name", length = 60)
    private String admDongName;

    @Column(name = "basic_district_no", length = 5)
    private String basicDistrictNo;

    @Column(name = "before_road_name_address", length = 400)
    private String beforeRoadNameAddress;

    @Column(name = "effect_start_date", length = 8)
    private String effectStartDate;

    @Column(name = "is_multi", length = 1)
    private String isMulti;

    @Column(name = "update_reason_code", length = 2)
    private String updateReasonCode;

    @Column(name = "building_name", length = 400)
    private String buildingName;

    @Column(name = "sigungu_building_name", length = 400)
    private String sigunguBuildingName;

    @Column(name = "remark", length = 200)
    private String remark;

    // ========= 주소 위치정보 소스
    @Column(name = "x_pos", precision = 15, scale = 6)
    private BigDecimal xPos;

    @Column(name = "y_pos", precision = 15, scale = 6)
    private BigDecimal yPos;

    /**
     * 건물명을 update한다.
     * @param newBuildingName 새로운 건물명
     */
    public void updateBuildingName(String newBuildingName) {
        this.buildingName = StringUtils.hasText(newBuildingName) ? newBuildingName : null;
    }

    /**
     * 도로명주소의 X좌표와 Y좌표를 변경한다.
     * @param xPos X좌표
     * @param yPos Y좌표
     */
    public void updatePosition(BigDecimal xPos, BigDecimal yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
