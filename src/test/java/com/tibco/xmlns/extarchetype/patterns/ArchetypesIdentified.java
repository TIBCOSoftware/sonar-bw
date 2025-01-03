package com.tibco.xmlns.extarchetype.patterns;

import com.tibco.xmlns.archetype.generator.Archetype;
import com.tibco.xmlns.archetype.generator.Archetypes;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "archetypes", propOrder = { "archetypes" })
public class ArchetypesIdentified {

	@XmlElement(required = true)
	protected List<ArchetypeIdentified> archetypes;

	public ArchetypesIdentified(Archetypes archetypes) {
		this.archetypes = new ArrayList<ArchetypeIdentified>();
		for (Archetype archetype :archetypes.getArchetype()) {
			this.archetypes.add(new ArchetypeIdentified(archetype));
		}
	}

	public ArchetypesIdentified() {
		this.archetypes = new ArrayList<ArchetypeIdentified>();
	}

	public List<ArchetypeIdentified> getListOfArchetypes() {
		if (archetypes == null) {
			archetypes = new ArrayList<ArchetypeIdentified>();
		}
		return this.archetypes;
	}
	
	public String toString(){
		String res = getClass().getName() + "@" + Integer.toHexString(hashCode());
		res += " archetypes[";
		int i = 0;
		for (ArchetypeIdentified archetype : archetypes) {
			i++;
			res += archetype.toString();
			if(i!=archetypes.size()) res += ",";
		}
		res += "]";
		return res;
	}

	public void addArchetype(ArchetypeIdentified archetypeIdentified) {
		if (archetypes == null) {
			archetypes = new ArrayList<ArchetypeIdentified>();
		}
		archetypes.add(archetypeIdentified);
	}
	
	public void clear(){
		if (archetypes != null) {
			archetypes.clear();
		}	
	}

	/**
	 * Copy with bound list of archetypes
	 * @param allArchetypes
	 */
	public void copyOf(ArchetypesIdentified allArchetypes) {
		archetypes = allArchetypes.getListOfArchetypes();	
	}

	public void setListOfArchetypes(
			List<ArchetypeIdentified> newPreferedArchetypes) {
		clear();
		for(ArchetypeIdentified archetypeIdentified:newPreferedArchetypes)
			addArchetype(archetypeIdentified);
	}

	public void clone(ArchetypesIdentified allArchetypes) {
		setListOfArchetypes(allArchetypes.getListOfArchetypes());	
	}
	
}
