//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.12.18 at 12:10:07 PM CET 
//


package com.tibco.xmlns.extarchetype.validation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Rules complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Rules">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="defaultRule" type="{http://www.tibco.com/xmlns/extarchetype/validation}DefaultRule"/>
 *         &lt;element name="warningRule" type="{http://www.tibco.com/xmlns/extarchetype/validation}WarningRule" minOccurs="0"/>
 *         &lt;element name="infoRule" type="{http://www.tibco.com/xmlns/extarchetype/validation}InfoRule" minOccurs="0"/>
 *         &lt;element name="tokenRule" type="{http://www.tibco.com/xmlns/extarchetype/validation}TokenRule" minOccurs="0"/>
 *         &lt;element name="customRule" type="{http://www.tibco.com/xmlns/extarchetype/validation}CustomRule" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Rules", propOrder = {
    "defaultRule",
    "warningRule",
    "infoRule",
    "tokenRule",
    "customRule"
})
public class Rules {

    @XmlElement(required = true)
    protected DefaultRule defaultRule;
    protected WarningRule warningRule;
    protected InfoRule infoRule;
    protected TokenRule tokenRule;
    protected List<CustomRule> customRule;

    /**
     * Gets the value of the defaultRule property.
     * 
     * @return
     *     possible object is
     *     {@link DefaultRule }
     *     
     */
    public DefaultRule getDefaultRule() {
        return defaultRule;
    }

    /**
     * Sets the value of the defaultRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link DefaultRule }
     *     
     */
    public void setDefaultRule(DefaultRule value) {
        this.defaultRule = value;
    }

    /**
     * Gets the value of the warningRule property.
     * 
     * @return
     *     possible object is
     *     {@link WarningRule }
     *     
     */
    public WarningRule getWarningRule() {
        return warningRule;
    }

    /**
     * Sets the value of the warningRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link WarningRule }
     *     
     */
    public void setWarningRule(WarningRule value) {
        this.warningRule = value;
    }

    /**
     * Gets the value of the infoRule property.
     * 
     * @return
     *     possible object is
     *     {@link InfoRule }
     *     
     */
    public InfoRule getInfoRule() {
        return infoRule;
    }

    /**
     * Sets the value of the infoRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoRule }
     *     
     */
    public void setInfoRule(InfoRule value) {
        this.infoRule = value;
    }

    /**
     * Gets the value of the tokenRule property.
     * 
     * @return
     *     possible object is
     *     {@link TokenRule }
     *     
     */
    public TokenRule getTokenRule() {
        return tokenRule;
    }

    /**
     * Sets the value of the tokenRule property.
     * 
     * @param value
     *     allowed object is
     *     {@link TokenRule }
     *     
     */
    public void setTokenRule(TokenRule value) {
        this.tokenRule = value;
    }

    /**
     * Gets the value of the customRule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the customRule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustomRule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustomRule }
     * 
     * 
     */
    public List<CustomRule> getCustomRule() {
        if (customRule == null) {
            customRule = new ArrayList<CustomRule>();
        }
        return this.customRule;
    }

}
