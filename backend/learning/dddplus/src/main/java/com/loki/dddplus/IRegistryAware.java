package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public interface IRegistryAware {
    void registerBean(@NotNull Object bean);
}
