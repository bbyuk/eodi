package com.bb.eodi.deal.application.util;

import com.bb.eodi.deal.domain.type.HousingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("small - 네이버부동산 연결 URL 생성 유틸 small test")
class NaverUrlGeneratorTest {

    NaverUrlGenerator naverUrlGenerator = new NaverUrlGenerator();

    @Test
    @DisplayName("정상 생성 케이스")
    void testGenerate() {
        // given
        BigDecimal latitude = BigDecimal.valueOf(37.539858);
        BigDecimal longitude = BigDecimal.valueOf(126.857215);
        List<HousingType> housingTypes = List.of(HousingType.APT, HousingType.OFFICETEL);

        String answer = "https://m.land.naver.com/map/37.539858:126.857215:15/APT:ABYG:JGC/A1:B1";

        // when
        String url = naverUrlGenerator.generate(latitude, longitude, housingTypes);

        // then
        Assertions.assertThat(url).isEqualTo(answer);
    }

    @Test
    @DisplayName("미매핑 케이스")
    void testGenerateOnUnmapped() throws Exception {
        // given
        List<HousingType> housingTypes = List.of(HousingType.OFFICETEL);

        // when
        String answer = naverUrlGenerator.generate(null, null, housingTypes);

        // then
        Assertions.assertThat(answer).isNull();
    }

}