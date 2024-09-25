/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import org.w3c.dom.Element;
import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Activity extends ProcessNode {

    private static final Logger LOG = Loggers.get(ProcessNode.class);
    
    protected Map<String, String> activityConfiguration;
    protected Process process;
    protected List<Transition> inputTransitions;
    protected List<Transition> outputTransitions;

    public Activity(Process process) {
        this.process = process;
        inputTransitions = new ArrayList<Transition>();
        outputTransitions = new ArrayList<Transition>();
    }

    public Element getConfiguration() {
        return XmlHelper.firstChildElement((Element) getNode(), "config");
    }

    public Element getInputBindings() {
        return XmlHelper.firstChildElement((Element) getNode(), "inputBindings");
    }

    public Map<String, String> getProperties() {
        if (activityConfiguration == null) {
            activityConfiguration = parseProperties();
        }
        return activityConfiguration;
    }

    public boolean hasProperty(String propName) {
        Map<String, String> properties = getProperties();
        if (properties != null) {
            return properties.containsKey(propName);
        }
        return false;
    }

    public Map<String, String> parseProperties() {
        if (activityConfiguration == null) {
            Map<String, String> out = new HashMap<>();
            Node exActivityConfiguration = XmlHelper.evaluateXpathNode(getNode(), ".//BWActivity/activityConfig/properties/value");
            if (exActivityConfiguration != null) {
                NamedNodeMap attrList = exActivityConfiguration.getAttributes();
                if (attrList != null) {
                    for (int i = 0; i < attrList.getLength(); i++) {
                        String propName = attrList.item(i).getNodeName();
                        String propValue = attrList.item(i).getNodeValue();
                        out.put(propName, propValue);
                    }
                }
            }
            activityConfiguration = out;
        }
        return activityConfiguration;
    }

    public String getProperty(String propName) {
        Map<String, String> properties = getProperties();
        if (properties != null) {
            return properties.get(propName);
        }
        return null;
    }

    /**
     * @param activityConfiguration the activityConfiguration to set
     */
    public void setActivityConfiguration(Map<String, String> activityConfiguration) {
        this.activityConfiguration = activityConfiguration;
    }

    /**
     * @return the process
     */
    public Process getProcess() {
        return process;
    }

    /**
     * @return the incomingTransitions
     */
    public List<Transition> getInputTransitions() {
        return inputTransitions;
    }

    /**
     * @param incomingTransitions the incomingTransitions to set
     */
    public void setInputTransitions(List<Transition> incomingTransitions) {
        this.inputTransitions = incomingTransitions;
    }

    /**
     * @return the outcomingTransitions
     */
    public List<Transition> getOutputTransitions() {
        return outputTransitions;
    }

    /**
     * @param outcomingTransitions the outcomingTransitions to set
     */
    public void setOutputTransitions(List<Transition> outcomingTransitions) {
        this.outputTransitions = outcomingTransitions;
    }

    void addOutputTransitions(Transition aThis) {
        if (!outputTransitions.contains(aThis)) {
            outputTransitions.add(aThis);
        }
    }

    void addInputTransitions(Transition aThis) {
        if (!inputTransitions.contains(aThis)) {
            inputTransitions.add(aThis);
        }
    }

    public void parseTransitions() {
            String logStartphrase = "Activity [";

            NodeList targetList = XmlHelper.evaluateXpathNodeSet(getNode(), "targets/target");
            if (targetList != null) {
                for (int i = 0; i < targetList.getLength(); i++) {
                    String linkName = XmlHelper.getAttributeValue((Element) targetList.item(i), "linkName");
                    Transition t = process.getTransitionByName(linkName);
                    if (t != null) {
                        process.setTransitionActivity(t, name, false,false);
                    }
                }
            }

            LOG.debug(logStartphrase + name+"]. Source Invocations. START");
            NodeList sourceList = XmlHelper.evaluateXpathNodeSet(getNode(), "sources/source");
            if (sourceList != null) {
                for (int i = 0; i < sourceList.getLength(); i++) {
                    String linkName = XmlHelper.getAttributeValue((Element) sourceList.item(i), "linkName");
                    LOG.debug(logStartphrase + name+"]. Source Invocations. Link Name ["+linkName+"] START");
                    Transition t = process.getTransitionByName(linkName);
                    if (t != null) {                       
                        process.setTransitionActivity(t, name, true,false);
                    }
                    LOG.debug(logStartphrase + name+"]. Source Invocations. Link Name ["+linkName+"] END");
                    
                    
                }
            }
            LOG.debug(logStartphrase +name+"]. Source Invocations. END");

        
    }

    public boolean hasParallelFlow() {
       if(outputTransitions != null){
           if(outputTransitions.size() > 1){
               int out = 0;
               for(Transition tr : outputTransitions){
                   if(!"SUCCESSWITHNOCONDITION".equals(tr.getConditionType())){
                       out++;
                   }
               }
               return out > 1;
           }
       }
       return false;
    }

    public List<List<Activity>> checkFlowArray(boolean includeOtherwise) {
        List<List<Activity>> out = new ArrayList<>();
        List<Activity> tmp = new ArrayList<>();
        checkFlowAppr(this,tmp,out,includeOtherwise);
        return out;
    }

    
    private void checkFlowAppr(Activity activity, List<Activity> activityList, List<List<Activity>> out, boolean includeOtherwise){
        if(activity != null){
            activityList.add(activity);
            if(activity.getOutputTransitions().isEmpty()){
                out.add(activityList);
            }else{
                for(Transition tr : activity.getOutputTransitions()){
                    if(!"SUCCESSWITHNOCONDITION".equals(tr.getConditionType()) || includeOtherwise){
                        List<Activity> tmp = (List<Activity>) ((ArrayList<Activity>) activityList).clone();
                        checkFlowAppr(tr.getToActivity(), tmp, out,includeOtherwise);
                    }
                }
            }
            
        }
    }
    
}
