package com.bb.eodi.deal.presentation.adapter;

import com.bb.eodi.deal.application.input.FindRecommendedRegionInput;
import com.bb.eodi.deal.presentation.dto.request.RegionRecommendRequest;
import org.springframework.stereotype.Component;

/**
 * 지역 추천 API request parameter -> application input adapter
 */
@Component
public class FindRegionInputAdapter {
    public FindRecommendedRegionInput toInput(RegionRecommendRequest request) {
        return new FindRecommendedRegionInput(
                request.cash(),
                request.hasLoan(),
                request.monthlyPayment(),
                request.yearlyIncome(),
                request.housingTypes()
        );
    }
}
