package com.bb.eodi.batch.domain.type;

/**
 * 건물용도
 */
public enum HousingType implements Type {
    // 아파트
    APT("A", "아파트"),

    // 연립/다세대
    MULTI_HOUSEHOLD_HOUSE("MH", "연립/다세대 주택"),

    // 단독
    DETACHED_HOUSE("D", "단독 주택"),

    // 다가구주택
    MULTI_UNIT_HOUSE("MU", "다가구 주택"),

    // 오피스텔
    OFFICETEL("O", "오피스텔"),

    // 분양권
    PRE_SALE_RIGHT("PR", "분양권"),
    
    // 입주권
    OCCUPY_RIGHT("OR", "입주권");

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
