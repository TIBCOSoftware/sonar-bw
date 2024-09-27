/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.sonar.plugins.bw5.source;

import com.tibco.utils.common.SaxParser;
import com.tibco.utils.bw5.helper.GvHelper;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import com.tibco.utils.common.helper.XmlHelper;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.rule.RuleKey;
import org.sonar.check.Rule;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.tibco.sonar.plugins.bw.source.XmlSource;

/**
 *
 * @author avazquez
 */
public class XmlBw5Source extends XmlSource {

        private XmlFile file;
        private SensorContext context;

	/**
	 * 
	 * This is some description that we must adhere to. 
	 * 
     * @param context TODO: Add description
	 * @param xmlFile TODO: Add description
	*/
	public XmlBw5Source(SensorContext context, XmlFile xmlFile) {
		super(xmlFile.getInputFile());
        this.context = context;
		this.file = xmlFile;
	}

	public XmlBw5Source(InputFile file) {
		super(file);
	}

	/**
	 *  
	 * This needs some description 
	 * 
	 * @param node TODO: Add description
	 * @return TODO: Add description
	 */
	public int getLineForNode(Node node) {
		return SaxParser.getLineNumber(node);
	}



	
	/**
	 * 
	 * This needs some description 
     *
	 * @param rule TODO: Add description
	 * @param node TODO: Add description
	 * @param message TODO: Add description
     *
	 */
	public void getViolationsHardCodedNode(RuleKey rule, Node node, String message){
		
		if(node.getTextContent() == null || node.getTextContent().isEmpty()){
			reportOnIssue(rule, getLineForNode(node), message + " (empty)");
		}else{
			if(!GvHelper.isGVReference(node.getTextContent())){
				reportOnIssue(rule, getLineForNode(node), message);
			}
		}		
	}
	
	/**
	 * 
	 * This needs some description 
	 *
	 * @param rule TODO: Add description
	 * @param node TODO: Add description
	 * @param elementDescription TODO: Add description
	 */
	public void findAndValidateHardCodedNode(RuleKey rule, Node node, String elementDescription){
		getViolationsHardCodedNode(rule, node, elementDescription);			
	}
	
	
	/**
	 * Return hard coded violations of first parent {@link Element}'s child {@link Node} based on input childName
	 *  
	 * @param rule			violated {@link Rule}
	 * @param parent		parent {@link Element} on which lookup will be done
	 * @param childName		childName {@link Node} name sought
	 * @param message		violation message
	 * 
	 */
	public void getViolationsHardCodedChild(RuleKey rule, Element parent, String childName, String message){
	
		try{
			Element elem = XmlHelper.firstChildElement(parent, childName);
			getViolationsHardCodedNode(rule, elem, message);
		}catch (Exception e) {
		
			                 reportOnIssue(rule, getLineForNode(parent), message + " (not found)");
		}
	
	}
	
	/**
	 * Add hard coded violations of first parent {@link Element}'s child {@link Node} based on input childName
	 * 
	 * @param rule			violated {@link Rule}
	 * @param parent		parent {@link Element} on which lookup will be done
	 * @param childName		childName {@link Node} name sought
	 * @param message		violation message
	 */
	public void findAndValidateHardCodedChild(RuleKey rule, Element parent, String childName, String message) {
		getViolationsHardCodedChild(rule, parent, childName, message);
	}
	
	/**
	 * Return hard coded violations of all elements related to the xPathQuery evaluated on the input context
	 * 
	 * @param rule			violated {@link Rule}
	 * @param context		input context {@link Node}
	 * @param xPathQuery 	XPath query {@link String}
	 * @param message		violations message {@link String}
	 * 
	 */
	public void getViolationsHardCodedXPath(RuleKey rule, Node context, String xPathQuery, String message){
		// Init violation 
		
		try{
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodesFound = (NodeList) xpath.evaluate(xPathQuery, context, XPathConstants.NODESET);
			int length = nodesFound.getLength();
			for (int i = 0; i < length; i++) {
				Node nodeFound = nodesFound.item(i);
                                getViolationsHardCodedNode(rule, nodeFound, message + " ("+i+"/"+length+")");
			}		
		}catch (Exception e) {
			
			reportOnIssue(rule, getLineForNode(context), message + " (not found)");
		}
		
	}


    private void reportOnIssue(RuleKey ruleKey, int line, String string) {
          NewIssue issue = context.newIssue();

        NewIssueLocation location = issue.newLocation()
                .on(file.getInputFile())
                .message(string);

        NewIssueLocation secondary = issue.newLocation()
                .on(file.getInputFile())
                .at(file.getInputFile().selectLine(line));
        issue.addLocation(secondary);

        issue
                .at(location)
                .forRule(ruleKey)
                .save();
    }

	public Document getDocument(boolean b) {
		if (!b) {
			return file.getNamespaceUnawareDocument();
		}else{
			return file.getNamespaceAwareDocument();
		}
	}
}