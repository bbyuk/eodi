package com.bb.eodi.batch.legaldong.reader;

import com.bb.eodi.batch.core.config.EodiBatchProperties;
import com.bb.eodi.batch.legaldong.model.LegalDongApiResponseRow;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    public LegalDongLoadStepReader(@Value("#{jobExecutionContext['DATA_FILE']}") String path,
                                   EodiBatchProperties eodiBatchProperties,
                                   ObjectMapper objectMapper) {
        this.path = path;
        this.objectMapper = objectMapper;
        this.eodiBatchProperties = eodiBatchProperties;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8);
            log.info("LegalDongLoadStepReader opened. file={}, limit={}", path, eodiBatchProperties.batchSize());
        }
        catch(IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }

    @Override
    public LegalDongApiResponseRow read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // read
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                return null; // EOF
            }

            if (!line.isBlank()) break;
        }

        return objectMapper.readValue(line, LegalDongApiResponseRow.class);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            br.close();
        }
        catch (IOException e) {
            log.error("대상 파일 close 중 에러가 발생했습니다.");
            throw new ItemStreamException(e);
        }

    }
}
