//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.14 at 07:15:40 PM CET 
//


package com.tibco.xmlns.extarchetype.metadata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ExtArchetypeDescriptor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExtArchetypeDescriptor">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requiredProperties" type="{http://www.tibco.com/xmlns/extarchetype/metadata}ExtendedProperties"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtArchetypeDescriptor", namespace = "http://www.tibco.com/xmlns/extarchetype/metadata", propOrder = {
    "requiredProperties"
})
public class ExtArchetypeDescriptor {

    @XmlElement(required = true)
    protected ExtendedProperties requiredProperties;

    /**
     * Gets the value of the requiredProperties property.
     * 
     * @return
     *     possible object is
     *     {@link ExtendedProperties }
     *     
     */
    public ExtendedProperties getRequiredProperties() {
        return requiredProperties;
    }

    /**
     * Sets the value of the requiredProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtendedProperties }
     *     
     */
    public void setRequiredProperties(ExtendedProperties value) {
        this.requiredProperties = value;
    }

}
