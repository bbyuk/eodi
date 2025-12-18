package com.bb.eodi.deal.infrastructure.external.naver;

import com.bb.eodi.deal.application.port.RealEstatePlatformUrlGeneratePort;
import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.entity.RealEstateSell;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 네이버 부동산 URL 생성
 */
@Component
public class NaverRealEstateUrlGenerator implements RealEstatePlatformUrlGeneratePort {

    private final String baseUrl;

    private final int defaultMapSize;

    public NaverRealEstateUrlGenerator(
            @Value("${external.naver.base-url}") String baseUrl,
            @Value("${external.naver.default-map-size}") int defaultMapSize) {

        this.baseUrl = baseUrl;
        this.defaultMapSize = defaultMapSize;
    }

    /**
     * 부동산 매매 데이터 URL 생성
     * A1 -> 매매
     *
     * @param realEstateSell 부동산 매매 실거래가 도메인 entity
     * @return 부동산 매매데이터 URL
     */
    @Override
    public String generate(RealEstateSell realEstateSell) {
        if (realEstateSell.getXPos() == null || realEstateSell.getYPos() == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String targetHousingType = "";
        String tradeType = "A1";

        switch(realEstateSell.getHousingType()) {
            case APT -> targetHousingType = "APT";
            case OFFICETEL -> targetHousingType = "OPST";
            default -> targetHousingType = "APT";
        }

        sb.append(baseUrl)
                .append("/").append(realEstateSell.getXPos()).append(":").append(realEstateSell.getYPos()).append(":").append(defaultMapSize)
                .append("/").append(targetHousingType).append("/").append(tradeType);
        return sb.toString();
    }

    /**
     * 부동산 임대차 데이터 URL 생성
     * @param realEstateLease 부동산 임대차 실거래가 도메인 entity
     * @return 부동산 임대차데이터 URL
     */
    @Override
    public String generate(RealEstateLease realEstateLease) {
        if (realEstateLease.getXPos() == null || realEstateLease.getYPos() == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        String targetHousingType = "";
        String tradeType = "B1:B2";

        switch(realEstateLease.getHousingType()) {
            case APT -> targetHousingType = "APT";
            case OFFICETEL -> targetHousingType = "OPST";
            default -> targetHousingType = "APT";
        }

        sb.append(baseUrl)
                .append("/").append(realEstateLease.getXPos()).append(":").append(realEstateLease.getYPos()).append(":").append(defaultMapSize)
                .append("/").append(targetHousingType).append("/").append(tradeType);
        return sb.toString();
    }
}
