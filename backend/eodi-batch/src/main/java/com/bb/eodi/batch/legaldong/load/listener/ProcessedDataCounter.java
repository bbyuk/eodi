package com.bb.eodi.batch.legaldong.load.listener;

import com.bb.eodi.batch.legaldong.LegalDongLoadKey;
import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Chunk 처리 step 이후 처리 데이터 카운터
 */
@Component
@RequiredArgsConstructor
public class ProcessedLegalDongCounter implements ItemWriteListener<LegalDong> {


    

    @Override
    public void afterWrite(Chunk<? extends LegalDong> items) {
        ItemWriteListener.super.afterWrite(items);
    }
}
