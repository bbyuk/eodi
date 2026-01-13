package com.bb.eodi.config;

import com.bb.eodi.common.presentation.filter.SlowRequestLoggingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 필터 설정
 */
@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SlowRequestLoggingFilter> slowRequestLoggingFilterRegistrationBean() {
        FilterRegistrationBean<SlowRequestLoggingFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new SlowRequestLoggingFilter(500L));
        bean.setOrder(Ordered.LOWEST_PRECEDENCE);
        bean.addUrlPatterns("/*");
        bean.setName("slowRequestLoggingFilter");

        return bean;
    }
}
