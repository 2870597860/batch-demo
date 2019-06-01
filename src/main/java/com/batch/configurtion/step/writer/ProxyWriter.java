package com.batch.configurtion.step.writer;

public interface ProxyWriter<T>{
    void executor(T t) throws Exception;
}
