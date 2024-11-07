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
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import org.mockito.Mockito;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import com.tibco.utils.common.logger.Logger;

/**
 *
 * @author avazquez
 */
public class ThreadpoolUsageInJDBCActivitiesCheckITTest {
    
    public ThreadpoolUsageInJDBCActivitiesCheckITTest() {
    }
    
  
       private static ProcessSource source;
    

     
    @BeforeClass
    public static void setUpClass() {
       
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\" name=\"t1.module.Process1\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20190518090735\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://xmlns.example.com/20190518090735PLT\"\n" +
"    xmlns:ns1=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"    xmlns:ns2=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+input\"\n" +
"    xmlns:ns3=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+output\"\n" +
"    xmlns:ns4=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.jdbc.JDBCQuery\"\n" +
"    xmlns:ns5=\"http://www.example.org/Infocaja_CONSULTA_PRECONTRATACION_1.0.0\"\n" +
"    xmlns:ns6=\"http://tns.tibco.com/bw/palette/internal/activityerror+71218a04-9b98-4bce-9b2b-fb12a49bc3f4\"\n" +
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
"        <schema\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+input\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+input\">\n" +
"            <element name=\"jdbcQueryActivityInput\">\n" +
"                <complexType>\n" +
"                    <sequence>\n" +
"                        <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                            name=\"ServerTimeZone\" type=\"string\"/>\n" +
"                        <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                            name=\"timeout\" type=\"long\"/>\n" +
"                        <element maxOccurs=\"1\" minOccurs=\"0\"\n" +
"                            name=\"maxRows\" type=\"long\"/>\n" +
"                    </sequence>\n" +
"                </complexType>\n" +
"            </element>\n" +
"        </schema>\n" +
"        <schema\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+output\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+output\">\n" +
"            <element name=\"resultSet\">\n" +
"                <complexType>\n" +
"                    <sequence>\n" +
"                        <element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"Record\">\n" +
"                            <complexType>\n" +
"                                <sequence/>\n" +
"                            </complexType>\n" +
"                        </element>\n" +
"                    </sequence>\n" +
"                </complexType>\n" +
"            </element>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"\n" +
"            version=\"0.1\" xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns2=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\">\n" +
"            <complexType name=\"ActivityExceptionType\">\n" +
"                <sequence>\n" +
"                    <element name=\"msg\" type=\"string\"/>\n" +
"                    <element minOccurs=\"0\" name=\"msgCode\" type=\"string\"/>\n" +
"                </sequence>\n" +
"            </complexType>\n" +
"            <complexType name=\"PluginExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:ActivityExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"JDBCPluginException\" type=\"tns2:JDBCPluginExceptionType\"/>\n" +
"            <complexType name=\"JDBCPluginExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:PluginExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"InvalidSQLTypeException\" type=\"tns2:InvalidSQLTypeExceptionType\"/>\n" +
"            <complexType name=\"InvalidSQLTypeExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:JDBCPluginExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"typeName\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"JDBCConnectionNotFoundException\" type=\"tns2:JDBCConnectionNotFoundExceptionType\"/>\n" +
"            <complexType name=\"JDBCConnectionNotFoundExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:JDBCPluginExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"jdbcConnection\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"DuplicatedFieldNameException\" type=\"tns2:DuplicatedFieldNameExceptionType\"/>\n" +
"            <complexType name=\"DuplicatedFieldNameExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:JDBCPluginExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"fieldName\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"InvalidTimeZoneException\" type=\"tns2:InvalidTimeZoneExceptionType\"/>\n" +
"            <complexType name=\"InvalidTimeZoneExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:JDBCPluginExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"timeZone\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"JDBCSQLException\" type=\"tns2:JDBCSQLExceptionType\"/>\n" +
"            <complexType name=\"JDBCSQLExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:JDBCPluginExceptionType\">\n" +
"                        <sequence>\n" +
"                            <element name=\"sqlState\" type=\"string\"/>\n" +
"                            <element name=\"detailStr\" type=\"string\"/>\n" +
"                        </sequence>\n" +
"                    </extension>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"            <element name=\"LoginTimedOutException\" type=\"tns2:LoginTimedOutExceptionType\"/>\n" +
"            <complexType name=\"LoginTimedOutExceptionType\">\n" +
"                <complexContent>\n" +
"                    <extension base=\"tns2:JDBCPluginExceptionType\"/>\n" +
"                </complexContent>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.jdbc.JDBCQuery\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\"\n" +
"            xmlns:Q1=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"\n" +
"            xmlns:Q2=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\" xmlns:tns=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.jdbc.JDBCQuery\">\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"/>\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/plugins/jdbc/5.0/jdbcExceptions\"/>\n" +
"            <element name=\"ActivityErrorData\" type=\"tns:ActivityErrorDataType\"/>\n" +
"            <complexType name=\"ActivityErrorDataType\">\n" +
"                <choice>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:JDBCConnectionNotFoundException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:InvalidTimeZoneException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:JDBCSQLException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:LoginTimedOutException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:InvalidSQLTypeException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:DuplicatedFieldNameException\"/>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q2:ActivityTimedOutException\"/>\n" +
"                </choice>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <schema elementFormDefault=\"unqualified\"\n" +
"            targetNamespace=\"http://tns.tibco.com/bw/palette/internal/activityerror+71218a04-9b98-4bce-9b2b-fb12a49bc3f4\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\"\n" +
"            xmlns:Q1=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\" xmlns:tns=\"http://tns.tibco.com/bw/palette/internal/activityerror+71218a04-9b98-4bce-9b2b-fb12a49bc3f4\">\n" +
"            <import namespace=\"http://schemas.tibco.com/bw/pe/plugin/5.0/exceptions\"/>\n" +
"            <element name=\"ActivityErrorData\" type=\"tns:ActivityErrorDataType\"/>\n" +
"            <complexType name=\"ActivityErrorDataType\">\n" +
"                <choice>\n" +
"                    <element maxOccurs=\"1\" minOccurs=\"0\" ref=\"Q1:ActivityTimedOutException\"/>\n" +
"                </choice>\n" +
"            </complexType>\n" +
"        </schema>\n" +
"        <wsdl:definitions\n" +
"            targetNamespace=\"http://xmlns.example.com/20190518090735PLT\"\n" +
"            xmlns:plnk=\"http://docs.oasis-open.org/wsbpel/2.0/plnktype\"\n" +
"            xmlns:ptyp=\"http://www.example.org/Infocaja_CONSULTA_PRECONTRATACION_1.0.0\"\n" +
"            xmlns:tns=\"http://xmlns.example.com/20190518090735PLT\"\n" +
"            xmlns:vprop=\"http://docs.oasis-open.org/wsbpel/2.0/varprop\"\n" +
"            xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
"            <plnk:partnerLinkType name=\"partnerLinkType\">\n" +
"                <plnk:role name=\"use\" portType=\"ptyp:CONSULTA_PRECONTRATACIONPortType\"/>\n" +
"            </plnk:partnerLinkType>\n" +
"            <wsdl:import namespace=\"http://www.example.org/Infocaja_CONSULTA_PRECONTRATACION_1.0.0\"/>\n" +
"        </wsdl:definitions>\n" +
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Sat May 18 09:07:35 CEST 2019\" description=\"\"\n" +
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
"                                    x=\"25\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"83\"/>\n" +
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
"                                    <layoutConstraint x=\"168\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"83\"/>\n" +
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
"                                    <layoutConstraint x=\"306\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"83\"/>\n" +
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
"    <bpws:import importType=\"http://schemas.xmlsoap.org/wsdl/\" namespace=\"http://www.example.org/Infocaja_CONSULTA_PRECONTRATACION_1.0.0\"/>\n" +
"    <bpws:partnerLinks>\n" +
"        <bpws:partnerLink name=\"CONSULTA_PRECONTRATACIONPortType\"\n" +
"            partnerLinkType=\"ns0:partnerLinkType\" partnerRole=\"use\"\n" +
"            sca-bpel:ignore=\"true\"\n" +
"            sca-bpel:reference=\"CONSULTA_PRECONTRATACIONPortType\" sca-bpel:wiredByImpl=\"false\">\n" +
"            <tibex:ReferenceBinding>\n" +
"                <tibex:binding>\n" +
"                    <bwbinding:BWBaseBinding\n" +
"                        xmlns:Infocaja=\"http://www.example.org/Infocaja\"\n" +
"                        xmlns:axis2=\"http://xsd.tns.tibco.com/bw/models/binding/soap/axis2\"\n" +
"                        xmlns:bwbinding=\"http://tns.tibco.com/bw/model/core/bwbinding\"\n" +
"                        xmlns:pt=\"http://www.example.org/Infocaja_CONSULTA_PRECONTRATACION_1.0.0\"\n" +
"                        xmlns:sca=\"http://www.osoa.org/xmlns/sca/1.0\"\n" +
"                        xmlns:scact=\"http://xsd.tns.tibco.com/amf/models/sca/componentType\"\n" +
"                        xmlns:scaext=\"http://xsd.tns.tibco.com/amf/models/sca/extensions\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                        <referenceBinding\n" +
"                            name=\"CONSULTA_PRECONTRATACIONPortType\" xsi:type=\"scact:Reference\">\n" +
"                            <sca:interface.wsdl\n" +
"                                interface=\"http://www.example.org/Infocaja_CONSULTA_PRECONTRATACION_1.0.0#wsdl.interface(CONSULTA_PRECONTRATACIONPortType)\" scaext:wsdlLocation=\"../../../Service%20Descriptors/svtbw1pu.wsdl\"/>\n" +
"                            <scaext:binding\n" +
"                                locationURI=\"http://SVTBW1PU:5049/SOAPServiceBinding/cONSULTA_PRECONTRATACIONPortType/\"\n" +
"                                modelVersion=\"1.2.0\"\n" +
"                                name=\"SOAPReferenceBinding\"\n" +
"                                portName=\"CONSULTA_PRECONTRATACIONPortTypePort\"\n" +
"                                portTypeQName=\"pt:CONSULTA_PRECONTRATACIONPortType\"\n" +
"                                portTypeWSDLLocation=\"platform:/resource/t1.module/Service%20Descriptors/svtbw1pu.wsdl\"\n" +
"                                serviceBindingQName=\"pt:SOAPServiceBinding\"\n" +
"                                serviceQName=\"pt:CONSULTA_PRECONTRATACIONPortType\"\n" +
"                                soapVersion=\"1.2\"\n" +
"                                transportBindingType=\"HTTP\"\n" +
"                                wsdlLocation=\"platform:/resource/t1.module/Service%20Descriptors/svtbw1pu.wsdl\" xsi:type=\"axis2:SOAPReferenceBinding\">\n" +
"                                <bindingType style=\"DOCUMENT\"/>\n" +
"                                <operationConfiguration\n" +
"                                    action=\"CONSULTA_PRECONTRATACIONOperation\" operationName=\"CONSULTA_PRECONTRATACIONOperation\">\n" +
"                                    <bindingType style=\"DOCUMENT\"/>\n" +
"                                    <messageConfiguration\n" +
"                                    messageQName=\"pt:Input\" wsdlLocation=\"platform:/resource/t1.module/Service%20Descriptors/svtbw1pu.wsdl\">\n" +
"                                    <messagePartConfigurations\n" +
"                                    encoding=\"LITERAL\"\n" +
"                                    partElementName=\"Infocaja:MCD_CONSULTA_PRECONTRATACION_IN\"\n" +
"                                    partName=\"CONSULTA_PRECONTRATACION_IN\" xsi:type=\"axis2:BodyPartConfiguration\"/>\n" +
"                                    </messageConfiguration>\n" +
"                                    <messageConfiguration\n" +
"                                    messageDirection=\"OUTPUT\"\n" +
"                                    messageQName=\"pt:Output\" wsdlLocation=\"platform:/resource/t1.module/Service%20Descriptors/svtbw1pu.wsdl\">\n" +
"                                    <messagePartConfigurations\n" +
"                                    encoding=\"LITERAL\"\n" +
"                                    partElementName=\"Infocaja:MCD_CONSULTA_PRECONTRATACION_OUT\"\n" +
"                                    partName=\"CONSULTA_PRECONTRATACION_OUT\" xsi:type=\"axis2:BodyPartConfiguration\"/>\n" +
"                                    </messageConfiguration>\n" +
"                                    <context>\n" +
"                                    <inputMapping/>\n" +
"                                    <outputMapping direction=\"OUTPUT\"/>\n" +
"                                    </context>\n" +
"                                </operationConfiguration>\n" +
"                                <outboundConfiguration xsi:type=\"axis2:HttpTransportConfiguration\"/>\n" +
"                            </scaext:binding>\n" +
"                            <scact:bindingAdjunct bindingName=\"SOAPReferenceBinding\"/>\n" +
"                        </referenceBinding>\n" +
"                    </bwbinding:BWBaseBinding>\n" +
"                </tibex:binding>\n" +
"            </tibex:ReferenceBinding>\n" +
"        </bpws:partnerLink>\n" +
"    </bpws:partnerLinks>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns1:TimerOutputSchema\" name=\"Timer\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"jdbcProperty\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc}JdbcDataSource\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns2:jdbcQueryActivityInput\"\n" +
"            name=\"JDBCQuery-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns3:resultSet\" name=\"JDBCQuery\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns4:ActivityErrorData\"\n" +
"            name=\"_error_JDBCQuery\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns:ErrorReport\" name=\"_error\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns5:Input\" name=\"Invoke-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable messageType=\"ns5:Output\" name=\"Invoke\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns6:ActivityErrorData\"\n" +
"            name=\"_error_Invoke\" sca-bpel:internal=\"true\"/>\n" +
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
"                <bpws:link name=\"TimerToJDBCQuery\" tibex:linkType=\"SUCCESS\"/>\n" +
"                <bpws:link name=\"JDBCQueryToInvoke\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\" name=\"Timer\"\n" +
"                    tibex:xpdlId=\"683d34e4-4167-49df-af51-37271043b655\"\n" +
"                    variable=\"Timer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"TimerToJDBCQuery\"/>\n" +
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
"                                    endTime=\"2019-05-18T09:08:17.986+0200\"\n" +
"                                    intervalUnit=\"Second\"\n" +
"                                    runOnce=\"true\"\n" +
"                                    startTime=\"2019-05-18T09:08:17.986+0200\"\n" +
"                                    timeInterval=\"1\" xsi:type=\"generalactivities:Timer\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:activityExtension inputVariable=\"JDBCQuery-input\"\n" +
"                    name=\"JDBCQuery\" outputVariable=\"JDBCQuery\"\n" +
"                    tibex:xpdlId=\"ee5ab235-fb0c-45ff-be10-5045bcc01322\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"TimerToJDBCQuery\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"JDBCQueryToInvoke\"/>\n" +
"                    </bpws:sources>\n" +
"                    <tibex:inputBindings>\n" +
"                        <tibex:inputBinding\n" +
"                            expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?> &lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/namespaces/tnt/plugins/jdbc+ee5ab235-fb0c-45ff-be10-5045bcc01322+input&quot; version=&quot;2.0&quot;> &lt;xsl:template name=&quot;JDBCQuery-input&quot; match=&quot;/&quot;> &lt;tns:jdbcQueryActivityInput/>&lt;/xsl:template> &lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                    </tibex:inputBindings>\n" +
"                    <tibex:config>\n" +
"                        <bwext:BWActivity\n" +
"                            activityTypeID=\"bw.jdbc.JDBCQuery\"\n" +
"                            xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                            xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                            xmlns:jdbcPalette=\"http://ns.tibco.com/bw/palette/jdbc\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                            <activityConfig>\n" +
"                                <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/jdbc#//JDBCQueryActivity\"/>\n" +
"                                    <value maxRows=\"100\"\n" +
"                                    sharedResourceProperty=\"jdbcProperty\"\n" +
"                                    timeout=\"10\" xsi:type=\"jdbcPalette:JDBCQueryActivity\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:config>\n" +
"                </tibex:activityExtension>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:invoke inputVariable=\"Invoke-input\" name=\"Invoke\"\n" +
"                operation=\"CONSULTA_PRECONTRATACIONOperation\"\n" +
"                outputVariable=\"Invoke\"\n" +
"                partnerLink=\"CONSULTA_PRECONTRATACIONPortType\"\n" +
"                portType=\"ns5:CONSULTA_PRECONTRATACIONPortType\" tibex:xpdlId=\"71218a04-9b98-4bce-9b2b-fb12a49bc3f4\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"JDBCQueryToInvoke\"/>\n" +
"                </bpws:targets>\n" +
"            </bpws:invoke>\n" +
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
        ThreadpoolUsageInJDBCActivitiesCheck instance = new ThreadpoolUsageInJDBCActivitiesCheck();
        ThreadpoolUsageInJDBCActivitiesCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());     
        spyInstance.validate(source);                   
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }
}
