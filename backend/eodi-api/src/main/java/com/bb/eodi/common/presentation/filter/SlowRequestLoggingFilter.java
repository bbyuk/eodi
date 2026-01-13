package com.bb.eodi.common.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 느린 API Request 로깅 필터
 */
@Slf4j
@RequiredArgsConstructor
public class SlowRequestLoggingFilter extends OncePerRequestFilter {

    private final Long slowRequestThresholdMs;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        long start = System.nanoTime();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long end = System.nanoTime();
            long elapsed = (end - start) / 1_000_000;

            if (elapsed > slowRequestThresholdMs) {
                log.warn("[SLOW] {} ms", elapsed);
            }
        }
    }
}
