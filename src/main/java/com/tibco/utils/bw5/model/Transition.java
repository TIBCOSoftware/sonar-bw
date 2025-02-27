 /*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

 package com.tibco.utils.bw5.model;
 
 import org.w3c.dom.Node;
 
 public class Transition {
   protected String from;
   protected String to;
   
   public enum Child {
     from, 
     to, 
     conditionType,
     xpathDescription,
     xpath;
     
     Child() {}
   }
   
   protected String conditionType;
   protected String xpath;
   protected String label;
   protected Node node;
   public String getFrom()
   {
     return this.from;
   }
   
   public void setFrom(String from) {
     this.from = from;
   }
   
   public String getTo() {
     return this.to;
   }
   
   public void setTo(String to) {
     this.to = to;
   }
   
   public String getConditionType() {
     return this.conditionType;
   }
   
   public void setConditionType(String conditionType) {
     this.conditionType = conditionType;
   }
   
   public String getXpath() {
     return this.xpath;
   }
   
   public void setXpath(String xpath) {
     this.xpath = xpath;
   }
   
   public Node getNode() {
     return this.node;
   }
   
   public void setNode(Node node) {
     this.node = node;
   }
   
   public void parse() {
     org.w3c.dom.NodeList children = this.node.getChildNodes();
     for (int index = 0; index < children.getLength(); index++) {
       Node child = children.item(index);
       parseChild(child);
     }
   }
   
   private void parseChild(Node node) {
     Child child;
     try {
       child = Child.valueOf(node.getLocalName());
     } catch (Exception e) {
       return;
     }
     switch (child) {
     case from: 
       setFrom(node.getTextContent());
       break;
     case to: 
       setTo(node.getTextContent());
       break;
     case conditionType: 
       setConditionType(node.getTextContent());
       break;
     case xpath: 
       setXpath(node.getTextContent());
       break;
       case xpathDescription:
         setLabel(node.getTextContent());
         break;
     }
   }

   public void setLabel(String textContent) {
     this.label = textContent;
   }

   public String getLabel(){
     return label;
   }


 }
