package com.mrtkrkrt.logging.filter;

import com.mrtkrkrt.logging.model.log.RequestWrapper;
import com.mrtkrkrt.logging.model.log.ResponseWrapper;
import com.mrtkrkrt.logging.service.TraceIdGeneratorService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

import static com.mrtkrkrt.logging.constants.LoggingConstants.X_TRACE_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class SpringLoggingFilter extends OncePerRequestFilter {

    private final TraceIdGeneratorService traceIdGeneratorService;

    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        traceIdGeneratorService.generateTraceId(request);
        setResponseHeader(response);
        RequestWrapper requestWrapper = setRequestHeader(request);
        log.info(requestLogFormatString(request));
        log.info(responseLogFormatString(response));
        filterChain.doFilter(requestWrapper, response);
    }

    private String requestLogFormatString(HttpServletRequest request) throws IOException {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        return String.format("## %s ## Request Method: %s, Request Uri: %s, Request TraceId: %s, Request Headers: %s, Request Body: %s",
                requestWrapper.getServerName(),
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                requestWrapper.getAttribute(X_TRACE_ID),
                requestWrapper.getAllHeaders(),
                MDC.get("REQUEST_BODY"));
    }

    private String responseLogFormatString(HttpServletResponse response) throws Exception {
        ResponseWrapper responseWrapper = new ResponseWrapper(response);
        return String.format("Response Status: %s, Response Headers: %s, Response TraceId: %s, Response Data: %s",
                responseWrapper.getStatus(),
                responseWrapper.getAllHeaders(),
                responseWrapper.getHeader(X_TRACE_ID),
                extractResponseData(response));
    }

    public String extractResponseData(HttpServletResponse response) throws Exception {
        HttpServletResponse responseToCache = new ContentCachingResponseWrapper((HttpServletResponse) response);
        return getResponseData(responseToCache);
    }

    private void setResponseHeader(HttpServletResponse response) {
        response.addHeader(X_TRACE_ID, MDC.get(X_TRACE_ID));
    }

    private RequestWrapper setRequestHeader(HttpServletRequest request) {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        requestWrapper.getAllHeaders().put(X_TRACE_ID, MDC.get(X_TRACE_ID));
        return requestWrapper;
    }

    private static String getResponseData(HttpServletResponse response) throws Exception {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response,
                ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
                String payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                log.debug("Response Payload :" + payload);
                return payload;
            }
        }
        return "";
    }
}
