/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
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
