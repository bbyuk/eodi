package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.domain.service.AddressLinkagePeriod;
import com.bb.eodi.address.domain.service.AddressLinkageResult;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import com.bb.eodi.address.job.reader.LandLotAddressItemReader;
import com.bb.eodi.address.job.reader.RoadNameAddressItemReader;
import com.bb.eodi.common.utils.FileCleaner;
import com.bb.eodi.core.EodiBatchProperties;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 도로명주소 일변동 적용 일배치 job config
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RoadNameAddressUpdateJobConfig {

    private final JobRepository jobRepository;
    private final EodiBatchProperties eodiBatchProperties;
    private final PlatformTransactionManager transactionManager;
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");
    private static final int CONCURRENCY_LIMIT = 6;


    /**
     * 도로명주소 일변동 적용 최신화 배치 Job
     * - 매일 수행 예정
     * - 이미 반영된 경우 skip 처리
     *
     * @param addressLinkageApiCallStep 주소연계 API 호출 Step (변동분 파일 다운로드)
     * @param addressLinkageFileUnzipStep 주소연계 API 호출 후 다운로드 받은 파일의 압축을 해제한다.
     * @param roadNameAddressUpdateFlow 도로명주소 일변동분 반영 step
     * @param landLotAddressUpdateFlow  관련지번 일변동분 반영 step
     * @param tempFileDeleteStep 주소연계 API 호출을 통해 다운로드 받은 파일을 전체 삭제한다.
     * @return 도로명주소 일변동 적용 일배치 Job
     */
    @Bean
    public Job roadNameAddressUpdateJob(
            Step addressLinkageApiCallStep,
            Step addressLinkageFileUnzipStep,
            Flow roadNameAddressUpdateFlow,
            Flow landLotAddressUpdateFlow,
            Step tempFileDeleteStep
    ) {
        Flow mainFlow = new FlowBuilder<Flow>("mainFlow")
                .start(addressLinkageApiCallStep)
                .next(addressLinkageFileUnzipStep)
                .next(roadNameAddressUpdateFlow)
                .next(landLotAddressUpdateFlow)
                .next(tempFileDeleteStep)
                .end();

        return new JobBuilder("roadNameAddressUpdateJob", jobRepository)
                .start(mainFlow)
                .end()
                .build();
    }

    /**
     * 주소 연계 API 호출 Step
     *
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
            // targetDirectory 없으면 mkdir
            File targetDirectoryObj = new File(targetDirectory);
            if (!targetDirectoryObj.exists()) {
                targetDirectoryObj.mkdirs();
            }

            if (!targetDirectoryObj.isDirectory()) {
                throw new RuntimeException("Target directory is not a directory: " + targetDirectory);
            }

            ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

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
     * 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 step
     *
     * @param addressLinkageFileUnzipTasklet 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 Tasklet
     * @return 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 step
     */
    @Bean
    public Step addressLinkageFileUnzipStep(Tasklet addressLinkageFileUnzipTasklet) {
        return new StepBuilder("addressLinkageFileUnzipStep", jobRepository)
                .tasklet(addressLinkageFileUnzipTasklet, transactionManager)
                .build();
    }

    /**
     * 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 Tasklet
     *
     * @param targetDirectory 주소연계 처리 대상 임시 디렉터리
     * @return 주소 연계 API를 통해 다운로드 받은 파일을 모두 unzip 하는 Tasklet
     */
    @Bean
    @StepScope
    public Tasklet addressLinkageFileUnzipTasklet(
            @Value("#{jobParameters['target-directory']}") String targetDirectory) {
        return (contribution, chunkContext) -> {
            File dir = new File(targetDirectory);
            File[] subDirectories = dir.listFiles();

            // 일자별 처리
            for (File subDirectory : Objects.requireNonNull(subDirectories)) {
                File[] zipFiles = subDirectory.listFiles();

                Arrays.stream(Objects.requireNonNull(zipFiles))
                        .forEach(zipFile -> {
                            // 현재 디렉터리에 풀기
                            Path targetPath = zipFile.getParentFile().toPath();

                            try (InputStream fis = new FileInputStream(zipFile);
                                 BufferedInputStream bis = new BufferedInputStream(fis);
                                 ZipInputStream zis = new ZipInputStream(bis)) {

                                Files.createDirectories(targetPath);

                                ZipEntry entry;
                                while ((entry = zis.getNextEntry()) != null) {

                                    Path resolvedPath = targetPath.resolve(entry.getName()).normalize();

                                    // Zip Slip 공격 방지 (필수)
                                    if (!resolvedPath.startsWith(targetPath)) {
                                        throw new IOException("Invalid ZIP entry: " + entry.getName());
                                    }

                                    if (entry.isDirectory()) {
                                        Files.createDirectories(resolvedPath);
                                    } else {
                                        Files.createDirectories(resolvedPath.getParent());

                                        try (OutputStream os = Files.newOutputStream(resolvedPath)) {
                                            zis.transferTo(os);
                                        }
                                    }

                                    zis.closeEntry();
                                }

                            } catch (IOException e) {
                                throw new RuntimeException("ZIP 압축 해제 실패", e);
                            }
                        });
            }

            return RepeatStatus.FINISHED;
        };
    }


    /**
     * 도로명주소 update flow
     *
     * @param fromDate                        시작 Date
     * @param toDate                          종료 Date
     * @param targetDirectory                 주소 연계 API 파일 다운로드 대상 디렉터리
     * @param roadNameAddressItemProcessor    도로명주소 ItemProcessor
     * @param roadNameAddressUpdateItemWriter 도로명주소 update ItemWriter
     * @return 도로명주소 update flow
     */
    @Bean
    @JobScope
    public Flow roadNameAddressUpdateFlow(
            @Value("#{jobExecutionContext['fromDate']}") String fromDate,
            @Value("#{jobExecutionContext['toDate']}") String toDate,
            @Value("#{jobParameters['target-directory']}") String targetDirectory,
            ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressUpdateItemWriter,
            ReferenceVersionRepository referenceVersionRepository
    ) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("roadNameAddressUpdateFlow");

        LocalDate from = LocalDate.parse(fromDate, dtf);
        LocalDate to = LocalDate.parse(toDate, dtf);

        from.datesUntil(to.plusDays(1))
                .forEach(date -> {
                    File targetFile = Arrays.stream(
                                    Objects.requireNonNull(Paths.get(targetDirectory).resolve(date.format(dtf)).toFile()
                                            .listFiles(file -> file.getName().endsWith("TH_SGCO_RNADR_MST.TXT")))).findFirst()
                            .orElseThrow(() -> new RuntimeException("파일을 찾지 못했습니다."));

                    Step workerStep = roadNameAddressUpdateStep(targetFile.getAbsolutePath(), roadNameAddressItemProcessor, roadNameAddressUpdateItemWriter);
                    Step referenceVersionUpdateStep = referenceVersionUpdateStep(referenceVersionRepository, "roadNameAddress", date);

                    if (date.isEqual(from)) {
                        flowBuilder.start(workerStep);
                    } else {
                        flowBuilder.next(workerStep);
                    }
                    flowBuilder.next(referenceVersionUpdateStep);
                });

        return flowBuilder.build();
    }

    /**
     * 도로명주소 일변동분 반영 step
     * <p>
     *
     * @return 도로명주소 일변동분 반영 step
     */
    public Step roadNameAddressUpdateStep(
            String targetFilePath,
            ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressUpdateItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressUpdateItemWriter
    ) {
        RoadNameAddressItemReader itemReader = new RoadNameAddressItemReader(targetFilePath);
        return new StepBuilder("roadNameAddressUpdateStep", jobRepository)
                .<RoadNameAddressItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(itemReader)
                .processor(roadNameAddressUpdateItemProcessor)
                .writer(roadNameAddressUpdateItemWriter)
                .stream(itemReader)
                .build();
    }

    /**
     * 기준정보버전 업데이트 step
     *
     * @param referenceVersionRepository 기준정보버전 repository bean
     * @param referenceVersionName       업데이트 대상명
     * @param value                      업데이트 값 - effective_date
     * @return 기준정보버전 업데이트 step
     */
    public Step referenceVersionUpdateStep(
            ReferenceVersionRepository referenceVersionRepository, String referenceVersionName, LocalDate value) {
        return new StepBuilder("roadNameAddressReferenceVersionUpdateStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    referenceVersionRepository.updateEffectiveDateByReferenceVersionName(value, referenceVersionName);
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    /**
     * 관련지번 update flow
     *
     * @param fromDate                        시작 Date
     * @param toDate                          종료 Date
     * @param targetDirectory                 주소 연계 API 파일 다운로드 대상 디렉터리
     * @param landLotAddressItemProcessor    관련지번 ItemProcessor
     * @param landLotAddressUpdateItemWriter 관련지번 update ItemWriter
     * @return 관련지번 update flow
     */
    @Bean
    @JobScope
    public Flow landLotAddressUpdateFlow(
            @Value("#{jobExecutionContext['fromDate']}") String fromDate,
            @Value("#{jobExecutionContext['toDate']}") String toDate,
            @Value("#{jobParameters['target-directory']}") String targetDirectory,
            ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressItemProcessor,
            ItemWriter<LandLotAddress> landLotAddressUpdateItemWriter,
            ReferenceVersionRepository referenceVersionRepository
    ) {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("landLotAddressUpdateFlow");

        LocalDate from = LocalDate.parse(fromDate, dtf);
        LocalDate to = LocalDate.parse(toDate, dtf);

        from.datesUntil(to.plusDays(1))
                .forEach(date -> {
                    File targetFile = Arrays.stream(
                                    Objects.requireNonNull(Paths.get(targetDirectory).resolve(date.format(dtf)).toFile()
                                            .listFiles(file -> file.getName().endsWith("TH_SGCO_RNADR_LNBR.TXT")))).findFirst()
                            .orElseThrow(() -> new RuntimeException("파일을 찾지 못했습니다."));

                    Step workerStep = landLotAddressUpdateStep(targetFile.getAbsolutePath(), landLotAddressItemProcessor, landLotAddressUpdateItemWriter);
                    Step referenceVersionUpdateStep = referenceVersionUpdateStep(referenceVersionRepository, "roadNameAddress", date);

                    if (date.isEqual(from)) {
                        flowBuilder.start(workerStep);
                    } else {
                        flowBuilder.next(workerStep);
                    }
                    flowBuilder.next(referenceVersionUpdateStep);
                });

        return flowBuilder.build();
    }


    /**
     * 관련지번 일변동분 반영 step
     * <p>
     *
     * @param targetFilePath 관련지번 일변동분 대상 파일 경로
     * @param landLotAddressUpdateItemWriter 관련지번 일변동분 ItemWriter
     * @return 관련지번 일변동분 반영 step
     */
    public Step landLotAddressUpdateStep(
            String targetFilePath,
            ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressUpdateItemProcessor,
            ItemWriter<LandLotAddress> landLotAddressUpdateItemWriter
    ) {
        LandLotAddressItemReader itemReader = new LandLotAddressItemReader(targetFilePath);
        return new StepBuilder("landLotAddressUpdateStep", jobRepository)
                .<LandLotAddressItem, LandLotAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(itemReader)
                .processor(landLotAddressUpdateItemProcessor)
                .writer(landLotAddressUpdateItemWriter)
                .stream(itemReader)
                .build();
    }

    /**
     * 임시 파일 삭제 Step
     *
     * @param tempFileDeleteTasklet 주소 연계 API를 통해 다운로드받은 임시파일을 삭제한다.
     * @return 임시 파일 삭제 Step
     */
    @Bean
    public Step tempFileDeleteStep(
            Tasklet tempFileDeleteTasklet
    ) {
        return new StepBuilder("tempFileDeleteStep", jobRepository)
                .tasklet(tempFileDeleteTasklet, transactionManager)
                .build();
    }


    /**
     * 임시 파일 삭제 Tasklet
     *
     * @param targetDirectory 주소 연계 API를 통해 변동분 파일을 다운로드 받을 대상 디렉터리 -> job 마지막 step에서 삭제처리한다.
     * @return 임시 파일 삭제 Tasklet
     */
    @Bean
    @StepScope
    public Tasklet tempFileDeleteTasklet(@Value("#{jobParameters['target-directory']}") String targetDirectory) {
        return (contribution, chunkContext) -> {
            FileCleaner.deleteAll(Path.of(targetDirectory));
            return RepeatStatus.FINISHED;
        };
    }
}