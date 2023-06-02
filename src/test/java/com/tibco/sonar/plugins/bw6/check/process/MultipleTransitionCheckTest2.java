/*
 * Copyright (C) 2016-2023. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
 */
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

/**
 *
 * @author avazquez
 */
public class MultipleTransitionCheckTest2 {
    
    private static ProcessSource source;
    
    public MultipleTransitionCheckTest2() {
    }
    
      
    @BeforeClass
    public static void setUpClass() {
    	final String part1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        		"<bpws:process exitOnStandardFault=\"no\"\n" +
        		"    name=\"foreachtransitiontest.module.SubProcess\"\n" +
        		"    suppressJoinFailure=\"yes\"\n" +
        		"    targetNamespace=\"http://xmlns.example.com/20220201120439\"\n" +
        		"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
        		"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
        		"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
        		"    xmlns:ns0=\"http://www.tibco.com/pe/WriteToLogActivitySchema\"\n" +
        		"    xmlns:ns1=\"http://www.example.com/namespaces/tns/1643717165856\"\n" +
        		"    xmlns:sca=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"\n" +
        		"    xmlns:sca-bpel=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"\n" +
        		"    xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"\n" +
        		"    xmlns:tibprop=\"http://ns.tibco.com/bw/property\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
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
        		"                    <xs:element\n" +
        		"                        block=\"extension restriction substitution\"\n" +
        		"                        form=\"unqualified\" maxOccurs=\"unbounded\"\n" +
        		"                        minOccurs=\"0\" name=\"TrackingInfo\" type=\"xs:string\"/>\n" +
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
        		"        <xs:schema attributeFormDefault=\"unqualified\"\n" +
        		"            elementFormDefault=\"qualified\"\n" +
        		"            targetNamespace=\"http://www.tibco.com/pe/WriteToLogActivitySchema\"\n" +
        		"            xmlns:tns=\"http://www.tibco.com/pe/WriteToLogActivitySchema\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
        		"            <xs:complexType name=\"LogParametersType\">\n" +
        		"                <xs:sequence>\n" +
        		"                    <xs:element form=\"unqualified\" minOccurs=\"0\"\n" +
        		"                        name=\"msgCode\" type=\"xs:string\"/>\n" +
        		"                    <xs:element form=\"unqualified\" minOccurs=\"0\"\n" +
        		"                        name=\"loggerName\" type=\"xs:string\"/>\n" +
        		"                    <xs:element form=\"unqualified\" minOccurs=\"0\"\n" +
        		"                        name=\"logLevel\" type=\"xs:string\"/>\n" +
        		"                    <xs:element form=\"unqualified\" name=\"message\" type=\"xs:string\"/>\n" +
        		"                </xs:sequence>\n" +
        		"            </xs:complexType>\n" +
        		"            <xs:element name=\"ActivityInput\" type=\"tns:LogParametersType\"/>\n" +
        		"        </xs:schema>\n" +
        		"    </tibex:Types>\n" +
        		"    <tibex:ProcessInfo callable=\"true\" createdBy=\"richard\"\n" +
        		"        createdOn=\"Tue Feb 01 12:04:39 GMT 2022\" description=\"\"\n" +
        		"        extraErrorVars=\"true\" modifiers=\"public\"\n" +
        		"        productVersion=\"2.6.0 V34 2020-11-06\" scalable=\"true\"\n" +
        		"        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n" +
        		"    <tibex:ProcessInterface context=\"\"\n" +
        		"        input=\"{http://www.example.com/namespaces/tns/1643717165856}Request\" output=\"\"/>\n" +
        		"    <tibex:ProcessTemplateConfigurations/>\n" +
        		"    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n" +
        		"        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n" +
        		"        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        		"        <children type=\"2001\">\n" +
        		"            <children type=\"5001\"/>\n" +
        		"            <children type=\"3002\">\n" +
        		"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"            </children>\n" +
        		"            <children type=\"3003\">\n" +
        		"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
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
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n" +
        		"                                    <layoutConstraint x=\"22\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"173\"/>\n" +
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
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
        		"                                    <layoutConstraint x=\"731\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"171\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4018\">\n" +
        		"                                    <children type=\"3018\">\n" +
        		"                                    <children type=\"4020\">\n" +
        		"                                    <children type=\"3020\">\n" +
        		"                                    <children type=\"4005\">\n" +
        		"                                    <children type=\"3007\">\n" +
        		"\n" +
        		"                                    <children type=\"4002\">\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@activities.2\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    x=\"12\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"8\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4002\">\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@activities.3\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    x=\"12\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"149\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    height=\"291\"\n" +
        		"                                    width=\"170\" xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    height=\"259\"\n" +
        		"                                    width=\"173\"\n" +
        		"                                    x=\"15\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"20\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4002\">\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@activities.0\"/>\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4002\">\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@activities.1\"/>\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    height=\"303\" width=\"213\"\n" +
        		"                                    x=\"235\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"51\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4018\">\n" +
        		"                                    <children type=\"3018\">\n" +
        		"                                    <children type=\"4020\">\n" +
        		"                                    <children type=\"3020\">\n" +
        		"                                    <children type=\"4005\">\n" +
        		"                                    <children type=\"3007\">\n" +
        		"\n" +
        		"                                    <children type=\"4002\">\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@activities.2\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    x=\"12\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"8\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4002\">\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <children type=\"4017\">\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@activities.3\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    x=\"12\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"149\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    height=\"291\"\n" +
        		"                                    width=\"170\" xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    height=\"259\"\n" +
        		"                                    width=\"173\"\n" +
        		"                                    x=\"15\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"20\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4002\">\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@activities.0\"/>\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4002\">\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <children type=\"4017\">\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles\n" +
        		"                                    fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles\n" +
        		"                                    backgroundColor=\"16777215\"\n" +
        		"                                    gradientEndColor=\"50431\"\n" +
        		"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@activities.1\"/>\n" +
        		"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                                    </children>\n" +
        		"                                    <styles fontName=\"Segoe UI\"\n" +
        		"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                                    <styles restoreHeight=\"303\"\n" +
        		"                                    restoreWidth=\"213\"\n" +
        		"                                    restoreX=\"474\"\n" +
        		"                                    restoreY=\"61\" xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
        		"                                    <layoutConstraint\n" +
        		"                                    height=\"303\" width=\"213\"\n" +
        		"                                    x=\"499\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"53\"/>\n" +
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
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.4\"/>\n" +
        		"                                    <layoutConstraint x=\"473\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"483\"/>\n" +
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
        		"                                    <element href=\"//0/@process/@activity/@activity/@activities.5\"/>\n" +
        		"                                    <layoutConstraint x=\"127\"\n" +
        		"                                    xsi:type=\"notation:Bounds\" y=\"539\"/>\n" +
        		"                                    </children>\n";
    	
    	final String part2 = "                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
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
        		"                                    <layoutConstraint height=\"637\"\n" +
        		"                                    width=\"864\" xsi:type=\"notation:Bounds\"/>\n" +
        		"                                </children>\n" +
        		"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                            </children>\n" +
        		"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                            <element href=\"//0/@process/@activity\"/>\n" +
        		"                            <layoutConstraint height=\"386\" width=\"867\" xsi:type=\"notation:Bounds\"/>\n" +
        		"                        </children>\n" +
        		"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                        <element href=\"//0/@process/@activity\"/>\n" +
        		"                    </children>\n" +
        		"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        		"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        		"                    <element href=\"//0/@process/@activity\"/>\n" +
        		"                    <layoutConstraint height=\"410\" width=\"867\" x=\"1\"\n" +
        		"                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n" +
        		"                </children>\n" +
        		"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        		"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        		"                <element href=\"//0/@process\"/>\n" +
        		"            </children>\n" +
        		"            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        		"            <element href=\"//0/@process\"/>\n" +
        		"            <layoutConstraint height=\"713\" width=\"1209\" xsi:type=\"notation:Bounds\"/>\n" +
        		"        </children>\n" +
        		"        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
        		"        <element href=\"//0\"/>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
        		"            <bendpoints\n" +
        		"                points=\"[25, 0, -158, -25]$[94, 0, -89, -25]$[94, -2, -89, -27]$[163, -2, -20, -27]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"            <targetAnchor id=\"(0.11382113821138211,0.6)\" xsi:type=\"notation:IdentityAnchor\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
        		"            <bendpoints points=\"[25, -17, -144, 97]$[145, -97, -24, 17]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"            <sourceAnchor id=\"(0.8873239436619719,0.5115511551155115)\" xsi:type=\"notation:IdentityAnchor\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2/@children.0/@children.1\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@links/@children.0\"/>\n" +
        		"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@links/@children.1\"/>\n" +
        		"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2/@children.0/@children.2\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.2/@activity/@activities.1/@activity/@activity/@links/@children.2\"/>\n" +
        		"            <bendpoints points=\"[25, -9, -70, 24]$[83, -29, -12, 4]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.1\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@links/@children.0\"/>\n" +
        		"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.2\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@links/@children.1\"/>\n" +
        		"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"            <targetAnchor id=\"(0.125,0.625)\" xsi:type=\"notation:IdentityAnchor\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.2\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@links/@children.2\"/>\n" +
        		"            <bendpoints points=\"[25, -9, -70, 24]$[83, -29, -12, 4]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.1\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1/@activity/@activity/@links/@children.3\"/>\n" +
        		"            <bendpoints points=\"[12, 7, -69, -42]$[57, 34, -24, -15]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.2\"/>\n" +
        		"            <bendpoints points=\"[89, 2, -175, 0]$[175, 0, -89, -2]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.3\"/>\n" +
        		"            <bendpoints\n" +
        		"                points=\"[25, -2, -684, 0]$[74, -2, -635, 0]$[74, 251, -635, 253]$[709, 251, 0, 253]$[709, 23, 0, 25]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.4\"/>\n" +
        		"            <bendpoints\n" +
        		"                points=\"[0, 25, -105, -341]$[0, 366, -105, 0]$[81, 366, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.5\"/>\n" +
        		"            <bendpoints\n" +
        		"                points=\"[0, 25, -451, -285]$[0, 310, -451, 0]$[427, 310, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.6\"/>\n" +
        		"            <bendpoints points=\"[25, -4, -321, 52]$[322, -52, -24, 4]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"        <edges\n" +
        		"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\"\n" +
        		"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        		"            <children type=\"6002\">\n" +
        		"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        		"            </children>\n" +
        		"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        		"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        		"            <element href=\"//0/@process/@activity/@activity/@links/@children.7\"/>\n" +
        		"            <bendpoints\n" +
        		"                points=\"[25, 0, -233, 312]$[258, 0, 0, 312]$[258, -287, 0, 25]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        		"        </edges>\n" +
        		"    </notation:Diagram>\n" +
        		"    <tibex:NamespaceRegistry enabled=\"true\">\n" +
        		"        <tibex:namespaceItem\n" +
        		"            namespace=\"http://www.tibco.com/pe/WriteToLogActivitySchema\" prefix=\"tns\"/>\n" +
        		"        <tibex:namespaceItem\n" +
        		"            namespace=\"http://www.w3.org/2001/XMLSchema\" prefix=\"xsd\"/>\n" +
        		"    </tibex:NamespaceRegistry>\n" +
        		"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.example.com/namespaces/tns/1643717165856\"/>\n" +
        		"    <bpws:variables>\n" +
        		"        <bpws:variable element=\"ns:ProcessContext\"\n" +
        		"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
        		"        <bpws:variable element=\"ns0:ActivityInput\" name=\"Log1-input\" sca-bpel:internal=\"true\"/>\n" +
        		"        <bpws:variable element=\"ns1:Request\" name=\"Start\"\n" +
        		"            sca-bpel:internal=\"true\" tibex:parameter=\"in\"/>\n" +
        		"        <bpws:variable element=\"ns0:ActivityInput\" name=\"Log2-input\" sca-bpel:internal=\"true\"/>\n" +
        		"        <bpws:variable element=\"ns0:ActivityInput\" name=\"Log3-input\" sca-bpel:internal=\"true\"/>\n" +
        		"        <bpws:variable element=\"ns0:ActivityInput\" name=\"Log4-input\" sca-bpel:internal=\"true\"/>\n" +
        		"        <bpws:variable element=\"ns0:ActivityInput\" name=\"Log-input\" sca-bpel:internal=\"true\"/>\n" +
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
        		"                <bpws:link name=\"StartToForEach\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"Copy_1_ForEachToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"ForEachToCopy_1_ForEach\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"StartToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"StartToLog\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"StartToEmpty\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"LogToEmpty\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                <bpws:link name=\"EmptyToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"            </bpws:links>\n" +
        		"            <bpws:extensionActivity>\n" +
        		"                <tibex:receiveEvent createInstance=\"yes\"\n" +
        		"                    eventTimeout=\"0\" name=\"Start\"\n" +
        		"                    tibex:xpdlId=\"c1faba4c-1b79-4aa3-81bc-ba7c8584f3a1\"\n" +
        		"                    variable=\"Start\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                    <bpws:sources>\n" +
        		"                        <bpws:source linkName=\"StartToForEach\"/>\n" +
        		"                        <bpws:source linkName=\"StartToEnd\"/>\n" +
        		"                        <bpws:source linkName=\"StartToLog\"/>\n" +
        		"                        <bpws:source linkName=\"StartToEmpty\"/>\n" +
        		"                    </bpws:sources>\n" +
        		"                    <tibex:eventSource>\n" +
        		"                        <tibex:StartEvent xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
        		"                    </tibex:eventSource>\n" +
        		"                </tibex:receiveEvent>\n" +
        		"            </bpws:extensionActivity>\n" +
        		"            <bpws:extensionActivity>\n" +
        		"                <tibex:activityExtension name=\"End\"\n" +
        		"                    tibex:xpdlId=\"67430d4d-0d71-4978-93fc-424ba8f19c31\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                    <bpws:targets>\n" +
        		"                        <bpws:target linkName=\"Copy_1_ForEachToEnd\"/>\n" +
        		"                        <bpws:target linkName=\"StartToEnd\"/>\n" +
        		"                        <bpws:target linkName=\"EmptyToEnd\"/>\n" +
        		"                    </bpws:targets>\n" +
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
        		"            <bpws:scope name=\"ForEach1\" tibex:group=\"foreach\"\n" +
        		"                tibex:indexOriginalName=\"index\"\n" +
        		"                tibex:indexToGlobal=\"false\" tibex:xpdlId=\"04290573-02ca-4699-bfc9-f3be35e10db2\">\n" +
        		"                <bpws:targets>\n" +
        		"                    <bpws:target linkName=\"StartToForEach\"/>\n" +
        		"                </bpws:targets>\n" +
        		"                <bpws:sources>\n" +
        		"                    <bpws:source linkName=\"ForEachToCopy_1_ForEach\"/>\n" +
        		"                </bpws:sources>\n" +
        		"                <bpws:sequence name=\"sequence\" tibex:group=\"foreach\">\n" +
        		"                    <bpws:empty name=\"GroupInit\" tibex:group=\"groupInit\"/>\n" +
        		"                    <bpws:forEach counterName=\"index\" name=\"forEach\"\n" +
        		"                        parallel=\"no\" tibex:group=\"foreach\">\n" +
        		"                        <bpws:startCounterValue><![CDATA[1]]></bpws:startCounterValue>\n" +
        		"                        <bpws:finalCounterValue><![CDATA[3]]></bpws:finalCounterValue>\n" +
        		"                        <bpws:scope name=\"innerScope\">\n" +
        		"                            <bpws:flow name=\"flow1\">\n" +
        		"                                <bpws:links>\n" +
        		"                                    <bpws:link name=\"GroupStartToLog\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                    <bpws:link name=\"LogToCopy_1_Log\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                    <bpws:link\n" +
        		"                                    name=\"Copy_1_LogToGroupEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                </bpws:links>\n" +
        		"                                <bpws:empty name=\"GroupStart\"\n" +
        		"                                    tibex:group=\"groupStart\" tibex:xpdlId=\"7aae808f-23a4-421d-9f29-f0983a6abd92\">\n" +
        		"                                    <bpws:sources>\n" +
        		"                                    <bpws:source linkName=\"GroupStartToLog\"/>\n" +
        		"                                    </bpws:sources>\n" +
        		"                                </bpws:empty>\n" +
        		"                                <bpws:empty name=\"GroupEnd\"\n" +
        		"                                    tibex:group=\"groupEnd\" tibex:xpdlId=\"c5cc1465-5caa-467e-9f6d-5a2452dcfcb0\">\n" +
        		"                                    <bpws:targets>\n" +
        		"                                    <bpws:target linkName=\"Copy_1_LogToGroupEnd\"/>\n" +
        		"                                    </bpws:targets>\n" +
        		"                                </bpws:empty>\n" +
        		"                                <bpws:extensionActivity>\n" +
        		"                                    <tibex:activityExtension\n" +
        		"                                    inputVariable=\"Log1-input\"\n" +
        		"                                    name=\"Log1\"\n" +
        		"                                    tibex:xpdlId=\"440de6c7-272d-4d33-a17e-c28cea24ca62\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                                    <bpws:targets>\n" +
        		"                                    <bpws:target linkName=\"GroupStartToLog\"/>\n" +
        		"                                    </bpws:targets>\n" +
        		"                                    <bpws:sources>\n" +
        		"                                    <bpws:source linkName=\"LogToCopy_1_Log\"/>\n" +
        		"                                    </bpws:sources>\n" +
        		"                                    <tibex:inputBindings>\n" +
        		"                                    <tibex:inputBinding\n" +
        		"                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/pe/WriteToLogActivitySchema&quot; xmlns:xsd=&quot;http://www.w3.org/2001/XMLSchema&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;index&quot;/>&lt;xsl:template name=&quot;Log-input&quot; match=&quot;/&quot;>&lt;tns:ActivityInput>&lt;message>&lt;xsl:value-of select=&quot;concat('Loop ', xsd:string($index))&quot;/>&lt;/message>&lt;/tns:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        		"                                    </tibex:inputBindings>\n" +
        		"                                    <tibex:config>\n" +
        		"                                    <bwext:BWActivity\n" +
        		"                                    activityTypeID=\"bw.generalactivities.log\"\n" +
        		"                                    version=\"6.0.0.001\"\n" +
        		"                                    xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        		"                                    xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        		"                                    xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        		"                                    <activityConfig>\n" +
        		"                                    <properties\n" +
        		"                                    name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        		"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Log\"/>\n" +
        		"                                    <value\n" +
        		"                                    role=\"Info\"\n" +
        		"                                    suppressJobInfo=\"true\" xsi:type=\"generalactivities:Log\"/>\n" +
        		"                                    </properties>\n" +
        		"                                    </activityConfig>\n" +
        		"                                    </bwext:BWActivity>\n" +
        		"                                    </tibex:config>\n" +
        		"                                    </tibex:activityExtension>\n" +
        		"                                </bpws:extensionActivity>\n" +
        		"                                <bpws:extensionActivity>\n" +
        		"                                    <tibex:activityExtension\n" +
        		"                                    inputVariable=\"Log2-input\"\n" +
        		"                                    name=\"Log2\"\n" +
        		"                                    tibex:xpdlId=\"c6bb887a-d323-4dcb-b53e-91b46dbd7e58\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                                    <bpws:targets>\n" +
        		"                                    <bpws:target linkName=\"LogToCopy_1_Log\"/>\n" +
        		"                                    </bpws:targets>\n" +
        		"                                    <bpws:sources>\n" +
        		"                                    <bpws:source linkName=\"Copy_1_LogToGroupEnd\"/>\n" +
        		"                                    </bpws:sources>\n" +
        		"                                    <tibex:inputBindings>\n" +
        		"                                    <tibex:inputBinding\n" +
        		"                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/pe/WriteToLogActivitySchema&quot; xmlns:xsd=&quot;http://www.w3.org/2001/XMLSchema&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;index&quot;/>&lt;xsl:template name=&quot;Log-input&quot; match=&quot;/&quot;>&lt;tns:ActivityInput>&lt;message>&lt;xsl:value-of select=&quot;concat('Loop ', xsd:string($index))&quot;/>&lt;/message>&lt;/tns:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        		"                                    </tibex:inputBindings>\n" +
        		"                                    <tibex:config>\n" +
        		"                                    <bwext:BWActivity\n" +
        		"                                    activityTypeID=\"bw.generalactivities.log\"\n" +
        		"                                    version=\"6.0.0.001\"\n" +
        		"                                    xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        		"                                    xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        		"                                    xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        		"                                    <activityConfig>\n" +
        		"                                    <properties\n" +
        		"                                    name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        		"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Log\"/>\n" +
        		"                                    <value\n" +
        		"                                    role=\"Info\"\n" +
        		"                                    suppressJobInfo=\"true\" xsi:type=\"generalactivities:Log\"/>\n" +
        		"                                    </properties>\n" +
        		"                                    </activityConfig>\n" +
        		"                                    </bwext:BWActivity>\n" +
        		"                                    </tibex:config>\n" +
        		"                                    </tibex:activityExtension>\n" +
        		"                                </bpws:extensionActivity>\n" +
        		"                            </bpws:flow>\n" +
        		"                        </bpws:scope>\n" +
        		"                    </bpws:forEach>\n" +
        		"                </bpws:sequence>\n" +
        		"            </bpws:scope>\n" +
        		"            <bpws:scope name=\"ForEach2\" tibex:group=\"foreach\"\n" +
        		"                tibex:indexOriginalName=\"index\"\n" +
        		"                tibex:indexToGlobal=\"false\" tibex:xpdlId=\"0d58fbba-e269-4303-a706-9d8227ca1383\">\n" +
        		"                <bpws:targets>\n" +
        		"                    <bpws:target linkName=\"ForEachToCopy_1_ForEach\"/>\n" +
        		"                </bpws:targets>\n" +
        		"                <bpws:sources>\n" +
        		"                    <bpws:source linkName=\"Copy_1_ForEachToEnd\"/>\n" +
        		"                </bpws:sources>\n" +
        		"                <bpws:sequence name=\"Copy_1_sequence\"\n" +
        		"                    tibex:group=\"foreach\" tibex:xpdlId=\"3401b559-fd51-4c1d-922a-6c06aec5139f\">\n" +
        		"                    <bpws:empty name=\"Copy_1_GroupInit\"\n" +
        		"                        tibex:group=\"groupInit\" tibex:xpdlId=\"7f1bf69c-54a5-475d-832d-bc2a750add61\"/>\n" +
        		"                    <bpws:forEach counterName=\"index\"\n" +
        		"                        name=\"Copy_1_forEach\" parallel=\"no\"\n" +
        		"                        tibex:group=\"foreach\" tibex:xpdlId=\"37be58bf-e84f-4e62-9d7b-248c761f3557\">\n" +
        		"                        <bpws:startCounterValue><![CDATA[1]]></bpws:startCounterValue>\n" +
        		"                        <bpws:finalCounterValue><![CDATA[3]]></bpws:finalCounterValue>\n" +
        		"                        <bpws:scope name=\"Copy_1_innerScope\" tibex:xpdlId=\"f1f2f7b5-788e-4bb4-8834-81b7c24a4604\">\n" +
        		"                            <bpws:flow name=\"Copy_1_flow1\" tibex:xpdlId=\"846b6550-a2b5-4b69-8084-565bd62c86e9\">\n" +
        		"                                <bpws:links>\n" +
        		"                                    <bpws:link name=\"GroupStartToLog1\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                    <bpws:link\n" +
        		"                                    name=\"Copy_2_LogToCopy_1_GroupEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                    <bpws:link\n" +
        		"                                    name=\"Copy_1_LogToGroupEnd1\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                    <bpws:link\n" +
        		"                                    name=\"Copy_1_GroupStartToCopy_3_Log\" tibex:linkType=\"SUCCESS\"/>\n" +
        		"                                </bpws:links>\n" +
        		"                                <bpws:empty name=\"Copy_1_GroupStart\"\n" +
        		"                                    tibex:group=\"groupStart\" tibex:xpdlId=\"89892b42-0e96-4bf8-8b4e-52fe7c99e4e8\">\n" +
        		"                                    <bpws:sources>\n" +
        		"                                    <bpws:source linkName=\"GroupStartToLog1\"/>\n" +
        		"                                    <bpws:source linkName=\"Copy_1_GroupStartToCopy_3_Log\"/>\n" +
        		"                                    </bpws:sources>\n" +
        		"                                </bpws:empty>\n" +
        		"                                <bpws:empty name=\"Copy_1_GroupEnd\"\n" +
        		"                                    tibex:group=\"groupEnd\" tibex:xpdlId=\"3a2d35cc-0f26-4f01-afa6-f38956a86d1f\">\n" +
        		"                                    <bpws:targets>\n" +
        		"                                    <bpws:target linkName=\"Copy_1_LogToGroupEnd1\"/>\n" +
        		"                                    <bpws:target linkName=\"Copy_2_LogToCopy_1_GroupEnd\"/>\n" +
        		"                                    </bpws:targets>\n" +
        		"                                </bpws:empty>\n" +
        		"                                <bpws:extensionActivity>\n" +
        		"                                    <tibex:activityExtension\n" +
        		"                                    inputVariable=\"Log3-input\"\n" +
        		"                                    name=\"Log3\"\n" +
        		"                                    tibex:xpdlId=\"5b0e01cd-2d97-45ed-9f6d-6e02081be67c\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                                    <bpws:targets>\n" +
        		"                                    <bpws:target linkName=\"GroupStartToLog1\"/>\n" +
        		"                                    </bpws:targets>\n" +
        		"                                    <bpws:sources>\n" +
        		"                                    <bpws:source linkName=\"Copy_2_LogToCopy_1_GroupEnd\"/>\n" +
        		"                                    </bpws:sources>\n" +
        		"                                    <tibex:inputBindings>\n" +
        		"                                    <tibex:inputBinding\n" +
        		"                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/pe/WriteToLogActivitySchema&quot; xmlns:xsd=&quot;http://www.w3.org/2001/XMLSchema&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;index&quot;/>&lt;xsl:template name=&quot;Log-input&quot; match=&quot;/&quot;>&lt;tns:ActivityInput>&lt;message>&lt;xsl:value-of select=&quot;concat('Loop ', xsd:string($index))&quot;/>&lt;/message>&lt;/tns:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        		"                                    </tibex:inputBindings>\n" +
        		"                                    <tibex:config>\n" +
        		"                                    <bwext:BWActivity\n" +
        		"                                    activityTypeID=\"bw.generalactivities.log\"\n" +
        		"                                    version=\"6.0.0.001\"\n" +
        		"                                    xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        		"                                    xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        		"                                    xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        		"                                    <activityConfig>\n" +
        		"                                    <properties\n" +
        		"                                    name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        		"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Log\"/>\n" +
        		"                                    <value\n" +
        		"                                    role=\"Info\"\n" +
        		"                                    suppressJobInfo=\"true\" xsi:type=\"generalactivities:Log\"/>\n" +
        		"                                    </properties>\n" +
        		"                                    </activityConfig>\n" +
        		"                                    </bwext:BWActivity>\n" +
        		"                                    </tibex:config>\n" +
        		"                                    </tibex:activityExtension>\n" +
        		"                                </bpws:extensionActivity>\n" +
        		"                                <bpws:extensionActivity>\n" +
        		"                                    <tibex:activityExtension\n" +
        		"                                    inputVariable=\"Log4-input\"\n" +
        		"                                    name=\"Log4\"\n" +
        		"                                    tibex:xpdlId=\"be217af7-4f9e-424a-9b2f-2be9ff474280\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                                    <bpws:targets>\n" +
        		"                                    <bpws:target linkName=\"Copy_1_GroupStartToCopy_3_Log\"/>\n" +
        		"                                    </bpws:targets>\n" +
        		"                                    <bpws:sources>\n" +
        		"                                    <bpws:source linkName=\"Copy_1_LogToGroupEnd1\"/>\n" +
        		"                                    </bpws:sources>\n" +
        		"                                    <tibex:inputBindings>\n" +
        		"                                    <tibex:inputBinding\n" +
        		"                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/pe/WriteToLogActivitySchema&quot; xmlns:xsd=&quot;http://www.w3.org/2001/XMLSchema&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;index&quot;/>&lt;xsl:template name=&quot;Log-input&quot; match=&quot;/&quot;>&lt;tns:ActivityInput>&lt;message>&lt;xsl:value-of select=&quot;concat('Loop ', xsd:string($index))&quot;/>&lt;/message>&lt;/tns:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        		"                                    </tibex:inputBindings>\n" +
        		"                                    <tibex:config>\n" +
        		"                                    <bwext:BWActivity\n" +
        		"                                    activityTypeID=\"bw.generalactivities.log\"\n" +
        		"                                    version=\"6.0.0.001\"\n" +
        		"                                    xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        		"                                    xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        		"                                    xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        		"                                    <activityConfig>\n" +
        		"                                    <properties\n" +
        		"                                    name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        		"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Log\"/>\n" +
        		"                                    <value\n" +
        		"                                    role=\"Info\"\n" +
        		"                                    suppressJobInfo=\"true\" xsi:type=\"generalactivities:Log\"/>\n" +
        		"                                    </properties>\n" +
        		"                                    </activityConfig>\n" +
        		"                                    </bwext:BWActivity>\n" +
        		"                                    </tibex:config>\n" +
        		"                                    </tibex:activityExtension>\n" +
        		"                                </bpws:extensionActivity>\n" +
        		"                            </bpws:flow>\n" +
        		"                        </bpws:scope>\n" +
        		"                    </bpws:forEach>\n" +
        		"                </bpws:sequence>\n" +
        		"            </bpws:scope>\n" +
        		"            <bpws:empty name=\"Empty\" tibex:xpdlId=\"156d743e-0b95-4fe5-afc0-67f71486a9ab\">\n" +
        		"                <bpws:targets>\n" +
        		"                    <bpws:target linkName=\"StartToEmpty\"/>\n" +
        		"                    <bpws:target linkName=\"LogToEmpty\"/>\n" +
        		"                </bpws:targets>\n" +
        		"                <bpws:sources>\n" +
        		"                    <bpws:source linkName=\"EmptyToEnd\"/>\n" +
        		"                </bpws:sources>\n" +
        		"            </bpws:empty>\n" +
        		"            <bpws:extensionActivity>\n" +
        		"                <tibex:activityExtension inputVariable=\"Log-input\"\n" +
        		"                    name=\"Log\"\n" +
        		"                    tibex:xpdlId=\"4710d993-a187-4da4-9e94-c9e4fa02a786\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        		"                    <bpws:targets>\n" +
        		"                        <bpws:target linkName=\"StartToLog\"/>\n" +
        		"                    </bpws:targets>\n" +
        		"                    <bpws:sources>\n" +
        		"                        <bpws:source linkName=\"LogToEmpty\"/>\n" +
        		"                    </bpws:sources>\n" +
        		"                    <tibex:inputBindings>\n" +
        		"                        <tibex:inputBinding\n" +
        		"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/pe/WriteToLogActivitySchema&quot; version=&quot;2.0&quot;>&lt;xsl:template name=&quot;Log-input&quot; match=&quot;/&quot;>&lt;tns:ActivityInput>&lt;message>&lt;xsl:value-of select=&quot;'message'&quot;/>&lt;/message>&lt;/tns:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        		"                    </tibex:inputBindings>\n" +
        		"                    <tibex:config>\n" +
        		"                        <bwext:BWActivity\n" +
        		"                            activityTypeID=\"bw.generalactivities.log\"\n" +
        		"                            version=\"6.0.0.001\"\n" +
        		"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        		"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        		"                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        		"                            <activityConfig>\n" +
        		"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        		"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Log\"/>\n" +
        		"                                    <value role=\"Info\"\n" +
        		"                                    suppressJobInfo=\"true\" xsi:type=\"generalactivities:Log\"/>\n" +
        		"                                </properties>\n" +
        		"                            </activityConfig>\n" +
        		"                        </bwext:BWActivity>\n" +
        		"                    </tibex:config>\n" +
        		"                </tibex:activityExtension>\n" +
        		"            </bpws:extensionActivity>\n" +
        		"        </bpws:flow>\n" +
        		"    </bpws:scope>\n" +
        		"</bpws:process>";
        		
    	source = new ProcessSource(new String(part1) + new String(part2));
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }



    @Test
    public void testValidate() {
        System.out.println("testValidate");
        MultipleTransitionCheck instance = new MultipleTransitionCheck();
        MultipleTransitionCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(), anyInt());        
        spyInstance.validate(source);        

        Mockito.verify(spyInstance,times(2)).reportIssueOnFile(anyString(), anyInt());
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(contains("group ForEach2."), anyInt());
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(contains("activity End."), anyInt());
    }
}
