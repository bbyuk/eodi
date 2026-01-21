package com.bb.eodi.address.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 주소 연계 기간
 */
public class AddressLinkagePeriod {

    private final LocalDate from;
    private final LocalDate to;

    public AddressLinkagePeriod(LocalDate from) {
        this.from = from;
        this.to = LocalDateTime.now().getHour() < 8 ? LocalDate.now().minusDays(1) : LocalDate.now();
    }

    public LocalDate from() {
        return this.from;
    }

    public LocalDate to() {
        return this.to;
    }

    public long duration() {
        return ChronoUnit.DAYS.between(from, to);
    }
}
