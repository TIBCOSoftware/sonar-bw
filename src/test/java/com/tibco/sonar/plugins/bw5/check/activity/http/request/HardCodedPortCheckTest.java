package com.tibco.sonar.plugins.bw5.check.activity.http.request;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class HardCodedPortCheckTest extends TestCase {
    public void testValidate() {
    ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\">\n" +
            "    <pd:name>Process Definition.process</pd:name>\n" +
            "    <pd:startName>Start</pd:startName>\n" +
            "    <pd:startX>150</pd:startX>\n" +
            "    <pd:startY>64</pd:startY>\n" +
            "    <pd:returnBindings/>\n" +
            "    <pd:endName>End</pd:endName>\n" +
            "    <pd:endX>450</pd:endX>\n" +
            "    <pd:endY>64</pd:endY>\n" +
            "    <pd:errorSchemas/>\n" +
            "    <pd:processVariables/>\n" +
            "    <pd:targetNamespace>http://xmlns.example.com/1728392641653</pd:targetNamespace>\n" +
            "    <pd:activity name=\"Send HTTP Request\">\n" +
            "        <pd:type>com.tibco.plugin.http.client.HttpRequestActivity</pd:type>\n" +
            "        <pd:resourceType>httppalette.httpRequest</pd:resourceType>\n" +
            "        <pd:x>299</pd:x>\n" +
            "        <pd:y>80</pd:y>\n" +
            "        <config>\n" +
            "            <InputHeaders>\n" +
            "                <xsd:element xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" name=\"root\">\n" +
            "                    <xsd:complexType>\n" +
            "                        <xsd:sequence>\n" +
            "                            <xsd:element name=\"Accept\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Accept-Charset\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Accept-Encoding\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Content-Type\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Cookie\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Pragma\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                        </xsd:sequence>\n" +
            "                    </xsd:complexType>\n" +
            "                </xsd:element>\n" +
            "            </InputHeaders>\n" +
            "            <OutputHeaders>\n" +
            "                <xsd:element xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" name=\"root\">\n" +
            "                    <xsd:complexType>\n" +
            "                        <xsd:sequence>\n" +
            "                            <xsd:element name=\"Allow\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Content-Type\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Content-Length\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Content-Encoding\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Date\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Location\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Set-Cookie\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                            <xsd:element name=\"Pragma\" type=\"xsd:string\" minOccurs=\"0\"/>\n" +
            "                        </xsd:sequence>\n" +
            "                    </xsd:complexType>\n" +
            "                </xsd:element>\n" +
            "            </OutputHeaders>\n" +
            "            <inputOutputVersion>5.3.0</inputOutputVersion>\n" +
            "            <newMimeSupport>true</newMimeSupport>\n" +
            "        </config>\n" +
            "        <pd:inputBindings>\n" +
            "            <RequestActivityInput/>\n" +
            "        </pd:inputBindings>\n" +
            "    </pd:activity>\n" +
            "    <pd:transition>\n" +
            "        <pd:from>Start</pd:from>\n" +
            "        <pd:to>Send HTTP Request</pd:to>\n" +
            "        <pd:lineType>Default</pd:lineType>\n" +
            "        <pd:lineColor>-16777216</pd:lineColor>\n" +
            "        <pd:conditionType>always</pd:conditionType>\n" +
            "    </pd:transition>\n" +
            "    <pd:transition>\n" +
            "        <pd:from>Send HTTP Request</pd:from>\n" +
            "        <pd:to>End</pd:to>\n" +
            "        <pd:lineType>Default</pd:lineType>\n" +
            "        <pd:lineColor>-16777216</pd:lineColor>\n" +
            "        <pd:conditionType>always</pd:conditionType>\n" +
            "    </pd:transition>\n" +
            "</pd:ProcessDefinition>");
        HardCodedPortCheck instance = new HardCodedPortCheck();
        HardCodedPortCheck spyInstance = Mockito.spy(instance);
    doNothing().when(spyInstance).reportIssueOnFile(anyString());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString());

}

}