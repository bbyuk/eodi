package com.bb.eodi.batch.domain.type.converter;

import com.bb.eodi.batch.domain.type.TradeMethodType;
import jakarta.persistence.Converter;

// 거래 방법 타입 컨버터
@Converter(autoApply = true)
public class TradeMethodTypeConverter extends AbstractTypeConverter<TradeMethodType> {
    public TradeMethodTypeConverter() {
        super(TradeMethodType.class);
    }
}
