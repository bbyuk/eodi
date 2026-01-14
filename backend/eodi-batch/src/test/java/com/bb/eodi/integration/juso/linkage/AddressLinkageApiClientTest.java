package com.bb.eodi.integration.juso.linkage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class AddressLinkageApiClientTest {

    @Autowired
    private AddressLinkageApiClient addressLinkageApiClient;

    @Test
    @DisplayName("도로명주소 연계 API 호출 테스트")
    void testRoadNameAddressLinkageApiRequest() throws Exception {
        // given
        LocalDate yesterday = LocalDate.of(2026, 1, 13);
        LocalDate today = LocalDate.of(2026, 1, 14);

        addressLinkageApiClient.downloadUpdatedAddress(yesterday, today);
        // when

        // then
    }
}