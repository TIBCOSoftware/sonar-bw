package com.tibco.sonar.plugins.bw6.source;


import com.tibco.utils.bw6.model.Project;
import java.util.ArrayList;
import java.util.List;
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.fs.InputModule;

/**
 * Checks and analyzes report measurements, issues and other findings in
 * WebSourceCode.
 *
 * @author Kapil Shivarkar
 */
public class ProjectSource extends AbstractSource {

    protected Project project;

    protected InputComponent file;
    
    protected XsdMap map;
    
    protected List<ProcessSource> process;
    
    protected List<SharedResourceSource> resource;

    public ProjectSource(InputModule file) {
            this.file = file;
            process = new ArrayList<>();
            resource = new ArrayList<>();
            map = new XsdMap();
    }
    
    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @param file the file to set
     */
    public void setComponent(InputComponent file) {
        this.file = file;
    }

    @Override
    public InputComponent getComponent() {
        return file;
    }

    /**
     * @return the map
     */
    public XsdMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(XsdMap map) {
        this.map = map;
    }

    /**
     * @return the file
     */
    public InputComponent getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(InputComponent file) {
        this.file = file;
    }

    /**
     * @return the process
     */
    public List<ProcessSource> getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(List<ProcessSource> process) {
        this.process = process;
    }

    /**
     * @return the resource
     */
    public List<SharedResourceSource> getResource() {
        return resource;
    }

    /**
     * @param resource the resource to set
     */
    public void setResource(List<SharedResourceSource> resource) {
        this.resource = resource;
    }

    
}
