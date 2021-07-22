package io.yichwen.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * this filter is to log request based on the configured log request paths
 */
@Component
@Slf4j
@Order(0)
public class LogRequestFilter extends OncePerRequestFilter {

    @Value("${log.request:#{null}}")
    private Set<String> paths;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        Map<String, Object> logMap = null;
        long startTime = System.currentTimeMillis();

        if (paths != null && paths.size() > 0) {
            String requestURI = request.getRequestURI();
            for (String path: paths) {
                if (requestURI.startsWith(path)) {
                    logMap = new HashMap<>();
                    logMap.put("timestamp", getCurrentTimestamp());
                    logMap.put("url", getRequestURL(request));
                    logMap.put("method", request.getMethod());
                }
            }
        }

        chain.doFilter(request, response);

        if (logMap != null) {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            logMap.put("elapsedTime", elapsedTime);
            logMap.put("status", response.getStatus());
            String logContent = objectMapper.writeValueAsString(logMap);
            log.info(logContent);
        }

    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    private String getRequestURL(HttpServletRequest request) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getRequestURI());
        String queryString = request.getQueryString();
        if (queryString != null) {
            builder.query(queryString);
        }
        return builder.build().toUriString();
    }

}
