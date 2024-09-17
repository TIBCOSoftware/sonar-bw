/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw.source;

import org.sonar.api.batch.fs.InputComponent;

public interface Source {

	void setCode(String code);
	
	String getCode();
        
	InputComponent getComponent();
	

}