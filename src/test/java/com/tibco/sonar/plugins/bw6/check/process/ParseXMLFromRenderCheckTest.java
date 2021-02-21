/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import org.sonar.api.utils.log.Logger;

/**
 *
 * @author avazquez
 */
public class ParseXMLFromRenderCheckTest {
    
    public ParseXMLFromRenderCheckTest() {
    }
     private static ProcessSource source;
    

    
     
    @BeforeClass
    public static void setUpClass() {
       
        
                
                
        String processSource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\"\n" +
"    name=\"arch_preactions.ARCH_PreActions\" suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20171018191132\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://www.framework.com/ARCH_FunctionalId\"\n" +
"    xmlns:ns10=\"http://www.framework.com/schemas/ARCH_ESBFramework\"\n" +
"    xmlns:ns11=\"http://www.framework.com/schemas/ARCH_AD_SetArchHeader\"\n" +
"    xmlns:ns12=\"http://www.framework.com/schemas/ARCH_AuditData\"\n" +
"    xmlns:ns2=\"http://www.tibco.com/namespaces/tnt/plugins/parsexml\"\n" +
"    xmlns:sca=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"\n" +
"    xmlns:sca-bpel=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"\n" +
"    xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"\n" +
"    xmlns:tibprop=\"http://ns.tibco.com/bw/property\"\n" +
"    xmlns:tns=\"http://www.framework.com/schemas/ARCH_AE_INPUT\"\n" +
"    xmlns:tns0=\"http://www.framework.com/schemas/ARCH_Import_Pattern_Parameters\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
"    <tibex:Types>\n" +
"        <xs:schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"            xmlns:tns=\"http://www.tibco.com/pe/EngineTypes\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"ErrorReport\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"StackTrace\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Msg\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"FullClass\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Class\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ProcessStack\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"MsgCode\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"OptionalErrorReport\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"StackTrace\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Msg\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"FullClass\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Class\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"ProcessStack\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"MsgCode\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"FaultDetail\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ActivityName\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Msg\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"MsgCode\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ProcessStack\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"StackTrace\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"FullClass\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"Class\" type=\"xs:string\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"ProcessContext\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"JobId\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ApplicationName\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"EngineName\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" name=\"ProcessInstanceId\" type=\"xs:string\"/>\n" +
"                    <xs:element\n" +
"                        block=\"extension restriction substitution\"\n" +
"                        form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"CustomJobId\" type=\"xs:string\"/>\n" +
"                    <!--xs:element name=\"RestartedFromCheckpoint\" form=\"unqualified\" block=\"extension restriction substitution\" type=\"xs:boolean\"/-->\n" +
"                    <!--xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"TrackingInfo\" form=\"unqualified\" block=\"extension restriction substitution\" type=\"xs:string\"/-->\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:complexType block=\"extension restriction\"\n" +
"                final=\"extension restriction\" name=\"anydata\">\n" +
"                <xs:sequence>\n" +
"                    <xs:any namespace=\"##any\" processContents=\"lax\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"OptionalErrorReport\" type=\"tns:OptionalErrorReport\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"ErrorReport\" type=\"tns:ErrorReport\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"FaultDetail\" type=\"tns:FaultDetail\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"ProcessContext\" type=\"tns:ProcessContext\"/>\n" +
"            <xs:element block=\"extension restriction substitution\"\n" +
"                final=\"extension restriction\" name=\"CorrelationValue\" type=\"xs:string\"/>\n" +
"        </xs:schema>\n" +
"        <schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"\n" +
"            version=\"\" xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\">\n" +
"            <complexType name=\"ActivityExceptionType\">\n" +
"                <sequence>\n" +
"                    <element name=\"msg\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"msgCode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ActivityException\" type=\"tns:ActivityExceptionType\"/>\n" +
"            <complexType name=\"ActivityTimedOutExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:ActivityExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"ActivityTimedOutException\" type=\"tns:ActivityTimedOutExceptionType\"/>\n" +
"            <complexType name=\"DuplicateKeyExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:ActivityExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"duplicateKey\" type=\"string\"/>\n" +
"                            <element minOccurs=\"0\" name=\"previousJobID\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"DuplicateKeyException\" type=\"tns:DuplicateKeyExceptionType\"/>\n" +
"        </schema>\n" +
"        <xsd:schema\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/parsexml\"\n" +
"            xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/parsexml\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <xsd:complexType name=\"configType\">\n" +
"                <xsd:sequence>\n" +
"                    <xsd:element form=\"unqualified\" name=\"term\" type=\"xsd:anyType\"/>\n" +
"                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"inputStyle\" type=\"xsd:string\"/>\n" +
"                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"xsdVersion\" type=\"xsd:string\"/>\n" +
"                </xsd:sequence>\n" +
"            </xsd:complexType>\n" +
"            <xsd:complexType name=\"dynamicInputType\">\n" +
"                <xsd:choice>\n" +
"                    <xsd:element ref=\"tns:xmlBinary\"/>\n" +
"                    <xsd:element ref=\"tns:xmlString\"/>\n" +
"                </xsd:choice>\n" +
"            </xsd:complexType>\n" +
"            <xsd:element name=\"config\" type=\"tns:configType\"/>\n" +
"            <xsd:element name=\"element\" type=\"xsd:string\"/>\n" +
"            <xsd:element name=\"schemaRef\" type=\"xsd:string\"/>\n" +
"            <xsd:element name=\"textEncoding\" type=\"xsd:string\"/>\n" +
"            <xsd:element name=\"validateOutput\" type=\"xsd:boolean\"/>\n" +
"            <xsd:element name=\"writeXsiTypes\" type=\"xsd:boolean\"/>\n" +
"            <xsd:element name=\"xmlBinary\">\n" +
"                <xsd:complexType>\n" +
"                    <xsd:sequence>\n" +
"                        <xsd:element form=\"unqualified\" name=\"bytes\" type=\"xsd:base64Binary\"/>\n" +
"                        <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
"                            name=\"forceEncoding\" type=\"xsd:string\"/>\n" +
"                    </xsd:sequence>\n" +
"                </xsd:complexType>\n" +
"            </xsd:element>\n" +
"            <xsd:element name=\"xmlParserInput\" type=\"tns:dynamicInputType\"/>\n" +
"            <xsd:element name=\"xmlString\" type=\"xsd:string\"/>\n" +
"        </xsd:schema>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"true\" createdBy=\"juan.jose.martinez\"\n" +
"        createdOn=\"Mon Aug 07 19:09:50 CEST 2017\" description=\"\"\n" +
"        extraErrorVars=\"false\" modifiers=\"public\"\n" +
"        productVersion=\"6.4.0 V29 2017-05-09\" scalable=\"true\"\n" +
"        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n" +
"    <tibex:ProcessInterface context=\"\"\n" +
"        input=\"{http://www.framework.com/schemas/ARCH_AE_INPUT}Input\" output=\"{http://www.framework.com/schemas/ARCH_ESBFramework}ArchitectureHeader\"/>\n" +
"    <tibex:ProcessTemplateConfigurations/>\n" +
"    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n" +
"        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n" +
"        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"        <children type=\"2001\">\n" +
"            <children type=\"5001\">\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3002\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3003\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <children type=\"3004\">\n" +
"                <children type=\"4018\">\n" +
"                    <children type=\"3018\">\n" +
"                        <children type=\"4020\">\n" +
"                            <children type=\"3020\">\n" +
"                                <children type=\"4005\">\n" +
"                                    <children type=\"3007\">\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
"                                    <layoutConstraint x=\"-32\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint x=\"842\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.generalactivities.callprocess\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"400\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.generalactivities.callprocess\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"556\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.generalactivities.bwassign\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.4\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.4\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.4\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.4\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.4\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"263\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.xml.parsexml\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.5\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.5\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.5\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.5\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.5\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"332\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"177\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.6\"/>\n" +
"                                    <layoutConstraint x=\"703\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.7\"/>\n" +
"                                    <layoutConstraint x=\"117\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.8\"/>\n" +
"                                    <layoutConstraint x=\"556\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"225\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.9\"/>\n" +
"                                    <layoutConstraint x=\"117\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"177\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.10\"/>\n" +
"                                    <layoutConstraint x=\"471\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"137\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity\"/>\n" +
"                                    <layoutConstraint height=\"384\"\n" +
"                                    width=\"1032\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                <element href=\"//0/@process/@activity\"/>\n" +
"                            </children>\n" +
"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                            <element href=\"//0/@process/@activity\"/>\n" +
"                            <layoutConstraint height=\"384\" width=\"800\" xsi:type=\"notation:Bounds\"/>\n" +
"                        </children>\n" +
"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                        <element href=\"//0/@process/@activity\"/>\n" +
"                    </children>\n" +
"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                    <element href=\"//0/@process/@activity\"/>\n" +
"                    <layoutConstraint height=\"408\" width=\"800\" x=\"1\"\n" +
"                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n" +
"                </children>\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"            <element href=\"//0/@process\"/>\n" +
"            <layoutConstraint height=\"460\" width=\"1377\" xsi:type=\"notation:Bounds\"/>\n" +
"        </children>\n" +
"        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
"        <element href=\"//0\"/>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.10\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
"            <bendpoints\n" +
"                points=\"[25, 0, -46, -86]$[71, 0, 0, -86]$[71, 62, 0, -24]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint x=\"33\" xsi:type=\"notation:Location\" y=\"-53\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.2\"/>\n" +
"            <bendpoints\n" +
"                points=\"[25, 0, -37, 130]$[62, 0, 0, 130]$[62, -105, 0, 25]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.3\"/>\n" +
"            <bendpoints\n" +
"                points=\"[0, 25, -72, -105]$[0, 130, -72, 0]$[48, 130, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.7\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.4\"/>\n" +
"            <bendpoints points=\"[25, 0, -92, 0]$[93, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.7\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.5\"/>\n" +
"            <bendpoints points=\"[25, 0, -144, 0]$[145, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.6\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.6\"/>\n" +
"            <bendpoints points=\"[25, 0, -171, 0]$[172, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.6\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.7\"/>\n" +
"            <bendpoints points=\"[25, 0, -122, 0]$[123, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.8\"/>\n" +
"            <bendpoints\n" +
"                points=\"[0, -24, -295, -24]$[0, -45, -295, -45]$[295, -45, 0, -45]$[295, -24, 0, -24]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.9\"/>\n" +
"            <bendpoints\n" +
"                points=\"[-2, -24, -288, -24]$[-2, -51, -288, -51]$[288, -51, 2, -51]$[288, -24, 2, -24]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.8\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.10\"/>\n" +
"            <bendpoints points=\"[5, -24, -23, 103]$[23, -102, -5, 25]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.7\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.9\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"255\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontColor=\"255\" fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.11\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.9\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.12\"/>\n" +
"            <bendpoints\n" +
"                points=\"[25, 0, -121, 126]$[91, 0, -55, 126]$[91, -126, -55, 0]$[122, -126, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.10\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.8\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.13\"/>\n" +
"            <bendpoints\n" +
"                points=\"[0, 25, -85, -63]$[0, 88, -85, 0]$[61, 88, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <tibex:NamespaceRegistry enabled=\"true\">\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_AE_INPUT\" prefix=\"ns\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\" prefix=\"ns1\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_AuditData\" prefix=\"ns3\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/ARCH_FunctionalId\" prefix=\"tns\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_Import_Pattern_Parameters\" prefix=\"ns2\"/>\n" +
"    </tibex:NamespaceRegistry>\n" +
"    <bpws:documentation>It sends an PROCIN Audit Event.</bpws:documentation>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_AE_INPUT\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_AD_SetArchHeader\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_AuditData\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\"\n" +
"        location=\"../../Schemas/ARCH_FrameworkTypes.xsd\" namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\"\n" +
"        location=\"../../Schemas/ARCH_HeaderCheck.xsd\" namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/ARCH_FunctionalId\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_Import_Pattern_Parameters\"/>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns12:AuditData\"\n" +
"            name=\"ARCH_SendEvent-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns2:xmlString\"\n" +
"            name=\"RetrieveMessage-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns10:Message\" name=\"RetrieveMessage\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns11:ARCH_SetArchHeader\"\n" +
"            name=\"ARCH_SetArchHeader-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns10:ArchitectureHeader\"\n" +
"            name=\"ARCH_SetArchHeader\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns10:HeaderCheck\" name=\"ExistingHeader\"/>\n" +
"        <bpws:variable name=\"SimpleProcessPatternType\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/PatternTypes/simpleProcessPattern\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"GLB_Codigo\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CFG/Architecture/Log/ErrorCodes/OK\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"GLB_TipoAviso\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CFG/Architecture/Log/WarningTypes/Start\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"GLB_AuditEventType\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_AUDITTYPE_PROCIN\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns10:ArchitectureHeader\"\n" +
"            name=\"End-input\" sca-bpel:internal=\"true\" tibex:parameter=\"out\"/>\n" +
"        <bpws:variable element=\"tns:Input\" name=\"Start\"\n" +
"            sca-bpel:internal=\"true\" tibex:parameter=\"in\"/>\n" +
"        <bpws:variable element=\"tns:Input\"\n" +
"            name=\"ARCH_CheckErrorFolder-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"SetSharedVariable-input\"\n" +
"            sca-bpel:internal=\"true\" type=\"xsd:integer\"/>\n" +
"        <bpws:variable name=\"SetSharedVariable\" sca-bpel:internal=\"true\" type=\"xsd:integer\"/>\n" +
"        <bpws:variable name=\"FaultName\" sca-bpel:internal=\"true\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns:ErrorReport\" name=\"_error\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns:OptionalErrorReport\"\n" +
"            name=\"_errorOptional\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns0:Element\"\n" +
"            name=\"SetFunctionalID-input\" sca-bpel:internal=\"true\"/>\n" +
"    </bpws:variables>\n" +
"    <bpws:extensions>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.tibco.com/bw/process/info\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://ns.tibco.com/bw/property\"/>\n" +
"        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"    </bpws:extensions>\n" +
"    <bpws:scope name=\"scope\">\n" +
"        <bpws:flow name=\"flow\">\n" +
"            <bpws:links>\n" +
"                <bpws:link name=\"ARCH_SetArchHeaderToSetFunctionalID\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"AssignToARCH_Headerdoesnotexist\"\n" +
"                    tibex:label=\"Header does not exist\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"                <bpws:link name=\"ParseXmlToARCH_SetArchHeader\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"CheckHeaderToRetrieveMessage\" tibex:linkType=\"SUCCESSWITHNOCONDITION\"/>\n" +
"                <bpws:link name=\"StartToARCH_DeleteBlockedFiles\" tibex:linkType=\"SUCCESSWITHNOCONDITION\"/>\n" +
"                <bpws:link name=\"ARCH_DeleteBlockedFilesToCheckHeader\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"ARCH_CheckErrorFolderToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"ARCH_SendEventToARCH_CheckErrorFolder\" tibex:linkType=\"SUCCESSWITHNOCONDITION\"/>\n" +
"                <bpws:link name=\"StartToCheckHeader\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"                <bpws:link name=\"ARCH_SendEventToEnd\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"                <bpws:link name=\"SetSharedVariableToARCH_SendEvent\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"ARCH_DeleteBlockedFilesToEmpty\" tibex:linkType=\"ERROR\"/>\n" +
"                <bpws:link name=\"EmptyToCheckHeader\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"SetFunctionalIDToSetSharedVariable\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"0\" name=\"Start\"\n" +
"                    tibex:xpdlId=\"935d5a4c-739e-4d60-9be1-593bebd447cc\"\n" +
"                    variable=\"Start\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"StartToARCH_DeleteBlockedFiles\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"##otherwise##\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[##otherwise##]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                        <bpws:source linkName=\"StartToCheckHeader\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"$Start/tns:PatternType eq $SimpleProcessPatternType\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[$Start/tns:PatternType eq $SimpleProcessPatternType]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:eventSource>\n" +
"                        <tibex:StartEvent xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension inputVariable=\"End-input\"\n" +
"                    name=\"End\"\n" +
"                    tibex:xpdlId=\"925a27e0-cfcd-4fc9-8e20-069876ce5d27\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"ARCH_CheckErrorFolderToEnd\"/>\n" +
"                        <bpws:target linkName=\"ARCH_SendEventToEnd\"/>\n" +
"                    </bpws:targets>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n";
        processSource +="                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;ARCH_SetArchHeader&quot;/>&lt;xsl:template name=&quot;End-input&quot; match=&quot;/&quot;>&lt;xsl:copy-of select=&quot;$ARCH_SetArchHeader&quot;/>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:config>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.internal.end\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:internalactivities=\"http://ns.tibco.com/bw/core/internalactivity\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/core/internalactivity#//End\"/>\n" +
"                                    <value xsi:type=\"internalactivities:End\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity\n" +
"                    inputVariable=\"ARCH_SetArchHeader-input\"\n" +
"                    name=\"ARCH_SetArchHeader\"\n" +
"                    outputVariable=\"ARCH_SetArchHeader\"\n" +
"                    tibex:xpdlId=\"f2005eb3-8ffb-4add-b294-6be0b25620ed\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"AssignToARCH_Headerdoesnotexist\"/>\n" +
"                        <bpws:target linkName=\"ParseXmlToARCH_SetArchHeader\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"ARCH_SetArchHeaderToSetFunctionalID\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:ns=&quot;http://www.framework.com/schemas/ARCH_AE_INPUT&quot; xmlns:ns4=&quot;http://www.framework.com/schemas/ARCH_AD_SetArchHeader&quot; xmlns:ns1=&quot;http://www.framework.com/schemas/ARCH_ESBFramework&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;Start&quot;/>&#xa;    &lt;xsl:param name=&quot;RetrieveMessage&quot;/>&#xa;    &lt;xsl:template name=&quot;ARCH_SetArchHeader-input&quot; match=&quot;/&quot;>&#xa;        &lt;ns4:ARCH_SetArchHeader>&#xa;            &lt;xsl:if test=&quot;count($RetrieveMessage)=1&quot;>&#xa;                &lt;xsl:copy-of select=&quot;$RetrieveMessage/ns1:ArchitectureHeader&quot;/>&#xa;            &lt;/xsl:if>&#xa;            &lt;ns4:modulename>&#xa;                &lt;xsl:value-of select=&quot;$Start/ns:moduleName&quot;/>&#xa;            &lt;/ns4:modulename>&#xa;        &lt;/ns4:ARCH_SetArchHeader>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_common.ARCH_SetArchHeader\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity inputVariable=\"ARCH_SendEvent-input\"\n" +
"                    name=\"ARCH_SendEvent\"\n" +
"                    tibex:xpdlId=\"be03e79e-64da-471f-a735-fc462e414a0a\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"SetSharedVariableToARCH_SendEvent\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"ARCH_SendEventToARCH_CheckErrorFolder\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"##otherwise##\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[##otherwise##]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                        <bpws:source linkName=\"ARCH_SendEventToEnd\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"$Start/tns:PatternType eq $SimpleProcessPatternType\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[$Start/tns:PatternType eq $SimpleProcessPatternType]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:ns3=&quot;http://www.framework.com/schemas/ARCH_AuditData&quot; xmlns:ns1=&quot;http://www.framework.com/schemas/ARCH_ESBFramework&quot; xmlns:ns=&quot;http://www.framework.com/schemas/ARCH_AE_INPUT&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;GLB_Codigo&quot;/>&lt;xsl:param name=&quot;GLB_TipoAviso&quot;/>&lt;xsl:param name=&quot;GLB_AuditEventType&quot;/>&lt;xsl:param name=&quot;RetrieveMessage&quot;/>&lt;xsl:param name=&quot;ARCH_SetArchHeader&quot;/>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;ARCH_SendEvent-input&quot; match=&quot;/&quot;>&lt;ns3:AuditData>&lt;ns3:Pattern_Type>&lt;xsl:value-of select=&quot;$Start/ns:PatternType&quot;/>&lt;/ns3:Pattern_Type>&lt;ns3:StartEvent>&lt;ns1:Message>&lt;xsl:copy-of select=&quot;$ARCH_SetArchHeader&quot;/>&lt;xsl:choose>&lt;xsl:when test=&quot;exists($RetrieveMessage)&quot;>&lt;xsl:copy-of select=&quot;$RetrieveMessage/ns1:Body&quot;/>&lt;/xsl:when>&lt;xsl:otherwise>&lt;ns1:Body>&lt;xsl:copy-of select=&quot;$Start/ns:Message/ancestor-or-self::*/namespace::node()&quot;/>&lt;xsl:copy-of select=&quot;$Start/ns:Message/@*&quot;/>&lt;xsl:copy-of select=&quot;$Start/ns:Message/node()&quot;/>&lt;/ns1:Body>&lt;/xsl:otherwise>&lt;/xsl:choose>&lt;/ns1:Message>&lt;/ns3:StartEvent>&lt;ns3:Codigo>&lt;xsl:value-of select=&quot;$GLB_Codigo&quot;/>&lt;/ns3:Codigo>&lt;ns3:TipoAviso>&lt;xsl:value-of select=&quot;$GLB_TipoAviso&quot;/>&lt;/ns3:TipoAviso>&lt;ns3:auditEventType>&lt;xsl:value-of select=&quot;$GLB_AuditEventType&quot;/>&lt;/ns3:auditEventType>&lt;/ns3:AuditData>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_common.ARCH_SendEvent\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension inputVariable=\"ExistingHeader\"\n" +
"                    name=\"CheckHeader\" outputVariable=\"ExistingHeader\"\n" +
"                    tibex:xpdlId=\"5751b14d-35b8-4aa1-b4a7-4a106065beb1\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"ARCH_DeleteBlockedFilesToCheckHeader\"/>\n" +
"                        <bpws:target linkName=\"StartToCheckHeader\"/>\n" +
"                        <bpws:target linkName=\"EmptyToCheckHeader\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"AssignToARCH_Headerdoesnotexist\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"$ExistingHeader/ns10:headerExists='false'\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[$ExistingHeader/ns10:headerExists='false']]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                        <bpws:source linkName=\"CheckHeaderToRetrieveMessage\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"##otherwise##\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition><![CDATA[##otherwise##]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:ns=&quot;http://www.framework.com/schemas/ARCH_AE_INPUT&quot; xmlns:ns1=&quot;http://www.framework.com/schemas/ARCH_ESBFramework&quot; xmlns:tib=&quot;http://www.tibco.com/bw/xslt/custom-functions&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;ExistingHeader&quot; match=&quot;/&quot;>&lt;ns1:HeaderCheck>&lt;ns1:param>&lt;xsl:value-of select=&quot;concat(tib:render-xml($Start/ns:Message/*), &amp;quot;ArchitectureHeader&amp;quot;)&quot;/>&lt;/ns1:param>&lt;ns1:headerExists>&lt;xsl:value-of select=&quot;contains(tib:render-xml($Start/ns:Message/*), &amp;quot;ArchitectureHeader&amp;quot;)&quot;/>&lt;/ns1:headerExists>&lt;/ns1:HeaderCheck>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:config>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.generalactivities.bwassign\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//BWAssign\"/>\n" +
"                                    <value\n" +
"                                    processVariable=\"ExistingHeader\" xsi:type=\"generalactivities:BWAssign\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension\n" +
"                    inputVariable=\"RetrieveMessage-input\"\n" +
"                    name=\"RetrieveMessage\"\n" +
"                    outputVariable=\"RetrieveMessage\"\n" +
"                    tibex:xpdlId=\"86f61fa1-f29d-454f-9ed0-16121bf72318\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"CheckHeaderToRetrieveMessage\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"ParseXmlToARCH_SetArchHeader\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns1=&quot;http://www.tibco.com/namespaces/tnt/plugins/parsexml&quot; xmlns:ns=&quot;http://www.framework.com/schemas/ARCH_AE_INPUT&quot; xmlns:tib=&quot;http://www.tibco.com/bw/xslt/custom-functions&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;Start&quot;/>&#xa;    &lt;xsl:template name=&quot;RetrieveMessage-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns1:xmlString>&#xa;            &lt;xsl:value-of select=&quot;tib:render-xml($Start/ns:Message/*)&quot;/>&#xa;        &lt;/tns1:xmlString>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:config>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.xml.parsexml\"\n" +
"                            version=\"6.0.0.20121128\"\n" +
"                            xmlns:ARCH_ESBFramework=\"http://www.framework.com/schemas/ARCH_ESBFramework\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:palettexml=\"http://ns.tibco.com/bw/palette/xmlpalette\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/xmlpalette#//ParseXml\"/>\n" +
"                                    <value inputStyle=\"text\"\n" +
"                                    outputElementQName=\"ARCH_ESBFramework:Message\"\n" +
"                                    validateOutput=\"true\" xsi:type=\"palettexml:ParseXml\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity\n" +
"                    inputVariable=\"ARCH_CheckErrorFolder-input\"\n" +
"                    name=\"ARCH_CheckErrorFolder\"\n" +
"                    tibex:xpdlId=\"3ca02e8b-fbee-4c6c-b0e7-99d2d9d843bb\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"ARCH_SendEventToARCH_CheckErrorFolder\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"ARCH_CheckErrorFolderToEnd\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:ns=&quot;http://www.framework.com/schemas/ARCH_AE_INPUT&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;ARCH_CheckErrorFolder-input&quot; match=&quot;/&quot;>&lt;ns:Input>&lt;xsl:copy-of select=&quot;$Start/ns:Message&quot;/>&lt;xsl:if test=&quot;$Start/ns:moduleName&quot;>&lt;ns:moduleName>&lt;xsl:value-of select=&quot;$Start/ns:moduleName&quot;/>&lt;/ns:moduleName>&lt;/xsl:if>&lt;xsl:choose>&lt;xsl:when test=&quot;$Start/ns:Delta&quot;>&lt;ns:Delta/>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns:DeltaKey&quot;>&lt;ns:DeltaKey/>&lt;/xsl:when>&lt;xsl:otherwise>&lt;ns:Full/>&lt;/xsl:otherwise>&lt;/xsl:choose>&lt;/ns:Input>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_preactions.ARCH_CheckErrorFolder\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity name=\"ARCH_DeleteBlockedFiles\"\n" +
"                    tibex:xpdlId=\"8445bdc3-fa54-4a24-8001-68d51a084c41\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"StartToARCH_DeleteBlockedFiles\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"ARCH_DeleteBlockedFilesToCheckHeader\"/>\n" +
"                        <bpws:source\n" +
"                            linkName=\"ARCH_DeleteBlockedFilesToEmpty\"\n" +
"                            tibex:faultDetailsVar=\"ARCH_DeleteBlockedFiles\" tibex:faultNameVar=\"FaultName\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression expression=\"##error##\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[##error##]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_preactions.ARCH_DeleteBlockedFiles\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity\n" +
"                    inputVariable=\"SetSharedVariable-input\"\n" +
"                    name=\"SetSharedVariable\"\n" +
"                    outputVariable=\"SetSharedVariable\"\n" +
"                    tibex:xpdlId=\"1552a854-a995-46b9-85ef-1202a6a10e3d\"\n" +
"                    type=\"bw.internal.setSharedVariable\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"SetFunctionalIDToSetSharedVariable\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"SetSharedVariableToARCH_SendEvent\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding expression=\"0\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:SetSharedVariable inline=\"true\"\n" +
"                        variableName=\"NumOfRetry\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:empty name=\"Empty\" tibex:xpdlId=\"dbf96aaa-c4b3-4c52-bf67-c72ca98e4282\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"ARCH_DeleteBlockedFilesToEmpty\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"EmptyToCheckHeader\"/>\n" +
"                </bpws:sources>\n" +
"            </bpws:empty>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity inputVariable=\"SetFunctionalID-input\"\n" +
"                    name=\"SetFunctionalID\"\n" +
"                    tibex:xpdlId=\"327548e8-81d3-440c-8dfe-7081967e3894\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"ARCH_SetArchHeaderToSetFunctionalID\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"SetFunctionalIDToSetSharedVariable\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;!--START COERCIONS-->&#xa;&lt;!-- $Start/ns:Message/*, element, ns2:Trigger -->&#xa;&lt;!--END COERCIONS-->&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.framework.com/ARCH_FunctionalId&quot; xmlns:ns=&quot;http://www.framework.com/schemas/ARCH_AE_INPUT&quot; xmlns:ns2=&quot;http://www.framework.com/schemas/ARCH_Import_Pattern_Parameters&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;SetFunctionalID-input&quot; match=&quot;/&quot;>&lt;tns:Element>&lt;xsl:choose>&lt;xsl:when test=&quot;count($Start/ns:Message/ns2:Trigger) > 0&quot;>&lt;tns:functionalID>&lt;xsl:value-of select=&quot;'Trigger'&quot;/>&lt;/tns:functionalID>&lt;/xsl:when>&lt;xsl:otherwise>&lt;tns:functionalID>&lt;xsl:value-of select=&quot;''&quot;/>&lt;/tns:functionalID>&lt;/xsl:otherwise>&lt;/xsl:choose>&lt;/tns:Element>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_publicLibraries.SetFunctionalID\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"        </bpws:flow>\n" +
"    </bpws:scope>\n" +
"</bpws:process>";

source = new ProcessSource(processSource);

        
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }



    @Test
    public void testValidate() {
        System.out.println("testValidate");
        ParseXMLFromRenderCheck instance = new ParseXMLFromRenderCheck();
        ParseXMLFromRenderCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,atLeastOnce()).reportIssueOnFile(anyString(),anyInt());
        
    }
}
