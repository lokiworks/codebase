package com.loki.dddplus.utils;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {

    static final AtomicInteger poolCount = new AtomicInteger(0);
    static final AtomicInteger threadCount = new AtomicInteger(1);


    private final ThreadGroup group;
    private final String namePrefix;
    private final boolean daemon;

    public  NamedThreadFactory(String prefix, boolean daemon){
        SecurityManager s = System.getSecurityManager();
        group = Objects.nonNull(s) ? s.getThreadGroup(): Thread.currentThread().getThreadGroup();
        namePrefix = prefix + "-" + poolCount.getAndIncrement() + "-T-";
        this.daemon = daemon;
    }



    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix+ threadCount.getAndIncrement(), 0);
        t.setDaemon(daemon);
        return t;
    }
}
