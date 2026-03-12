package com.bb.eodi.ops.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 기준정보버전 도메인 Entity
 */
public class ReferenceVersion {

    /**
     * 기준정보버전 ID
     */
    private Long id;

    /**
     * 기준대상명
     */
    private String targetName;

    /**
     * 기준정보 반영일자
     */
    private LocalDate effectiveDate;

    /**
     * 수정일시
     */
    private LocalDateTime updatedAt;

    /**
     * 수정자
     */
    private String updatedBy;

    public Long getId() {
        return id;
    }

    public String getTargetName() {
        return targetName;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }
}
