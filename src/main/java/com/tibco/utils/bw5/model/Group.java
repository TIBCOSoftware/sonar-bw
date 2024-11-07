 /*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

 package com.tibco.utils.bw5.model;
 
 import java.util.List;
 
 public class Group extends Activity {
   protected int width;
   protected int height;
   protected List<Activity> activities;
   protected List<Group> groups;
   protected List<Transition> transitions;
   
   public Group() {
     this.activities = new java.util.ArrayList<>();
     this.groups = new java.util.ArrayList<>();
     this.transitions = new java.util.ArrayList<>();
   }
   
   public int getWidth() { return this.width; }
   
   public void setWidth(int width)
   {
     this.width = width;
   }
   
   public int getHeight() {
     return this.height;
   }
   
   public void setHeight(int height) {
     this.height = height;
   }
   
   public List<Activity> getActivities() {
     return this.activities;
   }
   
   public void setActivities(List<Activity> activities) {
     this.activities = activities;
   }
   
   public List<Group> getGroups() {
     return this.groups;
   }
   
   public void setGroups(List<Group> groups) {
     this.groups = groups;
   }
   
   public List<Transition> getTransitions() {
     return this.transitions;
   }
   
   public void setTransitions(List<Transition> transitions) {
     this.transitions = transitions;
   }
   
   protected void parseChild(org.w3c.dom.Node node) {
     super.parseChild(node);
     Child child;
     try {
       child = Child.valueOf(node.getLocalName());
     } catch (Exception e) {
       return;
     }
     switch (child) {
     case width: 
       setWidth(Integer.parseInt(node.getTextContent()));
       break;
     case height: 
       setHeight(Integer.parseInt(node.getTextContent()));
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
   
 
   public enum Child
   {
     width, 
     height, 
     activity, 
     group, 
     transition;
     
     Child() {} }
   
   public int countAllSubGroups() { int result = 0;
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
   
 
 
 
 
 
 
 
 
 
 
   public List<String> getJavaScriptModelAsLines(String parentName)
   {
     java.util.ArrayList<String> result = new java.util.ArrayList<>();
     result.addAll(getJavaScriptModelAsLine(parentName));
     for (Activity activity : getActivities()) {
       result.addAll(activity.getJavaScriptModelAsLine(getName()));
     }
     for (Group group : getGroups()) {
       result.addAll(group.getJavaScriptModelAsLines(getName()));
     }
     return result;
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
     List<Activity> result = new java.util.ArrayList<>();
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
 }

