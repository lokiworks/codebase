package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public interface IExtPolicy<Model extends IDomainModel> {
    @NotNull
    String extensionCode(@NotNull Model model);
}
