package com.tibco.sonar.plugins.bw5.check.process;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DeadProcessCheckForStarterProcessTest extends TestCase {


    public void testValidate() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:ns=\"http://www.tibco.com/pe/WriteToLogActivitySchema\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns1=\"http://www.tibco.com/pe/DeployedVarsType\">\n" +
                "    <pd:name>Process Definition.process</pd:name>\n" +
                "    <pd:startName>asdasd</pd:startName>\n" +
                "    <pd:startType/>\n" +
                "    <pd:startX>166</pd:startX>\n" +
                "    <pd:startY>122</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1728978533713</pd:targetNamespace>\n" +
                "    <pd:activity name=\"Log\">\n" +
                "        <pd:type>com.tibco.pe.core.WriteToLogActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.log</pd:resourceType>\n" +
                "        <pd:x>300</pd:x>\n" +
                "        <pd:y>59</pd:y>\n" +
                "        <config>\n" +
                "            <role>User</role>\n" +
                "        </config>\n" +
                "        <pd:inputBindings>\n" +
                "            <ns:ActivityInput/>\n" +
                "        </pd:inputBindings>\n" +
                "    </pd:activity>\n" +
                "    <pd:activity name=\"JMS Queue Requestor\">\n" +
                "        <pd:type>com.tibco.plugin.jms.JMSQueueRequestReplyActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.JMSQueueRequestReplyActivity</pd:resourceType>\n" +
                "        <pd:x>217</pd:x>\n" +
                "        <pd:y>237</pd:y>\n" +
                "        <config>\n" +
                "            <PermittedMessageType>Text</PermittedMessageType>\n" +
                "            <SessionAttributes>\n" +
                "                <transacted>false</transacted>\n" +
                "                <acknowledgeMode>1</acknowledgeMode>\n" +
                "                <maxSessions>1</maxSessions>\n" +
                "                <destination>%%RvaHost%%</destination>\n" +
                "            </SessionAttributes>\n" +
                "            <ConfigurableHeaders>\n" +
                "                <JMSDeliveryMode>PERSISTENT</JMSDeliveryMode>\n" +
                "                <JMSExpiration>0</JMSExpiration>\n" +
                "                <JMSPriority>4</JMSPriority>\n" +
                "            </ConfigurableHeaders>\n" +
                "            <DeliveryDelay>0</DeliveryDelay>\n" +
                "        </config>\n" +
                "        <pd:inputBindings>\n" +
                "            <ns2:ActivityInput>\n" +
                "                <JMSExpiration>\n" +
                "                    <xsl:value-of select=\"$_globalVariables/ns1:GlobalVariables/NewItem\"/>\n" +
                "                </JMSExpiration>\n" +
                "                <requestTimeout>\n" +
                "                    <xsl:value-of select=\"$_globalVariables/ns1:GlobalVariables/NewItem\"/>\n" +
                "                </requestTimeout>\n" +
                "                <Body>\n" +
                "                    <xsl:value-of select=\"&quot;Test&quot;\"/>\n" +
                "                </Body>\n" +
                "            </ns2:ActivityInput>\n" +
                "        </pd:inputBindings>\n" +
                "    </pd:activity>\n" +
                "    <pd:activity name=\"JMS Queue Sender\">\n" +
                "        <pd:type>com.tibco.plugin.jms.JMSQueueSendActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.JMSQueueSendActivity</pd:resourceType>\n" +
                "        <pd:x>450</pd:x>\n" +
                "        <pd:y>277</pd:y>\n" +
                "        <config>\n" +
                "            <PermittedMessageType>Text</PermittedMessageType>\n" +
                "            <SessionAttributes>\n" +
                "                <transacted>false</transacted>\n" +
                "                <acknowledgeMode>1</acknowledgeMode>\n" +
                "                <maxSessions>1</maxSessions>\n" +
                "                <destination>%%RemoteRvDaemon%%</destination>\n" +
                "            </SessionAttributes>\n" +
                "            <ConfigurableHeaders>\n" +
                "                <JMSDeliveryMode>PERSISTENT</JMSDeliveryMode>\n" +
                "                <JMSExpiration>0</JMSExpiration>\n" +
                "                <JMSPriority>4</JMSPriority>\n" +
                "            </ConfigurableHeaders>\n" +
                "            <DeliveryDelay>0</DeliveryDelay>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:activity name=\"Catch\">\n" +
                "        <pd:type>com.tibco.pe.core.CatchActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.catch</pd:resourceType>\n" +
                "        <pd:x>149</pd:x>\n" +
                "        <pd:y>379</pd:y>\n" +
                "        <pd:handler>true</pd:handler>\n" +
                "        <config>\n" +
                "            <catchAll>true</catchAll>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:activity name=\"Invoke REST API\">\n" +
                "        <pd:type>com.tibco.plugin.json.activities.RestActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.RestActivity</pd:resourceType>\n" +
                "        <pd:x>594</pd:x>\n" +
                "        <pd:y>168</pd:y>\n" +
                "        <config>\n" +
                "            <enableProtocolUI>None</enableProtocolUI>\n" +
                "            <restMethodUI>GET</restMethodUI>\n" +
                "            <restResponseType>JSON</restResponseType>\n" +
                "            <authChoiceUI>No Authentication</authChoiceUI>\n" +
                "            <WADLPaths/>\n" +
                "            <WADLBaseURL/>\n" +
                "            <restMethodIDUI/>\n" +
                "            <OAuth2ParamPosition>Header</OAuth2ParamPosition>\n" +
                "            <restInputReferNode>\n" +
                "                <xsd:element name=\"Parameters\">\n" +
                "                    <xsd:complexType>\n" +
                "                        <xsd:sequence>\n" +
                "                            <xsd:element name=\"Query\" minOccurs=\"0\">\n" +
                "                                <xsd:complexType>\n" +
                "                                    <xsd:sequence>\n" +
                "                                        <xsd:element name=\"param\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                    </xsd:sequence>\n" +
                "                                </xsd:complexType>\n" +
                "                            </xsd:element>\n" +
                "                            <xsd:element name=\"Header\" minOccurs=\"0\">\n" +
                "                                <xsd:complexType>\n" +
                "                                    <xsd:sequence>\n" +
                "                                        <xsd:element name=\"param\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                    </xsd:sequence>\n" +
                "                                </xsd:complexType>\n" +
                "                            </xsd:element>\n" +
                "                            <xsd:element name=\"Body\" minOccurs=\"0\">\n" +
                "                                <xsd:complexType>\n" +
                "                                    <xsd:sequence>\n" +
                "                                        <xsd:element name=\"Form\" minOccurs=\"0\">\n" +
                "                                            <xsd:complexType>\n" +
                "                                                <xsd:sequence>\n" +
                "                                                    <xsd:element name=\"param\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                </xsd:sequence>\n" +
                "                                            </xsd:complexType>\n" +
                "                                        </xsd:element>\n" +
                "                                        <xsd:element name=\"Text\" minOccurs=\"0\">\n" +
                "                                            <xsd:complexType>\n" +
                "                                                <xsd:sequence>\n" +
                "                                                    <xsd:element name=\"type\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"content\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                </xsd:sequence>\n" +
                "                                            </xsd:complexType>\n" +
                "                                        </xsd:element>\n" +
                "                                        <xsd:element name=\"Binary\" minOccurs=\"0\">\n" +
                "                                            <xsd:complexType>\n" +
                "                                                <xsd:sequence>\n" +
                "                                                    <xsd:element name=\"type\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"content\" type=\"xsd:base64Binary\" minOccurs=\"0\"/>\n" +
                "                                                </xsd:sequence>\n" +
                "                                            </xsd:complexType>\n" +
                "                                        </xsd:element>\n" +
                "                                        <xsd:element name=\"Multipart\" minOccurs=\"0\" maxOccurs=\"unbounded\">\n" +
                "                                            <xsd:complexType>\n" +
                "                                                <xsd:sequence>\n" +
                "                                                    <xsd:element name=\"content-disposition\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"name\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"filename\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"content-type\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"content-id\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                                                    <xsd:element name=\"content\" minOccurs=\"0\">\n" +
                "                                                        <xsd:complexType>\n" +
                "                                                            <xsd:choice>\n" +
                "                                                                <xsd:element name=\"binary\" type=\"xsd:base64Binary\"/>\n" +
                "                                                                <xsd:element name=\"text\" type=\"xsd:string\"/>\n" +
                "                                                                <xsd:element name=\"fileName\" type=\"xsd:string\"/>\n" +
                "                                                            </xsd:choice>\n" +
                "                                                        </xsd:complexType>\n" +
                "                                                    </xsd:element>\n" +
                "                                                </xsd:sequence>\n" +
                "                                            </xsd:complexType>\n" +
                "                                        </xsd:element>\n" +
                "                                    </xsd:sequence>\n" +
                "                                </xsd:complexType>\n" +
                "                            </xsd:element>\n" +
                "                        </xsd:sequence>\n" +
                "                    </xsd:complexType>\n" +
                "                </xsd:element>\n" +
                "            </restInputReferNode>\n" +
                "            <restOutputReferNode>\n" +
                "                <xsd:element name=\"header\">\n" +
                "                    <xsd:complexType>\n" +
                "                        <xsd:sequence>\n" +
                "                            <xsd:element name=\"Server\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Location\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Allow\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Date\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Set-Cookie\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Pragma\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Content-Type\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Content-Length\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Content-Encoding\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Content-Range\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                            <xsd:element name=\"Last-Modifie\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
                "                        </xsd:sequence>\n" +
                "                    </xsd:complexType>\n" +
                "                </xsd:element>\n" +
                "            </restOutputReferNode>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>asdasd</pd:from>\n" +
                "        <pd:to>Log</pd:to>\n" +
                "        <pd:xpathDescription/>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>xpath</pd:conditionType>\n" +
                "        <pd:xpath>1 = 1</pd:xpath>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Log</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>asdasd</pd:from>\n" +
                "        <pd:to>JMS Queue Requestor</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>asdasd</pd:from>\n" +
                "        <pd:to>JMS Queue Sender</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>JMS Queue Sender</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>JMS Queue Requestor</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        ProcessSource source1 = Mockito.spy(source);

        when(source1.getBaseDir()).thenReturn(new File(System.getProperty("user.dir")+ "/src/test/resources/bw/bw5/SonarSamples"));

        DeadProcessCheckForStarterProcess instance = new DeadProcessCheckForStarterProcess();
        DeadProcessCheckForStarterProcess spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source1);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString());
    }
}