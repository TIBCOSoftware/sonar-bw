//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.01.07 at 05:01:20 PM CET 
//


package com.tibco.xmlns.archetype.generator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Goal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Goal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pluginGAV" type="{http://www.tibco.com/xmlns/archetype/generator}GAV" minOccurs="0"/>
 *         &lt;element name="goal" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Goal", propOrder = {
    "pluginGAV",
    "goal"
})
public class Goal {

    protected GAV pluginGAV;
    @XmlElement(required = true)
    protected String goal;

    /**
     * Gets the value of the pluginGAV property.
     * 
     * @return
     *     possible object is
     *     {@link GAV }
     *     
     */
    public GAV getPluginGAV() {
        return pluginGAV;
    }

    /**
     * Sets the value of the pluginGAV property.
     * 
     * @param value
     *     allowed object is
     *     {@link GAV }
     *     
     */
    public void setPluginGAV(GAV value) {
        this.pluginGAV = value;
    }

    /**
     * Gets the value of the goal property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoal() {
        return goal;
    }

    /**
     * Sets the value of the goal property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoal(String value) {
        this.goal = value;
    }

}
