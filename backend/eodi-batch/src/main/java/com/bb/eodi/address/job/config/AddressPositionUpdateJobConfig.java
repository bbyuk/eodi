package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.job.tasklet.AddressLinkageApiCallTasklet;
import com.bb.eodi.address.job.tasklet.AddressLinkageFileUnzipTasklet;
import com.bb.eodi.core.EodiBatchProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.format.DateTimeFormatter;

import static com.bb.eodi.address.domain.service.AddressLinkageTarget.ADDRESS_ENTRANCE;

/**
 * 주소 위치정보 Update Job Config
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class AddressPositionUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * 주소 위치정보 update Job Config
     *
     * @param addressEntranceLinkageApiCallStep
     * @return
     */
    @Bean
    public Job addressPositionUpdateJob(
            Step addressEntranceLinkageApiCallStep,
            Step addressEntranceLinkageFileUnzipStep
    ) {
        return new JobBuilder("addressPositionUpdateJob", jobRepository)
                .start(addressEntranceLinkageApiCallStep)
                .next(addressEntranceLinkageFileUnzipStep)
                .build();
    }

    /**
     * 주소 출입구 정보 연계 API 요청 Step
     *
     * @param addressLinkageApiCallService 주소 연계 API 요청 서비스 컴포넌트
     * @param targetDirectory              연계 파일 다운로드 대상 디렉터리
     * @return 주소 출입구 정보 연계 API 요청 Step
     */
    @Bean
    @JobScope
    public Step addressEntranceLinkageApiCallStep(
            AddressLinkageApiCallService addressLinkageApiCallService,
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        return new StepBuilder("addressEntranceLinkageApiCallStep", jobRepository)
                .tasklet(new AddressLinkageApiCallTasklet(
                        ADDRESS_ENTRANCE,
                        addressLinkageApiCallService,
                        targetDirectory
                ), transactionManager)
                .build();
    }

    /**
     * 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 step
     *
     * @param targetDirectory 재귀적으로 전체 unzip할 대상 디렉터리 - jobParameter
     * @return 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 step
     */
    @Bean
    @JobScope
    public Step addressEntranceLinkageFileUnzipStep(
            @Value("##{jobParameters['target-directory']}") String targetDirectory
    ) {
        return new StepBuilder("addressLinkageFileUnzipStep", jobRepository)
                .tasklet(new AddressLinkageFileUnzipTasklet(targetDirectory), transactionManager)
                .build();
    }


}
