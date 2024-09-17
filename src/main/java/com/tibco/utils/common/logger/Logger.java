/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.common.logger;

import javax.xml.xpath.XPathExpressionException;

/**
 *
 * @author avazquez
 */
public interface Logger {
    
    void debug(String msg);

    void warn(String string, Exception ex);
    
    
}
