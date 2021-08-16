package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public interface ISpecification<T extends IDomainModel> {
    default boolean satisfiedBy(@NotNull T candidate){
        return satisfiedBy(candidate, null);
    }

    boolean satisfiedBy(@NotNull T candidate, Notification notification);
}
