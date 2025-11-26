package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 부동산 임대차 실거래가 데이터 좌표 매핑 ItemProcessor
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateLeaseDataPositionMappingItemProcessor implements ItemProcessor<RealEstateLease, RealEstateLease> {

    @Override
    public RealEstateLease process(RealEstateLease item) throws Exception {
        return item;
    }
}
