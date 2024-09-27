/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

 package com.tibco.utils.bw5.model;
 
 import java.util.ArrayList;
 import java.util.List;

 import com.tibco.utils.common.helper.XmlHelper;
 import org.w3c.dom.Element;
 
 public class Activity
   extends ProcessNode
 {
   public Element getConfiguration()
   {
     return XmlHelper.firstChildElement((Element)getNode(), "config");
   }
   
   public Element getInputBindings() {
     return XmlHelper.firstChildElement((Element)getNode(), "inputBindings");
   }
   
 
 
 
 
 
   public List<String> getJavaScriptModelAsLine()
   {
     return getJavaScriptModelAsLine(null);
   }
   
 
 
 
 
 
 
   public List<String> getJavaScriptModelAsLine(String parentName)
   {
     ArrayList<String> result = new ArrayList<>();
     StringBuilder line = new StringBuilder();
     
     line.append("{");
     
     line.append("name: \"").append(getName()).append("\",");
     
     line.append("type: \"").append(getType()).append("\",");
     
     line.append("class: \"").append(getClass().getSimpleName()).append("\",");
     
     if ((parentName != null) && (!parentName.isEmpty())) {
       line.append("parent: \"").append(parentName).append("\",");
     }
     line.append("x: ").append(getX()).append(",");
     
     line.append("y: ").append(getY());
     
     line.append("}");
     result.add(line.toString());
     return result;
   }
 }

