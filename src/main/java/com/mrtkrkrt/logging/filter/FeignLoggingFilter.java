package com.mrtkrkrt.logging.filter;

public class FeignLoggingFilter extends feign.Logger{
    @Override
    protected void log(String s, String s1, Object... objects) {
        System.out.println("Feign: " + String.format(s, objects));
    }
}
