package com.bb.eodi.batch.job.deal.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.batch.job.deal.MonthlyDealDataLoadJobKey.*;

@Slf4j
public class RealEstateDealDataItemStreamReader<T> implements ItemStreamReader<T> {
    private final Class<T> targetClass;
    private final Path tempFilePath;
    private final ObjectMapper objectMapper;
    private BufferedReader br;

    private int readCounter = 0;

    public RealEstateDealDataItemStreamReader(
            Class<T> targetClass,
        Path tempFilePath,
        ObjectMapper objectMapper
    ) {
        this.targetClass = targetClass;
        this.tempFilePath = tempFilePath;
        this.objectMapper = objectMapper;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String line = nextLine();

        readCounter++;
        log.info("RealEstateDealDataItemStreamReader -> {} read line={}", targetClass.getName() ,line);
        return line == null ? null : objectMapper.readValue(line, targetClass);
    }

    private String nextLine() throws IOException {
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
        return line;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(tempFilePath, StandardCharsets.UTF_8);

            if (executionContext.containsKey(CURRENT_INDEX.name())) {
                int lastIndex = executionContext.getInt(CURRENT_INDEX.name());
                log.info("running from index {}", lastIndex);

                for (int i = 0; i < lastIndex; i++) {
                    br.readLine();
                }

                this.readCounter = lastIndex;
            }
            else {
                this.readCounter = 0;
            }

            log.info("ItemStream -> file opened. file={}", tempFilePath);
        } catch (IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.", e);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(CURRENT_INDEX.name(), readCounter);
//        ItemStreamReader.super.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        try {
            br.close();
        } catch (IOException e) {
            log.error("대상 파일 close 중 에러가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }
}
