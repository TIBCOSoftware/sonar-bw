/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;




public class ModuleProperties {

    private static final Logger LOG = LoggerFactory.getLogger(ModuleProperties.class);

    protected Document document;

    protected List<Property> propertyList;

    public ModuleProperties(Document document) {
        this.document = document;
        propertyList = new ArrayList<>();
        parseModuleProperties();
    }
    
    public String getElementName(){
        return "/sca:composite/sca:property";
    }

    private void parseModuleProperties() {
        if (document != null) {
            document.getDocumentElement().normalize();
            LOG.debug("Element Name: "+getElementName());
            NodeList temporalNodePropertyList = document.getElementsByTagName(getElementName());
            if (temporalNodePropertyList != null) {
                for (int i = 0; i < temporalNodePropertyList.getLength(); i++) {
                    String logStartphrase = "Parsed Property: ";
                    LOG.debug(logStartphrase + temporalNodePropertyList.item(i).getAttributes().getNamedItem("name"));
                    LOG.debug(logStartphrase + temporalNodePropertyList.item(i).getAttributes().getNamedItem("name").getNodeValue());
                    LOG.debug(logStartphrase + temporalNodePropertyList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    Property prop = new Property();
                    prop.setName(temporalNodePropertyList.item(i).getAttributes().getNamedItem("name").getTextContent());
                    prop.calculatePath();
                    LOG.debug("Parsed Property Path: "+prop.getPath());
                    propertyList.add(prop);
                }
            }
        }
    }

    public List<Property> getPropertyByPath(String path) {
        
        List<Property> temporaryListPropertiesWithExpectedPath = new ArrayList<>();
        if(path != null){
            for (Property prop : propertyList) {
                if (path.equals(prop.getPath())) {
                    temporaryListPropertiesWithExpectedPath.add(prop);
                }
            }
        }
        return temporaryListPropertiesWithExpectedPath;

    }

    public List<String> getPropertyPaths() {
        List<String> temporaryListWithPropertyPaths = new ArrayList<>();
        for (Property prop : propertyList) {
            LOG.debug("Property path: "+prop.getPath());
            if (prop.getPath() != null && !temporaryListWithPropertyPaths.contains(prop.getPath())) {
                LOG.debug("Property added path: "+prop.getPath());
                temporaryListWithPropertyPaths.add(prop.getPath());
            }
        }
        LOG.debug("Paths properties added: "+temporaryListWithPropertyPaths.size());
        return temporaryListWithPropertyPaths;
    }

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
     * @return the propertyList
     */
    public List<Property> getPropertyList() {
        return propertyList;
    }

    /**
     * @param propertyList the propertyList to set
     */
    public void setPropertyList(List<Property> propertyList) {
        this.propertyList = propertyList;
    }

    
}
