package com.bb.eodi.deal.job.writer;

import com.bb.eodi.deal.domain.entity.RealEstateLease;
import com.bb.eodi.deal.domain.repository.RealEstateLeaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


/**
 * 부동산 임대차 실거래가 아이템 update writer
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateLeaseItemUpdateWriter implements ItemWriter<RealEstateLease> {

    private final RealEstateLeaseRepository realEstateLeaseRepository;

    @Override
    public void write(Chunk<? extends RealEstateLease> chunk) throws Exception {
        realEstateLeaseRepository.batchUpdatePosition(chunk.getItems());
        log.debug("{} item update", chunk.size());
    }
}
