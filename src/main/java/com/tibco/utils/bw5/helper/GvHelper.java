/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.bw5.helper;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;

import com.tibco.utils.common.helper.XmlHelper;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.tibco.utils.wrapper.jaxb.JaxbHelper;
import com.tibco.xmlns.repo.types._2002.GlobalVariableType;
import com.tibco.xmlns.repo.types._2002.RepositoryType;

public class GvHelper {
	public static final String SUBSTVAR_ROOT_NODE_NAME = "repository";
	public static final String SUBSTVAR_GV_LIST_NODE_NAME = "globalVariables";
	public static final String SUBSTVAR_GV_NODE_NAME = "globalVariable";
	private static final String GV_BASE_FOLDER_NAME = "defaultVars";
	private static final String GV_FILE_NAME = "defaultVars.substvar";
	private static final QName GV_FILE_QNAME = new QName("http://www.tibco.com/xmlns/repo/types/2002", "repository");

	public static boolean isGVReference(String value) {
		return (value.startsWith("%%")) && (value.endsWith("%%"));
	}

	public static int countGV(Document substvarDoc) {
		int result = 0;
		Element globalVariables = XmlHelper.firstChildElement(substvarDoc.getDocumentElement(), "globalVariables");
		if (globalVariables.hasChildNodes()) {
			for (int i = 0; i < globalVariables.getChildNodes().getLength(); i++) {
				Node node = globalVariables.getChildNodes().item(i);
				if (node.getNodeName().equals("globalVariable")) {
					result++;
				}
			}
		}
		return result;
	}

	public static RepositoryType loadGVFile(File file)
			throws JAXBException, ParserConfigurationException, SAXException, IOException {
		if (!file.exists())
			throw new IOException("Unable to find file " + file.getAbsolutePath());
		if (!file.getName().equals("defaultVars.substvar")) {
			throw new IOException("File " + file.getAbsolutePath() + " is not a TIBCO Designer global variable file");
		}
		return JaxbHelper.xmlFileToObject(RepositoryType.class, file, false);
	}

	public static RepositoryType loadGVFile(String file)
			throws JAXBException, ParserConfigurationException, SAXException, IOException {
		return loadGVFile(new File(file));
	}

	public static RepositoryType renameGV(RepositoryType inputStructure, String oldGVName, String newGVName)
			throws Exception {
		if (inputStructure == null) {
			throw new Exception("The input global variable structure is null");
		}
		if ((inputStructure.getGlobalVariables() == null)
				|| (inputStructure.getGlobalVariables().getGlobalVariable().size() < 1)) {
			throw new Exception("The input global variable structure does not contain any GV");
		}
		if ((oldGVName == null) || (oldGVName.isEmpty())) {
			throw new Exception("The old GV name is incorrect: " + oldGVName);
		}
		if ((newGVName == null) || (newGVName.isEmpty())) {
			throw new Exception("The new GV name is incorrect: " + newGVName);
		}
		for (GlobalVariableType gv : inputStructure.getGlobalVariables().getGlobalVariable()) {
			if (gv.getName().equals(oldGVName)) {
				gv.setName(newGVName);
				break;
			}
		}

		return inputStructure;
	}

	public static void overwriteGVFile(RepositoryType inputStructure, File GVFile) throws Exception {
		if (inputStructure == null) {
			throw new Exception("The input global variable structure is null");
		}
		if ((GVFile.exists()) && (!GVFile.getName().equals("defaultVars.substvar"))) {
			throw new IOException("File " + GVFile.getAbsolutePath() + " is not a TIBCO Designer global variable file");
		}

		FileUtils.write(GVFile, JaxbHelper.objectToXmlString(inputStructure, GV_FILE_QNAME));
	}

	public static void renameGVInFile(File GVFile, String oldGVName, String newGVName) throws Exception {
		RepositoryType struct = loadGVFile(GVFile);
		struct = renameGV(struct, oldGVName, newGVName);
		overwriteGVFile(struct, GVFile);
	}

	public static RepositoryType updateGV(RepositoryType inputStructure, String GVName, String newGVValue)
			throws Exception {
		if (inputStructure == null) {
			throw new Exception("The input global variable structure is null");
		}
		if ((inputStructure.getGlobalVariables() == null)
				|| (inputStructure.getGlobalVariables().getGlobalVariable().size() < 1)) {
			throw new Exception("The input global variable structure does not contain any GV");
		}
		if ((GVName == null) || (GVName.isEmpty())) {
			throw new Exception("The GV name is incorrect: " + GVName);
		}
		for (GlobalVariableType gv : inputStructure.getGlobalVariables().getGlobalVariable()) {
			if (gv.getName().equals(GVName)) {
				gv.setValue(newGVValue);
				break;
			}
		}

		return inputStructure;
	}

	public static void updateGVInFile(File GVFile, String GVName, String newGVValue) throws Exception {
		RepositoryType struct = loadGVFile(GVFile);
		struct = updateGV(struct, GVName, newGVValue);
		overwriteGVFile(struct, GVFile);
	}

	public static void updateGVInProject(String bwProjectBaseDir, String gvFullName, String newGvValue)
			throws Exception {
		int lastIndexSeparator = gvFullName.lastIndexOf("/");
		String gvRelativePath = "";
		String gvName = gvFullName;
		if (lastIndexSeparator != -1) {
			gvRelativePath = gvFullName.substring(0, lastIndexSeparator);
			gvName = gvFullName.substring(lastIndexSeparator + 1);
		}
		File gvFile = new File(bwProjectBaseDir + File.separator + "defaultVars" + File.separator + gvRelativePath
				+ File.separator + "defaultVars.substvar");
		updateGVInFile(gvFile, gvName, newGvValue);
	}

	public static RepositoryType addGVList(RepositoryType inputStructure, List<GlobalVariableType> GVList)
			throws Exception {
		if ((inputStructure == null) || (inputStructure.getGlobalVariables() == null)) {
			throw new Exception("The input global variable structure is null");
		}

		for (GlobalVariableType newGV : GVList) {
			for (GlobalVariableType existingGV : inputStructure.getGlobalVariables().getGlobalVariable()) {
				if (existingGV.getName().equals(newGV.getName())) {
					throw new Exception("GV " + newGV.getName() + " already exists in the GV file");
				}
			}
			inputStructure.getGlobalVariables().getGlobalVariable().add(newGV);
		}

		return inputStructure;
	}

	public static void addGVInFile(File GVFile, List<GlobalVariableType> GVList) throws Exception {
		RepositoryType struct = loadGVFile(GVFile);
		struct = addGVList(struct, GVList);
		overwriteGVFile(struct, GVFile);
	}
}
