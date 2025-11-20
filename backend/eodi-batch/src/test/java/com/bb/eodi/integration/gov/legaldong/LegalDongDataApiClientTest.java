package com.bb.eodi.integration.gov.legaldong;

import com.bb.eodi.integration.gov.legaldong.dto.LegalDongApiResponseRow;
import com.bb.eodi.legaldong.domain.port.LegalDongDataPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("medium - 법정동 API 리팩토링 테스트")
class LegalDongDataApiClientTest {

    @Autowired
    LegalDongDataPort legalDongDataPort;


    @Test
    @DisplayName("medium - 법정동 API를 요청해 서울특별시의 법정동목록 1 페이지를 조회한다.")
    void testSeoulLegalDongCode() throws Exception {
        // given
        String targetRegion = "서울특별시";
        int pageNum = 1;

        // when
        List<LegalDongApiResponseRow> result = legalDongDataPort.findByRegion(targetRegion, pageNum);

        // then
        Assertions.assertThat(result).isNotNull();

    }

}