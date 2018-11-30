package com.tibco.sonar.plugins.bw6.source;

import java.nio.charset.Charset;
import java.util.List;

import com.tibco.sonar.plugins.bw6.violation.Violation;

public interface Source {

	/**
	 * Parses the source and returns true if succeeded false if the file is corrupted.
	 */
	public boolean parseSource(Charset charset);

	public List<Violation> getViolations();

	public void setCode(String code);
	
	public String getCode();
	
	public void addViolation(Violation violation);
	
//	@SuppressWarnings("rawtypes")
//	public Resource create(Resource parent, String key);


}