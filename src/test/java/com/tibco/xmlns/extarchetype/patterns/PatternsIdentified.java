package com.tibco.xmlns.extarchetype.patterns;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatternsIdentified", propOrder = { "patterns" })
public class PatternsIdentified {

	@XmlElement(required = true)
	protected List<PatternIdentified> patterns;

	public PatternsIdentified() {
		patterns = new ArrayList<PatternIdentified>();
	}

	public PatternsIdentified(PatternsDescriptor patternsDescriptor) {
		patterns = new ArrayList<PatternIdentified>();
		for (Pattern pattern : patternsDescriptor.getPattern()) {
			patterns.add(new PatternIdentified(pattern));
		}
	}

	public List<PatternIdentified> getListOfPatterns() {
		if (patterns == null) {
			patterns = new ArrayList<PatternIdentified>();
		}
		return this.patterns;
	}

	public void addPattern(PatternIdentified patternIdentified) {
		if (patterns == null) {
			patterns = new ArrayList<PatternIdentified>();
		}
		if (!patterns.contains(patternIdentified)) {
			patterns.add(patternIdentified);
		}
	}

	public void clear() {
		if (patterns != null) {
			patterns.clear();
		}
	}

	public void setListOfPatterns(List<PatternIdentified> newPatterns) {
		clear();
		for (PatternIdentified pattern : newPatterns){
			//addPattern(pattern.clone());
			addPattern(pattern);
		}
			
	}

	/**
	 * Copy with bound list of patterns
	 * @param patternsIdentified
	 */
	public void copyOf(PatternsIdentified patternsIdentified) {
		patterns = patternsIdentified.getListOfPatterns();
	}

	public void clone(PatternsIdentified allPatterns) {
		setListOfPatterns(allPatterns.getListOfPatterns());
	}

}
