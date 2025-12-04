package com.bb.eodi.deal.job.writer;

import com.bb.eodi.deal.domain.entity.RealEstateSell;
import com.bb.eodi.deal.domain.repository.RealEstateSellRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 부동산 매매 실거래가 데이터 Item Update Writer
 */
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class RealEstateSellItemUpdateWriter implements ItemWriter<RealEstateSell> {

    private final RealEstateSellRepository realEstateSellRepository;

    @Override
    public void write(Chunk<? extends RealEstateSell> chunk) throws Exception {
        realEstateSellRepository.saveAllChunk(chunk.getItems());
        log.debug("update {} items", chunk.size());
    }
}
