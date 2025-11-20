package com.bb.eodi.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eodi.batch")
public record EodiBatchProperties(
        String jobNamePrefix,
        String jobParameterPrefix,
        int batchSize,
        String forceNewInstanceParameter
) {}
