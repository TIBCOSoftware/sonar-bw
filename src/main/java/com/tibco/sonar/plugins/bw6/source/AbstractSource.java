package com.tibco.sonar.plugins.bw6.source;

public abstract class AbstractSource implements Source {
    
	protected String code;
	
	protected AbstractSource(){
	}
	
        @Override
	public void setCode(String code) {
		this.code = code;
	}
	
        @Override
	public String getCode() {
		return this.code;
	}
	
}
