 package com.tibco.xmlns.repo.types._2002;
 
 import javax.xml.bind.annotation.XmlAccessType;
 import javax.xml.bind.annotation.XmlAccessorType;
 import javax.xml.bind.annotation.XmlElement;
 import javax.xml.bind.annotation.XmlType;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 @XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name="repositoryType", propOrder={"globalVariables"})
 public class RepositoryType
 {
   @XmlElement(required=true)
   protected GlobalVariablesType globalVariables;
   
   public GlobalVariablesType getGlobalVariables()
   {
     return this.globalVariables;
   }
   
 
 
 
 
 
 
 
   public void setGlobalVariables(GlobalVariablesType value)
   {
     this.globalVariables = value;
   }
 }

