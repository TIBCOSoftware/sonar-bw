/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import com.tibco.utils.common.helper.XmlHelper;
import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Group extends Activity {

    private static final Logger LOG = Loggers.get(Group.class);

    protected List<Activity> activities = new ArrayList<>();
    protected List<Group> groups = new ArrayList<>();
    protected List<Transition> transitions = new ArrayList<>();
    protected List<Variable> variables = new ArrayList<>();
    protected Map<String,String> expresions = new HashMap<>();

    public Group(Process process) {
        super(process);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }

    public List<Activity> getActivitiesByType(String activityType) {
        List<Activity> result = new ArrayList<>();
        for (Activity activity : activities) {
            if (activity.getType().equals(activityType)) {
                result.add(activity);
            }
        }
        for (Group group : groups) {
            List<Activity> candidates = group.getActivitiesByType(activityType);
            result.addAll(candidates);
        }
        return result;
    }

    public void calculateVariables() {
        
        NodeList transitionNodeList = XmlHelper.evaluateXPath((Element) node, "variables/variable");
        if (transitionNodeList != null) {
            for (int j = 0; j < transitionNodeList.getLength(); j++) {
                Variable procVar = new Variable();
                String name = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "name");
                String internal = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "sca-bpel:internal");
                String privateProperty = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "sca-bpel:privateProperty");
                String source = XmlHelper.getAttributeValue((Element) transitionNodeList.item(j), "tibex:propertySource");
                procVar.setName(name);
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
    
    
    public int countAllSubGroups() {
        int result = 0;
        result += getGroups().size();
        for (Group g : getGroups()) {
            result += g.countAllSubGroups();
        }
        return result;
    }

    public int countAllSubActivities() {
        int result = 0;
        result += getActivities().size();
        for (Group g : getGroups()) {
            result += g.countAllSubActivities();
        }
        return result;
    }

    public int countAllSubTransitions() {
        int result = 0;
        result += getTransitions().size();
        for (Group g : getGroups()) {
            result += g.countAllSubTransitions();
        }
        return result;
    }

    public Activity getActivityByName(String activityName) {
        for (Activity activity : activities) {
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

    public Activity getFirstActivity() {
        if (!activities.isEmpty()) {
            Activity activity = activities.get(0);
            return getFirstActivity(activity);
        }

        return null;
    }

    protected Activity getFirstActivity(Activity activity) {
        if (activity != null) {
            if (activity.getInputTransitions().isEmpty()) {
                return activity;
            }else{
                Transition tr = activity.getInputTransitions().get(0);                
                return getFirstActivity(tr.getFromActivity());
            }
        }
        return null;
    }

    protected void calculateExpressions() {
        
            Element expressionNode = XmlHelper.evalueXPathSingleElement((Element) node, "//expression");
        if(expressionNode != null){
            String expression = XmlHelper.getAttributeValue(expressionNode, "expression");
            expresions.put("expression", expression);
        }
        
        expressionNode = XmlHelper.evalueXPathSingleElement((Element) node, "*/*[contains(name(),'startCounterValue')]");
        if(expressionNode != null){           
            expresions.put("startCounterValue", expressionNode.getTextContent());
        }
        
        expressionNode = XmlHelper.evalueXPathSingleElement((Element) node, "*/*[contains(name(),'finalCounterValue')]");
        if(expressionNode != null){           
            expresions.put("finalCounterValue", expressionNode.getTextContent());
        }
        
        
    }

    public boolean hasActivity(Activity activity) {
        for(Activity act : activities){
            if(act != null && act.equals(activity)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * @return the incomingTransitions
     */
    @Override
    public List<Transition> getInputTransitions() {
        List<Transition> tmp = new ArrayList<>();
        for(Transition tr : inputTransitions){
            if(!tr.isIsGroupInternal()){
                tmp.add(tr);
            }
        }
        return tmp;
    }

    @Override
    public List<Transition> getOutputTransitions() {
        List<Transition> tmp = new ArrayList<>();
        for(Transition tr : outputTransitions){
            if(!tr.isIsGroupInternal()){
                tmp.add(tr);
            }
        }
        return tmp;
    }
}
