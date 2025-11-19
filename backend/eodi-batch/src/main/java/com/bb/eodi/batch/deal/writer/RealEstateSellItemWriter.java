package com.bb.eodi.batch.deal.writer;

import com.bb.eodi.batch.deal.entity.RealEstateSell;
import com.bb.eodi.batch.deal.repository.RealEstateSellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 부동산 거래 데이터 chunk ItemWriter
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateSellItemWriter implements ItemWriter<RealEstateSell> {

    private final RealEstateSellRepository realEstateSellRepository;

    @Override
    public void write(Chunk<? extends RealEstateSell> chunk) throws Exception {
        log.info("RealEstateSellItemWriter.write called");
        log.info("write chunk size = {}", chunk.size());

        realEstateSellRepository.saveAllChunk(chunk.getItems());
    }
}
