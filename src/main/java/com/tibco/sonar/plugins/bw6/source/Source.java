package com.tibco.sonar.plugins.bw6.source;

import org.sonar.api.batch.fs.InputComponent;

public interface Source {

	public void setCode(String code);
	
	public String getCode();
        
        public InputComponent getComponent();
	

}