package com.bb.eodi.legaldong.domain.entity;

import java.time.LocalDateTime;

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

    // 상위 법정동
    private LegalDong parent;

    // 활성여부
    private boolean active;

    // 생성일시
    private LocalDateTime createdAt;

    // 변경 일시
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getSidoCode() {
        return sidoCode;
    }

    public String getSigunguCode() {
        return sigunguCode;
    }

    public String getDongCode() {
        return dongCode;
    }

    public String getName() {
        return name;
    }

    public int getLegalDongOrder() {
        return legalDongOrder;
    }

    public LegalDong getParent() {
        return parent;
    }

    public boolean isActive() {
        return active;
    }

    public LegalDong(Long id, String code, String sidoCode, String sigunguCode, String dongCode, String name, int legalDongOrder, LegalDong parent, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.code = code;
        this.sidoCode = sidoCode;
        this.sigunguCode = sigunguCode;
        this.dongCode = dongCode;
        this.name = name;
        this.legalDongOrder = legalDongOrder;
        this.parent = parent;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
