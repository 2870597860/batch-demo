package com.batch.configurtion.support;

import com.batch.entity.Person;
import com.batch.listener.JobDemoListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;

@Configuration
public class FirstJobConfiguration {
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
                .reader(reader)
                .processor(getDataProcess())
                .writer(getDataWriter())
                .taskExecutor(taskExecutor())
                .build();
    }
    public TaskExecutor taskExecutor(){
        log.info("taskExecutor:===========================================================");
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}
