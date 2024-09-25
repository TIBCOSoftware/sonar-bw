/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw.source;

import org.sonar.api.batch.fs.InputComponent;

public interface Source {

	void setCode(String code);
	
	String getCode();
        
	InputComponent getComponent();
	

}