package com.bb.eodi.common.presentation.interceptor;

import com.bb.eodi.config.CustomSecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 임시 admin API Auth Interceptor
 */
@Component
@RequiredArgsConstructor
public class AdminApiAuthInterceptor implements HandlerInterceptor {

    private final CustomSecurityProperties customSecurityProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ipAddr = request.getHeader("X-Forwarded-For");

        if (ipAddr == null) {
            ipAddr = request.getRemoteAddr();
        }

        if (!customSecurityProperties.getAdminIp().contains(ipAddr)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}
