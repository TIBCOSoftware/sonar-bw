package com.tibco.utils;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class TestUtils {


    public static Document generateDocumentFromXML(String xmlContent)  {
        Document doc = null;
        try {
            // Create a Document from the XML string
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new ByteArrayInputStream(xmlContent.getBytes()));
        }catch(ParserConfigurationException | IOException | SAXException ex){
            doc = null;
        }
        return doc;
    }
}
