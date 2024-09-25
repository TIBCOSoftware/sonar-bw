/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class ProcessNamingConventionCheckTest {
    
    public ProcessNamingConventionCheckTest() {
    }
   private static ProcessSource source;
 
    
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
"            targetNamespace=\"http://xmlns.tibco.com/encodings/mime\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://xmlns.tibco.com/encodings/mime\">\n" +
"            <complexType name=\"mimePartType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"mimeHeaders\" type=\"tns:mimeHeadersType\"/>\n" +
"                    <choice>\n" +
"                        <element name=\"binaryContent\" type=\"base64Binary\"/>\n" +
"                        <element name=\"textContent\" type=\"string\"/>\n" +
"                        <element name=\"fileName\" type=\"string\"/>\n" +
"                    </choice>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"mimeHeadersType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"content-disposition\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\"\n" +
"                        name=\"content-type\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"content-transfer-encoding\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"content-id\" type=\"string\"/>\n" +
"                    <any maxOccurs=\"unbounded\" minOccurs=\"0\"\n" +
"                        namespace=\"##any\" processContents=\"lax\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"mimeEnvelopeElementType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"unbounded\" minOccurs=\"0\"\n" +
"                        name=\"mimePart\" type=\"tns:mimePartType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"mimeEnvelopeElement\" type=\"tns:mimeEnvelopeElementType\"/>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"qualified\"\n" +
"            targetNamespace=\"http://xmlns.tibco.com/bw/security/tokens\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://xmlns.tibco.com/bw/security/tokens\">\n" +
"            <complexType name=\"CertificateChainType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"unbounded\" minOccurs=\"0\" ref=\"tns:Certificate\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"CertificateTokenType\">\n" +
"                <sequence>\n" +
"                    <element minOccurs=\"0\" name=\"CipherSuite\" type=\"string\"/>\n" +
"                    <element minOccurs=\"1\" ref=\"tns:CertificateChain\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"CertificateType\">\n" +
"                <sequence>\n" +
"                    <element minOccurs=\"0\" name=\"SubjectDN\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"IssuerDN\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"Fingerprint\" type=\"base64Binary\"/>\n" +
"                    <element minOccurs=\"1\" ref=\"tns:X509Certificate\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"SecurityContextType\">\n" +
"                <all>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"tns:CertificateToken\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"tns:UsernamePasswordToken\"/>\n" +
"                </all>\n" +
"            </complexType>\n" +
"            <complexType name=\"UsernamePasswordTokenType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"Username\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"Password\">\n" +
"                        <complexType>\n" +
"                            <simpleContent>\n" +
"                                <extension base=\"string\">\n" +
"                                    <attribute name=\"type\" type=\"string\"/>\n" +
"                                </extension>\n" +
"                            </simpleContent>\n" +
"                        </complexType>\n" +
"                    </element>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"X509CertificateType\">\n" +
"                <sequence>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"1\" name=\"Encoded\" type=\"base64Binary\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"Certificate\" type=\"tns:CertificateType\"/>\n" +
"            <element name=\"CertificateChain\" type=\"tns:CertificateChainType\"/>\n" +
"            <element name=\"CertificateToken\" type=\"tns:CertificateTokenType\"/>\n" +
"            <element name=\"SecurityContext\" type=\"tns:SecurityContextType\"/>\n" +
"            <element name=\"UsernamePasswordToken\" type=\"tns:UsernamePasswordTokenType\"/>\n" +
"            <element name=\"X509Certificate\" type=\"tns:X509CertificateType\"/>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/httpreceiver+b6c34be3-a042-4a26-a505-cc59867990a3+ActivityOutputType\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\"\n" +
"            xmlns:ns0=\"http://xmlns.tibco.com/encodings/mime\"\n" +
"            xmlns:ns1=\"http://xmlns.tibco.com/bw/security/tokens\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/httpreceiver+b6c34be3-a042-4a26-a505-cc59867990a3+ActivityOutputType\">\n" +
"            <!-- xmlns:ns1=\"http://xmlns.tibco.com/bw/plugin-api/messages/context\" -->\n" +
"            <import namespace=\"http://xmlns.tibco.com/encodings/mime\"/>\n" +
"            <import namespace=\"http://xmlns.tibco.com/bw/security/tokens\"/>\n" +
"            <complexType name=\"headersType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Accept\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Accept-Charset\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Accept-Encoding\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Content-Type\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Content-Length\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Connection\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Cookie\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" minOccurs=\"0\"\n" +
"                        name=\"Pragma\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"dynamicHeadersTypeDetails\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"1\" name=\"Name\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"1\" name=\"Value\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"dynamicHeadersType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"unbounded\"\n" +
"                        minOccurs=\"0\" name=\"Header\" type=\"tns:dynamicHeadersTypeDetails\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"ContextType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"1\" name=\"RemoteAddress\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"HTTPEventSourceOutputType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Method\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"RequestURI\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"HTTPVersion\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"PostData\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"QueryString\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Header\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Protocol\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"Port\" type=\"string\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" name=\"Headers\" type=\"tns:headersType\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                        name=\"DynamicHeaders\" type=\"tns:dynamicHeadersType\"/>\n" +
"                    <element minOccurs=\"0\" ref=\"ns0:mimeEnvelopeElement\"/>\n" +
"                    <element name=\"Context\" type=\"tns:ContextType\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"ProcessStarterOutput\" type=\"tns:HTTPEventSourceOutputType\"/>\n" +
"            <complexType name=\"WaitForHTTPRequestInputType\">\n" +
"                <sequence>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"key\" type=\"string\"/>\n" +
"                    <element form=\"unqualified\" maxOccurs=\"1\"\n" +
"                        minOccurs=\"0\" name=\"processTimeout\" type=\"int\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <element name=\"input\" type=\"tns:WaitForHTTPRequestInputType\"/>\n" +
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
"                                    <children type=\"4002 bw.http.HTTPReceiver\">\n" +
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
"                                    x=\"3\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"76\"/>\n" +
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
"                                    <layoutConstraint x=\"171\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"76\"/>\n" +
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
"                                    <layoutConstraint x=\"341\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"76\"/>\n" +
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
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns0:ProcessStarterOutput\"\n" +
"            name=\"HTTPReceiver\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"httpConnectorResource\"\n" +
"            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
"            sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/bw/models/sharedresource/httpconnector}HttpConnectorConfiguration\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns1:ActivityErrorData\"\n" +
"            name=\"_error_Checkpoint\" sca-bpel:internal=\"true\"/>\n" +
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
"                <bpws:link name=\"HTTPReceiverToCheckpoint\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"CheckpointToExit\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\"\n" +
"                    governedObjectTypes=\"BWHttpService\"\n" +
"                    name=\"HTTPReceiver\"\n" +
"                    tibex:xpdlId=\"b6c34be3-a042-4a26-a505-cc59867990a3\"\n" +
"                    variable=\"HTTPReceiver\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"HTTPReceiverToCheckpoint\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:eventSource>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.http.HTTPReceiver\"\n" +
"                            version=\"6.0.0.20132205\"\n" +
"                            xmlns:ActivityOutputType=\"http://www.tibco.com/namespaces/tnt/plugins/httpreceiver+b6c34be3-a042-4a26-a505-cc59867990a3+ActivityOutputType\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:http=\"http://ns.tibco.com/bw/palette/http\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/http#//HTTPReceiver\"/>\n" +
"                                    <value customId=\"\"\n" +
"                                    defaultEncoding=\"ISO8859_1\"\n" +
"                                    httpConnection=\"httpConnectorResource\"\n" +
"                                    outputHeadersQName=\"ActivityOutputType:headersType\"\n" +
"                                    outputStyle=\"String\"\n" +
"                                    sequencingKey=\"\" xsi:type=\"http:HTTPReceiver\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:extActivity name=\"Checkpoint\"\n" +
"                    tibex:xpdlId=\"ba406d85-daf3-4c47-9a8f-1b25a7232e03\"\n" +
"                    type=\"bw.internal.checkpoint\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"HTTPReceiverToCheckpoint\"/>\n" +
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
        CheckpointAfterHttpCheck instance = new CheckpointAfterHttpCheck();
        CheckpointAfterHttpCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }

}
