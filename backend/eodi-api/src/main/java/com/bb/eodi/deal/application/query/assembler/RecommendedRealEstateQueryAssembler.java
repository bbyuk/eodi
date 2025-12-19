package com.bb.eodi.deal.application.query.assembler;

import com.bb.eodi.deal.application.input.FindRecommendedLeaseInput;
import com.bb.eodi.deal.application.input.FindRecommendedSellInput;
import com.bb.eodi.deal.domain.query.RealEstateLeaseQuery;
import com.bb.eodi.deal.domain.query.RealEstateSellQuery;
import com.bb.eodi.deal.domain.type.HousingType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * 추천 부동산 실거래가 application input to domain uery assembler
 */
@Component
public class RecommendedRealEstateQueryAssembler {

    public RealEstateSellQuery assemble(FindRecommendedSellInput input, int priceGap) {
        return RealEstateSellQuery.builder()
                .maxPrice(input.cash() + priceGap)
                .minPrice(input.cash() - priceGap)
                .targetRegionIds(input.targetRegionIds())
                .targetHousingTypes(
                        input.targetHousingTypes()
                                .stream()
                                .map(HousingType::fromCode)
                                .collect(Collectors.toList())
                )
                .maxNetLeasableArea(input.maxNetLeasableArea())
                .minNetLeasableArea(input.minNetLeasableArea())
                .build();
    }

    public RealEstateLeaseQuery assemble(FindRecommendedLeaseInput input) {
        return RealEstateLeaseQuery.builder().build();
    }

}
