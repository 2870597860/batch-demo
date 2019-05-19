package com.batch.service;

import com.batch.configurtion.SimpleBatchConfiguration;
import com.batch.entity.Path;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("jobService")
public class JobService implements IJobService{
    @Resource
    JobLauncher jobLauncher;
    @Resource
    SimpleBatchConfiguration simpleBatchConfiguration;

    @Resource
    Job job;

    public JobParameters jobParameters;

    @Override
    public void dataHanlerJob() {
        jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(simpleBatchConfiguration.dataHanlerJob(),jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataHanlerJobWithTaskExecutor() {
        jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis())
                .addString("JobWithTaskExecutor","JobWithTaskExecutor")
                .toJobParameters();
        try {
            jobLauncher.run(simpleBatchConfiguration.dataHanlerJob2(),jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataHanlerJobWithMyThreadPoolTask() {
        jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis())
                .addString("JobWithMyThreadPoolTask","JobWithMyThreadPoolTask")
                .toJobParameters();
        try {
            jobLauncher.run(simpleBatchConfiguration.dataHanlerJob3(),jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dataHanlerJobWithPath(Path path) {
        jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis())
                .addString("input",path.getFilePath())
                .toJobParameters();
        try {
            jobLauncher.run(job,jobParameters);
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }
}
