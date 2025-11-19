package com.bb.eodi.batch.deal.type;

import com.bb.eodi.common.type.TypeCode;

// 거래 방법
public enum TradeMethodType implements TypeCode {
    // 직거래
    DIRECT("D", "직거래"),

    // 중개거래
    AGENCY("A", "중개거래"),

    // 기타
    OTHER("O", "기타");
    
    private final String code;
    private final String description;

    TradeMethodType(String code, String description) {
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

    /**
     * 원천 데이터로부터 type 가져오기
     * @param data
     * @return 원천데이터와 매핑되는 TypeCode enum
     */
    public static TradeMethodType fromData(String data) {
        for (TradeMethodType e : values()) {
            if (e.description.equals(data)) {
                return e;
            }
        }
        return OTHER;
    }
}
