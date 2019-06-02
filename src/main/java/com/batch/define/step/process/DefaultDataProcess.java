package com.batch.define.step.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.support.CompositeItemProcessor;

import java.util.ArrayList;
import java.util.List;

public abstract  class DefaultDataProcess<I,O> {
    private static final Logger log = LoggerFactory.getLogger(DefaultDataProcess.class);
    public ItemProcessor<I,O> dataProcessExector(){
        return t -> {
            log.info("process data:"+t);
            O m = doExecutor(t);
            return m;
        };
    }
    protected abstract O doExecutor(I i);
    public CompositeItemProcessor<I,O> personDataProcessor(ItemProcessor<I,O> ... itemProcessors){
        CompositeItemProcessor<I,O> processor=new CompositeItemProcessor<>();
        List<ItemProcessor<I,O>> listProcessor=new ArrayList<>();
        for (ItemProcessor itemProcessor: itemProcessors) {
            listProcessor.add(itemProcessor);
        }
        processor.setDelegates(listProcessor);
        return processor;

    }
}
