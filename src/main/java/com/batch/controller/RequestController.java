package com.batch.controller;

import com.batch.entity.Path;
import com.batch.service.JobService;
import com.google.gson.Gson;
import org.springframework.batch.core.Job;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/batch")
public class RequestController {

    @Resource
    JobService jobService;

    @PostMapping("/requestJob")
    public String requestJob(){
        jobService.dataHanlerJob();
        return  "dataHanlerJob...Ok" ;
    }
    @PostMapping("/requestJobWithTaskExcetor")
    public String requestJob2(){
        jobService.dataHanlerJobWithTaskExecutor();
        return  "requestJobWithTaskExcetor...Ok" ;
    }
    @PostMapping("/requestJobWithMyThread")
    public String requestJob3(){
        jobService.dataHanlerJobWithMyThreadPoolTask();
        return  "dataHanlerJobWithMyThreadPoolTask..Ok" ;
    }
    @PostMapping("/requestJobParam")
    public String requestJob4(@RequestBody Path path){
        jobService.dataHanlerJobWithPath(path);
        return  "dataHanlerJobWithPath..Ok" ;
    }
}
