package com.bb.eodi.batch.job.deal.load.reader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.bb.eodi.batch.job.deal.load.MonthlyDealDataLoadJobKey.CURRENT_INDEX;

@Slf4j
public abstract class RealEstateDealDataItemStreamReader<T> implements ItemStreamReader<T> {
    protected final Path tempFilePath;
    protected BufferedReader br;
    protected int readCounter = 0;
    private int skipCount = 16;

    public RealEstateDealDataItemStreamReader(Path tempFilePath) {
        this.tempFilePath = tempFilePath;
    }

    protected String nextLine() throws IOException {
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
            br = Files.newBufferedReader(tempFilePath, Charset.forName("EUC-KR"));

            for (int i = 0; i < skipCount; i++) {
                br.readLine();
            }

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
