package com.bb.eodi.batch.job.deal.writer;

import com.bb.eodi.domain.deal.entity.RealEstateLease;
import com.bb.eodi.domain.deal.repository.RealEstateLeaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 부동산 임대차 실거래가 데이터 적재 배치 chunk step ItemWriter
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateLeaseItemWriter implements ItemWriter<RealEstateLease> {
    private final RealEstateLeaseRepository realEstateLeaseRepository;

    @Override
    public void write(Chunk<? extends RealEstateLease> chunk) throws Exception {
        log.info("RealEstateSellItemWriter.write called");
        log.info("write chunk size = {}", chunk.size());

        realEstateLeaseRepository.saveAllChunk(chunk.getItems());
    }
}
