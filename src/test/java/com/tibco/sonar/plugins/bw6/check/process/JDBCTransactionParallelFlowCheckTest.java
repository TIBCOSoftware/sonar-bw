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
public class JDBCTransactionParallelFlowCheckTest {

    private static ProcessSource source;
    
    public JDBCTransactionParallelFlowCheckTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    
   
           
        source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<bpws:process exitOnStandardFault=\"no\" name=\"test3.module.Process1\"\n" +
"    suppressJoinFailure=\"yes\"\n" +
"    targetNamespace=\"http://xmlns.example.com/20190520113719\"\n" +
"    xmlns:bpws=\"http://docs.oasis-open.org/wsbpel/2.0/process/executable\"\n" +
"    xmlns:info=\"http://www.tibco.com/bw/process/info\"\n" +
"    xmlns:ns=\"http://www.tibco.com/pe/EngineTypes\"\n" +
"    xmlns:ns0=\"http://tns.tibco.com/bw/activity/timer/xsd/output\"\n" +
"    xmlns:ns1=\"http://www.tibco.com/pe/WriteToLogActivitySchema\"\n" +
"    xmlns:ns2=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+input\"\n" +
"    xmlns:ns3=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+output\"\n" +
"    xmlns:ns4=\"http://tns.tibco.com/bw/palette/internal/activityerror+bw.jdbc.JDBCQuery\"\n" +
"    xmlns:ns5=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+input\"\n" +
"    xmlns:ns6=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+output\"\n" +
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
"        <schema\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+input\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+input\">\n" +
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
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+output\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+output\">\n" +
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
"        <schema\n" +
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+input\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+input\">\n" +
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
"            targetNamespace=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+output\"\n" +
"            xmlns=\"http://www.w3.org/2001/XMLSchema\" xmlns:tns=\"http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+output\">\n" +
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
"    </tibex:Types>\n" +
"    <tibex:ProcessInfo callable=\"false\" createdBy=\"avazquez\"\n" +
"        createdOn=\"Mon May 20 11:37:19 CEST 2019\" description=\"\"\n" +
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
"                                    x=\"-31\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"140\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"4005\">\n" +
"                                    <children type=\"3033\">\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1/@activities.0\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"40\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"3007\">\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1/@activities.2\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    x=\"11\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"143\"/>\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1/@activities.3\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    x=\"112\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"61\"/>\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1/@activities.4\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    x=\"126\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"229\"/>\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1/@activities.5\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    x=\"229\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"138\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:DrawerStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <children type=\"3034\">\n" +
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
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1/@activities.1\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"40\"/>\n" +
"                                    </children>\n" +
"                                    <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                    <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                                    </children>\n" +
"                                    <styles fontName=\"Segoe UI\"\n" +
"                                    lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                                    <styles\n" +
"                                    backgroundColor=\"16777215\"\n" +
"                                    gradientEndColor=\"50431\"\n" +
"                                    gradientStartColor=\"16777215\" xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                                    <element href=\"//0/@process/@activity/@activity/@activities.1\"/>\n" +
"                                    <layoutConstraint\n" +
"                                    height=\"333\" width=\"431\"\n" +
"                                    x=\"95\"\n" +
"                                    xsi:type=\"notation:Bounds\" y=\"5\"/>\n" +
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
"                                    <layoutConstraint height=\"388\"\n" +
"                                    width=\"610\" xsi:type=\"notation:Bounds\"/>\n" +
"                                </children>\n" +
"                                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                            </children>\n" +
"                            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                            <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                            <element href=\"//0/@process/@activity\"/>\n" +
"                            <layoutConstraint height=\"388\" width=\"613\" xsi:type=\"notation:Bounds\"/>\n" +
"                        </children>\n" +
"                        <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                        <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                        <element href=\"//0/@process/@activity\"/>\n" +
"                    </children>\n" +
"                    <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:BackgroundStyle\"/>\n" +
"                    <styles xsi:type=\"bwnotation:ResizingStyle\"/>\n" +
"                    <element href=\"//0/@process/@activity\"/>\n" +
"                    <layoutConstraint height=\"412\" width=\"613\" x=\"1\"\n" +
"                        xsi:type=\"notation:Bounds\" y=\"1\"/>\n" +
"                </children>\n" +
"                <styles xsi:type=\"notation:SortingStyle\"/>\n" +
"                <styles xsi:type=\"notation:FilteringStyle\"/>\n" +
"                <element href=\"//0/@process\"/>\n" +
"            </children>\n" +
"            <styles fontName=\"Segoe UI\" lineColor=\"0\" xsi:type=\"notation:ShapeStyle\"/>\n" +
"            <element href=\"//0/@process\"/>\n" +
"            <layoutConstraint height=\"464\" width=\"955\" xsi:type=\"notation:Bounds\"/>\n" +
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
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.1\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.1/@links/@children.0\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.2\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.1/@links/@children.1\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.2\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.1/@links/@children.3\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.1\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.3\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.1/@links/@children.2\"/>\n" +
"            <bendpoints points=\"[25, 16, -92, -61]$[93, 61, -24, -16]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.0/@children.0\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.0\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.1/@links/@children.4\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"        <edges\n" +
"            source=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.1/@children.3\"\n" +
"            target=\"//@children.0/@children.4/@children.0/@children.0/@children.0/@children.0/@children.0/@children.0/@children.1/@children.2/@children.0\" type=\"4006\">\n" +
"            <children type=\"6002\">\n" +
"                <layoutConstraint xsi:type=\"notation:Location\" y=\"40\"/>\n" +
"            </children>\n" +
"            <styles lineColor=\"0\" xsi:type=\"notation:ConnectorStyle\"/>\n" +
"            <styles fontName=\"Segoe UI\" xsi:type=\"notation:FontStyle\"/>\n" +
"            <element href=\"//0/@process/@activity/@activity/@activities.1/@links/@children.5\"/>\n" +
"            <bendpoints points=\"[0, 0, 0, 0]$[0, 0, 0, 0]\" xsi:type=\"notation:RelativeBendpoints\"/>\n" +
"        </edges>\n" +
"    </notation:Diagram>\n" +
"    <bpws:variables>\n" +
"        <bpws:variable element=\"ns:ProcessContext\"\n" +
"            name=\"_processContext\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns0:TimerOutputSchema\" name=\"Timer\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns1:ActivityInput\" name=\"Log-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable name=\"jdbcProperty\" sca-bpel:hotUpdate=\"false\"\n" +
"            sca-bpel:privateProperty=\"true\" sca-bpel:property=\"yes\"\n" +
"            sca-bpel:sharedResourceType=\"{http://xsd.tns.tibco.com/amf/models/sharedresource/jdbc}JdbcDataSource\" type=\"xsd:string\"/>\n" +
"        <bpws:variable element=\"ns2:jdbcQueryActivityInput\"\n" +
"            name=\"JDBCQuery-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns3:resultSet\" name=\"JDBCQuery\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns4:ActivityErrorData\"\n" +
"            name=\"_error_JDBCQuery\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns:ErrorReport\" name=\"_error\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns5:jdbcQueryActivityInput\"\n" +
"            name=\"JDBCQuery1-input\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns6:resultSet\" name=\"JDBCQuery1\" sca-bpel:internal=\"true\"/>\n" +
"        <bpws:variable element=\"ns4:ActivityErrorData\"\n" +
"            name=\"_error_JDBCQuery1\" sca-bpel:internal=\"true\"/>\n" +
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
"                <bpws:link name=\"TimerToLocalTransaction\" tibex:linkType=\"SUCCESS\"/>\n" +
"            </bpws:links>\n" +
"            <bpws:extensionActivity>\n" +
"                <tibex:receiveEvent createInstance=\"yes\"\n" +
"                    eventTimeout=\"60\" name=\"Timer\"\n" +
"                    tibex:xpdlId=\"4d0e5d33-2f44-418a-8500-08273e738770\"\n" +
"                    variable=\"Timer\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"TimerToLocalTransaction\"/>\n" +
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
"                                    endTime=\"2019-05-20T11:37:26.380+0200\"\n" +
"                                    intervalUnit=\"Second\"\n" +
"                                    runOnce=\"true\"\n" +
"                                    startTime=\"2019-05-20T11:37:26.380+0200\"\n" +
"                                    timeInterval=\"1\" xsi:type=\"generalactivities:Timer\"/>\n" +
"                                </properties>\n" +
"                            </activityConfig>\n" +
"                        </bwext:BWActivity>\n" +
"                    </tibex:eventSource>\n" +
"                </tibex:receiveEvent>\n" +
"            </bpws:extensionActivity>\n" +
"            <bpws:flow name=\"LocalTransaction\" tibex:group=\"localTX\"\n" +
"                tibex:txGroup=\"none\" tibex:xpdlId=\"27f4de9b-88ec-4dde-951a-03c55fe3d9df\">\n" +
"                <bpws:targets>\n" +
"                    <bpws:target linkName=\"TimerToLocalTransaction\"/>\n" +
"                </bpws:targets>\n" +
"                <bpws:links>\n" +
"                    <bpws:link name=\"LogToJDBCQuery\" tibex:linkType=\"SUCCESS\"/>\n" +
"                    <bpws:link name=\"LogToJDBCQuery1\" tibex:linkType=\"SUCCESS\"/>\n" +
"                    <bpws:link name=\"JDBCQueryToEmpty\" tibex:linkType=\"SUCCESS\"/>\n" +
"                    <bpws:link name=\"JDBCQuery1ToEmpty\" tibex:linkType=\"SUCCESS\"/>\n" +
"                    <bpws:link name=\"GroupStartToLog\" tibex:linkType=\"SUCCESS\"/>\n" +
"                    <bpws:link name=\"EmptyToGroupEnd\" tibex:linkType=\"SUCCESS\"/>\n" +
"                </bpws:links>\n" +
"                <bpws:empty name=\"GroupStart\" tibex:group=\"groupStart\" tibex:xpdlId=\"1df640b7-acd8-49fd-9366-699912b40c75\">\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"GroupStartToLog\"/>\n" +
"                    </bpws:sources>\n" +
"                </bpws:empty>\n" +
"                <bpws:empty name=\"GroupEnd\" tibex:group=\"groupEnd\" tibex:xpdlId=\"94116f92-b770-4192-b22a-dcfda895b77b\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"EmptyToGroupEnd\"/>\n" +
"                    </bpws:targets>\n" +
"                </bpws:empty>\n" +
"                <bpws:extensionActivity>\n" +
"                    <tibex:activityExtension inputVariable=\"Log-input\"\n" +
"                        name=\"Log\"\n" +
"                        tibex:xpdlId=\"1d14b02d-8630-416f-8bc7-8690f3e17a4e\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                        <bpws:targets>\n" +
"                            <bpws:target linkName=\"GroupStartToLog\"/>\n" +
"                        </bpws:targets>\n" +
"                        <bpws:sources>\n" +
"                            <bpws:source linkName=\"LogToJDBCQuery\"/>\n" +
"                            <bpws:source linkName=\"LogToJDBCQuery1\"/>\n" +
"                        </bpws:sources>\n" +
"                        <tibex:inputBindings>\n" +
"                            <tibex:inputBinding\n" +
"                                expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?> &lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/pe/WriteToLogActivitySchema&quot; version=&quot;2.0&quot;> &lt;xsl:template name=&quot;Log-input&quot; match=&quot;/&quot;> &lt;tns:ActivityInput/>&lt;/xsl:template> &lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                        </tibex:inputBindings>\n" +
"                        <tibex:config>\n" +
"                            <bwext:BWActivity\n" +
"                                activityTypeID=\"bw.generalactivities.log\"\n" +
"                                version=\"6.0.0.001\"\n" +
"                                xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                                xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                                xmlns:generalactivities=\"http://ns.tibco.com/bw/palette/generalactivities\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                                <activityConfig>\n" +
"                                    <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/generalactivities#//Log\"/>\n" +
"                                    <value controlBy=\"Application\"\n" +
"                                    role=\"Info\"\n" +
"                                    suppressJobInfo=\"true\" xsi:type=\"generalactivities:Log\"/>\n" +
"                                    </properties>\n" +
"                                </activityConfig>\n" +
"                            </bwext:BWActivity>\n" +
"                        </tibex:config>\n" +
"                    </tibex:activityExtension>\n" +
"                </bpws:extensionActivity>\n" +
"                <bpws:extensionActivity>\n" +
"                    <tibex:activityExtension\n" +
"                        inputVariable=\"JDBCQuery-input\" name=\"JDBCQuery\"\n" +
"                        outputVariable=\"JDBCQuery\"\n" +
"                        tibex:xpdlId=\"ffe5e0f0-01cb-4b88-8673-8a3012881722\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                        <bpws:targets>\n" +
"                            <bpws:target linkName=\"LogToJDBCQuery\"/>\n" +
"                        </bpws:targets>\n" +
"                        <bpws:sources>\n" +
"                            <bpws:source linkName=\"JDBCQueryToEmpty\"/>\n" +
"                        </bpws:sources>\n" +
"                        <tibex:inputBindings>\n" +
"                            <tibex:inputBinding\n" +
"                                expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?> &lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/namespaces/tnt/plugins/jdbc+ffe5e0f0-01cb-4b88-8673-8a3012881722+input&quot; version=&quot;2.0&quot;> &lt;xsl:template name=&quot;JDBCQuery-input&quot; match=&quot;/&quot;> &lt;tns:jdbcQueryActivityInput/>&lt;/xsl:template> &lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                        </tibex:inputBindings>\n" +
"                        <tibex:config>\n" +
"                            <bwext:BWActivity\n" +
"                                activityTypeID=\"bw.jdbc.JDBCQuery\"\n" +
"                                xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                                xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                                xmlns:jdbcPalette=\"http://ns.tibco.com/bw/palette/jdbc\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                                <activityConfig>\n" +
"                                    <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/jdbc#//JDBCQueryActivity\"/>\n" +
"                                    <value maxRows=\"100\"\n" +
"                                    sharedResourceProperty=\"jdbcProperty\"\n" +
"                                    timeout=\"10\" xsi:type=\"jdbcPalette:JDBCQueryActivity\"/>\n" +
"                                    </properties>\n" +
"                                </activityConfig>\n" +
"                            </bwext:BWActivity>\n" +
"                        </tibex:config>\n" +
"                    </tibex:activityExtension>\n" +
"                </bpws:extensionActivity>\n" +
"                <bpws:extensionActivity>\n" +
"                    <tibex:activityExtension\n" +
"                        inputVariable=\"JDBCQuery1-input\"\n" +
"                        name=\"JDBCQuery1\" outputVariable=\"JDBCQuery1\"\n" +
"                        tibex:xpdlId=\"33a12921-023d-46c5-9ddf-44835615a9ff\" xmlns:tibex=\"http://www.tibco.com/bpel/2007/extensions\">\n" +
"                        <bpws:targets>\n" +
"                            <bpws:target linkName=\"LogToJDBCQuery1\"/>\n" +
"                        </bpws:targets>\n" +
"                        <bpws:sources>\n" +
"                            <bpws:source linkName=\"JDBCQuery1ToEmpty\"/>\n" +
"                        </bpws:sources>\n" +
"                        <tibex:inputBindings>\n" +
"                            <tibex:inputBinding\n" +
"                                expression=\"&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?> &lt;xsl:stylesheet xmlns:xsl=&quot;http://www.w3.org/1999/XSL/Transform&quot; xmlns:tns=&quot;http://www.tibco.com/namespaces/tnt/plugins/jdbc+33a12921-023d-46c5-9ddf-44835615a9ff+input&quot; version=&quot;2.0&quot;> &lt;xsl:template name=&quot;JDBCQuery1-input&quot; match=&quot;/&quot;> &lt;tns:jdbcQueryActivityInput/>&lt;/xsl:template> &lt;/xsl:stylesheet>\" expressionLanguage=\"urn:oasis:names:tc:wsbpel:2.0:sublang:xslt1.0\"/>\n" +
"                        </tibex:inputBindings>\n" +
"                        <tibex:config>\n" +
"                            <bwext:BWActivity\n" +
"                                activityTypeID=\"bw.jdbc.JDBCQuery\"\n" +
"                                xmlns:activityconfig=\"http://tns.tibco.com/bw/model/activityconfig\"\n" +
"                                xmlns:bwext=\"http://tns.tibco.com/bw/model/core/bwext\"\n" +
"                                xmlns:jdbcPalette=\"http://ns.tibco.com/bw/palette/jdbc\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
"                                <activityConfig>\n" +
"                                    <properties name=\"config\" xsi:type=\"activityconfig:EMFProperty\">\n" +
"                                    <type href=\"http://ns.tibco.com/bw/palette/jdbc#//JDBCQueryActivity\"/>\n" +
"                                    <value maxRows=\"100\"\n" +
"                                    sharedResourceProperty=\"jdbcProperty\"\n" +
"                                    timeout=\"10\" xsi:type=\"jdbcPalette:JDBCQueryActivity\"/>\n" +
"                                    </properties>\n" +
"                                </activityConfig>\n" +
"                            </bwext:BWActivity>\n" +
"                        </tibex:config>\n" +
"                    </tibex:activityExtension>\n" +
"                </bpws:extensionActivity>\n" +
"                <bpws:empty name=\"Empty\" tibex:xpdlId=\"cb5bfcf7-d2c2-4003-94d7-f3d3ce59dc7e\">\n" +
"                    <bpws:targets>\n" +
"                        <bpws:target linkName=\"JDBCQuery1ToEmpty\"/>\n" +
"                        <bpws:target linkName=\"JDBCQueryToEmpty\"/>\n" +
"                    </bpws:targets>\n" +
"                    <bpws:sources>\n" +
"                        <bpws:source linkName=\"EmptyToGroupEnd\"/>\n" +
"                    </bpws:sources>\n" +
"                </bpws:empty>\n" +
"            </bpws:flow>\n" +
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
        JDBCTransactionParallelFlowCheck instance = new JDBCTransactionParallelFlowCheck();
        JDBCTransactionParallelFlowCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());        
        spyInstance.validate(source);        
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
        
    }
    
}
