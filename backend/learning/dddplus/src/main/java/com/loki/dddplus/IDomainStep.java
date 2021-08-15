package com.loki.dddplus;

import javax.validation.constraints.NotNull;

/**
 * 领域活动（业务活动）的步骤，一种可以被编排的领域服务
 */
public interface IDomainStep<Model extends IDomainModel, Ex extends RuntimeException> extends IDomainService {
    void execute(@NotNull Model model) throws Ex;
    @NotNull
    String activityCode();
    @NotNull
    String stepCode();


}
