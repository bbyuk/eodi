package com.bb.eodi.finance.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 규제지역 entity
 */
@Getter
@Entity
@Table(name = "regulating_area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class RegulatingAreaJpaEntity {

    // 규제지역 id
    @Column(name = "id")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 규제지역 법정동 ID
    @Column(name = "legal_dong_id")
    private Long legalDongId;

    // 적용시작일자
    @Column(name = "effective_start_date")
    private LocalDate effectiveStartDate;

    // 적용종료일자
    @Column(name = "effective_end_date")
    private LocalDate effectiveEndDate;

    // 생성일시
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    // 수정일시
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void audit() {
        this.updatedAt = LocalDateTime.now();
    }
}
