/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.utils.common.helper;

import com.tibco.utils.common.logger.Logger;
import com.tibco.utils.common.logger.Loggers;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;


import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XmlHelper {

    private static final Logger LOG = Loggers.get(XmlHelper.class);

    public static String getAttributeValue(Element referenceServiceElement, String inline) {
        String value = null;
        if (referenceServiceElement != null && inline != null) {
            if (referenceServiceElement.getAttributes() != null) {
                Node nodeValue = referenceServiceElement.getAttributes().getNamedItem(inline);
                if (nodeValue != null) {
                    value = nodeValue.getNodeValue();
                }

            }
            LOG.debug("Attribute value for name [" + inline + "] in element [" + referenceServiceElement.getTagName() + "]: " + value);
        }

        return value;
    }

    public static boolean evaluateXPathAsBoolean(Element document, String expression) {
        Boolean out = null;
        if (document != null) {
            try {
                XPathExpression tmp = getXPathExpressionForDocument(expression);
                if (tmp != null) {
                    out = (Boolean) tmp.evaluate(document, XPathConstants.BOOLEAN);
                }
            } catch (XPathExpressionException ex) {
               LOG.warn("Error evaluating the XPath Expression: ",ex);
            }
        }
        return out;
    }



    private XmlHelper() {

    }

    final static String LINE_NUMBER_KEY_NAME = "lineNumber";
    final static String exceptionMsg = "Can't create SAX parser / DOM builder.";


    public static Document getDocument(File file) {
        try {
            final Document doc;

            SAXParser parser;
            try {
            	SAXParserFactory factory = null;
            	ClassLoader ocl = Thread.currentThread().getContextClassLoader();
        		try{
        			Thread.currentThread().setContextClassLoader(javax.xml.parsers.SAXParserFactory.class.getClassLoader());
        			factory  = SAXParserFactory.newDefaultInstance();
                                factory.setNamespaceAware(false);
	                parser = factory.newSAXParser();
	                final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newDefaultInstance();

	                final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	                doc = docBuilder.newDocument();
        		}finally{
        			Thread.currentThread().setContextClassLoader(ocl);
        		}
            } catch (final ParserConfigurationException e) {
                throw new RuntimeException(exceptionMsg, e);
            }

            final Stack<Element> elementStack = new Stack<Element>();
            final StringBuilder textBuffer = new StringBuilder();

            final DefaultHandler handler = new DefaultHandler() {
                private Locator locator;

                @Override
                public void setDocumentLocator(final Locator locator) {
                    this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.
                }

                @Override
                public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
                        throws SAXException {
                    addTextIfNeeded();
                    final Element el = doc.createElement(qName);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        el.setAttribute(attributes.getQName(i), attributes.getValue(i));
                    }
                    el.setAttribute(LINE_NUMBER_KEY_NAME, String.valueOf(this.locator.getLineNumber()));
                    elementStack.push(el);
                }

                @Override
                public void endElement(final String uri, final String localName, final String qName) {
                    addTextIfNeeded();
                    final Element closedEl = elementStack.pop();
                    if (elementStack.isEmpty()) { // Is this the root element?
                        doc.appendChild(closedEl);
                    } else {
                        final Element parentEl = elementStack.peek();
                        parentEl.appendChild(closedEl);
                    }
                }

                @Override
                public void characters(final char[] ch, final int start, final int length) throws SAXException {
                    textBuffer.append(ch, start, length);
                }

                // Outputs text accumulated under the current node
                private void addTextIfNeeded() {
                    if (textBuffer.length() > 0) {
                        final Element el = elementStack.peek();
                        final Node textNode = doc.createTextNode(textBuffer.toString());
                        el.appendChild(textNode);
                        textBuffer.delete(0, textBuffer.length());
                    }
                }
            };
            parser.parse(file, handler);
            return doc;
        } catch (SAXException ex) {
            //TODO LoggerFactory.getLogger(XmlHelper.class.getName()).error(null, ex);
        } catch (IOException ex) {
          //TODO  LoggerFactory.getLogger(XmlHelper.class.getName()).error(null, ex);
        }
        return null;
    }


    public static Document getDocument(InputStream file) {
        try {
            final Document doc;

            SAXParser parser;
            try {
            	SAXParserFactory factory = null;
            	ClassLoader ocl = Thread.currentThread().getContextClassLoader();
        		try{
        			Thread.currentThread().setContextClassLoader(javax.xml.parsers.SAXParserFactory.class.getClassLoader());
        			factory  = SAXParserFactory.newDefaultInstance();
                                factory.setNamespaceAware(false);
	                parser = factory.newSAXParser();
	                final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newDefaultInstance();


	                final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	                doc = docBuilder.newDocument();
        		}finally{
        			Thread.currentThread().setContextClassLoader(ocl);
        		}
            } catch (final ParserConfigurationException e) {
                throw new RuntimeException(exceptionMsg, e);
            }

            final Stack<Element> elementStack = new Stack<Element>();
            final StringBuilder textBuffer = new StringBuilder();

            final DefaultHandler handler = new DefaultHandler() {
                private Locator locator;

                @Override
                public void setDocumentLocator(final Locator locator) {
                    this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.
                }

                @Override
                public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
                        throws SAXException {
                    addTextIfNeeded();
                    final Element el = doc.createElement(qName);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        el.setAttribute(attributes.getQName(i), attributes.getValue(i));
                    }
                    el.setAttribute(LINE_NUMBER_KEY_NAME, String.valueOf(this.locator.getLineNumber()));
                    elementStack.push(el);
                }

                @Override
                public void endElement(final String uri, final String localName, final String qName) {
                    addTextIfNeeded();
                    final Element closedEl = elementStack.pop();
                    if (elementStack.isEmpty()) { // Is this the root element?
                        doc.appendChild(closedEl);
                    } else {
                        final Element parentEl = elementStack.peek();
                        parentEl.appendChild(closedEl);
                    }
                }

                @Override
                public void characters(final char[] ch, final int start, final int length) throws SAXException {
                    textBuffer.append(ch, start, length);
                }

                // Outputs text accumulated under the current node
                private void addTextIfNeeded() {
                    if (textBuffer.length() > 0) {
                        final Element el = elementStack.peek();
                        final Node textNode = doc.createTextNode(textBuffer.toString());
                        el.appendChild(textNode);
                        textBuffer.delete(0, textBuffer.length());
                    }
                }
            };
            parser.parse(file, handler);
            return doc;
        } catch (SAXException ex) {
            //TODO LoggerFactory.getLogger(XmlHelper.class.getName()).error(null, ex);
        } catch(IOException ex){
        }
        return null;
    }

    public static Document getDocument(String file) {
        try {
            final Document doc;

            SAXParser parser;
            try {
            	SAXParserFactory factory = null;
            	ClassLoader ocl = Thread.currentThread().getContextClassLoader();
        		try{
        			Thread.currentThread().setContextClassLoader(javax.xml.parsers.SAXParserFactory.class.getClassLoader());
        			factory  = SAXParserFactory.newDefaultInstance();
                                factory.setNamespaceAware(false);
	                parser = factory.newSAXParser();
	                final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newDefaultInstance();

	                final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	                doc = docBuilder.newDocument();
        		}finally{
        			Thread.currentThread().setContextClassLoader(ocl);
        		}
            } catch (final ParserConfigurationException e) {
                throw new RuntimeException(exceptionMsg, e);
            }

            final Stack<Element> elementStack = new Stack<Element>();
            final StringBuilder textBuffer = new StringBuilder();

            final DefaultHandler handler = new DefaultHandler() {
                private Locator locator;

                @Override
                public void setDocumentLocator(final Locator locator) {
                    this.locator = locator; // Save the locator, so that it can be used later for line tracking when traversing nodes.
                }

                @Override
                public void startElement(final String uri, final String localName, final String qName, final Attributes attributes)
                        throws SAXException {
                    addTextIfNeeded();
                    final Element el = doc.createElement(qName);
                    for (int i = 0; i < attributes.getLength(); i++) {
                        el.setAttribute(attributes.getQName(i), attributes.getValue(i));
                    }
                    el.setAttribute(LINE_NUMBER_KEY_NAME, String.valueOf(this.locator.getLineNumber()));
                    elementStack.push(el);
                }

                @Override
                public void endElement(final String uri, final String localName, final String qName) {
                    addTextIfNeeded();
                    final Element closedEl = elementStack.pop();
                    if (elementStack.isEmpty()) { // Is this the root element?
                        doc.appendChild(closedEl);
                    } else {
                        final Element parentEl = elementStack.peek();
                        parentEl.appendChild(closedEl);
                    }
                }

                @Override
                public void characters(final char[] ch, final int start, final int length) throws SAXException {
                    textBuffer.append(ch, start, length);
                }

                // Outputs text accumulated under the current node
                private void addTextIfNeeded() {
                    if (textBuffer.length() > 0) {
                        final Element el = elementStack.peek();
                        final Node textNode = doc.createTextNode(textBuffer.toString());
                        el.appendChild(textNode);
                        textBuffer.delete(0, textBuffer.length());
                    }
                }
            };
            parser.parse(new ByteArrayInputStream(file.getBytes(Charset.defaultCharset())), handler);
            return doc;
        } catch (SAXException ex) {
            LOG.warn("SAX Exception", ex);

        } catch (IOException ex) {
            LOG.warn("IO Exception", ex);
        }
        return null;
    }

    public static int getLineNumber(Node node) {
        if (node != null) {
            NamedNodeMap attributes = node.getAttributes();
            if(attributes != null){
                for (int i = 0; i < attributes.getLength(); i++) {
                    String attrName = attributes.item(i).getNodeName();
                    if (LINE_NUMBER_KEY_NAME.equals(attrName)) {
                        return Integer.parseInt(attributes.item(i).getTextContent());
                    }
                }
            }
        }
        return 1;
    }

      public static NodeList evaluateXPath(Element document, String expression) {
        NodeList out = null;
        if (document != null) {
            try {
                XPathExpression tmp = getXPathExpressionForDocument(expression);
                if (tmp != null) {
                    out = (NodeList) tmp.evaluate(document, XPathConstants.NODESET);
                }
            } catch (XPathExpressionException ex) {
               LOG.warn("Error evaluating the XPath Expression",ex);
            }
        }
        return out;
    }

    private static XPathExpression getXPathExpressionForDocument(String expression) {
        if (expression != null) {
            try {
            	ClassLoader ocl = Thread.currentThread().getContextClassLoader();
        		try{
        			Thread.currentThread().setContextClassLoader(javax.xml.xpath.XPathFactory.class.getClassLoader());
                XPath xpath = XPathFactory.newInstance().newXPath();
                if (xpath != null) {
                    return xpath.compile(expression);
                }
        		}finally{
        			Thread.currentThread().setContextClassLoader(ocl);
        		}
            } catch (XPathExpressionException ex) {

            }
        }
        return null;
    }

    public static Element evalueXPathSingleElement(Element document, String expression) {
        NodeList node = evaluateXPath(document, expression);
        if (node != null && node.getLength() > 0) {
            return (Element) node.item(0);
        }
        return null;
    }

    public static Element firstChildElement(Element element,
            String childNameSpace, String childName) {
        NodeList childCandidateList;
        if (childNameSpace == null || childNameSpace.isEmpty()) {
            childCandidateList = element.getElementsByTagName(childName);
        } else {
            childCandidateList = element.getElementsByTagNameNS(childNameSpace, childName);
        }
        if (childCandidateList.getLength() > 0) {
            return (Element) childCandidateList.item(0);
        } else {
            return null;
        }
    }

    public static Element firstChildElement(Element element, String childName) {
        return firstChildElement(element, null, childName);
    }

    public static Element firstChildElement(Node element, String childName) {
        return firstChildElement((Element) element, null, childName);
    }

    public static Node evaluateXpathNode(Node rootNode,
            String xPathQuery) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return (Node) xPath.compile(xPathQuery).evaluate(rootNode, XPathConstants.NODE);
        } catch (XPathExpressionException ex) {
            LOG.warn("Failed on executing XPath expression: " + xPathQuery, ex);
        }
        return null;
    }

    public static NodeList evaluateXpathNodeSet(Node rootNode, String xPathQuery) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return (NodeList) xPath.compile(xPathQuery).evaluate(rootNode, XPathConstants.NODESET);
        } catch (XPathExpressionException ex) {
            LOG.warn("Failed on executing XPath expression: " + xPathQuery, ex);
        }
        return null;
    }

}
