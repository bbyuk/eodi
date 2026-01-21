package com.bb.eodi.legaldong.job.reader;

import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.legaldong.job.dto.LegalDongItem;
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
 * 법정동 ItemReader
 */
@Slf4j
@StepScope
@Component
public class LegalDongItemReader implements ItemStreamReader<LegalDongItem> {
    private final String targetFile;

    private BufferedReader br;

    private final EodiBatchProperties eodiBatchProperties;

    public LegalDongItemReader(@Value("#{jobParameters['target-file']}") String targetFile,
                                   EodiBatchProperties eodiBatchProperties) {
        this.targetFile = targetFile;
        this.eodiBatchProperties = eodiBatchProperties;
    }


    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        try {
            br = Files.newBufferedReader(Paths.get(targetFile), StandardCharsets.UTF_8);
            log.info("LegalDongLoadStepReader opened. file={}, limit={}", targetFile, eodiBatchProperties.batchSize());

            br.readLine();
            log.info("skip header line");
        }
        catch(IOException e) {
            log.error("대상 파일을 open 하는 도중 문제가 발생했습니다.");
            throw new ItemStreamException(e);
        }
    }

    @Override
    public LegalDongItem read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // read
        String line;

        while (true) {
            line = br.readLine();
            if (line == null) {
                return null; // EOF
            }

            if (!line.isBlank()) break;
        }

        String[] split = line.split(",", -1);

        return LegalDongItem.builder()
                .legalDongCode(split[0])
                .sidoName(split[1])
                .sigunguName(split[2])
                .umdName(split[3])
                .riName(split[4])
                .legalDongOrder(split[5])
                .entranceDate(split[6])
                .revocationDate(split[7])
                .beforeLegalDongCode(split[8])
                .build();
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
