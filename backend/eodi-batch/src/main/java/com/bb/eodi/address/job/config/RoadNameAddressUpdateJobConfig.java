package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.domain.service.AddressLinkagePeriod;
import com.bb.eodi.address.domain.service.AddressLinkageResult;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import com.bb.eodi.core.EodiBatchProperties;
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
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.format.DateTimeFormatter;

/**
 * 도로명주소 일변동 적용 일배치 job config
 */
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;

    private static final int CONCURRENCY_LIMIT = 6;


    /**
     * 도로명주소 일변동 적용 최신화 배치 Job
     *      - 매일 수행 예정
     *      - 이미 반영된 경우 skip 처리
     * TODO
     *  (전처리) API 요청 이후 대상일자 디렉터리 내 zip 파일들 unzip tasklet
     *  (후처리) 관련지번 일변동분 반영 후 targetDirectory 삭제
     * @param addressLinkageApiCallStep 주소연계 API 호출 Step (변동분 파일 다운로드)
     * @param roadNameAddressUpdateStep 도로명주소 일변동분 반영 step
     * @param landLotAddressUpdateStep 관련지번 일변동분 반영 step
     * @return 도로명주소 일변동 적용 일배치 Job
     */
    @Bean
    public Job roadNameAddressUpdateJob(
            Step addressLinkageApiCallStep,
            Step roadNameAddressUpdateStep,
            Step landLotAddressUpdateStep
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
     * TODO target-directory 임시 디렉터리이므로 없을시 mkdir 로직 추가
     * 
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


    /**
     * 도로명주소 일변동분 반영 step
     * 
     * TODO
     *  - 도로명주소 Item stream reader 구현
     *  - 도로명주소 Item stream writer 구현
     * 
     * @param roadNameAddressItemStreamReader 도로명주소 일변동분 ItemReader
     * @param roadNameAddressUpdateItemWriter 도로명주소 일변동분 ItemWriter
     * @return 도로명주소 일변동분 반영 step
     */
    @Bean
    public Step roadNameAddressUpdateStep(
            ItemStreamReader<RoadNameAddressItem> roadNameAddressItemStreamReader,
            ItemWriter<RoadNameAddress> roadNameAddressUpdateItemWriter
    ) {
        return new StepBuilder("roadNameAddressUpdateStep", jobRepository)
                .<RoadNameAddressItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(roadNameAddressItemStreamReader)
                .writer(roadNameAddressUpdateItemWriter)
                .stream(roadNameAddressItemStreamReader)
                .build();
    }


    /**
     * 관련지번 일변동분 반영 step
     * 
     * TODO
     *  - 관련지번 Item stream reader 구현
     *  - 관련지번 Item stream writer 구현
     * 
     * @param landLotAddressItemUpdateStreamReader  관련지번 일변동분 ItemReader
     * @param landLotAddressUpdateItemWriter        관련지번 일변동분 ItemWriter
     * @return 관련지번 일변동분 반영 step
     */
    @Bean
    public Step landLotAddressUpdateStep(
            ItemStreamReader<LandLotAddressItem> landLotAddressItemUpdateStreamReader,
            ItemWriter<LandLotAddress> landLotAddressUpdateItemWriter
    ) {
        return new StepBuilder("landLotAddressUpdateStep", jobRepository)
                .<LandLotAddressItem, LandLotAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(landLotAddressItemUpdateStreamReader)
                .writer(landLotAddressUpdateItemWriter)
                .stream(landLotAddressItemUpdateStreamReader)
                .build();
    }
}