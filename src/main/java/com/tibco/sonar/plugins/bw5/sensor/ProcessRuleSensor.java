package com.tibco.sonar.plugins.bw5.sensor;

import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw5.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw5.check.XPathCheck;
import com.tibco.sonar.plugins.bw5.language.BusinessWorks5Language;
import com.tibco.sonar.plugins.bw5.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.InputModule;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import com.tibco.utils.bw5.model.Process;
import org.sonar.api.batch.sensor.SensorDescriptor;

//import org.sonar.api.resources.Project;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

/**
 * XmlSensor provides analysis of xml files.
 *
 * @author Matthijs Galesloot
 */
public class ProcessRuleSensor implements Sensor {

    private final static Logger LOG = Loggers.get(ProcessRuleSensor.class);
    private final Map<String, Process> servicetoprocess = new HashMap<>();
    protected List<Process> processList = new ArrayList<>();
    private final String processname = null;
    protected FileSystem fileSystem;
    protected String languageKey;
    protected InputModule project;
    protected SensorContext sensorContext;
    protected CheckFactory checkFactory;
    private final FilePredicate mainFilesPredicate;
    private final Checks<Object> checkReturned;
    protected static final Map<String, Integer> foundResources = new HashMap<>();

    public ProcessRuleSensor(FileSystem fileSystem,
            CheckFactory checkFactory) {
        LOG.debug("ProcessRuleSensor - START");

        this.fileSystem = fileSystem;
        checkReturned = checkFactory.create(ProcessRuleDefinition.REPOSITORY_KEY).addAnnotatedChecks(ProcessRuleDefinition.getChecks());
        this.mainFilesPredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasLanguage(BusinessWorks5Language.KEY));
        LOG.debug("ProcessRuleSensor - END");
    }

  protected void analyseFile(InputFile file) {
        LOG.debug("analyseFile - START");
        if (file != null) {
            ProcessSource sourceCode = new ProcessSource(file); // TODO:  Handle this better....
            com.tibco.utils.bw5.model.Process process = sourceCode.getProcessModel();         
            processList.add(process);

            for (Iterator<Object> it = checkReturned.all().iterator(); it.hasNext();) {
                AbstractCheck check = (AbstractCheck) it.next();
                if (check instanceof AbstractProcessCheck) {
                    RuleKey ruleKey = checkReturned.ruleKey(check);
                    check.setRuleKey(ruleKey);
                    check.scanFile(sensorContext, ruleKey, sourceCode);
                }
            }
        }
        LOG.debug("analyseFile - END");

    }
 
  
  
      protected void checkCustom() {
        Iterable<InputFile> files = fileSystem.inputFiles(fileSystem.predicates().hasType(InputFile.Type.MAIN));
        for (InputFile file : files) {
            try{
                XmlBw5Source xSource = new XmlBw5Source(file);
                for (Iterator<Object> it = checkReturned.all().iterator(); it.hasNext();) {
                    AbstractCheck check = (AbstractCheck) it.next();
                    if (check instanceof XPathCheck) {
                        LOG.debug("## XPathCheck detected: [" + check.getRuleKeyName() + "]");
                        XPathCheck xmlCheck = (XPathCheck) check;
                        RuleKey ruleKey = checkReturned.ruleKey(xmlCheck);
                        xmlCheck.setRuleKey(ruleKey);
                        xmlCheck.scanFile(sensorContext, ruleKey, xSource);
                    }
                }
            }catch(Exception ex){
               LOG.warn("Not able to handle this file as XML ["+file.filename()+"] ",ex);
            }

        }

    }
  

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    
    @Override
    public void execute(SensorContext context) {
        LOG.debug("execute - START");
        this.sensorContext = context;
        LOG.info("Starting ProcessRuleSensor");
        List<InputFile> inputFiles = new ArrayList<>();
        fileSystem.inputFiles(mainFilesPredicate).forEach(inputFiles::add);

        if (inputFiles.isEmpty()) {
            return;
        }

        LOG.info("Searching for BW5 PrcoessFiles");
        inputFiles.forEach(this::analyseFile);
        LOG.info("Completed Search of BW5 Resources");        
        checkCustom();
        LOG.debug("execute - END");
    }

    /*public boolean shouldExecuteOnProject(Project prjct) {
        return fileSystem.inputFiles(fileSystem.predicates().hasLanguage(languageKey)).iterator().hasNext();
    }*/

    @Override
    public void describe(SensorDescriptor descriptor) {
     
    }

}