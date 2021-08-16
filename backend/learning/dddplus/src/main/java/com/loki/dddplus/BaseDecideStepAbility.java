package com.loki.dddplus;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

public abstract class BaseDecideStepAbility<Model extends  IDomainModel> extends BaseDomainAbility<Model, IDecideStepsExt> {

    private static final IDecideStepsExt defaultExt = new EmptyExt();

    public List<String> decideSteps(@NotNull Model model, String activityCode){
        return firstExtension(model).decideSteps(model, activityCode);
    }

    public IDecideStepsExt defaultExtension(@NotNull Model model){
        return defaultExt;
    }

    private static class EmptyExt implements IDecideStepsExt{
        private static List<String> emptySteps = Collections.emptyList();

        @Override
        public @NotNull List<String> decideSteps(@NotNull IDomainModel model, @NotNull String activityCode) {
            return emptySteps;
        }
    }
}
