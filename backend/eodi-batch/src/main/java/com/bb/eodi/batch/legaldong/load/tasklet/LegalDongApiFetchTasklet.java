package com.bb.eodi.batch.legaldong.load.tasklet;

import com.bb.eodi.batch.legaldong.load.api.LegalDongApiClient;
import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponseRow;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.bb.eodi.batch.legaldong.LegalDongLoadKey.DATA_FILE;
import static com.bb.eodi.batch.legaldong.LegalDongLoadKey.PAGE_NUM;

/**
 * 법정동 코드 API 요청 Tasklet
 */
@Slf4j
@StepScope
@Component
public class LegalDongApiFetchTasklet implements Tasklet {

    private final LegalDongApiClient legalDongApiClient;
    private final String targetRegion;
    private final ObjectMapper objectMapper;

    public LegalDongApiFetchTasklet(LegalDongApiClient legalDongApiClient,
                                    @Value("#{jobParameters['region']}") String region,
                                    ObjectMapper objectMapper) {
        this.legalDongApiClient = legalDongApiClient;
        this.targetRegion = region;
        this.objectMapper = objectMapper;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        int pageNum = ctx.getInt(PAGE_NUM.name());

        List<LegalDongApiResponseRow> legalDongApiResponse = legalDongApiClient.findByRegion(targetRegion, pageNum);

        // 1. temp file로 write
        // TODO temp file remove step 필요
        Path tempFile = Files.createTempFile("legal-dong-page-", ".json");
        try (BufferedWriter bw = Files.newBufferedWriter(tempFile)) {
            for (LegalDongApiResponseRow row : legalDongApiResponse) {
                bw.write(objectMapper.writeValueAsString(row));
                bw.write('\n');
            }
        }

        // 2. Page File context 저장 -> reader에서 파일 read
        ctx.putString(DATA_FILE.name(), tempFile.toString());

        return RepeatStatus.FINISHED;
    }
}
