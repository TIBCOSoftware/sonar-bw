/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw.source;

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
