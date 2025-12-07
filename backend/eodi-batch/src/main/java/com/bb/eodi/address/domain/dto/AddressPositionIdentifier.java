package com.bb.eodi.address.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * 주소 위치 테이블 Identifier 도메인 PK dto
 */
@Data
@Builder
public class AddressPositionIdentifier {
    private String legalDongCode;
    private String roadNameCode;
    private String isUnderground;
    private Integer buildingMainNo;
    private Integer buildingSubNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddressPositionIdentifier other)) return false;

        return Objects.equals(this.roadNameCode, other.getRoadNameCode())
                && Objects.equals(this.legalDongCode, other.getLegalDongCode())
                && Objects.equals(this.isUnderground, other.getIsUnderground())
                && Objects.equals(this.buildingMainNo, other.getBuildingMainNo())
                && Objects.equals(this.buildingSubNo, other.getBuildingSubNo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(roadNameCode, legalDongCode, isUnderground, buildingMainNo, buildingSubNo);
    }
}
