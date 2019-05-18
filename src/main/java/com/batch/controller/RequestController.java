package com.batch.controller;

import com.batch.service.JobService;
import com.google.gson.Gson;
import org.springframework.batch.core.Job;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
        return  "Ok" ;
    }
}
