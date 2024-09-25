/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;



import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import org.w3c.dom.Document;

public class ModuleSharedVariables  extends ModuleProperties{

    private static final Logger LOG = Loggers.get(ModuleSharedVariables.class);

    
    public String getElementName(){
        return "moduleSharedVariable";
    }

    public ModuleSharedVariables(Document document) {
        super(document);
    }


}
