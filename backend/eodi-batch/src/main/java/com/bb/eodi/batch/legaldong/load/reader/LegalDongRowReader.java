package com.bb.eodi.batch.legaldong.load.reader;

import com.bb.eodi.batch.legaldong.LegalDongLoadKey;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 법정동 코드 데이터 reader
 */
@Slf4j
@StepScope
@Component
public class LegalDongRowReader implements ItemStreamReader<LegalDongApiResponseRow> {

    private final List<LegalDongApiResponseRow> list;
    private int idx = 0;

    public LegalDongRowReader(@Value("#{jobExecutionContext['DATA']}") List<LegalDongApiResponseRow> list) {
        this.list = (list != null) ? list : new ArrayList<>();
    }

    @Override
    public LegalDongApiResponseRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return (idx < list.size()) ? list.get(idx++) : null;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.idx = executionContext.getInt(LegalDongLoadKey.CURRENT_DATA_INDEX.name(), 0);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put(LegalDongLoadKey.CURRENT_DATA_INDEX.name(), idx);
    }

    @Override
    public void close() throws ItemStreamException {}
}
