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
     Element element = XmlHelper.firstChildElement((Element)getNode(), "config");
     return element;
   }
   
   public Element getInputBindings() {
     Element element = XmlHelper.firstChildElement((Element)getNode(), "inputBindings");
     return element;
   }
   
 
 
 
 
 
   public List<String> getJavaScriptModelAsLine()
   {
     return getJavaScriptModelAsLine(null);
   }
   
 
 
 
 
 
 
   public List<String> getJavaScriptModelAsLine(String parentName)
   {
     ArrayList<String> result = new ArrayList();
     StringBuilder line = new StringBuilder();
     
     line.append("{");
     
     line.append("name: \"" + getName() + "\",");
     
     line.append("type: \"" + getType() + "\",");
     
     line.append("class: \"" + getClass().getSimpleName() + "\",");
     
     if ((parentName != null) && (parentName != "")) {
       line.append("parent: \"" + parentName + "\",");
     }
     line.append("x: " + getX() + ",");
     
     line.append("y: " + getY());
     
     line.append("}");
     result.add(line.toString());
     return result;
   }
 }

