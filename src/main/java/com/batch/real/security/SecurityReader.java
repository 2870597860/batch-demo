package com.batch.real.security;

import com.batch.define.step.reader.DefaultItemReader;
import com.batch.define.step.reader.rule.BaseFieldSet;
import com.batch.define.step.reader.rule.BaseLineMapper;
import com.batch.real.entity.Person;
import com.batch.real.entity.Security;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/6/2.
 */
@Component
public class SecurityReader extends DefaultItemReader<Security>{

    public FlatFileItemReader<Security> getFlatFileReader(String filePath,String delimiter){
        SecurityReader securityReader = new SecurityReader();
        FlatFileItemReader<Security> securityFlatFileItemReader = securityReader
                .setFlatFileReader(new SecurityFieldSet(), new BaseLineMapper<Security>())
                .buildFlatFileReader(filePath, delimiter);
        return securityFlatFileItemReader;
    }
}
