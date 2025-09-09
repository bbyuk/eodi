package com.bb.eodi.batch.legaldong.load.reader;

import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.bb.eodi.batch.legaldong.LegalDongLoadKey.CURRENT_DATA_INDEX;

/**
 * 법정동 코드 데이터 reader
 */
@Slf4j
@StepScope
@Component
public class LegalDongLoadStepReader implements ItemStreamReader<LegalDongApiResponseRow> {

    private final String path;
    private final ObjectMapper objectMapper;

    private BufferedReader br;
    private int lineIdx = 0;

    public LegalDongLoadStepReader(@Value("#{jobExecutionContext['DATA_FILE']}") String path,
                                   ObjectMapper objectMapper) {
        this.path = path;
        this.objectMapper = objectMapper;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        this.lineIdx = executionContext.getInt(CURRENT_DATA_INDEX.name(), 0);

        try {
            this.br = Files.newBufferedReader(Paths.get(path));
            // 재시작이면 lineIdx만큼 스킵
            for (int i = 0; i < lineIdx; i++) {
                if (br.readLine() == null) break;
            }
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public LegalDongApiResponseRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = br.readLine();
        if (line == null) return null;

        lineIdx++;
        return objectMapper.readValue(line, LegalDongApiResponseRow.class);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put(CURRENT_DATA_INDEX.name(), lineIdx);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (br != null) {
                br.close();
            }
        }
        catch(Exception e) {
            log.error(e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
