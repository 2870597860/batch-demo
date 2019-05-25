package com.batch.configurtion.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JobListener implements JobExecutionListener {
    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private long startTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        logger.info("job beforce:"+jobExecution.getJobParameters());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("JOB STATUS : {}", jobExecution.getStatus());
        if(jobExecution.getStatus() == BatchStatus.COMPLETED){
            logger.info("job finish");
            threadPoolTaskExecutor.destroy();
        }else if(jobExecution.getStatus() == BatchStatus.FAILED){
            logger.info("job failed");
        }
        logger.info("job cost time :{} ms",(System.currentTimeMillis()-startTime));
    }
}
