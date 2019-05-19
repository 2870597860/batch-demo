package com.batch.entity;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/5/19.
 */
@Component
public class Path {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
