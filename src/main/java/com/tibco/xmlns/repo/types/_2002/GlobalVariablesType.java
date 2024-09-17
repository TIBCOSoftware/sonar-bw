package com.tibco.xmlns.repo.types._2002;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "globalVariablesType", propOrder = { "globalVariable" })
public class GlobalVariablesType {
	@XmlElement(required = true)
	protected List<GlobalVariableType> globalVariable;

	public List<GlobalVariableType> getGlobalVariable() {
		if (this.globalVariable == null) {
			this.globalVariable = new ArrayList();
		}
		return this.globalVariable;
	}
}

