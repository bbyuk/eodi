package com.bb.eodi.batch.legaldong.load.reader;

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

    private final int startOffset;
    private final int pageSize;

    private int globalIndex = 0;
    private int readInThisLoop = 0;

    public LegalDongLoadStepReader(@Value("#{jobExecutionContext['DATA_FILE']}") String path,
                                   @Value("#{jobExecutionContext['READ_START_OFFSET']}") int startOffset,
                                   @Value("#{jobExecutionContext['PAGE_SIZE']}") int pageSize,
                                   ObjectMapper objectMapper) {
        this.path = path;
        this.objectMapper = objectMapper;
        this.startOffset = startOffset;
        this.pageSize = pageSize;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            this.br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);

            // 재시작/루프 진입 지점으로 스킵
            // (파일 기준 글로벌 인덱스는 startOffset부터 시작)
            for (int i = 0; i < startOffset; i++) {
                if (br.readLine() == null) break;
            }
            this.globalIndex = startOffset;
            this.readInThisLoop = 0;

            log.info("LegalDongLoadStepReader opened. file={}, startOffset={}, limit={}", path, startOffset, pageSize);
        } catch (Exception e) {
            log.error("Failed to open reader: {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public LegalDongApiResponseRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // 이번 루프에서 읽을 최대 개수에 도달하면 종료
        if (readInThisLoop >= pageSize) {
            return null;
        }

        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                return null; // EOF
            }
            globalIndex++;

            // 빈 줄/공백 라인 방지
            if (!line.isBlank()) break;
        }

        readInThisLoop++;
        try {
            return objectMapper.readValue(line, LegalDongApiResponseRow.class);
        } catch (Exception ex) {
            // 파싱 실패는 상황에 따라 skip/retry 정책으로 넘길 수도 있음
            log.warn("JSON parse failed at line {}: {}", globalIndex, ex.getMessage());
            throw ex;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // 글로벌 인덱스를 저장해두면 재시작 시 이어서 진행 가능
        // 다음 루프(다음 Step 실행)에서 시작점으로 쓰도록 누적 오프셋도 갱신
        executionContext.putInt(READ_START_OFFSET.name(), globalIndex);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            if (br != null) {
                br.close();
            }
            log.info("LegalDongLoadStepReader closed. lastGlobalIndex={}, readInThisLoop={}", globalIndex, readInThisLoop);
        } catch (Exception e) {
            log.error("Failed to close reader: {}", e.getMessage(), e);
            throw new ItemStreamException(e);
        }
    }
}
