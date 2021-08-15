package com.loki.dddplus;

import com.loki.dddplus.utils.CollectionUtils;
import com.loki.dddplus.utils.StringUtils;

import java.util.Set;
import java.util.TreeSet;

public class Notification {
    private Set<String> reasons;

    public static Notification create(){return new Notification();}
    private Notification(){this.reasons = new TreeSet<>();
    }

    public boolean addReason(String reason){
        if (StringUtils.isEmpty(reason)){
            return false;
        }
        if (reasons.contains(reason)){
            return false;
        }

        return reasons.add(reason);
    }

    public String firstReason(){
        if (CollectionUtils.isEmpty(reasons)){
            return null;
        }
        return reasons.stream().findFirst().get();
    }
}
