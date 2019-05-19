package com.batch.configurtion;

import com.batch.entity.Person;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class PlayerFieldSetMapper implements FieldSetMapper<Person> {
    public Person mapFieldSet(FieldSet fieldSet) {
        Person person = new Person();
        person.setId(fieldSet.readInt(0));
        person.setName(fieldSet.readString(1));
        person.setAge(fieldSet.readInt(2));
        return person;
    }
}