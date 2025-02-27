/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.source;


import com.tibco.sonar.plugins.bw.source.AbstractSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.Process;

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
public class ProcessSource extends AbstractSource {

    private Process process;

    private XmlFile file;

    public ProcessSource(ProjectSource project, InputFile file) {
        try {
            this.file = XmlFile.create(file);
            this.process = new Process();
            this.process.setProcessXmlDocument(XmlHelper.getDocument(file.inputStream()) );
            process.startParsing();
            project.getProcess().add(this);
        } catch (Exception ex) {
            Logger.getLogger(ProcessSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
       public ProcessSource(String file) {
       
           
            this.file = XmlFile.create(file);
             this.process = new Process();
             this.process.setProcessXmlDocument(XmlHelper.getDocument(file));
                process.startParsing();
       
    }

    public void setProcessModel(Process process) {
        this.process = process;
    }

    public Process getProcessModel() {
        return process;
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

    
}
