package com.bb.eodi.batch.legaldong.load.tasklet;

import com.bb.eodi.batch.legaldong.load.model.LegalDongApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 법정동코드 API fetch 작업
 */
@Slf4j
@StepScope
@Component
public class LegalDongApiFetchTasklet implements Tasklet {

    private final WebClient legalDongApiClient;
    private final String region;
    private final int pageNum;

    public LegalDongApiFetchTasklet(
            @Qualifier("legalDongApiClient") WebClient legalDongApiClient,
            @Value("#{jobParameters['region']}") String region,
            @Value("#{jobParameters['pageNum']}") String pageNum) {
        this.legalDongApiClient = legalDongApiClient;
        this.region = StringUtils.hasText(region) ? region : "";
        this.pageNum = Integer.parseInt(pageNum);
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        return RepeatStatus.FINISHED;
    }
}
