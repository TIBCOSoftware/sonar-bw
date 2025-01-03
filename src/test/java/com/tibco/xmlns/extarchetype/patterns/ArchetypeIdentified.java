package com.tibco.xmlns.extarchetype.patterns;
import com.tibco.xmlns.archetype.generator.Archetype;
import com.tibco.xmlns.archetype.generator.GAV;
import com.tibco.xmlns.extarchetype.metadata.ExtArchetypeDescriptor;
import com.tibco.xmlns.extarchetype.metadata.ExtendedReferenceProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArchetypeIdentified", propOrder = { "gav", "isPartial" })
public class ArchetypeIdentified extends ExtArchetypeDescriptor {

	
	@XmlAttribute(required = true)
	private String name;
	@XmlElement(required = true)
	private GAV gav;
	@XmlElement(required = true)
	private boolean isPartial;

	public ArchetypeIdentified() {
		super();
		this.gav = new GAV();
		this.name = "";
	}
	
	public ArchetypeIdentified(Archetype archetype) {
		this.gav = archetype.getGav();
		this.name = archetype.getName();
	}

	public boolean isPartial() {
		return isPartial;
	}

	public void setPartial(boolean isPartial) {
		this.isPartial = isPartial;
	}

	public GAV getArchetypeGAV() {
		return this.gav;
	}

	public void setGAV(GAV gav) {
		this.gav = gav;
	}

	public String getSimpleId() {
		return gav.getArtifactId() + " (" + gav.getVersion() + ")";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		String res = name+"("+gav.getGroupId()+":"+gav.getArtifactId()+":"+gav.getVersion()+")";
		return res;
	}

	public Pattern toPattern() {
		Pattern pattern = new Pattern(); 
		pattern.setBehaviour("");
		pattern.setName(this.getName());
		pattern.setDescription(this.getSimpleId());
		pattern.setComponents(new PatternComponents());
		// Component
		PatternComponent component = new PatternComponent();
		component.setArchetypeGAV(null);
		component.setName(this.getName());
		component.setDescription(this.getSimpleId());
		component.setLinkedArchetypes(new PatternArchetypes());
		// Archetype
		PatternArchetype archetype = new PatternArchetype();
		archetype.setName(this.getName());
		archetype.setDescription(this.getSimpleId());
		archetype.setArchetypeGAV(this.getArchetypeGAV());
		archetype.setParameters(new PatternProperties());
		// Properties
		if(requiredProperties != null){
			for(ExtendedReferenceProperty refProp:requiredProperties.getRequiredProperty()){
				PatternProperty property = new PatternProperty();
				property.setKey(refProp.getKey());
				property.setValidationRule(refProp.getValidationRule());
				property.setValue(null);
				property.setHidden(false);
				archetype.getParameters().getParameter().add(property);
			}
		}
		component.getLinkedArchetypes().getArchetype().add(archetype);
		pattern.getComponents().getComponent().add(component);	
		return pattern;
	}

}
