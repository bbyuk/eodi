package com.bb.eodi.deal.application.query.assembler;

import com.bb.eodi.deal.application.input.FindRealEstateSellInput;
import com.bb.eodi.deal.application.query.RealEstateSellQuery;
import org.springframework.stereotype.Component;

/**
 * 부동산 매매 실거래가 조회 query assembler
 */
@Component
public class FindRealEstateSellQueryAssembler {
    public RealEstateSellQuery assemble(FindRealEstateSellInput input) {
        return RealEstateSellQuery
                .builder()
                .maxPrice(input.maxPrice())
                .minPrice(input.minPrice())
                .maxNetLeasableArea(input.maxNetLeasableArea())
                .minNetLeasableArea(input.minNetLeasableArea())
                .startYearMonth(input.startYearMonth())
                .endYearMonth(input.endYearMonth())
                .targetHousingTypes(input.targetHousingTypes())
                .targetRegionIds(input.targetRegionIds())
                .build();
    }
}
