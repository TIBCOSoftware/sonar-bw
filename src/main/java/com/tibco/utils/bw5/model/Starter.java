 package com.tibco.utils.bw5.model;
 
 import org.w3c.dom.Node;
 
 public class Starter extends Activity
 {
   public Node nodeName;
   
   public Starter()
   {
     this.type = "start";
     this.name = "Start";
   }
   
   public void setNodeName(Node node) {
     this.nodeName = node;
   }
   
   public Node getNodeName() {
     return this.nodeName;
   }
 }

