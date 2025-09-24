package com.bb.eodi.common.type;

import java.util.Arrays;

/**
 * type 코드 인터페이스
 */
public interface TypeCode {
    String code();
    String description();

    static <E extends Enum<E> & TypeCode> E from(Class<E> enumClass, String codeData) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> e.code().equals(codeData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown code [" + codeData + "] for " + enumClass.getSimpleName()
                ));
    }
}
