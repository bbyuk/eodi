package com.bb.eodi.domain.legaldong.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 법정동코드
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LegalDong {

    // 법정동 ID
    private Long id;

    // 법정동 코드
    private String code;

    // 시/도 코드
    private String sidoCode;

    // 시/군/구 코드
    private String sigunguCode;

    // 동 코드
    private String dongCode;

    // 법정동 명
    private String name;

    // 법정동 서열
    private int legalDongOrder;

    // 상위 법정동 ID
    private Long parentId;

    // 활성여부
    private boolean isActive;

    // 생성일시
    private LocalDateTime createdAt;

    // 변경 일시
    private LocalDateTime updatedAt;

    @Builder
    public LegalDong(String code, String sidoCode, String sigunguCode, String dongCode, String name, int legalDongOrder, Long parentId, boolean isActive) {
        this.code = code;
        this.sidoCode = sidoCode;
        this.sigunguCode = sigunguCode;
        this.dongCode = dongCode;
        this.name = name;
        this.legalDongOrder = legalDongOrder;
        this.parentId = parentId;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
