package com.batch.configurtion.support;

import com.batch.entity.Person;
import com.batch.configurtion.listener.JobListener;
import com.batch.proxy.PersonProcessor;
import com.batch.writer.FileItemWriter1;
import com.batch.writer.FileItemWriter2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecondJobConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SecondJobConfiguration.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobListener jobListener;
    @Bean
    public Job dataHanlerJob(){
        return jobBuilderFactory.get("dataHanlerJob").incrementer(new RunIdIncrementer())
                .start(handleDataStep())
                .listener(jobListener)
                .build();
    }
    /**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     */
    @Bean
    public Step handleDataStep(){
        return stepBuilderFactory.get("getdata").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(getDataReader())
                .processor(getDataProcess())
                .writer(getDataWriter())
                .build();
    }

    @Bean
    public ItemProcessor<Person,Person> getDataProcess(){
        return person-> {
            log.info("process data:"+person);
            return person;
        };
    }
    @Bean
    public ItemWriter<Person> getDataWriter(){
        ItemWriterAdapter<Person> adapter = new ItemWriterAdapter();
        adapter.setTargetMethod("print");
        adapter.setTargetObject(new PersonProcessor());
        CompositeItemWriter writer = new CompositeItemWriter();
        new FileItemWriter1();
        List<ItemWriter> writers = new ArrayList<>(2);
        writers.add(new FileItemWriter1());
        writers.add(new FileItemWriter2());
        return adapter;

    }
}
