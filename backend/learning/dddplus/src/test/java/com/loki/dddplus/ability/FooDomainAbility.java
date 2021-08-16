package com.loki.dddplus.ability;

import com.loki.dddplus.BaseDomainAbility;
import com.loki.dddplus.ext.IFooExt;
import com.loki.dddplus.model.FooModel;

import javax.validation.constraints.NotNull;

public class FooDomainAbility extends BaseDomainAbility<FooModel, IFooExt> {

    public String submit(FooModel model){
        if (model.isWillSleepLong() || model.isWillThrowRuntimeException()){
            firstExtension(model, 500).execute(model);
            return "";
        }
        String s1 = "submit received: " + String.valueOf(this.getExtension(model, null).execute(model));
        return s1 + ", firstExt got: " + String.valueOf(firstExtension(model).execute(model));
    }

    @Override
    public IFooExt defaultExtension(@NotNull FooModel model) {
        return null;
    }
}
