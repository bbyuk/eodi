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
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

        Path tempFile = Paths.get(ctx.getString(DATA_FILE.name()));

        // 1. temp file로 write
        // TODO temp file remove step 필요
        try (BufferedWriter bw = Files.newBufferedWriter(tempFile,
                StandardOpenOption.CREATE,      // 없으면 새로 생성
                StandardOpenOption.APPEND )) {  // 있으면 이어쓰기
            for (LegalDongApiResponseRow row : legalDongApiResponse) {
                bw.write(objectMapper.writeValueAsString(row));
                bw.newLine();
            }
        }

        return RepeatStatus.FINISHED;
    }
}
