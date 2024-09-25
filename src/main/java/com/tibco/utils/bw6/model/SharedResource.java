/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import com.tibco.utils.common.helper.XmlHelper;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

/**
 *
 * @author avazquez
 */
public class SharedResource {

    private String name;
    private String type;
    private Document document;
    //protected File file;
    protected String fileName;
    private List<SharedResourceParameter> properties = new ArrayList<>();
    
    protected Project project;
    
    
    
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
     * @return the properties
     */
    public List<SharedResourceParameter> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(List<SharedResourceParameter> properties) {
        this.properties = properties;
    }


    public void parse() {        
        if (document != null) {
            parseName();
            parseType();
            parseConfigurationData();
        }
    }

    protected void parseName() throws DOMException {
        Element resourceNode = (Element) XmlHelper.evaluateXpathNode(document.getDocumentElement(), "/namedResource");
        if (resourceNode != null) {
            name = XmlHelper.getAttributeValue(resourceNode, "name");
        }

        if (name == null || name.isEmpty()) {
            name = fileName;
        }
    }

    protected void parseType() throws DOMException {
        Element resourceNode = (Element) XmlHelper.evaluateXpathNode(document.getDocumentElement(), "/namedResource");
        if (resourceNode != null) {
            type = XmlHelper.getAttributeValue(resourceNode, "type");
        }

        if (type == null || type.isEmpty()) {
            type = FilenameUtils.getExtension(fileName);
        }       
    }

    protected void setDocumentConfigParams(Element tempElement, String prefixAttrName){
        if (tempElement != null) {
            NamedNodeMap attributes = tempElement.getAttributes();
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    String attrName = attributes.item(i).getNodeName();
                    String attrValue = attributes.item(i).getNodeValue();
                    addParameter(prefixAttrName+ attrName, attrValue);
                }
            }
        }
    }

    protected void parseConfigurationData() {
        if (document != null) {
            Element configuration = (Element) XmlHelper.evaluateXpathNode(document.getDocumentElement(), "/namedResource/configuration");
            setDocumentConfigParams(configuration,"");

            Element tcpDetails = (Element) XmlHelper.evaluateXpathNode(document.getDocumentElement(), "/namedResource/configuration/tcpDetails");
            setDocumentConfigParams(tcpDetails,"tcpDetails_");

            NodeList configurationItemList = XmlHelper.evaluateXpathNodeSet(document.getDocumentElement(), "/namedResource/configuration//substitutionBindings");
            if (configurationItemList != null) {
                for (int i = 0; i < configurationItemList.getLength(); i++) {
                    Element el = (Element) configurationItemList.item(i);
                    if (el != null) {

                        String template = XmlHelper.getAttributeValue(el, "template");
                        String propName = XmlHelper.getAttributeValue(el, "propName");
                        addParameterWithProperty(template, propName);
                    }
                }
            }
            Element bwIdentitySet = (Element) XmlHelper.evaluateXpathNode(document.getDocumentElement(), "/namedResource/identitySet");
            setDocumentConfigParams(bwIdentitySet,"");
        }
    }

public void addParameter(String name, String value) {
        SharedResourceParameter returnParameter = getParameterByName(name);
        if (returnParameter != null) {
            returnParameter.setValue(value);
        } else {
            returnParameter = new SharedResourceParameter();
            returnParameter.setName(name);
            returnParameter.setValue(value);
            properties.add(returnParameter);
        }

    }
    
    
    private void addParameterWithProperty(String template, String name) {
        SharedResourceParameter returnParameter = getParameterByName(template);
        if (returnParameter != null) {
            returnParameter.setValue(name);
        } else {
            returnParameter = new SharedResourceParameter();
            returnParameter.setName(template);
            returnParameter.setValue(name);
            properties.add(returnParameter);
        }
        returnParameter.setGlobalVariable(true);
    }
    
    public SharedResourceParameter getParameterByName(String name) {
        if (name != null) {
            for (SharedResourceParameter parameter :properties) {
                if (name.equals(parameter.getName())) {
                    return parameter;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Name: ").append(name).append(";Type: ").append(type).append(";");
        for ( SharedResourceParameter parameter : properties) {
            out.append("{").append(parameter.getName()).append(",").append(parameter.getValue()).append("}");
        }
        out.append(";");
        return out.toString();
    }

    

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
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

}
