package com.bb.eodi.common.type;

import jakarta.persistence.AttributeConverter;

/**
 * JPA Enum type code 매핑 공통 컨버터
 */
public class AbstractTypeCodeEnumConverter<E extends Enum<E> & TypeCode> implements AttributeConverter<E, String> {

    private final Class<E> enumClass;

    protected AbstractTypeCodeEnumConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(E attribute) {
        return attribute == null ? null : attribute.code();
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TypeCode.from(enumClass, dbData);
    }
}
