/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

/**
 *
 * @author avazquez
 */
public class Property {
    protected String name;    
    protected String value;    
    protected String path;

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
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    void calculatePath() {
        if(name != null && !"".equals(name)){
            int idxPath = name.lastIndexOf("/");
            if(idxPath > 0){
                path = name.substring(0,idxPath);
            }
            else{
                path = "/";
            }
        }
    }
    
    
}
