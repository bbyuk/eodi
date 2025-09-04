package com.bb.eodi.domain.deal.type.converter;

import com.bb.eodi.domain.deal.type.HousingType;
import jakarta.persistence.Converter;

// 건물용도 타입 컨버터
@Converter(autoApply = true)
public class HousingTypeConverter extends AbstractTypeConverter<HousingType> {
    public HousingTypeConverter() {
        super(HousingType.class);
    }
}
