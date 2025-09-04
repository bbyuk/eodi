package com.bb.eodi.batch.job.legaldong.load.processor;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import com.bb.eodi.batch.job.legaldong.load.model.LegalDongRow;
import org.springframework.batch.item.ItemProcessor;

/**
 * 법정동코드 적재 배치 Item Processor
 */
public class LegalDongRowProcessor implements ItemProcessor<LegalDongRow, LegalDong> {
    @Override
    public LegalDong process(LegalDongRow item) throws Exception {
        return null;
    }
}
