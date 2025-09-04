package com.bb.eodi.domain.deal.type;

// 거래 방법
public enum TradeMethodType implements Type {
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
}
