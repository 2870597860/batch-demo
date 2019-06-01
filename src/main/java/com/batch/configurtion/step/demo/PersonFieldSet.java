package com.batch.configurtion.step.demo;

import com.batch.configurtion.step.reader.rule.BaseFieldSet;
import com.batch.entity.Person;
import org.springframework.batch.item.file.transform.FieldSet;

public class PersonFieldSet extends BaseFieldSet<Person> {
    public PersonFieldSet(Person person) {
        super(person);
    }
    public void setObjProperties(FieldSet fieldSet){
        fieldSet.getFieldCount();
        this.t.setId(fieldSet.readInt(0));
        this.t.setName(fieldSet.readString(1));
        this.t.setAge(fieldSet.readInt(2));
    }

}
