package com.bb.eodi.domain.deal.type.converter;

import com.bb.eodi.common.type.AbstractTypeCodeEnumConverter;
import com.bb.eodi.domain.deal.type.TradeMethodType;
import jakarta.persistence.Converter;

/**
 * 거래 유형 코드 enum 컨버터
 */
@Converter(autoApply = true)
public class TradeMethodTypeConverter extends AbstractTypeCodeEnumConverter<TradeMethodType> {

    public TradeMethodTypeConverter() {
        super(TradeMethodType.class);
    }
}
