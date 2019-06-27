/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import org.sonar.api.utils.log.Logger;

/**
 *
 * @author avazquez
 */
public class NoOtherwiseConditionCheckTest {

    private final ProcessSource source;
    
    public NoOtherwiseConditionCheckTest() {
    
    
   
           
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\" name=\"test3.module.Process2\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20190520114221\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
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
"            targetNamespace=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"            xmlns:tns=\"http://tns.tibco.com/bw/activity/timer/xsd/output\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <xs:complexType name=\"TimerOutputSchemaType\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element form=\"unqualified\" name=\"Now\" type=\"xs:long\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Hour\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Minute\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Second\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Week\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Month\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Year\" type=\"xs:int\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Date\" type=\"xs:date\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"Time\" type=\"xs:string\"/>\n" +
"                    <xs:element form=\"unqualified\" name=\"DayOfMonth\" type=\"xs:int\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:element name=\"TimerOutputSchema\" type=\"tns:TimerOutputSchemaType\"/>\n" +
"        </xs:schema>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Mon May 20 11:42:21 CEST 2019\" description=\"\"\n" +
"        extraErrorVars=\"true\" modifiers=\"public\"\n" +
"        productVersion=\"6.5.1 V9 2019-02-06\" scalable=\"true\"\n" +
"        singleton=\"true\" stateless=\"false\" type=\"IT\"/>\n" +
"    <tibex:ProcessInterface context=\"\" input=\"\" output=\"\"/>\n" +
"    <tibex:ProcessTemplateConfigurations/>\n" +
"    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n" +
"        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n" +
"        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"        <children type=\"2001\">\n" +
"            <children type=\"5001\"/>\n" +
"            <children type=\"3001\">\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
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
"                                    <children type=\"4002 bw.generalactivities.timer\">\n" +
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
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"-28\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"109\"/>\n" +
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
"                                    <layoutConstraint x=\"118\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"24\"/>\n" +
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
"                                    <layoutConstraint x=\"132\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"170\"/>\n" +
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
"                                    <layoutConstraint x=\"268\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"98\"/>\n" +
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
"                                    width=\"515\" xsi:type=\"notation:Bounds\"/>\n" +
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
"            <layoutConstraint height=\"460\" width=\"860\" xsi:type=\"notation:Bounds\"/>\n" +
"        </children>\n" +
"        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
"        <element href=\"//0\"/>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.2\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.3\"/>\n" +
"            <bendpoints points=\"[25, 12, -125, -62]$[126, 62, -24, -12]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns0:TimerOutputSchema\" name=\"Timer\" sca-bpel:internal=\"true\"/>\n" +
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
"                <bpws:link name=\"TimerToEmpty\" tibex:linkType=\"SUCCESSWITHCONDITION\"/>\n" +
"                <bpws:link name=\"TimerToEmpty1\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"Empty1ToEmpty2\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"EmptyToEmpty2\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\" name=\"Timer\"\n" +
"                    tibex:xpdlId=\"9c7ed99f-76c5-4430-bd58-4ce032f72457\"\n" +
"                    variable=\"Timer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"TimerToEmpty\">\n" +
"                            <tibex:DesignExpression>\n" +
"                                <tibex:expression expression=\"1 = 1 \" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"/>\n" +
"                            </tibex:DesignExpression>\n" +
"                            <bpws:transitionCondition expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xpath2.0\"><![CDATA[1 = 1 ]]></bpws:transitionCondition>\n" +
"                        </bpws:source>\n" +
"                        <bpws:source linkName=\"TimerToEmpty1\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:eventSource>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.generalactivities.timer\"\n" +
"                            version=\"6.0.0.001\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Timer\"/>\n" +
"                                    <value\n" +
"                                    endTime=\"2019-05-20T11:42:30.480+0200\"\n" +
"                                    intervalUnit=\"Second\"\n" +
"                                    runOnce=\"true\"\n" +
"                                    startTime=\"2019-05-20T11:42:30.480+0200\"\n" +
"                                    timeInterval=\"1\" xsi:type=\"generalactivities:Timer\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:empty name=\"Empty\" tibex:xpdlId=\"d6b0931a-2588-4b83-9408-9727417327ba\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"TimerToEmpty\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"EmptyToEmpty2\"/>\n" +
"                </bpws:sources>\n" +
"            </bpws:empty>\n" +
"            <bpws:empty name=\"Empty1\" tibex:xpdlId=\"cefebe57-8ae9-48bd-b94c-0e02d46330c4\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"TimerToEmpty1\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"Empty1ToEmpty2\"/>\n" +
"                </bpws:sources>\n" +
"            </bpws:empty>\n" +
"            <bpws:empty name=\"Empty2\" tibex:xpdlId=\"d4e7b425-0b5d-493c-9d8c-a03cb15790de\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"Empty1ToEmpty2\"/>\n" +
"                    <bpws:target linkName=\"EmptyToEmpty2\"/>\n" +
"                </bpws:targets>\n" +
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
        NoOtherwiseConditionCheck instance = new NoOtherwiseConditionCheck();
        NoOtherwiseConditionCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(), anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }
    
}
