package com.tibco.sonar.plugins.bw5.check.activity.soap.receiver;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class HardCodedSoapActionCheckTest extends TestCase {
    public void testValidate() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pfx=\"http://www.webserviceX.NET\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\">\n" +
                "    <wsdl:import namespace=\"http://www.webserviceX.NET\" location=\"/globalweather.wsdl\"/>\n" +
                "    <pd:name>Process Definition (3).process</pd:name>\n" +
                "    <pd:startName>SOAPEventSource</pd:startName>\n" +
                "    <pd:startX>0</pd:startX>\n" +
                "    <pd:startY>0</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:starter name=\"SOAPEventSource\">\n" +
                "        <pd:type>com.tibco.plugin.soap.SOAPEventSource</pd:type>\n" +
                "        <pd:resourceType>ae.activities.SOAPEventSourceUI</pd:resourceType>\n" +
                "        <pd:x>90</pd:x>\n" +
                "        <pd:y>81</pd:y>\n" +
                "        <config>\n" +
                "            <service>pfx:GlobalWeatherHttpGet</service>\n" +
                "            <wsdlNamespaceRoot>http://www.webserviceX.NET/GetWeatherImpl</wsdlNamespaceRoot>\n" +
                "            <operation>GetWeather</operation>\n" +
                "            <portAddressPrefix/>\n" +
                "            <operationStyle>rpc</operationStyle>\n" +
                "            <soapUse>literal</soapUse>\n" +
                "            <inputNamespace>http://InputMessageNamespace</inputNamespace>\n" +
                "            <outputNamespace>http://OutputMessageNamespace</outputNamespace>\n" +
                "            <faultNamespace>http://FaultMessageNamespace</faultNamespace>\n" +
                "            <embedWsdlComponents>true</embedWsdlComponents>\n" +
                "            <embedSchemaComponents>true</embedSchemaComponents>\n" +
                "            <soapAction>%%JmsProviderUrl%%</soapAction>\n" +
                "            <sharedChannel>/HTTP Connection.sharedhttp</sharedChannel>\n" +
                "            <soapVersion>SOAP 1.2</soapVersion>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:starter>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1729678522377</pd:targetNamespace>\n" +
                "    <pd:activity name=\"SOAPSendReply\">\n" +
                "        <pd:type>com.tibco.plugin.soap.SOAPSendReplyActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.SOAPSendReplyUI</pd:resourceType>\n" +
                "        <pd:x>324</pd:x>\n" +
                "        <pd:y>78</pd:y>\n" +
                "        <config>\n" +
                "            <eventSource>SOAPEventSource</eventSource>\n" +
                "            <sharedChannels>\n" +
                "                <jmsChannel>\n" +
                "                    <JMSDeliveryMode>2</JMSDeliveryMode>\n" +
                "                </jmsChannel>\n" +
                "            </sharedChannels>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>SOAPEventSource</pd:from>\n" +
                "        <pd:to>SOAPSendReply</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>SOAPSendReply</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck instance = new com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck();
        com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString());

    }

    public void testValidateKO() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pfx=\"http://www.webserviceX.NET\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\">\n" +
                "    <wsdl:import namespace=\"http://www.webserviceX.NET\" location=\"/globalweather.wsdl\"/>\n" +
                "    <pd:name>Process Definition (3).process</pd:name>\n" +
                "    <pd:startName>SOAPEventSource</pd:startName>\n" +
                "    <pd:startX>0</pd:startX>\n" +
                "    <pd:startY>0</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:starter name=\"SOAPEventSource\">\n" +
                "        <pd:type>com.tibco.plugin.soap.SOAPEventSource</pd:type>\n" +
                "        <pd:resourceType>ae.activities.SOAPEventSourceUI</pd:resourceType>\n" +
                "        <pd:x>90</pd:x>\n" +
                "        <pd:y>81</pd:y>\n" +
                "        <config>\n" +
                "            <service>pfx:GlobalWeatherHttpGet</service>\n" +
                "            <wsdlNamespaceRoot>http://www.webserviceX.NET/GetWeatherImpl</wsdlNamespaceRoot>\n" +
                "            <operation>GetWeather</operation>\n" +
                "            <portAddressPrefix/>\n" +
                "            <operationStyle>rpc</operationStyle>\n" +
                "            <soapUse>literal</soapUse>\n" +
                "            <inputNamespace>http://InputMessageNamespace</inputNamespace>\n" +
                "            <outputNamespace>http://OutputMessageNamespace</outputNamespace>\n" +
                "            <faultNamespace>http://FaultMessageNamespace</faultNamespace>\n" +
                "            <embedWsdlComponents>true</embedWsdlComponents>\n" +
                "            <embedSchemaComponents>true</embedSchemaComponents>\n" +
                "            <soapAction>/Process%20Definition%20(3)</soapAction>\n" +
                "            <sharedChannel>/HTTP Connection.sharedhttp</sharedChannel>\n" +
                "            <soapVersion>SOAP 1.2</soapVersion>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:starter>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1729678522377</pd:targetNamespace>\n" +
                "    <pd:activity name=\"SOAPSendReply\">\n" +
                "        <pd:type>com.tibco.plugin.soap.SOAPSendReplyActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.SOAPSendReplyUI</pd:resourceType>\n" +
                "        <pd:x>324</pd:x>\n" +
                "        <pd:y>78</pd:y>\n" +
                "        <config>\n" +
                "            <eventSource>SOAPEventSource</eventSource>\n" +
                "            <sharedChannels>\n" +
                "                <jmsChannel>\n" +
                "                    <JMSDeliveryMode>2</JMSDeliveryMode>\n" +
                "                </jmsChannel>\n" +
                "            </sharedChannels>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>SOAPEventSource</pd:from>\n" +
                "        <pd:to>SOAPSendReply</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>SOAPSendReply</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck instance = new com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck();
        com.tibco.sonar.plugins.bw5.check.activity.soap.receiver.HardCodedSoapActionCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString());

    }
}