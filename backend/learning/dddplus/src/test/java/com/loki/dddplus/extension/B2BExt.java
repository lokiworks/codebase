package com.loki.dddplus.extension;

import com.loki.dddplus.annotation.Extension;
import com.loki.dddplus.ext.IFooExt;
import com.loki.dddplus.model.FooModel;
@Extension(code = "b2b")
public class B2BExt implements IFooExt {
    @Override
    public Integer execute(FooModel model) {
        if (model.isWillThrowRuntimeException()){
            throw new RuntimeException("runtime ex on purpose");
        }
        if (model.isWillThrowOOM()){
            throw new OutOfMemoryError("OOM on purpose");
        }
        if (model.isWillSleepLong()){
            try {
                Thread.sleep(2<< 10); // 2s
            }catch (InterruptedException e){}
        }
        throw new RuntimeException("b2c error on purpose");
    }
}
