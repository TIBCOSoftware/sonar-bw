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
import java.io.FilenameFilter;
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
import org.sonar.api.batch.fs.InputComponent;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.measures.Metric;
import org.sonar.api.profiles.RulesProfile;
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
import com.tibco.sonar.plugins.bw6.metric.BusinessWorksMetrics;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.violation.DefaultViolation;
import com.tibco.sonar.plugins.bw6.violation.Violation;
import com.tibco.utils.bw.model.ModuleProperties;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Service;
import java.util.Collection;
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

    private Map<String, Process> servicetoprocess = new HashMap<String, Process>();
    protected List<Process> processList = new ArrayList<Process>();
    private String processname = null;
    protected FileSystem fileSystem;
    protected String languageKey;
    protected InputModule project;
    protected SensorContext sensorContext;
    protected CheckFactory checkFactory;
    private final FilePredicate mainFilesPredicate;
    private final Checks<Object> checkReturned;

    
    
    public ProcessRuleSensor(FileSystem fileSystem,
            CheckFactory checkFactory ) {
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

    }

    private static final Logger LOG = Loggers.get(ProcessRuleSensor.class);


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
                check.scanFile(sensorContext,ruleKey,sourceCode);
            }
        }
        

    }

    public void checkSubprocess(Process process) {
        File file = new File(System.getProperty("sonar.sources") + "/META-INF/module.bwm");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        NodeList propertyList = null;
        boolean flag = true;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            propertyList = doc.getElementsByTagName("sca:component");
            for (int i = 0; i < propertyList.getLength(); i++) {
                if (process.getName().equals(propertyList.item(i).getChildNodes().item(1).getAttributes().getNamedItem("processName").getNodeValue())) {
                    flag = false;
                    break;
                }
            }
            process.setSubProcess(flag);
        } catch (ParserConfigurationException e) {
            LOG.error(e.getMessage());
        } catch (SAXException e) {
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }

    }

    protected void analyseDeadLock(Iterable<InputFile> filesIterable) {
        for (int i = 0; i < processList.size(); i++) {
            Map<String, Service> services = processList.get(i).getServices();
            for (String servicename : services.keySet()) {
                String key = servicename + "-" + services.get(servicename).getNamespace() + "-" + processList.get(i).getName();
                servicetoprocess.put(key, processList.get(i));
            }
        }
        //------All set ready to go
        for (Iterator<Process> iterator = processList.iterator(); iterator.hasNext();) {
            Process process = iterator.next();
            String proc1 = process.getName();
            proc1 = proc1.substring(proc1.lastIndexOf(".") + 1).concat(".bwp");
            for (InputFile file : fileSystem.inputFiles(fileSystem.predicates().hasLanguage(languageKey))) {
                if (file.filename().equals(proc1)) {
                    ProcessSource sourceCode = new ProcessSource(file);
                    findDeadLock(process.getServices(), process.getProcessReferenceServices(), process, sourceCode, file);
                }
            }
        }
    }

    
    public final void reportIssueOnFile(RuleKey ruleKey, InputFile inputFile, String message, int line) {
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
    }
    
    public void findDeadLock(Map<String, Service> services, Map<String, Service> referencedServices, Process process, ProcessSource sourceCode, InputFile resource) {
        if (services.size() > 0 && referencedServices.size() > 0) {
            Set<String> serviceName = services.keySet();
            Set<String> referenceServiceName = referencedServices.keySet();
            Set<String> dupReferencedServiceName = new HashSet<String>(referenceServiceName);
            dupReferencedServiceName.retainAll(serviceName);
            if (dupReferencedServiceName.size() > 0) {
                String[] deadlockedService = dupReferencedServiceName.toArray(new String[dupReferencedServiceName.size()]);
                String referencedServiceNameSpace = referencedServices.get(deadlockedService[0]).getNamespace();
                String serviceNamespace = services.get(deadlockedService[0]).getNamespace();
                String referenceProcessName = referencedServices.get(deadlockedService[0]).getImplementationProcess();
                String proc2 = process.getName();

                if (referencedServiceNameSpace.equals(serviceNamespace) && referenceProcessName != null && proc2.equals(referenceProcessName)) {
                    for (Object checkObject : checkReturned.all()) {
                        AbstractCheck check = (AbstractCheck) checkObject;
                        if (check instanceof DeadLockCheck) {
                            RuleKey ruleKey = checkReturned.ruleKey(check);
                            check.setRuleKey(ruleKey);
                            proc2 = proc2.substring(proc2.lastIndexOf(".") + 1).concat(".bwp");
                            if (processname == null) {
                                reportIssueOnFile(check.getRuleKey(),resource,"There is a very high possibility of deadlock in the implementation of service " + deadlockedService[0] + " exposed by process " + proc2,1);
    

                            } else {
                               reportIssueOnFile(check.getRuleKey(),resource,"Deadlock is detected between processes " + proc2 + " and " + processname + ". There is a very high possibility of deadlock in the implementation of service " + deadlockedService[0] + " exposed by process " + proc2 + " and consumed by process " + processname,1);
                            }

                        }
                    }
                } else {
                    for (String name : referenceServiceName) {
                        Process proc = servicetoprocess.get(name + "-" + referencedServices.get(name).getNamespace() + "-" + referencedServices.get(name).getImplementationProcess());
                        if (proc.getProcessReferenceServices() != null) {
                            processname = null;
                            processname = proc.getName();
                            processname = processname.substring(processname.lastIndexOf(".") + 1).concat(".bwp");
                            findDeadLock(services, proc.getProcessReferenceServices(), process, sourceCode, resource);
                        }
                    }
                }
            } else {
                for (String name : referenceServiceName) {
                    Process proc = servicetoprocess.get(name + "-" + referencedServices.get(name).getNamespace() + "-" + referencedServices.get(name).getImplementationProcess());
                    if (proc != null && proc.getProcessReferenceServices() != null) {
                        processname = null;
                        processname = proc.getName();
                        processname = processname.substring(processname.lastIndexOf(".") + 1).concat(".bwp");
                        findDeadLock(services, proc.getProcessReferenceServices(), process, sourceCode, resource);
                    }
                }

            }
        }
    }

    @Override
    public void execute(SensorContext context) {
        
        
        this.sensorContext = context;

        Loggers.get(getClass()).info("Starting ProcessRuleSensor");
        //createResourceExtensionMapper(resourceExtensionMapper);
        List<InputFile> inputFiles = new ArrayList<>();
        fileSystem.inputFiles(mainFilesPredicate).forEach(inputFiles::add);

        if (inputFiles.isEmpty()) {
            return;
        }

        Loggers.get(getClass()).info("Searching for BW6 PrcoessFiles");
        for (InputFile file : inputFiles) {
            analyseFile(file);
        }
        Loggers.get(getClass()).info("Completed Search of BW6 Resources");
        Loggers.get(getClass()).info("ProcessRuleSensor End");
    }

    private void saveMeasure(final InputComponent inputComponent, SensorContext context, Metric metric, Integer value) {

        context.newMeasure()
                .forMetric(metric)
                .withValue(value)
                .on(inputComponent)
                .save();
    }

    public static Map<String, Integer> foundResources = new HashMap<String, Integer>();

    

    public int getPropertiesCount(SensorContext context, final String fileExtension) {

        String projectPath = context.config().get("sonar.sources").orElse("./");
        Loggers.get(getClass()).info("Loading properties from Project folder" + projectPath);

        File dir = new File(projectPath + "/META-INF");

        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(fileExtension);
            }
        });

        ModuleProperties moduleprops = new ModuleProperties(files[0]);
        if (fileExtension.equals(".jsv")) {
            return moduleprops.getPropertiesCount("jobSharedVariable");
        } else if (fileExtension.equals(".bwm")) {
            return moduleprops.getPropertiesCount("sca:property");
        } else {
            return moduleprops.getPropertiesCount("moduleSharedVariable");
        }
    }

    public int getModulePropertiesCount() {
        File dir = new File(System.getProperty("sonar.sources") + "/META-INF");
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".bwm");
            }
        });
        ModuleProperties moduleprops = new ModuleProperties(files[0]);
        return moduleprops.getPropertiesCount("sca:property");
    }

    /**
     * This sensor only executes on projects with active XML rules.
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
