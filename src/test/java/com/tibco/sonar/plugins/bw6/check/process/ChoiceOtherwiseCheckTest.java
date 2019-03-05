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
public class ChoiceOtherwiseCheckTest {
    
    private static ProcessSource source;
    public ChoiceOtherwiseCheckTest() {
   
    }
    @BeforeClass
    public static void setUpClass() {
       
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\" name=\"t2.module.Process\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20190302225941\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://www.tibco.com/namespaces/tnt/plugins/httpreceiver+b6c34be3-a042-4a26-a505-cc59867990a3+ActivityOutputType\"\n" +
"    xmlns:ns1=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.internal.checkpoint\"\n" +
"    xmlns:ns2=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"    xmlns:ns3=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+53084000-a004-4fe2-92e8-6c352bdb024c+input\"\n" +
"    xmlns:ns4=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+53084000-a004-4fe2-92e8-6c352bdb024c+output\"\n" +
"    xmlns:ns5=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.jdbc.JDBCQuery\"\n" +
"    xmlns:ns6=\"http://www.tibco.com/namespaces/tnt/plugins/restinvoke+197af7a1-9ffe-455f-99e1-e435a781caa2+RestInvokeInput\"\n" +
"    xmlns:ns7=\"http://www.tibco.com/namespaces/tnt/plugins/restinvoke+197af7a1-9ffe-455f-99e1-e435a781caa2+RestInvokeOutput\"\n" +
"    xmlns:ns8=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.restjson.Rest\"\n" +
"    xmlns:ns9=\"http://tns.tibco.com/bw/activity/sleep/xsd\"\n" +
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
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.internal.checkpoint\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\"\n" +
"            xmlns:Q1=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\" xmlns:tns=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.internal.checkpoint\">\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"/>\n" +
"            <element name=\"ActivityErrorData\" type=\"tns:ActivityErrorDataType\"/>\n" +
"            <complexType name=\"ActivityErrorDataType\">\n" +
"                <choice>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:DuplicateKeyException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:ActivityTimedOutException\"/>\n" +
"                </choice>\n" +
"            </complexType>\n" +
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
"        <xs:schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/activity/sleep/xsd\"\n" +
"            xmlns:tns=\"http://tns.tibco.com/bw/activity/sleep/xsd\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <xs:complexType name=\"SleepInputSchema\">\n" +
"                <xs:sequence>\n" +
"                    <xs:element form=\"unqualified\"\n" +
"                        name=\"IntervalInMillisec\" type=\"xs:integer\"/>\n" +
"                </xs:sequence>\n" +
"            </xs:complexType>\n" +
"            <xs:element name=\"SleepInputSchema\" type=\"tns:SleepInputSchema\"/>\n" +
"        </xs:schema>\n" +
"        <wsdl:definitions\n" +
"            targetNamespace=\"http://xmlns.example.com/20190302225941\"\n" +
"            xmlns:tns=\"http://xmlns.example.com/20190302225941\"\n" +
"            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
"            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"/>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Sat Mar 02 22:59:41 CET 2019\" description=\"\"\n" +
"        extraErrorVars=\"true\" modifiers=\"public\"\n" +
"        productVersion=\"6.5.0 HF4 V99 2018-11-21\" scalable=\"true\"\n" +
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
"                                    <layoutConstraint x=\"349\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"142\"/>\n" +
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
"                                    <layoutConstraint x=\"349\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"36\"/>\n" +
"                                    </children>\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\" width=\"40\"\n" +
"                                    x=\"-37\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"81\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4018\">\n" +
"                                    <children type=\"3018\">\n" +
"                                    <children type=\"4020\">\n" +
"                                    <children type=\"3020\">\n" +
"                                    <children type=\"4005\">\n" +
"                                    <children type=\"3007\">\n" +
"\n" +
"                                    <children type=\"4002 bw.generalactivities.sleep\">\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.2\"/>\n" +
"\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"40\"\n" +
"                                    width=\"40\"\n" +
"                                    x=\"19\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"31\"/>\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity\"/>\n" +
"\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"108\"\n" +
"                                    width=\"400\"\n" +
"                                    x=\"2\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n" +
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
"                                    height=\"320\"\n" +
"                                    width=\"420\"\n" +
"                                    x=\"30\"\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.0\"/>\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.3\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"165\" width=\"213\"\n" +
"                                    x=\"98\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"74\"/>\n" +
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
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.2\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.1\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@links/@children.0\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.3/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <tibex:NamespaceRegistry enabled=\"true\">\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://tns.tibco.com/bw/activity/sleep/xsd\" prefix=\"tns\"/>\n" +
"    </tibex:NamespaceRegistry>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"httpConnectorResource\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/bw/models/sharedresource/httpconnector}HttpConnectorConfiguration\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns1:ActivityErrorData\"\n" +
"            name=\"_error_Checkpoint\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns:ErrorReport\" name=\"_error\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns2:TimerOutputSchema\" name=\"Timer\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"jdbcProperty\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc}JdbcDataSource\" type=\"xsd:string\"/>\n" +
"        <bpws:variable name=\"httpClientResource\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/bw/models/sharedresource/httpclient}HttpClientConfiguration\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns9:SleepInputSchema\" name=\"Sleep-input\" sca-bpel:internal=\"true\"/>\n" +
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
"                <bpws:link name=\"CheckpointToExit\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"TimerToCriticalSection\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"CriticalSectionToCheckpoint\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity name=\"Checkpoint\"\n" +
"                    tibex:xpdlId=\"ba406d85-daf3-4c47-9a8f-1b25a7232e03\"\n" +
"                    type=\"bw.internal.checkpoint\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"CriticalSectionToCheckpoint\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"CheckpointToExit\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:checkpoint/>\n" +
"                </tibex:extActivity>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:exit name=\"Exit\" tibex:xpdlId=\"109d8a82-d856-4b8b-9cc3-449547bc182d\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"CheckpointToExit\"/>\n" +
"                </bpws:targets>\n" +
"            </bpws:exit>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\" name=\"Timer\"\n" +
"                    tibex:xpdlId=\"d60a7293-9a64-49d6-977e-bb3cbfc78ab6\"\n" +
"                    variable=\"Timer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"TimerToCriticalSection\"/>\n" +
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
"                                    endTime=\"2019-03-02T23:01:41.409+0100\"\n" +
"                                    intervalUnit=\"Second\"\n" +
"                                    runOnce=\"true\"\n" +
"                                    startTime=\"2019-03-02T23:01:41.409+0100\"\n" +
"                                    timeInterval=\"1\" xsi:type=\"generalactivities:Timer\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:scope name=\"CriticalSection\" tibex:group=\"critical\" tibex:xpdlId=\"6c7ed04f-a86a-4875-8443-8db36469cb80\">\n" +
"                <tibex:criticalSection sharedLock=\"false\"/>\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"TimerToCriticalSection\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"CriticalSectionToCheckpoint\"/>\n" +
"                </bpws:sources>\n" +
"                <bpws:flow name=\"flow1\">\n" +
"                    <bpws:links>\n" +
"                        <bpws:link name=\"GroupStartToSleep\" tibex:linkType=\"SUCCESS\"/>\n" +
"                        <bpws:link name=\"SleepToGroupEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                    </bpws:links>\n" +
"                    <bpws:empty name=\"GroupStart\"\n" +
"                        tibex:group=\"groupStart\" tibex:xpdlId=\"13d57736-7ef8-4b1e-acab-a93a8cc3a943\">\n" +
"                        <bpws:sources>\n" +
"                            <bpws:source linkName=\"GroupStartToSleep\"/>\n" +
"                        </bpws:sources>\n" +
"                    </bpws:empty>\n" +
"                    <bpws:empty name=\"GroupEnd\" tibex:group=\"groupEnd\" tibex:xpdlId=\"94ef1193-e2be-4cfc-b017-53135955c10d\">\n" +
"                        <bpws:targets>\n" +
"                            <bpws:target linkName=\"SleepToGroupEnd\"/>\n" +
"                        </bpws:targets>\n" +
"                    </bpws:empty>\n" +
"                    <bpws:extensionActivity>\n" +
"                        <tibex:activityExtension\n" +
"                            inputVariable=\"Sleep-input\" name=\"Sleep\"\n" +
"                            tibex:xpdlId=\"cd91ed72-8e89-43b6-9e0f-b44adac37924\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                            <bpws:targets>\n" +
"                                <bpws:target linkName=\"GroupStartToSleep\"/>\n" +
"                            </bpws:targets>\n" +
"                            <bpws:sources>\n" +
"                                <bpws:source linkName=\"SleepToGroupEnd\"/>\n" +
"                            </bpws:sources>\n" +
"                            <tibex:inputBindings>\n" +
"                                <tibex:inputBinding\n" +
"                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://tns.tibco.com/bw/activity/sleep/xsd&quot; version=&quot;2.0&quot;>&lt;xsl:template name=&quot;Sleep-input&quot; match=&quot;/&quot;>&lt;tns:SleepInputSchema>&lt;xsl:choose>&lt;xsl:when test=&quot;&quot;>&lt;IntervalInMillisec/>&lt;/xsl:when>&lt;/xsl:choose>&lt;/tns:SleepInputSchema>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                            </tibex:inputBindings>\n" +
"                            <tibex:config>\n" +
"                                <bwext:BWActivity\n" +
"                                    activityTypeID=\"bw.generalactivities.sleep\"\n" +
"                                    xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                                    xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                                    xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                                    <activityConfig>\n" +
"                                    <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Sleep\"/>\n" +
"                                    <value xsi:type=\"generalactivities:Sleep\"/>\n" +
"                                    </properties>\n" +
"                                    </activityConfig>\n" +
"                                </bwext:BWActivity>\n" +
"                            </tibex:config>\n" +
"                        </tibex:activityExtension>\n" +
"                    </bpws:extensionActivity>\n" +
"                </bpws:flow>\n" +
"            </bpws:scope>\n" +
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
        ChoiceOtherwiseCheck instance = new ChoiceOtherwiseCheck();
        ChoiceOtherwiseCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString());
        
    }
    
}
