package com.batch.real.security;

import com.batch.define.step.process.DefaultDataProcess;
import com.batch.real.entity.Security;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/6/2.
 */
@Component
public class SecurityProcessor extends DefaultDataProcess<Security,Security>{
    private static final Logger log = LoggerFactory.getLogger(SecurityProcessor.class);
    public Security doExecutor(Security security) {
        log.info("process data:"+security);
        return security;
    }
}
