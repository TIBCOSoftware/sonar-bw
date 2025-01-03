package com.tibco.sonar.plugins.bw5.check.sharedhttp;

import com.tibco.sonar.plugins.bw5.check.sharedjms.HardCodedJndiPasswordCheck;
import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import com.tibco.utils.TestUtils;
import junit.framework.TestCase;
import org.mockito.Mockito;
import org.w3c.dom.Document;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class HardCodedPortCheckTest extends TestCase {

    public void testValidateXmlNoPassword() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:httpSharedResource xmlns:ns0=\"www.tibco.com/shared/HTTPConnection\">\n" +
                "    <config>\n" +
                "        <Host>%%RvDaemon%%</Host>\n" +
                "        <serverType>Tomcat</serverType>\n" +
                "        <HttpConnectionProperties>\n" +
                "            <row>\n" +
                "                <PropertyName>maxPostSize (bytes)</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>2097152</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>maxSavePostSize (bytes)</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>4096</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>uriEncoding</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue/>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>acceptCount</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue/>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>compressableMimeType</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>text/html,text/xml,text/plain</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>compression</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>off</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>connectionTimeOut (ms)</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>60000</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "        </HttpConnectionProperties>\n" +
                "        <Port>%%NewItem%%</Port>\n" +
                "    </config>\n" +
                "</ns0:httpSharedResource>";
        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("sharedhttp");
        System.out.println("testValidate");
        HardCodedJndiPasswordCheck instance = new HardCodedJndiPasswordCheck();
        HardCodedJndiPasswordCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString(),anyInt());
    }


    public void testValidateXmlHarcoded() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ns0:httpSharedResource xmlns:ns0=\"www.tibco.com/shared/HTTPConnection\">\n" +
                "    <config>\n" +
                "        <Host>localhost</Host>\n" +
                "        <serverType>Tomcat</serverType>\n" +
                "        <HttpConnectionProperties>\n" +
                "            <row>\n" +
                "                <PropertyName>maxPostSize (bytes)</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>2097152</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>maxSavePostSize (bytes)</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>4096</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>uriEncoding</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue/>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>acceptCount</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue/>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>compressableMimeType</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>text/html,text/xml,text/plain</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>compression</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>off</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "            <row>\n" +
                "                <PropertyName>connectionTimeOut (ms)</PropertyName>\n" +
                "                <PropertyValue/>\n" +
                "                <PropertyDefaultValue>60000</PropertyDefaultValue>\n" +
                "            </row>\n" +
                "        </HttpConnectionProperties>\n" +
                "        <Port>80</Port>\n" +
                "    </config>\n" +
                "</ns0:httpSharedResource>";

        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("sharedhttp");

        System.out.println("testValidate");
        HardCodedPortCheck instance = new HardCodedPortCheck();

        HardCodedPortCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        doCallRealMethod().when(source).findAndValidateHardCodedChild(any(),any(),anyString(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedNode(any(),any(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedChild(any(),any(),anyString(),anyString());



        spyInstance.validate(source);
        Mockito.verify(source,times(1)).reportOnIssue(any(),anyInt(),anyString());
    }
}