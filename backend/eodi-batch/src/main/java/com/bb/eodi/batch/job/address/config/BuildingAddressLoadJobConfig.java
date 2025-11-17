package com.bb.eodi.batch.job.address.config;

import com.bb.eodi.batch.job.address.dto.BuildingAddressItem;
import com.bb.eodi.batch.job.address.entity.BuildingAddress;
import com.bb.eodi.batch.job.address.reader.BuildingAddressItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 건물주소 테이블 초기 적재 job config
 */
@Configuration
@RequiredArgsConstructor
public class BuildingAddressLoadJobConfig {

    private final JobRepository jobRepository;

    /**
     * 건물DB - 건물주소 데이터 초기 적재 job
     *
     * @param buildingAddressLoadStep 건물주소 데이터 적재 step
     * @return 건물주소 데이터 초기 적재 job
     */
    @Bean
    public Job buildingAddressLoadJob(Step buildingAddressLoadStep) {
        return new JobBuilder("buildingAddressLoadJob", jobRepository)
                .start(buildingAddressLoadStep)
                .build();
    }


    /**
     * 건물주소 테이블 전체분 MultiResourceItemReader bean
     *
     * @param targetDirectory job parameter 대상 디렉터리
     * @return 건물주소 테이블 전체분 MultiResourceItemReader
     */
    @Bean
    @StepScope
    public MultiResourceItemReader<BuildingAddressItem> buildingAddressItemMultiResourceItemReader(
            @Value("#{jobParameters['targer-directory']}") String targetDirectory
    ) {
        MultiResourceItemReader<BuildingAddressItem> reader = new MultiResourceItemReader<>();

        File directory = new File(targetDirectory);
        Resource[] resources = Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(file -> file.getName().startsWith("build"))
                .map(FileSystemResource::new)
                .toArray(Resource[]::new);

        reader.setResources(resources);
        reader.setDelegate(new BuildingAddressItemReader());

        return reader;
    }


    /**
     * 건물주소 전체분 ItemProcessor
     *
     * @return 건물주소 전체분 ItemProcessor
     */
    @Bean
    @StepScope
    public ItemProcessor<BuildingAddressItem, BuildingAddress> buildingAddressItemProcessor() {
        Function<String, Integer> parseIntWithNull = str -> str == null || !StringUtils.hasText(str) ? null : Integer.parseInt(str);

        return item -> BuildingAddress.builder()
                .legalDongCode(item.getLegalDongCode())
                .sidoName(item.getSidoName())
                .sigunguName(item.getSigunguName())
                .legalUmdName(item.getLegalUmdName())
                .legalRiName(item.getLegalUmdName())
                .isMountain(item.getIsMountain())
                .landLotMainNo(parseIntWithNull.apply(item.getLandLotMainNo()))
                .landLotSubNo(parseIntWithNull.apply(item.getLandLotSubNo()))
                .roadNameCode(item.getRoadNameCode())
                .roadName(item.getRoadName())
                .isUnderground(item.getIsUnderground())
                .buildingMainNo(parseIntWithNull.apply(item.getBuildingMainNo()))
                .buildingSubNo(parseIntWithNull.apply(item.getBuildingSubNo()))
                .buildingName(item.getBuildingName())
                .buildingNameDetail(item.getBuildingNameDetail())
                .buildingManageNo(item.getBuildingManageNo())
                .umdSeq(item.getUmdSeq())
                .admDongCode(item.getAdmDongCode())
                .admDongName(item.getAdmDongName())
                .zipNo(item.getZipNo())
                .zipNoSeq(item.getZipNoSeq())
                .changeReasonCode(item.getChangeReasonCode())
                .announcementDate(item.getAnnouncementDate())
                .sigunguBuildingName(item.getSigunguBuildingName())
                .isComplex(item.getIsComplex())
                .basicDistrictNo(item.getBasicDistrictNo())
                .hasDetailAddress(item.getHasDetailAddress())
                .remark1(item.getRemark1())
                .remark2(item.getRemark2())
                .build();
    }


}
