package com.bb.eodi.deal.domain.type;

import com.bb.eodi.common.type.TypeCode;

import java.util.Arrays;

/**
 * 건물용도
 */
public enum HousingType implements TypeCode {
    // 아파트
    APT("AP", "아파트", true),

    // 연립/다세대
    MULTI_HOUSEHOLD_HOUSE("MH", "연립/다세대 주택", false),

    // 단독
    DETACHED_HOUSE("DT", "단독 주택", false),

    // 다가구주택
    MULTI_UNIT_HOUSE("MU", "다가구 주택", false),

    // 오피스텔
    OFFICETEL("OF", "오피스텔", true),

    // 분양권
    PRESALE_RIGHT("PR", "분양권", false),
    
    // 입주권
    OCCUPY_RIGHT("OR", "입주권", false),
    OTHER("O", "기타", false);

    private final String code;
    private final String description;
    private final boolean enabled;

    HousingType(String code, String description, boolean enabled) {
        this.code = code;
        this.description = description;
        this.enabled = enabled;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String description() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 코드 입력으로 HousingType 리턴
     * @param code
     * @return
     */
    public static HousingType fromCode(String code) {
        return Arrays.stream(values())
                .filter(type -> type.code().equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("잘못된 코드 입력입니다."));
    }
}
