package com.batch.real.security;

import com.batch.define.step.writer.DefaultItemWriter;
import com.batch.real.configurtion.demo.FileItemWriter1;
import com.batch.real.configurtion.demo.FileItemWriter2;
import com.batch.real.entity.Person;
import com.batch.real.entity.Security;
import com.batch.real.proxy.PersonProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/6/2.
 */
@Component
public class SecurityWriter extends DefaultItemWriter<Security> {

    public ItemWriter<Security> getDataWriter(){
        ItemWriterAdapter<Security> adapter = new ItemWriterAdapter();
        adapter.setTargetMethod("print");
        adapter.setTargetObject(new SecurityProcessor());
        /*CompositeItemWriter writer = new CompositeItemWriter();
        List<ItemWriter> writers = new ArrayList<>(2);
        writers.add(new FileItemWriter1());
        writers.add(new FileItemWriter2());*/
        return adapter;
    }
}
