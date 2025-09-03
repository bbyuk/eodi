package com.bb.eodi.batch.domain.deal.type;

/**
 * 거래 종류
 */
public enum DealType implements Type {
    SALE("S", "매매"),   // 매매
    LEASE("L", "전/월세");   // 전, 월세

    private final String code;
    private final String description;

    DealType(String code, String description) {
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
