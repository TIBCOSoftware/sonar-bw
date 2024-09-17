/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.model;

import org.w3c.dom.Node;

public class Transition {

    protected Activity fromActivity;
    protected String from;
    protected String to;
    protected Activity toActivity;
    protected String conditionType;
    protected String xpath;
    protected Node node;
    protected String name;
    protected String label;
    protected int lineNumber;
    protected Process process;
    protected boolean isGroupInternal;
    
    public Transition(Process process){
        this.process = process;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    /**
     * @return the fromActivity
     */
    public Activity getFromActivity() {
        return fromActivity;
    }

    /**
     * @param fromActivity the fromActivity to set
     */
    public void setFromActivity(Activity fromActivity) {
        this.fromActivity = fromActivity;
        if(fromActivity != null){
            fromActivity.addOutputTransitions(this);
        }
    }

    /**
     * @return the toActivity
     */
    public Activity getToActivity() {
        return toActivity;
    }

    /**
     * @param toActivity the toActivity to set
     */
    public void setToActivity(Activity toActivity) {
        this.toActivity = toActivity;
        if(toActivity != null){
            toActivity.addInputTransitions(this);
        }
        
    }

    /**
     * @return the process
     */
    public Process getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(Process process) {
        this.process = process;
    }

    /**
     * @return the isGroupInternal
     */
    public boolean isIsGroupInternal() {
        return isGroupInternal;
    }

    /**
     * @param isGroupInternal the isGroupInternal to set
     */
    public void setIsGroupInternal(boolean isGroupInternal) {
        this.isGroupInternal = isGroupInternal;
    }
    
    

}
