package com.bb.eodi.batch.legaldong.load.processor;

import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * 법정동 코드 적재 배치 processor
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LegalDongRowProcessor implements ItemProcessor<LegalDongApiResponseRow, LegalDong> {

    @Override
    public LegalDong process(LegalDongApiResponseRow item) throws Exception {
        log.debug("법정동 적재 배치 processor start");
        return null;
    }
}
