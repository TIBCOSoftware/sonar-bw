/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.common.logger.impl;

import com.tibco.utils.common.logger.Logger;

/**
 *
 * @author avazquez
 */
public class SysOutLogger implements Logger {

    public SysOutLogger(Class className) {

    }

    @Override
    public void debug(String arg0) {
        System.out.println(arg0);
    }

    @Override
    public void warn(String arg0, Exception arg1) {
        if (arg1 != null) {
            System.err.println(arg0 + ": " + arg1.getMessage());
        } else {
            System.err.println(arg0);
        }
    }

}
