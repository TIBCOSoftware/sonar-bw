/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.sonar.plugins.bw6.source;

import com.tibco.utils.bw6.model.XmlResource;
import java.util.HashMap;
import java.util.Map;
import org.sonar.api.batch.fs.InputFile;

/**
 *
 * @author avazquez
 */
public class XsdMap {
    
    protected Map<XmlResource,InputFile> map;
    
    public XsdMap(){
        map = new HashMap<>();
    }

    public boolean addFile(XmlResource schema, InputFile file) {
        InputFile target = map.get(schema);
        if(target == null){
            map.put(schema, file);
            return true;
        }
        return false;
    }
    
    public InputFile getFile(XmlResource schema) {
        return map.get(schema);
    }
}
