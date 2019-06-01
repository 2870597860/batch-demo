package com.batch.configurtion.step.reader.rule;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public abstract class BaseFieldSet<T> implements FieldSetMapper<T> {
    protected T t;
    public BaseFieldSet(T t) {
        this.t = t;
    }
    public T mapFieldSet(FieldSet fieldSet) {
        setObjProperties(fieldSet);
        return t;
    }
    protected abstract void setObjProperties(FieldSet fieldSet);
}