package com.loki.dddplus;

import com.loki.dddplus.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public abstract class StepExecTemplate<Step extends IDomainStep, Model extends IDomainModel> {
    private static final Set<String> emptyAsyncSteps = Collections.emptySet();
    private static final List<String> emptyReviseSteps = Collections.emptyList();

    protected void beforeStep(Step step, Model model){}
    protected void afterStep(Step step, Model model){}

    public final void execute(String activityCode, List<String> stepCodes, Model model){
        execute(activityCode, stepCodes, model, null, emptyAsyncSteps);
    }
    public final void execute(String activityCode, List<String> stepCodes, Model model, ScheduledExecutorService taskExecutor, Set<String> asyncStepCodes) throws  RuntimeException{
        if (CollectionUtils.isEmpty(stepCodes)){
            log.warn("Empty steps of activity:{} on {}", activityCode, model);
            return;
        }




    }

    private List<String> executeSteps(String activityCode, List<String> stepCodes, Model model,ScheduledExecutorService taskExecutor, Set<String> asyncStepCodes) throws RuntimeException{
        if (CollectionUtils.isEmpty(stepCodes)){
            asyncStepCodes = emptyAsyncSteps;
        }
        List<Step> steps = DDD.findSteps(activityCode, stepCodes);
        for (Step step: steps){
            String stepCode = step.stepCode();
            beforeStep(step, model);
            if (asyncStepCodes.contains(stepCode)){
                // TODO:
            }else {
                step.execute(model);
            }
            afterStep(step, model);

            if (step instanceof IRevocableDomainStep && !asyncStepCodes.contains(stepCode)){
                // TODO:
            }
        }
        return null;
    }

    private List<String> executeSteps(String activityCode, List<String> stepCodes, Stack<IRevocableDomainStep> executedSteps, Model model, ScheduledExecutorService taskExecutor, Set<String> asyncStepCodes) throws RuntimeException{
        if (Objects.isNull(asyncStepCodes) || Objects.isNull(taskExecutor)){
            asyncStepCodes = emptyAsyncSteps;
        }

        List<Step> steps = DDD.findSteps(activityCode, stepCodes);
        String stepCode = "";
        try {

            for (Step step: steps){
                stepCode = step.stepCode();
                beforeStep(step, model);
                if (asyncStepCodes.contains(stepCode)){
                    // TODO:
                }else {
                    step.execute(model);
                }
                afterStep(step, model);

                if (step instanceof IRevocableDomainStep && !asyncStepCodes.contains(stepCode)){
                    // TODO:
                }
            }
        }catch (Exception e){
            if (e instanceof IReviseStepsException){
                return ((IReviseStepsException)e).subsequentSteps();
            }
            log.error("Step:{}.{} false for {}", activityCode, stepCode, e);
            if (e instanceof RejectedExecutionException){
                throw (RejectedExecutionException)e;
            }
            if (!CollectionUtils.isEmpty(executedSteps) && e instanceof RuntimeException){
                // TODO:
            }
            throw  e;
        }
        return emptyReviseSteps;
    }
}
