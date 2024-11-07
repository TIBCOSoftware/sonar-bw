/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;



import org.w3c.dom.Document;

public class JobSharedVariables  extends ModuleProperties{



    @Override
    public String getElementName(){
        return "jobSharedVariable";
    }
    
    public JobSharedVariables(Document document) {
        super(document);
    }


}
