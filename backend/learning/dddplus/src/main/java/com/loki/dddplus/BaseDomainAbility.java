package com.loki.dddplus;

import javax.validation.constraints.NotNull;

public abstract class BaseDomainAbility<Model extends IDomainModel, Ext extends IDomainExtension> implements IDomainService {
    public abstract Ext defaultExtension(@NotNull Model model);

    protected <R> Ext getExtension(@NotNull Model model, IReducer<R> reducer){
        Class<? extends IDomainExtension> extClazz = InternalIndexer.getDomainAbilityExtDeclaration(this.getClass());
        return findExtension((Class<Ext>) extClazz, model, reducer, defaultExtension(model), 0);
    }

    protected Ext firstExtension(@NotNull Model model){
        return firstExtension(model, 0);
    }

    protected Ext firstExtension(@NotNull Model model, int timeoutInMs){
        Class<? extends IDomainExtension> extClazz = InternalIndexer.getDomainAbilityExtDeclaration(this.getClass());
        return findExtension((Class<Ext> )extClazz, model, null, defaultExtension(model), timeoutInMs);
    }


    private <Ext extends IDomainExtension, R> Ext findExtension(@NotNull Class<Ext> extClazz, @NotNull Model model, IReducer<R> reducer, Ext defaultExt, int timeoutInMs){
        ExtensionInvocationHandler<Ext, R> proxy = new ExtensionInvocationHandler<>(extClazz, model, reducer, defaultExt, timeoutInMs);
        return proxy.createProxy();

    }
}
