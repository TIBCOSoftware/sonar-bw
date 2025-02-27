/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

package com.tibco.utils.common;

import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.sonar.api.utils.MessageException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Provides reusable code for Xml parsers.
 *
 * @author TIBCODX Toolkit
 */
public abstract class AbstractParser {

  private static final SAXParserFactory SAX_FACTORY_NAMESPCE_AWARE;
  private static final SAXParserFactory SAX_FACTORY_NAMESPCE_UNAWARE;

  /**
   * Build the SAXParserFactory.
   */
  static {

    SAX_FACTORY_NAMESPCE_AWARE = new SAXParserFactoryImpl();
    SAX_FACTORY_NAMESPCE_UNAWARE = new SAXParserFactoryImpl();

    setCommonConf(SAX_FACTORY_NAMESPCE_AWARE);
    SAX_FACTORY_NAMESPCE_AWARE.setNamespaceAware(true);

    setCommonConf(SAX_FACTORY_NAMESPCE_UNAWARE);
    SAX_FACTORY_NAMESPCE_UNAWARE.setNamespaceAware(false);
  }

  protected SAXParser newSaxParser(boolean namespaceAware) {
    try {
      return namespaceAware ? SAX_FACTORY_NAMESPCE_AWARE.newSAXParser() : SAX_FACTORY_NAMESPCE_UNAWARE.newSAXParser();
    } catch (SAXException | ParserConfigurationException e) {
      throw MessageException.of("Error creating SAX Parser", e);
    }
  }

  private static void setCommonConf(SAXParserFactory factory) {
    try {
      factory.setValidating(false);
      factory.setFeature("http://xml.org/sax/features/validation", false);
      factory.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
      factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
      factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
    } catch (SAXException |ParserConfigurationException e) {
      throw MessageException.of("Error setting Common Conf to SAX Parser", e);
    }
  }

}
