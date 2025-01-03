package com.tibco.sonar.plugins.bw5.check.activity.soap.request;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class HardCodedTimeoutCheckTest extends TestCase {
    public void testValidate() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pfx=\"http://www.webserviceX.NET\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\">\n" +
                "    <wsdl:import namespace=\"http://www.webserviceX.NET\" location=\"/globalweather.wsdl\"/>\n" +
                "    <pd:name>Process Definition (2).process</pd:name>\n" +
                "    <pd:startName>Start</pd:startName>\n" +
                "    <pd:startX>150</pd:startX>\n" +
                "    <pd:startY>64</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1729678122503</pd:targetNamespace>\n" +
                "    <pd:activity name=\"SOAPRequestReply\">\n" +
                "        <pd:type>com.tibco.plugin.soap.SOAPSendReceiveActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.SOAPSendReceiveUI</pd:resourceType>\n" +
                "        <pd:x>313</pd:x>\n" +
                "        <pd:y>74</pd:y>\n" +
                "        <config>\n" +
                "            <timeout>%%NewItem%%</timeout>\n" +
                "            <soapAttachmentStyle>SwA</soapAttachmentStyle>\n" +
                "            <timeoutType>Seconds</timeoutType>\n" +
                "            <service>pfx:GlobalWeather</service>\n" +
                "            <servicePort>GlobalWeatherSoap</servicePort>\n" +
                "            <operation>GetWeather</operation>\n" +
                "            <soapAction>%%JmsProviderUrl%%</soapAction>\n" +
                "            <endpointURL>%%JmsProviderUrl%%</endpointURL>\n" +
                "            <authScheme>NONE</authScheme>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Start</pd:from>\n" +
                "        <pd:to>SOAPRequestReply</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>SOAPRequestReply</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        HardCodedTimeoutCheck instance = new HardCodedTimeoutCheck();
        HardCodedTimeoutCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString());

    }

    public void testValidateKO() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pfx=\"http://www.webserviceX.NET\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\">\n" +
                "    <wsdl:import namespace=\"http://www.webserviceX.NET\" location=\"/globalweather.wsdl\"/>\n" +
                "    <pd:name>Process Definition (2).process</pd:name>\n" +
                "    <pd:startName>Start</pd:startName>\n" +
                "    <pd:startX>150</pd:startX>\n" +
                "    <pd:startY>64</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1729678122503</pd:targetNamespace>\n" +
                "    <pd:activity name=\"SOAPRequestReply\">\n" +
                "        <pd:type>com.tibco.plugin.soap.SOAPSendReceiveActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.SOAPSendReceiveUI</pd:resourceType>\n" +
                "        <pd:x>313</pd:x>\n" +
                "        <pd:y>74</pd:y>\n" +
                "        <config>\n" +
                "            <timeout>0</timeout>\n" +
                "            <soapAttachmentStyle>SwA</soapAttachmentStyle>\n" +
                "            <timeoutType>Seconds</timeoutType>\n" +
                "            <service>pfx:GlobalWeather</service>\n" +
                "            <servicePort>GlobalWeatherSoap</servicePort>\n" +
                "            <operation>GetWeather</operation>\n" +
                "            <soapAction>http://www.webserviceX.NET/GetWeather</soapAction>\n" +
                "            <endpointURL>http://www.webservicex.com/globalweather.asmx</endpointURL>\n" +
                "            <authScheme>NONE</authScheme>\n" +
                "        </config>\n" +
                "        <pd:inputBindings/>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Start</pd:from>\n" +
                "        <pd:to>SOAPRequestReply</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>SOAPRequestReply</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        HardCodedTimeoutCheck instance = new HardCodedTimeoutCheck();
        HardCodedTimeoutCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString());

    }
}