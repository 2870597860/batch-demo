package com.batch.real.configurtion.support;


import com.batch.real.configurtion.exception.ItemSetException;
import com.batch.real.entity.Person;
import com.batch.real.entity.Security;
import com.batch.real.listener.JobDemoListener;
import com.batch.real.security.SecurityProcessor;
import com.batch.real.security.SecurityReader;
import com.batch.real.security.SecurityWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import javax.annotation.Resource;

@Configuration("simpleBatchConfiguration")
@EnableBatchProcessing(modular = true)
public class SimpleBatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(SimpleBatchConfiguration.class);
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private JobDemoListener jobListener;
    @Resource
    private SecurityReader securityReader;
    @Resource
    private SecurityProcessor securityProcessor;
    @Resource
    private SecurityWriter securityWriter;
    /**
     * 一个简单基础的Job通常由一个或者多个Step组成
     */
    @Bean(name = "securityJob")
    public Job dataHanlerJob(Step securityStep) throws Exception{
       /* FlatFileItemReader flatFileReader = securityReader.getFlatFileReader("data/Person.txt", ":");
        ItemWriter<Security> dataWriter = securityWriter.getDataWriter();
        ItemProcessor<Security, Security> securitySecurityItemProcessor = securityProcessor.dataProcessExector();
       */ return jobBuilderFactory.get("dataHanlerJob").incrementer(new RunIdIncrementer())
                .start(securityStep)
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
    @StepScope
    public Step handleDataStep(@Value("#{jobParameters['input']}")String filePath,@Value("#{jobParameters['delimiter']}")String delimiter) throws ItemSetException {
        FlatFileItemReader flatFileReader = securityReader.getFlatFileReader(filePath, delimiter);
        ItemWriter<Security> dataWriter = securityWriter.getDataWriter();
        ItemProcessor<Security, Security> securitySecurityItemProcessor = securityProcessor.dataProcessExector();
        boolean b = validateExecutor(flatFileReader,securitySecurityItemProcessor,dataWriter);
        if(!b){
            throw new ItemSetException("ItemReader,ItemProcessor,ItemWriter",new NullPointerException());
        }
        return stepBuilderFactory.get("getdata2").<Person,Person>chunk(6000)
                .faultTolerant().retryLimit(1000).retry(Exception.class).skipLimit(50).skip(Exception.class)
                .reader(flatFileReader)
                .processor(securitySecurityItemProcessor)
                .writer(dataWriter)
                .taskExecutor(taskExecutor())
                .build();
    }

    /*public void setItemExecutor(ItemReader readerExecutor,ItemProcessor processExecutor,ItemWriter writerExecutor) {
        this.readerExecutor = readerExecutor;
        this.processExecutor = processExecutor;
        this.writerExecutor = writerExecutor;
    }*/

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
