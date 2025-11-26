package com.bb.eodi.deal.job.processor;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 부동산 매매 실거래가 데이터 좌표 매핑 ItemProcessor
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateSellDataPositionMappingItemProcessor implements ItemProcessor<RealEstateSell, RealEstateSell> {

    @Override
    public RealEstateSell process(RealEstateSell item) throws Exception {
        return item;
    }
}
