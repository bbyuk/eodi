package com.bb.eodi.batch.job.legaldong.writer;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.domain.legaldong.repository.LegalDongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 법정동 코드 적재 배치 writer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LegalDongLoadStepWriter implements ItemWriter<LegalDong> {

    private final LegalDongRepository legalDongRepository;

    @Override
    public void write(Chunk<? extends LegalDong> chunk) throws Exception {
        log.debug("[writer] chunk write");
        legalDongRepository.mergeBatch(chunk.getItems());
        log.debug("[writer] chunk write success");
    }
}
