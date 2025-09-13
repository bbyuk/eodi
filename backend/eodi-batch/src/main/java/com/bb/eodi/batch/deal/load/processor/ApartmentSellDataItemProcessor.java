package com.bb.eodi.batch.deal.load.processor;

import com.bb.eodi.domain.deal.entity.RealEstateDeal;
import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class ApartmentSellDataItemProcessor implements ItemProcessor<ApartmentSellDataItem, RealEstateDeal> {
    @Override
    public RealEstateDeal process(ApartmentSellDataItem item) throws Exception {
        log.info("ApartmentSellDataItemProcessor.process called");
        log.debug("item : {}", item);
        return null;
    }
}
