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
public class LastActivityAndEndActivityCheckTest {

    public LastActivityAndEndActivityCheckTest() {
    }

    private static ProcessSource source;

    @BeforeClass
    public static void setUpClass() {

        String p1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<bpws:process exitOnStandardFault=\"no\"\n"
                + "    name=\"com.tibco.bw.arch.fwk.messaging.jms.error\"\n"
                + "    suppressJoinFailure=\"yes\"\n"
                + "    targetNamespace=\"http://xmlns.example.com/20171105210637\"\n"
                + "    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n"
                + "    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n"
                + "    xmlns:ns=\"http://xmlns.example.com/20171003160204PLT\"\n"
                + "    xmlns:ns0=\"http://www.tibco.com/pe/EngineTypes\"\n"
                + "    xmlns:ns1=\"http://xmlns.tibco.com/psg/wsdl/AdminService\"\n"
                + "    xmlns:ns2=\"http://www.tibco.com/namespaces/tnt/plugins/jms+40fac99b-5a3c-43b7-b8c3-66e5ef27abd4+input\"\n"
                + "    xmlns:ns3=\"http://www.tibco.com/namespaces/tnt/plugins/jms+40fac99b-5a3c-43b7-b8c3-66e5ef27abd4+output\"\n"
                + "    xmlns:ns4=\"http://xmlns.endesa.com/wsdl/XX_AMBITO_XX/XX_NOMBRE_REQUERIMIENTO_XX/TTXXXZZZ_Physical/\"\n"
                + "    xmlns:ns5=\"http://www.tibco.com/namespaces/tnt/plugins/jms+4cc8c635-d5b9-4a30-998f-c807aa0d7067+input\"\n"
                + "    xmlns:ns6=\"http://www.tibco.com/namespaces/tnt/plugins/jms\"\n"
                + "    xmlns:ns7=\"http://xmlns.tibco.com/psg/wsdl/InternalServicePhysical\"\n"
                + "    xmlns:ns8=\"http://xmlns.tibco.com/psg/wsdl/ErrorService\"\n"
                + "    xmlns:sca=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"\n"
                + "    xmlns:sca-bpel=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"\n"
                + "    xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"\n"
                + "    xmlns:tibprop=\"http://ns.tibco.com/bw/property\"\n"
                + "    xmlns:tns=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
                + "    <tibex:Types>\n"
                + "        <xs:schema attributeFormDefault=\"unqualified\"\n"
                + "            elementFormDefault=\"qualified\"\n"
                + "            targetNamespace=\"http://www.tibco.com/pe/EngineTypes\"\n"
                + "            xmlns:tns=\"http://www.tibco.com/pe/EngineTypes\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"
                + "            <xs:complexType block=\"extension restriction\"\n"
                + "                final=\"extension restriction\" name=\"ErrorReport\">\n"
                + "                <xs:sequence>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"StackTrace\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"Msg\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"FullClass\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"Class\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"ProcessStack\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"MsgCode\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n"
                + "                </xs:sequence>\n"
                + "            </xs:complexType>\n"
                + "            <xs:complexType block=\"extension restriction\"\n"
                + "                final=\"extension restriction\" name=\"OptionalErrorReport\">\n"
                + "                <xs:sequence>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"StackTrace\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"Msg\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"FullClass\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"Class\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"ProcessStack\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"MsgCode\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n"
                + "                </xs:sequence>\n"
                + "            </xs:complexType>\n"
                + "            <xs:complexType block=\"extension restriction\"\n"
                + "                final=\"extension restriction\" name=\"FaultDetail\">\n"
                + "                <xs:sequence>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"ActivityName\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\" name=\"Data\" type=\"tns:anydata\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"Msg\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"MsgCode\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"ProcessStack\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"StackTrace\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"FullClass\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"Class\" type=\"xs:string\"/>\n"
                + "                </xs:sequence>\n"
                + "            </xs:complexType>\n"
                + "            <xs:complexType block=\"extension restriction\"\n"
                + "                final=\"extension restriction\" name=\"ProcessContext\">\n"
                + "                <xs:sequence>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"JobId\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"ApplicationName\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"EngineName\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" name=\"ProcessInstanceId\" type=\"xs:string\"/>\n"
                + "                    <xs:element\n"
                + "                        block=\"extension restriction substitution\"\n"
                + "                        form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"CustomJobId\" type=\"xs:string\"/>\n"
                + "                    <!--xs:element name=\"RestartedFromCheckpoint\" form=\"unqualified\" block=\"extension restriction substitution\" type=\"xs:boolean\"/-->\n"
                + "                    <!--xs:element minOccurs=\"0\" maxOccurs=\"unbounded\" name=\"TrackingInfo\" form=\"unqualified\" block=\"extension restriction substitution\" type=\"xs:string\"/-->\n"
                + "                </xs:sequence>\n"
                + "            </xs:complexType>\n"
                + "            <xs:complexType block=\"extension restriction\"\n"
                + "                final=\"extension restriction\" name=\"anydata\">\n"
                + "                <xs:sequence>\n"
                + "                    <xs:any namespace=\"##any\" processContents=\"lax\"/>\n"
                + "                </xs:sequence>\n"
                + "            </xs:complexType>\n"
                + "            <xs:element block=\"extension restriction substitution\"\n"
                + "                final=\"extension restriction\" name=\"OptionalErrorReport\" type=\"tns:OptionalErrorReport\"/>\n"
                + "            <xs:element block=\"extension restriction substitution\"\n"
                + "                final=\"extension restriction\" name=\"ErrorReport\" type=\"tns:ErrorReport\"/>\n"
                + "            <xs:element block=\"extension restriction substitution\"\n"
                + "                final=\"extension restriction\" name=\"FaultDetail\" type=\"tns:FaultDetail\"/>\n"
                + "            <xs:element block=\"extension restriction substitution\"\n"
                + "                final=\"extension restriction\" name=\"ProcessContext\" type=\"tns:ProcessContext\"/>\n"
                + "            <xs:element block=\"extension restriction substitution\"\n"
                + "                final=\"extension restriction\" name=\"CorrelationValue\" type=\"xs:string\"/>\n"
                + "        </xs:schema>\n"
                + "        <schema attributeFormDefault=\"unqualified\"\n"
                + "            elementFormDefault=\"unqualified\"\n"
                + "            targetNamespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"\n"
                + "            version=\"\" xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\">\n"
                + "            <complexType name=\"ActivityExceptionType\">\n"
                + "                <sequence>\n"
                + "                    <element name=\"msg\" type=\"string\"/>\n"
                + "                    <element minOccurs=\"0\" name=\"msgCode\" type=\"string\"/>\n"
                + "                </sequence>\n"
                + "            </complexType>\n"
                + "            <element name=\"ActivityException\" type=\"tns:ActivityExceptionType\"/>\n"
                + "            <complexType name=\"ActivityTimedOutExceptionType\">\n"
                + "                <complexContent>\n"
                + "                    <extension base=\"tns:ActivityExceptionType\"/>\n"
                + "                </complexContent>\n"
                + "            </complexType>\n"
                + "            <element name=\"ActivityTimedOutException\" type=\"tns:ActivityTimedOutExceptionType\"/>\n"
                + "            <complexType name=\"DuplicateKeyExceptionType\">\n"
                + "                <complexContent>\n"
                + "                    <extension base=\"tns:ActivityExceptionType\">\n"
                + "                        <sequence>\n"
                + "                            <element name=\"duplicateKey\" type=\"string\"/>\n"
                + "                            <element minOccurs=\"0\" name=\"previousJobID\" type=\"string\"/>\n"
                + "                        </sequence>\n"
                + "                    </extension>\n"
                + "                </complexContent>\n"
                + "            </complexType>\n"
                + "            <element name=\"DuplicateKeyException\" type=\"tns:DuplicateKeyExceptionType\"/>\n"
                + "        </schema>\n"
                + "        <xsd:schema attributeFormDefault=\"unqualified\"\n"
                + "            elementFormDefault=\"qualified\"\n"
                + "            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jms+4cc8c635-d5b9-4a30-998f-c807aa0d7067+input\"\n"
                + "            version=\"0.1\"\n"
                + "            xmlns=\"http://www.tibco.com/namespaces/tnt/plugins/jms+4cc8c635-d5b9-4a30-998f-c807aa0d7067+input\"\n"
                + "            xmlns:Q1=\"http://xmlns.tibco.com/psg/CommonSchemas\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
                + "            <xsd:import namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n"
                + "            <xsd:complexType name=\"ActivityOutput\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element name=\"MessageID\" type=\"xsd:string\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <!-- awagle addition -->\n"
                + "            <xsd:complexType name=\"JMSTransmitInputSchemaType\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"destination\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"replyTo\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSExpiration\" type=\"xsd:int\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSPriority\" type=\"xsd:int\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSDeliveryMode\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSType\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSProperties\" type=\"JmsTransmitProperties\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"1\" name=\"Body\">\n"
                + "                        <xsd:complexType>\n"
                + "                            <xsd:sequence>\n"
                + "                                <xsd:element ref=\"Q1:ContextData\"/>\n"
                + "                            </xsd:sequence>\n"
                + "                        </xsd:complexType>\n"
                + "                    </xsd:element>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsTransmitProperties\">\n"
                + "                <xsd:sequence>\n"
                + "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n"
                + "                    <!-- tibco specific\n"
                + "      <xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      <xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      <! these two are TNT-specific, supporting the XML decoding\n"
                + "      <xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      <xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      -->\n"
                + "                    <!-- all other properties, vendor or application defined, in a standard format -->\n"
                + "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsHeaders\">\n"
                + "                <xsd:sequence>\n"
                + "                    <!-- NOTE: a destination is actually an object.  We are able to treat is as a string\n"
                + "           because the other items that define a destination (topic vs queue, server/connection)\n"
                + "           are already defined in the config element.  We do the same for the ReplyTo -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSDestination\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSReplyTo\" type=\"xsd:string\"/>\n"
                + "                    <!-- PROBLEM: this is actually an int, but we're representing it here as a string enumeration,\n"
                + "  meaning that the app must then translate from the constants in JMS to the 'constants' in the enumeration -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSDeliveryMode\" type=\"JmsDeliveryMode\"/>\n"
                + "                    <!-- actually an int ... -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSMessageID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSTimestamp\" type=\"xsd:long\"/>\n"
                + "                    <!-- Java timestamp, millis from era -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSExpiration\" type=\"xsd:long\"/>\n"
                + "                    <!-- Java timestamp, millis from era -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSRedelivered\" type=\"xsd:boolean\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSPriority\" type=\"JmsPriority\"/>\n"
                + "                    <!-- PROBLEM: correlation id can also be retrieved as a stream of bytes -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSType\" type=\"xsd:string\"/>\n"
                + "                    <!-- <xsd:element name=\"EventSource\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsProperties\">\n"
                + "                <xsd:sequence>\n"
                + "                    <!-- most of these are not really accessible, easily, but appear on incoming messages -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXUserID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXAppID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXProducerTXID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXConsumerTXID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXRcvTimestamp\" type=\"xsd:long\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXDeliveryCount\" type=\"xsd:int\"/>\n"
                + "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n"
                + "                    <!-- tibco specific -->\n"
                + "                    <!--<xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!--<xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!-- these two are TNT-specific, supporting the XML decoding -->\n"
                + "                    <!--<xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!--<xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!-- all other properties, vendor or application defined, in a standard format -->\n"
                + "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsDynamicProperties\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element maxOccurs=\"unbounded\" minOccurs=\"0\"\n"
                + "                        name=\"property\" type=\"JmsDynamicProperty\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsDynamicProperty\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\" name=\"name\"\n"
                + "                        nillable=\"true\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n"
                + "                        name=\"value\" nillable=\"true\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"type\"\n"
                + "                        nillable=\"true\" type=\"xsd:string\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsMessage\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n"
                + "                        name=\"MessageHeaders\" type=\"JmsHeaders\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"MessageProperties\" type=\"JmsProperties\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JMSReceiveSchema\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element form=\"unqualified\" name=\"JMSHeaders\" type=\"JmsHeaders\"/>\n"
                + "                    <xsd:element form=\"unqualified\" name=\"JMSProperties\" type=\"JmsProperties\"/>\n"
                + "                    <!-- <xsd:element name=\"OtherProperties\" form=\"unqualified\" type=\"tns:OtherProperties\" minOccurs=\"0\"/>-->\n"
                + "                    <xsd:element form=\"unqualified\" name=\"Body\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:element name=\"ActivityOutput\" type=\"JMSReceiveSchema\"/>\n"
                + "            <!-- enumerations for various constants -->\n"
                + "            <xsd:simpleType name=\"JmsDeliveryMode\">\n"
                + "                <xsd:restriction base=\"xsd:string\">\n"
                + "                    <xsd:enumeration value=\"PERSISTENT\"/>\n"
                + "                    <xsd:enumeration value=\"NON_PERSISTENT\"/>\n"
                + "                    <xsd:enumeration value=\"RELIABLE_DELIVERY\"/>\n"
                + "                    <!-- Tibco Enterprise for JMS defined (TibjmsxConst) -->\n"
                + "                    <!-- <xsd:enumeration value=\"NO_ACKNOWLEDGE\" /> -->\n"
                + "                </xsd:restriction>\n"
                + "            </xsd:simpleType>\n"
                + "            <xsd:simpleType name=\"JmsPriority\">\n"
                + "                <xsd:restriction base=\"xsd:int\">\n"
                + "                    <xsd:minInclusive value=\"0\"/>\n"
                + "                    <xsd:maxInclusive value=\"9\"/>\n"
                + "                </xsd:restriction>\n"
                + "            </xsd:simpleType>\n"
                + "            <xsd:element name=\"aEmptyOutputClass\" type=\"ActivityOutput\"/>\n"
                + "            <xsd:element name=\"ActivityInput\" type=\"JMSTransmitInputSchemaType\"/>\n"
                + "            <xsd:complexType name=\"JMSQueueGetMessageInputSchema\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"destinationQueue\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"timeout\" type=\"xsd:long\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"selector\" type=\"xsd:string\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:element name=\"GetJMSActivityInput\" type=\"JMSQueueGetMessageInputSchema\"/>\n"
                + "        </xsd:schema>\n"
                + "        <xsd:schema attributeFormDefault=\"unqualified\"\n"
                + "            elementFormDefault=\"qualified\"\n"
                + "            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jms\"\n"
                + "            version=\"0.1\"\n"
                + "            xmlns=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
                + "            <xsd:complexType name=\"ActivityOutput\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element name=\"MessageID\" type=\"xsd:string\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <!-- awagle addition -->\n"
                + "            <xsd:complexType name=\"JMSTransmitInputSchemaType\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"destination\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"replyTo\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSExpiration\" type=\"xsd:int\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSPriority\" type=\"xsd:int\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSDeliveryMode\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSType\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"JMSProperties\" type=\"JmsTransmitProperties\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"1\"\n"
                + "                        name=\"Body\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsTransmitProperties\">\n"
                + "                <xsd:sequence>\n"
                + "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n"
                + "                    <!-- tibco specific\n"
                + "      <xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      <xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      <! these two are TNT-specific, supporting the XML decoding\n"
                + "      <xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      <xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" />\n"
                + "      -->\n"
                + "                    <!-- all other properties, vendor or application defined, in a standard format -->\n"
                + "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsHeaders\">\n"
                + "                <xsd:sequence>\n"
                + "                    <!-- NOTE: a destination is actually an object.  We are able to treat is as a string\n"
                + "           because the other items that define a destination (topic vs queue, server/connection)\n"
                + "           are already defined in the config element.  We do the same for the ReplyTo -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSDestination\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSReplyTo\" type=\"xsd:string\"/>\n"
                + "                    <!-- PROBLEM: this is actually an int, but we're representing it here as a string enumeration,\n"
                + "  meaning that the app must then translate from the constants in JMS to the 'constants' in the enumeration -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSDeliveryMode\" type=\"JmsDeliveryMode\"/>\n"
                + "                    <!-- actually an int ... -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSMessageID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSTimestamp\" type=\"xsd:long\"/>\n"
                + "                    <!-- Java timestamp, millis from era -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSExpiration\" type=\"xsd:long\"/>\n"
                + "                    <!-- Java timestamp, millis from era -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSRedelivered\" type=\"xsd:boolean\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSPriority\" type=\"JmsPriority\"/>\n"
                + "                    <!-- PROBLEM: correlation id can also be retrieved as a stream of bytes -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSCorrelationID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSType\" type=\"xsd:string\"/>\n"
                + "                    <!-- <xsd:element name=\"EventSource\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsProperties\">\n"
                + "                <xsd:sequence>\n"
                + "                    <!-- most of these are not really accessible, easily, but appear on incoming messages -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXUserID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXAppID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXProducerTXID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXConsumerTXID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXRcvTimestamp\" type=\"xsd:long\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXDeliveryCount\" type=\"xsd:int\"/>\n"
                + "                    <!-- these two are actually useful and used (but group seq can't be set) -->\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupID\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"JMSXGroupSeq\" type=\"xsd:int\"/>\n"
                + "                    <!-- tibco specific -->\n"
                + "                    <!--<xsd:element name=\"JMS_TIBCO_MSG_EXT\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!--<xsd:element name=\"JMS_TIBCO_COMPRESS\" type=\"xsd:boolean\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!-- these two are TNT-specific, supporting the XML decoding -->\n"
                + "                    <!--<xsd:element name=\"TIBCO_TNT_schema_reference\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!--<xsd:element name=\"TIBCO_TNT_root_element\" type=\"xsd:string\" minOccurs=\"0\" maxOccurs=\"1\" /> -->\n"
                + "                    <!-- all other properties, vendor or application defined, in a standard format -->\n"
                + "                    <!-- <xsd:element name=\"JMS_VendorDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                    <!-- <xsd:element name=\"JMS_ApplicationDefined\" type=\"typed-name-value-pair\" minOccurs=\"0\" maxOccurs=\"unbounded\" />  -->\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsDynamicProperties\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element maxOccurs=\"unbounded\" minOccurs=\"0\"\n"
                + "                        name=\"property\" type=\"JmsDynamicProperty\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsDynamicProperty\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\" name=\"name\"\n"
                + "                        nillable=\"true\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n"
                + "                        name=\"value\" nillable=\"true\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\" name=\"type\"\n"
                + "                        nillable=\"true\" type=\"xsd:string\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JmsMessage\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"1\"\n"
                + "                        name=\"MessageHeaders\" type=\"JmsHeaders\"/>\n"
                + "                    <xsd:element maxOccurs=\"1\" minOccurs=\"0\"\n"
                + "                        name=\"MessageProperties\" type=\"JmsProperties\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:complexType name=\"JMSReceiveSchema\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element form=\"unqualified\" name=\"JMSHeaders\" type=\"JmsHeaders\"/>\n"
                + "                    <xsd:element form=\"unqualified\" name=\"JMSProperties\" type=\"JmsProperties\"/>\n"
                + "                    <!-- <xsd:element name=\"OtherProperties\" form=\"unqualified\" type=\"tns:OtherProperties\" minOccurs=\"0\"/>-->\n"
                + "                    <xsd:element form=\"unqualified\" name=\"Body\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"DynamicProperties\" type=\"JmsDynamicProperties\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:element name=\"ActivityOutput\" type=\"JMSReceiveSchema\"/>\n"
                + "            <!-- enumerations for various constants -->\n"
                + "            <xsd:simpleType name=\"JmsDeliveryMode\">\n"
                + "                <xsd:restriction base=\"xsd:string\">\n"
                + "                    <xsd:enumeration value=\"PERSISTENT\"/>\n"
                + "                    <xsd:enumeration value=\"NON_PERSISTENT\"/>\n"
                + "                    <xsd:enumeration value=\"RELIABLE_DELIVERY\"/>\n"
                + "                    <!-- Tibco Enterprise for JMS defined (TibjmsxConst) -->\n"
                + "                    <!-- <xsd:enumeration value=\"NO_ACKNOWLEDGE\" /> -->\n"
                + "                </xsd:restriction>\n"
                + "            </xsd:simpleType>\n"
                + "            <xsd:simpleType name=\"JmsPriority\">\n"
                + "                <xsd:restriction base=\"xsd:int\">\n"
                + "                    <xsd:minInclusive value=\"0\"/>\n"
                + "                    <xsd:maxInclusive value=\"9\"/>\n"
                + "                </xsd:restriction>\n"
                + "            </xsd:simpleType>\n"
                + "            <xsd:element name=\"aEmptyOutputClass\" type=\"ActivityOutput\"/>\n"
                + "            <xsd:element name=\"ActivityInput\" type=\"JMSTransmitInputSchemaType\"/>\n"
                + "            <xsd:complexType name=\"JMSQueueGetMessageInputSchema\">\n"
                + "                <xsd:sequence>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"destinationQueue\" type=\"xsd:string\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"timeout\" type=\"xsd:long\"/>\n"
                + "                    <xsd:element form=\"unqualified\" minOccurs=\"0\"\n"
                + "                        name=\"selector\" type=\"xsd:string\"/>\n"
                + "                </xsd:sequence>\n"
                + "            </xsd:complexType>\n"
                + "            <xsd:element name=\"GetJMSActivityInput\" type=\"JMSQueueGetMessageInputSchema\"/>\n"
                + "        </xsd:schema>\n"
                + "        <wsdl:definitions\n"
                + "            targetNamespace=\"http://xmlns.example.com/20171003160204PLT\"\n"
                + "            xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"\n"
                + "            xmlns:ptyp=\"http://xmlns.tibco.com/psg/wsdl/AdminService\"\n"
                + "            xmlns:ptyp1=\"http://xmlns.tibco.com/psg/wsdl/ErrorService\"\n"
                + "            xmlns:ptyp2=\"http://xmlns.tibco.com/psg/wsdl/InternalServicePhysical\"\n"
                + "            xmlns:tns=\"http://xmlns.example.com/20171003160204PLT\"\n"
                + "            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n"
                + "            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
                + "            <plnk:partnerLinkType name=\"partnerLinkType1\">\n"
                + "                <plnk:role name=\"use\" portType=\"ptyp1:Service\"/>\n"
                + "            </plnk:partnerLinkType>\n"
                + "            <wsdl:import namespace=\"http://xmlns.tibco.com/psg/wsdl/InternalServicePhysical\"/>\n"
                + "            <wsdl:import namespace=\"http://xmlns.tibco.com/psg/wsdl/AdminService\"/>\n"
                + "            <wsdl:import namespace=\"http://xmlns.tibco.com/psg/wsdl/ErrorService\"/>\n"
                + "        </wsdl:definitions>\n"
                + "    </tibex:Types>\n"
                + "    <tibex:ProcessInfo callable=\"false\" createdBy=\"fmartins\"\n"
                + "        createdOn=\"Tue Oct 03 16:02:04 BST 2017\" description=\"\"\n"
                + "        extraErrorVars=\"false\" modifiers=\"private\"\n"
                + "        productVersion=\"6.4.1 V22 2017-08-17\" scalable=\"true\"\n"
                + "        singleton=\"true\" stateless=\"true\" type=\"IT\"/>\n"
                + "    <tibex:ProcessInterface context=\"\" input=\"\" output=\"\"/>\n"
                + "    <tibex:ProcessTemplateConfigurations/>\n"
                + "    <notation:Diagram measurementUnit=\"Pixel\" type=\"BWProcess\"\n"
                + "        xmlns:bwnotation=\"http://tns.tibco.com/bw/runtime/BWNotation\"\n"
                + "        xmlns:notation=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                + "        <children type=\"2001\">\n"
                + "            <children type=\"5001\">\n"
                + "                <element href=\"//0/@process\"/>\n"
                + "            </children>\n"
                + "            <children type=\"3001\">\n"
                + "                <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                <element href=\"//0/@process\"/>\n"
                + "            </children>\n"
                + "            <children type=\"3002\">\n"
                + "                <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                <element href=\"//0/@process\"/>\n"
                + "            </children>\n"
                + "            <children type=\"3003\">\n"
                + "                <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                <element href=\"//0/@process\"/>\n"
                + "            </children>\n"
                + "            <children type=\"3004\">\n"
                + "                <children type=\"4018\">\n"
                + "                    <children type=\"3018\">\n"
                + "                        <children type=\"4020\">\n"
                + "                            <children type=\"3020\">\n"
                + "                                <children type=\"4005\">\n"
                + "                                    <children type=\"3007\">\n"
                + "                                    <children type=\"4002\">\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles\n"
                + "                                    backgroundColor=\"16777215\"\n"
                + "                                    gradientEndColor=\"50431\"\n"
                + "                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.0\"/>\n"
                + "                                    <layoutConstraint x=\"2\"\n"
                + "                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4002 bw.jms.send\">\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles\n"
                + "                                    backgroundColor=\"16777215\"\n"
                + "                                    gradientEndColor=\"50431\"\n"
                + "                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n"
                + "                                    <layoutConstraint\n"
                + "                                    height=\"40\" width=\"40\"\n"
                + "                                    x=\"230\"\n"
                + "                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4002\">\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles\n"
                + "                                    backgroundColor=\"16777215\"\n"
                + "                                    gradientEndColor=\"50431\"\n"
                + "                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity/@activities.2\"/>\n"
                + "                                    <layoutConstraint x=\"454\"\n"
                + "                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n"
                + "                                    <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles backgroundColor=\"16777215\"\n"
                + "                                    gradientEndColor=\"50431\"\n"
                + "                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@activity\"/>\n"
                + "                                    <layoutConstraint height=\"227\"\n"
                + "                                    width=\"710\" xsi:type=\"notation:Bounds\"/>\n"
                + "                                </children>\n"
                + "                                <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                                <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                                <element href=\"//0/@process/@activity\"/>\n"
                + "                            </children>\n"
                + "                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n"
                + "                            <element href=\"//0/@process/@activity\"/>\n"
                + "                            <layoutConstraint height=\"227\" width=\"713\" xsi:type=\"notation:Bounds\"/>\n"
                + "                        </children>\n"
                + "                        <children type=\"4022\">\n"
                + "                            <children type=\"3022\">\n"
                + "                                <children type=\"4029\">\n"
                + "                                    <children type=\"3031\">\n"
                + "                                    <children type=\"4033\">\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@faultHandlers/@catchAll\"/>\n"
                + "                                    <layoutConstraint\n"
                + "                                    height=\"34\" width=\"34\"\n"
                + "                                    xsi:type=\"notation:Bounds\" y=\"68\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"3032\">\n"
                + "                                    <children type=\"4005\">\n"
                + "                                    <children type=\"3007\">\n"
                + "                                    <children type=\"4002\">\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <children type=\"4017\">\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles\n"
                + "                                    fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles\n"
                + "                                    backgroundColor=\"16777215\"\n"
                + "                                    gradientEndColor=\"50431\"\n"
                + "                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@faultHandlers/@catchAll/@activity/@activity/@activities.0\"/>\n"
                + "                                    <layoutConstraint\n"
                + "                                    x=\"86\"\n"
                + "                                    xsi:type=\"notation:Bounds\" y=\"43\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n"
                + "                                    <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles\n"
                + "                                    backgroundColor=\"16777215\"\n"
                + "                                    gradientEndColor=\"50431\"\n"
                + "                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@faultHandlers/@catchAll/@activity/@activity\"/>\n"
                + "                                    <layoutConstraint\n"
                + "                                    height=\"168\" width=\"612\" xsi:type=\"notation:Bounds\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                                    </children>\n"
                + "                                    <styles fontName=\"Segoe UI\"\n"
                + "                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n"
                + "                                    <element href=\"//0/@process/@activity/@faultHandlers/@catchAll\"/>\n"
                + "                                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n"
                + "                                </children>\n"
                + "                                <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                                <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                            </children>\n"
                + "                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n"
                + "                            <element href=\"//0/@process/@activity/@faultHandlers\"/>\n"
                + "                            <layoutConstraint height=\"200\" width=\"618\" xsi:type=\"notation:Bounds\"/>\n"
                + "                        </children>\n"
                + "                        <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                        <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                        <element href=\"//0/@process/@activity\"/>\n"
                + "                    </children>\n"
                + "                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n"
                + "                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n"
                + "                    <element href=\"//0/@process/@activity\"/>\n"
                + "                    <layoutConstraint height=\"428\" width=\"713\" x=\"1\"\n"
                + "                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n"
                + "                </children>\n"
                + "                <styles xsi:type=\"notation:SortingStyle\"/>\n"
                + "                <styles xsi:type=\"notation:FilteringStyle\"/>\n"
                + "                <element href=\"//0/@process\"/>\n"
                + "            </children>\n"
                + "            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n"
                + "            <element href=\"//0/@process\"/>\n"
                + "            <layoutConstraint height=\"480\" width=\"1055\" xsi:type=\"notation:Bounds\"/>\n"
                + "        </children>\n"
                + "        <styles xsi:type=\"notation:DiagramStyle\"/>\n"
                + "        <element href=\"//0\"/>\n"
                + "        <edges\n"
                + "            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0\"\n"
                + "            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\" type=\"4006\">\n"
                + "            <children type=\"6002\">\n"
                + "                <layoutConstraint x=\"15\" xsi:type=\"notation:Location\" y=\"26\"/>\n"
                + "            </children>\n"
                + "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n"
                + "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n"
                + "            <element href=\"//0/@process/@activity/@activity/@links/@children.0\"/>\n"
                + "            <bendpoints points=\"[25, 15, -149, -92]$[150, 92, -24, -15]\" xsi:type=\"notation:RelativeBendpoints\"/>\n"
                + "        </edges>\n"
                + "        <edges\n"
                + "            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1\"\n"
                + "            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.2\" type=\"4006\">\n"
                + "            <children type=\"6002\">\n"
                + "                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n"
                + "            </children>\n"
                + "            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n"
                + "            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n"
                + "            <element href=\"//0/@process/@activity/@activity/@links/@children.1\"/>\n"
                + "            <bendpoints points=\"[25, -9, -254, 95]$[255, -95, -24, 9]\" xsi:type=\"notation:RelativeBendpoints\"/>\n"
                + "        </edges>\n"
                + "    </notation:Diagram>\n"
                + "    <bpws:import importType=\"http://www.w3.org/2001/XMLSchema\" namespace=\"http://xmlns.tibco.com/psg/CommonSchemas\"/>\n"
                + "    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\" namespace=\"http://xmlns.tibco.com/psg/wsdl/ErrorService\"/>\n"
                + "    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\"\n"
                + "        location=\"../../../../../../../../com.tibco.bw.arch.fwk/Service%20Descriptors/sendError.wsdl\" namespace=\"http://xmlns.tibco.com/psg/wsdl/ErrorService\"/>\n"
                + "    <bpws:partnerLinks>\n"
                + "        <bpws:partnerLink myRole=\"use\" name=\"JMSError\"\n"
                + "            partnerLinkType=\"ns:partnerLinkType1\"\n"
                + "            sca-bpel:ignore=\"false\" sca-bpel:service=\"JMSError\" tibex:register=\"true\"/>\n"
                + "    </bpws:partnerLinks>\n"
                + "    <bpws:variables>\n"
                + "        <bpws:variable element=\"ns0:ProcessContext\"\n"
                + "            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable messageType=\"ns8:sendErrorRequest\"\n"
                + "            name=\"recoverIn\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable name=\"jmsConnection\" sca-bpel:hotUpdate=\"false\"\n"
                + "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n"
                + "            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/bw/models/sharedresource/jms}JMSConnectionFactory\" type=\"xsd:string\">\n"
                + "            <bpws:from>\n"
                + "                <bpws:literal>com.tibco.bw.arch.fwk.messaging.jms.JMSMessagingConnection</bpws:literal>\n"
                + "            </bpws:from>\n"
                + "        </bpws:variable>\n"
                + "        <bpws:variable element=\"ns5:ActivityInput\"\n"
                + "            name=\"JMSSendMessage-input\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable element=\"ns6:aEmptyOutputClass\"\n"
                + "            name=\"JMSSendMessage\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable name=\"jmsTimeout\" sca-bpel:hotUpdate=\"false\"\n"
                + "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n"
                + "            tibex:propertySource=\"/Framework/Messaging/DefaultTimeout\" type=\"xsd:int\"/>\n"
                + "        <bpws:variable element=\"tns:InternalErrorNormalizer\"\n"
                + "            name=\"internalErrorNormalizer-input\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable element=\"tns:Error\"\n"
                + "            name=\"internalErrorNormalizer\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable name=\"FaultName\" sca-bpel:internal=\"true\" type=\"xsd:string\"/>\n"
                + "        <bpws:variable element=\"ns0:FaultDetail\" name=\"FaultDetails\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable element=\"tns:InternalErrorNormalizer\"\n"
                + "            name=\"internalErrorNormalizer-input2\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable element=\"tns:Error\"\n"
                + "            name=\"internalErrorNormalizer2\" sca-bpel:internal=\"true\"/>\n"
                + "        <bpws:variable name=\"jmsExpiration\" sca-bpel:hotUpdate=\"false\"\n"
                + "            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n"
                + "            tibex:propertySource=\"/Framework/Messaging/DefaultExpiration\" type=\"xsd:int\"/>\n"
                + "    </bpws:variables>\n"
                + "    <bpws:extensions>\n"
                + "        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.eclipse.org/gmf/runtime/1.0.2/notation\"/>\n"
                + "        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.tibco.com/bw/process/info\"/>\n"
                + "        <bpws:extension mustUnderstand=\"no\" namespace=\"http://docs.oasis-open.org/ns/opencsa/sca-bpel/200801\"/>\n"
                + "        <bpws:extension mustUnderstand=\"no\" namespace=\"http://docs.oasis-open.org/ns/opencsa/sca/200912\"/>\n"
                + "        <bpws:extension mustUnderstand=\"no\" namespace=\"http://ns.tibco.com/bw/property\"/>\n"
                + "        <bpws:extension mustUnderstand=\"no\" namespace=\"http://www.tibco.com/bpel/2007/extensions\"/>\n"
                + "    </bpws:extensions>\n"
                + "    <bpws:scope name=\"scope\">\n"
                + "        <bpws:faultHandlers>\n"
                + "            <bpws:catchAll tibex:faultDetailsVar=\"FaultDetails\"\n"
                + "                tibex:faultNameVar=\"FaultName\" tibex:xpdlId=\"c84ebd02-526d-4817-b3ca-90496b5c04db\">\n"
                + "                <bpws:scope name=\"scope1\">\n"
                + "                    <bpws:flow name=\"flow1\">\n"
                + "                        <bpws:links/>\n"
                + "                        <bpws:extensionActivity>\n"
                + "                            <tibex:extActivity\n"
                + "                                inputVariable=\"internalErrorNormalizer-input2\"\n"
                + "                                name=\"internalErrorNormalizer\"\n"
                + "                                outputVariable=\"internalErrorNormalizer2\"\n"
                + "                                tibex:xpdlId=\"c05c17cf-b84a-4321-9387-f2fa9e124a63\"\n"
                + "                                type=\"bw.generalactivities.callprocess\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n"
                + "                                <tibex:inputBindings>\n"
                + "                                    <tibex:inputBinding\n"
                + "                                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;FaultDetails&quot;/>&lt;xsl:template name=&quot;internalErrorNormalizer-input2&quot; match=&quot;/&quot;>&lt;tns:InternalErrorNormalizer>&lt;tns:Msg>&lt;xsl:value-of select=&quot;$FaultDetails/Msg&quot;/>&lt;/tns:Msg>&lt;tns:ActivityName>&lt;xsl:value-of select=&quot;$FaultDetails/ActivityName&quot;/>&lt;/tns:ActivityName>&lt;tns:ComponentName>&lt;xsl:value-of select=&quot;&amp;quot;Bus JMS&amp;quot;&quot;/>&lt;/tns:ComponentName>&lt;tns:StackTrace>&lt;xsl:value-of select=&quot;$FaultDetails/StackTrace&quot;/>&lt;/tns:StackTrace>&lt;/tns:InternalErrorNormalizer>&lt;/xsl:template>&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n"
                + "                                </tibex:inputBindings>\n"
                + "                                <tibex:CallProcess\n"
                + "                                    moduleRef=\"com.tibco.bw.arch.fwk\"\n"
                + "                                    subProcessName=\"com.tibco.bw.fwk.main.error.internalErrorNormalizer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\"/>\n"
                + "                            </tibex:extActivity>\n"
                + "                        </bpws:extensionActivity>\n"
                + "                    </bpws:flow>\n"
                + "                </bpws:scope>\n"
                + "            </bpws:catchAll>\n"
                + "        </bpws:faultHandlers>\n"
                + "        <bpws:flow name=\"flow\">\n"
                + "            <bpws:links>\n"
                + "                <bpws:link name=\"StartToJMSSendMessage_Name\"\n"
                + "                    tibex:label=\"\" tibex:linkType=\"SUCCESS\"/>\n"
                + "                <bpws:link name=\"JMSSendMessageToExit\" tibex:linkType=\"SUCCESS\"/>\n"
                + "            </bpws:links>\n"
                + "            <bpws:receive createInstance=\"yes\" name=\"Start\"\n"
                + "                operation=\"sendError\" partnerLink=\"JMSError\"\n"
                + "                portType=\"ns8:Service\"\n"
                + "                tibex:xpdlId=\"ed8bd404-2501-4c3d-9ed6-234163e88fe5\" variable=\"recoverIn\">\n"
                + "                <bpws:sources>\n"
                + "                    <bpws:source linkName=\"StartToJMSSendMessage_Name\">\n"
                + "                        <tibex:DesignExpression/>\n"
                + "                    </bpws:source>\n"
                + "                </bpws:sources>\n"
                + "            </bpws:receive>\n"
                + "            <bpws:extensionActivity>\n"
                + "                <tibex:activityExtension\n"
                + "                    expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/namespaces/tnt/plugins/jms+4cc8c635-d5b9-4a30-998f-c807aa0d7067+input&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&lt;xsl:param name=&quot;recoverIn&quot;/>&lt;xsl:template name=&quot;JMSSendMessage-input&quot; match=&quot;/&quot;>&lt;tns:ActivityInput>&lt;destination>&lt;xsl:value-of select=&quot;concat(&amp;quot;com.tibco.bw.arch.fwk.error.&amp;quot;,$recoverIn/parameters/tns2:ContextData/tns2:Data/tns2:SOAHeader/tns2:ServiceName,&amp;quot;.&amp;quot;,$recoverIn/parameters/tns2:ContextData/tns2:Data/tns2:SOAHeader/tns2:ServiceVersion)&quot;/>&lt;/destination>&lt;Body>&lt;xsl:copy-of select=&quot;$recoverIn/parameters/tns2:ContextData&quot;/>&lt;/Body>&lt;/tns:ActivityInput>&lt;/xsl:template>&lt;/xsl:stylesheet>\"\n"
                + "                    expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"\n"
                + "                    inputVariable=\"JMSSendMessage-input\"\n"
                + "                    name=\"JMSSendMessage\"\n"
                + "                    outputVariable=\"JMSSendMessage\"\n"
                + "                    tibex:xpdlId=\"4cc8c635-d5b9-4a30-998f-c807aa0d7067\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n"
                + "                    <bpws:targets>\n"
                + "                        <bpws:target linkName=\"StartToJMSSendMessage_Name\"/>\n"
                + "                    </bpws:targets>\n"
                + "                    <bpws:sources>\n"
                + "                        <bpws:source linkName=\"JMSSendMessageToExit\"/>\n"
                + "                    </bpws:sources>\n"
                + "                    <tibex:inputBindings>\n"
                + "                        <tibex:inputBinding\n"
                + "                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/namespaces/tnt/plugins/jms+4cc8c635-d5b9-4a30-998f-c807aa0d7067+input&quot; xmlns:tns2=&quot;http://xmlns.tibco.com/psg/CommonSchemas&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;recoverIn.parameters&quot;/>&#xa;    &lt;xsl:template name=&quot;JMSSendMessage-input&quot; match=&quot;/&quot;>&#xa;        &lt;tns:ActivityInput>&#xa;            &lt;destination>&#xa;                &lt;xsl:value-of select=&quot;concat(&amp;quot;com.tibco.bw.arch.fwk.error.&amp;quot;,$recoverIn.parameters/tns2:Data/tns2:SOAHeader/tns2:ServiceName,&amp;quot;.&amp;quot;,$recoverIn.parameters/tns2:Data/tns2:SOAHeader/tns2:ServiceVersion)&quot;/>&#xa;            &lt;/destination>&#xa;            &lt;Body>&#xa;                &lt;xsl:copy-of select=&quot;$recoverIn.parameters&quot;/>&#xa;            &lt;/Body>&#xa;        &lt;/tns:ActivityInput>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n"
                + "                    </tibex:inputBindings>\n"
                + "                    <tibex:config>\n"
                + "                        <bwext:BWActivity activityTypeID=\"bw.jms.send\"\n"
                + "                            version=\"6.0.0.20132205\"\n"
                + "                            xmlns:CommonSchemas=\"http://xmlns.tibco.com/psg/CommonSchemas\"\n";
        source = new ProcessSource(p1
                + "                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n"
                + "                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n"
                + "                            xmlns:jms=\"http://ns.tibco.com/bw/palette/jms\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n"
                + "                            <activityConfig>\n"
                + "                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n"
                + "                                    <type href=\"http://ns.tibco.com/bw/palette/jms#//JMSSenderActivityConfiguration\"/>\n"
                + "                                    <value\n"
                + "                                    bodyTypeQName=\"CommonSchemas:ContextData\"\n"
                + "                                    connectionReference=\"jmsConnection\"\n"
                + "                                    messageType=\"XML Text\"\n"
                + "                                    messagingStyle=\"Queue\"\n"
                + "                                    priority=\"4\" xsi:type=\"jms:JMSSenderActivityConfiguration\"/>\n"
                + "                                </properties>\n"
                + "                            </activityConfig>\n"
                + "                        </bwext:BWActivity>\n"
                + "                    </tibex:config>\n"
                + "                </tibex:activityExtension>\n"
                + "            </bpws:extensionActivity>\n"
                + "            <bpws:exit name=\"Exit\" tibex:xpdlId=\"3d4ba415-055c-497b-b0fd-2b359d3dd307\">\n"
                + "                <bpws:targets>\n"
                + "                    <bpws:target linkName=\"JMSSendMessageToExit\"/>\n"
                + "                </bpws:targets>\n"
                + "            </bpws:exit>\n"
                + "        </bpws:flow>\n"
                + "    </bpws:scope>\n"
                + "</bpws:process>"
        );

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testValidate() {
        System.out.println("testValidate");
        LastActivityAndEndActivityCheck instance = new LastActivityAndEndActivityCheck();
        LastActivityAndEndActivityCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        spyInstance.validate(source);
        Mockito.verify(spyInstance, Mockito.times(1)).reportIssueOnFile(anyString(),anyInt());
        
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\"\n" +
"    name=\"arch_genericLibraries_genericDynamicInvocation.DummyProcess\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20171018194534\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://xmlns.example.com/20170929093525PLT\"\n" +
"    xmlns:ns0=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns2=\"http://www.framework.com/schemas/ARCH_ESBFramework\"\n" +
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
"            targetNamespace=\"http://xmlns.example.com/20170929093525PLT\"\n" +
"            xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"\n" +
"            xmlns:ptyp2=\"http://www.framework.com/schemas/ARCH_ESBFramework\"\n" +
"            xmlns:tns=\"http://xmlns.example.com/20170929093525PLT\"\n" +
"            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
"            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <plnk:partnerLinkType name=\"partnerLinkType\">\n" +
"                <plnk:role name=\"use\" portType=\"ptyp2:ARCH_GenericDynamicInvocation\"/>\n" +
"            </plnk:partnerLinkType>\n" +
"            <plnk:partnerLinkType name=\"partnerLinkType1\">\n" +
"                <plnk:role name=\"use\" portType=\"ptyp2:ARCH_GenericDynamicInvocation\"/>\n" +
"            </plnk:partnerLinkType>\n" +
"            <wsdl:import namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\"/>\n" +
"        </wsdl:definitions>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"juan.nevado.martin\"\n" +
"        createdOn=\"Fri Sep 29 09:35:25 CEST 2017\" description=\"\"\n" +
"        extraErrorVars=\"false\" modifiers=\"public\"\n" +
"        productVersion=\"6.4.0 V29 2017-05-09\" scalable=\"true\"\n" +
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
"                <children type=\"4001\">\n" +
"                    <children type=\"4017\">\n" +
"                        <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                        <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                        <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                    </children>\n" +
"                    <children type=\"4017\">\n" +
"                        <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                        <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                        <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                    </children>\n" +
"                    <children type=\"4017\">\n" +
"                        <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                        <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                        <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                    </children>\n" +
"                    <children type=\"4017\">\n" +
"                        <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                        <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                        <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                    </children>\n" +
"                    <children type=\"40011\">\n" +
"                        <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                    </children>\n" +
"                    <children type=\"3006\">\n" +
"                        <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                        <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                        <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                        <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                    </children>\n" +
"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                    <element href=\"//0/@process/@partnerLinks/@children.0\"/>\n" +
"                    <layoutConstraint xsi:type=\"notation:Bounds\"/>\n" +
"                </children>\n" +
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
"                                    <layoutConstraint x=\"2\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n" +
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
"                                    <layoutConstraint x=\"135\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n" +
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
"                                    <layoutConstraint height=\"108\"\n" +
"                                    width=\"400\" x=\"2\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"2\"/>\n" +
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
"    </notation:Diagram>\n" +
"    <bpws:documentation>Dummy subprocess</bpws:documentation>\n" +
"    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\"\n" +
"        location=\"../../Service%20Descriptors/ARCH_GenericDynamicInvocation.wsdl\" namespace=\"http://www.framework.com/schemas/ARCH_ESBFramework\"/>\n" +
"    <bpws:partnerLinks>\n" +
"        <bpws:partnerLink myRole=\"use\"\n" +
"            name=\"ARCH_GenericDynamicInvocation\"\n" +
"            partnerLinkType=\"ns:partnerLinkType1\"\n" +
"            sca-bpel:ignore=\"false\"\n" +
"            sca-bpel:service=\"ARCH_GenericDynamicInvocation\" tibex:register=\"false\"/>\n" +
"    </bpws:partnerLinks>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns0:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns2:DynamicCallRequest\"\n" +
"            name=\"DynamicCallIn\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns2:DynamicCallResponse\"\n" +
"            name=\"DynamicCallOut-input\" sca-bpel:internal=\"true\"/>\n" +
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
"            </bpws:links>\n" +
"            <bpws:receive createInstance=\"yes\" name=\"Start\"\n" +
"                operation=\"DynamicCall\"\n" +
"                partnerLink=\"ARCH_GenericDynamicInvocation\"\n" +
"                portType=\"ns2:ARCH_GenericDynamicInvocation\"\n" +
"                tibex:xpdlId=\"a499f94d-d674-4aad-904f-b9fb91e675cb\" variable=\"DynamicCallIn\">\n" +
"                <bpws:sources>\n" +
"                    <bpws:source linkName=\"StartToEnd\"/>\n" +
"                </bpws:sources>\n" +
"            </bpws:receive>\n" +
"            <bpws:reply name=\"End\" operation=\"DynamicCall\"\n" +
"                partnerLink=\"ARCH_GenericDynamicInvocation\"\n" +
"                portType=\"ns2:ARCH_GenericDynamicInvocation\"\n" +
"                tibex:xpdlId=\"ddc3c75f-5816-4eb6-b2b7-898896b5b004\" variable=\"DynamicCallOut-input\">\n" +
"                <tibex:inputBinding expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\">&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n" +
"&lt;xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"2.0\"&gt;&lt;xsl:param name=\"_processContext\"/&gt;&lt;xsl:template name=\"DynamicCallOut-input\" match=\"/\"&gt;&lt;ns0:DynamicCallResponse xmlns:ns0=\"http://www.framework.com/schemas/ARCH_ESBFramework\"&gt;&lt;parameters&gt;&lt;ns0:OutputElement&gt;&lt;xsl:copy-of select=\"$_processContext/JobId\"/&gt;&lt;/ns0:OutputElement&gt;&lt;/parameters&gt;&lt;/ns0:DynamicCallResponse&gt;&lt;/xsl:template&gt;&lt;/xsl:stylesheet&gt;</tibex:inputBinding>\n" +
"                <tibex:inputBindings>\n" +
"                    <tibex:partBinding\n" +
"                        expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?>&#xa;&lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; version=&quot;2.0&quot;>&#xa;    &lt;xsl:param name=&quot;_processContext&quot;/>&#xa;    &lt;xsl:template name=&quot;DynamicCallOut-input&quot; match=&quot;/&quot;>&#xa;        &lt;ns0:OutputElement xmlns:ns0=&quot;http://www.framework.com/schemas/ARCH_ESBFramework&quot;>&#xa;            &lt;xsl:copy-of select=&quot;$_processContext/JobId&quot;/>&#xa;        &lt;/ns0:OutputElement>&#xa;    &lt;/xsl:template>&#xa;&lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                </tibex:inputBindings>\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"StartToEnd\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:correlations/>\n" +
"            </bpws:reply>\n" +
"        </bpws:flow>\n" +
"    </bpws:scope>\n" +
"</bpws:process>");
        spyInstance.validate(source);
        Mockito.verify(spyInstance, Mockito.times(1)).reportIssueOnFile(anyString(),anyInt());
    }
}
