package com.bb.eodi.batch.legaldong.load.reader;

import com.bb.eodi.batch.config.EodiBatchProperties;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.bb.eodi.batch.legaldong.LegalDongLoadKey.READ_START_OFFSET;

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
    private final EodiBatchProperties eodiBatchProperties;

    private int lineIdx = 0;

    public LegalDongLoadStepReader(@Value("#{jobExecutionContext['DATA_FILE']}") String path,
                                   EodiBatchProperties eodiBatchProperties,
                                   ObjectMapper objectMapper) {
        this.path = path;
        this.objectMapper = objectMapper;
        this.eodiBatchProperties = eodiBatchProperties;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        int startOffset = executionContext.getInt(READ_START_OFFSET.name(), 0);

        try {
            this.br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);

            // 재시작/루프 진입 지점으로 스킵
            // (파일 기준 글로벌 인덱스는 startOffset부터 시작)
            for (int i = 0; i < startOffset; i++) {
                if (br.readLine() == null) break;
            }

            log.info("LegalDongLoadStepReader opened. file={}, startOffset={}, limit={}", path, startOffset, eodiBatchProperties.batchSize());
        } catch (Exception e) {
            log.error("Failed to open reader: {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public LegalDongApiResponseRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 이번 루프에서 읽을 최대 개수에 도달하면 종료
        if (lineIdx >= eodiBatchProperties.batchSize()) {
            return null;
        }

        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                return null; // EOF
            }

            // 빈 줄/공백 라인 방지
            if (!line.isBlank()) break;
        }
        lineIdx++;

        try {
            return objectMapper.readValue(line, LegalDongApiResponseRow.class);
        } catch (Exception ex) {
            // 파싱 실패는 상황에 따라 skip/retry 정책으로 넘길 수도 있음
            log.warn("JSON parse failed at line {}: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        int startOffset = executionContext.getInt(READ_START_OFFSET.name(), 0);
        executionContext.putInt(READ_START_OFFSET.name(), startOffset + lineIdx);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (br != null) {
                br.close();
            }
        } catch (Exception e) {
            log.error("Failed to close reader: {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
