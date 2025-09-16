package com.bb.eodi.batch.job.deal.load;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 월별 거래 데이터 적재 배치 properties
 */
@ConfigurationProperties(prefix = "eodi.batch.job.monthly-deal-data-load")
public record MonthlyDealDataLoadJobProperties(
        String aptSaleTempFileName
) {}
