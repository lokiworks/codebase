package com.loki.dddplus.utils;

public class BootstrapException extends RuntimeException {
    private BootstrapException(String message) {super(message);}
    public static BootstrapException ofMessage(String... messages){
        StringBuilder sb = new StringBuilder(100);
        for (String s: messages){
            sb.append(s);
        }
        return new BootstrapException(sb.toString());
    }
}
