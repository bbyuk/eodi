package com.bb.eodi.deal.job.service;

import com.bb.eodi.deal.domain.type.DealType;
import com.bb.eodi.deal.domain.type.HousingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
@DisplayName("medium - 부동산 실거래가 데이터 API 연동 서비스 테스트")
class DealDataApiIntegrationServiceTest {

    @Autowired
    DealDataApiIntegrationService service;

    @Test
    @DisplayName("medium - 부동산 실거래가 데이터 API 연동 서비스 요청")
    void testApiIntegration() throws Exception {
        // given
        DealType dealType = DealType.SELL;
        HousingType housingType = HousingType.APT;
        String yearMonth = "202601";

        //when
        Path tempFilePath = service.createDealDataTempFileWithApiFetching(yearMonth, dealType, housingType);

        // then
        Assertions.assertThat(tempFilePath).isNotNull();

        Files.delete(tempFilePath);

    }

}