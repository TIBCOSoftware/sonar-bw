package com.tibco.sonar.plugins.bw5.check.activity.jms.queue.sender;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class HardCodedDestinationCheckTest extends TestCase {

    public void testValidate() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:ns=\"http://www.tibco.com/pe/WriteToLogActivitySchema\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns2=\"http://www.tibco.com/namespaces/tnt/plugins/jms\">\n" +
                "    <pd:name>Process Definition.process</pd:name>\n" +
                "    <pd:startName>Start</pd:startName>\n" +
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
                "        <pd:y>236</pd:y>\n" +
                "        <config>\n" +
                "            <PermittedMessageType>Text</PermittedMessageType>\n" +
                "            <SessionAttributes>\n" +
                "                <transacted>false</transacted>\n" +
                "                <acknowledgeMode>1</acknowledgeMode>\n" +
                "                <maxSessions>1</maxSessions>\n" +
                "                <destination>hardcoded</destination>\n" +
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
                "                <requestTimeout>\n" +
                "                    <xsl:value-of select=\"60\"/>\n" +
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
                "                <destination>hardcoded</destination>\n" +
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
                "    <pd:transition>\n" +
                "        <pd:from>Start</pd:from>\n" +
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
                "        <pd:from>Start</pd:from>\n" +
                "        <pd:to>JMS Queue Requestor</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Start</pd:from>\n" +
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
        HardCodedDestinationCheck instance = new HardCodedDestinationCheck();
        HardCodedDestinationCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance, times(1)).reportIssueOnFile(anyString());
    }

}