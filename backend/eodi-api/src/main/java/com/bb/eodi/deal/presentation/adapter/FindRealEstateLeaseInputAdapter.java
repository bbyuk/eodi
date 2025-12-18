package com.bb.eodi.deal.presentation.adapter;

import com.bb.eodi.deal.application.input.FindRealEstateLeaseInput;
import com.bb.eodi.deal.presentation.dto.request.RealEstateLeaseRequestParameter;
import org.springframework.stereotype.Component;

/**
 * 부동산 임대차 실거래가 조회 API request parameter -> application input adapter
 */
@Component
public class FindRealEstateLeaseInputAdapter {

    /**
     * 부동산 임대차
     * @param requestParameter
     * @return
     */
    public FindRealEstateLeaseInput toInput(RealEstateLeaseRequestParameter requestParameter) {
        return new FindRealEstateLeaseInput(
                requestParameter.maxDeposit(),
                requestParameter.minDeposit(),
                requestParameter.maxMonthlyRentFee(),
                requestParameter.minMonthlyRentFee(),
                requestParameter.maxNetLeasableArea(),
                requestParameter.minNetLeasableArea(),
                requestParameter.startYearMonth(),
                requestParameter.endYearMonth(),
                requestParameter.targetRegionIds(),
                requestParameter.targetHousingTypes()
        );
    }
}
