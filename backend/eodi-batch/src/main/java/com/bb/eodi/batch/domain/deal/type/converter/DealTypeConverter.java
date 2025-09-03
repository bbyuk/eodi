package com.bb.eodi.batch.domain.deal.type.converter;

import com.bb.eodi.batch.domain.deal.type.DealType;
import jakarta.persistence.Converter;

// 거래 유형 타입 컨버터
@Converter(autoApply = true)
public class DealTypeConverter extends AbstractTypeConverter<DealType> {
    public DealTypeConverter() {
        super(DealType.class);
    }
}
