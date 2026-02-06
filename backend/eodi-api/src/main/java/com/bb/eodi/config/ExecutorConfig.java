package com.bb.eodi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * executor 관련 설정 클래스
 */
@Configuration
public class ExecutorConfig {

    /**
     * 스트리밍 처리에 사용되는 Executor bean
     * @return streamingExecutor bean
     */
    @Bean
    public ThreadPoolTaskExecutor streamingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("streaming-");
        executor.initialize();

        return executor;
    }
}