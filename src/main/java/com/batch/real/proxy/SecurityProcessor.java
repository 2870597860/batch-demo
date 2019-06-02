package com.batch.real.proxy;

import com.batch.real.entity.Person;
import com.batch.real.entity.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityProcessor {
    private static final Logger log = LoggerFactory.getLogger(SecurityProcessor.class);
    public void print(Security security){
        log.info("write data:"+security);
    }
}
