/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw.source;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sonar.api.batch.fs.InputFile;
import org.sonarsource.analyzer.commons.xml.XmlFile;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 *
 * @author TIBCODX Toolkit
 */
public class XmlSource extends AbstractSource {

    protected XmlFile file;

    public XmlSource(InputFile file) {
        try {
            this.file = XmlFile.create(file);            
            
        } catch (IOException ex) {
            Logger.getLogger(XmlSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       public XmlSource(String file) {
       
           
            this.file = XmlFile.create(file);
         
       
    }

    /**
     * @return the file
     */
    public InputFile getComponent() {
        return file.getInputFile();
    }

    /**
     * @param file the file to set
     */
    public void setComponent(XmlFile file) {
        this.file = file;
    }

    public XmlFile getFile() {
        return file;
    }

    
}
