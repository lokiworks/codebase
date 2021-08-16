package com.loki.dddplus.utils;

import java.util.Objects;

public class StringUtils {
    public static boolean isEmpty(String s){
        return  (Objects.isNull(s) || s.isEmpty());
    }
}
