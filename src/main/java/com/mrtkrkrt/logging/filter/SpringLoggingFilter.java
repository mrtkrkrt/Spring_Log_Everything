package com.mrtkrkrt.logging.filter;

import com.mrtkrkrt.logging.model.log.RequestWrapper;
import com.mrtkrkrt.logging.model.log.ResponseWrapper;
import com.mrtkrkrt.logging.service.TraceIdGeneratorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.mrtkrkrt.logging.constants.LoggingConstants.X_TRACE_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringLoggingFilter extends OncePerRequestFilter {

    private final TraceIdGeneratorService traceIdGeneratorService;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        traceIdGeneratorService.generateTraceId(request);
        setResponseHeader(response);
        log.info(requestLogFormatString(request));
        log.info(responseLogFormatString(response));
        filterChain.doFilter(request, response);
    }

    private String requestLogFormatString(HttpServletRequest request) throws IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        return String.format("## %s ## Request Method: %s, Request Uri: %s, Request TraceId: %s, Request Body: %s, Request Headers: %s",
                requestWrapper.getServerName(),
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                requestWrapper.getHeader(X_TRACE_ID),
                IOUtils.toString(requestWrapper.getInputStream(), StandardCharsets.UTF_8),
                requestWrapper.getAllHeaders());
    }

    private String responseLogFormatString(HttpServletResponse response) {
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        return String.format("Response Status: %s, Response Headers: %s",
                responseWrapper.getStatus(),
                responseWrapper.getAllHeaders());
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.addHeader(X_TRACE_ID, MDC.get(X_TRACE_ID));
    }
}
