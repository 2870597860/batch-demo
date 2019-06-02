package com.batch.define.step.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.support.CompositeItemWriter;

import java.util.ArrayList;
import java.util.List;


public abstract class DefaultItemWriter<T> {
    private static final String targetMethodName = "executor";

    /**
     * 适配器模式
     */
    public ItemWriter<T> adapterWriter(ProxyWriter pw){
        ItemWriterAdapter<T> adapter = new ItemWriterAdapter();
        adapter.setTargetMethod(targetMethodName);
        adapter.setTargetObject(pw);
        return adapter;
    }
    public ItemWriter<T> compositeWriter(ItemWriter ... itemWriters){
        CompositeItemWriter writer = new CompositeItemWriter();
        List<ItemWriter> writers = new ArrayList<>(itemWriters.length);
        for (ItemWriter pw: itemWriters) {
            writers.add(pw);
        }
        return writer;
    }
}
