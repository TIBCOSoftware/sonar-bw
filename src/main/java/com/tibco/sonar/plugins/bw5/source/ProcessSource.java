package com.tibco.sonar.plugins.bw5.source;

/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import com.tibco.sonar.plugins.bw.source.AbstractSource;
import com.tibco.utils.bw5.model.Process;

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

    public ProcessSource(InputFile file) {
        try {
            this.file = XmlFile.create(file);
            this.process = new Process();
            this.process.setProcessXmlDocument(this.file.getNamespaceUnawareDocument());
            process.parse();
        } catch (IOException ex) {
            Logger.getLogger(ProcessSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProcessSource(String file) {

        this.file = XmlFile.create(file);
        this.process = new Process();
        this.process.setProcessXmlDocument(this.file.getNamespaceUnawareDocument());
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

}
