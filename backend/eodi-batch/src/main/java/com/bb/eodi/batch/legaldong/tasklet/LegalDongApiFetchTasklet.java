package com.bb.eodi.batch.legaldong.tasklet;

import com.bb.eodi.port.out.legaldong.LegalDongDataPort;
import com.bb.eodi.batch.legaldong.model.LegalDongApiResponseRow;
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

import static com.bb.eodi.batch.legaldong.enums.LegalDongLoadKey.DATA_FILE;
import static com.bb.eodi.batch.legaldong.enums.LegalDongLoadKey.TOTAL_COUNT;

/**
 * 법정동 코드 API 요청 Tasklet
 */
@Slf4j
@StepScope
@Component
public class LegalDongApiFetchTasklet implements Tasklet {

    private final LegalDongDataPort legalDongDataPort;
    private final String targetRegion;
    private final ObjectMapper objectMapper;

    public LegalDongApiFetchTasklet(LegalDongDataPort legalDongDataPort,
                                    @Value("#{jobParameters['region']}") String region,
                                    ObjectMapper objectMapper) {
        this.legalDongDataPort = legalDongDataPort;
        this.targetRegion = region;
        this.objectMapper = objectMapper;
    }


    /**
     * API 요청 및 임시파일에 데이터 append
     * 페이지 수만큼 API 요청해 file에 append
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        int totalCount = ctx.getInt(TOTAL_COUNT.name());
        int pageCount = (totalCount / legalDongDataPort.getPageSize()) + 1;
        Path tempFile = Paths.get(ctx.getString(DATA_FILE.name()));

        for (int pageNum = 1; pageNum <= pageCount; pageNum++) {
            List<LegalDongApiResponseRow> legalDongApiResponse = legalDongDataPort.findByRegion(targetRegion, pageNum);
            try (BufferedWriter bw = Files.newBufferedWriter(tempFile, StandardOpenOption.APPEND )) {
                for (LegalDongApiResponseRow row : legalDongApiResponse) {
                    bw.write(objectMapper.writeValueAsString(row));
                    bw.newLine();
                }
            }
            catch(Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        return RepeatStatus.FINISHED;
    }
}
