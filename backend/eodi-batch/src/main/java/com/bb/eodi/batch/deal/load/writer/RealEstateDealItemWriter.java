package com.bb.eodi.batch.deal.load.writer;

import com.bb.eodi.domain.deal.entity.RealEstateSell;
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
public class RealEstateDealItemWriter implements ItemWriter<RealEstateSell> {

    @Override
    public void write(Chunk<? extends RealEstateSell> chunk) throws Exception {
        log.info("RealEstateDealItemWriter.write called");
        log.info("write chunk size = {}", chunk.size());

    }
}
