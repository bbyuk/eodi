package com.bb.eodi.address.domain.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 주소 연계 기간
 */
public record AddressLinkagePeriod(
        LocalDate from,
        LocalDate to
) {
    public long duration() {
        return ChronoUnit.DAYS.between(from, to);
    }
}
