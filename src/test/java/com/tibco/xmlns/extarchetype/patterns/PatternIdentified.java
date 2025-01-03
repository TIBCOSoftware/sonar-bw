package com.tibco.xmlns.extarchetype.patterns;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatternIdentified")
public class PatternIdentified extends Pattern {

	public PatternIdentified(){
		super();
	}
	
	public String toString() {
		return name;
	}

	public PatternIdentified(Pattern pattern) {
		this.name = pattern.getName();
		this.description = pattern.getDescription();
		this.components = pattern.getComponents();
		this.behaviour = pattern.getBehaviour();
	}
	
	public PatternIdentified clone(){
		PatternIdentified cloned = new PatternIdentified();
		cloned.components = this.components;	
		cloned.name = this.name;
		cloned.description = this.description;
		cloned.behaviour = this.behaviour;
		return cloned;
	}

}
