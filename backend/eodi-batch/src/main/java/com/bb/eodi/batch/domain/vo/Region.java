package com.bb.eodi.batch.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * 행정 구역
 */
@Embeddable
public class Region {
    // 시/도
    @Column(name = "region_sido")
    private String sido;

    // 시/군/구
    @Column(name = "region_sigungu")
    private String sigungu;

    // 동
    @Column(name = "region_dong")
    private String dong;

    protected Region() {}

    public Region(String regionData) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.dong = dong;
    }

    public String getSido() {
        return sido;
    }

    public String getSigungu() {
        return sigungu;
    }

    public String getDong() {
        return dong;
    }
}
