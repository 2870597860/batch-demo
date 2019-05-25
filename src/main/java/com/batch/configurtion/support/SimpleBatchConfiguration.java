package com.batch.configurtion.support;


import com.batch.configurtion.common.PersonFieldSetMapper;
import com.batch.entity.Person;
import com.batch.proxy.PersonProcessor;
import com.batch.writer.FileItemWriter1;
import com.batch.writer.FileItemWriter2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import com.batch.configurtion.listener.JobListener;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public abstract class SimpleBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SimpleBatchConfiguration.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobListener jobListener;

    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */

    public Job dataHanlerJob(){
        return jobBuilderFactory.get("dataHanlerJob").incrementer(new RunIdIncrementer())
                .start(handleDataStep())
                .listener(jobListener)
                .build();
    }
    public Job dataHanlerJob2(){
        return jobBuilderFactory.get("dataHanlerJob2").incrementer(new RunIdIncrementer())
                .start(handleDataStep2())
                .listener(jobListener)
                .build();
    }

    /**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     */
    public Step handleDataStep(){
        return stepBuilderFactory.get("getdata").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(getDataReader())
                .processor(getDataProcess())
                .writer(getDataWriter())
                .build();
    }
    public Step handleDataStep2(){
        return stepBuilderFactory.get("getdata2").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(getDataReader())
                .processor(getDataProcess())
                .writer(getDataWriter())
                .taskExecutor(taskExecutor())
                .build();
    }

    @StepScope
    @Bean("reader")
    public FlatFileItemReader<Person> reader(@Value("#{jobParameters['input']}")String filePath) {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer(":"));
        lineMapper.setFieldSetMapper(new PersonFieldSetMapper());
        reader.setLineMapper(lineMapper);
        return reader;
    }

    public ItemProcessor<Person,Person> getDataProcess(){
        return person-> {
            log.info("process data:"+person);
            return person;
        };
    }

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

    public TaskExecutor taskExecutor(){
        log.info("taskExecutor:===========================================================");
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}