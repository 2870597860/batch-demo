/*
package com.batch.real.configurtion.support;

import com.batch.real.configurtion.demo.FileItemWriter1;
import com.batch.real.configurtion.demo.FileItemWriter2;
import com.batch.real.configurtion.demo.PersonFieldSet;
import com.batch.real.entity.Person;
import com.batch.real.listener.JobDemoListener;
import com.batch.real.proxy.PersonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.InputStreamResource;

import javax.annotation.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    private JobDemoListener jobListener;
    @Bean
    public Job dataHanlerJob(){
        return jobBuilderFactory.get("dataHanlerJob").incrementer(new RunIdIncrementer())
                .start(handleDataStep())
                .listener(jobListener)
                .build();
    }
    */
/**
     * 一个简单基础的Step主要分为三个部分
     * ItemReader : 用于读取数据
     * ItemProcessor : 用于处理数据
     * ItemWriter : 用于写数据
     *//*


    public Step handleDataStep(){
        return stepBuilderFactory.get("getdata").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(getDataReader())
                .processor(getDataProcess())
                .writer(getDataWriter())
                .build();
    }
    public ItemReader<? extends Person> getDataReader(){
        FlatFileItemReader reader=new FlatFileItemReader();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("data/Person.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        reader.setResource( new InputStreamResource(fis));
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();
       lineMapper.setLineTokenizer(new DelimitedLineTokenizer(":"));
        lineMapper.setFieldSetMapper(new PersonFieldSet(new Person()));
        reader.setLineMapper(lineMapper);
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
        CompositeItemWriter writer = new CompositeItemWriter();
        List<ItemWriter> writers = new ArrayList<>(2);
        writers.add(new FileItemWriter1());
        writers.add(new FileItemWriter2());
        return adapter;
    }
}
*/
