package com.bb.eodi.batch.job.legaldong.load.writer;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LegalDongParentMappingStepWriter implements ItemWriter<LegalDong> {

    private final LegalDongRepository legalDongRepository;

    @Override
    public void write(Chunk<? extends LegalDong> chunk) throws Exception {
        log.debug("legal dong parent mapping writer");
        legalDongRepository.mappingParentIdBatch(chunk.getItems());
    }
}
