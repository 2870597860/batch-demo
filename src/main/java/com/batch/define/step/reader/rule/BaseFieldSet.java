package com.batch.define.step.reader.rule;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public abstract class BaseFieldSet<T> implements FieldSetMapper<T> {
    /*protected T feild;
    public BaseFieldSet(T t) {
        this.feild = t;
    }*/
    public T mapFieldSet(FieldSet fieldSet) {
        T obj = setObjProperties(fieldSet);
        return obj;
    }
    protected abstract T setObjProperties(FieldSet fieldSet);
}