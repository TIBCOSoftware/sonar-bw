package com.tibco.sonar.plugins.bw6.source;


import com.tibco.utils.bw.model.Process;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sonar.api.batch.fs.InputFile;
import org.sonarsource.analyzer.commons.xml.XmlFile;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 *
 * @author Kapil Shivarkar
 */
public class ProcessSource extends AbstractSource {

    private Process process;

    private XmlFile file;

    public ProcessSource(InputFile file) {
        try {
            this.file = XmlFile.create(file);
            this.process = new Process();
            this.process.setProcessXmlDocument(this.file.getNamespaceUnawareDocument());
        } catch (IOException ex) {
            Logger.getLogger(ProcessSource.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public XmlFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(XmlFile file) {
        this.file = file;
    }

    
}
