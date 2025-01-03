package com.tibco.sonar.plugins.bw5.check.sharedjms;

import com.tibco.sonar.plugins.bw5.source.XmlBw5Source;
import com.tibco.utils.TestUtils;
import junit.framework.TestCase;
import org.mockito.Mockito;
import org.sonar.api.batch.fs.InputFile;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import java.io.IOException;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class HardCodedJndiPasswordCheckTest extends TestCase {





    public void testValidateXmlNoPassword() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BWSharedResource>\n" +
                "    <name>JMS Connection</name>\n" +
                "    <resourceType>ae.shared.JMSConnectionKey</resourceType>\n" +
                "    <config>\n" +
                "        <NamingEnvironment>\n" +
                "            <UseJNDI>false</UseJNDI>\n" +
                "            <ProviderURL>%%NewItem/EMSServer%%</ProviderURL>\n" +
                "            <NamingURL>tibjmsnaming://localhost:7222</NamingURL>\n" +
                "            <NamingInitialContextFactory>com.tibco.tibjms.naming.TibjmsInitialContextFactory</NamingInitialContextFactory>\n" +
                "            <TopicFactoryName>TopicConnectionFactory</TopicFactoryName>\n" +
                "            <QueueFactoryName>QueueConnectionFactory</QueueFactoryName>\n" +
                "            <NamingPrincipal/>\n" +
                "            <NamingCredential/>\n" +
                "        </NamingEnvironment>\n" +
                "        <ConnectionAttributes>\n" +
                "            <username/>\n" +
                "            <password/>\n" +
                "            <clientID/>\n" +
                "            <autoGenClientID>true</autoGenClientID>\n" +
                "        </ConnectionAttributes>\n" +
                "        <UseXACF>false</UseXACF>\n" +
                "    </config>\n" +
                "</BWSharedResource>";
        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("sharedjms");
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
                "<BWSharedResource>\n" +
                "    <name>JMS Connection</name>\n" +
                "    <resourceType>ae.shared.JMSConnectionKey</resourceType>\n" +
                "    <config>\n" +
                "        <NamingEnvironment>\n" +
                "            <UseJNDI>true</UseJNDI>\n" +
                "            <ProviderURL>%%NewItem/EMSServer%%</ProviderURL>\n" +
                "            <NamingURL>tibjmsnaming://localhost:7222</NamingURL>\n" +
                "            <NamingInitialContextFactory>com.tibco.tibjms.naming.TibjmsInitialContextFactory</NamingInitialContextFactory>\n" +
                "            <TopicFactoryName>TopicConnectionFactory</TopicFactoryName>\n" +
                "            <QueueFactoryName>QueueConnectionFactory</QueueFactoryName>\n" +
                "            <NamingPrincipal>test</NamingPrincipal>\n" +
                "            <NamingCredential>#!LY/GFpJ0LzfwUuEQvYfRnVNPpglDZ+yK</NamingCredential>\n" +
                "        </NamingEnvironment>\n" +
                "        <ConnectionAttributes>\n" +
                "            <username/>\n" +
                "            <password/>\n" +
                "            <clientID/>\n" +
                "            <autoGenClientID>true</autoGenClientID>\n" +
                "        </ConnectionAttributes>\n" +
                "        <UseXACF>false</UseXACF>\n" +
                "    </config>\n" +
                "</BWSharedResource>";

        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("sharedjms");

        System.out.println("testValidate");
        HardCodedJndiPasswordCheck instance = new HardCodedJndiPasswordCheck();
        HardCodedJndiPasswordCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        doCallRealMethod().when(source).findAndValidateHardCodedChild(any(),any(),anyString(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedNode(any(),any(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedChild(any(),any(),anyString(),anyString());



        spyInstance.validate(source);
        Mockito.verify(source,times(1)).reportOnIssue(any(),anyInt(),anyString());
    }
}