 package com.tibco.utils.bw5.model;
 
 import org.w3c.dom.Node;
 
 public class Transition {
   protected String from;
   protected String to;
   
   public enum Child {
     from, 
     to, 
     conditionType, 
     xpath;
     
     Child() {}
   }
   
   protected String conditionType;
   protected String xpath;
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
       child = Child.valueOf(node.getNodeName());
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
     }
   }
 }
