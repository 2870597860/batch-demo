package com.batch.configurtion.step.reader;

import com.batch.configurtion.common.BaseLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;

public abstract class DefaultItemReader<T>{
    private static final Logger log = LoggerFactory.getLogger(DefaultItemReader.class);
    protected final BaseLineMapper<T> lineMapper;
    protected final FieldSetMapper<T> defaultFieldSet;
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 1、lineMapper.setLineTokenizer(new DelimitedLineTokenizer(delimiter));
     *
     * 2、{BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
     * fieldSetMapper.setPrototypeBeanName("player");}
     */
    abstract void setFieldSetMapperProperties();

    /**
     * .resource(new FileSystemResource(path))
     * .addFragmentRootElements("Security")
     * .unmarshaller(xStreamMarshaller)
     * xStreamMarshaller.setAnnotatedClasses(Security.class);
     */
    abstract void setStaxEventProperties(StaxEventItemReaderBuilder<T> staxEventReader);

    /**
     * jdbcReader
     *.sql("select ID, NAME, CREDIT from CUSTOMER")
     * .rowMapper(new CustomerCreditRowMapper())
     */
    abstract void setJDBCQuryProperties(JdbcCursorItemReaderBuilder<T> jdbcReader );

    public DefaultItemReader(BaseLineMapper<T> lineMapper,FieldSetMapper<T> defaultFieldSet) {
        this.defaultFieldSet = defaultFieldSet;
        this.lineMapper = lineMapper;
    }
    public FlatFileItemReader<T> flatFileReader(String filePath) {
        FlatFileItemReader<T> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        setFieldSetMapperProperties();
        lineMapper.setFieldSetMapper(defaultFieldSet);
        reader.setLineMapper(lineMapper);
        return reader;
    }
    public StaxEventItemReader<T> staxEventReader(String filePath) {
        StaxEventItemReaderBuilder<T> tStaxEventItemReaderBuilder = new StaxEventItemReaderBuilder<>();
        tStaxEventItemReaderBuilder.name("staxEventReader")
                .resource(new FileSystemResource(filePath));
        setStaxEventProperties(tStaxEventItemReaderBuilder);
        return tStaxEventItemReaderBuilder.build();

    }
    public JdbcCursorItemReader<T> jdbcReader() {
        JdbcCursorItemReaderBuilder<T> jdbcReaderBuilder = new JdbcCursorItemReaderBuilder<T>()
                .dataSource(dataSource)
                .name("jdbcReader");
        setJDBCQuryProperties(jdbcReaderBuilder);
        return jdbcReaderBuilder.build();
    }



}

