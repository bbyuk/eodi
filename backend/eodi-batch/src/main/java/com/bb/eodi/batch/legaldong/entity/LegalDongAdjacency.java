package com.bb.eodi.batch.legaldong.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 법정동코드 인접 정보
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegalDongAdjacency {

    // 법정동 인접 ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 법정동 인접 시작 ID
    @Column(name = "source_id")
    private Long sourceId;

    // 법정동 인접 종료 ID
    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LegalDongAdjacency(Long sourceId, Long targetId) {
        this.sourceId = sourceId;
        this.targetId = targetId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
