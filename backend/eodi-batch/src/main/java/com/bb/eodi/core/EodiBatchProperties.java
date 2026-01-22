package com.bb.eodi.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "eodi.batch")
public record EodiBatchProperties(
        String jobNamePrefix,
        String jobParameterPrefix,
        int batchSize,
        String forceNewInstanceParameter,
        Set<String> dailyJobs
) {}
