/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
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
