package com.batch.real.security;

import com.batch.define.step.reader.rule.BaseFieldSet;
import com.batch.real.entity.Security;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

/**
 * Created by Administrator on 2019/6/2.
 */
public class SecurityFieldSet extends BaseFieldSet<Security> {
    private Security security;
    /*public SecurityFieldSet(Security security) {
        super(security);
    }
    public SecurityFieldSet() {
        this(new Security());
    }*/
    public SecurityFieldSet() {
        security = new Security();
    }
    @Override
    public Security setObjProperties(FieldSet fieldSet) {
        fieldSet.getFieldCount();
        security.setId(fieldSet.readInt(0));
        security.setName(fieldSet.readString(1));
        security.setAge(fieldSet.readInt(2));
        return security;
    }
}
