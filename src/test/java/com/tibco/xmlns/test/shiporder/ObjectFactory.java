//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.13 at 02:30:12 PM CET 
//


package com.tibco.xmlns.test.shiporder;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tibco.xmlns.test.shiporder package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ShipOrder_QNAME = new QName("http://www.tibco.com/utils/wrapper/jaxb/tests", "shipOrder");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tibco.xmlns.test.shiporder
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link ShipTo }
     * 
     */
    public ShipTo createShipTo() {
        return new ShipTo();
    }

    /**
     * Create an instance of {@link ShipOrder }
     * 
     */
    public ShipOrder createShipOrder() {
        return new ShipOrder();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ShipOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tibco.com/utils/wrapper/jaxb/tests", name = "shipOrder")
    public JAXBElement<ShipOrder> createShipOrder(ShipOrder value) {
        return new JAXBElement<ShipOrder>(_ShipOrder_QNAME, ShipOrder.class, null, value);
    }

}
