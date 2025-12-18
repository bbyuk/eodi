package com.bb.eodi.deal.presentation.adapter;

import com.bb.eodi.deal.application.input.FindRealEstateSellInput;
import com.bb.eodi.deal.application.input.FindRecommendedSellInput;
import com.bb.eodi.deal.presentation.dto.request.RealEstateSellRecommendRequestParameter;
import com.bb.eodi.deal.presentation.dto.request.RealEstateSellRequestParameter;
import org.springframework.stereotype.Component;

/**
 * 부동산 매매 실거래가 조회 API request parameter -> application input adapter
 */
@Component
public class FindRealEstateSellInputAdapter {

    /**
     * 부동산 매매 실거래가 추천 데이터 요청 -> 추천 매매 데이터 조회 application input
     * @param requestParameter
     * @return
     */
    public FindRecommendedSellInput toInput(RealEstateSellRecommendRequestParameter requestParameter) {
        return new FindRecommendedSellInput(
                requestParameter.cash(),
                requestParameter.targetRegionIds(),
                requestParameter.targetHousingTypes(),
                requestParameter.maxNetLeasableArea(),
                requestParameter.minNetLeasableArea()
        );
    }

    /**
     * 부동산 매매 실거래가 조회 Open API 요청 -> 부동산 매매 실거래가 데이터 조회 application input
     * @param requestParameter
     * @return
     */
    public FindRealEstateSellInput toInput(RealEstateSellRequestParameter requestParameter) {
        return new FindRealEstateSellInput(
                requestParameter.maxPrice(),
                requestParameter.minPrice(),
                requestParameter.maxNetLeasableArea(),
                requestParameter.minNetLeasableArea(),
                requestParameter.startYearMonth(),
                requestParameter.endYearMonth(),
                requestParameter.targetRegionIds(),
                requestParameter.targetHousingTypes()
        );
    }
}
