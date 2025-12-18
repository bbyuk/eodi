package com.bb.eodi.deal.domain.entity;

import com.bb.eodi.deal.domain.type.HousingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

@DisplayName("small - 부동산 매매 실거래가 메소드 단위 테스트")
public class RealEstateSellTest {

    String naverBaseUrl = "https://m.land.naver.com/map";
    int naverMapLev = 15;

    @Test
    @DisplayName("정상 생성 케이스")
    void testGenerate() {
        // given

        String answer = "https://m.land.naver.com/map/37.539858:126.857215:15/APT/A1:B1";

        RealEstateSell realEstateSell = RealEstateSell.builder()
                .xPos(BigDecimal.valueOf(37.539858))
                .yPos(BigDecimal.valueOf(126.857215))
                .housingType(HousingType.APT)
                .build();

        // when
        String url = realEstateSell.createUrl(naverBaseUrl, naverMapLev);

        // then
        Assertions.assertThat(url).isEqualTo(answer);
    }

    @Test
    @DisplayName("미매핑 케이스")
    void testGenerateOnUnmapped() throws Exception {
        // given
        RealEstateSell realEstateSell = RealEstateSell.builder()
                .xPos(null)
                .yPos(null)
                .build();

        // when
        String url = realEstateSell.createUrl(naverBaseUrl, naverMapLev);

        // then
        Assertions.assertThat(url).isNull();
    }
}
