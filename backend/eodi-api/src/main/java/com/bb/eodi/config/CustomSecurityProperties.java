package com.bb.eodi.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Getter
@ConfigurationProperties(prefix = "security")
@RequiredArgsConstructor
public class CustomSecurityProperties {
    private final Set<String> adminIp;
}
