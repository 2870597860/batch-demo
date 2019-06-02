package com.batch.real.configurtion.demo;

import com.batch.define.step.reader.rule.BaseFieldSet;
import com.batch.real.entity.Person;
import com.batch.real.entity.Security;
import org.springframework.batch.item.file.transform.FieldSet;

public class PersonFieldSet extends BaseFieldSet<Person> {
    private Person person;
    public PersonFieldSet() {
        person = new Person();
    }
    public PersonFieldSet(Person person) {
        this.person = person;
    }
    public Person setObjProperties(FieldSet fieldSet){
        fieldSet.getFieldCount();
        person.setId(fieldSet.readInt(0));
        person.setName(fieldSet.readString(1));
        person.setAge(fieldSet.readInt(2));
        return  person;
    }

}
