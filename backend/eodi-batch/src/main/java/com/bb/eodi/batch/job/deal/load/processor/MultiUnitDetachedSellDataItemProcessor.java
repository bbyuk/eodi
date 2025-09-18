package com.bb.eodi.batch.job.deal.load.processor;

import com.bb.eodi.domain.deal.entity.RealEstateSell;
import com.bb.eodi.port.out.deal.dto.MultiUnitDetachedSellDataItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 단독/다가구주택 매매 데이터 적재 Chunk Step ItemProcessor
 */
@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class MultiUnitDetachedSellDataItemProcessor
        implements ItemProcessor<MultiUnitDetachedSellDataItem, RealEstateSell> {

    @Override
    public RealEstateSell process(MultiUnitDetachedSellDataItem item) throws Exception {
        log.info("multi unit-detached sell item process: {}", item);
        return null;
    }
}

