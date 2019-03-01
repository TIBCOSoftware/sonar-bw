package com.tibco.sonar.plugins.bw6.source;

import org.sonarsource.analyzer.commons.xml.XmlFile;

public interface Source {


	
	public void setCode(String code);
	
	public String getCode();
        
        public XmlFile getFile();
	

	
//	@SuppressWarnings("rawtypes")
//	public Resource create(Resource parent, String key);


}