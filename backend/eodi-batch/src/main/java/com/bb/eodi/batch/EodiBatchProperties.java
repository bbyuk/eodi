package com.bb.eodi.batch;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eodi.batch")
public record EodiBatchProperties(
        String jobNamePrefix,
        String jobParameterPrefix
) {}
