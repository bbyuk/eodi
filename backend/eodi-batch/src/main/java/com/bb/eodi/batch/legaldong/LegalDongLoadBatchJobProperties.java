package com.bb.eodi.batch.legaldong;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 법정동 적재 배치 job 설정 값
 */
@ConfigurationProperties(prefix = "eodi.batch.job.legal-dong-load")
public record LegalDongLoadBatchJobProperties(
        String tempFileName,
        String tempFileSuffix
) {

}
