package com.tibco.sonar.plugins.bw6.source;


import com.tibco.utils.bw6.model.SharedResource;
import java.io.IOException;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.xml.XmlFile;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 *
 * @author Kapil Shivarkar
 */
public class SharedResourceSource extends AbstractSource {

    private final static Logger LOG = Loggers.get(SharedResourceSource.class);
    
    private SharedResource resource;

    private XmlFile file;
    
    protected FileSystem fileSystem;

    public SharedResourceSource(FileSystem fileSystem,InputFile file) {
        try {
            LOG.info("Parsing Shared Resource ["+file+"]");
            this.fileSystem = fileSystem;
            this.file = XmlFile.create(file);
            this.resource = new SharedResource();
            this.resource.setFile(file);
            this.resource.setDocument(this.file.getNamespaceUnawareDocument());
            LOG.info("Shared Resource ["+file+"] parsed");
        } catch (IOException ex) {
            LOG.error("Error while parsing shared resource in file ["+file+"]",ex);
        }
    }

    
    
    
    

    public SharedResource getResource() {
        return resource;
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

    /**
     * @return the fileSystem
     */
    public FileSystem getFileSystem() {
        return fileSystem;
    }

    /**
     * @param fileSystem the fileSystem to set
     */
    public void setFileSystem(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    
}
