/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author avazquez
 */
public class Policy {
    
    private static final Logger LOG = Loggers.get(Policy.class);
    
    protected String name;
    
    protected String id;
    
    protected Document xmlDocument;
    
    protected String fileName;
    
    protected String type;
    
    protected Map<String,String> parameters;
    
    public Policy(){
        parameters = new HashMap<>();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the parameters
     */
    public Map<String,String> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Map<String,String> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the xmlDocument
     */
    public Document getXmlDocument() {
        return xmlDocument;
    }

    /**
     * @param xmlDocument the xmlDocument to set
     */
    public void setXmlDocument(Document xmlDocument) {
        this.xmlDocument = xmlDocument;
    }

    public void startParsing() {
        LOG.debug("Start parsing policy");
        if (xmlDocument != null) {
            parseName();
            parseType();
            parseConfigurationData();
        }
        LOG.debug("End parsing policy");
    }
    

    protected void parseName() throws DOMException {
        
        Element resourceNode = (Element) XmlHelper.evaluateXpathNode(xmlDocument.getDocumentElement(), "/Policy");
        if (resourceNode != null) {
            name = XmlHelper.getAttributeValue(resourceNode, "name");
        }

        if (name == null || name.isEmpty()) {
            name = fileName;
        }

        if (resourceNode != null) {
            id = XmlHelper.getAttributeValue(resourceNode, "policyId");
        }
    }

    protected void parseType() throws DOMException {
        Element resourceNode = (Element) XmlHelper.evaluateXpathNode(xmlDocument.getDocumentElement(), "/Policy/configuration");
        if (resourceNode != null) {
            type = XmlHelper.getAttributeValue(resourceNode, "template");
        }


    }

    protected void parseConfigurationData() {
        if (xmlDocument != null) {
     
            NodeList configurationItemList = XmlHelper.evaluateXpathNodeSet(xmlDocument.getDocumentElement(), "/Policy/configuration/groups/paramSettings");
            if (configurationItemList != null) {
                for (int i = 0; i < configurationItemList.getLength(); i++) {
                    Element el = (Element) configurationItemList.item(i);
                    if (el != null) {

                        String paramName = XmlHelper.getAttributeValue(el, "paramName");
                        String paramValue = XmlHelper.getAttributeValue(el, "value");
                        parameters.put(paramName,paramValue);
                    }
                }
            }
        }
    }
    
    protected String getProperty(String name){
        return parameters.get(name);
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
