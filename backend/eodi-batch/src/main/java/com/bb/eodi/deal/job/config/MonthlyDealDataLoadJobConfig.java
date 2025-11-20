package com.bb.eodi.deal.job.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 월별 부동산 실거래가 데이터 적재 배치 job 설정
 */
@Configuration
@RequiredArgsConstructor
public class MonthlyDealDataLoadJobConfig {

    private final JobRepository jobRepository;

    /**
     * 월별 부동산 실거래가 데이터 적재 batch job
     *
     * @param monthlyDealDataLoadPreprocessFlow         월별 부동산 거래 데이터 적재 batch 전처리 step
     * @param apartmentSellDataLoadFlow                 아파트 매매 실거래가 데이터 적재 flow
     * @param apartmentPresaleRightSellDataLoadFlow     아파트 분양권 매매 실거래가 데이터 적재 flow
     * @param multiUnitDetachedSellDataLoadFlow         단독/다가구주택 매매 실거래가 데이터 적재 flow
     * @param multiHouseholdHouseSellDataLoadFlow       연립/다세대주택 매매 실거래가 데이터 적재 flow
     * @param officetelSellDataLoadFlow                 오피스텔 매매 실거래가 데이터 적재 flow
     * @param apartmentLeaseDataLoadFlow                아파트 전월세 실거래가 데이터 적재 flow
     * @param multiUnitDetachedLeaseDataLoadFlow        단독/다가구주택 전월세 실거래가 데이터 적재 flow
     * @param multiHouseholdHouseLeaseDataLoadFlow      연립/다세대주택 전월세 실거래가 데이터 적재 flow
     * @param officetelLeaseDataLoadFlow                오피스텔 전월세 실거래가 데이터 적재 flow
     * @return 월별 부동산 거래 데이터 적재 batch job
     */
    @Bean
    public Job monthlyDealDataLoad(
            Flow monthlyDealDataLoadPreprocessFlow,
            Flow apartmentSellDataLoadFlow,
            Flow apartmentPresaleRightSellDataLoadFlow,
            Flow multiUnitDetachedSellDataLoadFlow,
            Flow multiHouseholdHouseSellDataLoadFlow,
            Flow officetelSellDataLoadFlow,
            Flow apartmentLeaseDataLoadFlow,
            Flow multiUnitDetachedLeaseDataLoadFlow,
            Flow multiHouseholdHouseLeaseDataLoadFlow,
            Flow officetelLeaseDataLoadFlow
    ) {

        return new JobBuilder("monthlyDealDataLoad", jobRepository)
                .start(monthlyDealDataLoadPreprocessFlow)       // job 전처리
                .next(apartmentSellDataLoadFlow)                // 아파트 매매
                .next(apartmentPresaleRightSellDataLoadFlow)    // 아파트 분양권 매매
                .next(multiUnitDetachedSellDataLoadFlow)        // 단독/다가구주택 매매
                .next(multiHouseholdHouseSellDataLoadFlow)      // 연립/다세대주택 매매
                .next(officetelSellDataLoadFlow)                // 오피스텔 매매
                .next(apartmentLeaseDataLoadFlow)               // 아파트 임대차
                .next(multiUnitDetachedLeaseDataLoadFlow)       // 단독/다가구주택 전월세 실거래가 데이터 적재 flow
                .next(multiHouseholdHouseLeaseDataLoadFlow)     // 연립/다세대주택 전월세 실거래가 데이터 적재 flow
                .next(officetelLeaseDataLoadFlow)               // 오피스텔 전월세 실거래가 데이터 적재 flow
                .end()
                .build();
    }

}
