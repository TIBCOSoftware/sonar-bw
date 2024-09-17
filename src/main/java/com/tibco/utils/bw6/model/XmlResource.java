/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.model;

import java.io.File;
import org.w3c.dom.Document;

/**
 *
 * @author avazquez
 */
public class XmlResource extends GenericResource {
    
    protected Document document;
    
    protected String targetNamespace;
    
    

    /**
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

    /**
     * @return the targetNamespace
     */
    public String getTargetNamespace() {
        return targetNamespace;
    }

    /**
     * @param targetNamespace the targetNamespace to set
     */
    public void setTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
    }

   
    
    
    
}
