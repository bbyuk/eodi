package com.bb.eodi.deal.application.util;

import com.bb.eodi.deal.domain.type.HousingType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 네이버 부동산 Url Generator
 */
@Slf4j
@Component
public class NaverUrlGenerator {

    private String baseUrl = "https://m.land.naver.com/map";
    private int lev = 15;

    public String generate(
            BigDecimal latitude,
            BigDecimal longitude,
            List<HousingType> housingTypes) {
        StringBuilder sb = new StringBuilder();

        // TODO housingType별 코드 분기처리 추가
        log.debug("housingType = {}", housingTypes);
        String naverHousingType = "APT:ABYG:JGC";

        sb.append(baseUrl).append("/").append(latitude.toString()).append(":").append(longitude.toString()).append(":").append(lev).append("/").append(naverHousingType).append("/").append("A1").append(":").append("B1");
        return sb.toString();
    }
}
