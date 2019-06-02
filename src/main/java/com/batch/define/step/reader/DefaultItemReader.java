package com.batch.define.step.reader;

import com.batch.define.step.reader.rule.BaseLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.sql.DataSource;

public abstract class DefaultItemReader<T> implements IReaderExector {
    private static final Logger log = LoggerFactory.getLogger(DefaultItemReader.class);

    BaseLineMapper<T> lineMapper;
    FieldSetMapper<T> defaultFieldSet;

    /**
     * txt,csv，文件读。
     */
    /**
     *
     * @param isUseAutoMapper 是否使用自动映射到
     *  @param prototypeBeanName 映射对象beanName（JavaBean规范通过将字段名称与对象上的setter匹配来自动映射字段）
     */
    public void setUseAutoMapper(boolean isUseAutoMapper,String prototypeBeanName){
        if(isUseAutoMapper){
            defaultFieldSet = getBeanWrapperFieldSetMapper(prototypeBeanName);
        }
    }
    private FieldSetMapper getBeanWrapperFieldSetMapper(String prototypeBeanName) {
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setPrototypeBeanName(prototypeBeanName);
        return fieldSetMapper;
    }
    public DefaultItemReader<T> setFlatFileReader(FieldSetMapper<T> defaultFieldSet, BaseLineMapper<T> lineMapper) {
        this.lineMapper = lineMapper;
        this.defaultFieldSet = defaultFieldSet;
        return this;
    }
    public FlatFileItemReader<T> buildFlatFileReader(String filePath,String delimiter) {
        FlatFileItemReader<T> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        lineMapper.setLineTokenizer(new DelimitedLineTokenizer(delimiter));
        lineMapper.setFieldSetMapper(defaultFieldSet);
        reader.setLineMapper(lineMapper);
        return reader;
    }

    /**
     *xml文件读
     */
    /**
     *
     * @param filePath 文件路径
     * @param rootElement xml对象的根元素
     * @param xStreamMarshaller 映射包装
     * @return
     */
    public StaxEventItemReader<T> staxEventReader(String filePath,String rootElement,XStreamMarshaller xStreamMarshaller) {
        StaxEventItemReaderBuilder<T> tStaxEventItemReaderBuilder = new StaxEventItemReaderBuilder<>();
        tStaxEventItemReaderBuilder.name("staxEventReader")
                .resource(new FileSystemResource(filePath))
                .addFragmentRootElements(rootElement)
                .unmarshaller(xStreamMarshaller);
        return tStaxEventItemReaderBuilder.build();

    }
    public JdbcCursorItemReader<T> jdbcReader(DataSource dataSource) {
        JdbcCursorItemReaderBuilder<T> jdbcReaderBuilder = new JdbcCursorItemReaderBuilder<T>()
                .dataSource(dataSource)
                .name("jdbcReader");
        setJDBCQuryProperties(jdbcReaderBuilder);
        return jdbcReaderBuilder.build();
    }
    /**
     * jdbcReader
     *.sql("select ID, NAME, CREDIT from CUSTOMER")
     * .rowMapper(new CustomerCreditRowMapper())
     */
    protected void setJDBCQuryProperties(JdbcCursorItemReaderBuilder<T> jdbcReader ){
        // todo
    }


}

