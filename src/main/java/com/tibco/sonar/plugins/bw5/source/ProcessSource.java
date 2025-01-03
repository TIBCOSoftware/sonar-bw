/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.source;


import com.tibco.sonar.plugins.bw.source.AbstractSource;
import com.tibco.utils.bw5.model.Process;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sonar.api.batch.fs.InputFile;
import org.sonarsource.analyzer.commons.xml.XmlFile;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 *
 * @author Matthijs Galesloot
 */
public class ProcessSource extends AbstractSource {

    private Process process;

    private XmlFile file;

    private File baseDir;

    public ProcessSource(InputFile file) {
        try {
            this.file = XmlFile.create(file);
            this.process = new Process();
            this.process.setProcessXmlDocument(this.file.getNamespaceAwareDocument());
            process.parse();
        } catch (IOException ex) {
            Logger.getLogger(ProcessSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public ProcessSource(String file) {

        this.file = XmlFile.create(file);
        this.process = new Process();
        this.process.setProcessXmlDocument(this.file.getNamespaceAwareDocument());
        process.parse();

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
    @Override
    public InputFile getComponent() {
        return file.getInputFile();
    }

    /**
     * @param file the file to set
     */
    public void setFile(XmlFile file) {
        this.file = file;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public void setBaseDir(File baseDir) {
        this.baseDir = baseDir;
    }
}
