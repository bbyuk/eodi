package com.bb.eodi.batch.deal.type.converter;

import com.bb.eodi.common.type.AbstractTypeCodeEnumConverter;
import com.bb.eodi.batch.deal.type.HousingType;
import jakarta.persistence.Converter;

/**
 * 주택 타입 코드 enum 컨버터
 */
@Converter(autoApply = true)
public class HousingTypeConverter extends AbstractTypeCodeEnumConverter<HousingType> {
    public HousingTypeConverter() {
        super(HousingType.class);
    }
}
