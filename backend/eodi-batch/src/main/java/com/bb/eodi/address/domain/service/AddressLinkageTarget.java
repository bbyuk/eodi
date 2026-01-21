package com.bb.eodi.address.domain.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 주소 연계 대상
 */
@Getter
@RequiredArgsConstructor
public enum AddressLinkageTarget {
    ROAD_NAME_ADDRESS_KOR("address", "도로명주소 한글"),
    ADDRESS_ENTRANCE("address_entrance", "주소 출입구 정보");

    private final String referenceVersionName;
    private final String description;
}
