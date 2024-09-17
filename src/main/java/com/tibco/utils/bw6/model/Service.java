/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.utils.bw6.model;

import java.util.HashMap;
import java.util.Map;

public class Service {
	protected String name;
	protected String namespace;
	protected Map<String, Operation> operations = new HashMap<>();
	protected String implementationProcess;
	protected String inline;        
        protected Binding binding;
	
	public String getInline() {
		return inline;
	}

	public void setInline(String inline) {
		this.inline = inline;
	}

	public String getImplementationProcess() {
		return implementationProcess;
	}

	public void setImplementationProcess(String implementationProcess) {
		this.implementationProcess = implementationProcess;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	
	public void setOperations(Map<String, Operation> operations) {
		this.operations = operations;
	}

	public Map<String, Operation> getOperations() {
		return this.operations;
	}
	public Service(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

    /**
     * @return the binding
     */
    public Binding getBinding() {
        return binding;
    }

    /**
     * @param binding the binding to set
     */
    public void setBinding(Binding binding) {
        this.binding = binding;
    }

	
}
