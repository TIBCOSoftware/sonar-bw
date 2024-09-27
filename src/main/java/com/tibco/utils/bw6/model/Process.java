/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import static com.tibco.utils.bw6.constants.BwpModelConstants.*;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.w3c.dom.Element;

public class Process {

    private static final Logger LOG = Loggers.get(Process.class);

    protected String name;
    protected String description;
    protected String namespace;
    protected String modifiers;
    protected boolean stateless;
    protected boolean isSubProcess;
    protected List<EventSource> eventSources = new ArrayList<>();
    protected List<Activity> activities = new ArrayList<>();
    protected List<Group> groups = new ArrayList<>();
    protected Deque<Group> groupsstack = new ArrayDeque<>();
    protected Map<String, Service> services = new HashMap<>();
    protected Map<String, Service> processReferenceServices = new HashMap<>();
    protected Map<String, String> synonymsGroupMapping = new HashMap<>();
    protected List<Variable> variables = new ArrayList<>();
    
    
    protected boolean hasForEachGroup;
    protected Document processXmlDocument;
    protected HashMap<String, Transition> transitionMap = new HashMap<>();
    protected int groupcount = 0;
    protected int catchcount = 0;
    protected int eventHandler = 0;
    protected NamedNodeMap namedNodeMap;
    
    protected boolean hasTest;
            
    protected Project project;

    public Map<String, String> getSynonymsGroupMapping() {
        return synonymsGroupMapping;
    }

    public boolean isHasForEachGroup() {
        return hasForEachGroup;
    }

    public void setHasForEachGroup(boolean hasForEachGroup) {
        this.hasForEachGroup = hasForEachGroup;
    }

    public boolean isSubProcess() {
        return isSubProcess;
    }

    public void setSubProcess(boolean isSubProcess) {
        this.isSubProcess = isSubProcess;
    }

    public Map<String, Service> getProcessReferenceServices() {
        return processReferenceServices;
    }

    public void setProcessReferenceServices(Map<String, Service> onlyReferenceServices) {
        this.processReferenceServices = onlyReferenceServices;
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }

    public int getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(int eventHandler) {
        this.eventHandler = eventHandler;
    }

    public int getGroupcount() {
        return groupcount;
    }

    public void setGroupcount(int groupcount) {
        this.groupcount = groupcount;
    }

    public int getCatchcount() {
        return catchcount;
    }

    public void setCatchcount(int catchcount) {
        this.catchcount = catchcount;
    }

    public Process() {
        super();
    }

    public String getName() {
        return name;
    }

    public String getBasename() {
        if(name.lastIndexOf('.') >= 0 ){
            return name.substring(name.lastIndexOf('.') + 1).concat(".bwp");
        }
        return "default.bwp";
    }

    public Process setName(String name) {
        this.name = name;
        return this;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public Process setActivities(List<Activity> activities) {
        this.activities = activities;
        return this;
    }

    public Activity getActivityByName(String activityName) {
        for (Activity activity : activities) {
            if (activity.getName().equals(activityName)) {
                return activity;
            }
        }
        for (EventSource activity : eventSources) {
            if (activity.getName().equals(activityName)) {
                return activity;
            }
        }
        for (Group group : groups) {
            Activity candidate;
            candidate = group.getActivityByName(activityName);
            if (candidate != null) {
                return candidate;
            }
        }
        return null;
    }

    public EventSource getEventSourceByName(String eventsource) {
        for (EventSource evSource : eventSources) {
            if (evSource.getName().equals(eventsource)) {
                return evSource;
            }
        }
        return null;
    }

    public Group getGroupByName(String groupName) {
        for (Group group : groups) {
            if (group.getName().equals(groupName)) {
                return group;
            }
        }
        return null;
    }

    public List<Activity> getActivitiesByType(String activityType) {
        List<Activity> result = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getType() != null && activity.getType().equals(activityType)) {
                result.add(activity);
            }else if(activity.getType() == null){
                LOG.warn("Activity Type has not been detected for activity: "+activity.getName(), null);
            }
        }
        return result;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Process setGroups(List<Group> groups) {
        this.groups = groups;
        return this;
    }

    public Map<String, Transition> getTransitions() {
        return transitionMap;
    }

    public Document getProcessXmlDocument() {
        return processXmlDocument;
    }

    public Process setProcessXmlDocument(Document processXmlDocument) {
        this.processXmlDocument = processXmlDocument;
        return this;
    }

    public void startParsing() {
        String process = "/process";
        String transitions = "/process/scope";
        parseProcessName(process);
        NodeList children = XmlHelper.evaluateXpathNodeSet(processXmlDocument, transitions);
        parseProcess(null, children.item(0).getChildNodes());
        calculateVariables();
    }

    private Reference getReferenceServiceAttribute(Node partnerLink, Element referenceServiceElement) {
        Reference reference = null;

        if (referenceServiceElement != null) {
            reference = new Reference(XmlHelper.getAttributeValue(referenceServiceElement, "serviceName"));
            reference.setInline(XmlHelper.getAttributeValue(referenceServiceElement, "inline"));
            reference.setImplementationProcess(XmlHelper.getAttributeValue(referenceServiceElement, "processName"));
        } else {
            referenceServiceElement = XmlHelper.firstChildElement(partnerLink, "tibex:ReferenceBinding");
            reference = new Reference(XmlHelper.getAttributeValue((Element) partnerLink, "name"));
            reference.setInline(XmlHelper.getAttributeValue(referenceServiceElement, "inline"));

            Element referenceBinding = XmlHelper.evalueXPathSingleElement(referenceServiceElement, "binding/BWBaseBinding/referenceBinding");
            if (referenceBinding != null) {
                Project.handleBindingObject(referenceBinding, reference);
            }

        }
        return reference;
    }

    public Reference findReferenceServiceAttribute(String referenceService) {
        String partnerLinks = "/process/partnerLinks/partnerLink";
        Reference reference = null;
        NodeList children = XmlHelper.evaluateXpathNodeSet(processXmlDocument, partnerLinks);
        if (children != null) {
            for (int i = 0; i < children.getLength(); i++) {
                Node partnerLink = children.item(i);
                if (referenceService.equals(XmlHelper.getAttributeValue((Element) partnerLink, "name"))) {
                    NodeList nodeList = partnerLink.getChildNodes();
                    if (nodeList != null) {
                        Element referenceServiceElement = XmlHelper.firstChildElement(partnerLink, "tibex:ReferenceWire");
                        reference = getReferenceServiceAttribute(partnerLink, referenceServiceElement);
                    }
                }
            }
        }
        return reference;
    }


    public void parseProcessName(String processName) {
        String logStartphrase = "Name attribute node retrieved: " ;
        NodeList children = XmlHelper.evaluateXpathNodeSet(processXmlDocument, processName);
        LOG.debug("parseProcessName - START");
        if (children != null) {
            LOG.debug("Nodeset size founded: " + children.getLength());
            if (children.getLength() >= 1) {
                namedNodeMap = children.item(0).getAttributes();
                Node nodeName = namedNodeMap.getNamedItem("name");
                LOG.debug("Name attribute node retrieved: " + nodeName);
                if (nodeName != null) {
                    LOG.debug("Name content: " + nodeName.getTextContent());
                    setName(nodeName.getTextContent());

                }

                Node targetNamespaceNode = namedNodeMap.getNamedItem("targetNamespace");
                LOG.debug(logStartphrase + targetNamespaceNode);
                if (targetNamespaceNode != null) {
                    LOG.debug("Description content: " + targetNamespaceNode.getTextContent());
                    setNamespace(targetNamespaceNode.getTextContent());
                }
            }

            children = XmlHelper.evaluateXpathNodeSet(processXmlDocument, "/process/ProcessInfo");
            if (children.getLength() >= 1) {
                Node descriptionNode = children.item(0).getAttributes().getNamedItem("description");
                LOG.debug(logStartphrase + descriptionNode);
                if (descriptionNode != null) {
                    LOG.debug("Description content: " + descriptionNode.getTextContent());
                    setDescription(descriptionNode.getTextContent());
                }

                Node statelessNode = children.item(0).getAttributes().getNamedItem("stateless");
                LOG.debug(logStartphrase + statelessNode);
                if (statelessNode != null) {
                    LOG.debug("Stateless content: " + statelessNode.getTextContent());
                    setStateless(Boolean.parseBoolean(statelessNode.getTextContent()));
                }

                Node modifiersNode = children.item(0).getAttributes().getNamedItem("modifiers");
                LOG.debug(logStartphrase + modifiersNode);
                if (modifiersNode != null) {
                    LOG.debug("Stateless content: " + modifiersNode.getTextContent());
                    setModifiers(modifiersNode.getTextContent());
                }
            }

        }
        LOG.debug("parseProcessName - END");
    }

    public void parseProcess(Group group, NodeList transitions) {
        for (int i = 0; i < transitions.getLength(); i++) {
            Node transition = transitions.item(i);
            String nodeName = transition.getNodeName();
            NamedNodeMap nodeMap = transition.getAttributes();
            String whichGroup = null;
            if (nodeMap != null && nodeMap.getNamedItem(TIBEXGROUP) != null) {
                whichGroup = nodeMap.getNamedItem(TIBEXGROUP).getNodeValue();
            }
            if (nodeName.equals(BPWSEXTENSION_ACTIVITY) || nodeName.equals(BPWSREPLY) || nodeName.equals(BPWSCOMPENSATE) || nodeName.equals(BPWSEXIT) || nodeName.equals(BPWSRECEIVE) || nodeName.equals(BPWSEMPTY) || nodeName.equals(BPWSTHROW) || nodeName.equals(BPWSRETHROW)) {
                parseActivities(group, transition);
            } else if (nodeName.equals(BPWSLINKS)) {
                parseTransitions(transition, groupsstack);
                if (groupsstack.peek() != null) {
                    groupsstack.pollLast();
                }
            } else if (nodeName.equals(BPWSSCOPE) || nodeName.equals("bpws:sequence") ||  nodeName.equals(BPWSFLOW) || nodeName.equals(BPWSEVENT_HANDLERS) || nodeName.equals(BPWSON_EVENT) || nodeName.equals(BPWSFAULT_HANDLERS) || nodeName.equals(BPWSREPEAT_UNTIL) || nodeName.equals(BPWSWHILE) || nodeName.equals(BPWSCATCH_ALL) || nodeName.equals(BPWSCATCH) || nodeName.equals(BPWSFOR_EACH) || nodeName.equals(BPWSPICK)) {
                Group returnedGroup = parseScope(group, nodeName, transition, whichGroup, nodeMap);
                parseProcess(returnedGroup, transition.getChildNodes());
                
            } else if (nodeName.equals(BPWSON_MESSAGE)) {
                parseServiceDefinition(transition);
                parseProcess(group, transition.getChildNodes());
                //do something related to reference and service
                //also create an activity with source and target similar to normal activity
            } else if (nodeName.equals(BPWSINVOKE)) {
                parseInvoke(group, transition);

            }
        }
    }

    private void parseInvoke(Group group, Node transition) {
        String serviceName = null;
        String referencedServiceName = transition.getAttributes().getNamedItem(PARTNER_LINK).getTextContent();
        String calledOperation = transition.getAttributes().getNamedItem(OPERATION).getTextContent();
        String namespacePrefix = transition.getAttributes().getNamedItem(PORT_TYPE).getNodeValue();
        if(namespacePrefix.indexOf(':') >= 0){

            namespacePrefix = namespacePrefix.substring(0, namespacePrefix.indexOf(':'));
            if (transition.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem(PARTNER_LINK) != null) {
                serviceName = transition.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem(PARTNER_LINK).getNodeValue();
                String operationName = transition.getParentNode().getParentNode().getParentNode().getAttributes().getNamedItem(OPERATION).getNodeValue();
                Service service = services.get(serviceName);
                Map<String, Operation> operations = service.getOperations();
                Operation operation = operations.get(operationName);
                List<Service> operationReferencedService = operation.getOperationReferencedService();
                Service referencedService = new Service(referencedServiceName);
                referencedService.getOperations().put(calledOperation, new Operation(calledOperation));
                operationReferencedService.add(referencedService);
                populateProcessReferencedService(referencedServiceName, calledOperation, namespacePrefix);
                parseActivities(group, transition);
            } else {
                populateProcessReferencedService(referencedServiceName, calledOperation, namespacePrefix);
                parseActivities(group, transition);
            }
        }
    }

    private void parseServiceDefinition(Node transition) {
        String serviceName = transition.getAttributes().getNamedItem(PARTNER_LINK).getNodeValue();
        String operationName = transition.getAttributes().getNamedItem(OPERATION).getNodeValue();
        if (services.get(serviceName) == null) {
            String namespacePrefix = transition.getAttributes().getNamedItem(PORT_TYPE).getNodeValue();
            if(namespacePrefix.indexOf(':') >=  0){
                namespacePrefix = namespacePrefix.substring(0, namespacePrefix.indexOf(':'));
                Service service = new Service(serviceName);
                service.setNamespace(namedNodeMap.getNamedItem("xmlns:" + namespacePrefix).getNodeValue());
                service.setImplementationProcess(getName());
                Operation operation = new Operation(operationName);
                service.getOperations().put(operationName, operation);
                services.put(serviceName, service);
            }
        } else {
            Service service = services.get(serviceName);
            Operation operation = new Operation(operationName);
            service.getOperations().put(operationName, operation);
        }
    }

    private Group parseScope(Group inputGroup, String nodeName, Node transition, String whichGroup, NamedNodeMap nodeMap) {
        if (nodeName.equals(BPWSCATCH_ALL) || nodeName.equals(BPWSCATCH)) {
            Group dummygroup = new Group(this);
            dummygroup.setNode(transition);
            dummygroup.calculateVariables();
            groupsstack.add(dummygroup);
            setCatchcount(getCatchcount() + 1);
            return dummygroup;
        }
        if (nodeName.equals(BPWSEVENT_HANDLERS)) {
            setEventHandler(getEventHandler() + 1);
        }
        if ( (whichGroup != null && nodeName.equals(BPWSFLOW) && whichGroup.equalsIgnoreCase("localTX") )  || (nodeName.equals(BPWSSCOPE) && transition.getAttributes().getNamedItem(TIBEXGROUP) != null && whichGroup != null && (whichGroup.equalsIgnoreCase("critical") || whichGroup.equalsIgnoreCase("repeatUntil") || whichGroup.equalsIgnoreCase("while") || whichGroup.equalsIgnoreCase("foreach") || whichGroup.equalsIgnoreCase("iterate") || whichGroup.equalsIgnoreCase("none") || whichGroup.equalsIgnoreCase("repeatOnError")))) {
            setGroupcount(getGroupcount() + 1);
            if (whichGroup.equalsIgnoreCase("foreach")) {
                hasForEachGroup = true;
            }
            Group group = new Group(this);
            group.setName(nodeMap.getNamedItem("name").getNodeValue());
            group.setNode(transition);
            group.calculateExpressions();
            group.calculateVariables();
            group.setType(whichGroup);
            
            groups.add(group);
            activities.add(group);
            groupsstack.add(group);
            
            if(inputGroup != null){
                inputGroup.getActivities().add(group);
                inputGroup.getGroups().add(group);
            }
            
            parseTranstionFromToGroups(transition);
            return group;
        }
        return inputGroup;
    }

    public void populateProcessReferencedService(String referencedServiceName, String calledOperation, String namespacePrefix) {
        Service reference = processReferenceServices.get(referencedServiceName);
        if (reference == null) {
            reference = findReferenceServiceAttribute(referencedServiceName);            
            reference.setNamespace(namedNodeMap.getNamedItem("xmlns:" + namespacePrefix).getNodeValue());            
            Map<String, Operation> operations = reference.getOperations();
            operations.put(calledOperation, new Operation(calledOperation));
            processReferenceServices.put(reference.getName(), reference);
            
        } else {
            Map<String, Operation> operations = reference.getOperations();
            if (operations.get(calledOperation) == null) {
                operations.put(calledOperation, new Operation(calledOperation));
            }
        }
    }

    public void parseTranstionFromToGroups(Node parent) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            NodeList transitions = children.item(i).getChildNodes();
            if (children.item(i).getNodeName().equals("bpws:targets")) {
                parseTransitionTarget(transitions, parent);
            } else if (children.item(i).getNodeName().equals("bpws:sources")) {
                parseTransitionSource(transitions, parent);
            }
        }
    }

    private void parseTransitionSource(NodeList transitions, Node parent) {
        for (int j = 0; j < transitions.getLength(); j++) {
            if (transitions.item(j).getNodeName().equals(BPWSSOURCE)) {
                String grouptransition = transitions.item(j).getAttributes().getNamedItem(LINK_NAME).getNodeValue();
                String grouptransition2 = synonymsGroupMapping.get(grouptransition);
                if (grouptransition.contains(GROUP_START1)) {
                    Transition transition = transitionMap.get(grouptransition2);
                    if(transition != null && grouptransition2.indexOf("To") >= 0){
                        grouptransition2 = grouptransition2.substring(0, grouptransition2.indexOf("To"));
                        setTransitionActivity(transition, grouptransition2, true,true);
                    }

                } else if (grouptransition.contains(GROUP_END1)) {
                    Transition transition = transitionMap.get(grouptransition2);
                    if(transition != null && grouptransition2.indexOf("To") >= 0){
                        grouptransition2 = grouptransition2.substring(grouptransition2.indexOf("To") + 2);
                        setTransitionActivity(transition, grouptransition2, false,true);
                    }
                } else {
                    Transition transition = transitionMap.get(transitions.item(j).getAttributes().getNamedItem(LINK_NAME).getNodeValue());
                    if(transition != null){
                        setTransitionActivity(transition, parent.getAttributes().getNamedItem("name").getTextContent(), true,false);
                    }
                }
            }
        }
    }

    public void setTransitionActivity(Transition transition, String name, boolean from, boolean isGroupInternal) {
        LOG.debug("Setting activity reference to transition [" + transition.getName() + "] with activty [" + name + "] and is and outcoming [" + from + "] ");

        Activity act = getActivityByName(name);
        LOG.debug("Activity retrieved: " + act);
        if (act != null) {
            LOG.debug("Activity retrieved [" + act.getName() + "]");
            if (from) {
                transition.setFromActivity(act);
                transition.setFrom(name);
            } else {
                transition.setToActivity(act);
                transition.setTo(name);
            }
            transition.setIsGroupInternal(isGroupInternal);
        }
    }

    private void parseTransitionTarget(NodeList transitions, Node parent) {
        for (int j = 0; j < transitions.getLength(); j++) {
            if (transitions.item(j).getNodeName().equals(BPWSTARGET)) {
                String grouptransition = transitions.item(j).getAttributes().getNamedItem(LINK_NAME).getNodeValue();
                String grouptransition2 = synonymsGroupMapping.get(grouptransition);
                if (grouptransition.contains(GROUP_END1)) {
                    Transition transition = transitionMap.get(grouptransition2);
                    if(transition != null && grouptransition2.indexOf("To") >= 0){
                        grouptransition2 = grouptransition2.substring(grouptransition2.indexOf("To") + 2);
                        setTransitionActivity(transition, grouptransition2, false,true);
                    }
                } else if (grouptransition.contains(GROUP_START1)) {
                    Transition transition = transitionMap.get(grouptransition2);
                    if(transition != null && grouptransition2.indexOf("To") >= 0){
                        grouptransition2 = grouptransition2.substring(0, grouptransition2.indexOf("To"));
                        setTransitionActivity(transition, grouptransition2, true,true);
                    }
                } else {
                    Transition transition = transitionMap.get(transitions.item(j).getAttributes().getNamedItem(LINK_NAME).getNodeValue());
                    if(transition != null ){
                        setTransitionActivity(transition, parent.getAttributes().getNamedItem("name").getTextContent(), false,true);
                    }
                }
            }
        }
    }

    public void parseTransitions(Node parent, Deque<Group> groupsstack) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node sibling = children.item(i).getNextSibling();
            parseTransition(sibling, groupsstack);

        }

    }

    private void parseTransition(Node sibling, Deque<Group> groupsstack1) {
        if (sibling != null && sibling.getNodeName().equals("bpws:link")) {
            Transition transition = new Transition(this);
            transition.setNode(sibling);
            String transitionName = XmlHelper.getAttributeValue((Element) sibling, "name");
           
            if (transitionName.contains(GROUP_START1)) {
                String synonymsKey = transitionName;
                if(transitionName.indexOf("To") >= 0){
                transitionName = transitionName.substring(transitionName.indexOf("To") + 2);
                 if(groupsstack1 != null && groupsstack1.peekLast() != null){
                String from = groupsstack1.peekLast().getName();
                setTransitionActivity(transition, from, true,true);
                setTransitionActivity(transition, transitionName, false,true);
                transitionName = from + "To" + transitionName;
                 }
                synonymsGroupMapping.put(synonymsKey, transitionName);
                }
                 
            }
            if (transitionName.contains(GROUP_END1)) {
                String synonymsKey = transitionName;
                if(transitionName.indexOf("To") >= 0){
                    transitionName = transitionName.substring(0, transitionName.indexOf("To"));
                     if(groupsstack1 != null && groupsstack1.peekLast() != null){
                    String to = groupsstack1.peekLast().getName();
                    setTransitionActivity(transition, transitionName, true,true);
                    setTransitionActivity(transition, to, false,true);
                    transitionName = transitionName + "To" + to;
                     }
                    synonymsGroupMapping.put(synonymsKey, transitionName);
                }
                
            }
            String label = XmlHelper.getAttributeValue((Element) sibling, "tibex:label");
            if (label != null) {
                transition.setLabel(label);
            }
            String linkType = XmlHelper.getAttributeValue((Element) sibling, "tibex:linkType");
            if (linkType != null) {
                transition.setConditionType(linkType);
            }
            transition.setName(transitionName);
            transitionMap.put(transitionName, transition);
        
        }
    }

    public void parseActivities(Group group, Node parent) {

        System.out.println("Group: " + group + "Node: " + parent);

        if (parent.getNodeName().equals(BPWSRECEIVE)) {
            parseServiceDefinition(parent);
            Activity activity = new Activity(this);
            activities.add(activity);
            if (group != null) {
                group.getActivities().add(activity);
            }
            activity.setNode(parent);
            activity.setName(parent.getAttributes().getNamedItem("name").getTextContent());
            activity.parseActivityConfiguration(transitionMap, synonymsGroupMapping);
            activity.parseProperties();
            activity.parseTransitions();
                        if(activity.getType() == null){
                activity.setType(BPWSRECEIVE);
            }
        } else {
            if (parent.getChildNodes().item(0) != null) {
                Node children = parent.getChildNodes().item(0).getNextSibling();
                if (children.getNodeName().equals("tibex:receiveEvent")) {
                    parseProcessStarterActivity(children);
                } else if (children.getNodeName().equals("tibex:activityExtension") || children.getNodeName().equals("tibex:extActivity")) {
                    Activity activity = new Activity(this);
                    activities.add(activity);
                    if (group != null) {
                        group.getActivities().add(activity);
                    }
                    activity.setNode(children);
                    activity.parseActivityConfiguration(transitionMap, synonymsGroupMapping);
                    activity.parseProperties();
                    activity.parseTransitions();                                

                } else if (parent.getNodeName().equals(BPWSRETHROW) || parent.getNodeName().equals(BPWSCOMPENSATE) || parent.getNodeName().equals(BPWSTHROW) || parent.getNodeName().equals(BPWSEXIT) || parent.getNodeName().equals(BPWSREPLY) || parent.getNodeName().equals(BPWSINVOKE) || (parent.getNodeName().equals(BPWSEMPTY) && parent.getAttributes().getNamedItem(TIBEXGROUP) == null)) {
                    Activity activity = new Activity(this);
                    activities.add(activity);
                    if (group != null) {
                        group.getActivities().add(activity);
                    }
                    activity.setNode(parent);
                    activity.setName(parent.getAttributes().getNamedItem("name").getTextContent());
                    activity.parseActivityConfiguration(transitionMap, synonymsGroupMapping);
                    activity.parseProperties();
                    activity.parseTransitions();
                                if(activity.getType() == null){
                activity.setType(parent.getNodeName());
            }
                } else if (parent.getNodeName().equals(BPWSEMPTY) && parent.getAttributes().getNamedItem(TIBEXGROUP) != null) {
                    parseTranstionFromToGroups(parent);
                }
            }
        }

    }

    public void parseProcessStarterActivity(Node processStarter) {
        EventSource eventSource = new EventSource(this);
        eventSources.add(eventSource);
        activities.add(eventSource);
        
        eventSource.setNode(processStarter);
        eventSource.setName(processStarter.getAttributes().getNamedItem("name").getTextContent());
        LOG.debug("EventSource detected ["+eventSource.getName()+"]");
        eventSource.parseTransitions();
        for (int i = 0; i < processStarter.getChildNodes().getLength(); i++) {
            if (processStarter.getChildNodes().item(i).getNodeName().equals("bpws:sources")) {
                String transitionName = processStarter.getChildNodes().item(i).getChildNodes().item(1).getAttributes().getNamedItem(LINK_NAME).getTextContent();
                Transition transition = transitionMap.get(transitionName);
                setTransitionActivity(transition, processStarter.getAttributes().getNamedItem("name").getTextContent(), true,false);
            }
            if (processStarter.getChildNodes().item(i).getNodeName().equals("tibex:eventSource")) {
                if (processStarter.getChildNodes().item(i).getChildNodes().item(1).getAttributes().getNamedItem("activityTypeID") == null) {
                    eventSource.setType("bw.Start");
                } else {
                    eventSource.setType(processStarter.getChildNodes().item(i).getChildNodes().item(1).getAttributes().getNamedItem("activityTypeID").getNodeValue());
                }

            }
        }
    }

    public int countAllGroups() {
        int result = 0;
        result += getGroups().size();
        for (Group g : getGroups()) {
            result += g.countAllSubGroups();
        }
        return result;
    }

    public int countAllActivities() {
        int result = 0;
        result += getActivities().size();
        return result;
    }

    public int countAllTransitions() {
        int result = 0;
        Map<String, Transition> map = getTransitions();
        result += map.size();
        for (Group g : getGroups()) {
            result += g.countAllSubTransitions();
        }
        return result;
    }

    public void calculateVariables() {
        NodeList transitionNodeList = XmlHelper.evaluateXPath(processXmlDocument.getDocumentElement(), "/process/variables/variable");
        if (transitionNodeList != null) {
            for (int j = 0; j < transitionNodeList.getLength(); j++) {
                Variable procVar = new Variable();
                String nameElement = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "name");
                String internal = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "sca-bpel:internal");
                String privateProperty = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "sca-bpel:privateProperty");
                String source = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "tibex:propertySource");
                procVar.setName(nameElement);
                procVar.setInternal(Boolean.parseBoolean(internal));
                procVar.setProperty(Boolean.parseBoolean(privateProperty));
                if (source != null && !source.isEmpty()) {
                    procVar.setSource(true);
                    procVar.setValue(source);
                } else {
                    Element result = XmlHelper.evalueXPathSingleElement((Element) transitionNodeList.item(j), "from/literal");
                    if (result != null) {
                        procVar.setValue(result.getTextContent());
                    }
                }
                LOG.debug("Variable name: " + procVar.getName() + " and value: " + procVar.getValue() + " source: " + procVar.isSource());
                variables.add(procVar);
            }
        }
    }
    
   
    public int getEventSourcesCount() {
        return eventSources.size();
    }

    public List<EventSource> getEventSources() {
        return eventSources;
    }

    public Transition getTransitionByName(String linkName) {
        return transitionMap.get(linkName);
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the namespace
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * @param namespace the namespace to set
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * @return the modifiers
     */
    public String getModifiers() {
        return modifiers;
    }

    /**
     * @param modifiers the modifiers to set
     */
    public void setModifiers(String modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * @return the stateless
     */
    public boolean isStateless() {
        return stateless;
    }

    /**
     * @param stateless the stateless to set
     */
    public void setStateless(boolean stateless) {
        this.stateless = stateless;
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
     * @return the hasTest
     */
    public boolean isHasTest() {
        return hasTest;
    }

    /**
     * @param hasTest the hasTest to set
     */
    public void setHasTest(boolean hasTest) {
        this.hasTest = hasTest;
    }

    public Element getNode() {
        return this.processXmlDocument.getDocumentElement();
    }

    /**
     * @return the variables
     */
    public List<Variable> getVariables() {
        return variables;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public boolean belongActivityToGroup(Activity activity) {
       if(groups != null){
           for(Group group : groups){
               if(group.hasActivity(activity)){
                   return true;
               }
           }
       }
       return false;
    }
}
