/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

/**
 *
 * @author avazquez
 */
public class UnneededEmptyActivityCheckTest {
    
     private static ProcessSource source;
    
    public UnneededEmptyActivityCheckTest() {
    }

     
    @BeforeClass
    public static void setUpClass() {
       
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\" name=\"arch_common.ARCH_SendEvent\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20171018194003\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns1=\"/ESB_patronesEnableDisable/Services/Patrones/Subprocesos/LogUnico/EscribirLogUnico-Start-Input.xsd\"\n" +
"    xmlns:ns6=\"http://www.framework.com/schemas/ARCH_ESBFramework\"\n" +
"    xmlns:ns7=\"http://www.framework.com/schemas/ARCH_AuditData\"\n" +
"    xmlns:ns8=\"http://www.framework.com/schemas/ARCH_LogUnico\"\n" +
"    xmlns:ns9=\"http://www.framework.com/schemas/ARCH_AuditoriaFichero\"\n" +
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
"        <wsdl:definitions\n" +
"            targetNamespace=\"http://xmlns.example.com/20171018194003\"\n" +
"            xmlns:tns=\"http://xmlns.example.com/20171018194003\"\n" +
"            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
"            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"/>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"true\" createdBy=\"juan.jose.martinez\"\n" +
"        createdOn=\"Thu Aug 10 17:40:11 CEST 2017\" description=\"\"\n" +
"        extraErrorVars=\"false\" modifiers=\"public\"\n" +
"        productVersion=\"6.4.0 V29 2017-05-09\" scalable=\"true\"\n" +
"        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n" +
"    <tibex:ProcessInterface context=\"\"\n" +
"        input=\"{http://www.framework.com/schemas/ARCH_AuditData}AuditData\" output=\"\"/>\n" +
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
"                                    <layoutConstraint x=\"-5\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"88\"/>\n" +
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
"                                    <layoutConstraint x=\"493\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"88\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.generalactivities.bwassign\">\n" +
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
"                                    x=\"74\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"88\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 com.tibco.bw.core.design.process.editor.Activity_4002_Empty\">\n" +
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
"                                    x=\"182\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"73\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.generalactivities.callprocess\">\n" +
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
"                                    x=\"328\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"8\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.generalactivities.callprocess\">\n" +
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
"                                    x=\"329\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"172\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 com.tibco.bw.core.design.process.editor.Activity_4002_Empty\">\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.6\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.6\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.6\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4017\">\n" +
"                                    <styles\n" +
"                                    fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.6\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.6\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"325\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"276\"/>\n" +
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
"                                    width=\"685\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                <element href=\"//0/@process/@activity\"/>\n" +
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
"            <layoutConstraint height=\"460\" width=\"1030\" xsi:type=\"notation:Bounds\"/>\n" +
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
"                points=\"[25, 0, -343, 0]$[185, 0, -183, 0]$[344, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint x=\"4\" xsi:type=\"notation:Location\" y=\"-12\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.2\"/>\n" +
"            <bendpoints\n" +
"                points=\"[25, 15, -138, -69]$[82, 15, -81, -69]$[82, 84, -81, 0]$[139, 84, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.3\"/>\n" +
"            <bendpoints\n" +
"                points=\"[25, 24, -137, 104]$[81, 24, -81, 104]$[81, -80, -81, 0]$[138, -80, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"            <sourceAnchor id=\"(0.7560975609756098,0.09333333333333334)\" xsi:type=\"notation:IdentityAnchor\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.4\"/>\n" +
"            <bendpoints points=\"[25, 12, -140, -68]$[141, 68, -24, -12]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\"\n" +
"            target=\"//@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.5\"/>\n" +
"            <bendpoints points=\"[25, -13, -139, 71]$[140, -71, -24, 13]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
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
"            <bendpoints\n" +
"                points=\"[25, 6, -143, 194]$[109, 6, -59, 194]$[109, -188, -59, 0]$[144, -188, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
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
"            <bendpoints\n" +
"                points=\"[25, 0, -134, -188]$[38, 0, -121, -188]$[38, 203, -121, 15]$[135, 203, -24, 15]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <tibex:NamespaceRegistry enabled=\"true\">\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\" prefix=\"esb1\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_AuditData\" prefix=\"ns1\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_LogUnico\" prefix=\"ns0\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"/ESB_patronesEnableDisable/Services/Patrones/Subprocesos/LogUnico/EscribirLogUnico-Start-Input.xsd\" prefix=\"ns\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.framework.com/schemas/ARCH_AuditoriaFichero\" prefix=\"ns2\"/>\n" +
"    </tibex:NamespaceRegistry>\n" +
"    <bpws:documentation>This subprocess sends the indicated Audit Event and writes a log by console. </bpws:documentation>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\"\n" +
"        location=\"../../Schemas/ARCH_EventType.xsd\" namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_AuditoriaFichero\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_AuditData\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://www.framework.com/schemas/ARCH_LogUnico\"/>\n" +
"    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"/ESB_patronesEnableDisable/Services/Patrones/Subprocesos/LogUnico/EscribirLogUnico-Start-Input.xsd\"/>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns7:AuditData\" name=\"Start\"\n" +
"            sca-bpel:internal=\"true\" tibex:parameter=\"in\"/>\n" +
"        <bpws:variable element=\"ns7:AuditData\"\n" +
"            name=\"ARCH_SendAuditEvent-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns6:EventType\" name=\"EventType\"/>\n" +
"        <bpws:variable name=\"localTracingLevel\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CFG/Architecture/Audit/traceLevel\" type=\"xsd:int\"/>\n" +
"        <bpws:variable element=\"ns9:EscribeFicheroAuditoria\"\n" +
"            name=\"Fichero-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"START_EVENT\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_AUDITTYPE_PROCIN\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"END_EVENT\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_AUDITTYPE_PROCOUT\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"REQUEST_EVENT\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_AUDITTYPE_REQUEST\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"RESPONSE_EVENT\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_AUDITTYPE_RESPONSE\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"ERROR_EVENT\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_AUDITTYPE_ERROR\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"LOG_EVENT\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CTE/AuditCodes/CTE_LOG\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"externalTracingLevel\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            tibex:propertySource=\"/CFG/Architecture/Audit/externalTraceLevel\" type=\"xsd:int\"/>\n" +
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
"                <bpws:link name=\"StartToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"AssignToEnd_Otherwise\"\n" +
"                    tibex:label=\"Otherwise\" tibex:linkType=\"SUCCESSWITHNOCONDITION\"/>\n" +
"                <bpws:link name=\"EmptyToCallProcess2\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"                <bpws:link name=\"EmptyToCallProcess\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"                <bpws:link name=\"CallProcessToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"CallProcess2ToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"Empty1ToEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"EmptyToTraceNotEnabled\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"0\" name=\"Start\"\n" +
"                    tibex:xpdlId=\"704671d7-d605-440a-8316-db4c99010954\"\n" +
"                    variable=\"Start\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"StartToEnd\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:eventSource>\n" +
"                        <tibex:StartEvent xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension name=\"End\"\n" +
"                    tibex:xpdlId=\"392aaaef-77ac-4337-9c4b-e13ef32ff82b\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"CallProcessToEnd\"/>\n" +
"                        <bpws:target linkName=\"CallProcess2ToEnd\"/>\n" +
"                        <bpws:target linkName=\"Empty1ToEnd\"/>\n" +
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
"                <tibex:activityExtension inputVariable=\"EventType\"\n" +
"                    name=\"AssignTraceLevel\" outputVariable=\"EventType\"\n" +
"                    tibex:xpdlId=\"8982cad9-9c4a-4083-9904-899b4f831158\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"StartToEnd\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"AssignToEnd_Otherwise\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression\n" +
"                                    expression=\"##otherwise##\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition><![CDATA[##otherwise##]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:ns1=&quot;http://www.framework.com/schemas/ARCH_AuditData&quot; xmlns:esb1=&quot;http://www.framework.com/schemas/ARCH_ESBFramework&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;EventType&quot; match=&quot;/&quot;>&lt;xsl:choose>&lt;xsl:when test=&quot;count($Start/ns1:ServiceInvocationEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;2&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:when test=&quot;count($Start/ns1:StartEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;2&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:when test=&quot;count($Start/ns1:EndEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;2&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:when test=&quot;count($Start/ns1:ErrorEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;1&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:when test=&quot;count($Start/ns1:ReplyEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;2&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:when test=&quot;count($Start/ns1:LogEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;3&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:when test=&quot;count($Start/ns1:AlarmEvent)=1&quot;>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;1&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:when>&lt;xsl:otherwise>&lt;esb1:EventType>&lt;esb1:EventType>&lt;xsl:value-of select=&quot;2&quot;/>&lt;/esb1:EventType>&lt;/esb1:EventType>&lt;/xsl:otherwise>&lt;/xsl:choose>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
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
"                                    <value processVariable=\"EventType\" xsi:type=\"generalactivities:BWAssign\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:empty name=\"Empty\" tibex:xpdlId=\"8094cd5d-a000-4b1c-88d7-599ff453eaf7\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"AssignToEnd_Otherwise\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"EmptyToCallProcess2\">\n" +
"                        <tibex:DesignExpression>\n" +
"                            <tibex:expression\n" +
"                                expression=\"$localTracingLevel ge $EventType/ns6:EventType\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                        </tibex:DesignExpression>\n" +
"                        <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[$localTracingLevel ge $EventType/ns6:EventType]]></bpws:transitionCondition>\n" +
"                    </bpws:source>\n" +
"                    <bpws:source linkName=\"EmptyToCallProcess\">\n" +
"                        <tibex:DesignExpression>\n" +
"                            <tibex:expression\n" +
"                                expression=\"$localTracingLevel ge $EventType/ns6:EventType\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                        </tibex:DesignExpression>\n" +
"                        <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[$localTracingLevel ge $EventType/ns6:EventType]]></bpws:transitionCondition>\n" +
"                    </bpws:source>\n" +
"                    <bpws:source linkName=\"EmptyToTraceNotEnabled\">\n" +
"                        <tibex:DesignExpression>\n" +
"                            <tibex:expression\n" +
"                                expression=\"$EventType/ns6:EventType > $localTracingLevel\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                        </tibex:DesignExpression>\n" +
"                        <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[$EventType/ns6:EventType > $localTracingLevel]]></bpws:transitionCondition>\n" +
"                    </bpws:source>\n" +
"                </bpws:sources>\n" +
"            </bpws:empty>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity\n" +
"                    inputVariable=\"ARCH_SendAuditEvent-input\"\n" +
"                    name=\"ARCH_SendAuditEvent\"\n" +
"                    tibex:xpdlId=\"f050b453-be40-4c45-92f4-a69dcc36da19\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"EmptyToCallProcess\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"CallProcessToEnd\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;ARCH_SendAuditEvent-input&quot; match=&quot;/&quot;>&lt;xsl:copy-of select=&quot;$Start&quot;/>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_common.ARCH_SendAuditEvent\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity inputVariable=\"Fichero-input\"\n" +
"                    name=\"Fichero\"\n" +
"                    tibex:xpdlId=\"b7da8db8-887a-442b-bed7-0d2eaa2235d2\"\n" +
"                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"EmptyToCallProcess2\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"CallProcess2ToEnd\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:ns1=&quot;http://www.framework.com/schemas/ARCH_AuditData&quot; xmlns:ns2=&quot;http://www.framework.com/schemas/ARCH_AuditoriaFichero&quot; xmlns:esb1=&quot;http://www.framework.com/schemas/ARCH_ESBFramework&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Start&quot;/>&lt;xsl:template name=&quot;Fichero-input&quot; match=&quot;/&quot;>&lt;ns2:EscribeFicheroAuditoria>&lt;xsl:choose>&lt;xsl:when test=&quot;$Start/ns1:StartEvent&quot;>&lt;xsl:copy-of select=&quot;$Start/ns1:StartEvent/esb1:Message&quot;/>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns1:EndEvent&quot;>&lt;xsl:copy-of select=&quot;$Start/ns1:EndEvent/esb1:Message&quot;/>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns1:ErrorEvent&quot;>&lt;esb1:Message>&lt;xsl:copy-of select=&quot;$Start/ns1:ErrorEvent/esb1:ArchitectureHeader&quot;/>&lt;esb1:Body>&lt;xsl:copy-of select=&quot;$Start/ns1:ErrorEvent/esb1:ErrorMessage&quot;/>&lt;/esb1:Body>&lt;/esb1:Message>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns1:AlarmEvent&quot;>&lt;esb1:Message>&lt;xsl:copy-of select=&quot;$Start/ns1:AlarmEvent/esb1:ArchitectureHeader&quot;/>&lt;esb1:Body>&lt;xsl:copy-of select=&quot;$Start/ns1:AlarmEvent/esb1:AlarmMessage&quot;/>&lt;/esb1:Body>&lt;/esb1:Message>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns1:ReplyEvent&quot;>&lt;xsl:copy-of select=&quot;$Start/ns1:ReplyEvent/esb1:Message&quot;/>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns1:ServiceInvocationEvent&quot;>&lt;esb1:Message>&lt;xsl:copy-of select=&quot;$Start/ns1:ServiceInvocationEvent/esb1:ArchitectureHeader&quot;/>&lt;esb1:Body>&lt;xsl:copy-of select=&quot;$Start/ns1:ServiceInvocationEvent/ns1:InvokingMessage/ancestor-or-self::*/namespace::node()&quot;/>&lt;xsl:copy-of select=&quot;$Start/ns1:ServiceInvocationEvent/ns1:InvokingMessage/@*&quot;/>&lt;xsl:copy-of select=&quot;$Start/ns1:ServiceInvocationEvent/ns1:InvokingMessage/node()&quot;/>&lt;/esb1:Body>&lt;/esb1:Message>&lt;/xsl:when>&lt;xsl:when test=&quot;$Start/ns1:ProcessHandlerEvent&quot;>&lt;xsl:copy-of select=&quot;$Start/ns1:ProcessHandlerEvent/esb1:Message&quot;/>&lt;/xsl:when>&lt;xsl:otherwise>&lt;esb1:Message>&lt;xsl:copy-of select=&quot;$Start/ns1:LogEvent/esb1:ArchitectureHeader&quot;/>&lt;esb1:Body>&lt;xsl:copy-of select=&quot;$Start/ns1:LogEvent/esb1:LogEvent&quot;/>&lt;/esb1:Body>&lt;/esb1:Message>&lt;/xsl:otherwise>&lt;/xsl:choose>&lt;auditEventType>&lt;xsl:value-of select=&quot;$Start/ns1:auditEventType&quot;/>&lt;/auditEventType>&lt;/ns2:EscribeFicheroAuditoria>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:CallProcess\n" +
"                        subProcessName=\"arch_common_audit.Auditoria_Fichero\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:empty name=\"TraceNotEnabled\" tibex:xpdlId=\"eaf56748-85f0-4dd8-b197-5ae8d4c43076\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"EmptyToTraceNotEnabled\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"Empty1ToEnd\"/>\n" +
"                </bpws:sources>\n" +
"            </bpws:empty>\n" +
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
        UnneededEmptyActivityCheck instance = new UnneededEmptyActivityCheck();
        UnneededEmptyActivityCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }

    
}
