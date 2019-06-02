package com.batch.real.entity;

import org.springframework.stereotype.Component;

@Component
public class Access {
    private Integer id;
    private String username;
    private String shopName;
    private String shopId;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
