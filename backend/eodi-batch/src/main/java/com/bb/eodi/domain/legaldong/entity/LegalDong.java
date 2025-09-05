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

    // 법정동 명
    private String name;

    // 상위 법정동 ID
    private Long parentId;

    // 활성여부
    private boolean isActive;

    // 생성일시
    private LocalDateTime createdAt;

    // 변경 일시
    private LocalDateTime updatedAt;

    @Builder
    public LegalDong(String code, String name, Long parentId, boolean isActive) {
        this.code = code;
        this.name = name;
        this.parentId = parentId;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
