package com.batch.define.step.writer;

public interface ProxyWriter<T>{
    void executor(T t) throws Exception;
}
