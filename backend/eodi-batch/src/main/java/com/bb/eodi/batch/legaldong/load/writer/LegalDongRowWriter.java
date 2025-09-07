package com.bb.eodi.batch.legaldong.load.writer;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
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
public class LegalDongRowWriter implements ItemWriter<LegalDong> {
    @Override
    public void write(Chunk<? extends LegalDong> chunk) throws Exception {
        log.debug("법정동 적재 배치 writer start");
    }
}
