package com.batch.service;

import com.batch.entity.Path;
import org.springframework.batch.core.Job;

public interface IJobService {
    void dataHanlerJob();
    void dataHanlerJobWithTaskExecutor();
    void dataHanlerJobWithMyThreadPoolTask();
    void dataHanlerJobWithPath(Path path);
}
