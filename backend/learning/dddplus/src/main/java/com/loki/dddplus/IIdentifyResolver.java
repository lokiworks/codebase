package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public interface IIdentifyResolver<Model extends IDomainModel> extends IPlugable{
    boolean match(@NotNull Model model);
}
