/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.check.process;

import com.tibco.sonar.plugins.bw6.source.ProcessSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
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
public class JMSAcknowledgementModeCheckTest {

    private static ProcessSource source;
    
    public JMSAcknowledgementModeCheckTest() {
    }
      
    @BeforeClass
    public static void setUpClass() {

        source = new ProcessSource(new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<bpws:process exitOnStandardFault=\"no\"\n" +
                "    name=\"com.tibco.bw.service.SISEG201.internal.virtual\"\n" +
                "    suppressJoinFailure=\"yes\"\n" +
                "    targetNamespace=\"http://xmlns.example.com/20171024142226\"\n" +
                "    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
                "    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
                "    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
                "    xmlns:ns0=\"http://xmlns.example.com/20171020155648PLT\"\n" +
                "    xmlns:ns1=\"http://xmlns.endesa.com/wsdl/XX_AMBITO_XX/XX_NOMBRE_REQUERIMIENTO_XX/TTXXXZZZ/\"\n" +
                "    xmlns:ns10=\"http://www.tibco.com/namespaces/tnt/plugins/jms+ae723f68-40a0-4791-af30-3b32fec57f55+output\"\n" +
                "    xmlns:ns11=\"http://www.tibco.com/namespaces/tnt/plugins/jms+78894712-47e4-4e22-a426-59efe9145c41+input\"\n" +
                "    xmlns:ns12=\"http://www.tibco.com/namespaces/tnt/plugins/jms\"\n" +
                "    xmlns:ns13=\"http://www.tibco.com/namespaces/tnt/plugins/jms+27cdfca6-28b8-4e07-8fbe-5ef62e53fd4d+output\"\n" +
                "    xmlns:ns14=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+222bc454-c407-4ff3-b0f4-85451655fac6+input\"\n" +
                "    xmlns:ns15=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+222bc454-c407-4ff3-b0f4-85451655fac6+output\"\n" +
                "    xmlns:ns2=\"http://www.example.org/wsdl/1508569656669\"\n" +
                "    xmlns:ns3=\"http://www.tibco.com/namespaces/tnt/plugins/jms+a00e1d72-6814-4339-8ed7-ec3afe775934+output\"\n" +
                "    xmlns:ns4=\"http://www.tibco.com/namespaces/tnt/plugins/jms+745dab48-9d4e-4af9-9942-6d3af7480313+input\"\n" +
                "    xmlns:ns5=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"\n" +
                "    xmlns:ns6=\"http://tns.tibco.com/bw/model/addressing\"\n" +
                "    xmlns:ns7=\"http://www.tibco.com/namespaces/tnt/plugins/jms+input\"\n" +
                "    xmlns:ns8=\"http://xmlns.tibco.com/psg/CommonSchemas\"\n" +
                "    xmlns:ns9=\"http://www.tibco.com/namespaces/tnt/plugins/jms+output\"\n" +
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
                "        <schema elementFormDefault=\"qualified\"\n" +
                "            targetNamespace=\"http://tns.tibco.com/bw/model/addressing\"\n" +
                "            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://tns.tibco.com/bw/model/addressing\">\n" +
                "            <complexType name=\"Address\">\n" +
                "                <simpleContent>\n" +
                "                    <extension base=\"string\"/>\n" +
                "                </simpleContent>\n" +
                "            </complexType>\n" +
                "            <element name=\"Address\" type=\"tns:Address\"/>\n" +
                "            <complexType name=\"SimpleEndpointReference\">\n" +
                "                <sequence>\n" +
                "                    <element ref=\"tns:Address\"/>\n" +
                "                </sequence>\n" +
                "            </complexType>\n" +
                "            <element name=\"SimpleEndpointReference\" type=\"tns:SimpleEndpointReference\"/>\n" +
                "            <complexType name=\"EndpointReference\">\n" +
                "                <complexContent>\n" +
                "                    <extension base=\"tns:SimpleEndpointReference\">\n" +
                "                        <sequence>\n" +
                "                            <element minOccurs=\"0\" name=\"Interface\" type=\"string\"/>\n" +
                "                        </sequence>\n" +
                "                    </extension>\n" +
                "                </complexContent>\n" +
                "            </complexType>\n" +
                "            <element name=\"EndpointReference\" type=\"tns:EndpointReference\"/>\n" +
                "        </schema>\n" +
                "        <xsd:schema attributeFormDefault=\"unqualified\"\n" +
                "            elementFormDefault=\"qualified\"\n" +
                "            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jms+output\"\n" +
                "            version=\"0.1\"\n" +
                "            xmlns=\"http://www.tibco.com/namespaces/tnt/plugins/jms+output\"\n" +
                "            xmlns:Q1=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "            <xsd:import namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n" +
                "            <xsd:complexType name=\"ActivityOutput\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element name=\"MessageID\" type=\"xsd:string\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <!-- awagle addition -->\n" +
                "            <xsd:complexType name=\"JMSTransmitInputSchemaType\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"destination\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"replyTo\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSExpiration\" type=\"xsd:int\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSPriority\" type=\"xsd:int\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSDeliveryMode\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSType\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSProperties\" type=\"JmsTransmitProperties\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"1\"\n" +
                "                        name=\"Body\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsTransmitProperties\">\n" +
                "                <xsd:sequence>\n" +
                "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n" +
                "                    <!-- tibco specific\n" +
                "      <xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      <xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      <! these two are TNT-specific, supporting the XML decoding\n" +
                "      <xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      <xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      -->\n" +
                "                    <!-- all other properties, vendor or application defined, in a standard format -->\n" +
                "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsHeaders\">\n" +
                "                <xsd:sequence>\n" +
                "                    <!-- NOTE: a destination is actually an object.  We are able to treat is as a string\n" +
                "           because the other items that define a destination (topic vs queue, server/connection)\n" +
                "           are already defined in the config element.  We do the same for the ReplyTo -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSDestination\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSReplyTo\" type=\"xsd:string\"/>\n" +
                "                    <!-- PROBLEM: this is actually an int, but we're representing it here as a string enumeration,\n" +
                "  meaning that the app must then translate from the constants in JMS to the 'constants' in the enumeration -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSDeliveryMode\" type=\"JmsDeliveryMode\"/>\n" +
                "                    <!-- actually an int ... -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSMessageID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSTimestamp\" type=\"xsd:long\"/>\n" +
                "                    <!-- Java timestamp, millis from era -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSExpiration\" type=\"xsd:long\"/>\n" +
                "                    <!-- Java timestamp, millis from era -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSRedelivered\" type=\"xsd:boolean\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSPriority\" type=\"JmsPriority\"/>\n" +
                "                    <!-- PROBLEM: correlation id can also be retrieved as a stream of bytes -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSType\" type=\"xsd:string\"/>\n" +
                "                    <!-- <xsd:element name=\"EventSource\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsProperties\">\n" +
                "                <xsd:sequence>\n" +
                "                    <!-- most of these are not really accessible, easily, but appear on incoming messages -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXUserID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXAppID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXProducerTXID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXConsumerTXID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXRcvTimestamp\" type=\"xsd:long\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXDeliveryCount\" type=\"xsd:int\"/>\n" +
                "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n" +
                "                    <!-- tibco specific -->\n" +
                "                    <!--<xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!--<xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!-- these two are TNT-specific, supporting the XML decoding -->\n" +
                "                    <!--<xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!--<xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!-- all other properties, vendor or application defined, in a standard format -->\n" +
                "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsDynamicProperties\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element maxOccurs=\"unbounded\" minOccurs=\"0\"\n" +
                "                        name=\"property\" type=\"JmsDynamicProperty\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsDynamicProperty\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\" name=\"name\"\n" +
                "                        nillable=\"true\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n" +
                "                        name=\"value\" nillable=\"true\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"type\"\n" +
                "                        nillable=\"true\" type=\"xsd:string\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsMessage\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n" +
                "                        name=\"MessageHeaders\" type=\"JmsHeaders\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"MessageProperties\" type=\"JmsProperties\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JMSReceiveSchema\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element form=\"unqualified\" name=\"JMSHeaders\" type=\"JmsHeaders\"/>\n" +
                "                    <xsd:element form=\"unqualified\" name=\"JMSProperties\" type=\"JmsProperties\"/>\n" +
                "                    <!-- <xsd:element name=\"OtherProperties\" form=\"unqualified\" type=\"tns:OtherProperties\" minOccurs=\"0\"/>-->\n" +
                "                    <xsd:element form=\"unqualified\" name=\"Body\">\n" +
                "                        <xsd:complexType>\n" +
                "                            <xsd:sequence>\n" +
                "                                <xsd:element ref=\"Q1:SOAInput\"/>\n" +
                "                            </xsd:sequence>\n" +
                "                        </xsd:complexType>\n" +
                "                    </xsd:element>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:element name=\"ActivityOutput\" type=\"JMSReceiveSchema\"/>\n" +
                "            <!-- enumerations for various constants -->\n" +
                "            <xsd:simpleType name=\"JmsDeliveryMode\">\n" +
                "                <xsd:restriction base=\"xsd:string\">\n" +
                "                    <xsd:enumeration value=\"PERSISTENT\"/>\n" +
                "                    <xsd:enumeration value=\"NON_PERSISTENT\"/>\n" +
                "                    <xsd:enumeration value=\"RELIABLE_DELIVERY\"/>\n" +
                "                    <!-- Tibco Enterprise for JMS defined (TibjmsxConst) -->\n" +
                "                    <!-- <xsd:enumeration value=\"NO_ACKNOWLEDGE\" /> -->\n" +
                "                </xsd:restriction>\n" +
                "            </xsd:simpleType>\n" +
                "            <xsd:simpleType name=\"JmsPriority\">\n" +
                "                <xsd:restriction base=\"xsd:int\">\n" +
                "                    <xsd:minInclusive value=\"0\"/>\n" +
                "                    <xsd:maxInclusive value=\"9\"/>\n" +
                "                </xsd:restriction>\n" +
                "            </xsd:simpleType>\n" +
                "            <xsd:element name=\"aEmptyOutputClass\" type=\"ActivityOutput\"/>\n" +
                "            <xsd:element name=\"ActivityInput\" type=\"JMSTransmitInputSchemaType\"/>\n" +
                "            <xsd:complexType name=\"JMSQueueGetMessageInputSchema\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"destinationQueue\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"timeout\" type=\"xsd:long\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"selector\" type=\"xsd:string\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:element name=\"GetJMSActivityInput\" type=\"JMSQueueGetMessageInputSchema\"/>\n" +
                "        </xsd:schema>\n" +
                "        <xsd:schema attributeFormDefault=\"unqualified\"\n" +
                "            elementFormDefault=\"qualified\"\n" +
                "            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jms+input\"\n" +
                "            version=\"0.1\"\n" +
                "            xmlns=\"http://www.tibco.com/namespaces/tnt/plugins/jms+input\"\n" +
                "            xmlns:Q1=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "            <xsd:import namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n" +
                "            <xsd:complexType name=\"ActivityOutput\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element name=\"MessageID\" type=\"xsd:string\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <!-- awagle addition -->\n" +
                "            <xsd:complexType name=\"JMSTransmitInputSchemaType\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"destination\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"replyTo\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSExpiration\" type=\"xsd:int\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSPriority\" type=\"xsd:int\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSDeliveryMode\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSType\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"JMSProperties\" type=\"JmsTransmitProperties\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"1\" name=\"Body\">\n" +
                "                        <xsd:complexType>\n" +
                "                            <xsd:sequence>\n" +
                "                                <xsd:element ref=\"Q1:SOAOutput\"/>\n" +
                "                            </xsd:sequence>\n" +
                "                        </xsd:complexType>\n" +
                "                    </xsd:element>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsTransmitProperties\">\n" +
                "                <xsd:sequence>\n" +
                "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n" +
                "                    <!-- tibco specific\n" +
                "      <xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      <xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      <! these two are TNT-specific, supporting the XML decoding\n" +
                "      <xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      <xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n" +
                "      -->\n" +
                "                    <!-- all other properties, vendor or application defined, in a standard format -->\n" +
                "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsHeaders\">\n" +
                "                <xsd:sequence>\n" +
                "                    <!-- NOTE: a destination is actually an object.  We are able to treat is as a string\n" +
                "           because the other items that define a destination (topic vs queue, server/connection)\n" +
                "           are already defined in the config element.  We do the same for the ReplyTo -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSDestination\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSReplyTo\" type=\"xsd:string\"/>\n" +
                "                    <!-- PROBLEM: this is actually an int, but we're representing it here as a string enumeration,\n" +
                "  meaning that the app must then translate from the constants in JMS to the 'constants' in the enumeration -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSDeliveryMode\" type=\"JmsDeliveryMode\"/>\n" +
                "                    <!-- actually an int ... -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSMessageID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSTimestamp\" type=\"xsd:long\"/>\n" +
                "                    <!-- Java timestamp, millis from era -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSExpiration\" type=\"xsd:long\"/>\n" +
                "                    <!-- Java timestamp, millis from era -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSRedelivered\" type=\"xsd:boolean\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSPriority\" type=\"JmsPriority\"/>\n" +
                "                    <!-- PROBLEM: correlation id can also be retrieved as a stream of bytes -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSType\" type=\"xsd:string\"/>\n" +
                "                    <!-- <xsd:element name=\"EventSource\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsProperties\">\n" +
                "                <xsd:sequence>\n" +
                "                    <!-- most of these are not really accessible, easily, but appear on incoming messages -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXUserID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXAppID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXProducerTXID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXConsumerTXID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXRcvTimestamp\" type=\"xsd:long\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXDeliveryCount\" type=\"xsd:int\"/>\n" +
                "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n" +
                "                    <!-- tibco specific -->\n" +
                "                    <!--<xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!--<xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!-- these two are TNT-specific, supporting the XML decoding -->\n" +
                "                    <!--<xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!--<xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n" +
                "                    <!-- all other properties, vendor or application defined, in a standard format -->\n" +
                "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsDynamicProperties\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element maxOccurs=\"unbounded\" minOccurs=\"0\"\n" +
                "                        name=\"property\" type=\"JmsDynamicProperty\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsDynamicProperty\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\" name=\"name\"\n" +
                "                        nillable=\"true\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n" +
                "                        name=\"value\" nillable=\"true\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"type\"\n" +
                "                        nillable=\"true\" type=\"xsd:string\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JmsMessage\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n" +
                "                        name=\"MessageHeaders\" type=\"JmsHeaders\"/>\n" +
                "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n" +
                "                        name=\"MessageProperties\" type=\"JmsProperties\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:complexType name=\"JMSReceiveSchema\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element form=\"unqualified\" name=\"JMSHeaders\" type=\"JmsHeaders\"/>\n" +
                "                    <xsd:element form=\"unqualified\" name=\"JMSProperties\" type=\"JmsProperties\"/>\n" +
                "                    <!-- <xsd:element name=\"OtherProperties\" form=\"unqualified\" type=\"tns:OtherProperties\" minOccurs=\"0\"/>-->\n" +
                "                    <xsd:element form=\"unqualified\" name=\"Body\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:element name=\"ActivityOutput\" type=\"JMSReceiveSchema\"/>\n" +
                "            <!-- enumerations for various constants -->\n" +
                "            <xsd:simpleType name=\"JmsDeliveryMode\">\n" +
                "                <xsd:restriction base=\"xsd:string\">\n" +
                "                    <xsd:enumeration value=\"PERSISTENT\"/>\n" +
                "                    <xsd:enumeration value=\"NON_PERSISTENT\"/>\n" +
                "                    <xsd:enumeration value=\"RELIABLE_DELIVERY\"/>\n" +
                "                    <!-- Tibco Enterprise for JMS defined (TibjmsxConst) -->\n" +
                "                    <!-- <xsd:enumeration value=\"NO_ACKNOWLEDGE\" /> -->\n" +
                "                </xsd:restriction>\n" +
                "            </xsd:simpleType>\n" +
                "            <xsd:simpleType name=\"JmsPriority\">\n" +
                "                <xsd:restriction base=\"xsd:int\">\n" +
                "                    <xsd:minInclusive value=\"0\"/>\n" +
                "                    <xsd:maxInclusive value=\"9\"/>\n" +
                "                </xsd:restriction>\n" +
                "            </xsd:simpleType>\n" +
                "            <xsd:element name=\"aEmptyOutputClass\" type=\"ActivityOutput\"/>\n" +
                "            <xsd:element name=\"ActivityInput\" type=\"JMSTransmitInputSchemaType\"/>\n" +
                "            <xsd:complexType name=\"JMSQueueGetMessageInputSchema\">\n" +
                "                <xsd:sequence>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"destinationQueue\" type=\"xsd:string\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"timeout\" type=\"xsd:long\"/>\n" +
                "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n" +
                "                        name=\"selector\" type=\"xsd:string\"/>\n" +
                "                </xsd:sequence>\n" +
                "            </xsd:complexType>\n" +
                "            <xsd:element name=\"GetJMSActivityInput\" type=\"JMSQueueGetMessageInputSchema\"/>\n" +
                "        </xsd:schema>\n" +
                "        <wsdl:definitions\n" +
                "            targetNamespace=\"http://xmlns.example.com/20171020155648PLT\"\n" +
                "            xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"\n" +
                "            xmlns:ptyp=\"http://xmlns.endesa.com/wsdl/XX_AMBITO_XX/XX_NOMBRE_REQUERIMIENTO_XX/TTXXXZZZ/\"\n" +
                "            xmlns:ptyp1=\"http://www.example.org/wsdl/1508569656669\"\n" +
                "            xmlns:ptyp2=\"http://xmlns.example.com/20171020131918\"\n" +
                "            xmlns:ptyp3=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"\n" +
                "            xmlns:tns=\"http://xmlns.example.com/20171020155648PLT\"\n" +
                "            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
                "            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "            <plnk:partnerLinkType name=\"partnerLinkType\">\n" +
                "                <plnk:role name=\"use\" portType=\"ptyp1:SISEG201\"/>\n" +
                "            </plnk:partnerLinkType>\n" +
                "            <plnk:partnerLinkType name=\"partnerLinkType1\">\n" +
                "                <plnk:role name=\"use\" portType=\"ptyp3:MainPortType\"/>\n" +
                "            </plnk:partnerLinkType>\n" +
                "            <wsdl:import namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
                "            <wsdl:import namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
                "        </wsdl:definitions>\n" +
                "    </tibex:Types>\n" +
                "    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
                "        createdOn=\"Fri Oct 20 15:56:48 CEST 2017\" description=\"\"\n" +
                "        extraErrorVars=\"false\" modifiers=\"public\"\n" +
                "        productVersion=\"6.4.1 V22 2017-08-17\" scalable=\"true\"\n" +
                "        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n" +
                "    <tibex:ProcessInterface context=\"\" input=\"\" output=\"\"/>\n" +
                "    <tibex:ProcessTemplateConfigurations/>\n" +
                "    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n" +
                "        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n" +
                "        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "        <children type=\"2001\">\n" +
                "            <children type=\"5001\">\n" +
                "                <element href=\"//0/@process\"/>\n" +
                "            </children>\n" +
                "            <children type=\"3001\">\n" +
                "                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
                "                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
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
                "                                    <layoutConstraint x=\"318\"\n" +
                "                                    xsi:type=\"notation:Bounds\" y=\"15\"/>\n" +
                "                                    </children>\n" +
                "                                    <children type=\"4002 bw.jms.receive\">\n" +
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
                "                                    <layoutConstraint\n" +
                "                                    height=\"40\" width=\"40\"\n" +
                "                                    x=\"-17\"\n" +
                "                                    xsi:type=\"notation:Bounds\" y=\"15\"/>\n" +
                "                                    </children>\n" +
                "                                    <children type=\"4002 bw.jms.reply\">\n" +
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
                "                                    x=\"543\"\n" +
                "                                    xsi:type=\"notation:Bounds\" y=\"15\"/>\n" +
                "                                    </children>\n" +
                "                                    <children type=\"4002 bw.internal.setEPR\">\n" +
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
                "                                    height=\"81\" width=\"40\"\n" +
                "                                    x=\"224\"\n" +
                "                                    xsi:type=\"notation:Bounds\" y=\"12\"/>\n" +
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
                "                                    <layoutConstraint x=\"418\"\n" +
                "                                    xsi:type=\"notation:Bounds\" y=\"15\"/>\n" +
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
                "                                    <layoutConstraint x=\"106\"\n" +
                "                                    xsi:type=\"notation:Bounds\" y=\"15\"/>\n" +
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
                "                                    <layoutConstraint height=\"177\"\n" +
                "                                    width=\"706\" xsi:type=\"notation:Bounds\"/>\n" +
                "                                </children>\n" +
                "                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
                "                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
                "                                <element href=\"//0/@process/@activity\"/>\n" +
                "                            </children>\n" +
                "                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
                "                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
                "                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
                "                            <element href=\"//0/@process/@activity\"/>\n" +
                "                            <layoutConstraint height=\"177\" width=\"709\" xsi:type=\"notation:Bounds\"/>\n" +
                "                        </children>\n" +
                "                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
                "                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
                "                        <element href=\"//0/@process/@activity\"/>\n" +
                "                    </children>\n" +
                "                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
                "                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
                "                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
                "                    <element href=\"//0/@process/@activity\"/>\n" +
                "                    <layoutConstraint height=\"201\" width=\"709\" x=\"1\"\n" +
                "                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n" +
                "                </children>\n" +
                "                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
                "                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
                "                <element href=\"//0/@process\"/>\n" +
                "            </children>\n" +
                "            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
                "            <element href=\"//0/@process\"/>\n" +
                "            <layoutConstraint height=\"253\" width=\"1051\" xsi:type=\"notation:Bounds\"/>\n" +
                "        </children>\n" +
                "        <styles xsi:type=\"notation:DiagramStyle\"/>\n" +
                "        <element href=\"//0\"/>\n" +
                "        <edges\n" +
                "            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n" +
                "            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\" type=\"4006\">\n" +
                "            <children type=\"6002\">\n" +
                "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
                "            </children>\n" +
                "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
                "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
                "            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n" +
                "            <bendpoints points=\"[25, 0, -134, 1]$[135, 0, -24, 1]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
                "        </edges>\n" +
                "        <edges\n" +
                "            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n" +
                "            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\" type=\"4006\">\n" +
                "            <children type=\"6002\">\n" +
                "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
                "            </children>\n" +
                "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
                "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
                "            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n" +
                "            <bendpoints points=\"[25, 1, -118, 0]$[119, 0, -24, -1]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
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
                "            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.4\"\n" +
                "            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n" +
                "            <children type=\"6002\">\n" +
                "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
                "            </children>\n" +
                "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
                "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
                "            <element href=\"//0/@process/@activity/@activity/@links/@children.3\"/>\n" +
                "            <bendpoints points=\"[25, 0, -101, 0]$[102, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
                "        </edges>\n" +
                "        <edges\n" +
                "            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.5\"\n" +
                "            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.3\" type=\"4006\">\n" +
                "            <children type=\"6002\">\n" +
                "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
                "            </children>\n" +
                "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
                "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
                "            <element href=\"//0/@process/@activity/@activity/@links/@children.4\"/>\n" +
                "            <bendpoints points=\"[25, 0, -93, 0]$[94, 0, -24, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
                "        </edges>\n" +
                "    </notation:Diagram>\n" +
                "    <tibex:NamespaceRegistry enabled=\"true\">\n" +
                "        <tibex:namespaceItem\n" +
                "            namespace=\"http://www.tibco.com/namespaces/tnt/plugins/jms+78894712-47e4-4e22-a426-59efe9145c41+input\" prefix=\"ns1\"/>\n" +
                "        <tibex:namespaceItem\n" +
                "            namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\" prefix=\"tns2\"/>\n" +
                "    </tibex:NamespaceRegistry>\n" +
                "    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n" +
                "    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\" namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
                "    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\"\n" +
                "        location=\"../../../../../../../../com.tibco.bw.arch.fwk/Service%20Descriptors/Internal_Service.wsdl\" namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalService\"/>\n" +
                "    <bpws:partnerLinks>\n" +
                "        <bpws:partnerLink name=\"SISEG201\"\n" +
                "            partnerLinkType=\"ns0:partnerLinkType1\" partnerRole=\"use\"\n" +
                "            sca-bpel:ignore=\"true\" sca-bpel:reference=\"SISEG201\" sca-bpel:wiredByImpl=\"false\">\n" +
                "            <tibex:ReferenceWire dynamic=\"true\" inline=\"false\"\n" +
                "                processConfigurationName=\"\" processName=\"\" serviceName=\"\"/>\n" +
                "        </bpws:partnerLink>\n" +
                "    </bpws:partnerLinks>\n" +
                "    <bpws:variables>\n" +
                "        <bpws:variable element=\"ns:ProcessContext\"\n" +
                "            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable messageType=\"ns5:MainRequest\"\n" +
                "            name=\"SISEG201-input\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable messageType=\"ns5:MainResponse\" name=\"SISEG201\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable element=\"ns9:ActivityOutput\"\n" +
                "            name=\"JMSReceiveMessage\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable name=\"jmsConnection\" sca-bpel:hotUpdate=\"false\"\n" +
                "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
                "            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/bw/models/sharedresource/jms}JMSConnectionFactory\" type=\"xsd:string\">\n" +
                "            <bpws:from>\n" +
                "                <bpws:literal>com.tibco.bw.arch.fwk.messaging.jms.JMSMessagingConnection</bpws:literal>\n" +
                "            </bpws:from>\n" +
                "        </bpws:variable>\n" +
                "        <bpws:variable element=\"ns7:ActivityInput\"\n" +
                "            name=\"ReplytoJMSMessage-input\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable element=\"ns6:SimpleEndpointReference\"\n" +
                "            name=\"SetEPR-input\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable element=\"ns6:EndpointReference\" name=\"SetEPR\" sca-bpel:internal=\"true\"/>\n" +
                "        <bpws:variable\n" +
                "            name=\"com.tibco.bw.arch.fwk.messaging.jms-Virtual\"\n" +
                "            sca-bpel:hotUpdate=\"false\" sca-bpel:privateProperty=\"true\"\n" +
                "            sca-bpel:property=\"yes\"\n" +
                "            tibex:propertySource=\"//com.tibco.bw.arch.fwk.messaging.jms///Framework/Messaging/SharedResources/JMS/Queues/Virtual\" type=\"xsd:string\"/>\n" +
                "        <bpws:variable name=\"serviceName\" sca-bpel:hotUpdate=\"false\"\n" +
                "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
                "            tibex:propertySource=\"BW.DEPLOYMENTUNIT.NAME\" type=\"xsd:string\"/>\n" +
                "        <bpws:variable name=\"serviceVersion\" sca-bpel:hotUpdate=\"false\"\n" +
                "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
                "            tibex:propertySource=\"BW.DEPLOYMENTUNIT.VERSION\" type=\"xsd:string\"/>\n" +
                "        <bpws:variable element=\"ns8:LogRequest\"\n").append(
                "            name=\"frameworkLog-input\" sca-bpel:internal=\"true\"/>\n" +
                        "        <bpws:variable element=\"ns8:LogRequest\"\n" +
                        "            name=\"frameworkLog1-input\" sca-bpel:internal=\"true\"/>\n" +
                        "        <bpws:variable name=\"jdbcProperty\" sca-bpel:hotUpdate=\"false\"\n" +
                        "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
                        "            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc}JdbcDataSource\" type=\"xsd:string\"/>\n" +
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
                        "                <bpws:link name=\"JMSReceiveMessageToframeworkLog1\" tibex:linkType=\"SUCCESS\"/>\n" +
                        "                <bpws:link name=\"SISEG201ToframeworkLog\" tibex:linkType=\"SUCCESS\"/>\n" +
                        "                <bpws:link name=\"SetEPRToSISEG201\" tibex:linkType=\"SUCCESS\"/>\n" +
                        "                <bpws:link name=\"frameworkLogToReplytoJMSMessage\" tibex:linkType=\"SUCCESS\"/>\n" +
                        "                <bpws:link name=\"frameworkLog1ToSetEPR\" tibex:linkType=\"SUCCESS\"/>\n" +
                        "            </bpws:links>\n" +
                        "            <bpws:invoke inputVariable=\"SISEG201-input\" name=\"SISEG201\"\n" +
                        "                operation=\"main\" outputVariable=\"SISEG201\"\n" +
                        "                partnerLink=\"SISEG201\" portType=\"ns5:MainPortType\" tibex:xpdlId=\"0dafc44e-4439-4286-b261-35cbfe2727ab\">\n" +
                        "                <tibex:inputBinding expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\">&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n" +
                        "&lt;xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:tns2=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:mensajes=\"http://xmlns.tibco.com/psg/wsdl/InternalService\" version=\"2.0\"&gt;\n" +
                        "    &lt;xsl:param name=\"JMSReceiveMessage\"/&gt;\n" +
                        "    &lt;xsl:template name=\"SISEG201-input\" match=\"/\"&gt;\n" +
                        "        &lt;mensajes:MainRequest&gt;\n" +
                        "            &lt;request&gt;\n" +
                        "                &lt;tns2:MainRequest&gt;\n" +
                        "                    &lt;tns2:SOAHeader&gt;\n" +
                        "                        &lt;xsl:if test=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SOAId\"&gt;\n" +
                        "                            &lt;tns2:SOAId&gt;\n" +
                        "                                &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SOAId\"/&gt;\n" +
                        "                            &lt;/tns2:SOAId&gt;\n" +
                        "                        &lt;/xsl:if&gt;\n" +
                        "                        &lt;tns2:ExternalId&gt;\n" +
                        "                            &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ExternalId\"/&gt;\n" +
                        "                        &lt;/tns2:ExternalId&gt;\n" +
                        "                        &lt;tns2:SourceApplication&gt;\n" +
                        "                            &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SourceApplication\"/&gt;\n" +
                        "                        &lt;/tns2:SourceApplication&gt;\n" +
                        "                        &lt;tns2:ServiceName&gt;\n" +
                        "                            &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceName\"/&gt;\n" +
                        "                        &lt;/tns2:ServiceName&gt;\n" +
                        "                        &lt;tns2:ServiceVersion&gt;\n" +
                        "                            &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceVersion\"/&gt;\n" +
                        "                        &lt;/tns2:ServiceVersion&gt;\n" +
                        "                        &lt;tns2:OperationName&gt;\n" +
                        "                            &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName\"/&gt;\n" +
                        "                        &lt;/tns2:OperationName&gt;\n" +
                        "                        &lt;xsl:if test=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User\"&gt;\n" +
                        "                            &lt;tns2:User&gt;\n" +
                        "                                &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User\"/&gt;\n" +
                        "                            &lt;/tns2:User&gt;\n" +
                        "                        &lt;/xsl:if&gt;\n" +
                        "                        &lt;xsl:if test=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FrameworkVersion\"&gt;\n" +
                        "                            &lt;tns2:FrameworkVersion&gt;\n" +
                        "                                &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FrameworkVersion\"/&gt;\n" +
                        "                            &lt;/tns2:FrameworkVersion&gt;\n" +
                        "                        &lt;/xsl:if&gt;\n" +
                        "                        &lt;xsl:if test=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FunctionalGroup\"&gt;\n" +
                        "                            &lt;tns2:FunctionalGroup&gt;\n" +
                        "                                &lt;xsl:value-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FunctionalGroup\"/&gt;\n" +
                        "                            &lt;/tns2:FunctionalGroup&gt;\n" +
                        "                        &lt;/xsl:if&gt;\n" +
                        "                    &lt;/tns2:SOAHeader&gt;\n" +
                        "                    &lt;xsl:copy-of select=\"$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAFunctionalInfo\"/&gt;\n" +
                        "                &lt;/tns2:MainRequest&gt;\n" +
                        "            &lt;/request&gt;\n" +
                        "        &lt;/mensajes:MainRequest&gt;\n" +
                        "    &lt;/xsl:template&gt;\n" +
                        "&lt;/xsl:stylesheet&gt;</tibex:inputBinding>\n" +
                        "                <tibex:inputBindings>\n" +
                        "                    <tibex:partBinding\n" +
                        "                        expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; xmlns:mensajes=&quot;http://xmlns.tibco.com/psg/wsdl/InternalService&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;JMSReceiveMessage&quot;/>&#xa;    &lt;xsl:template name=&quot;SISEG201-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns2:MainRequest>&#xa;            &lt;tns2:SOAHeader>&#xa;                &lt;xsl:if test=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SOAId&quot;>&#xa;                    &lt;tns2:SOAId>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SOAId&quot;/>&#xa;                    &lt;/tns2:SOAId>&#xa;                &lt;/xsl:if>&#xa;                &lt;tns2:ExternalId>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ExternalId&quot;/>&#xa;                &lt;/tns2:ExternalId>&#xa;                &lt;tns2:SourceApplication>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SourceApplication&quot;/>&#xa;                &lt;/tns2:SourceApplication>&#xa;                &lt;tns2:ServiceName>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceName&quot;/>&#xa;                &lt;/tns2:ServiceName>&#xa;                &lt;tns2:ServiceVersion>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceVersion&quot;/>&#xa;                &lt;/tns2:ServiceVersion>&#xa;                &lt;tns2:OperationName>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName&quot;/>&#xa;                &lt;/tns2:OperationName>&#xa;                &lt;xsl:if test=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User&quot;>&#xa;                    &lt;tns2:User>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User&quot;/>&#xa;                    &lt;/tns2:User>&#xa;                &lt;/xsl:if>&#xa;                &lt;xsl:if test=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FrameworkVersion&quot;>&#xa;                    &lt;tns2:FrameworkVersion>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FrameworkVersion&quot;/>&#xa;                    &lt;/tns2:FrameworkVersion>&#xa;                &lt;/xsl:if>&#xa;                &lt;xsl:if test=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FunctionalGroup&quot;>&#xa;                    &lt;tns2:FunctionalGroup>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FunctionalGroup&quot;/>&#xa;                    &lt;/tns2:FunctionalGroup>&#xa;                &lt;/xsl:if>&#xa;            &lt;/tns2:SOAHeader>&#xa;            &lt;xsl:copy-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAFunctionalInfo&quot;/>&#xa;        &lt;/tns2:MainRequest>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
                        "                </tibex:inputBindings>\n" +
                        "                <bpws:targets>\n" +
                        "                    <bpws:target linkName=\"SetEPRToSISEG201\"/>\n" +
                        "                </bpws:targets>\n" +
                        "                <bpws:sources>\n" +
                        "                    <bpws:source linkName=\"SISEG201ToReplytoJMSMessage\"/>\n" +
                        "                    <bpws:source linkName=\"SISEG201ToframeworkLog\"/>\n" +
                        "                </bpws:sources>\n" +
                        "                <bpws:correlations/>\n" +
                        "            </bpws:invoke>\n" +
                        "            <bpws:extensionActivity>\n" +
                        "                <tibex:receiveEvent createInstance=\"yes\"\n" +
                        "                    eventTimeout=\"60\" name=\"JMSReceiveMessage\" variable=\"JMSReceiveMessage\">\n" +
                        "                    <bpws:sources>\n" +
                        "                        <bpws:source linkName=\"JMSReceiveMessageToframeworkLog1\"/>\n" +
                        "                    </bpws:sources>\n" +
                        "                    <bpws:correlations/>\n" +
                        "                    <tibex:eventSource>\n" +
                        "                        <bwext:BWActivity\n" +
                        "                            activityTypeID=\"bw.jms.receive\"\n" +
                        "                            version=\"6.0.0.20132205\"\n" +
                        "                            xmlns:CommonSchemas=\"http://xmlns.tibco.com/psg/CommonSchemas\"\n" +
                        "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
                        "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
                        "                            xmlns:jms=\"http://ns.tibco.com/bw/palette/jms\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "                            <attributeBindings\n" +
                        "                                bindingType=\"moduleProperty\"\n" +
                        "                                eAttributeName=\"destinationName\" processProperty=\"com.tibco.bw.arch.fwk.messaging.jms-Virtual\"/>\n" +
                        "                            <activityConfig>\n" +
                        "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
                        "                                    <type href=\"http://ns.tibco.com/bw/palette/jms#//JMSReceiverEventSource\"/>\n" +
                        "                                    <value\n" +
                        "                                    bodyTypeQName=\"CommonSchemas:SOAInput\"\n" +
                        "                                    connectionReference=\"jmsConnection\"\n" +
                        "                                    maxSessions=\"1\"\n" +
                        "                                    messageType=\"XML Text\"\n" +
                        "                                    messagingStyle=\"Queue\"\n" +
                        "                                    receiveTimeout=\"2\" xsi:type=\"jms:JMSReceiverEventSource\"/>\n" +
                        "                                </properties>\n" +
                        "                            </activityConfig>\n" +
                        "                        </bwext:BWActivity>\n" +
                        "                    </tibex:eventSource>\n" +
                        "                </tibex:receiveEvent>\n" +
                        "            </bpws:extensionActivity>\n" +
                        "            <bpws:extensionActivity>\n" +
                        "                <tibex:activityExtension\n" +
                        "                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; xmlns:ns1=&quot;http://www.tibco.com/namespaces/tnt/plugins/jms+78894712-47e4-4e22-a426-59efe9145c41+input&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;SISEG201&quot;/>&lt;xsl:template name=&quot;ReplytoJMSMessage-input&quot; match=&quot;/&quot;>&lt;ns0:ActivityInput xmlns:ns0=&quot;http://www.tibco.com/namespaces/tnt/plugins/jms+input&quot;>&lt;Body>&lt;tns2:SOAOutput>&lt;xsl:copy-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAHeader&quot;/>&lt;tns2:SOAFunctionalInfo>&lt;xsl:copy-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAFunctionalInfo/ancestor-or-self::*/namespace::node()&quot;/>&lt;xsl:copy-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAFunctionalInfo/@*&quot;/>&lt;xsl:copy-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAFunctionalInfo/node()&quot;/>&lt;/tns2:SOAFunctionalInfo>&lt;/tns2:SOAOutput>&lt;/Body>&lt;/ns0:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\"\n" +
                        "                    expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"\n" +
                        "                    inputVariable=\"ReplytoJMSMessage-input\" name=\"ReplytoJMSMessage\">\n" +
                        "                    <bpws:targets>\n" +
                        "                        <bpws:target linkName=\"frameworkLogToReplytoJMSMessage\"/>\n" +
                        "                    </bpws:targets>\n" +
                        "                    <tibex:inputBindings>\n" +
                        "                        <tibex:inputBinding\n" +
                        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; xmlns:ns1=&quot;http://www.tibco.com/namespaces/tnt/plugins/jms+78894712-47e4-4e22-a426-59efe9145c41+input&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;SISEG201.response&quot;/>&#xa;    &lt;xsl:template name=&quot;ReplytoJMSMessage-input&quot; match=&quot;/&quot;>&#xa;        &lt;ns0:ActivityInput xmlns:ns0=&quot;http://www.tibco.com/namespaces/tnt/plugins/jms+input&quot;>&#xa;            &lt;Body>&#xa;                &lt;tns2:SOAOutput>&#xa;                    &lt;xsl:copy-of select=&quot;$SISEG201.response/tns2:SOAHeader&quot;/>&#xa;                    &lt;tns2:SOAFunctionalInfo>&#xa;                        &lt;xsl:copy-of select=&quot;$SISEG201.response/tns2:SOAFunctionalInfo/ancestor-or-self::*/namespace::node()&quot;/>&#xa;                        &lt;xsl:copy-of select=&quot;$SISEG201.response/tns2:SOAFunctionalInfo/@*&quot;/>&#xa;                        &lt;xsl:copy-of select=&quot;$SISEG201.response/tns2:SOAFunctionalInfo/node()&quot;/>&#xa;                    &lt;/tns2:SOAFunctionalInfo>&#xa;                &lt;/tns2:SOAOutput>&#xa;            &lt;/Body>&#xa;        &lt;/ns0:ActivityInput>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
                        "                    </tibex:inputBindings>\n" +
                        "                    <bpws:correlations/>\n" +
                        "                    <tibex:config>\n" +
                        "                        <bwext:BWActivity activityTypeID=\"bw.jms.reply\"\n" +
                        "                            version=\"6.0.0.20132205\"\n" +
                        "                            xmlns:CommonSchemas=\"http://xmlns.tibco.com/psg/CommonSchemas\"\n" +
                        "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
                        "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
                        "                            xmlns:jms=\"http://ns.tibco.com/bw/palette/jms\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "                            <activityConfig>\n" +
                        "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
                        "                                    <type href=\"http://ns.tibco.com/bw/palette/jms#//ReplyToJMSMessage\"/>\n" +
                        "                                    <value\n" +
                        "                                    bodyTypeQName=\"CommonSchemas:SOAOutput\"\n" +
                        "                                    messageType=\"XML Text\"\n" +
                        "                                    priority=\"4\"\n" +
                        "                                    replyFor=\"JMSReceiveMessage\" xsi:type=\"jms:ReplyToJMSMessage\"/>\n" +
                        "                                </properties>\n" +
                        "                            </activityConfig>\n" +
                        "                        </bwext:BWActivity>\n" +
                        "                    </tibex:config>\n" +
                        "                </tibex:activityExtension>\n" +
                        "            </bpws:extensionActivity>\n" +
                        "            <bpws:extensionActivity>\n" +
                        "                <tibex:activityExtension inputVariable=\"SetEPR-input\"\n" +
                        "                    name=\"SetEPR\" outputVariable=\"SetEPR\">\n" +
                        "                    <bpws:targets>\n" +
                        "                        <bpws:target linkName=\"frameworkLog1ToSetEPR\"/>\n" +
                        "                    </bpws:targets>\n" +
                        "                    <bpws:sources>\n" +
                        "                        <bpws:source linkName=\"SetEPRToSISEG201\"/>\n" +
                        "                    </bpws:sources>\n" +
                        "                    <tibex:inputBindings>\n" +
                        "                        <tibex:inputBinding\n" +
                        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://tns.tibco.com/bw/model/addressing&quot; xmlns:bw=&quot;http://www.tibco.com/bw/xpath/bw-custom-functions&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;serviceName&quot;/>&lt;xsl:param name=&quot;serviceVersion&quot;/>&lt;xsl:template name=&quot;SetEPR-input&quot; match=&quot;/&quot;>&lt;tns:SimpleEndpointReference>&lt;tns:Address>&lt;xsl:value-of select=&quot;bw:generateEPR($serviceName, $serviceVersion ,$serviceName)&quot;/>&lt;/tns:Address>&lt;/tns:SimpleEndpointReference>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
                        "                    </tibex:inputBindings>\n" +
                        "                    <tibex:config>\n" +
                        "                        <bwext:BWActivity\n" +
                        "                            activityTypeID=\"bw.internal.setEPR\"\n" +
                        "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
                        "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
                        "                            xmlns:internalactivities=\"http://ns.tibco.com/bw/core/internalactivity\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                        "                            <activityConfig>\n" +
                        "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
                        "                                    <type href=\"http://ns.tibco.com/bw/core/internalactivity#//SetEPR\"/>\n" +
                        "                                    <value referenceName=\"SISEG201\" xsi:type=\"internalactivities:SetEPR\"/>\n" +
                        "                                </properties>\n" +
                        "                            </activityConfig>\n" +
                        "                        </bwext:BWActivity>\n" +
                        "                    </tibex:config>\n" +
                        "                </tibex:activityExtension>\n" +
                        "            </bpws:extensionActivity>\n" +
                        "            <bpws:extensionActivity>\n" +
                        "                <tibex:extActivity\n" +
                        "                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;serviceName&quot;/>&#xa;    &lt;xsl:param name=&quot;serviceVersion&quot;/>&#xa;    &lt;xsl:param name=&quot;SISEG201&quot;/>&#xa;    &lt;xsl:param name=&quot;JMSReceiveMessage&quot;/>&#xa;    &lt;xsl:template name=&quot;frameworkLog-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns2:LogRequest>&#xa;            &lt;tns2:Service>&#xa;                &lt;tns2:Name>&#xa;                    &lt;xsl:value-of select=&quot;$serviceName&quot;/>&#xa;                &lt;/tns2:Name>&#xa;                &lt;tns2:Version>&#xa;                    &lt;xsl:value-of select=&quot;$serviceVersion&quot;/>&#xa;                &lt;/tns2:Version>&#xa;                &lt;tns2:ID>&#xa;                    &lt;xsl:value-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAHeader/tns2:SOAId&quot;/>&#xa;                &lt;/tns2:ID>&#xa;            &lt;/tns2:Service>&#xa;            &lt;tns2:OperationName>&#xa;                &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName&quot;/>&#xa;            &lt;/tns2:OperationName>&#xa;            &lt;tns2:Task>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;Virtual&amp;quot;&quot;/>&#xa;            &lt;/tns2:Task>&#xa;            &lt;tns2:Data>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;Response sent using JMS&amp;quot;&quot;/>&#xa;            &lt;/tns2:Data>&#xa;            &lt;tns2:Level>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;DEBUG&amp;quot;&quot;/>&#xa;            &lt;/tns2:Level>&#xa;            &lt;tns2:Additional>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;virtual.jms&amp;quot;&quot;/>&#xa;            &lt;/tns2:Additional>&#xa;            &lt;tns2:Payload>&#xa;                &lt;tns2:SOAHeader>&#xa;                    &lt;xsl:if test=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAHeader/tns2:SOAId&quot;>&#xa;                        &lt;tns2:SOAId>&#xa;                            &lt;xsl:value-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAHeader/tns2:SOAId&quot;/>&#xa;                        &lt;/tns2:SOAId>&#xa;                    &lt;/xsl:if>&#xa;                    &lt;xsl:copy-of select=&quot;$SISEG201/response/tns2:MainResponse/tns2:SOAHeader/tns2:Result&quot;/>&#xa;                    &lt;tns2:ExternalId>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ExternalId&quot;/>&#xa;                    &lt;/tns2:ExternalId>&#xa;                    &lt;tns2:SourceApplication>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SourceApplication&quot;/>&#xa;                    &lt;/tns2:SourceApplication>&#xa;                    &lt;tns2:ServiceName>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceName&quot;/>&#xa;                    &lt;/tns2:ServiceName>&#xa;                    &lt;tns2:ServiceVersion>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceVersion&quot;/>&#xa;                    &lt;/tns2:ServiceVersion>&#xa;                    &lt;tns2:OperationName>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName&quot;/>&#xa;                    &lt;/tns2:OperationName>&#xa;                    &lt;xsl:if test=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User&quot;>&#xa;                        &lt;tns2:User>&#xa;                            &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User&quot;/>&#xa;                        &lt;/tns2:User>&#xa;                    &lt;/xsl:if>&#xa;                    &lt;tns2:FrameworkVersion>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FrameworkVersion&quot;/>&#xa;                    &lt;/tns2:FrameworkVersion>&#xa;                    &lt;tns2:FunctionalGroup>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FunctionalGroup&quot;/>&#xa;                    &lt;/tns2:FunctionalGroup>&#xa;                &lt;/tns2:SOAHeader>&#xa;                &lt;xsl:copy-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAFunctionalInfo&quot;/>&#xa;            &lt;/tns2:Payload>&#xa;        &lt;/tns2:LogRequest>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\"\n" +
                        "                    expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"\n" +
                        "                    inputVariable=\"frameworkLog-input\"\n" +
                        "                    name=\"frameworkLog\"\n" +
                        "                    tibex:xpdlId=\"7a5c0a3b-622f-4d16-a237-d18ecc99d319\"\n" +
                        "                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
                        "                    <bpws:targets>\n" +
                        "                        <bpws:target linkName=\"SISEG201ToframeworkLog\"/>\n" +
                        "                    </bpws:targets>\n" +
                        "                    <bpws:sources>\n" +
                        "                        <bpws:source linkName=\"frameworkLogToReplytoJMSMessage\"/>\n" +
                        "                    </bpws:sources>\n" +
                        "                    <tibex:inputBindings>\n" +
                        "                        <tibex:inputBinding\n" +
                        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;SISEG201.response&quot;/>&#xa;    &lt;xsl:param name=&quot;serviceName&quot;/>&#xa;    &lt;xsl:param name=&quot;serviceVersion&quot;/>&#xa;    &lt;xsl:param name=&quot;JMSReceiveMessage&quot;/>&#xa;    &lt;xsl:template name=&quot;frameworkLog-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns2:LogRequest>&#xa;            &lt;tns2:Service>&#xa;                &lt;tns2:Name>&#xa;                    &lt;xsl:value-of select=&quot;$serviceName&quot;/>&#xa;                &lt;/tns2:Name>&#xa;                &lt;tns2:Version>&#xa;                    &lt;xsl:value-of select=&quot;$serviceVersion&quot;/>&#xa;                &lt;/tns2:Version>&#xa;                &lt;tns2:ID>&#xa;                    &lt;xsl:value-of select=&quot;$SISEG201.response/tns2:SOAHeader/tns2:SOAId&quot;/>&#xa;                &lt;/tns2:ID>&#xa;            &lt;/tns2:Service>&#xa;            &lt;tns2:OperationName>&#xa;                &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName&quot;/>&#xa;            &lt;/tns2:OperationName>&#xa;            &lt;tns2:Task>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;Virtual&amp;quot;&quot;/>&#xa;            &lt;/tns2:Task>&#xa;            &lt;tns2:Data>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;Response sent using JMS&amp;quot;&quot;/>&#xa;            &lt;/tns2:Data>&#xa;            &lt;tns2:Level>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;DEBUG&amp;quot;&quot;/>&#xa;            &lt;/tns2:Level>&#xa;            &lt;tns2:Additional>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;virtual.jms&amp;quot;&quot;/>&#xa;            &lt;/tns2:Additional>&#xa;            &lt;tns2:Payload>&#xa;                &lt;tns2:SOAHeader>&#xa;                    &lt;xsl:if test=&quot;$SISEG201.response/tns2:SOAHeader/tns2:SOAId&quot;>&#xa;                        &lt;tns2:SOAId>&#xa;                            &lt;xsl:value-of select=&quot;$SISEG201.response/tns2:SOAHeader/tns2:SOAId&quot;/>&#xa;                        &lt;/tns2:SOAId>&#xa;                    &lt;/xsl:if>&#xa;                    &lt;xsl:copy-of select=&quot;$SISEG201.response/tns2:SOAHeader/tns2:Result&quot;/>&#xa;                    &lt;tns2:ExternalId>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ExternalId&quot;/>&#xa;                    &lt;/tns2:ExternalId>&#xa;                    &lt;tns2:SourceApplication>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SourceApplication&quot;/>&#xa;                    &lt;/tns2:SourceApplication>&#xa;                    &lt;tns2:ServiceName>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceName&quot;/>&#xa;                    &lt;/tns2:ServiceName>&#xa;                    &lt;tns2:ServiceVersion>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceVersion&quot;/>&#xa;                    &lt;/tns2:ServiceVersion>&#xa;                    &lt;tns2:OperationName>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName&quot;/>&#xa;                    &lt;/tns2:OperationName>&#xa;                    &lt;xsl:if test=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User&quot;>&#xa;                        &lt;tns2:User>&#xa;                            &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:User&quot;/>&#xa;                        &lt;/tns2:User>&#xa;                    &lt;/xsl:if>&#xa;                    &lt;tns2:FrameworkVersion>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FrameworkVersion&quot;/>&#xa;                    &lt;/tns2:FrameworkVersion>&#xa;                    &lt;tns2:FunctionalGroup>&#xa;                        &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:FunctionalGroup&quot;/>&#xa;                    &lt;/tns2:FunctionalGroup>&#xa;                &lt;/tns2:SOAHeader>&#xa;                &lt;xsl:copy-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAFunctionalInfo&quot;/>&#xa;            &lt;/tns2:Payload>&#xa;        &lt;/tns2:LogRequest>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
                        "                    </tibex:inputBindings>\n" +
                        "                    <tibex:CallProcess moduleRef=\"com.tibco.bw.arch.fwk\"\n" +
                        "                        subProcessName=\"com.tibco.bw.fwk.main.log.frameworkLog\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
                        "                </tibex:extActivity>\n" +
                        "            </bpws:extensionActivity>\n" +
                        "            <bpws:extensionActivity>\n" +
                        "                <tibex:extActivity inputVariable=\"frameworkLog1-input\"\n" +
                        "                    name=\"frameworkLog1\"\n" +
                        "                    tibex:xpdlId=\"36de9324-56b3-4d16-9ebc-88c283a520d1\"\n" +
                        "                    type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
                        "                    <bpws:targets>\n" +
                        "                        <bpws:target linkName=\"JMSReceiveMessageToframeworkLog1\"/>\n" +
                        "                    </bpws:targets>\n" +
                        "                    <bpws:sources>\n" +
                        "                        <bpws:source linkName=\"frameworkLog1ToSetEPR\"/>\n" +
                        "                    </bpws:sources>\n" +
                        "                    <tibex:inputBindings>\n" +
                        "                        <tibex:inputBinding\n" +
                        "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;JMSReceiveMessage&quot;/>&#xa;    &lt;xsl:template name=&quot;frameworkLog1-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns2:LogRequest>&#xa;            &lt;tns2:Service>&#xa;                &lt;tns2:Name>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceName&quot;/>&#xa;                &lt;/tns2:Name>&#xa;                &lt;tns2:Version>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:ServiceVersion&quot;/>&#xa;                &lt;/tns2:Version>&#xa;                &lt;tns2:ID>&#xa;                    &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:SOAId&quot;/>&#xa;                &lt;/tns2:ID>&#xa;            &lt;/tns2:Service>&#xa;            &lt;tns2:OperationName>&#xa;                &lt;xsl:value-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader/tns2:OperationName&quot;/>&#xa;            &lt;/tns2:OperationName>&#xa;            &lt;tns2:Task>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;Virtual&amp;quot;&quot;/>&#xa;            &lt;/tns2:Task>&#xa;            &lt;tns2:Data>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;Received request by JMS&amp;quot;&quot;/>&#xa;            &lt;/tns2:Data>&#xa;            &lt;tns2:Level>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;DEBUG&amp;quot;&quot;/>&#xa;            &lt;/tns2:Level>&#xa;            &lt;tns2:Additional>&#xa;                &lt;xsl:value-of select=&quot;&amp;quot;virtual.jms&amp;quot;&quot;/>&#xa;            &lt;/tns2:Additional>&#xa;            &lt;tns2:Payload>&#xa;                &lt;xsl:copy-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAHeader&quot;/>&#xa;                &lt;xsl:copy-of select=&quot;$JMSReceiveMessage/Body/tns2:SOAInput/tns2:SOAFunctionalInfo&quot;/>&#xa;            &lt;/tns2:Payload>&#xa;        &lt;/tns2:LogRequest>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
                        "                    </tibex:inputBindings>\n" +
                        "                    <tibex:CallProcess moduleRef=\"com.tibco.bw.arch.fwk\"\n" +
                        "                        subProcessName=\"com.tibco.bw.fwk.main.log.frameworkLog\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n" +
                        "                </tibex:extActivity>\n" +
                        "            </bpws:extensionActivity>\n" +
                        "        </bpws:flow>\n" +
                        "    </bpws:scope>\n" +
                        "</bpws:process>").toString());

        
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }



    @Test
    public void testValidate() {
        System.out.println("testValidate");
        JMSAcknowledgementModeCheck instance = new JMSAcknowledgementModeCheck();
        JMSAcknowledgementModeCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }
    
}
