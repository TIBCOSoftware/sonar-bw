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
package com.tibco.sonar.plugins.bw6.sensor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.fs.InputModule;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.tibco.sonar.plugins.bw6.check.AbstractCheck;
import com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterHttpCheck;
import com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterJDBCÇheck;
import com.tibco.sonar.plugins.bw6.check.process.CheckpointAfterRESTCheck;
import com.tibco.sonar.plugins.bw6.check.process.CheckpointInTransation;
import com.tibco.sonar.plugins.bw6.check.process.ChoiceOtherwiseCheck;
import com.tibco.sonar.plugins.bw6.check.process.CriticalSectionCheck;
import com.tibco.sonar.plugins.bw6.check.process.DeadLockCheck;
import com.tibco.sonar.plugins.bw6.check.process.ForEachGroupCheck;
import com.tibco.sonar.plugins.bw6.check.process.ForEachMappingCheck;
import com.tibco.sonar.plugins.bw6.check.process.JDBCHardCodeCheck;
import com.tibco.sonar.plugins.bw6.check.process.JDBCWildCardCheck;
import com.tibco.sonar.plugins.bw6.check.process.JMSAcknowledgementModeCheck;
import com.tibco.sonar.plugins.bw6.check.process.JMSHardCodeCheck;
import com.tibco.sonar.plugins.bw6.check.process.LogOnlyInSubprocessCheck;
import com.tibco.sonar.plugins.bw6.check.process.MultipleTransitionCheck;
import com.tibco.sonar.plugins.bw6.check.process.NoDescriptionCheck;
import com.tibco.sonar.plugins.bw6.check.process.NumberofActivitiesCheck;
import com.tibco.sonar.plugins.bw6.check.process.NumberofServicesCheck;
import com.tibco.sonar.plugins.bw6.check.process.SubProcessInlineCheck;
import com.tibco.sonar.plugins.bw6.check.process.TransitionLabelCheck;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.utils.bw.model.ModuleProperties;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Service;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;

/**
 * XmlSensor provides analysis of xml files.
 *
 * @author Kapil Shivarkar
 */
public class ProcessRuleSensor implements Sensor {

    private final static Logger LOG = Loggers.get(ProcessRuleSensor.class);
    private Map<String, Process> servicetoprocess = new HashMap<>();
    protected List<Process> processList = new ArrayList<>();
    private String processname = null;
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
        ArrayList<Object> allChecks = new ArrayList<>();
        allChecks.add(NoDescriptionCheck.class);
        allChecks.add(NumberofActivitiesCheck.class);
        allChecks.add(TransitionLabelCheck.class);
        allChecks.add(ChoiceOtherwiseCheck.class);
        allChecks.add(JDBCWildCardCheck.class);
        allChecks.add(JDBCHardCodeCheck.class);
        allChecks.add(MultipleTransitionCheck.class);
        allChecks.add(DeadLockCheck.class);
        allChecks.add(LogOnlyInSubprocessCheck.class);
        allChecks.add(JMSHardCodeCheck.class);
        allChecks.add(ForEachMappingCheck.class);
        allChecks.add(ForEachGroupCheck.class);
        allChecks.add(NumberofServicesCheck.class);
        allChecks.add(CheckpointAfterRESTCheck.class);
        allChecks.add(CheckpointAfterJDBCÇheck.class);
        allChecks.add(CheckpointAfterHttpCheck.class);
        allChecks.add(CheckpointInTransation.class);
        allChecks.add(JMSAcknowledgementModeCheck.class);
        allChecks.add(CriticalSectionCheck.class);
        allChecks.add(SubProcessInlineCheck.class);
        checkReturned = checkFactory.create(ProcessRuleDefinition.REPOSITORY_KEY).addAnnotatedChecks((Iterable<?>) allChecks);

        this.mainFilesPredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasLanguage(BWProcessLanguage.KEY));
        LOG.debug("ProcessRuleSensor - END");
    }

    public enum BWResources {
        HTTPClient,
        XMLAuthentication,
        WSSAuthentication,
        TrustProvider,
        ThrealPool,
        TCPConnection,
        SubjectProvider,
        SSLServerConfiguration,
        SSLClientConfiguration,
        SMTPResource,
        RendezvousTransport,
        ProxyConfiguration,
        LDAPAuthentication,
        KeystoreProvider,
        JNDIConfiguration,
        JMSConnection,
        JDBCConnection,
        JavaGlobalInstance,
        IdentityProvider,
        HTTPConnector,
        FTPConnection,
        FTLRealmServerConnection,
        DataFormat,
        SQLFile
    }

    protected void analyseFile(InputFile file) {
        LOG.debug("analyseFile - START");
        if (file != null) {
            ProcessSource sourceCode = new ProcessSource(file); // TODO:  Handle this better....
            Process process = sourceCode.getProcessModel();
            process.startParsing();
            checkSubprocess(process);
            processList.add(process);

            for (Iterator<Object> it = checkReturned.all().iterator(); it.hasNext();) {
                AbstractCheck check = (AbstractCheck) it.next();
                if (!(check instanceof DeadLockCheck)) {
                    RuleKey ruleKey = checkReturned.ruleKey(check);
                    check.setRuleKey(ruleKey);
                    check.scanFile(sensorContext, ruleKey, sourceCode);
                }
            }
        }
        LOG.debug("analyseFile - END");

    }

    public void checkSubprocess(Process process) {
        LOG.debug("checkSubprocess - START");
        if(process != null){
            LOG.debug("Checking if process is a subprocess: "+process.getName());
            File file = new File("META-INF/module.bwm");
            LOG.debug("File location: "+file.getAbsolutePath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            NodeList propertyList = null;
            boolean flag = true;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();
                propertyList = doc.getElementsByTagName("sca:component");
                if(propertyList != null){
                    for (int i = 0; i < propertyList.getLength(); i++) {
                        if (process.getName().equals(propertyList.item(i).getChildNodes().item(1).getAttributes().getNamedItem("processName").getNodeValue())) {
                            flag = false;
                            break;
                        }
                    }
                }
                LOG.debug("Process detected as subprocess: "+flag);
                process.setSubProcess(flag);
            } catch (ParserConfigurationException e) {
                LOG.error(e.getMessage());
            } catch (SAXException e) {
                LOG.error(e.getMessage());
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.debug("checkSubprocess - END");

    }

    protected void analyseDeadLock() {
        LOG.debug("analyseDeadLock - START");
        for (Iterator<Process> iterator = processList.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            LOG.debug("Adding service to process references for process: " + process.getName());
            Map<String, Service> services = process.getServices();
            if (services != null) {
                for (String servicename : services.keySet()) {
                    LOG.debug("Detected Service Name: " + servicename);
                    String key = servicename + "-" + services.get(servicename).getNamespace() + "-" + process.getName();
                    servicetoprocess.put(key, process);
                }
            }
        }
        //------All set ready to go
        for (Iterator<Process> iterator = processList.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            String baseName = process.getBasename();
            for (InputFile file : fileSystem.inputFiles(fileSystem.predicates().hasLanguage(languageKey))) {
                if (file.filename().equals(baseName)) {
                    LOG.debug("Finding deadlock for process: " + process.getName());
                    ProcessSource sourceCode = new ProcessSource(file);
                    findDeadLock(process.getServices(), process.getProcessReferenceServices(), process, sourceCode, file);
                }
            }
        }
        LOG.debug("analyseDeadLock - END");
    }

    public final void reportIssueOnFile(RuleKey ruleKey, InputFile inputFile, String message, int line) {
        LOG.debug("reportIssueOnFile - START");
        LOG.info("Issue reported on file ["+ inputFile.filename() + "] with message ["+ message +"]");
        NewIssue issue = sensorContext.newIssue();
        NewIssueLocation location = issue.newLocation()
                .on(inputFile)
                .message(message);

        NewIssueLocation secondary = issue.newLocation()
                .on(inputFile)
                .at(inputFile.selectLine(line));
        issue.addLocation(secondary);

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
        LOG.debug("reportIssueOnFile - END");
    }

    public void findDeadLock(Map<String, Service> services, Map<String, Service> referencedServices, Process process, ProcessSource sourceCode, InputFile resource) {
        LOG.debug("findDeadLock - START");
        if (services != null && services.size() > 0 && referencedServices != null && referencedServices.size() > 0) {
            Set<String> serviceName = services.keySet();
            Set<String> referenceServiceName = referencedServices.keySet();
            Set<String> dupReferencedServiceName = new HashSet<>(referenceServiceName);
            dupReferencedServiceName.retainAll(serviceName);
            if (dupReferencedServiceName.size() > 0) {
                String[] deadlockedService = dupReferencedServiceName.toArray(new String[dupReferencedServiceName.size()]);
                if(deadlockedService != null && deadlockedService.length > 0){
                    String referencedServiceNameSpace = referencedServices.get(deadlockedService[0]).getNamespace();
                    LOG.debug("Referenced service namespace: "+referencedServiceNameSpace);
                    String referenceProcessName = referencedServices.get(deadlockedService[0]).getImplementationProcess();
                    LOG.debug("Reference process name: "+referenceProcessName);
                    String serviceNamespace = services.get(deadlockedService[0]).getNamespace();
                    LOG.debug("Service namespace: "+serviceNamespace);                    
                    String proc2 = process.getName();
                    LOG.debug("Process Name: "+proc2);

                    if (referencedServiceNameSpace.equals(serviceNamespace) && referenceProcessName != null && proc2.equals(referenceProcessName)) {                        
                        
                        LOG.debug("Reference Process Name is equal to Process Name");
                        for (Object checkObject : checkReturned.all()) {
                            AbstractCheck check = (AbstractCheck) checkObject;
                            if (check instanceof DeadLockCheck) {
                                RuleKey ruleKey = checkReturned.ruleKey(check);
                                check.setRuleKey(ruleKey);
                                proc2 = process.getBasename();
                                if (processname == null) {                                    
                                    reportIssueOnFile(check.getRuleKey(), resource, "There is a very high possibility of deadlock in the implementation of service " + deadlockedService[0] + " exposed by process " + proc2, 1);

                                } else {
                                    reportIssueOnFile(check.getRuleKey(), resource, "Deadlock is detected between processes " + proc2 + " and " + processname + ". There is a very high possibility of deadlock in the implementation of service " + deadlockedService[0] + " exposed by process " + proc2 + " and consumed by process " + processname, 1);
                                }

                            }
                        }
                    } else {
                        for (String name : referenceServiceName) {
                            Process proc = servicetoprocess.get(name + "-" + referencedServices.get(name).getNamespace() + "-" + referencedServices.get(name).getImplementationProcess());
                            if (proc.getProcessReferenceServices() != null) {
                                processname = proc.getBasename();
                                findDeadLock(services, proc.getProcessReferenceServices(), process, sourceCode, resource);
                            }
                        }
                    }
                }
            } else {
                for (String name : referenceServiceName) {
                    Process proc = servicetoprocess.get(name + "-" + referencedServices.get(name).getNamespace() + "-" + referencedServices.get(name).getImplementationProcess());
                    if (proc != null && proc.getProcessReferenceServices() != null) {
                        processname = proc.getBasename();
                        findDeadLock(services, proc.getProcessReferenceServices(), process, sourceCode, resource);
                    }
                }
            }
        }
        LOG.debug("findDeadLock - END");
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

        LOG.info("Searching for BW6 PrcoessFiles");
        inputFiles.forEach(this::analyseFile);
        LOG.info("Completed Search of BW6 Resources");        
        LOG.debug("execute - END");
    }

  

    public int getPropertiesCount(SensorContext context, final String fileExtension) {
        LOG.debug("getPropertiesCount - START");
        String projectPath = context.config().get("sonar.sources").orElse("./");
        LOG.debug("Loading properties from Project folder" + projectPath);

        File dir = new File(projectPath + "/META-INF");
        File[] files = dir.listFiles((File dir1, String filename) -> filename.endsWith(fileExtension));

        ModuleProperties moduleprops = new ModuleProperties(files[0]);
        LOG.debug("getPropertiesCount - END");
        if (fileExtension.equals(".jsv")) {
            return moduleprops.getPropertiesCount("jobSharedVariable");
        } else if (fileExtension.equals(".bwm")) {
            return moduleprops.getPropertiesCount("sca:property");
        } else {
            return moduleprops.getPropertiesCount("moduleSharedVariable");
        }
        
    }

    public int getModulePropertiesCount() {
        LOG.debug("getModulePropertiesCount - START");
        File dir = new File("META-INF");
        File[] files = dir.listFiles((File dir1, String filename) -> filename.endsWith(".bwm"));
        ModuleProperties moduleprops = new ModuleProperties(files[0]);
        LOG.debug("getModulePropertiesCount - END");
        return moduleprops.getPropertiesCount("sca:property");
    }

    /**
     * This sensor only executes on projects with active XML rules.
     * @param inputModule
     * @return 
     */
    public boolean shouldExecuteOnProject(InputModule inputModule) {
        return fileSystem.inputFiles(fileSystem.predicates().hasLanguage(languageKey)).iterator().hasNext();

    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void describe(SensorDescriptor arg0) {
        // TODO Auto-generated method stub

    }

}
