package com.bb.eodi.address.domain.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("small - 주소 연계 기간 테스트")
class AddressLinkagePeriodTest {

    @Test
    @DisplayName("오전 8시 이전인 경우 to date는 어제 날짜")
    void testBeforeEightOClock() {
        // given
        AddressLinkagePeriod period = new AddressLinkagePeriod(LocalDate.of(2025, 12, 2));

        // when
        System.out.println("period.to() = " + period.to());

        Assertions.assertThat(period.to()).isEqualTo(LocalDate.now().minusDays(1));

        // then
    }

}