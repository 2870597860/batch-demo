/*
package com.batch.real.configurtion.support;

import com.batch.real.configurtion.demo.FileItemWriter1;
import com.batch.real.configurtion.demo.FileItemWriter2;
import com.batch.real.entity.Person;
import com.batch.real.listener.JobDemoListener;
import com.batch.real.proxy.PersonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FirstJobConfiguration {
    private static final Logger log = LoggerFactory.getLogger(FirstJobConfiguration.class);

    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobDemoListener jobListener;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Job dataHanlerJob4(Step step){
        return jobBuilderFactory.get("dataHanlerJob4").incrementer(new RunIdIncrementer())
                .start(step)
                .listener(jobListener)
                .build();
    }
    @Bean
    @StepScope
    public Step handleDataStep4(@Value("#{jobParameters['input']}")String filePath){
        return stepBuilderFactory.get("getdata4").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(getDataReader())
                .processor(getDataProcess())
                .writer(getDataWriter())
                .taskExecutor(taskExecutor())
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
       */
/* lineMapper.setLineTokenizer(new DelimitedLineTokenizer(":"));
        lineMapper.setFieldSetMapper(new PersonFieldSet(new Person()));*//*

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
    public TaskExecutor taskExecutor(){
        log.info("taskExecutor:===========================================================");
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}
*/
