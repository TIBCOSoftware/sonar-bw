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
import java.io.Serializable;
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
import org.sonar.api.batch.sensor.measure.NewMeasure;
import org.sonar.api.measures.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.resources.Project;
import org.sonar.api.resources.Resource;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.tibco.sonar.plugins.bw6.check.AbstractCheck;
import com.tibco.sonar.plugins.bw6.check.process.DeadLockCheck;
import com.tibco.sonar.plugins.bw6.language.BWProcessLanguage;
import com.tibco.sonar.plugins.bw6.metric.BusinessWorksMetrics;
import com.tibco.sonar.plugins.bw6.rulerepository.ProcessRuleDefinition;
import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import com.tibco.sonar.plugins.bw6.violation.DefaultViolation;
import com.tibco.sonar.plugins.bw6.violation.Violation;
import com.tibco.utils.bw.model.ModuleProperties;
import com.tibco.utils.bw.model.Process;
import com.tibco.utils.bw.model.Service;
/**
 * XmlSensor provides analysis of xml files.
 * 
 * @author Kapil Shivarkar
 */
public class ProcessRuleSensor extends AbstractRuleSensor {

	private Resource processFileResource;
	private Map<String, Process> servicetoprocess = new HashMap<String, Process>();
	protected List<Process> processList = new ArrayList<Process>();
	private String processname = null;
	
	public ProcessRuleSensor(RulesProfile profile, FileSystem fileSystem,
			 CheckFactory checkFactory) {
		super(profile, fileSystem,
				ProcessRuleDefinition.REPOSITORY_KEY, BWProcessLanguage.KEY, checkFactory);
	}

	private static final Logger LOG = Loggers.get(ProcessRuleSensor.class);

	public enum BWResources{		
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

	@SuppressWarnings("unchecked")
	protected void analyseFile(java.io.File file) {
		InputFile resource = fs.inputFile(fs.predicates().is(file));		
		ProcessSource sourceCode = new ProcessSource(file);
		Process process = sourceCode.getProcessModel();
		process.startParsing();
		checkSubprocess(process);
		processList.add(process);

		if (sourceCode.parseSource(fileSystem.encoding())) {
			for (AbstractCheck check : abstractCheck) {
				if(!(check instanceof DeadLockCheck)){
					RuleKey ruleKey = checks.ruleKey(check);
					check.setRuleKey(ruleKey);
					check.setRule(profile.getActiveRule(ruleKey.repository(), ruleKey.rule()).getRule());
					sourceCode = check.validate(sourceCode);
				}
			}
			saveIssues(sourceCode, resource);
		}
	}

	public void checkSubprocess(Process process){
		File file = new File(System.getProperty("user.dir")+"/META-INF/module.bwm");
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
				if(process.getName().equals(propertyList.item(i).getChildNodes().item(1).getAttributes().getNamedItem("processName").getNodeValue())){
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


	protected void analyseDeadLock(Iterable<File> filesIterable){
		for (int i = 0; i < processList.size(); i++) {
			Map<String, Service> services = processList.get(i).getServices();
			for(String servicename : services.keySet()){
				String key = servicename+"-"+services.get(servicename).getNamespace()+"-"+processList.get(i).getName();
				servicetoprocess.put(key, processList.get(i));
			}
		}
		//------All set ready to go
		for (Iterator<Process> iterator  = processList.iterator(); iterator.hasNext();) {
			Process process = iterator.next();
			String proc1 = process.getName();
			proc1 = proc1.substring(proc1.lastIndexOf(".")+1).concat(".bwp");
			for (File file : fileSystem.files(fileSystem.predicates().hasLanguage(languageKey))) {
				if(file.getName().equals(proc1)){
					InputFile resource = fs.inputFile(fs.predicates().is(file));		
					ProcessSource sourceCode = new ProcessSource(file);
					findDeadLock(process.getServices(), process.getProcessReferenceServices(), process, sourceCode, resource);
				}
			}
		}
	}

	public void findDeadLock(Map<String, Service> services, Map<String, Service> referencedServices, Process process, ProcessSource sourceCode, InputFile resource){
		if(services.size() > 0 && referencedServices.size() > 0){
			Set<String> serviceName = services.keySet();
			Set<String> referenceServiceName = referencedServices.keySet();
			Set<String> dupReferencedServiceName = new HashSet<String>(referenceServiceName);
			dupReferencedServiceName.retainAll(serviceName);
			if(dupReferencedServiceName.size() > 0 ){
				String[] deadlockedService = dupReferencedServiceName.toArray(new String[dupReferencedServiceName.size()]);
				String referencedServiceNameSpace = referencedServices.get(deadlockedService[0]).getNamespace();
				String serviceNamespace = services.get(deadlockedService[0]).getNamespace();
				String referenceProcessName = referencedServices.get(deadlockedService[0]).getImplementationProcess();
				String proc2 = process.getName();

				if(referencedServiceNameSpace.equals(serviceNamespace) && referenceProcessName != null && proc2.equals(referenceProcessName)){
					for (AbstractCheck check : abstractCheck) {
						if(check instanceof DeadLockCheck){
							RuleKey ruleKey = checks.ruleKey(check);
							check.setRuleKey(ruleKey);
							check.setRule(profile.getActiveRule(ruleKey.repository(), ruleKey.rule()).getRule());
							proc2 = proc2.substring(proc2.lastIndexOf(".")+1).concat(".bwp");
							Violation violation;
							if(processname == null){
								violation = new DefaultViolation(check.getRule(),
										1,
										"There is a very high possibility of deadlock in the implementation of service "+deadlockedService[0] +" exposed by process "+proc2);

							}else{
								violation = new DefaultViolation(check.getRule(),
										1,
										"Deadlock is detected between processes "+proc2+" and "+processname+". There is a very high possibility of deadlock in the implementation of service "+deadlockedService[0] +" exposed by process "+proc2+" and consumed by process "+processname);
								processname = null;
							}
							sourceCode.addViolation(violation);
							saveIssues(sourceCode, resource);
						}
					}
				}else{
					for (String name : referenceServiceName) {
						Process proc = servicetoprocess.get(name+"-"+referencedServices.get(name).getNamespace()+"-"+referencedServices.get(name).getImplementationProcess());
						if(proc.getProcessReferenceServices() != null){
							processname = null;
							processname = proc.getName();
							processname = processname.substring(processname.lastIndexOf(".")+1).concat(".bwp");
							findDeadLock(services, proc.getProcessReferenceServices(), process, sourceCode, resource);					
						}
					}
				}
			}else{
				for (String name : referenceServiceName) {
					Process proc = servicetoprocess.get(name+"-"+referencedServices.get(name).getNamespace()+"-"+referencedServices.get(name).getImplementationProcess());
					if(proc != null && proc.getProcessReferenceServices() != null){
						processname = null;
						processname = proc.getName();
						processname = processname.substring(processname.lastIndexOf(".")+1).concat(".bwp");
						findDeadLock(services, proc.getProcessReferenceServices(), process, sourceCode, resource);					
					}
				}

			}
		}
	}


//	public void processMetrics(){
	@Override
	public void execute(SensorContext context) {
	
		int moduleProperties = getPropertiesCount(".bwm");
		int groupsProcess = 0;
		int activitiesProcess = 0;
		int transitionsProcess = 0;
		int processStarters = 0;
		int catchBlocks = 0;
		int noOfProcesses = processList.size();
		int sharedResources = getSharedResourcesCount(new File(System.getProperty("user.dir")+"/Resources"));
		int jobSharedVariable = getPropertiesCount(".jsv");
		int moduleSharedVariable = getPropertiesCount(".msv");
		int eventHandlers = 0;
		int services = 0;
		int subprocesscount = 0;
		int subservice = 0;
		int subreference = 0;

		for (Iterator<Process> iterator = processList.iterator(); iterator.hasNext();) {
			Process process = iterator.next();
			groupsProcess += process.getGroupcount();
			activitiesProcess += process.countAllActivities();
			transitionsProcess += process.countAllTransitions();
			processStarters += process.getEventSourcesCount();
			catchBlocks += process.getCatchcount();
			eventHandlers += process.getEventHandler();
			services += process.getServices().size();
			if(process.isSubProcess()){
				subprocesscount++;
				subservice += process.getServices().size();
				subreference += process.getProcessReferenceServices().size();
			}
		}

		noOfProcesses = noOfProcesses - subprocesscount;
	//	if(sensorContext.getMeasure(BusinessWorksMetrics.BWLANGUAGEFLAG) == null)
	//		saveMeasure(BusinessWorksMetrics.BWLANGUAGEFLAG, (double)1);
		//processFileResource = sensorContext.getResource(ProcessFileResource.fromIOFile(file, project));
		saveMeasure(context,BusinessWorksMetrics.PROCESSES, (Integer) noOfProcesses);
		saveMeasure(context,BusinessWorksMetrics.SUBPROCESSES, (Integer) subprocesscount);
		saveMeasure(context,BusinessWorksMetrics.GROUPS, (Integer) groupsProcess);
		saveMeasure(context,BusinessWorksMetrics.ACTIVITIES, (Integer) activitiesProcess);
		saveMeasure(context,BusinessWorksMetrics.TRANSITIONS, (Integer) transitionsProcess);
		saveMeasure(context,BusinessWorksMetrics.PROCESSSTARTER, (Integer) processStarters);
		saveMeasure(context,BusinessWorksMetrics.GLOBALVARIABLES, (Integer) moduleProperties);
		saveMeasure(context,BusinessWorksMetrics.BWRESOURCES, (Integer) sharedResources);
		saveMeasure(context,BusinessWorksMetrics.JOBSHAREDVARIABLES, (Integer) jobSharedVariable);
		saveMeasure(context,BusinessWorksMetrics.MODULESHAREDVARIABLES, (Integer) moduleSharedVariable);
		saveMeasure(context,BusinessWorksMetrics.CATCHBLOCK, (Integer) catchBlocks);
		saveMeasure(context,BusinessWorksMetrics.EVENTHANDLER, (Integer) eventHandlers);
		saveMeasure(context,BusinessWorksMetrics.SERVICES, (Integer) services);
		saveMeasure(context,BusinessWorksMetrics.SUBSERVICES, (Integer) subservice);
		saveMeasure(context,BusinessWorksMetrics.SUBREFERENCE, (Integer) subreference);
	//	saveMeasure(BusinessWorksMetrics.PROJECTCOMPLEXITY, "MEDIUM");
//		saveMeasure(BusinessWorksMetrics.CODEQUALITY, "AVERAGE");
		
		Metric[] metric = BusinessWorksMetrics.resourceMetrics();
		
		BWResources bwresource;
		
		for (int i = 0; i < metric.length; i++) {	
			bwresource = BWResources.valueOf(metric[i].getName().replaceAll("\\s",""));
			switch (bwresource) {
			case HTTPClient:
				saveMeasure(context,BusinessWorksMetrics.BWRESOURCES_HTTP_CONNECTION_FLAG, (Integer)1);
				break;
			case XMLAuthentication:
				saveMeasure(context,BusinessWorksMetrics.XML_AUTHENTICATION_FLAG, (Integer)1);
				break;
			case WSSAuthentication:
				saveMeasure(context,BusinessWorksMetrics.WSS_Authentication_FLAG, (Integer)1);
				break;
			case TrustProvider:
				saveMeasure(context,BusinessWorksMetrics.Trust_Provider_Flag, (Integer)1);
				break;
			case ThrealPool:
				saveMeasure(context,BusinessWorksMetrics.Threal_Pool_Flag, (Integer)1);
				break;
			case TCPConnection:
				saveMeasure(context,BusinessWorksMetrics.TCP_Connection_Flag, (Integer)1);
				break;
			case SubjectProvider:
				saveMeasure(context,BusinessWorksMetrics.Subject_Provider_Flag, (Integer)1);
				break;
			case SSLServerConfiguration:
				saveMeasure(context,BusinessWorksMetrics.SSL_Server_Configuration_Flag, (Integer)1);
				break;
			case SSLClientConfiguration:
				saveMeasure(context,BusinessWorksMetrics.SSL_Client_Configuration_Flag, (Integer)1);
				break;
			case SMTPResource:
				saveMeasure(context,BusinessWorksMetrics.SMTP_Resource_Flag, (Integer)1);
				break;
			case RendezvousTransport:
				saveMeasure(context,BusinessWorksMetrics.RVTRANSPORTFLAG, (Integer)1);
				break;
			case ProxyConfiguration:
				saveMeasure(context,BusinessWorksMetrics.Proxy_Configuration_Flag, (Integer)1);
				break;
			case LDAPAuthentication:
				saveMeasure(context,BusinessWorksMetrics.LDAP_Authentication_Flag, (Integer)1);
				break;
			case KeystoreProvider:
				saveMeasure(context,BusinessWorksMetrics.Keystore_Provider_Flag, (Integer)1);
				break;
			case JNDIConfiguration:
				saveMeasure(context,BusinessWorksMetrics.JNDI_Configuration_Flag, (Integer)1);
				break;
			case JMSConnection:
				saveMeasure(context,BusinessWorksMetrics.BWRESOURCES_JMS_CONNECTION_FLAG, (Integer)1);
				break;
			case JDBCConnection:
				saveMeasure(context,BusinessWorksMetrics.BWRESOURCES_JDBC_CONNECTION_FLAG, (Integer)1);
				break;
			case JavaGlobalInstance:
				saveMeasure(context,BusinessWorksMetrics.Java_Global_Instance_Flag, (Integer)1);
				break;
			case IdentityProvider:
				saveMeasure(context,BusinessWorksMetrics.Identity_Provider_Flag, (Integer)1);
				break;
			case HTTPConnector:
				saveMeasure(context,BusinessWorksMetrics.BWRESOURCES_HTTP_CONNECTOR_FLAG, (Integer)1);
				break;
			case FTPConnection:
				saveMeasure(context,BusinessWorksMetrics.FTP_Connection_Flag, (Integer)1);
				break;
			case FTLRealmServerConnection:
				saveMeasure(context,BusinessWorksMetrics.FTL_Realm_Server_Connection_Flag, (Integer)1);
				break;
			case DataFormat:
				saveMeasure(context,BusinessWorksMetrics.Data_Format_Flag, (Integer)1);
				break;
			case SQLFile:
				saveMeasure(context,BusinessWorksMetrics.SQL_File_Flag, (Integer)1);
				break;
			default:
				break;
			}
	//		saveMeasure(context, metric[i], (Integer)foundResources.get(metric[i].getName()));
		}
	}


	private void saveMeasure(SensorContext context, Metric<Integer> metric, Integer value) {
	
		context.<Integer>newMeasure()		
		.forMetric(metric)
		.on(null)
        .withValue(value)
        .save();
	}

	public static Map<String, Integer> foundResources = new HashMap<String, Integer>(); 

	public static int getSharedResourcesCount(File directory) {
		int count = 0;
		for (File file : directory.listFiles()) {
			if (file.isFile()) {
				String name = file.getName();
				String extension = name.substring(name.lastIndexOf("."));
				String resourceType = resourceExtensionMapper.get(extension);
				if(resourceType != null){
					if(foundResources.get(resourceType) == null)
						foundResources.put(resourceType, 1);
					else
						foundResources.put(resourceType, foundResources.get(resourceType) + 1);
					count++;
				}
			}
			if (file.isDirectory()) {
				count += getSharedResourcesCount(file);
			}
		}
		return count;
	}

	public int getPropertiesCount(final String fileExtension){
		File dir = new File(System.getProperty("user.dir")+"/META-INF");
		File[] files = dir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename)
			{ return filename.endsWith(fileExtension); }
		});
		ModuleProperties moduleprops = new ModuleProperties(files[0]);
		if (fileExtension.equals(".jsv"))
			return moduleprops.getPropertiesCount("jobSharedVariable");
		else if(fileExtension.equals(".bwm"))
			return moduleprops.getPropertiesCount("sca:property");
		else
			return moduleprops.getPropertiesCount("moduleSharedVariable");
	}

	public int getModulePropertiesCount(){
		File dir = new File(System.getProperty("user.dir")+"/META-INF");
		File[] files = dir.listFiles(new FilenameFilter() { 
			public boolean accept(File dir, String filename)
			{ return filename.endsWith(".bwm"); }
		});
		ModuleProperties moduleprops = new ModuleProperties(files[0]);
		return moduleprops.getPropertiesCount("sca:property");
	}
	/**
	 * This sensor only executes on projects with active XML rules.
	 */
	public boolean shouldExecuteOnProject(Project project) {
		/*return !fileSystem.files(FileQuery.onSource().onLanguage(ProcessLanguage.KEY))
				.isEmpty();*/
		return fileSystem.files(fileSystem.predicates().hasLanguage(languageKey)).iterator().hasNext();

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
