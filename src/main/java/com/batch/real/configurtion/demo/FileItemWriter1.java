package com.batch.real.configurtion.demo;


import com.batch.real.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;


public class FileItemWriter1  implements ItemWriter<Person> {
    private static final Logger log = LoggerFactory.getLogger(FileItemWriter1.class);

    @Override
    public void write(List<? extends Person> list) throws Exception {
        for (Person person:list) {
            log.info("Writer1 data:"+ person);
        }

    }
}
