package com.batch.define.step.reader.rule;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

public class BaseLineMapper<T> implements LineMapper<T> , InitializingBean {
    private LineTokenizer tokenizer;

    private FieldSetMapper<T> fieldSetMapper;

    public BaseLineMapper() {
    }

    public BaseLineMapper(LineTokenizer tokenizer, FieldSetMapper<T> fieldSetMapper) {
        this.tokenizer = tokenizer;
        this.fieldSetMapper = fieldSetMapper;
    }
    @Override
    public T mapLine(String line, int lineNumber) throws Exception {
        if(!StringUtils.isEmpty(line)){
            return fieldSetMapper.mapFieldSet(tokenizer.tokenize(line));
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public void setLineTokenizer(LineTokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    public void setFieldSetMapper(FieldSetMapper<T> fieldSetMapper) {
        this.fieldSetMapper = fieldSetMapper;
    }

    public LineTokenizer getTokenizer() {
        return tokenizer;
    }

    public FieldSetMapper<T> getFieldSetMapper() {
        return fieldSetMapper;
    }

}
