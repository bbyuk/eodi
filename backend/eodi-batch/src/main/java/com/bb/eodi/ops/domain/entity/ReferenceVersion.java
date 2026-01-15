package com.bb.eodi.ops.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 기준정보버전 JPA Entity
 */
@Getter
@Entity
@Table(name = "reference_version")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReferenceVersion {

    /**
     * 기준정보버전 ID
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 기준대상명
     */
    @Column(name = "target_name", length = 50, nullable = false, unique = true)
    private String targetName;

    /**
     * 기준정보 반영일자
     */
    @Column(name = "effective_date", nullable = false)
    private LocalDate effectiveDate;

    /**
     * 수정일시
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * 수정자
     */
    @Column(name = "updated_by", length = 50)
    private String updatedBy;
}
