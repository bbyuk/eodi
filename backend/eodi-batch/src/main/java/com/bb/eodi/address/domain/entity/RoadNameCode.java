package com.bb.eodi.address.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "road_name_code")
public class RoadNameCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "road_name_code", length = 12, nullable = false)
    private String roadNameCode;

    @Column(name = "road_name", length = 80)
    private String roadName;

    @Column(name = "eng_road_name", length = 80)
    private String engRoadName;

    @Column(name = "umd_seq", length = 2, nullable = false)
    private String umdSeq;

    @Column(name = "sido_name", length = 20)
    private String sidoName;

    @Column(name = "eng_sido_name", length = 40)
    private String engSidoName;

    @Column(name = "umd_name", length = 20)
    private String umdName;

    @Column(name = "eng_umd_name", length = 40)
    private String engUmdName;

    @Column(name = "umd_type", length = 1)
    private String umdType;

    @Column(name = "umd_code", length = 3)
    private String umdCode;

    @Column(name = "enabled", length = 1)
    private String enabled;

    @Column(name = "change_code", length = 1)
    private String changeCode;

    @Column(name = "entrance_date", length = 8)
    private String entranceDate;

    @Column(name = "revocation_date", length = 8)
    private String revocationDate;
}
