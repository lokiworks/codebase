package com.loki.dddplus.ext;

import com.loki.dddplus.IDomainExtension;
import com.loki.dddplus.model.FooModel;

public interface IFooExt extends IDomainExtension {
    Integer execute(FooModel model);
}
