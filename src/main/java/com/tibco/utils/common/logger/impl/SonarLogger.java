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
    
    private final org.sonar.api.utils.log.Logger LOG;
    
    public SonarLogger(Class className){
        LOG = org.sonar.api.utils.log.Loggers.get(className);
    }

    @Override
    public void debug(String arg0) {
       LOG.debug(arg0);
    }

    @Override
    public void warn(String arg0, Exception arg1) {
        LOG.warn(arg0, arg1);
    }
    
}
