package com.bb.eodi.batch.job.address.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.batch.job.deal.MonthlyDealDataLoadJobKey.CURRENT_INDEX;

/**
 * 주소 데이터 ItemStreamReader 공통 로직 구현 추상클래스
 * @param <T>
 */
@Slf4j
public abstract class AbstractAddressItemStreamReader<T> implements ItemStreamReader<T> {

    private final Path filePath;
    private BufferedReader br;

    private int readCounter = 0;

    public AbstractAddressItemStreamReader(String filePath) {
        this.filePath = Path.of(filePath);
    }

    protected String[] nextLine(String delimiter) throws IOException {
        readCounter++;
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

        return line.split(delimiter, -1);
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(filePath, Charset.forName("MS949"));

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

            log.info("ItemStream -> file opened. file={}", filePath);
        } catch (IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.", e);
            throw new ItemStreamException(e);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.putInt(CURRENT_INDEX.name(), readCounter);
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
