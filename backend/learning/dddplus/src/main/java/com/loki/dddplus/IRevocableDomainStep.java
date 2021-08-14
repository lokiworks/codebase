package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public interface IRevocableDomainStep<Model extends IDomainModel, Ex extends RuntimeException> extends IDomainStep<Model, Ex> {
    void rollback(@NotNull Model model, @NotNull Ex cause);
}
