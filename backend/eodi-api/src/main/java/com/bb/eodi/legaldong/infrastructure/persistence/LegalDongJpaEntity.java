package com.bb.eodi.legaldong.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 법정동코드
 */
@Getter
@Entity
@Table(name = "legal_dong")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegalDongJpaEntity {

    // 법정동 ID
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    // 법정동 코드
    @Column(name = "code")
    private String code;

    // 시/도 코드
    @Column(name = "sido_code")
    private String sidoCode;

    // 시/군/구 코드
    @Column(name = "sigungu_code")
    private String sigunguCode;

    // 동 코드
    @Column(name = "dong_code")
    private String dongCode;

    // 법정동 명
    @Column(name = "name")
    private String name;

    // 법정동 서열
    @Column(name = "legalDongOrder")
    private int legalDongOrder;

    // 상위 법정동 ID
    @JoinColumn(name = "parent_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private LegalDongJpaEntity parent;

    // 활성여부
    @Column(name = "is_active")
    private boolean active;

    // 생성일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 변경 일시
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
