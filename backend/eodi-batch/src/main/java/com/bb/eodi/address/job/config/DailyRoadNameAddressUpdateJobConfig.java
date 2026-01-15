package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.port.AddressLinkageApiPort;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.ops.domain.entity.ReferenceVersion;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
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
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

/**
 * 도로명주소 일변동 적용 일배치 job config
 */
@Configuration
@RequiredArgsConstructor
public class DailyRoadNameAddressUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;


    /**
     * 도로명주소 일변동 적용 일배치 Job
     * @param addressLinkageApiCallStep 주소연계 API 호출 Step (변동분 파일 다운로드)
     * @return 도로명주소 일변동 적용 일배치 Job
     */
    @Bean
    public Job dailyRoadNameAddressUpdateJob(
            Step addressLinkageApiCallStep
    ) {
        return new JobBuilder("dailyRoadNameAddressUpdateJob", jobRepository)
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
     * @param addressLinkageApiPort 주소연계 API Port
     * @return 주소 연계 API 요청 Tasklet
     */
    @Bean
    @StepScope
    public Tasklet addressLinkageApiCallTasklet(
            @Value("#{jobParameters['target-directory']}") String targetDirectory,
            ReferenceVersionRepository referenceVersionRepository,
            AddressLinkageApiPort addressLinkageApiPort
    ) {
        return (contribution, chunkContext) -> {
            ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");

            /**
             * TODO
             * 1. 기준정보 현재 적용 일자 조회
             * 2. 주소 / 주소 입구 정보 적용 일자 가져와서 그 다음날부터 step 수행
             * 3. 모두 처리 후 파일 삭제 -> 처리 후 스텝에서 작업
             */
            String addressReferenceName = "address";
            ReferenceVersion referenceVersion = referenceVersionRepository.findByTargetName(addressReferenceName)
                    .orElseThrow(() -> new RuntimeException(addressReferenceName + " 기준정보 버전 정보를 찾지 못했습니다."));

            LocalDate today = LocalDate.now();
            if (!today.isAfter(referenceVersion.getEffectiveDate())) {
                throw new JobInterruptedException("주소 DB가 이미 최신 상태입니다.");
            }
            LocalDate fromDate = referenceVersion.getEffectiveDate().plusDays(1);

            long between = ChronoUnit.DAYS.between(fromDate, today);

            /**
             * 10일 초과시 10일씩 나눠서 api 요청 및 jobExecutionContext에 등록
             */
            if (between > 10) {
                long mul = between / 10;

                for (int i = 0; i < mul; i++) {
                    LocalDate fixedFromDate = fromDate.plusDays(i * 10);
                    LocalDate fixedToDate = fromDate.plusDays((i + 1) * 10);

                    addressLinkageApiPort.downloadUpdatedAddress(targetDirectory, fixedFromDate, fixedToDate);
                }

                LocalDate lastFromDate = fromDate.plusDays(mul * 10);
                addressLinkageApiPort.downloadUpdatedAddress(targetDirectory, lastFromDate, today);
            }


            jobCtx.put("fromDate", fromDate.format(dtf));
            jobCtx.put("toDate", today.format(dtf));

            return RepeatStatus.FINISHED;
        };
    }
}