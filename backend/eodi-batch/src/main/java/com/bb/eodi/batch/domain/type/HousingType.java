package com.bb.eodi.batch.domain.type;

/**
 * 건물용도
 */
public enum HousingType implements Type {
    // 아파트
    APT("A", "아파트"),

    // 연립/다세대
    MULTIPLEX_HOUSE("M", "연립/다세대 주택"),

    // 단독/다가구
    DETACHED_HOUSE("D", "단독/다가구 주택"),

    // 오피스텔
    OFFICETEL("O", "오피스텔"),

    // 분양/입주권
    PRESALE_RIGHT("P", "분양/입주권");

    private final String code;
    private final String description;

    HousingType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }
}
