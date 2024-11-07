/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.check;

import java.util.List;
import java.util.NoSuchElementException;

import com.tibco.utils.common.helper.XmlHelper;
import org.w3c.dom.Element;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import com.tibco.utils.bw5.model.Activity;
import com.tibco.utils.bw5.model.Process;

public abstract class AbstractProcessCatchCheck extends AbstractProcessCheck {
	
	public abstract String getCatchFaultElementValue();
	public abstract void setCatchFaultElementValue(String catchFaultElementValue);

	public abstract String getCatchFaultElementName();
	public abstract void setCatchFaultElementName(String catchFaultElementName);

	public abstract String getCatchAllElementValue();
	public abstract void setCatchAllElementValue(String catchAllElementValue);

	public abstract String getCatchAllElementName();
	public abstract void setCatchAllElementName(String catchAllElementName);

	public abstract String getCatchActivityType();
	public abstract void setCatchActivityType(String catchActivityType);

	public abstract String getNotFoundMessage();
	public abstract void setNotFoundMessage(String notFoundMessage);

	public abstract String getNoCatchMessage();
	public abstract void setNoCatchMessage(String noCatchMessage);
	
	@Override
	protected void validate(ProcessSource processSource) {
		// Get process
		Process process = processSource.getProcessModel();
		// Get all catch activities
		List<Activity> activitiesCatch = process
				.getActivitiesByType(getCatchActivityType());
		// If no catch activity found raise a violation
		if (activitiesCatch.isEmpty()) {
			reportIssueOnFile(getNoCatchMessage());
		// Else parse catch activities
		} else {
			boolean catchFound = false;
			// For each catch activity found
			for (Activity activity : activitiesCatch) {
				catchFound = checkCatch(activity) || catchFound;
			}

			// if rule's catch not found raise a violation
			if (!catchFound) {
				reportIssueOnFile(getNotFoundMessage());
			}

		}
	}

	private boolean checkCatch(Activity activity){
			// try to retrieve catchAll element in configuration
		boolean catchAllFound = false;
		try{
				Element catchAllConfigElement = XmlHelper.firstChildElement(
						activity.getConfiguration(),getCatchAllElementName());
				// if catchAll found and value equal to activation value
				if (catchAllConfigElement.getTextContent().equals(getCatchAllElementValue())) {
					// then catch all found
					catchAllFound = true;
				}
			}catch(NoSuchElementException e){
				// else catch all not found
				catchAllFound = false;
			}
			// if catch all found
			if(catchAllFound){
				// and no specifi fault element defined
				if(getCatchFaultElementValue() == null || getCatchFaultElementValue().isEmpty()){
					return catchAllFound;
				}
				// but if catch all not found
			}else{
				// if the catch activity is configured to catch a specific fault
				if(getCatchFaultElementValue() != null && !getCatchFaultElementValue().isEmpty()){
					// check if this specific fault is the rule's one
					return findFaultCatch(activity);
				}
			}

		return false;
		}


	
	private boolean findFaultCatch(Activity activity){
		// init catch not found
		boolean catchFound = false;
		// if specific fault element (based on name and value) is searched
		if(!getCatchFaultElementName().isEmpty() && !getCatchFaultElementValue().isEmpty()){
			// get fault element 
			Element catchFaultConfigElement = XmlHelper.firstChildElement(
                    activity.getConfiguration(),getCatchFaultElementName());
			// if fault value is equal to what we are looking for
			if (getCatchFaultElementValue().equals(catchFaultConfigElement.getTextContent())) {
				// set catch found
				catchFound = true;
			}
		}
		return catchFound;
	}

	
}
