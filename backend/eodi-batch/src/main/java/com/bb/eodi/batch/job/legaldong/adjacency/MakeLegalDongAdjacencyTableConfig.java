package com.bb.eodi.batch.job.legaldong.adjacency;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 법정동 인접 테이블 생성 배치 job config
 */
@Configuration
@RequiredArgsConstructor
public class MakeLegalDongAdjacencyTableConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;


    /**
     * SHP 파일로부터 데이터를 읽어 법정동 인접 테이블을 생성하고 저장한다.
     * @param makeLegalDongAdjacencyTableTaskletStep 법정동 인접 테이블 생성 tasklet
     * @return 법정동 인접 테이블 생성 job
     */
    @Bean
    public Job makeLegalDongAdjacencyTable(Step makeLegalDongAdjacencyTableTaskletStep) {
        return new JobBuilder("makeLegalDongAdjacencyTable", jobRepository)
                .start(makeLegalDongAdjacencyTableTaskletStep)
                .build();
    }


    @Bean
    public Step makeLegalDongAdjacencyTableTaskletStep(Tasklet makeLegalDongAdjacencyTableTasklet) {
        return new StepBuilder("makeLegalDongAdjacencyTableTaskletStep", jobRepository)
                .tasklet(makeLegalDongAdjacencyTableTasklet, transactionManager)
                .build();
    }

}
