 /*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

 package com.tibco.utils.bw5.model;
 
 import org.w3c.dom.Node;
 
 public abstract class ProcessNode
 {
   protected String type;
   protected String name;
   
   public enum Child {
     type, 
     x, 
     y;
     
     Child() {}
   }
   
   protected int x = 0;
   protected int y = 0;
   protected Node node;
   
   public String getType()
   {
     return this.type;
   }
   
   public void setType(String type) {
     this.type = type;
   }
   
   public String getName() {
     return this.name;
   }
   
   public void setName(String name) {
     this.name = name;
   }
   
   public int getX() {
     return this.x;
   }
   
   public void setX(int x) {
     this.x = x;
   }
   
   public int getY() {
     return this.y;
   }
   
   public void setY(int y) {
     this.y = y;
   }
   
   public Node getNode() {
     return this.node;
   }
   
   public void setNode(Node node) {
     this.node = node;
   }
   
   public void parse() {
     if (this.node != null) {
       org.w3c.dom.NamedNodeMap attributes = this.node.getAttributes();
       if (attributes != null) {
         Node nameAttribute = attributes.getNamedItem("name");
         if (nameAttribute != null) {
           setName(nameAttribute.getTextContent());
         }
       }
       org.w3c.dom.NodeList children = this.node.getChildNodes();
       if (children.getLength() > 0) {
         for (int index = 0; index < children.getLength(); index++) {
           Node child = children.item(index);
           parseChild(child);
         }
       }
     }
   }
   
   protected void parseChild(Node node) {
     Child child;
     try {
       child = Child.valueOf(node.getNodeName());
     } catch (Exception e) {
       return;
     }
     switch (child) {
     case type: 
       setType(node.getTextContent());
       break;
     case x: 
       setX(Integer.parseInt(node.getTextContent()));
       break;
     case y: 
       setY(Integer.parseInt(node.getTextContent()));
       break;
     }
   }
 }
