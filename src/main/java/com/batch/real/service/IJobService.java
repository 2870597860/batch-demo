package com.batch.real.service;

import com.batch.real.entity.Path;

public interface IJobService {
    void dataHanlerJob();
    void dataHanlerJobWithTaskExecutor();
    void dataHanlerJobWithMyThreadPoolTask();
    void dataHanlerJobWithPath(Path path);
}
