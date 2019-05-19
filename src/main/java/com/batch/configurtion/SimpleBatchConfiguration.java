package com.batch.configurtion;


import com.batch.entity.Person;
import com.batch.listener.JobListener;
import com.batch.proxy.PersonProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

@Configuration
@EnableBatchProcessing
public class SimpleBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SimpleBatchConfiguration.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobListener jobListener;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
    public Job dataHanlerJob3(){
        return jobBuilderFactory.get("dataHanlerJob3").incrementer(new RunIdIncrementer())
                .start(handleDataStep3())
                .listener(jobListener)
                .build();
    }
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Job dataHanlerJob4(Step step){
        return jobBuilderFactory.get("dataHanlerJob4").incrementer(new RunIdIncrementer())
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
    public Step handleDataStep3(){
        return stepBuilderFactory.get("getdata3").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(getDataReader())
                .processor(getDataProcess())
                .writer(getDataWriter())
                .taskExecutor(threadPoolTaskExecutor)
                .build();
    }
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Step handleDataStep4(ItemReader<Person> reader){
        return stepBuilderFactory.get("getdata4").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(reader)
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
    @StepScope
    @Bean("reader")
    public FlatFileItemReader<Person> reader(@Value("#{jobParameters['input']}")String filePath) {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new PlayerFieldSetMapper());
        reader.setLineMapper(lineMapper);
        reader.open(new ExecutionContext());
        try {
            Person person = reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*reader.setLineMapper((s, i) -> {
            String[] ss = s.split(",");
            Person person = new Person();
            person.setName(ss[0]);

            return person;
        });*/
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
        return adapter;

    }

    public TaskExecutor taskExecutor(){
        log.info("taskExecutor:===========================================================");
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}
