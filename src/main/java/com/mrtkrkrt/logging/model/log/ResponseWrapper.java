package com.mrtkrkrt.logging.model.log;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream outputStreamWrapper;
    private PrintWriter writerWrapper;
    private ServletOutputStreamWrapper copier;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public Map<String, String> getAllHeaders() {
        Map<String, String> headers = new HashMap<>();
        getHeaderNames().forEach(headerName -> headers.put(headerName, getHeader(headerName)));
        return headers;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writerWrapper != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStreamWrapper == null) {
            outputStreamWrapper = getResponse().getOutputStream();
            copier = new ServletOutputStreamWrapper(outputStreamWrapper);
        }
        return copier;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStreamWrapper != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writerWrapper == null) {
            copier = new ServletOutputStreamWrapper(getResponse().getOutputStream());
            writerWrapper = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
        }
        return writerWrapper;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writerWrapper != null) {
            writerWrapper.flush();
        }
        else if (outputStreamWrapper != null) {
            copier.flush();
        }
    }

    public byte[] getCopyBody() {
        if (copier != null) {
            return copier.getCopy();
        }
        return new byte[0];
    }
}
