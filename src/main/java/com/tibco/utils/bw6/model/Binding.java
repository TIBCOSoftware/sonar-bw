/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author avazquez
 */
public class Binding {

    public static final String SOAP_VERSION = "soapVersion";
    public static final String TRANSPORT_BINDING_TYPE = "transportBindingType";
    public static final String ATTACHMENT_STYLE = "attachmentStyle";
    public static final String ENDPOINT_URI = "endpointURI";
    public static final String HTTP_CONNECTOR = "httpConnector";

    protected boolean isPropertyURI;

    protected Map<String, String> properties;

    public Binding() {
        properties = new HashMap<>();
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    /**
     * @return the httpConnector
     */
    public String getHttpConnector() {
        return properties.get(HTTP_CONNECTOR);
    }

    /**
     * @param httpConnector the httpConnector to set
     */
    public void setHttpConnector(String httpConnector) {
        properties.put(HTTP_CONNECTOR, httpConnector);
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return properties.get(ENDPOINT_URI);
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.properties.put(ENDPOINT_URI, uri);
    }

    /**
     * @return the isPropertyURI
     */
    public boolean isIsPropertyURI() {
        return isPropertyURI;
    }

    /**
     * @param isPropertyURI the isPropertyURI to set
     */
    public void setIsPropertyURI(boolean isPropertyURI) {
        this.isPropertyURI = isPropertyURI;
    }

    /**
     * @return the soapVersion
     */
    public String getSoapVersion() {
        return properties.get(SOAP_VERSION);
    }

    /**
     * @param soapVersion the soapVersion to set
     */
    public void setSoapVersion(String soapVersion) {
        properties.put(SOAP_VERSION, soapVersion);
    }

    /**
     * @return the transportBindingType
     */
    public String getTransportBindingType() {
        return properties.get(TRANSPORT_BINDING_TYPE);
    }

    /**
     * @param transportBindingType the transportBindingType to set
     */
    public void setTransportBindingType(String transportBindingType) {
        properties.put(TRANSPORT_BINDING_TYPE, transportBindingType);
    }

    /**
     * @return the attachmentStyle
     */
    public String getAttachmentStyle() {
        return properties.get(ATTACHMENT_STYLE);
    }

    /**
     * @param attachmentStyle the attachmentStyle to set
     */
    public void setAttachmentStyle(String attachmentStyle) {
        properties.put(ATTACHMENT_STYLE, attachmentStyle);
    }
}
