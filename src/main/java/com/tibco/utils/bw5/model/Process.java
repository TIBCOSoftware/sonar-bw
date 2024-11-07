/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

 package com.tibco.utils.bw5.model;

 import org.w3c.dom.Document;
 import org.w3c.dom.Node;
 import org.w3c.dom.NodeList;

 import java.util.ArrayList;
 import java.util.List;
 
 public class Process
 {
   protected String name;

   public List<Transition> getOutputTransitions(Activity activity) {
     List<Transition> out = new ArrayList<>();
     if(activity != null){

       String tName = activity.getName();
       for (Transition t : transitions){
         if(tName.equals(t.getFrom())){
           out.add(t);
         }
       }
     }
     return out;
   }

     public boolean isSubprocess() {
        return starter.getNode() == null;
     }

     public enum ElementsName
   {
     group, 
     activity, 
     transition, 
     name, 
     startName, 
     startX, 
     startY, 
     starter, 
     endName, 
     endX, 
     endY;
     
     ElementsName() {} }
   
   protected Starter starter = new Starter();
   protected Ender ender = new Ender();
   protected List<Activity> activities = new ArrayList<>();
   protected List<Group> groups = new ArrayList<>();
   protected List<Transition> transitions = new ArrayList<>();
   
 
   protected Document processXmlDocument;
   
 
   public Starter getStarter()
   {
     return this.starter;
   }
   
   public void setStarter(Starter starter) {
     this.starter = starter;
   }
   
   public Ender getEnder() {
     return this.ender;
   }
   
   public void setEnder(Ender ender) {
     this.ender = ender;
   }
   
   public String getName() {
     return this.name;
   }
   
   public Process setName(String name) {
     this.name = name;
     return this;
   }
   
   public List<Activity> getActivities() {
     return this.activities;
   }
   
   public Process setActivities(List<Activity> activities) {
     this.activities = activities;
     return this;
   }
   
   public Activity getActivityByName(String activityName) {
     for (Activity activity : this.activities) {
       if (activity.getName().equals(activityName)) {
         return activity;
       }
     }
     for (Group group : this.groups)
     {
       Activity candidate = group.getActivityByName(activityName);
       if (candidate != null) {
         return candidate;
       }
     }
     return null;
   }
   
   public List<Activity> getActivitiesByType(String activityType) {
     List<Activity> result = new ArrayList<>();
     for (Activity activity : this.activities) {
       if (activity.getType().equals(activityType)) {
         result.add(activity);
       }
     }
     for (Group group : this.groups) {
       List<Activity> candidates = group.getActivitiesByType(activityType);
       result.addAll(candidates);
     }
     return result;
   }
   
   public List<Group> getGroups() {
     return this.groups;
   }
   
   public Process setGroups(List<Group> groups) {
     this.groups = groups;
     return this;
   }
   
   public List<Transition> getTransitions() {
     return this.transitions;
   }
   
   public Process setTransitions(List<Transition> transitions) {
     this.transitions = transitions;
     return this;
   }
   
   public Document getProcessXmlDocument() {
     return this.processXmlDocument;
   }
   
   public Process setProcessXmlDocument(Document processXmlDocument) {
     this.processXmlDocument = processXmlDocument;
     return this;
   }


   public Process parse() {
     NodeList children = this.processXmlDocument.getDocumentElement().getChildNodes();
     
     int size = children.getLength();
     for (int index = 0; index < size; index++) {
       Node child = children.item(index);
       parseChild(child);
     }
     return this;
   }
   
   private void parseChild(Node node) {
     ElementsName element;
     try {
       element = ElementsName.valueOf(node.getLocalName());
     } catch (Exception e) {
       return;
     }
     switch (element) {
     case name: 
       setName(node.getTextContent());
       break;
     case startName: 
       this.starter.setNodeName(node);
       this.starter.setName(node.getTextContent());
       break;
     case startX: 
       this.starter.setX(Integer.parseInt(node.getTextContent()));
       break;
     case startY: 
       this.starter.setY(Integer.parseInt(node.getTextContent()));
       break;
     case starter: 
       this.starter.setNode(node);
       this.starter.parse();
       break;
     case endName: 
       this.ender.setNodeName(node);
       this.ender.setName(node.getTextContent());
       break;
     case endX: 
       this.ender.setX(Integer.parseInt(node.getTextContent()));
       break;
     case endY: 
       this.ender.setY(Integer.parseInt(node.getTextContent()));
       break;
     case activity: 
       Activity activity = new Activity();
       activity.setNode(node);
       activity.parse();
       this.activities.add(activity);
       break;
     case group: 
       Group group = new Group();
       group.setNode(node);
       group.parse();
       this.groups.add(group);
       break;
     case transition: 
       Transition transition = new Transition();
       transition.setNode(node);
       transition.parse();
       this.transitions.add(transition);
       break;
     }
     
   }
   
   public int countAllGroups()
   {
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
     for (Group g : getGroups()) {
       result += g.countAllSubActivities();
     }
     return result;
   }
   
   public int countAllTransitions() {
     int result = 0;
     result += getTransitions().size();
     for (Group g : getGroups()) {
       result += g.countAllSubTransitions();
     }
     return result;
   }
 }

