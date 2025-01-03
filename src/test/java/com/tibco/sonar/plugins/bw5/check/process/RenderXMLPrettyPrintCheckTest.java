package com.tibco.sonar.plugins.bw5.check.process;

import com.tibco.sonar.plugins.bw5.source.ProcessSource;
import junit.framework.TestCase;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class RenderXMLPrettyPrintCheckTest extends TestCase {

    public void testValidateOK() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:tib=\"http://www.tibco.com/bw/xslt/custom-functions\">\n" +
                "    <pd:name>Process Definition (4).process</pd:name>\n" +
                "    <pd:startName>Start</pd:startName>\n" +
                "    <pd:startX>150</pd:startX>\n" +
                "    <pd:startY>64</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1730968602385</pd:targetNamespace>\n" +
                "    <pd:activity name=\"Map Data\">\n" +
                "        <pd:type>com.tibco.plugin.mapper.MapperActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.MapperActivity</pd:resourceType>\n" +
                "        <pd:x>309</pd:x>\n" +
                "        <pd:y>72</pd:y>\n" +
                "        <config>\n" +
                "            <element>\n" +
                "                <xsd:element name=\"root\">\n" +
                "                    <xsd:complexType>\n" +
                "                        <xsd:sequence>\n" +
                "                            <xsd:element name=\"param\" type=\"xsd:string\"/>\n" +
                "                            <xsd:element name=\"param1\" type=\"xsd:string\"/>\n" +
                "                        </xsd:sequence>\n" +
                "                    </xsd:complexType>\n" +
                "                </xsd:element>\n" +
                "            </element>\n" +
                "        </config>\n" +
                "        <pd:inputBindings>\n" +
                "            <root>\n" +
                "                <param>\n" +
                "                    <xsl:value-of select=\"tib:render-xml($_globalVariables, true(),false())\"/>\n" +
                "                </param>\n" +
                "            </root>\n" +
                "        </pd:inputBindings>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Start</pd:from>\n" +
                "        <pd:to>Map Data</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Map Data</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        RenderXMLPrettyPrintCheck instance = new RenderXMLPrettyPrintCheck();
        RenderXMLPrettyPrintCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString(),anyInt());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString(),anyInt());
    }

    public void testValidateKO() {
        ProcessSource source = new ProcessSource("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<pd:ProcessDefinition xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" xmlns:pd=\"http://xmlns.tibco.com/bw/process/2003\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:tib=\"http://www.tibco.com/bw/xslt/custom-functions\">\n" +
                "    <pd:name>Process Definition (4).process</pd:name>\n" +
                "    <pd:startName>Start</pd:startName>\n" +
                "    <pd:startX>150</pd:startX>\n" +
                "    <pd:startY>64</pd:startY>\n" +
                "    <pd:returnBindings/>\n" +
                "    <pd:endName>End</pd:endName>\n" +
                "    <pd:endX>450</pd:endX>\n" +
                "    <pd:endY>64</pd:endY>\n" +
                "    <pd:errorSchemas/>\n" +
                "    <pd:processVariables/>\n" +
                "    <pd:targetNamespace>http://xmlns.example.com/1730968602385</pd:targetNamespace>\n" +
                "    <pd:activity name=\"Map Data\">\n" +
                "        <pd:type>com.tibco.plugin.mapper.MapperActivity</pd:type>\n" +
                "        <pd:resourceType>ae.activities.MapperActivity</pd:resourceType>\n" +
                "        <pd:x>309</pd:x>\n" +
                "        <pd:y>72</pd:y>\n" +
                "        <config>\n" +
                "            <element>\n" +
                "                <xsd:element name=\"root\">\n" +
                "                    <xsd:complexType>\n" +
                "                        <xsd:sequence>\n" +
                "                            <xsd:element name=\"param\" type=\"xsd:string\"/>\n" +
                "                            <xsd:element name=\"param1\" type=\"xsd:string\"/>\n" +
                "                        </xsd:sequence>\n" +
                "                    </xsd:complexType>\n" +
                "                </xsd:element>\n" +
                "            </element>\n" +
                "        </config>\n" +
                "        <pd:inputBindings>\n" +
                "            <root>\n" +
                "                <param>\n" +
                "                    <xsl:value-of select=\"tib:render-xml($_globalVariables, true(),true())\"/>\n" +
                "                </param>\n" +
                "            </root>\n" +
                "        </pd:inputBindings>\n" +
                "    </pd:activity>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Start</pd:from>\n" +
                "        <pd:to>Map Data</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "    <pd:transition>\n" +
                "        <pd:from>Map Data</pd:from>\n" +
                "        <pd:to>End</pd:to>\n" +
                "        <pd:lineType>Default</pd:lineType>\n" +
                "        <pd:lineColor>-16777216</pd:lineColor>\n" +
                "        <pd:conditionType>always</pd:conditionType>\n" +
                "    </pd:transition>\n" +
                "</pd:ProcessDefinition>");
        RenderXMLPrettyPrintCheck instance = new RenderXMLPrettyPrintCheck();
        RenderXMLPrettyPrintCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(anyString(),anyInt());

        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(1)).reportIssueOnFile(anyString(),anyInt());
    }
}