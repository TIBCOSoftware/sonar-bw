//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.07 at 11:02:14 AM CET 
//


package com.tibco.xmlns.extarchetype.patterns;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PatternComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatternComponent">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tibco.com/xmlns/extarchetype/patterns}PatternArchetype">
 *       &lt;sequence>
 *         &lt;element name="linkedArchetypes" type="{http://www.tibco.com/xmlns/extarchetype/patterns}PatternArchetypes" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatternComponent", namespace = "http://www.tibco.com/xmlns/extarchetype/patterns", propOrder = {
    "linkedArchetypes"
})
public class PatternComponent
    extends PatternArchetype
{

    protected PatternArchetypes linkedArchetypes;

    /**
     * Gets the value of the linkedArchetypes property.
     * 
     * @return
     *     possible object is
     *     {@link PatternArchetypes }
     *     
     */
    public PatternArchetypes getLinkedArchetypes() {
        return linkedArchetypes;
    }

    /**
     * Sets the value of the linkedArchetypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatternArchetypes }
     *     
     */
    public void setLinkedArchetypes(PatternArchetypes value) {
        this.linkedArchetypes = value;
    }

}
