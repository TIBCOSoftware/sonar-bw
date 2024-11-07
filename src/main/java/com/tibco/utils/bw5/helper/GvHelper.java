/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.bw5.helper;

import com.tibco.utils.common.helper.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class GvHelper {

	public static final String SUBSTVAR_GV_LIST_NODE_NAME = "globalVariables";
	public static final String SUBSTVAR_GV_NODE_NAME = "globalVariable";

	private GvHelper(){

	}

	public static boolean isGVReference(String value) {
		return (value.startsWith("%%")) && (value.endsWith("%%"));
	}

	public static int countGV(Document substvarDoc) {
		int result = 0;
		Element globalVariables = XmlHelper.firstChildElement(substvarDoc.getDocumentElement(), SUBSTVAR_GV_LIST_NODE_NAME);
		if (globalVariables.hasChildNodes()) {
			for (int i = 0; i < globalVariables.getChildNodes().getLength(); i++) {
				Node node = globalVariables.getChildNodes().item(i);
				if (node.getLocalName().equals(SUBSTVAR_GV_NODE_NAME )) {
					result++;
				}
			}
		}
		return result;
	}















}
