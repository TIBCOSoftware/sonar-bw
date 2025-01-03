/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.bw6.model;

import java.util.ArrayList;
import java.util.List;

public class Operation {
	protected String name;
	protected List<Service> operationReferencedService = new ArrayList<>();

	public List<Service> getOperationReferencedService() {
		return operationReferencedService;
	}

	public void setOperationReferencedService(List<Service> operationReferencedService) {
		this.operationReferencedService = operationReferencedService;
	}

	public Operation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
