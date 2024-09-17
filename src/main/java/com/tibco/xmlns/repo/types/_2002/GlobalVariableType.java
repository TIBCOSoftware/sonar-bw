package com.tibco.xmlns.repo.types._2002;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "globalVariableType", propOrder = { "name", "value", "deploymentSettable", "serviceSettable", "type",
		"modTime" })
public class GlobalVariableType {
	@XmlElement(required = true)
	protected String name;
	@XmlElement(required = true)
	protected String value;
	@XmlElement(required = true)
	protected String deploymentSettable;
	@XmlElement(required = true)
	protected String serviceSettable;
	@XmlElement(required = true)
	protected String type;
	protected long modTime;

	public String getName() {
		return this.name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDeploymentSettable() {
		return this.deploymentSettable;
	}

	public void setDeploymentSettable(String value) {
		this.deploymentSettable = value;
	}

	public String getServiceSettable() {
		return this.serviceSettable;
	}

	public void setServiceSettable(String value) {
		this.serviceSettable = value;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public long getModTime() {
		return this.modTime;
	}

	public void setModTime(long value) {
		this.modTime = value;
	}
}
