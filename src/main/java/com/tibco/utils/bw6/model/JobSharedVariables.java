/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.model;



import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import org.w3c.dom.Document;

public class JobSharedVariables  extends ModuleProperties{

    private static final Logger LOG = Loggers.get(JobSharedVariables.class);


    public String getElementName(){
        return "jobSharedVariable";
    }
    
    public JobSharedVariables(Document document) {
        super(document);
    }


}
