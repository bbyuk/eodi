package com.bb.eodi.address.domain.vo;

import java.time.LocalDate;

/**
 * 주소 연계 기간
 */
public record AddressLinkagePeriod(
        LocalDate from,
        LocalDate to
) {}
