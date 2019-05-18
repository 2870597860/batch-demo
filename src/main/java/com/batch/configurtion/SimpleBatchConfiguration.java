package com.batch.configurtion;


import com.batch.entity.Person;
import com.batch.listener.JobListener;
import com.batch.proxy.PersonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Configuration
@EnableBatchProcessing
public class SimpleBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SimpleBatchConfiguration.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private SimpleAsyncTaskExecutor simpleAsyncTaskExecutor;
    @Resource
    private JobListener jobListener;

    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    @Bean
    public Job dataHanlerJob(Step step){
        return jobBuilderFactory.get("dataHanlerJob").incrementer(new RunIdIncrementer())
                .start(step)
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
    public Step handleDataStep(ItemReader<? extends Person> reader, ItemWriter<Person> writer,ItemProcessor<Person,Person> process ){
        return stepBuilderFactory.get("getdata").<Person,Person>chunk(50)
                .faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(reader)
                .processor(process)
                .writer(writer)
                .taskExecutor(simpleAsyncTaskExecutor)
                .build();
    }
    @Bean
    public Flow splitFlow() {
        return new FlowBuilder<SimpleFlow>("splitFlow")
                .split(simpleAsyncTaskExecutor)
                .add(flow1(), flow2())
                .build();
    }

    @Bean
    public Flow flow1() {
        return new FlowBuilder<SimpleFlow>("flow1")
                .start(step1())
                .next(step2())
                .build();
    }

    @Bean
    public Flow flow2() {
        return new FlowBuilder<SimpleFlow>("flow2")
                .start(step3())
                .build();
    }
    @Bean
    public ItemReader<? extends Person> getDataReader(){
        FlatFileItemReader reader=new FlatFileItemReader();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("D:\\install\\developmentInstall\\workspace\\batch-demo\\src\\main\\resources\\Person.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reader.setResource( new InputStreamResource(fis));
        reader.setLineMapper((line,number)->{
            String[] str = line.split(",");
            Person p = new Person();
            p.setId(Integer.parseInt(str[0]));
            p.setName(str[1]);
            p.setAge(Integer.parseInt(str[2]));
            return p;
        });
        return reader;
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
        return adapter;

    }
}
