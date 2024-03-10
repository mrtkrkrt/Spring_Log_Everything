package com.mrtkrkrt.logging.model.log;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper {
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public Map<String, String> getAllHeaders() {
        Map<String, String> headers = new HashMap<>();
        Collections.list(getHeaderNames()).forEach(headerName -> headers.put(headerName, getHeader(headerName)));
        return headers;
    }
}
