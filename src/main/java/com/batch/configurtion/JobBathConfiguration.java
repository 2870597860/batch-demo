package com.batch.configurtion;

import com.batch.configurtion.support.FirstJobConfiguration;
import com.batch.configurtion.support.SecondJobConfiguration;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobBathConfiguration {
    @Bean
    public ApplicationContextFactory firstJobContext() {
        return new GenericApplicationContextFactory(FirstJobConfiguration.class);
    }
    @Bean
    public ApplicationContextFactory secondJobContext() {
        return new GenericApplicationContextFactory(SecondJobConfiguration.class);
    }
}
