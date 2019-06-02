package com.batch.real.proxy;

import com.batch.real.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonProcessor {
    private static final Logger log = LoggerFactory.getLogger(PersonProcessor.class);
    public void print(Person person){
        log.info("write data:"+person);
    }
}
