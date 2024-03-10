package com.mrtkrkrt.logging.model.log;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper extends HttpServletResponseWrapper {
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public Map<String, String> getAllHeaders() {
        Map<String, String> headers = new HashMap<>();
        getHeaderNames().forEach(headerName -> headers.put(headerName, getHeader(headerName)));
        return headers;
    }
}
