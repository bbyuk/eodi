package com.bb.eodi.batch.job.address.common.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * 파일 목록 Read Tasklet
 */
@Component
@StepScope
public class BuildingDBFileListingTasklet implements Tasklet {

    @Value("#{jobParameters['target-directory']}")
    private String targetDirectory;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        File directory = new File(targetDirectory);

        chunkContext.getStepContext().getStepExecution()
                .getJobExecution().getExecutionContext()
                .put("files", Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                        .filter(File::isFile)
                        .map(File::getAbsolutePath)
                        .toList());

        return RepeatStatus.FINISHED;
    }
}
