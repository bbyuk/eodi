package com.bb.eodi.address.job.tasklet;

import com.bb.eodi.common.utils.FileCleaner;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

/**
 * 주소 연계 임시 파일 삭제 Tasklet
 */
@Component
@StepScope
public class AddressLinkageFileDeleteTasklet implements Tasklet {

    private String targetDirectory;

    public AddressLinkageFileDeleteTasklet(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        this.targetDirectory = targetDirectory;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        FileCleaner.deleteAll(Path.of(targetDirectory));
        return RepeatStatus.FINISHED;
        return RepeatStatus.FINISHED;
    }
}
