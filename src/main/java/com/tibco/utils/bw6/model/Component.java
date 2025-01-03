/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author avazquez
 */
public class Component {
    
    protected Document bwmDocument;
    
    protected Element element;
    
    protected String name;
   
    protected Process process;
    
    protected List<Service> services;
    
    public Component(Element root, Document bwmDocument){
        services = new ArrayList<>();
        this.element = root;
        this.bwmDocument = bwmDocument;
    }

    /**
     * @return the bwmDocument
     */
    public Document getBwmDocument() {
        return bwmDocument;
    }

    /**
     * @param bwmDocument the bwmDocument to set
     */
    public void setBwmDocument(Document bwmDocument) {
        this.bwmDocument = bwmDocument;
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
     * @return the process
     */
    public Process getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(Process process) {
        this.process = process;
    }

    /**
     * @return the services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * @param services the services to set
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }

    public void addService(Service service) {
       services.add(service);
    }
    
    public Service getServiceByName(String serviceName) {
        if(serviceName != null){
            for( Service service : services){
                if(serviceName.equals(service.getName())){
                    return service;
                }
            }
        }
        return null;
    }
    
}
