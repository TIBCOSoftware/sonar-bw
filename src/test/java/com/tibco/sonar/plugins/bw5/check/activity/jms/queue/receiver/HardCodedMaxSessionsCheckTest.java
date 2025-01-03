package com.tibco.sonar.plugins.bw5.check.activity.jms.queue.receiver;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class HardCodedMaxSessionsCheckTest extends TestCase {

    public void testValidate() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:ns=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns2=\"http://www.tibco.com/pe/WriteToLogActivitySchema\">\n" +
                "    <pd:name>JMSReceiver.process</pd:name>\n" +
                "    <pd:startName>JMS Queue Receiver</pd:startName>\n" +
                "    <pd:startX>0</pd:startX>\n" +
                "    <pd:startY>0</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:starter name=\"JMS Queue Receiver\">\n" +
                "        <pd:type>com.tibco.plugin.jms.JMSQueueEventSource</pd:type>\n" +
                "        <pd:resourceType>ae.activities.JMSQueueEventSource</pd:resourceType>\n" +
                "        <pd:x>80</pd:x>\n" +
                "        <pd:y>68</pd:y>\n" +
                "        <config>\n" +
                "            <PermittedMessageType>Text</PermittedMessageType>\n" +
                "            <SessionAttributes>\n" +
                "                <transacted>false</transacted>\n" +
                "                <acknowledgeMode>1</acknowledgeMode>\n" +
                "                <maxSessions>1</maxSessions>\n" +
                "                <destination>sample.req</destination>\n" +
                "            </SessionAttributes>\n" +
                "            <ConfigurableHeaders>\n" +
                "                <JMSDeliveryMode>PERSISTENT</JMSDeliveryMode>\n" +
                "                <JMSExpiration>0</JMSExpiration>\n" +
                "                <JMSPriority>4</JMSPriority>\n" +
                "            </ConfigurableHeaders>\n" +
                "            <ConnectionReference>/JMS Connection.sharedjmscon</ConnectionReference>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:starter>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1727779833037</pd:targetNamespace>\n" +
                "    <pd:activity name=\"Log\">\n" +
                "        <pd:type>com.tibco.pe.core.WriteToLogActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.log</pd:resourceType>\n" +
                "        <pd:x>224</pd:x>\n" +
                "        <pd:y>64</pd:y>\n" +
                "        <config>\n" +
                "            <role>User</role>\n" +
                "        </config>\n" +
                "        <pd:inputBindings>\n" +
                "            <ns2:ActivityInput>\n" +
                "                <message>\n" +
                "                    <xsl:value-of select=\"$JMS-Queue-Receiver/ns:ActivityOutput/Body\"/>\n" +
                "                </message>\n" +
                "            </ns2:ActivityInput>\n" +
                "        </pd:inputBindings>\n" +
                "    </pd:activity>\n" +
                "    <pd:activity name=\"Reply to JMS Message\">\n" +
                "        <pd:type>com.tibco.plugin.jms.JMSReplyActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.JMSReplyActivity</pd:resourceType>\n" +
                "        <pd:x>354</pd:x>\n" +
                "        <pd:y>57</pd:y>\n" +
                "        <config>\n" +
                "            <PermittedMessageType>Text</PermittedMessageType>\n" +
                "            <SessionAttributes>\n" +
                "                <transacted>false</transacted>\n" +
                "                <acknowledgeMode>1</acknowledgeMode>\n" +
                "                <maxSessions>1</maxSessions>\n" +
                "            </SessionAttributes>\n" +
                "            <ConfigurableHeaders>\n" +
                "                <JMSDeliveryMode>PERSISTENT</JMSDeliveryMode>\n" +
                "                <JMSExpiration>0</JMSExpiration>\n" +
                "                <JMSPriority>4</JMSPriority>\n" +
                "                <EventSource>JMS Queue Receiver</EventSource>\n" +
                "            </ConfigurableHeaders>\n" +
                "            <DeliveryDelay>0</DeliveryDelay>\n" +
                "        </config>\n" +
                "        <pd:inputBindings>\n" +
                "            <ns:ActivityInput>\n" +
                "                <Body>\n" +
                "                    <xsl:value-of select=\"$JMS-Queue-Receiver/ns:ActivityOutput/Body\"/>\n" +
                "                </Body>\n" +
                "            </ns:ActivityInput>\n" +
                "        </pd:inputBindings>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>JMS Queue Receiver</pd:from>\n" +
                "        <pd:to>Log</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Log</pd:from>\n" +
                "        <pd:to>Reply to JMS Message</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Reply to JMS Message</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        HardCodedMaxSessionsCheck instance = new HardCodedMaxSessionsCheck();
        HardCodedMaxSessionsCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance, times(0)).reportIssueOnFile(anyString());
    }

}