package com.tibco.sonar.plugins.bw5.check.sharedjdbc;

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

public class HardCodedUrlCheckTest extends TestCase {

    public void testValidateXmlNoPassword() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BWSharedResource>\n" +
                "    <name>JDBC Connection</name>\n" +
                "    <resourceType>ae.shared.JDBCSharedResource</resourceType>\n" +
                "    <config>\n" +
                "        <driver>com.sybase.jdbc2.jdbc.SybDriver</driver>\n" +
                "        <maxConnections>10</maxConnections>\n" +
                "        <loginTimeout>0</loginTimeout>\n" +
                "        <connectionType>JDBC</connectionType>\n" +
                "        <UseSharedJndiConfig>false</UseSharedJndiConfig>\n" +
                "        <location>%%JmsProviderUrl%%</location>\n" +
                "        <user>%%RvDaemon%%</user>\n" +
                "        <password>%%NewItem%%</password>\n" +
                "    </config>\n" +
                "</BWSharedResource>";
        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("sharedjdbc");

        System.out.println("testValidate");
        HardCodedUrlCheck instance = new HardCodedUrlCheck();
        HardCodedUrlCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString(),anyInt());
    }


    public void testValidateXmlHarcoded() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<BWSharedResource>\n" +
                "    <name>JDBC Connection</name>\n" +
                "    <resourceType>ae.shared.JDBCSharedResource</resourceType>\n" +
                "    <config>\n" +
                "        <driver>com.sybase.jdbc2.jdbc.SybDriver</driver>\n" +
                "        <maxConnections>10</maxConnections>\n" +
                "        <loginTimeout>0</loginTimeout>\n" +
                "        <connectionType>JDBC</connectionType>\n" +
                "        <UseSharedJndiConfig>false</UseSharedJndiConfig>\n" +
                "        <location>jdbc:sybase:Tds:&lt;host&gt;:&lt;port#&gt;/&lt;databaseName&gt;</location>\n" +
                "        <user>test</user>\n" +
                "        <password>#!Rikiy4EviZ7CFIfImNLh6wkySxquEQjc</password>\n" +
                "    </config>\n" +
                "</BWSharedResource>";

        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("sharedjdbc");

        System.out.println("testValidate");
        HardCodedUrlCheck instance = new HardCodedUrlCheck();
        HardCodedUrlCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        doCallRealMethod().when(source).findAndValidateHardCodedChild(any(),any(),anyString(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedNode(any(),any(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedChild(any(),any(),anyString(),anyString());



        spyInstance.validate(source);
        Mockito.verify(source,times(1)).reportOnIssue(any(),anyInt(),anyString());
    }
}