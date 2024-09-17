/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.model;

import com.tibco.utils.common.helper.XmlHelper;
import java.util.Map;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class ProcessNode {

    protected String type;
    protected String name;
    protected Node node;
    protected String expression;


    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void parseActivityConfiguration(Map<String, Transition> transitions, Map<String, String> synonymsGroupMapping) {
        if (node != null) {
            setType(XmlHelper.getAttributeValue((Element) node, "type"));
            setName(XmlHelper.getAttributeValue((Element) node, "name"));

            Node inputBinding = XmlHelper.evaluateXpathNode(node, ".//inputBindings/inputBinding");
            if(inputBinding != null){
                setExpression(XmlHelper.getAttributeValue((Element) inputBinding, "expression"));
            }
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeName().equals("tibex:config")) {
                    Node bwactivityConfig = child.getChildNodes().item(1);
                    setType(bwactivityConfig.getAttributes().getNamedItem("activityTypeID").getTextContent());
                    break;
                } else if (child.getNodeName().equals("bpws:targets")) {
                    parseActivityOutcomingTransitions(child, synonymsGroupMapping, transitions, name);
                } else if (child.getNodeName().equals("bpws:sources")) {
                    parseActivityIncomingTransitions(child, synonymsGroupMapping, transitions, name);
                }
            }

        }
    }

    private void parseActivityOutcomingTransitions(Node child, Map<String, String> synonymsGroupMapping, Map<String, Transition> transitions, String name) {
        NodeList transitionsTo = child.getChildNodes();
        for (int j = 0; j < transitionsTo.getLength(); j++) {
            if (transitionsTo.item(j).getNodeName().equals("bpws:target")) {
                parseActivityTransition(transitionsTo, j, synonymsGroupMapping, transitions, name,false);
            }
        }
    }

    private Transition  setGroupTransition(String grouptransition, String grouptransition2, NodeList transitionsTo, int j, Map<String, Transition> transitions,  String name1, boolean defaultIncoming)
    {   Transition transition;
        if (grouptransition.contains("GroupStart")) {
            transition = transitions.get(grouptransition2);
            if(grouptransition2.indexOf("To") >= 0){
                grouptransition2 = grouptransition2.substring(0, grouptransition2.indexOf("To"));
            }
            transition.getProcess().setTransitionActivity(transition, grouptransition2, true,true);
        } else if (grouptransition.contains("GroupEnd")) {
            transition = transitions.get(grouptransition2);
            if(grouptransition2.indexOf("To") >= 0){
                grouptransition2 = grouptransition2.substring(grouptransition2.indexOf("To") + 2);
            }
            transition.getProcess().setTransitionActivity(transition, grouptransition2, false,true);
        } else {
            transition = transitions.get(transitionsTo.item(j).getAttributes().getNamedItem("linkName").getTextContent());
            transition.getProcess().setTransitionActivity(transition, name1, defaultIncoming,false);
        }

        return transition;
    }

    private void parseActivityTransition(NodeList transitionsTo, int j, Map<String, String> synonymsGroupMapping, Map<String, Transition> transitions, String name1, boolean defaultIncoming) {
        Transition transition;
        String grouptransition = transitionsTo.item(j).getAttributes().getNamedItem("linkName").getNodeValue();
        String grouptransition2 = synonymsGroupMapping.get(grouptransition);
        if (grouptransition != null && grouptransition2 != null) {

            transition = setGroupTransition(grouptransition, grouptransition2, transitionsTo, j, transitions, name1, defaultIncoming );

            NodeList expressions = transitionsTo.item(j).getChildNodes();
            if (expressions.item(1) != null && expressions.item(1).getChildNodes() != null && expressions.item(1).getChildNodes().item(1) != null) {
                String xpathExpression = expressions.item(1).getChildNodes().item(1).getAttributes().getNamedItem("expression").getTextContent();
                transition.setXpath(xpathExpression);
            }
        }
    }

    private void parseActivityIncomingTransitions(Node child, Map<String, String> synonymsGroupMapping, Map<String, Transition> transitions, String name) {
        NodeList transitionsTo = child.getChildNodes();
        for (int j = 0; j < transitionsTo.getLength(); j++) {
            if (transitionsTo.item(j).getNodeName().equals("bpws:source")) {
                parseActivityTransition(transitionsTo, j, synonymsGroupMapping, transitions, name,true);
            }
        }
    }



}
