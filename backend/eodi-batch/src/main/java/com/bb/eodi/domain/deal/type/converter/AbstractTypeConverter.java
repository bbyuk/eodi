package com.bb.eodi.domain.deal.type.converter;

import com.bb.eodi.domain.deal.type.Type;
import jakarta.persistence.AttributeConverter;

/**
 * 공통 타입 컨버터 추상 클래스
 * @param <E>
 */
public abstract class AbstractTypeConverter<E extends Enum<E> & Type>
        implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    protected AbstractTypeConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.code();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return dbData == null ? null : Type.fromCode(enumClass, dbData);
    }
}
