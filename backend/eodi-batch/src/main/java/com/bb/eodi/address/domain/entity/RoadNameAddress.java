package com.bb.eodi.address.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

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

    @Column(name = "manage_no", length = 25, nullable = false)
    private String manageNo;

    @Column(name = "road_name_code", length = 12, nullable = false)
    private String roadNameCode;

    @Column(name = "umd_seq", length = 2, nullable = false)
    private String umdSeq;

    @Column(name = "is_underground", length = 1)
    private String isUnderground;

    @Column(name = "building_main_no")
    private Integer buildingMainNo;

    @Column(name = "building_sub_no")
    private Integer buildingSubNo;

    @Column(name = "basic_district_no", length = 5)
    private String basicDistrictNo;

    @Column(name = "has_detail_address", length = 1)
    private String hasDetailAddress;

    // ========= 부가정보 소스
    @Column(name = "building_name")
    private String buildingName;

    /**
     * 건물명을 update한다.
     * @param newBuildingName 새로운 건물명
     */
    public void updateBuildingName(String newBuildingName) {
        this.buildingName = StringUtils.hasText(newBuildingName) ? newBuildingName : null;
    }
}
