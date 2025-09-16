package com.bb.eodi.batch.job.deal.load.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 부동산 매매 데이터 ItemStream 추상 클래스
 */
@Slf4j
public class AbstractRealEstateDealDataItemStream implements ItemStream {

    protected final Path tempFilePath;
    protected final ObjectMapper objectMapper;
    protected BufferedReader br;

    public AbstractRealEstateDealDataItemStream(Path tempFilePath, ObjectMapper objectMapper) {
        this.tempFilePath = tempFilePath;
        this.objectMapper = objectMapper;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(tempFilePath, StandardCharsets.UTF_8);
            log.info("ItemStream -> file opend. file={}", tempFilePath);
        } catch (IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }


    @Override
    public void close() throws ItemStreamException {
        try {
            br.close();
            Files.deleteIfExists(tempFilePath);
            log.info("ItemStream -> temp file deleted. file={}", tempFilePath);
        } catch (IOException e) {
            log.error("대상 파일 close 중 에러가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }
}
