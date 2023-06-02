/*
 * Copyright (C) 2016-2020. TIBCO Software Inc. All Rights Reserved. Confidential & Proprietary.
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
public class ForEachMappingCheckTest2 {
    
    private static ProcessSource source;
    
    public ForEachMappingCheckTest2() {
    }
     
    @BeforeClass
    public static void setUpClass() {
        source = new ProcessSource(
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
        "<bpws:process exitOnStandardFault=\"no\"\n" +
        "    name=\"foreachtransitiontest.module.SubProcess2\"\n" +
        "    suppressJoinFailure=\"yes\"\n" +
        "    targetNamespace=\"http://xmlns.example.com/20220202120616\"\n" +
        "    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
        "    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
        "    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
        "    xmlns:ns0=\"http://www.example.com/namespaces/tns/1643717165856\"\n" +
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
        "    </tibex:Types>\n" +
        "    <tibex:ProcessInfo callable=\"true\" createdBy=\"richard\"\n" +
        "        createdOn=\"Wed Feb 02 12:06:16 GMT 2022\" description=\"\"\n" +
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
        "                                    <layoutConstraint\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"113\"/>\n" +
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
        "                                    <layoutConstraint x=\"539\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"113\"/>\n" +
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
        "                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
        "                                    <layoutConstraint x=\"154\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n" +
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
        "                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
        "                                    <layoutConstraint x=\"154\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"113\"/>\n" +
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
        "                                    <layoutConstraint x=\"154\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"235\"/>\n" +
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
        "                                    <layoutConstraint x=\"284\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n" +
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
        "                                    <layoutConstraint x=\"294\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"113\"/>\n" +
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
        "                                    <layoutConstraint x=\"284\"\n" +
        "                                    xsi:type=\"notation:Bounds\" y=\"235\"/>\n" +
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
        "                                    width=\"875\" xsi:type=\"notation:Bounds\"/>\n" +
        "                                </children>\n" +
        "                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        "                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        "                            </children>\n" +
        "                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        "                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        "                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        "                            <element href=\"//0/@process/@activity\"/>\n" +
        "                            <layoutConstraint height=\"365\" width=\"444\"\n" +
        "                                x=\"30\" xsi:type=\"notation:Bounds\" y=\"20\"/>\n" +
        "                        </children>\n" +
        "                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        "                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        "                        <element href=\"//0/@process/@activity\"/>\n" +
        "                    </children>\n" +
        "                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        "                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
        "                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
        "                    <element href=\"//0/@process/@activity\"/>\n" +
        "                    <layoutConstraint height=\"409\" width=\"519\" xsi:type=\"notation:Bounds\"/>\n" +
        "                </children>\n" +
        "                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
        "                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
        "                <element href=\"//0/@process\"/>\n" +
        "            </children>\n" +
        "            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
        "            <element href=\"//0/@process\"/>\n" +
        "            <layoutConstraint height=\"460\" width=\"1220\" xsi:type=\"notation:Bounds\"/>\n" +
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
        "            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
        "            <bendpoints points=\"[25, 0, -121, 0]$[122, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.2\"/>\n" +
        "            <bendpoints points=\"[25, 18, -127, -93]$[128, 93, -24, -18]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.6\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.3\"/>\n" +
        "            <bendpoints points=\"[25, -19, -121, 92]$[122, -92, -24, 19]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.4\"/>\n" +
        "            <bendpoints\n" +
        "                points=\"[16, 25, -136, -208]$[136, 209, -16, -24]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.7\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.5\"/>\n" +
        "            <bendpoints\n" +
        "                points=\"[15, -24, -131, 209]$[131, -208, -15, 25]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.6\"/>\n" +
        "            <bendpoints\n" +
        "                points=\"[25, 11, -230, -100]$[231, 100, -24, -11]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.6\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.7\"/>\n" +
        "            <bendpoints points=\"[25, 0, -220, 0]$[221, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "        <edges\n" +
        "            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.7\"\n" +
        "            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
        "            <children type=\"6002\">\n" +
        "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
        "            </children>\n" +
        "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
        "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
        "            <element href=\"//0/@process/@activity/@activity/@links/@children.8\"/>\n" +
        "            <bendpoints\n" +
        "                points=\"[25, -12, -230, 110]$[231, -110, -24, 12]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
        "        </edges>\n" +
        "    </notation:Diagram>\n" +
        "    <tibex:NamespaceRegistry enabled=\"true\">\n" +
        "        <tibex:namespaceItem\n" +
        "            namespace=\"http://www.example.com/namespaces/tns/1643717165856\" prefix=\"tns\"/>\n" +
        "    </tibex:NamespaceRegistry>\n" +
        "    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.example.com/namespaces/tns/1643717165856\"/>\n" +
        "    <bpws:variables>\n" +
        "        <bpws:variable element=\"ns:ProcessContext\"\n" +
        "            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\" name=\"Start\"\n" +
        "            sca-bpel:internal=\"true\" tibex:parameter=\"in\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\"\n" +
        "            name=\"Mapper-For-Each-input\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\" name=\"Mapper-For-Each\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\"\n" +
        "            name=\"Mapper-For-Each-Disabled-input\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\"\n" +
        "            name=\"Mapper-For-Each-Disabled\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\" name=\"Mapper-Copy-Of-input\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\" name=\"Mapper-Copy-Of\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\"\n" +
        "            name=\"SubProc-For-Each-input\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\"\n" +
        "            name=\"SubProc-For-Each-Disabled-input\" sca-bpel:internal=\"true\"/>\n" +
        "        <bpws:variable element=\"ns0:Request\"\n" +
        "            name=\"SubProc-Copy-Of-input\" sca-bpel:internal=\"true\"/>\n" +
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
        "                <bpws:link name=\"StartToMapper\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link name=\"Mapper-For-EachToCopyOfSubProcess2\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link name=\"StartToCopy_1_Mapper-For-Each\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link\n" +
        "                    name=\"Mapper-For-Each-DisabledToCopy_1_CopyOfSubProcess2\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link name=\"StartToMapper-Copy-Of\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link\n" +
        "                    name=\"Mapper-Copy-OfToCopy_2_CopyOfSubProcess2\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link name=\"CopyOfSubProcess2ToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link name=\"Copy_1_CopyOfSubProcess2ToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        "                <bpws:link name=\"SubProc-Copy-OfToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
        "            </bpws:links>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:receiveEvent createInstance=\"yes\"\n" +
        "                    eventTimeout=\"0\" name=\"Start\"\n" +
        "                    tibex:xpdlId=\"9f36d50f-5fb0-4616-be93-e282450c6fd3\"\n" +
        "                    variable=\"Start\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"StartToMapper\"/>\n" +
        "                        <bpws:source linkName=\"StartToCopy_1_Mapper-For-Each\"/>\n" +
        "                        <bpws:source linkName=\"StartToMapper-Copy-Of\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:eventSource>\n" +
        "                        <tibex:StartEvent xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
        "                    </tibex:eventSource>\n" +
        "                </tibex:receiveEvent>\n" +
        "            </bpws:extensionActivity>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:activityExtension name=\"End\"\n" +
        "                    tibex:xpdlId=\"b693215d-9230-437c-a08c-3fc2dcb7b86b\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"CopyOfSubProcess2ToEnd\"/>\n" +
        "                        <bpws:target linkName=\"Copy_1_CopyOfSubProcess2ToEnd\"/>\n" +
        "                        <bpws:target linkName=\"SubProc-Copy-OfToEnd\"/>\n" +
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
        "            <bpws:extensionActivity>\n" +
        "                <tibex:activityExtension\n" +
        "                    inputVariable=\"Mapper-For-Each-input\"\n" +
        "                    name=\"Mapper-For-Each\"\n" +
        "                    outputVariable=\"Mapper-For-Each\"\n" +
        "                    tibex:xpdlId=\"8bae0b29-caa3-4be8-9ca4-425f3a7bd2a7\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"StartToMapper\"/>\n" +
        "                    </bpws:targets>\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"Mapper-For-EachToCopyOfSubProcess2\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:inputBindings>\n" +
        "                        <tibex:inputBinding\n" +
        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.example.com/namespaces/tns/1643717165856&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;Mapper-For-Each-input&quot; match=&quot;/&quot;>&lt;tns:Request>&lt;xsl:for-each select=&quot;$Start/tns:value&quot;>&lt;tns:value>&lt;xsl:value-of select=&quot;.&quot;/>&lt;/tns:value>&lt;/xsl:for-each>&lt;/tns:Request>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        "                    </tibex:inputBindings>\n" +
        "                    <tibex:config>\n" +
        "                        <bwext:BWActivity\n" +
        "                            activityTypeID=\"bw.generalactivities.mapper\"\n" +
        "                            version=\"6.0.0.001\"\n" +
        "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        "                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\"\n" +
        "                            xmlns:tns_1643717165856=\"http://www.example.com/namespaces/tns/1643717165856\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        "                            <activityConfig>\n" +
        "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        "                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Mapper\"/>\n" +
        "                                    <value\n" +
        "                                    inputQName=\"tns_1643717165856:Request\" xsi:type=\"generalactivities:Mapper\"/>\n" +
        "                                </properties>\n" +
        "                            </activityConfig>\n" +
        "                        </bwext:BWActivity>\n" +
        "                    </tibex:config>\n" +
        "                </tibex:activityExtension>\n" +
        "            </bpws:extensionActivity>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:activityExtension\n" +
        "                    inputVariable=\"Mapper-For-Each-Disabled-input\"\n" +
        "                    name=\"Mapper-For-Each-Disabled\"\n" +
        "                    outputVariable=\"Mapper-For-Each-Disabled\"\n" +
        "                    tibex:xpdlId=\"b1e37bd9-3f03-4f1c-8a2f-d9aa8ba57ef5\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:documentation>Description 1&#xd;\n" +
        "Description 2&#xd;\n" +
        "SQIGNORE:ForEachMapping&#xd;\n" +
        "Description 3&#xd;\n" +
        "</bpws:documentation>\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"StartToCopy_1_Mapper-For-Each\"/>\n" +
        "                    </bpws:targets>\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"Mapper-For-Each-DisabledToCopy_1_CopyOfSubProcess2\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:inputBindings>\n" +
        "                        <tibex:inputBinding\n" +
        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.example.com/namespaces/tns/1643717165856&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;Mapper-For-Each-input&quot; match=&quot;/&quot;>&lt;tns:Request>&lt;xsl:for-each select=&quot;$Start/tns:value&quot;>&lt;tns:value>&lt;xsl:value-of select=&quot;.&quot;/>&lt;/tns:value>&lt;/xsl:for-each>&lt;/tns:Request>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        "                    </tibex:inputBindings>\n" +
        "                    <tibex:config>\n" +
        "                        <bwext:BWActivity\n" +
        "                            activityTypeID=\"bw.generalactivities.mapper\"\n" +
        "                            version=\"6.0.0.001\"\n" +
        "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        "                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\"\n" +
        "                            xmlns:tns_1643717165856=\"http://www.example.com/namespaces/tns/1643717165856\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        "                            <activityConfig>\n" +
        "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        "                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Mapper\"/>\n" +
        "                                    <value\n" +
        "                                    inputQName=\"tns_1643717165856:Request\" xsi:type=\"generalactivities:Mapper\"/>\n" +
        "                                </properties>\n" +
        "                            </activityConfig>\n" +
        "                        </bwext:BWActivity>\n" +
        "                    </tibex:config>\n" +
        "                </tibex:activityExtension>\n" +
        "            </bpws:extensionActivity>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:activityExtension\n" +
        "                    inputVariable=\"Mapper-Copy-Of-input\"\n" +
        "                    name=\"Mapper-Copy-Of\"\n" +
        "                    outputVariable=\"Mapper-Copy-Of\"\n" +
        "                    tibex:xpdlId=\"b42f9c89-5133-4051-80e5-fe495e203592\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"StartToMapper-Copy-Of\"/>\n" +
        "                    </bpws:targets>\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"Mapper-Copy-OfToCopy_2_CopyOfSubProcess2\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:inputBindings>\n" +
        "                        <tibex:inputBinding\n" +
        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.example.com/namespaces/tns/1643717165856&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;Start&quot;/>&#xa;    &lt;xsl:template name=&quot;Mapper-Copy-Of-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns:Request>&#xa;            &lt;xsl:copy-of select=&quot;$Start/tns:value&quot;/>&#xa;        &lt;/tns:Request>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        "                    </tibex:inputBindings>\n" +
        "                    <tibex:config>\n" +
        "                        <bwext:BWActivity\n" +
        "                            activityTypeID=\"bw.generalactivities.mapper\"\n" +
        "                            version=\"6.0.0.001\"\n" +
        "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
        "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
        "                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\"\n" +
        "                            xmlns:tns_1643717165856=\"http://www.example.com/namespaces/tns/1643717165856\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
        "                            <activityConfig>\n" +
        "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
        "                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Mapper\"/>\n" +
        "                                    <value\n" +
        "                                    inputQName=\"tns_1643717165856:Request\" xsi:type=\"generalactivities:Mapper\"/>\n" +
        "                                </properties>\n" +
        "                            </activityConfig>\n" +
        "                        </bwext:BWActivity>\n" +
        "                    </tibex:config>\n" +
        "                </tibex:activityExtension>\n" +
        "            </bpws:extensionActivity>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:extActivity\n" +
        "                    inputVariable=\"SubProc-For-Each-input\"\n" +
        "                    name=\"SubProc-For-Each\"\n" +
        "                    tibex:xpdlId=\"73e69213-aa81-4e86-9b9a-78c0c0f126a5\"\n" +
        "                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"Mapper-For-EachToCopyOfSubProcess2\"/>\n" +
        "                    </bpws:targets>\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"CopyOfSubProcess2ToEnd\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:inputBindings>\n" +
        "                        <tibex:inputBinding coercion=\"\"\n" +
        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.example.com/namespaces/tns/1643717165856&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Mapper-For-Each&quot;/>&lt;xsl:template name=&quot;CopyOfSubProcess2-input&quot; match=&quot;/&quot;>&lt;tns:Request>&lt;xsl:for-each select=&quot;$Mapper-For-Each/tns:value&quot;>&lt;tns:value>&lt;xsl:value-of select=&quot;.&quot;/>&lt;/tns:value>&lt;/xsl:for-each>&lt;/tns:Request>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        "                    </tibex:inputBindings>\n" +
        "                    <tibex:CallProcess\n" +
        "                        subProcessName=\"foreachtransitiontest.module.CopyOfSubProcess2\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
        "                </tibex:extActivity>\n" +
        "            </bpws:extensionActivity>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:extActivity\n" +
        "                    inputVariable=\"SubProc-For-Each-Disabled-input\"\n" +
        "                    name=\"SubProc-For-Each-Disabled\"\n" +
        "                    tibex:xpdlId=\"f9a42414-4108-40d3-88ef-9f49e0d97c47\"\n" +
        "                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:documentation>Description 1&#xd;\n" +
        "Description 2&#xd;\n" +
        "SQIGNORE:ForEachMapping&#xd;\n" +
        "Description 3&#xd;\n" +
        "</bpws:documentation>\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"Mapper-For-Each-DisabledToCopy_1_CopyOfSubProcess2\"/>\n" +
        "                    </bpws:targets>\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"Copy_1_CopyOfSubProcess2ToEnd\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:inputBindings>\n" +
        "                        <tibex:inputBinding coercion=\"\"\n" +
        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.example.com/namespaces/tns/1643717165856&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Mapper-For-Each&quot;/>&lt;xsl:template name=&quot;CopyOfSubProcess2-input&quot; match=&quot;/&quot;>&lt;tns:Request>&lt;xsl:for-each select=&quot;$Mapper-For-Each/tns:value&quot;>&lt;tns:value>&lt;xsl:value-of select=&quot;.&quot;/>&lt;/tns:value>&lt;/xsl:for-each>&lt;/tns:Request>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        "                    </tibex:inputBindings>\n" +
        "                    <tibex:CallProcess\n" +
        "                        subProcessName=\"foreachtransitiontest.module.CopyOfSubProcess2\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
        "                </tibex:extActivity>\n" +
        "            </bpws:extensionActivity>\n" +
        "            <bpws:extensionActivity>\n" +
        "                <tibex:extActivity inputVariable=\"SubProc-Copy-Of-input\"\n" +
        "                    name=\"SubProc-Copy-Of\"\n" +
        "                    tibex:xpdlId=\"445c8a6e-44f4-4304-b29e-1409f015b6e7\"\n" +
        "                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
        "                    <bpws:targets>\n" +
        "                        <bpws:target linkName=\"Mapper-Copy-OfToCopy_2_CopyOfSubProcess2\"/>\n" +
        "                    </bpws:targets>\n" +
        "                    <bpws:sources>\n" +
        "                        <bpws:source linkName=\"SubProc-Copy-OfToEnd\"/>\n" +
        "                    </bpws:sources>\n" +
        "                    <tibex:inputBindings>\n" +
        "                        <tibex:inputBinding coercion=\"\"\n" +
        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.example.com/namespaces/tns/1643717165856&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;Start&quot;/>&#xa;    &lt;xsl:template name=&quot;SubProc-Copy-Of-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns:Request>&#xa;            &lt;xsl:copy-of select=&quot;$Start/tns:value&quot;/>&#xa;        &lt;/tns:Request>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
        "                    </tibex:inputBindings>\n" +
        "                    <tibex:CallProcess\n" +
        "                        subProcessName=\"foreachtransitiontest.module.CopyOfSubProcess2\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
        "                </tibex:extActivity>\n" +
        "            </bpws:extensionActivity>\n" +
        "        </bpws:flow>\n" +
        "    </bpws:scope>\n" +
        "</bpws:process>");
    }
    
    @AfterClass
    public static void tearDownClass() {
    }



    @Test
    public void testValidate() {
        System.out.println("testValidate");
        ForEachMappingCheck instance = new ForEachMappingCheck();
        ForEachMappingCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(), anyInt());        
        spyInstance.validate(source);        

        Mockito.verify(spyInstance,times(2)).reportIssueOnFile(anyString(), anyInt());
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(contains("For-Each is used in the Input mapping of activity Mapper-For-Each."), anyInt());
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(contains("For-Each is used in the Input mapping of activity SubProc-For-Each."), anyInt());
    }
}
