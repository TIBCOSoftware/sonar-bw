 package com.tibco.utils.bw5.model;
 
 import org.w3c.dom.Node;
 
 public class Ender extends Activity
 {
   public Node nodeName;
   
   public Ender()
   {
     this.type = "end";
     this.name = "End";
   }
   
   public void setNodeName(Node node) {
     this.nodeName = node;
   }
   
   public Node getNodeName() {
     return this.nodeName;
   }
 }
