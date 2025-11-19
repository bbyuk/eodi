package com.bb.eodi.domain.deal.type.converter;

import com.bb.eodi.common.type.AbstractTypeCodeEnumConverter;
import com.bb.eodi.domain.deal.type.DealType;
import jakarta.persistence.Converter;

/**
 * DealType 코드 enum 컨버터
 */
@Converter(autoApply = true)
public class DealTypeConverter extends AbstractTypeCodeEnumConverter<DealType> {
    public DealTypeConverter() {
        super(DealType.class);
    }
}
