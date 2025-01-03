/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.sensor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.scanner.fs.InputProject;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.tibco.sonar.plugins.bw.check.AbstractCheck;
import com.tibco.sonar.plugins.bw6.check.AbstractProcessCheck;
import com.tibco.sonar.plugins.bw6.check.AbstractProjectCheck;
import com.tibco.sonar.plugins.bw6.check.AbstractResourceCheck;
import com.tibco.sonar.plugins.bw6.check.XPathCheck;
import com.tibco.sonar.plugins.bw6.check.process.DeadLockCheck;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.source.ProjectSource;
import com.tibco.sonar.plugins.bw6.source.SharedResourceSource;
import com.tibco.sonar.plugins.bw.source.XmlSource;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.bw6.model.GenericResource;
import com.tibco.utils.bw6.model.JSONResource;
import com.tibco.utils.bw6.model.JobSharedVariables;
import com.tibco.utils.bw6.model.ModuleProperties;
import com.tibco.utils.bw6.model.ModuleSharedVariables;
import com.tibco.utils.bw6.model.Process;
import com.tibco.utils.bw6.model.Project;
import com.tibco.utils.bw6.model.SharedResource;
import com.tibco.utils.bw6.model.SharedResourceParameter;
import com.tibco.utils.bw6.model.WsdlResource;
import com.tibco.utils.bw6.model.XsdResource;
import java.util.Arrays;
import java.util.jar.Manifest;

import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.measure.Metric;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.measures.CoreMetrics;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.w3c.dom.Element;

/**
 * XmlSensor provides analysis of xml files.
 *
 * @author TIBCODX Toolkit
 */
public class ProcessRuleSensor implements Sensor {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessRuleSensor.class);
    protected FileSystem fileSystem;
    protected String languageKey;
    protected SensorContext sensorContext;
    protected CheckFactory checkFactory;
    private final FilePredicate mainFilesPredicate;
    private final Checks<Object> checkReturned;
    protected static Map<String, Integer> foundResources = new HashMap<>();
    protected static Map<String, String> resourceExtensionMapper = new HashMap<>();

    public ProcessRuleSensor(FileSystem fileSystem,
            CheckFactory checkFactory) {
        LOG.debug("ProcessRuleSensor - START");

        this.fileSystem = fileSystem;
        checkReturned = checkFactory.create(ProcessRuleDefinition.REPOSITORY_KEY).addAnnotatedChecks(ProcessRuleDefinition.getChecks());

        this.mainFilesPredicate = fileSystem.predicates().and(
                fileSystem.predicates().hasLanguage(BWProcessLanguage.KEY));
        LOG.debug("ProcessRuleSensor - END");
    }

    protected void analyzeProcess(ProjectSource project) {
        LOG.debug("analyzeProcess - START");
        if (project != null) {
            for (ProcessSource sourceCode : project.getProcess()) {
                for (Iterator<Object> it = checkReturned.all().iterator(); it.hasNext();) {
                    AbstractCheck check = (AbstractCheck) it.next();
                    if (!(check instanceof DeadLockCheck) && check instanceof AbstractProcessCheck) {
                        RuleKey ruleKey = checkReturned.ruleKey(check);
                        check.setRuleKey(ruleKey);
                        check.scanFile(sensorContext, ruleKey, sourceCode);
                    }
                }
            }
        }
        LOG.debug("analyzeProcess - END");

    }

    protected void analyzeProject(ProjectSource projectSource) {
        LOG.debug("analyzeProject - START");
        if (projectSource != null) {

            for (Iterator<Object> it = checkReturned.all().iterator(); it.hasNext();) {
                AbstractCheck check = (AbstractCheck) it.next();
                if (check instanceof AbstractProjectCheck) {
                    LOG.debug("analyzeProject - Check  [" + check.getRuleKeyName() + "]");
                    RuleKey ruleKey = checkReturned.ruleKey(check);
                    check.setRuleKey(ruleKey);
                    check.scanFile(sensorContext, ruleKey, projectSource);
                }
            }
        }
        LOG.debug("analyseFile - END");

    }

    private void checkSubprocess(Process process) {
        LOG.debug("checkSubprocess - START");
        if (process != null) {
            LOG.debug("Checking if process is a subprocess: " + process.getName());
            File file = new File("META-INF/module.bwm");
            LOG.debug("File location: " + file.getAbsolutePath());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newDefaultInstance();       
            DocumentBuilder dBuilder;
            NodeList propertyList = null;
            boolean flag = true;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(file);
                doc.getDocumentElement().normalize();
                propertyList = doc.getElementsByTagName("sca:component");
                if (propertyList != null) {
                    for (int i = 0; i < propertyList.getLength(); i++) {
                        if (process.getName() != null && process.getName().equals(propertyList.item(i).getChildNodes().item(1).getAttributes().getNamedItem("processName").getNodeValue())) {
                            flag = false;
                            break;
                        }
                    }
                }
                LOG.debug("Process detected as subprocess: " + flag);
                process.setSubProcess(flag);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                LOG.error(e.getMessage());
            }
        }
        LOG.debug("checkSubprocess - END");

    }


    public final void reportIssueOnFile(RuleKey ruleKey, InputFile inputFile, String message, int line) {
        LOG.debug("reportIssueOnFile - START");
        LOG.info("Issue reported on file [" + inputFile.filename() + "] with message [" + message + "]");
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



    @Override
    public void execute(SensorContext context) {
        LOG.debug("execute - START");
        this.sensorContext = context;

        LOG.info("Start of parsing + analyze + other utilities check");
        List<InputFile> inputFiles = new ArrayList<>();

        Project bwProject = new Project();
        InputProject projectInputFile = context.project();
        ProjectSource projectSource = new ProjectSource(projectInputFile);
        projectSource.setProject(bwProject);
        bwProject.setFile(fileSystem.baseDir());

        LOG.debug("Project Input File: " + projectInputFile);

        parseProjectProperties(bwProject);
        parseProcesses(inputFiles, projectSource, bwProject);
        parseResources(bwProject, projectSource, context);

        bwProject.parseBindings();
        bwProject.parsePolicies();
        bwProject.parseKeystores();

        analyzeProject(projectSource);
        analyzeProcess(projectSource);

        checkResources(projectSource);
        checkCustom();

        LOG.info("End of parsing + analyze + other utilities check");
        LOG.debug("execute - END");
    }

    protected void parseResources(Project bwProject, ProjectSource projectSource, SensorContext context) {
        createResourceExtensionMapper(resourceExtensionMapper);
        Iterable<InputFile> files = fileSystem.inputFiles(fileSystem.predicates().hasType(InputFile.Type.MAIN));

        LOG.info("Searching for BW Resources");
        for (InputFile file : files) {
            LOG.info("Found File: " + file.filename());
            if (file.filename().lastIndexOf(".") > 0) {
                String extension = file.filename().substring(file.filename().lastIndexOf("."));
                LOG.debug("Extension for file: " + extension);

                parseAdditionalResources(bwProject, projectSource, file, extension);

                String resourceType = resourceExtensionMapper.get(extension);
                LOG.debug("Resource Type for file: " + resourceType);
                if (resourceType != null) {
                    parseMainResourceObject(bwProject, projectSource, context, file, resourceType);
                }else{
                    parseGenericResourceObject(bwProject, projectSource, file);
                }
            }
            
            try{
            context.<Integer>newMeasure()
                            .forMetric(CoreMetrics.NCLOC)
                            .on(file)
                            .withValue(file.lines())
                            .save();
            }catch(Exception ex){
                LOG.warn("Already registered the following measure: ",ex);
            }
        }
        LOG.info("Completed Search of BW6 Resources");
    }

    private static void parseGenericResourceObject(Project bwProject, ProjectSource projectSource, InputFile file) {
        if("pom.xml".equals(file.filename())){
            parsePOMResource(bwProject, file);
        }

        if("MANIFEST.MF".equals(file.filename())){
            parseManifestResource(bwProject, file);
        }

        GenericResource resource = new GenericResource();
        bwProject.addOtherFile(resource);
        projectSource.getMap().addFile(resource, file);
    }

    private void parseMainResourceObject(Project bwProject, ProjectSource projectSource, SensorContext context, InputFile file, String resourceType) {
        LOG.info("Found BW6 Resource " + resourceType + " " + file.filename());
        SharedResourceSource sourceCode = new SharedResourceSource(projectSource, fileSystem, file);
        SharedResource resource = sourceCode.getResource();
        resource.setProject(bwProject);
        bwProject.addResource(resource);
        resource.parse();
        parseResourceRequireParameters(resourceType, resource);
        context.<Integer>newMeasure()
                .forMetric(getSharedResourceMetric(resourceType))
                .on(file)
                .withValue(1)
                .save();
    }

    private static void parseManifestResource(Project bwProject, InputFile file) {
        LOG.debug("Detected MANIFEST.MF");
        try{
            Manifest xmanifest = new Manifest(file.inputStream());
            bwProject.setManifest(xmanifest);

        }catch(IOException ex){
               LOG.error("Error reading the MANIFEST.MF file",ex);
        }
    }

    private static void parsePOMResource(Project bwProject, InputFile file) {
        LOG.debug("Detected pom.xml file --> This is a maven project");
        try{
            XmlFile xFile = XmlFile.create(file);
            bwProject.setPomFile(xFile.getDocument());
        }catch(IOException ex){
            LOG.debug("Added schema to project: " + file.filename());
        }
    }

    private void parseResourceRequireParameters(String resourceType, SharedResource resource) {
        List<String> parameterRequired = getRequiredParameters(resourceType);
        if (parameterRequired != null) {
            for (String parameter : parameterRequired) {
                SharedResourceParameter param = resource.getParameterByName(parameter);
                if (param != null) {
                    LOG.debug("Setting as required parameter [" + param.getName() + "] for resources Type [" + resourceType + "]");
                    param.setRequired(true);
                }
            }
        }
    }

    private void parseAdditionalResources(Project bwProject, ProjectSource projectSource, InputFile file, String extension) {
        if (".xsd".equals(extension)) {
            parseXSDResource(bwProject, projectSource, file);
        } else if (".wsdl".equals(extension)) {
            parseWSDLResource(bwProject, projectSource, file);
        } else if (".bwt".equals(extension)) {
            parseBWTResource(bwProject, file);
        } else if (".json".equals(extension)) {
            LOG.debug("JSON file deteected");
            JSONResource resource = new JSONResource();
            bwProject.addJSONSchema(resource);
            projectSource.getMap().addFile(resource, file);
        }
    }

    private void parseBWTResource(Project bwProject, InputFile file) {
        try {
            LOG.debug("Test file deteected");
            XmlFile xFile = XmlFile.create(file);
            List<String> testFiles = parseTest(xFile.getDocument());
            if (testFiles != null) {
                for (String process : testFiles) {
                    Process procObj = bwProject.getProcessByName(process);
                    if (procObj != null) {
                        LOG.debug("Process Name recovered by name: " + procObj.getName());
                        procObj.setHasTest(true);
                    }
                }
            }
        } catch (Exception ex) {
            LOG.warn("Error parsing BWT resource: " + file.filename());
        }
    }

    private static void parseWSDLResource(Project bwProject, ProjectSource projectSource, InputFile file) {
        try {
            XmlFile xFile = XmlFile.create(file);
            WsdlResource resource = bwProject.addWSDL(xFile.getDocument());
            projectSource.getMap().addFile(resource, xFile.getInputFile());
            LOG.debug("Added service descriptor to project: " + file.filename());
        } catch (Exception ex) {
            LOG.warn("Error parsing WSDL resource: " + file.filename());
        }
    }

    private static void parseXSDResource(Project bwProject, ProjectSource projectSource, InputFile file) {
        try {
            XmlFile xFile = XmlFile.create(file);
            XsdResource resource = bwProject.addSchema(xFile.getDocument());
            projectSource.getMap().addFile(resource, xFile.getInputFile());
            LOG.debug("Added schema to project: " + file.filename());
        } catch (Exception ex) {
            LOG.warn("Error parsing XSD resource: " + file.filename());
        }
    }

    protected void parseProcesses(List<InputFile> inputFiles, ProjectSource projectSource, Project bwProject) {
        fileSystem.inputFiles(mainFilesPredicate).forEach(inputFiles::add);

        LOG.info("Searching for BW Process files");
        inputFiles.forEach(inputFile -> {
            ProcessSource sourceCode = new ProcessSource(projectSource, inputFile);
            Process process = sourceCode.getProcessModel();
            if(process != null){
                checkSubprocess(process);
                process.setProject(bwProject);
                bwProject.addProcess(process);
            }else{
                LOG.warn("Process couldn't be parsed from file: "+ inputFiles);
            }
        });
        LOG.info("Searching for BW Process files... DONE!");

        //Parse additional files
    }

    protected void checkResources(ProjectSource projectSource) {
        List<SharedResourceSource> resourceSourceList = projectSource.getResource();
        if (resourceSourceList != null) {
            for (SharedResourceSource sourceCode : resourceSourceList) {
                for (Iterator<Object> it = checkReturned.all().iterator(); it.hasNext();) {
                    AbstractCheck check = (AbstractCheck) it.next();
                    if (check instanceof AbstractResourceCheck) {
                        RuleKey ruleKey = checkReturned.ruleKey(check);
                        check.setRuleKey(ruleKey);
                        check.scanFile(sensorContext, ruleKey, sourceCode);
                    }
                }
            }
        }
    }

    protected void checkCustom() {
        Iterable<InputFile> files = fileSystem.inputFiles(fileSystem.predicates().hasType(InputFile.Type.MAIN));
        for (InputFile file : files) {
            try{
                if(XmlHelper.isXML(file.contents())) {
                    XmlSource xSource = new XmlSource(file);
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
                }
            }catch(Exception ex){
               LOG.warn("Not able to handle this file as XML ["+file.filename()+"] ",ex);
            }

        }

    }

    private Metric<Integer> getSharedResourceMetric(String resourceType) {
        switch (resourceType) {
            case SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT_KEY:
                return SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT;
            case SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION;
            case SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION;
            case SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER_KEY:
                return SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER;
            case SharedResourceMetrics.BWRESOURCES_THREAD_POOL_KEY:
                return SharedResourceMetrics.BWRESOURCES_THREAD_POOL;
            case SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION_KEY:
                return SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION;
            case SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER_KEY:
                return SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER;
            case SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION;
            case SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION;
            case SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE_KEY:
                return SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE;
            case SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT_KEY:
                return SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT;
            case SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION;
            case SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION;
            case SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER_KEY:
                return SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER;
            case SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION_KEY:
                return SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION;
            case SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION_KEY:
                return SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION;
            case SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION_KEY:
                return SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION;
            case SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY:
                return SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE;
            case SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER_KEY:
                return SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER;
            case SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR_KEY:
                return SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR;
            case SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION_KEY:
                return SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION;
            case SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY:
                return SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION;
            case SharedResourceMetrics.BWRESOURCES_DATA_FORMAT_KEY:
                return SharedResourceMetrics.BWRESOURCES_DATA_FORMAT;
            case SharedResourceMetrics.BWRESOURCES_SQL_FILE_KEY:
                return SharedResourceMetrics.BWRESOURCES_SQL_FILE;
            default:
                return SharedResourceMetrics.BWRESOURCES_UNKNOWN;
        }
    }

    private List<String> getRequiredParameters(String resourceType) {
        String[] out = new String[]{};
        switch (resourceType) {
            case SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT_KEY:
                out = new String[]{"tcpDetails_port", "tcpDetails_host", "maxTotalConnections", "idleConnectionTimeout", "maxTotalConnectionsPerHost"};
                break;
            case SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION_KEY:
            case SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION_KEY:
            case SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER_KEY:
            case SharedResourceMetrics.BWRESOURCES_THREAD_POOL_KEY:
            case SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION_KEY:
            case SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER_KEY:
            case SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY:
            case SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY:
            case SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE_KEY:
            case SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT_KEY:
            case SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION_KEY:
            case SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION_KEY:
                out = new String[]{"serverURL", "connectionPools", "searchTimeOut", "userSearchExpression"};
                break;
            case SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER_KEY:
            case SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION_KEY:
            case SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION_KEY:
                out = new String[]{"providerURL"};
                break;
            case SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION_KEY:
            case SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY:
            case SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER_KEY:
            case SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR_KEY:
            case SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION_KEY:
            case SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY:
            case SharedResourceMetrics.BWRESOURCES_DATA_FORMAT_KEY:
            case SharedResourceMetrics.BWRESOURCES_SQL_FILE_KEY:
            default:
                break;
        }

        return Arrays.asList(out);
    }

    private void parseProjectProperties(Project project) {

        parseModuleProperties(project);
        parseModuleSharedVariables(project);
        parseJobSharedVariables(project);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
      descriptor
        .onlyOnLanguage(BWProcessLanguage.KEY)
        .name("ProcessRuleSensor");
    }

    private void parseModuleProperties(Project project) {
        LOG.debug("parseModuleProperties - START");
        InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().hasExtension("bwm"));
        LOG.debug("Input file for parseModuleProperties: " + inputFile);
        try {
            if (inputFile != null) {
                LOG.debug("Input filename: " + inputFile.filename());
                XmlFile file = XmlFile.create(inputFile);
                project.setBwmDocument(file.getDocument());
                ModuleProperties moduleprops = new ModuleProperties(file.getDocument());
                LOG.debug("Property List : " + moduleprops.getPropertyList().size());
                project.setProperties(moduleprops);
            }
        } catch (IOException ex) {
            LOG.warn("Error parsing module properties: ", ex);
        }

        LOG.debug("parseModuleProperties - END");
    }

    private void parseModuleSharedVariables(Project project) {
        LOG.debug("parseModuleSharedVariables - START");
        InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().hasExtension("msv"));
        LOG.debug("Input file for parseModuleSharedVariables: " + inputFile);
        try {
            if (inputFile != null) {
                LOG.debug("Input filename for parseModuleSharedVariables: " + inputFile.filename());
                XmlFile file = XmlFile.create(inputFile);
                ModuleSharedVariables moduleprops = new ModuleSharedVariables(file.getDocument());
                project.setModuleSharedVariables(moduleprops);
            }
        } catch (IOException ex) {
            LOG.warn("Error parsing module shared variables: ", ex);
        }
        LOG.debug("parseModuleSharedVariables - END");
    }

    private void parseJobSharedVariables(Project project) {
        LOG.debug("parseJobSharedVariables - START");
        InputFile inputFile = fileSystem.inputFile(fileSystem.predicates().hasExtension("jsv"));
        LOG.debug("Input file for parseJobSharedVariables: " + inputFile);
        try {
            if (inputFile != null) {
                LOG.debug("Input filename for parseJobSharedVariables: " + inputFile.filename());
                XmlFile file = XmlFile.create(inputFile);
                JobSharedVariables moduleprops = new JobSharedVariables(file.getDocument());
                project.setJobSharedVariables(moduleprops);
            }
        } catch (IOException ex) {
            LOG.warn("Error parsing job shared properties: ", ex);
        }
        LOG.debug("parseJobSharedVariables - END");
    }

    public void createResourceExtensionMapper(Map<String, String> resourceExtensionMapper) {
        resourceExtensionMapper.put(".httpClientResource", SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT_KEY);
        resourceExtensionMapper.put(".authxml", SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION_KEY);
        resourceExtensionMapper.put(".wssResource", SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION_KEY);
        resourceExtensionMapper.put(".trustResource", SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER_KEY);
        resourceExtensionMapper.put(".threadPoolResource", SharedResourceMetrics.BWRESOURCES_THREAD_POOL_KEY);
        resourceExtensionMapper.put(".tcpResource", SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION_KEY);
        resourceExtensionMapper.put(".sipResource", SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER_KEY);
        resourceExtensionMapper.put(".sslServerResource", SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY);
        resourceExtensionMapper.put(".sslClientResource", SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY);
        resourceExtensionMapper.put(".smtpResource", SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE_KEY);
        resourceExtensionMapper.put(".rvResource", SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT_KEY);
        resourceExtensionMapper.put(".httpProxyResource", SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION_KEY);
        resourceExtensionMapper.put(".ldapResource", SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION_KEY);
        resourceExtensionMapper.put(".keystoreProviderResource", SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER_KEY);
        resourceExtensionMapper.put(".jndiConfigResource", SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION_KEY);
        resourceExtensionMapper.put(".jmsConnResource", SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION_KEY);
        resourceExtensionMapper.put(".jdbcResource", SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION_KEY);
        resourceExtensionMapper.put(".javaGlobalInstanceResource", SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY);
        resourceExtensionMapper.put(".userIdResource", SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER_KEY);
        resourceExtensionMapper.put(".httpConnResource", SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR_KEY);
        resourceExtensionMapper.put(".ftpResource", SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION_KEY);
        resourceExtensionMapper.put(".ftlResource", SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY);
        resourceExtensionMapper.put(".dataFormatResource", SharedResourceMetrics.BWRESOURCES_DATA_FORMAT_KEY);
        resourceExtensionMapper.put(".sql", SharedResourceMetrics.BWRESOURCES_SQL_FILE_KEY);
    }

    private List<String> parseTest(Document document) {
        List<String> out = new ArrayList<>();
        if (document != null) {
            NodeList processNodeNodeList = document.getElementsByTagName("ProcessNode");
            if (processNodeNodeList != null) {
                for (int i = 0; i < processNodeNodeList.getLength(); i++) {
                    Element processNode = (Element) processNodeNodeList.item(i);
                    if (processNode != null) {
                        String name = XmlHelper.getAttributeValue(processNode, "Name");
                        LOG.debug("Process with tests: " + name);
                        out.add(name);
                    }
                }
            }
        }
        return out;
    }

}
