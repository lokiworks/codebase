package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public interface IPrepareAware {
    void prepare(@NotNull Object bean);
}
