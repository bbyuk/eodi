package com.bb.eodi.batch.deal.load.reader;

import com.bb.eodi.port.out.deal.dto.ApartmentSellDataItem;
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
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 아파트 매매 데이터 chunk Item Reader
 */
@Slf4j
@StepScope
@Component
public class ApartmentSellDataItemReader implements ItemStreamReader<ApartmentSellDataItem> {

    private final Path tempFilePath;
    private final ObjectMapper objectMapper;
    private BufferedReader br;

    public ApartmentSellDataItemReader(
            @Value("#{jobExecutionContext['APT_SALE_TEMP_FILE']}") String tempFilePath,
            ObjectMapper objectMapper) {
        this.tempFilePath = Paths.get(tempFilePath);
        this.objectMapper = objectMapper;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(tempFilePath, StandardCharsets.UTF_8);
            log.info("ApartmentSellDataItemReader -> file opend. file={}", tempFilePath);
        } catch (IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }

    @Override
    public ApartmentSellDataItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                return null;
            }

            if (!line.isBlank()) {
                break;
            }
        }

        log.info("ApartmentSellDataItemReader -> read line={}", line);
        return objectMapper.readValue(line, ApartmentSellDataItem.class);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            br.close();
            Files.deleteIfExists(tempFilePath);
            log.info("ApartmentSellDataItemReader -> temp file deleted. file={}", tempFilePath);
        } catch (IOException e) {
            log.error("대상 파일 close 중 에러가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }
}
