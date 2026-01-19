package com.bb.eodi.ops.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 기준정보 대상
 */
@Getter
@RequiredArgsConstructor
public enum ReferenceTarget {

    ADDRESS("address", "도로명주소 버전"),
    ADDRESS_ENTRANCE("address_entrance", "도로명주소 출입구정보 (좌표) 버전");

    // 대상이름
    private final String value;
    // 설명
    private final String description;
}
