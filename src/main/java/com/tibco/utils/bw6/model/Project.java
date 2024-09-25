/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author avazquez
 */
public class Project {

    protected static Logger LOG = Loggers.get(Project.class);

    protected File file;

    protected ModuleProperties properties;

    protected ModuleSharedVariables moduleSharedVariables;

    protected JobSharedVariables jobSharedVariables;

    protected List<Process> processList;

    protected List<SharedResource> resources;

    protected Document bwmDocument;

    protected List<WsdlResource> serviceDescriptor;

    protected List<XsdResource> schemas;
    
    protected List<JSONResource> jsonSchemas;

    protected List<String> dependencies;

    protected List<Component> components;

    protected Map<String, String> profileVariables;
    
    protected boolean isApplicationModule;
    
    protected boolean isMavenProject;
    
    protected Manifest manifest;
    
    protected List<GenericResource> otherFiles;
    
    protected Document pomFile;
    
    protected List<Policy> policies;
    
    protected List<KeyStore> keystores;

    public Project() {
        processList = new ArrayList<>();
        resources = new ArrayList<>();
        schemas = new ArrayList<>();
        jsonSchemas = new ArrayList<>();
        serviceDescriptor = new ArrayList<>();
        dependencies = new ArrayList<>();
        components = new ArrayList<>();
        profileVariables = new HashMap<>();
        otherFiles = new ArrayList<>();
        policies = new ArrayList<>();
        keystores = new ArrayList<>();
    }

    /**
     * @return the properties
     */
    public ModuleProperties getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(ModuleProperties properties) {
        this.properties = properties;
    }

    /**
     * @return the process
     */
    public List<Process> getProcess() {
        return processList;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(List<Process> process) {
        this.processList = process;
    }

    /**
     * @return the resources
     */
    public List<SharedResource> getResources() {
        return resources;
    }

    /**
     * @param resources the resources to set
     */
    public void setResources(List<SharedResource> resources) {
        this.resources = resources;
    }

    /**
     * @return the moduleSharedVariables
     */
    public ModuleSharedVariables getModuleSharedVariables() {
        return moduleSharedVariables;
    }

    /**
     * @param moduleSharedVariables the moduleSharedVariables to set
     */
    public void setModuleSharedVariables(ModuleSharedVariables moduleSharedVariables) {
        this.moduleSharedVariables = moduleSharedVariables;
    }

    /**
     * @return the jobSharedVariables
     */
    public JobSharedVariables getJobSharedVariables() {
        return jobSharedVariables;
    }

    /**
     * @param jobSharedVariables the jobSharedVariables to set
     */
    public void setJobSharedVariables(JobSharedVariables jobSharedVariables) {
        this.jobSharedVariables = jobSharedVariables;
    }

    /**
     * @return the bwmDocument
     */
    public Document getBwmDocument() {
        return bwmDocument;
    }

    /**
     * @param bwmDocument the bwmDocument to set
     */
    public void setBwmDocument(Document bwmDocument) {
        this.bwmDocument = bwmDocument;
    }

    public WsdlResource addWSDL(Document document) {
        String searchKey = "targetNamespace";
        WsdlResource resource = new WsdlResource();
        resource.setDocument(document);
        NamedNodeMap map = document.getDocumentElement().getAttributes();
        String namespace = map.getNamedItem(searchKey).getNodeValue();
        resource.setTargetNamespace(namespace);
        serviceDescriptor.add(resource);
        return resource;
    }

    public XsdResource addSchema(Document document) {
        String searchKey = "targetNamespace";
        XsdResource resource = new XsdResource();
        resource.setDocument(document);
        NamedNodeMap map = document.getDocumentElement().getAttributes();
        if (map.getNamedItem(searchKey) != null) {
            String namespace = map.getNamedItem(searchKey).getNodeValue();
            resource.setTargetNamespace(namespace);
            schemas.add(resource);
        }
        return resource;
    }
    
    
     public void addJSONSchema(JSONResource resource) {
        jsonSchemas.add(resource);
    }

    /**
     * @return the serviceDescriptor
     */
    public List<WsdlResource> getServiceDescriptor() {
        return serviceDescriptor;
    }

    /**
     * @param serviceDescriptor the serviceDescriptor to set
     */
    public void setServiceDescriptor(List<WsdlResource> serviceDescriptor) {
        this.serviceDescriptor = serviceDescriptor;
    }

    /**
     * @return the schemas
     */
    public List<XsdResource> getSchemas() {
        return schemas;
    }

    /**
     * @param schemas the schemas to set
     */
    public void setSchemas(List<XsdResource> schemas) {
        this.schemas = schemas;
    }

    public void addProcess(Process process) {
        this.processList.add(process);
    }

    public void addResource(SharedResource resource) {
        this.resources.add(resource);
    }

    public void init(File file) {
        this.file = file;
        LOG.debug("Initializing the BwProject: " + getFile().getAbsolutePath());

        Collection<File> bwmFileList = FileUtils.listFiles(file, new String[]{"bwm"}, true);
        if (!bwmFileList.isEmpty()) {
            setBwmDocument(XmlHelper.getDocument(bwmFileList.iterator().next()));
        }
        
        
        Collection<File> mfFileList = FileUtils.listFiles(file, new String[]{"MF"}, true);
        if (!mfFileList.isEmpty()) {
            checkManifest(mfFileList.iterator().next());
        }
        
        properties = new ModuleProperties(bwmDocument);
        jobSharedVariables = new JobSharedVariables(bwmDocument);
        moduleSharedVariables = new ModuleSharedVariables(bwmDocument);
        parseProcesses();
        parseResources();
        parseBindings();
        parsePolicies();
        parseKeystores();
        LOG.debug("Initialized the BwProject: " + getFile().getAbsolutePath());

    }

    public void addProfileVariable(String name, String value) {

        Pattern p = Pattern.compile("//[^/]+//(.*)");
        Matcher m = p.matcher(name);
        if (m.matches()) {
            name = m.group(1);
        }
        profileVariables.put(name, value);
    }

    public String getProfileVariable(String name) {
        return profileVariables.get(name);
    }

    public boolean isProfileVariable(String portStr) {
        return getProfileVariable(portStr) != null;
    }

    private void parseResources() {
        Collection<File> files = FileUtils.listFiles(file,
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        if (files != null) {
            for (File f : files) {
                if (f.getName().matches(".*Resource")) {
                    SharedResource resource = new SharedResource();
                    resource.setFileName(f.getName());
                    resource.setDocument(XmlHelper.getDocument(f));
                    resource.setProject(this);
                    resource.parse();
                    addResource(resource);
                }
            }
        }

    }

    public void parsePolicies() {
        LOG.debug("parsePolicies - START");
        Collection<File> files = FileUtils.listFiles(file, new String[]{"policy"}, true);
        if (files != null) {
            for (File f : files) {
                Policy p = new Policy();
                p.setXmlDocument(XmlHelper.getDocument(f));
                p.setFileName(f.getName());
                p.startParsing();
                
                addPolicy(p);
            }
        }
        LOG.debug("parsePolicies - END");

    }
    
    
    private void parseProcesses() {
        Collection<File> files = FileUtils.listFiles(getFile(),
                new String[]{"bwp"}, true);
        if (files != null) {
            for (File f : files) {
                Process proc = new Process();
                proc.setProcessXmlDocument(XmlHelper.getDocument(f));
                proc.startParsing();
                addProcess(proc);
            }
        }

    }
    
    public void parseKeystores() {
        LOG.debug("parseKeystores - START");
        Collection<File> files = FileUtils.listFiles(file,
                new String[]{"jks","jckes"}, true);
        if (files != null) {
            LOG.debug("Reading files for keystore analyzing");
            for(File f: files){
                try {
                    KeyStore kstore = KeyStore.getInstance(KeyStore.getDefaultType());
                    LOG.debug("Loading Keystore to be able to analyze it");
                    kstore.load(new FileInputStream(f) , null);
                    LOG.debug("Keystore loaded");
                    this.keystores.add(kstore);
                } catch (KeyStoreException ex) {
                   LOG.warn(" ",ex);
                } catch (IOException | NoSuchAlgorithmException | CertificateException ex) {
                   LOG.warn(" ", ex);
                }
            }
                
            }
        LOG.debug("parseKeystores - END");

    }

    public void parseBindings() {
        LOG.debug("Starting parsing bindings..");
        if (bwmDocument != null) {
            bwmDocument.getDocumentElement().normalize();
            NodeList temporalNodeComponentList = bwmDocument.getElementsByTagName("sca:component");
            if (temporalNodeComponentList != null) {
                for (int i = 0; i < temporalNodeComponentList.getLength(); i++) {
                    Element componentNode = (Element) temporalNodeComponentList.item(i);
                    LOG.debug("Component found : " + componentNode);
                    Component cmp = new Component(componentNode, bwmDocument);
                    cmp.setName(XmlHelper.getAttributeValue(componentNode, "name"));
                    LOG.debug("Component name : " + cmp.getName());
                    components.add(cmp);
                    Element implementation = XmlHelper.firstChildElement(componentNode, "scaext:implementation");
                    LOG.debug("Implementation element:" + implementation);
                    if (implementation != null) {
                        String processName = implementation.getAttribute("processName");
                        LOG.debug("Implementation process name: " + processName);
                        Process process = getProcessByName(processName);
                        cmp.setProcess(process);
                    }
                    Element serviceNode = XmlHelper.firstChildElement(componentNode, "sca:service");
                    LOG.debug("Service node: " + serviceNode);
                    if (serviceNode != null) {
                        String serviceName = XmlHelper.getAttributeValue(serviceNode, "name");
                        LOG.debug("Service name: " + serviceName);
                        Service service = getServiceByName(serviceName);
                        cmp.addService(service);
                        LOG.debug("Adding service to component");
                    }
                    Element referenceNode = XmlHelper.firstChildElement(componentNode, "sca:reference");
                    LOG.debug("Reference node: " + referenceNode);
                    if (referenceNode != null) {
                        String serviceName = XmlHelper.getAttributeValue(referenceNode, "name");
                        LOG.debug("BwService name: " + serviceName);
                        Service service = getServiceByName(serviceName);
                        cmp.addService(service);
                        LOG.debug("Adding service to component");
                    }
                }
            }
            temporalNodeComponentList = bwmDocument.getElementsByTagName("sca:service");
            if (temporalNodeComponentList != null) {
                handleBinding(temporalNodeComponentList);
            }
            temporalNodeComponentList = bwmDocument.getElementsByTagName("sca:reference");
            if (temporalNodeComponentList != null) {
                handleBinding(temporalNodeComponentList);
            }

        }
        LOG.debug("Parsing bindings..DONE");
    }

    private void handleBinding(NodeList temporalNodeComponentList) {
        LOG.debug("Iterating over sca:services");
        for (int i = 0; i < temporalNodeComponentList.getLength(); i++) {
            Element serviceBindingNode = (Element) temporalNodeComponentList.item(i);
            LOG.debug("BwService node: " + serviceBindingNode);
            String promote = XmlHelper.getAttributeValue(serviceBindingNode, "promote");
            LOG.debug("BwService node promote attribute value: " + promote);
            if (promote != null && !"".equals(promote)) {
                LOG.debug("Detected as service + binding");
                Service service = getServiceByComponentPath(promote);
                handleBindingObject(serviceBindingNode, service);

            }

        }
    }

    public static void handleBindingObject(Element serviceBindingNode,
            Service service) {
        if (service != null) {
            Element bindingNode = XmlHelper.firstChildElement(serviceBindingNode, "scaext:binding");
            if (bindingNode != null) {

                LOG.debug("Binding Element: " + bindingNode);
                Binding binding = new Binding();
                for (int i = 0; i < bindingNode.getAttributes().getLength(); i++) {
                    Node attribute = bindingNode.getAttributes().item(i);
                    binding.addProperty(attribute.getNodeName(), attribute.getNodeValue());
                }
                Element inboundConfigurationNode = XmlHelper.firstChildElement(bindingNode, "inboundConfiguration");
                if (inboundConfigurationNode != null) {
                    LOG.debug("Inbound configuration node: " + inboundConfigurationNode);
                    for (int i = 0; i < inboundConfigurationNode.getAttributes().getLength(); i++) {
                        Node attribute = inboundConfigurationNode.getAttributes().item(i);
                        binding.addProperty(attribute.getNodeName(), attribute.getNodeValue());
                    }
                }
                Element outboundConfigurationNode = XmlHelper.firstChildElement(bindingNode, "outboundConfiguration");
                if (outboundConfigurationNode != null) {
                    LOG.debug("Inbound configuration node: " + outboundConfigurationNode);
                    for (int i = 0; i < outboundConfigurationNode.getAttributes().getLength(); i++) {
                        Node attribute = outboundConfigurationNode.getAttributes().item(i);
                        binding.addProperty(attribute.getNodeName(), attribute.getNodeValue());
                    }
                }

                service.setBinding(binding);
            }

            Element bindingAdjuntNode = XmlHelper.firstChildElement(serviceBindingNode, "scact:bindingAdjunct");
            LOG.debug("Binding Adjunct: " + bindingAdjuntNode);
            if (bindingAdjuntNode != null) {

                String bindingName = XmlHelper.getAttributeValue(bindingAdjuntNode, "bindingName");
                LOG.debug("Binding Name: " + bindingName);
                Binding binding = service.getBinding();
                NodeList bindingAdjuntPropertyList = bindingAdjuntNode.getElementsByTagName("sca:property");
                if (bindingAdjuntPropertyList != null) {
                    LOG.debug("Property list for binding: " + bindingAdjuntPropertyList.getLength());
                    for (int j = 0; j < bindingAdjuntPropertyList.getLength(); j++) {
                        Element bindingAdjuntProperty = (Element) bindingAdjuntPropertyList.item(j);
                        if (bindingAdjuntProperty != null) {
                            LOG.debug("Property: " + bindingAdjuntProperty);
                            String name = XmlHelper.getAttributeValue(bindingAdjuntProperty, "name");
                            LOG.debug("Property name: " + name);
                            String simpleValue = XmlHelper.getAttributeValue(bindingAdjuntProperty, "scaext:simpleValue");
                            LOG.debug("Property simple value: " + simpleValue);
                            binding.addProperty(name, simpleValue);
                            if (name.equals("endpointURI")) {
                                binding.setIsPropertyURI(true);
                                LOG.debug("Set binding URI is set by property: " + binding.isIsPropertyURI());
                                binding.setUri(simpleValue);
                            }
                        }
                    }
                }
            }
        }
    }

    public Process getProcessByName(String processName) {
        if (processName != null) {
            for (Process process : processList) {
                if (processName.equals(process.getName())) {
                    return process;
                }
            }
        }
        return null;
    }
    
    
        public Policy getPolicyByName(String policyName) {
        if (policyName != null) {
            for (Policy policy : policies) {
                if (policyName.equals(policy.getName())) {
                    return policy;
                }
            }
        }
        return null;
    }


    public Service getServiceByName(String serviceName) {
        if (serviceName != null) {
            for (Process process : processList) {
                for (Entry<String, Service> entryService : process.getServices().entrySet()) {
                    Service service = entryService.getValue();
                    if (serviceName.equals(service.getName())) {
                        return service;
                    }
                }
            }
        }
        return null;
    }

    private Service getServiceByComponentPath(String promote) {
        if (promote != null) {
            for (Component comp : components) {
                for (Service service : comp.getServices()) {
                    if (service != null) {
                        if (promote.equals(comp.getName() + "/" + service.getName())) {
                            return service;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * @return the processList
     */
    public List<Process> getProcessList() {
        return processList;
    }

    /**
     * @param processList the processList to set
     */
    public void setProcessList(List<Process> processList) {
        this.processList = processList;
    }

    /**
     * @return the dependencies
     */
    public List<String> getDependencies() {
        return dependencies;
    }

    /**
     * @param dependencies the dependencies to set
     */
    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * @return the components
     */
    public List<Component> getComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(List<Component> components) {
        this.components = components;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * @return the isApplicationModule
     */
    public boolean isIsApplicationModule() {
        return isApplicationModule;
    }

    /**
     * @param isApplicationModule the isApplicationModule to set
     */
    public void setIsApplicationModule(boolean isApplicationModule) {
        this.isApplicationModule = isApplicationModule;
    }

    /**
     * @return the isMavenProject
     */
    public boolean isIsMavenProject() {
        return isMavenProject;
    }

    /**
     * @param isMavenProject the isMavenProject to set
     */
    public void setIsMavenProject(boolean isMavenProject) {
        this.isMavenProject = isMavenProject;
    }

    private void checkManifest(File next) {
        try {
            Manifest manifest = new Manifest(new FileInputStream(next));

                Attributes attr = manifest.getMainAttributes();
                if(attr != null){
                    String appModule = attr.getValue("TIBCO-BW-ApplicationModule");
                    if(appModule != null && !"".equals(appModule)){
                        isApplicationModule = true;
                    }
                }
            
            
            
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the LOG
     */
    public static Logger getLOG() {
        return LOG;
    }

    /**
     * @param aLOG the LOG to set
     */
    public static void setLOG(Logger aLOG) {
        LOG = aLOG;
    }

    /**
     * @return the jsonSchemas
     */
    public List<JSONResource> getJsonSchemas() {
        return jsonSchemas;
    }

    /**
     * @param jsonSchemas the jsonSchemas to set
     */
    public void setJsonSchemas(List<JSONResource> jsonSchemas) {
        this.jsonSchemas = jsonSchemas;
    }

    /**
     * @return the profileVariables
     */
    public Map<String, String> getProfileVariables() {
        return profileVariables;
    }

    /**
     * @param profileVariables the profileVariables to set
     */
    public void setProfileVariables(Map<String, String> profileVariables) {
        this.profileVariables = profileVariables;
    }

    /**
     * @return the manifest
     */
    public Manifest getManifest() {
        return manifest;
    }

    /**
     * @param manifest the manifest to set
     */
    public void setManifest(Manifest manifest) {
        this.manifest = manifest;
    }

    /**
     * @return the otherFiles
     */
    public List<GenericResource> getOtherFiles() {
        return otherFiles;
    }

    /**
     * @param otherFiles the otherFiles to set
     */
    public void setOtherFiles(List<GenericResource> otherFiles) {
        this.otherFiles = otherFiles;
    }

    public void addOtherFile(GenericResource resource) {
        otherFiles.add(resource);
    }

    public boolean hasActivator() {
       boolean activator = false;
       for(Component component : components){
           if ("ActivatorComponent".equals(component.getName())) {
               activator = true;
               break;
           }
       }
       return activator;
    }

    public Document getPomFile() {
        return pomFile;
    }

    /**
     * @param pomFile the pomFile to set
     */
    public void setPomFile(Document pomFile) {
        this.pomFile = pomFile;
    }

    private void addPolicy(Policy p) {
        policies.add(p);
    }

    /**
     * @return the keystores
     */
    public List<KeyStore> getKeystores() {
        return keystores;
    }

    /**
     * @param keystores the keystores to set
     */
    public void setKeystores(List<KeyStore> keystores) {
        this.keystores = keystores;
    }
}
