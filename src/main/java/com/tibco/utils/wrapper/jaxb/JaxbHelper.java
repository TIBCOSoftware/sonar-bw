/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.wrapper.jaxb;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.bind.*;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * @author Gilles Seghaier
 *
 */
public class JaxbHelper {

	/**
	 * 
	 */
	public static final String UTF8_ENCODING = "UTF-8";

	/**
	 * @param obj
	 * @param qName
	 * @return
	 * @throws JAXBException
	 */
	public static <T> String objectToXmlString(T obj, QName qName)
			throws JAXBException {
		JAXBContext pContext;
		java.io.StringWriter sw = new StringWriter();
		pContext = JAXBContext.newInstance(obj.getClass());
		Marshaller marshaller = pContext.createMarshaller();

		@SuppressWarnings("unchecked")
		JAXBElement<T> je = new JAXBElement<T>(qName,
				(Class<T>) obj.getClass(), obj);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF8_ENCODING);
		marshaller.marshal(je, sw);
		return sw.toString();
	}

	/**
	 * @param obj
	 * @param qName
	 * @param filePath
	 * @param overwrite
	 * @return
	 * @throws IOException
	 * @throws JAXBException
	 */
	public static <T> File objectToXmlFile(T obj, QName qName, String filePath,
			boolean overwrite) throws IOException, JAXBException {
		File xmlFile = new File(filePath);
		if (!xmlFile.createNewFile()) {
			if (!overwrite) {
				throw new IOException("File " + filePath
						+ " already exists. It cannot be overwritten.");
			}
		}
		FileUtils.writeStringToFile(xmlFile, objectToXmlString(obj, qName));
		return xmlFile;
	}
	
	/**
	 * @param clazz
	 * @param is
	 * @return
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static <T> T xmlInputStreamToObject(Class<T> clazz, InputStream is)
			throws JAXBException, ParserConfigurationException, SAXException,
			IOException {
		return xmlInputStreamToObject(clazz, is, true);
	}
	
	/**
	 * @param clazz
	 * @param is
	 * @return
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static <T> T xmlInputStreamToObject(Class<T> clazz, InputStream is, boolean nsAware)
			throws JAXBException, ParserConfigurationException, SAXException,
			IOException {
		JAXBContext pContext = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = pContext.createUnmarshaller();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(nsAware);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		Node root = doc.getFirstChild();
		JAXBElement<T> element = unmarshaller.unmarshal(root,
				clazz);
		return element.getValue();
	}
	
	
	/**
	 * @param clazz
	 * @param xmlString
	 * @return
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static <T> T xmlStringToObject(Class<T> clazz, String xmlString)
			throws JAXBException, ParserConfigurationException, SAXException,
			IOException {
		InputStream is = new ByteArrayInputStream(xmlString.getBytes());
		return xmlInputStreamToObject(clazz, is);		
	}

	/**
	 * @param clazz
	 * @param file
	 * @return
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public static <T> T xmlFileToObject(Class<T> clazz, File file)
			throws JAXBException, ParserConfigurationException, SAXException,
			IOException {
		InputStream is = new FileInputStream(file);
		return xmlInputStreamToObject(clazz, is);
	}
	
	public static <T> T xmlFileToObject(Class<T> clazz, File file, boolean nsAware)
			throws JAXBException, ParserConfigurationException, SAXException,
			IOException {
		InputStream is = new FileInputStream(file);
		return xmlInputStreamToObject(clazz, is, nsAware);
	}

}
