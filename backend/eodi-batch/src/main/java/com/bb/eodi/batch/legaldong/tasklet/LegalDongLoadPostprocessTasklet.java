package com.bb.eodi.batch.legaldong.tasklet;

import com.bb.eodi.batch.legaldong.enums.LegalDongLoadKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 법정동 적재 배치 후처리 Step Tasklet
 */
@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class LegalDongLoadPostprocessTasklet implements Tasklet {

    /**
     * 임시 파일 삭제
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        ExecutionContext ctx = contribution.getStepExecution().getJobExecution().getExecutionContext();
        Path tempFilePath = Paths.get(ctx.getString(LegalDongLoadKey.DATA_FILE.name()));

        Files.deleteIfExists(tempFilePath);

        log.info("temp file deleted : {}", tempFilePath);
        return RepeatStatus.FINISHED;
    }
}


