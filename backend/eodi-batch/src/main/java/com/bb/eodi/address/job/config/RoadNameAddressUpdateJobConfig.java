package com.bb.eodi.address.job.config;

import com.bb.eodi.address.domain.entity.LandLotAddress;
import com.bb.eodi.address.domain.entity.RoadNameAddress;
import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.domain.service.AddressLinkagePeriod;
import com.bb.eodi.address.domain.service.AddressLinkageResult;
import com.bb.eodi.address.job.dto.LandLotAddressItem;
import com.bb.eodi.address.job.dto.RoadNameAddressItem;
import com.bb.eodi.address.job.reader.LandLotAddressUpdateItemReader;
import com.bb.eodi.address.job.reader.RoadNameAddressItemReader;
import com.bb.eodi.address.job.reader.RoadNameAddressUpdateItemReader;
import com.bb.eodi.common.utils.FileCleaner;
import com.bb.eodi.core.EodiBatchProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

    private static final int CONCURRENCY_LIMIT = 6;


    /**
     * 도로명주소 일변동 적용 최신화 배치 Job
     * - 매일 수행 예정
     * - 이미 반영된 경우 skip 처리
     * TODO
     *  (전처리) API 요청 이후 대상일자 디렉터리 내 zip 파일들 unzip tasklet
     *  (후처리) 관련지번 일변동분 반영 후 targetDirectory 삭제
     *
     * @param addressLinkageApiCallStep 주소연계 API 호출 Step (변동분 파일 다운로드)
     * @param roadNameAddressUpdateStep 도로명주소 일변동분 반영 step
     * @param landLotAddressUpdateStep  관련지번 일변동분 반영 step
     * @return 도로명주소 일변동 적용 일배치 Job
     */
    @Bean
    public Job roadNameAddressUpdateJob(
            Step addressLinkageApiCallStep,
            Step addressLinkageFileUnzipStep,
            Step roadNameAddressUpdateStep,
            Step landLotAddressUpdateStep,
            Step tempFileDeleteStep
    ) {
        return new JobBuilder("roadNameAddressUpdateJob", jobRepository)
                .start(addressLinkageApiCallStep)
                .next(addressLinkageFileUnzipStep)
                .next(roadNameAddressUpdateStep)
                .next(landLotAddressUpdateStep)
                .next(tempFileDeleteStep)
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
     * 도로명주소 일변동분 반영 step
     * <p>
     *
     * @param roadNameAddressUpdateItemReader 도로명주소 일변동분 ItemReader
     * @param roadNameAddressUpdateItemProcessor 도로명주소 일변동분 ItemProcessor
     * @param roadNameAddressUpdateItemWriter 도로명주소 일변동분 ItemWriter
     * @return 도로명주소 일변동분 반영 step
     */
    @Bean
    public Step roadNameAddressUpdateStep(
            MultiResourceItemReader<RoadNameAddressItem> roadNameAddressUpdateItemReader,
            ItemProcessor<RoadNameAddressItem, RoadNameAddress> roadNameAddressUpdateItemProcessor,
            ItemWriter<RoadNameAddress> roadNameAddressUpdateItemWriter
    ) {
        return new StepBuilder("roadNameAddressUpdateStep", jobRepository)
                .<RoadNameAddressItem, RoadNameAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(roadNameAddressUpdateItemReader)
                .processor(roadNameAddressUpdateItemProcessor)
                .writer(roadNameAddressUpdateItemWriter)
                .build();
    }

    /**
     * 도로명주소 최신화 배치 도로명주소 MultiResourceItemReqader
     * @param targetDirectory 주소 연계 API 결과 파일 다운로드 대상 디렉터리
     * @return 도로명주소 최신화 배치 도로명주소 MultiResourceItemReqader
     */
    @Bean
    @StepScope
    public MultiResourceItemReader<RoadNameAddressItem> roadNameAddressUpdateItemReader(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        MultiResourceItemReader<RoadNameAddressItem> itemReader = new MultiResourceItemReader<>();

        List<File> files = new ArrayList<>();

        File dir = new File(targetDirectory);
        File[] dirsPerDate = Arrays.stream(Objects.requireNonNull(dir.listFiles())).sorted(Comparator.comparing(File::getName)).toArray(File[]::new);
        for (File dirPerDate : Objects.requireNonNull(dirsPerDate)) {
            files.addAll(
                    Arrays.asList(
                            Objects.requireNonNull(dirPerDate.listFiles(file -> file.getName().endsWith("TH_SGCO_RNADR_MST.TXT")))
                    )
            );
        }

        itemReader.setResources(
                files.stream()
                        .map(FileSystemResource::new)
                        .toArray(Resource[]::new)
        );
        itemReader.setDelegate(new RoadNameAddressUpdateItemReader());
        return itemReader;
    }

    /**
     * 도로명주소 최신화 배치 도로명주소 ItemWriter
     * TODO
     *  - 도로명주소 Item stream writer 구현
     * @return 도로명주소 최신화 배치 도로명주소 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<RoadNameAddress> roadNameAddressUpdateItemWriter() {
        return (chunk) -> {};
    }

    /**
     * 관련지번 일변동분 반영 step
     * <p>
     *
     * @param landLotAddressUpdateItemReader 관련지번 일변동분 ItemReader
     * @param landLotAddressUpdateItemWriter       관련지번 일변동분 ItemWriter
     * @return 관련지번 일변동분 반영 step
     */
    @Bean
    public Step landLotAddressUpdateStep(
            MultiResourceItemReader<LandLotAddressItem> landLotAddressUpdateItemReader,
            ItemProcessor<LandLotAddressItem, LandLotAddress> landLotAddressUpdateItemProcessor,
            ItemWriter<LandLotAddress> landLotAddressUpdateItemWriter
    ) {
        return new StepBuilder("landLotAddressUpdateStep", jobRepository)
                .<LandLotAddressItem, LandLotAddress>chunk(eodiBatchProperties.batchSize(), transactionManager)
                .reader(landLotAddressUpdateItemReader)
                .processor(landLotAddressUpdateItemProcessor)
                .writer(landLotAddressUpdateItemWriter)
                .stream(landLotAddressUpdateItemReader)
                .build();
    }

    /**
     * 도로명주소 최신화 배치 관련지번 MultiResourceItemReqader
     * @param targetDirectory 주소 연계 API 결과 파일 다운로드 대상 디렉터리
     * @return 도로명주소 최신화 배치 관련지번 MultiResourceItemReqader
     */
    @Bean
    @StepScope
    public MultiResourceItemReader<LandLotAddressItem> landLotAddressUpdateItemReader(
            @Value("#{jobParameters['target-directory']}") String targetDirectory
    ) {
        MultiResourceItemReader<LandLotAddressItem> itemReader = new MultiResourceItemReader<>();

        List<File> files = new ArrayList<>();

        File dir = new File(targetDirectory);
        File[] dirsPerDate = Arrays.stream(Objects.requireNonNull(dir.listFiles())).sorted(Comparator.comparing(File::getName)).toArray(File[]::new);
        for (File dirPerDate : Objects.requireNonNull(dirsPerDate)) {
            files.addAll(
                    Arrays.asList(
                            Objects.requireNonNull(dirPerDate.listFiles(file -> file.getName().endsWith("TH_SGCO_RNADR_LNBR.TXT")))
                    )
            );
        }

        itemReader.setResources(
                files.stream()
                        .map(FileSystemResource::new)
                        .toArray(Resource[]::new)
        );
        itemReader.setDelegate(new LandLotAddressUpdateItemReader());
        return itemReader;
    }

    /**
     * 도로명주소 변동분 반영 배치 관련지번 ItemWriter
     * TODO
     *  - 관련지번 Item stream writer 구현
     * @return 도로명주소 변동분 반영 배치 관련지번 ItemWriter
     */
    @Bean
    @StepScope
    public ItemWriter<LandLotAddress> landLotAddressUpdateItemWriter() {
        return (chunk) -> {};
    }


    /**
     * 임시 파일 삭제 Tasklet
     * @param tempFileDeleteTasklet 주소 연계 API를 통해 다운로드받은 임시파일을 삭제한다.
     * @return 임시 파일 삭제 Tasklet
     */
    @Bean
    public Step tempFileDeleteStep(
            Tasklet tempFileDeleteTasklet
    ) {
        return new StepBuilder("tempFileDeleteStep", jobRepository)
                .tasklet(tempFileDeleteTasklet, transactionManager)
                .build();
    }


    @Bean
    @StepScope
    public Tasklet tempFileDeleteTasklet(@Value("#{jobParameters['target-directory']}") String targetDirectory) {
        return (contribution, chunkContext) -> {
            FileCleaner.deleteAll(Path.of(targetDirectory));
            return RepeatStatus.FINISHED;
        };
    }
}