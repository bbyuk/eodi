package com.bb.eodi.deal.application.port;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.type.HousingType;
import com.bb.eodi.deal.infrastructure.external.naver.NaverRealEstateUrlGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("small - 부동산 플랫폼 URL 생성 port 슬라이스 테스트")
class RealEstatePlatformUrlGeneratePortTest {


    @Test
    @DisplayName("네이버 부동산 매매 URL 생성 슬라이스 테스트")
    void testGenerateNaverSellUrl() throws Exception {
        // given
        String answer = "https://m.land.naver.com/map/37.539858:126.857215:15/APT/A1";

        String baseUrl = "https://m.land.naver.com/map";
        int defaultMapSize = 15;

        RealEstatePlatformUrlGeneratePort port = new NaverRealEstateUrlGenerator(baseUrl, defaultMapSize);

        RealEstateSell realEstateSell = RealEstateSell.builder()
                .xPos(BigDecimal.valueOf(37.539858))
                .yPos(BigDecimal.valueOf(126.857215))
                .housingType(HousingType.APT)
                .build();

        // when
        String url = port.generate(realEstateSell);

        // then
        Assertions.assertThat(url).isEqualTo(answer);
    }

    @Test
    @DisplayName("네이버 부동산 매매 URL 생성 경로 미매핑 데이터 슬라이스 테스트")
    void testGenerateNaverSellUrlWithoutPositionMapping() throws Exception {
        // given
        String baseUrl = "https://m.land.naver.com/map";
        int defaultMapSize = 15;

        RealEstatePlatformUrlGeneratePort port = new NaverRealEstateUrlGenerator(baseUrl, defaultMapSize);

        RealEstateSell realEstateSell = RealEstateSell.builder()
                .xPos(null)
                .yPos(null)
                .build();

        // when
        String url = port.generate(realEstateSell);

        // then
        Assertions.assertThat(url).isNull();
    }

    @Test
    @DisplayName("네이버 부동산 임대차 URL 생성 슬라이스 테스트")
    void testGenerateNaverLeaseUrl() throws Exception {
        // given
        String answer = "https://m.land.naver.com/map/37.539858:126.857215:15/APT/B1:B2";
        String baseUrl = "https://m.land.naver.com/map";
        int defaultMapSize = 15;

        RealEstatePlatformUrlGeneratePort port = new NaverRealEstateUrlGenerator(baseUrl, defaultMapSize);

        RealEstateLease realEstateLease = RealEstateLease.builder()
                .xPos(BigDecimal.valueOf(37.539858))
                .yPos(BigDecimal.valueOf(126.857215))
                .housingType(HousingType.APT)
                .build();

        // when
        String url = port.generate(realEstateLease);

        // then
        Assertions.assertThat(url).isEqualTo(answer);
    }
}