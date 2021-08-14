package com.loki.dddplus.utils;

import java.util.Collection;
import java.util.Objects;

public class CollectionUtils {
    public static <E> boolean isEmpty(Collection<E> collections){
        return  (Objects.isNull(collections) || collections.isEmpty());
    }
}
