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
import org.mockito.Matchers;
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
public class RenderXMLPrettyPrintCheckTest {
    
    public RenderXMLPrettyPrintCheckTest() {
    }
     private static ProcessSource source;
    

    
     
    @BeforeClass
    public static void setUpClass() {
       
        
                
                
        String processSource = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\" name=\"t1.module.Process\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20190703214606\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"    xmlns:ns1=\"http://www.tibco.com/namespaces/tnt/plugins/parsexml\"\n" +
"    xmlns:ns2=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.xml.parsexml\"\n" +
"    xmlns:ns3=\"http://www.tibco.com/namespaces/tnt/plugins/renderxml\"\n" +
"    xmlns:ns4=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.xml.renderxml\"\n" +
"    xmlns:ns5=\"http://tns.tibco.com/bw/activity/put/input+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutInputDataFile\"\n" +
"    xmlns:ns6=\"http://tns.tibco.com/bw/activity/output+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutOutputFile\"\n" +
"    xmlns:ns7=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.sftp.put\"\n" +
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
"        <schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/activity/put/input+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutInputDataFile\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:Q1=\"http://tns.tibco.com/bw/activity/put/input+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutInputDataFile\">\n" +
"            <element name=\"SFTPPutInputDataFile\" type=\"Q1:SFTPPutInputType\"/>\n" +
"            <complexType name=\"SFTPPutInputType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"1\" name=\"RemoteFileName\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"1\" name=\"LocalFileName\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Host\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Port\" type=\"int\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"UserName\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Password\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"PrivKey\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"PrivKeyPass\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"ProxyHost\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"ProxyPort\" type=\"int\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"ProxyUserName\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"ProxyPassword\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Timeout\" type=\"int\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/activity/output+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutOutputFile\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:Q1=\"http://tns.tibco.com/bw/activity/output+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutOutputFile\">\n" +
"            <complexType name=\"SFTPTransferFile\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Name\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"NumOfBytes\" type=\"long\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"SFTPPutOutputFile\" type=\"Q1:SFTPPutOutputClassLocal50\"/>\n" +
"            <complexType name=\"SFTPPutOutputClassLocal50\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"unbounded\"\n" +
"                        minOccurs=\"0\" name=\"FileTransferred\" type=\"Q1:SFTPTransferFile\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <schema attributeFormDefault=\"unqualified\"\n" +
"            elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/palette/sp/exception\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://tns.tibco.com/bw/palette/sp/exception\">\n" +
"            <complexType name=\"CommandExecutionExceptionType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" name=\"msg\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" name=\"msgCode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"ConnectionErrorExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:CommandExecutionExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <complexType name=\"ConnectionTimeoutExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:CommandExecutionExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <complexType name=\"GetFilesExceptionType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"unbounded\"\n" +
"                        minOccurs=\"0\" name=\"SuccessfulFiles\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"unbounded\"\n" +
"                        minOccurs=\"0\" name=\"FailedFiles\" type=\"tns:FAIL_TYPE\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"PutFilesExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns:GetFilesExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <complexType name=\"FAIL_TYPE\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" name=\"Filename\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" name=\"Error\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"GetFilesException\" type=\"tns:GetFilesExceptionType\"/>\n" +
"            <element name=\"ConnectionTimeoutException\" type=\"tns:ConnectionTimeoutExceptionType\"/>\n" +
"            <element name=\"CommandExecutionException\" type=\"tns:CommandExecutionExceptionType\"/>\n" +
"            <element name=\"ConnectionErrorException\" type=\"tns:ConnectionErrorExceptionType\"/>\n" +
"            <element name=\"PutFilesException\" type=\"tns:PutFilesExceptionType\"/>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.sftp.put\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\"\n" +
"            xmlns:Q1=\"http://tns.tibco.com/bw/palette/sp/exception\" xmlns:tns=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.sftp.put\">\n" +
"            <import namespace=\"http://tns.tibco.com/bw/palette/sp/exception\"/>\n" +
"            <element name=\"ActivityErrorData\" type=\"tns:ActivityErrorDataType\"/>\n" +
"            <complexType name=\"ActivityErrorDataType\">\n" +
"                <choice>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:PutFilesException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:ConnectionErrorException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:CommandExecutionException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:ConnectionTimeoutException\"/>\n" +
"                </choice>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <wsdl:definitions\n" +
"            targetNamespace=\"http://xmlns.example.com/20190703214606\"\n" +
"            xmlns:tns=\"http://xmlns.example.com/20190703214606\"\n" +
"            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
"            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"/>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Wed Jul 03 21:46:06 CEST 2019\" description=\"\"\n" +
"        extraErrorVars=\"true\" modifiers=\"public\"\n" +
"        productVersion=\"2.4.4 V39 2019-06-19\" scalable=\"true\"\n" +
"        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n" +
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
"                                    x=\"14\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"93\"/>\n" +
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
"                                    <layoutConstraint x=\"274\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"94\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4002 bw.sftp.put\">\n" +
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
"                                    x=\"154\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"94\"/>\n" +
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
"                                    width=\"532\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                            </children>\n" +
"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                            <element href=\"//0/@process/@activity\"/>\n" +
"                            <layoutConstraint height=\"384\" width=\"535\" xsi:type=\"notation:Bounds\"/>\n" +
"                        </children>\n" +
"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                        <element href=\"//0/@process/@activity\"/>\n" +
"                    </children>\n" +
"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                    <element href=\"//0/@process/@activity\"/>\n" +
"                    <layoutConstraint height=\"408\" width=\"535\" x=\"1\"\n" +
"                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n" +
"                </children>\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"            <element href=\"//0/@process\"/>\n" +
"            <layoutConstraint height=\"460\" width=\"877\" xsi:type=\"notation:Bounds\"/>\n" +
"        </children>\n" +
"        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
"        <element href=\"//0\"/>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
"            <bendpoints points=\"[25, 1, -115, 0]$[116, 0, -24, -1]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[25, -1, -136, 0]$[137, 0, -24, 1]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <tibex:NamespaceRegistry enabled=\"true\">\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.tibco.com/namespaces/tnt/plugins/parsexml\" prefix=\"tns\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://tns.tibco.com/bw/activity/put/input+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutInputDataFile\" prefix=\"tns1\"/>\n" +
"        <tibex:namespaceItem\n" +
"            namespace=\"http://www.tibco.com/bw/xslt/custom-functions\" prefix=\"tib\"/>\n" +
"    </tibex:NamespaceRegistry>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns0:TimerOutputSchema\" name=\"Timer\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"SFTPConnectionProperty\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://ns.tibco.com/bw/palette/sftpconnection}sftpconnection\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns5:SFTPPutInputDataFile\"\n" +
"            name=\"SFTPPut-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns6:SFTPPutOutputFile\" name=\"SFTPPut\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns7:ActivityErrorData\"\n" +
"            name=\"_error_SFTPPut\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns:ErrorReport\" name=\"_error\" sca-bpel:internal=\"true\"/>\n" +
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
"                <bpws:link name=\"TimerToSFTPPut\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"SFTPPutToExit\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\" name=\"Timer\"\n" +
"                    tibex:xpdlId=\"35314ccd-641b-42d0-a150-d00ab8fcb944\"\n" +
"                    variable=\"Timer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"TimerToSFTPPut\"/>\n" +
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
"                                    endTime=\"2019-07-03T21:46:33.309+0200\"\n" +
"                                    intervalUnit=\"Second\"\n" +
"                                    runOnce=\"true\"\n" +
"                                    startTime=\"2019-07-03T21:46:33.309+0200\"\n" +
"                                    timeInterval=\"1\" xsi:type=\"generalactivities:Timer\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:exit name=\"Exit\" tibex:xpdlId=\"8b5a1294-f673-445a-bfb8-c4af5d9273ff\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"SFTPPutToExit\"/>\n" +
"                </bpws:targets>\n" +
"            </bpws:exit>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension inputVariable=\"SFTPPut-input\"\n" +
"                    name=\"SFTPPut\" outputVariable=\"SFTPPut\"\n" +
"                    tibex:xpdlId=\"6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"TimerToSFTPPut\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"SFTPPutToExit\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns1=&quot;http://tns.tibco.com/bw/activity/put/input+6dff08b2-85d6-49fd-a5b8-5e21cac0d2a0+SFTPPutInputDataFile&quot; xmlns:tib=&quot;http://www.tibco.com/bw/xslt/custom-functions&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;Timer&quot;/>&lt;xsl:template name=&quot;SFTPPut-input&quot; match=&quot;/&quot;>&lt;tns1:SFTPPutInputDataFile>&lt;RemoteFileName>&lt;xsl:value-of select=&quot;tib:render-xml($Timer/Time,false(),true())&quot;/>&lt;/RemoteFileName>&lt;/tns1:SFTPPutInputDataFile>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:config>\n" +
"                        <bwext:BWActivity activityTypeID=\"bw.sftp.put\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:sftpPalette=\"http://ns.tibco.com/bw/palette/sp\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/sp#//SFTPPut\"/>\n" +
"                                    <value overwriteExistingFile=\"true\"\n" +
"                                    sftpConnection=\"SFTPConnectionProperty\"\n" +
"                                    timeout=\"120000\" xsi:type=\"sftpPalette:SFTPPut\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
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
        RenderXMLPrettyPrintCheck instance = new RenderXMLPrettyPrintCheck();
        RenderXMLPrettyPrintCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,atLeastOnce()).reportIssueOnFile(anyString(),anyInt());
        
    }

 
}
