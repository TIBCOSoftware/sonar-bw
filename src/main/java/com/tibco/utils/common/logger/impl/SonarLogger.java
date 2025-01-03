/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.common.logger.impl;

import com.tibco.utils.common.logger.Logger;

/**
 *
 * @author avazquez
 */
public class SonarLogger implements Logger {
    
    private final  org.slf4j.Logger log;
    
    public SonarLogger(Class<?> className){
        log = org.slf4j.LoggerFactory.getLogger(className);
    }

    @Override
    public void debug(String arg0) {
       log.debug(arg0);
    }

    @Override
    public void warn(String arg0, Exception arg1) {
        log.warn(arg0, arg1);
    }

    @Override
    public void warn(String arg0) {
        log.warn(arg0);
    }

    @Override
    public void info(String arg0) {
        log.info(arg0);
    }

    @Override
    public void error(String arg0) {
        log.error(arg0);
    }

    @Override
    public void error(String arg0, Exception arg1) {
        log.error(arg0, arg1);
    }
}
