package com.batch.service;

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
    Job importJob;

    public JobParameters jobParameters;

    @Override
    public void dataHanlerJob() {
        jobParameters = new JobParametersBuilder().addLong("time",System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(importJob,jobParameters);
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
