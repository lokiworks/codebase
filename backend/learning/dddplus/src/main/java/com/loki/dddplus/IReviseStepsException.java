package com.loki.dddplus;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 修订后续步骤的异常。
 */
public interface IReviseStepsException {
    @NotNull
    List<String> subsequentSteps();
}
