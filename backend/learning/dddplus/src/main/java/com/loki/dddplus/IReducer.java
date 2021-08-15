package com.loki.dddplus;

import java.util.List;

public interface IReducer<R> {
    R reduce(List<R> accumulatedResults);
    boolean shouldStop(List<R> accumulatedResults);
}
