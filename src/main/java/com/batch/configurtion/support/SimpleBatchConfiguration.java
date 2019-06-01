package com.batch.configurtion.support;


import com.batch.configurtion.exception.ItemSetException;
import com.batch.entity.Person;
import com.batch.listener.JobDemoListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import javax.annotation.Resource;

@Configuration
public class SimpleBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SimpleBatchConfiguration.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobDemoListener jobListener;

    private ItemReader readerExecutor;
    private ItemProcessor processExecutor;
    private ItemWriter writerExecutor;
    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    public Job dataHanlerJob() throws Exception{
        boolean b = validateExecutor(readerExecutor, processExecutor, writerExecutor);
        if(!b){
            throw new ItemSetException("ItemReader,ItemProcessor,ItemWriter",new NullPointerException());
        }
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
    public Step handleDataStep(){
        return stepBuilderFactory.get("getdata2").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(readerExecutor)
                .processor(processExecutor)
                .writer(writerExecutor)
                .taskExecutor(taskExecutor())
                .build();
    }

    public void setItemExecutor(ItemReader readerExecutor,ItemProcessor processExecutor,ItemWriter writerExecutor) {
        this.readerExecutor = readerExecutor;
        this.processExecutor = processExecutor;
        this.writerExecutor = writerExecutor;
    }

    private boolean validateExecutor(ItemReader readerExecutor,ItemProcessor processExecutor,ItemWriter writerExecutor) {
        if(null == readerExecutor || null == processExecutor || null == writerExecutor) {
            log.info("ItemReader,ItemProcessor,writerExecutor有至少一个为空");
            return false;
        }
        return true;
    }
    public TaskExecutor taskExecutor(){
        log.info("taskExecutor:===========================================================");
        return new SimpleAsyncTaskExecutor("spring_batch");
    }
}
