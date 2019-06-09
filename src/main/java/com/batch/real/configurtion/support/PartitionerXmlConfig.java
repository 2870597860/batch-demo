/*
package com.batch.real.configurtion.support;

import com.batch.real.configurtion.RangePartitionerXml;
import com.batch.real.listener.JobDemoListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;

@Configuration("partitionerXmlConfig")
@EnableBatchProcessing()
public class PartitionerXmlConfig {
    private static final Logger log = LoggerFactory.getLogger(PartitionerXmlConfig.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobDemoListener jobListener;

    @Bean
    public Job partitionerXmlJob(){
       return  jobBuilderFactory.get("partitionerXmlJob").incrementer(new RunIdIncrementer())
                .start(masterStep())
                .listener(jobListener)
                .build();
    }
    @Bean
    public Step masterStep() {
        return stepBuilderFactory.get("masterStep").partitioner("partitionerSlaveJob", rangePartitioner())
                .partitionHandler(masterSlaveHandler()).build();
    }

    public RangePartitionerXml rangePartitioner(String path,String splitRoot) {
        return new RangePartitionerXml(path,splitRoot);
    }
   public PartitionHandler masterSlaveHandler(){
       TaskExecutorPartitionHandler handler = new TaskExecutorPartitionHandler();
       handler.setGridSize(10);
       handler.setTaskExecutor(taskExecutor());
       handler.setStep(slave());
       try {
           handler.afterPropertiesSet();
       } catch (Exception e) {
           e.printStackTrace();
       }
       return handler;
    }
    public TaskExecutor taskExecutor(){
        log.info("taskExecutor:===========================================================");
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}
*/
