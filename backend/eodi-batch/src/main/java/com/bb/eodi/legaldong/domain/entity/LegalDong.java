package com.bb.eodi.legaldong.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 법정동코드
 */
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegalDong {

    // 법정동 ID
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "parent_id")
    private Long parentId;

    // 상위 법정동 코드
    @Setter
    @Transient
    private String parentCode;

    // 활성여부
    @Column(name = "is_active")
    private boolean isActive;

    // 생성일시
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 변경 일시
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public LegalDong(String code, String sidoCode, String sigunguCode, String dongCode, String name, String parentCode, int legalDongOrder, Long parentId, boolean isActive) {
        this.code = code;
        this.sidoCode = sidoCode;
        this.sigunguCode = sigunguCode;
        this.dongCode = dongCode;
        this.name = name;
        this.parentCode = parentCode;
        this.legalDongOrder = legalDongOrder;
        this.parentId = parentId;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
