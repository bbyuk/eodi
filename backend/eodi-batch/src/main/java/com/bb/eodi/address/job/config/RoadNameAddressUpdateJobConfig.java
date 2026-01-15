package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.port.AddressLinkageApiPort;
import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.domain.service.AddressLinkageResult;
import com.bb.eodi.address.domain.vo.AddressLinkagePeriod;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 도로명주소 일변동 적용 일배치 job config
 */
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;


    /**
     * 도로명주소 일변동 적용 최신화 배치 Job
     *      - 매일 수행 예정
     *      - 이미 반영된 경우 skip 처리
     * @param addressLinkageApiCallStep 주소연계 API 호출 Step (변동분 파일 다운로드)
     * @return 도로명주소 일변동 적용 일배치 Job
     */
    @Bean
    public Job roadNameAddressUpdateJob(
            Step addressLinkageApiCallStep
    ) {
        return new JobBuilder("roadNameAddressUpdateJob", jobRepository)
                .start(addressLinkageApiCallStep)
                .build();
    }

    /**
     * 주소 연계 API 호출 Step
     * @param addressLinkageApiCallTasklet 주소 연계 API 호출 Tasklet
     * @return 주소 연계 API 호출 Step
     */
    @Bean
    public Step addressLinkageApiCallStep(Tasklet addressLinkageApiCallTasklet) {
        return new StepBuilder("addressLinkageApiCallStep", jobRepository)
                .tasklet(addressLinkageApiCallTasklet, transactionManager)
                .build();
    }

    /**
     * 주소 연계 API 요청 Tasklet
     * @param targetDirectory 임시파일 다운로드 대상 디렉터리
     * @return 주소 연계 API 요청 Tasklet
     */
    @Bean
    @StepScope
    public Tasklet addressLinkageApiCallTasklet(
            @Value("#{jobParameters['target-directory']}") String targetDirectory,
            AddressLinkageApiCallService addressLinkageApiCallService
    ) {
        return (contribution, chunkContext) -> {
            ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");

            AddressLinkagePeriod targetPeriod = addressLinkageApiCallService.findTargetPeriod();
            AddressLinkageResult addressLinkageResult = addressLinkageApiCallService.downloadNewFiles(targetDirectory, targetPeriod);

            if (AddressLinkageResult.ALREADY_UP_TO_DATE == addressLinkageResult) {
                throw new JobInterruptedException("주소 DB가 이미 최신 상태입니다.");
            }

            jobCtx.put("fromDate", targetPeriod.from().format(dtf));
            jobCtx.put("toDate", targetPeriod.to().format(dtf));

            return RepeatStatus.FINISHED;
        };
    }
}