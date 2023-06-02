/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.logger.impl;

import com.tibco.utils.bw6.logger.Logger;
import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author avazquez
 */
public class SonarLogger implements Logger {
    
    private org.sonar.api.utils.log.Logger LOG;
    
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
