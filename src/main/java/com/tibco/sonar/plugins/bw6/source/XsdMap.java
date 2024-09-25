/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.source;

import com.tibco.utils.bw6.model.GenericResource;
import com.tibco.utils.bw6.model.XmlResource;
import java.util.HashMap;
import java.util.Map;
import org.sonar.api.batch.fs.InputFile;

/**
 *
 * @author avazquez
 */
public class XsdMap {
    
    protected Map<GenericResource,InputFile> map;
    
    public XsdMap(){
        map = new HashMap<>();
    }

    public boolean addFile(GenericResource schema, InputFile file) {
        InputFile target = map.get(schema);
        if(target == null){
            map.put(schema, file);
            return true;
        }
        return false;
    }
    
    public InputFile getFile(GenericResource schema) {
        return map.get(schema);
    }
}
