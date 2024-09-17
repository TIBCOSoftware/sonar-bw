/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.common.logger;

import com.tibco.utils.common.logger.impl.SysOutLogger;

import java.lang.reflect.InvocationTargetException;


/**
 *
 * @author avazquez
 */
public class Loggers {

    private static String loggerImplementationClass = "com.tibco.utils.bw6.logger.impl.SysOutLogger";

    public static void setLoggerImplementation(String className){
        loggerImplementationClass =  className;
    }

    public static Logger get(Class className) {

        Class c = null;
        Logger logger;
        try {
            c = Class.forName(loggerImplementationClass);
        } catch (ClassNotFoundException e) {

        }

        if (c != null) {

            try {
                logger = (Logger) c.getConstructor(Class.class).newInstance(className);
                return logger;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                System.err.println("Error generating the logger component: " + e.getMessage());
            }

        }

        return new SysOutLogger(className);

    }

}
