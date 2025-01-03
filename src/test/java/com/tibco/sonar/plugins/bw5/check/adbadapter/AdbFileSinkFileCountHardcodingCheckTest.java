package com.tibco.sonar.plugins.bw5.check.adbadapter;

import com.tibco.sonar.plugins.bw5.check.sapadapter.SapFileSinkFileCountHardcodingCheck;
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

public class AdbFileSinkFileCountHardcodingCheckTest extends TestCase {


    public void testValidateXmlNoPassword() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Repository:repository xmlns:Repository=\"http://www.tibco.com/xmlns/repo/types/2002\" xmlns:AESDK=\"http://www.tibco.com/xmlns/aemeta/adapter/2002\" xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <SAPAdapter:adapter xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\" name=\"SAPAdapter\">\n" +
                "        <AESDK:instanceId>R3AdapterConfiguration</AESDK:instanceId>\n" +
                "        <AESDK:startup>\n" +
                "            <AESDK:defaultStartup>active</AESDK:defaultStartup>\n" +
                "            <AESDK:banner>true</AESDK:banner>\n" +
                "            <AESDK:hasStdMicroAgent>true</AESDK:hasStdMicroAgent>\n" +
                "            <AESDK:stdMicroAgentName>COM.TIBCO.ADAPTER.adr3.%%Deployment%%.%%InstanceId%%</AESDK:stdMicroAgentName>\n" +
                "            <AESDK:stdMicroAgentTimeout>10000</AESDK:stdMicroAgentTimeout>\n" +
                "            <AESDK:hasClassMicroAgent>%%HawkEnabled%%</AESDK:hasClassMicroAgent>\n" +
                "            <AESDK:classMicroAgentTimeout>10000</AESDK:classMicroAgentTimeout>\n" +
                "            <AESDK:classMicroAgentName>COM.TIBCO.adr3.%%Deployment%%.%%InstanceId%%</AESDK:classMicroAgentName>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>R3HawkDefault</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>R3RVSession</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>RvStopSubscriber</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>r3OutBound-RPCServer</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>idocOutbound-RPCServer</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>RFCServer-RPCServer</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>RFCClient-RPCServer</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:defaultSession>R3RVSession</AESDK:defaultSession>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>TIDManagerSession</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>TID_RPC_CLIENT</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>INBOUND_TID_RPC_CLIENT</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:defaultMicroAgentSession>R3HawkDefault</AESDK:defaultMicroAgentSession>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>DefaultRVCMQSession</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>idocCMQSubscriber-R3AdapterConfiguration</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "        </AESDK:startup>\n" +
                "        <AESDK:deployment>\n" +
                "            <AESDK:advisories>\n" +
                "                <AESDK:advisory>\n" +
                "                    <AESDK:name>advisory.rv.warn</AESDK:name>\n" +
                "                    <AESDK:subject>_RV.WARN.&gt;</AESDK:subject>\n" +
                "                </AESDK:advisory>\n" +
                "                <AESDK:advisory>\n" +
                "                    <AESDK:subject>_SDK.ERROR.&gt;</AESDK:subject>\n" +
                "                    <AESDK:name>advisory.sdk.error</AESDK:name>\n" +
                "                </AESDK:advisory>\n" +
                "                <AESDK:advisory>\n" +
                "                    <AESDK:subject>_SDK.WARN.&gt;</AESDK:subject>\n" +
                "                    <AESDK:name>advisory.sdk.warn</AESDK:name>\n" +
                "                </AESDK:advisory>\n" +
                "                <AESDK:advisory>\n" +
                "                    <AESDK:subject>_SDK.INFO.&gt;</AESDK:subject>\n" +
                "                    <AESDK:name>advisory.sdk.info</AESDK:name>\n" +
                "                </AESDK:advisory>\n" +
                "            </AESDK:advisories>\n" +
                "            <AESDK:sessions>\n" +
                "                <AESDK:messaging>\n" +
                "                    <AESDK:rvSession isRef=\"true\">#rvSession.R3HawkDefault</AESDK:rvSession>\n" +
                "                    <AESDK:rvSession isRef=\"true\">#rvSession.R3RVSession</AESDK:rvSession>\n" +
                "                    <AESDK:rvSession isRef=\"true\">#rvSession.TIDManagerSession</AESDK:rvSession>\n" +
                "                    <AESDK:rvCmqSession isRef=\"true\">#rvCmqSession.DefaultRVCMQSession</AESDK:rvCmqSession>\n" +
                "                </AESDK:messaging>\n" +
                "            </AESDK:sessions>\n" +
                "            <AESDK:consumers>\n" +
                "                <AESDK:rvSubscriber isRef=\"true\">#consumer.RvStopSubscriber</AESDK:rvSubscriber>\n" +
                "                <AESDK:rvCmqSubscriber isRef=\"true\">#consumer.idocCMQSubscriber-R3AdapterConfiguration</AESDK:rvCmqSubscriber>\n" +
                "            </AESDK:consumers>\n" +
                "            <AESDK:servers>\n" +
                "                <AESDK:rvRpcServer isRef=\"true\">#server.r3OutBound-RPCServer</AESDK:rvRpcServer>\n" +
                "                <AESDK:rvRpcServer isRef=\"true\">#server.idocOutbound-RPCServer</AESDK:rvRpcServer>\n" +
                "                <AESDK:rvRpcServer isRef=\"true\">#server.RFCServer-RPCServer</AESDK:rvRpcServer>\n" +
                "                <AESDK:rvRpcServer isRef=\"true\">#server.RFCClient-RPCServer</AESDK:rvRpcServer>\n" +
                "            </AESDK:servers>\n" +
                "            <AESDK:clients>\n" +
                "                <AESDK:rvRpcClient isRef=\"true\">#client.TID_RPC_CLIENT</AESDK:rvRpcClient>\n" +
                "                <AESDK:rvRpcClient isRef=\"true\">#client.INBOUND_TID_RPC_CLIENT</AESDK:rvRpcClient>\n" +
                "            </AESDK:clients>\n" +
                "        </AESDK:deployment>\n" +
                "        <AESDK:timers xsi:nil=\"true\"/>\n" +
                "        <AESDK:txControls xsi:nil=\"true\"/>\n" +
                "        <AESDK:reporting>\n" +
                "            <AESDK:fileSink>\n" +
                "                <AESDK:fileName>%%DirTrace%%/%%Deployment%%.%%InstanceId%%.log</AESDK:fileName>\n" +
                "                <AESDK:fileCount>3</AESDK:fileCount>\n" +
                "                <AESDK:fileLimit>30000</AESDK:fileLimit>\n" +
                "                <AESDK:appendMode>true</AESDK:appendMode>\n" +
                "                <AESDK:name>fileSink</AESDK:name>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>infoRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>errorRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>warnRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "            </AESDK:fileSink>\n" +
                "            <AESDK:stdioSink>\n" +
                "                <AESDK:ioName>stdout</AESDK:ioName>\n" +
                "                <AESDK:name>stdioSink</AESDK:name>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>infoRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>errorRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>warnRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "            </AESDK:stdioSink>\n" +
                "        </AESDK:reporting>\n" +
                "        <AESDK:metadata>\n" +
                "            <AESDK:loadUrl isRef=\"true\">/AESchemas/ae/SAPAdapter40/classes/#class</AESDK:loadUrl>\n" +
                "        </AESDK:metadata>\n" +
                "        <AESDK:designer>\n" +
                "            <AESDK:advancedLogging>false</AESDK:advancedLogging>\n" +
                "            <AESDK:adapterVersion>sdk53</AESDK:adapterVersion>\n" +
                "            <AESDK:rememberPassword>true</AESDK:rememberPassword>\n" +
                "            <AESDK:lockedProperties>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/TIDManagerSession/INBOUND_TID_RPC_CLIENT</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/TIDManagerSession/TID_RPC_CLIENT</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession/RFCClient-RPCServer</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name,wireFormat,description,endpoint.RVRPCServer</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession/RFCServer-RPCServer</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name,wireFormat,description,endpoint.RVRPCServer</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession/idocOutbound-RPCServer</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name,wireFormat,description,endpoint.RVRPCServer</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession/r3OutBound-RPCServer</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name,wireFormat,description,endpoint.RVRPCServer</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession/RvStopSubscriber</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name,wireFormat,description,listenTimeout,endpoint.RVSubscriber</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3HawkDefault</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Advisories/advisory.sdk.info</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Advisories/advisory.sdk.warn</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Advisories/advisory.sdk.error</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Advisories/advisory.rv.warn</AESDK:resource>\n" +
                "                    <AESDK:properties>subject,name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Adapter Services</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "            </AESDK:lockedProperties>\n" +
                "            <AESDK:fixedChildren>\n" +
                "                <AESDK:fixed>\n" +
                "                    <AESDK:resource>/Advanced/Sessions</AESDK:resource>\n" +
                "                    <AESDK:children>R3HawkDefault,R3RVSession</AESDK:children>\n" +
                "                </AESDK:fixed>\n" +
                "                <AESDK:fixed>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/TIDManagerSession</AESDK:resource>\n" +
                "                    <AESDK:children>TID_RPC_CLIENT,INBOUND_TID_RPC_CLIENT</AESDK:children>\n" +
                "                </AESDK:fixed>\n" +
                "                <AESDK:fixed>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession</AESDK:resource>\n" +
                "                    <AESDK:children>RvStopSubscriber,r3OutBound-RPCServer,idocOutbound-RPCServer,RFCServer-RPCServer,RFCClient-RPCServer</AESDK:children>\n" +
                "                </AESDK:fixed>\n" +
                "                <AESDK:fixed>\n" +
                "                    <AESDK:resource>/Advanced/Advisories</AESDK:resource>\n" +
                "                    <AESDK:children>advisory.rv.warn,advisory.sdk.error,advisory.sdk.warn,advisory.sdk.info</AESDK:children>\n" +
                "                </AESDK:fixed>\n" +
                "            </AESDK:fixedChildren>\n" +
                "            <AESDK:resourceDescriptions>\n" +
                "                <AESDK:node>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/R3RVSession/RvStopSubscriber</AESDK:resource>\n" +
                "                    <AESDK:description>This is the endpoint which listens on STOP ADAPTER message for RV Transport</AESDK:description>\n" +
                "                </AESDK:node>\n" +
                "            </AESDK:resourceDescriptions>\n" +
                "        </AESDK:designer>\n" +
                "        <SAPAdapter:r3AdpConnectionType>r3AdpDedicated</SAPAdapter:r3AdpConnectionType>\n" +
                "        <SAPAdapter:appServer>%%AppServer%%</SAPAdapter:appServer>\n" +
                "        <SAPAdapter:systemNo>%%SystemNumber%%</SAPAdapter:systemNo>\n" +
                "        <SAPAdapter:sncMode>%%SncMode%%</SAPAdapter:sncMode>\n" +
                "        <SAPAdapter:sncPartnerName>%%SncPartnername%%</SAPAdapter:sncPartnerName>\n" +
                "        <SAPAdapter:sncQop>%%SncQop%%</SAPAdapter:sncQop>\n" +
                "        <SAPAdapter:sncLib>%%SncLib%%</SAPAdapter:sncLib>\n" +
                "        <SAPAdapter:number>%%Client%%</SAPAdapter:number>\n" +
                "        <SAPAdapter:userName>%%UserName%%</SAPAdapter:userName>\n" +
                "        <SAPAdapter:password>%%Password%%</SAPAdapter:password>\n" +
                "        <SAPAdapter:language>E</SAPAdapter:language>\n" +
                "        <SAPAdapter:components xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:component isRef=\"true\">#r3Inbound.r3Inbound</SAPAdapter:component>\n" +
                "        </SAPAdapter:components>\n" +
                "        <SAPAdapter:deploymentDescriptions xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:deploymentDescription isRef=\"true\">#inBoundDeployment.inBoundDeployment</SAPAdapter:deploymentDescription>\n" +
                "        </SAPAdapter:deploymentDescriptions>\n" +
                "        <SAPAdapter:connectionManagers xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:connectionManager isRef=\"true\">/R3Connections.adr3Connections#connectionManager.R3AdapterConfigurationActiveConnections</SAPAdapter:connectionManager>\n" +
                "        </SAPAdapter:connectionManagers>\n" +
                "        <AESDK:hawk xmlns:AESDK=\"http://www.tibco.com/xmlns/aemeta/adapter/2002\">\n" +
                "            <AESDK:microAgentName>SAPAdapterMicroAgent</AESDK:microAgentName>\n" +
                "            <AESDK:sessionName isRef=\"true\">/R3AdapterConfiguration.adr3#rvSession.R3HawkDefault</AESDK:sessionName>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>getActivityStatisticsByService</AESDK:name>\n" +
                "                <AESDK:help>Get Runtime Statistics for an R/3 BAPI/RFC/IDOC</AESDK:help>\n" +
                "                <AESDK:type>INFO</AESDK:type>\n" +
                "                <AESDK:index>SerialNo</AESDK:index>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>SerialNo</AESDK:name>\n" +
                "                    <AESDK:help>Sequence Number</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ServiceName</AESDK:name>\n" +
                "                    <AESDK:help>Name of the Adapter Service</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ServiceType</AESDK:name>\n" +
                "                    <AESDK:help>IDoc or RFC/BAPI</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>Schema</AESDK:name>\n" +
                "                    <AESDK:help>Name of IDoc or RFC/BAPI</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>NumberTotalMessages</AESDK:name>\n" +
                "                    <AESDK:help>Total messages processed</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>NumberSucessMessages</AESDK:name>\n" +
                "                    <AESDK:help>Total messages processed successfully</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>NumberErrorMessages</AESDK:name>\n" +
                "                    <AESDK:help>Total messages processed unsuccessfully</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>resetActivityStatistics</AESDK:name>\n" +
                "                <AESDK:help>Reset R/3 RFC/BAPI/IDoc Activity Statistics</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>refreshExtendedLogging</AESDK:name>\n" +
                "                <AESDK:help>Refresh Extended logging of the adapter</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>resumeRemoteTIDManager</AESDK:name>\n" +
                "                <AESDK:help>Resume connectivity to remote TID Manager</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>resumeOutboundServices</AESDK:name>\n" +
                "                <AESDK:help>Restart stopped outbound services</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>getOutboundThreadData</AESDK:name>\n" +
                "                <AESDK:help>Get Outbound Thread Data for Adapter</AESDK:help>\n" +
                "                <AESDK:type>INFO</AESDK:type>\n" +
                "                <AESDK:index>SerialNo</AESDK:index>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>SerialNo</AESDK:name>\n" +
                "                    <AESDK:help>Sequence Number</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ServerConnectionPool</AESDK:name>\n" +
                "                    <AESDK:help>Server Connection Pool</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>GatewayHost</AESDK:name>\n" +
                "                    <AESDK:help>Gateway Host</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>GatewayService</AESDK:name>\n" +
                "                    <AESDK:help>Gateway Service</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ProgramID</AESDK:name>\n" +
                "                    <AESDK:help>Program ID</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ThreadCount</AESDK:name>\n" +
                "                    <AESDK:help>Number of threads in pool</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>setOutboundThreadPool</AESDK:name>\n" +
                "                <AESDK:help>Set thread count for outbound connection pool</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>GatewayHost</AESDK:name>\n" +
                "                    <AESDK:help>Gateway Host</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>GatewayService</AESDK:name>\n" +
                "                    <AESDK:help>Gateway Service</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>ProgramID</AESDK:name>\n" +
                "                    <AESDK:help>Program ID</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>ThreadCount</AESDK:name>\n" +
                "                    <AESDK:help>Number of threads in pool</AESDK:help>\n" +
                "                    <AESDK:type>integer</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>getInboundConnectionData</AESDK:name>\n" +
                "                <AESDK:help>Get Client Connection Pools data for Adapter</AESDK:help>\n" +
                "                <AESDK:type>INFO</AESDK:type>\n" +
                "                <AESDK:index>SerialNo</AESDK:index>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>SerialNo</AESDK:name>\n" +
                "                    <AESDK:help>Sequence Number</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ClientConnectionPool</AESDK:name>\n" +
                "                    <AESDK:help>Client Connection Pool</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>MaxConnections</AESDK:name>\n" +
                "                    <AESDK:help>Number of connections in pool</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>getInboundThreadData</AESDK:name>\n" +
                "                <AESDK:help>Get Inbound Thread data for Adapter</AESDK:help>\n" +
                "                <AESDK:type>INFO</AESDK:type>\n" +
                "                <AESDK:index>SerialNo</AESDK:index>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>SerialNo</AESDK:name>\n" +
                "                    <AESDK:help>Sequence Number</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ServiceName</AESDK:name>\n" +
                "                    <AESDK:help>Inbound Adapter Service Name</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ServiceType</AESDK:name>\n" +
                "                    <AESDK:help>Service Type</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>Schema</AESDK:name>\n" +
                "                    <AESDK:help>Name of RFC, BAPI, or IDoc</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ClientConnReference</AESDK:name>\n" +
                "                    <AESDK:help>Clent Connection Reference</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>SessionReference</AESDK:name>\n" +
                "                    <AESDK:help>Session Reference</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "                <AESDK:outputParameter>\n" +
                "                    <AESDK:name>ThreadCount</AESDK:name>\n" +
                "                    <AESDK:help>Number of threads in pool</AESDK:help>\n" +
                "                    <AESDK:type>Integer</AESDK:type>\n" +
                "                </AESDK:outputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>setInboundConnectionPool</AESDK:name>\n" +
                "                <AESDK:help>Set maximum number of connections for client connection pool</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>ClientConnectionPoolName</AESDK:name>\n" +
                "                    <AESDK:help>Client Connection Pool Name</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>MaxConnections</AESDK:name>\n" +
                "                    <AESDK:help>Number of connections in client connection pool</AESDK:help>\n" +
                "                    <AESDK:type>integer</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>setInboundThreadPool</AESDK:name>\n" +
                "                <AESDK:help>Set thread count for inbound adapter service</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>ServiceName</AESDK:name>\n" +
                "                    <AESDK:help>Inbound Adapter Service Name</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>ThreadCount</AESDK:name>\n" +
                "                    <AESDK:help>Number of threads in pool</AESDK:help>\n" +
                "                    <AESDK:type>integer</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>resumeInboundServices</AESDK:name>\n" +
                "                <AESDK:help>Resume all suspended services for client connection pool</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "                <AESDK:inputParameter>\n" +
                "                    <AESDK:name>ClientConnectionPoolName</AESDK:name>\n" +
                "                    <AESDK:help>Client Connection Pool Name</AESDK:help>\n" +
                "                    <AESDK:type>string</AESDK:type>\n" +
                "                </AESDK:inputParameter>\n" +
                "            </AESDK:method>\n" +
                "            <AESDK:method>\n" +
                "                <AESDK:name>refreshABAPRepository</AESDK:name>\n" +
                "                <AESDK:help>Refresh ABAP Repositories</AESDK:help>\n" +
                "                <AESDK:type>ACTION</AESDK:type>\n" +
                "            </AESDK:method>\n" +
                "        </AESDK:hawk>\n" +
                "        <SAPAdapter:designTime xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:designTimeParameters>\n" +
                "                <SAPAdapter:r3AdpConnectionType>r3AdpDedicated</SAPAdapter:r3AdpConnectionType>\n" +
                "                <SAPAdapter:appServer>%%AppServer%%</SAPAdapter:appServer>\n" +
                "                <SAPAdapter:systemNo>%%SystemNumber%%</SAPAdapter:systemNo>\n" +
                "                <SAPAdapter:client>%%Client%%</SAPAdapter:client>\n" +
                "                <SAPAdapter:username>%%UserName%%</SAPAdapter:username>\n" +
                "                <SAPAdapter:language>E</SAPAdapter:language>\n" +
                "                <SAPAdapter:rememberPassword>true</SAPAdapter:rememberPassword>\n" +
                "                <SAPAdapter:copyToRuntime>false</SAPAdapter:copyToRuntime>\n" +
                "                <SAPAdapter:password>%%Password%%</SAPAdapter:password>\n" +
                "            </SAPAdapter:designTimeParameters>\n" +
                "        </SAPAdapter:designTime>\n" +
                "        <SAPAdapter:g11n xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:encoding>LATIN_1</SAPAdapter:encoding>\n" +
                "        </SAPAdapter:g11n>\n" +
                "        <SAPAdapter:custom xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:name/>\n" +
                "        </SAPAdapter:custom>\n" +
                "    </SAPAdapter:adapter>\n" +
                "    <AEService:rvSession xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"session.RV\" name=\"R3HawkDefault\">\n" +
                "        <AEService:daemon>%%TIBHawkDaemon%%</AEService:daemon>\n" +
                "        <AEService:service>%%TIBHawkService%%</AEService:service>\n" +
                "        <AEService:network>%%TIBHawkNetwork%%</AEService:network>\n" +
                "        <AEService:mode>asynchronous</AEService:mode>\n" +
                "    </AEService:rvSession>\n" +
                "    <AEService:rvSession xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"session.RV\" name=\"R3RVSession\">\n" +
                "        <AEService:daemon>%%RvDaemon%%</AEService:daemon>\n" +
                "        <AEService:service>%%RvService%%</AEService:service>\n" +
                "        <AEService:network>%%RvNetwork%%</AEService:network>\n" +
                "        <AEService:mode>synchronous</AEService:mode>\n" +
                "    </AEService:rvSession>\n" +
                "    <AEService:rvSession xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"session.RV\" name=\"TIDManagerSession\">\n" +
                "        <AEService:daemon>%%RvDaemon%%</AEService:daemon>\n" +
                "        <AEService:service>%%RvService%%</AEService:service>\n" +
                "        <AEService:network>%%RvNetwork%%</AEService:network>\n" +
                "        <AEService:mode>synchronous</AEService:mode>\n" +
                "    </AEService:rvSession>\n" +
                "    <AEService:consumer xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVSubscriber\" name=\"RvStopSubscriber\">\n" +
                "        <AEService:subject>%%Domain%%.%%Deployment%%.adr3.%%InstanceId%%.exit</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.R3RVSession</AEService:session>\n" +
                "    </AEService:consumer>\n" +
                "    <AEService:server xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVRPCServer\" name=\"r3OutBound-RPCServer\">\n" +
                "        <AEService:subject>r3Outbound.RPCServer.R3AdapterConfiguration</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.R3RVSession</AEService:session>\n" +
                "    </AEService:server>\n" +
                "    <AEService:server xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVRPCServer\" name=\"idocOutbound-RPCServer\">\n" +
                "        <AEService:subject>idocOutbound.RPCServer.R3AdapterConfiguration</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.R3RVSession</AEService:session>\n" +
                "    </AEService:server>\n" +
                "    <AEService:server xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVRPCServer\" name=\"RFCServer-RPCServer\">\n" +
                "        <AEService:subject>RFCServer.RPCServer.R3AdapterConfiguration</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.R3RVSession</AEService:session>\n" +
                "    </AEService:server>\n" +
                "    <AEService:server xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVRPCServer\" name=\"RFCClient-RPCServer\">\n" +
                "        <AEService:subject>RFCClient.RPCServer.R3AdapterConfiguration</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.R3RVSession</AEService:session>\n" +
                "    </AEService:server>\n" +
                "    <AEService:client xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVRPCClient\" name=\"TID_RPC_CLIENT\">\n" +
                "        <AEService:subject>SAP.TIDMANAGER</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:invocationTimeout>5000</AEService:invocationTimeout>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.TIDManagerSession</AEService:session>\n" +
                "    </AEService:client>\n" +
                "    <AEService:client xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"endpoint.RVRPCClient\" name=\"INBOUND_TID_RPC_CLIENT\">\n" +
                "        <AEService:subject>SAP.TIDMANAGER.INBOUND</AEService:subject>\n" +
                "        <AEService:wireFormat>aeRvMsg</AEService:wireFormat>\n" +
                "        <AEService:invocationTimeout>5000</AEService:invocationTimeout>\n" +
                "        <AEService:session isRef=\"true\">#rvSession.TIDManagerSession</AEService:session>\n" +
                "    </AEService:client>\n" +
                "    <SAPAdapter:r3Inbound xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\" name=\"r3Inbound\">\n" +
                "        <SAPAdapter:name>r3Inbound</SAPAdapter:name>\n" +
                "        <SAPAdapter:type>Client</SAPAdapter:type>\n" +
                "        <SAPAdapter:helpString>RFC Client Component</SAPAdapter:helpString>\n" +
                "        <SAPAdapter:tidManagerRpcRef/>\n" +
                "        <SAPAdapter:tidFileName>tidFileInbound.tid</SAPAdapter:tidFileName>\n" +
                "        <SAPAdapter:connectionRef isRef=\"true\">/R3Connections.adr3Connections#connectionManager.R3AdapterConfigurationActiveConnections</SAPAdapter:connectionRef>\n" +
                "        <SAPAdapter:rpcRef isRef=\"true\"/>\n" +
                "        <SAPAdapter:interface xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:name>idocInbound</SAPAdapter:name>\n" +
                "            <SAPAdapter:rpcRef isRef=\"true\"/>\n" +
                "            <SAPAdapter:helpString>IDOC Inbound Interface</SAPAdapter:helpString>\n" +
                "            <SAPAdapter:classRef isRef=\"true\">/AESchemas/ae/SAPAdapter40/classes.aeschema#rpcClass.idocInbound</SAPAdapter:classRef>\n" +
                "            <SAPAdapter:poolRef isRef=\"true\">/R3Connections.adr3Connections#clientConnectionPool.R3AdapterConfigurationInboundConnection</SAPAdapter:poolRef>\n" +
                "            <SAPAdapter:deploymentRef isRef=\"true\">#inBoundDeployment.inBoundDeployment</SAPAdapter:deploymentRef>\n" +
                "            <SAPAdapter:consumerRef isRef=\"true\">/R3AdapterConfiguration.adr3#consumer.idocCMQSubscriber-R3AdapterConfiguration</SAPAdapter:consumerRef>\n" +
                "        </SAPAdapter:interface>\n" +
                "    </SAPAdapter:r3Inbound>\n" +
                "    <SAPAdapter:inBoundDeployment xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\" name=\"inBoundDeployment\">\n" +
                "        <SAPAdapter:name>inBoundDeployment</SAPAdapter:name>\n" +
                "        <SAPAdapter:idoc xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\">\n" +
                "            <SAPAdapter:name>SubscriptionService</SAPAdapter:name>\n" +
                "            <SAPAdapter:serviceName>SubscriptionService</SAPAdapter:serviceName>\n" +
                "            <SAPAdapter:idocLogMode>file</SAPAdapter:idocLogMode>\n" +
                "            <SAPAdapter:idocLogDirectory/>\n" +
                "            <SAPAdapter:idocLogFormat>xml</SAPAdapter:idocLogFormat>\n" +
                "            <SAPAdapter:threadCount>1</SAPAdapter:threadCount>\n" +
                "            <SAPAdapter:subscriberMode>false</SAPAdapter:subscriberMode>\n" +
                "            <SAPAdapter:subscriberModeIdocFormat>false</SAPAdapter:subscriberModeIdocFormat>\n" +
                "            <SAPAdapter:client>R3AdapterConfigurationInboundConnectionClient0</SAPAdapter:client>\n" +
                "            <SAPAdapter:poolRef>R3AdapterConfigurationInboundConnection</SAPAdapter:poolRef>\n" +
                "            <SAPAdapter:designer_ConsumerRef isRef=\"true\"/>\n" +
                "        </SAPAdapter:idoc>\n" +
                "        <SAPAdapter:consumer xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\"/>\n" +
                "    </SAPAdapter:inBoundDeployment>\n" +
                "    <SAPAdapter:r3OutBound xmlns:SAPAdapter=\"http://www.tibco.com/xmlns/adapter/SAPAdapter/2002\" name=\"r3OutBound\">\n" +
                "        <SAPAdapter:name>r3OutBound</SAPAdapter:name>\n" +
                "        <SAPAdapter:type>Server</SAPAdapter:type>\n" +
                "        <SAPAdapter:tidManagerRpcRef>none</SAPAdapter:tidManagerRpcRef>\n" +
                "        <SAPAdapter:rpcRef isRef=\"true\">/R3AdapterConfiguration.adr3#server.r3OutBound-RPCServer</SAPAdapter:rpcRef>\n" +
                "        <SAPAdapter:helpString>RFC Server Component</SAPAdapter:helpString>\n" +
                "        <SAPAdapter:connectionRef isRef=\"true\">/R3Connections.adr3Connections#connectionManager.R3AdapterConfigurationActiveConnections</SAPAdapter:connectionRef>\n" +
                "        <SAPAdapter:tidStopRetryTimeoutEnabled>false</SAPAdapter:tidStopRetryTimeoutEnabled>\n" +
                "        <SAPAdapter:tidFileName>tidFile.tid</SAPAdapter:tidFileName>\n" +
                "        <SAPAdapter:tidRetryCount>3</SAPAdapter:tidRetryCount>\n" +
                "        <SAPAdapter:tidRetryInterval>30000</SAPAdapter:tidRetryInterval>\n" +
                "    </SAPAdapter:r3OutBound>\n" +
                "</Repository:repository>";
        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("adr3");
        System.out.println("testValidate");
        AdbFileSinkFileCountHardcodingCheck instance = new AdbFileSinkFileCountHardcodingCheck();
        AdbFileSinkFileCountHardcodingCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString(),anyInt());
    }


    public void testValidateXmlHarcoded() {
        XmlBw5Source source = mock(XmlBw5Source.class);
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Repository:repository xmlns:Repository=\"http://www.tibco.com/xmlns/repo/types/2002\" xmlns:AESDK=\"http://www.tibco.com/xmlns/aemeta/adapter/2002\" xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <ADB:adapter xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\" name=\"ActiveDatabaseAdapterConfiguration\">\n" +
                "        <AESDK:instanceId>ActiveDatabaseAdapterConfiguration</AESDK:instanceId>\n" +
                "        <AESDK:startup>\n" +
                "            <AESDK:defaultStartup>active</AESDK:defaultStartup>\n" +
                "            <AESDK:banner>true</AESDK:banner>\n" +
                "            <AESDK:hasStdMicroAgent>true</AESDK:hasStdMicroAgent>\n" +
                "            <AESDK:stdMicroAgentName>COM.TIBCO.ADAPTER.adb.%%Deployment%%.%%InstanceId%%</AESDK:stdMicroAgentName>\n" +
                "            <AESDK:stdMicroAgentTimeout>10000</AESDK:stdMicroAgentTimeout>\n" +
                "            <AESDK:hasClassMicroAgent>%%HawkEnabled%%</AESDK:hasClassMicroAgent>\n" +
                "            <AESDK:classMicroAgentTimeout>10000</AESDK:classMicroAgentTimeout>\n" +
                "            <AESDK:classMicroAgentName>COM.TIBCO.adb.%%Deployment%%.%%InstanceId%%</AESDK:classMicroAgentName>\n" +
                "            <AESDK:startComponent>\n" +
                "                <AESDK:state>active</AESDK:state>\n" +
                "                <AESDK:name>ADBHawkDefault</AESDK:name>\n" +
                "            </AESDK:startComponent>\n" +
                "            <AESDK:defaultMicroAgentSession>ADBHawkDefault</AESDK:defaultMicroAgentSession>\n" +
                "        </AESDK:startup>\n" +
                "        <AESDK:deployment>\n" +
                "            <AESDK:advisories xsi:nil=\"true\"/>\n" +
                "            <AESDK:sessions>\n" +
                "                <AESDK:messaging>\n" +
                "                    <AESDK:rvSession isRef=\"true\">#rvSession.ADBHawkDefault</AESDK:rvSession>\n" +
                "                </AESDK:messaging>\n" +
                "            </AESDK:sessions>\n" +
                "        </AESDK:deployment>\n" +
                "        <AESDK:timers xsi:nil=\"true\"/>\n" +
                "        <AESDK:txControls xsi:nil=\"true\"/>\n" +
                "        <AESDK:reporting>\n" +
                "            <AESDK:fileSink>\n" +
                "                <AESDK:fileName>%%DirTrace%%/%%Deployment%%.%%InstanceId%%.log</AESDK:fileName>\n" +
                "                <AESDK:fileCount>3</AESDK:fileCount>\n" +
                "                <AESDK:fileLimit>30000</AESDK:fileLimit>\n" +
                "                <AESDK:appendMode>true</AESDK:appendMode>\n" +
                "                <AESDK:name>fileSink</AESDK:name>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>infoRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>debugRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>errorRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>warnRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "            </AESDK:fileSink>\n" +
                "            <AESDK:stdioSink>\n" +
                "                <AESDK:ioName>stdout</AESDK:ioName>\n" +
                "                <AESDK:name>stdioSink</AESDK:name>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>infoRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>debugRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>errorRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "                <AESDK:role>\n" +
                "                    <AESDK:name>warnRole</AESDK:name>\n" +
                "                </AESDK:role>\n" +
                "            </AESDK:stdioSink>\n" +
                "        </AESDK:reporting>\n" +
                "        <AESDK:metadata>\n" +
                "            <AESDK:loadUrl isRef=\"true\">/AESchemas/ae/ADB/adbmetadata.aeschema</AESDK:loadUrl>\n" +
                "        </AESDK:metadata>\n" +
                "        <ADB:hawk xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "            <ADB:help>TIB/Adapter for ActiveDatabase MicroAgent</ADB:help>\n" +
                "            <ADB:microAgentName>COM.TIBCO.adb.custom.%%Deployment%%.%%InstanceId%%</ADB:microAgentName>\n" +
                "            <ADB:sessionName isRef=\"true\">#rvSession.ADBHawkDefault</ADB:sessionName>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Sets the debug level for the agent (possible values 0-3)</ADB:help>\n" +
                "                <ADB:name>setDebugLevel</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>The debug level</ADB:help>\n" +
                "                    <ADB:name>DebugLevel</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Retrieve configuration information for the agent.</ADB:help>\n" +
                "                <ADB:name>showConfiguration</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Verbose flag information</ADB:help>\n" +
                "                    <ADB:name>VerboseInfo</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Debug level information</ADB:help>\n" +
                "                    <ADB:name>DebugLevelInfo</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Terminates the agent</ADB:help>\n" +
                "                <ADB:name>terminateADBagent</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Toggles the value of the verbose flag.</ADB:help>\n" +
                "                <ADB:name>toggleVerboseFlag</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Gets number of messages waiting in the event queue.</ADB:help>\n" +
                "                <ADB:name>getEventQueueSize</ADB:name>\n" +
                "                <ADB:type>ACTION_INFO</ADB:type>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Specify subscriber or request/reply listener</ADB:help>\n" +
                "                    <ADB:name>Category</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                    <ADB:legalValueChoices>Subscriber,RequestReply</ADB:legalValueChoices>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>The session which the subscriber or request/reply blongs to</ADB:help>\n" +
                "                    <ADB:name>SessionName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Number of events in the queue</ADB:help>\n" +
                "                    <ADB:name>QueueCount</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Maximum number of events in queue (0 = unlimited)</ADB:help>\n" +
                "                    <ADB:name>QueueLimit</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get statistics about the data handled by the adapter instance</ADB:help>\n" +
                "                <ADB:name>getActivityStatistics</ADB:name>\n" +
                "                <ADB:type>ACTION_INFO</ADB:type>\n" +
                "                <ADB:index>Name</ADB:index>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Get statistics by either service or operation</ADB:help>\n" +
                "                    <ADB:name>Get Subtotal By</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                    <ADB:legalValueChoices>Service,Operation</ADB:legalValueChoices>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Service Name</ADB:help>\n" +
                "                    <ADB:name>Name</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects processed</ADB:help>\n" +
                "                    <ADB:name>Total</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects successfully processed</ADB:help>\n" +
                "                    <ADB:name>Success</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects that caused an error during processing</ADB:help>\n" +
                "                    <ADB:name>Failure</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get statistics about the data handled by a particular adapter service</ADB:help>\n" +
                "                <ADB:name>getActivityStatisticsByService</ADB:name>\n" +
                "                <ADB:type>ACTION_INFO</ADB:type>\n" +
                "                <ADB:index>Operation</ADB:index>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Name of which service to get the statistic for</ADB:help>\n" +
                "                    <ADB:name>Service Name</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>The type of operation this service provides</ADB:help>\n" +
                "                    <ADB:name>Operation</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Name of the top level schema processed by this service, or the subject of a request/response service</ADB:help>\n" +
                "                    <ADB:name>SchemaName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects processed</ADB:help>\n" +
                "                    <ADB:name>Total</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects successfully processed</ADB:help>\n" +
                "                    <ADB:name>Success</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects that caused an error during processing</ADB:help>\n" +
                "                    <ADB:name>Failure</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get statistics about one operation</ADB:help>\n" +
                "                <ADB:name>getActivityStatisticsByOperation</ADB:name>\n" +
                "                <ADB:type>ACTION_INFO</ADB:type>\n" +
                "                <ADB:index>ServiceName</ADB:index>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Name of the operation</ADB:help>\n" +
                "                    <ADB:name>Operation</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                    <ADB:legalValueChoices>fetch,poll,insert,update,delete,request/reply,standard_operation,custom_operation</ADB:legalValueChoices>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Name of the Service</ADB:help>\n" +
                "                    <ADB:name>ServiceName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects processed</ADB:help>\n" +
                "                    <ADB:name>Total</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects successfully processed</ADB:help>\n" +
                "                    <ADB:name>Success</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of objects that caused an error during processing</ADB:help>\n" +
                "                    <ADB:name>Failure</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get operation counts of the current threads</ADB:help>\n" +
                "                <ADB:name>getThreadStatistics</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:index>IndexID</ADB:index>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>IndexID of the table</ADB:help>\n" +
                "                    <ADB:name>IndexID</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>A unique identification of a particular thread</ADB:help>\n" +
                "                    <ADB:name>ThreadID</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>A type that tells what part of the adapter this thread belongs to</ADB:help>\n" +
                "                    <ADB:name>ThreadType</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Description of the tasks this thread processes</ADB:help>\n" +
                "                    <ADB:name>TaskType</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Number of tasks processed by this thread</ADB:help>\n" +
                "                    <ADB:name>TaskCount</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get statistics about current count of elements in any internal queue</ADB:help>\n" +
                "                <ADB:name>getQueueStatistics</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:index>QueueID</ADB:index>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>A unique identification of a particular queue</ADB:help>\n" +
                "                    <ADB:name>QueueID</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>A type that will match this queue to a thread or connection</ADB:help>\n" +
                "                    <ADB:name>QueueType</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Current number of elements in the queue</ADB:help>\n" +
                "                    <ADB:name>QueueCount</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Maximum number of elements that can be held in this queue</ADB:help>\n" +
                "                    <ADB:name>MaxQueueSize</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Return the state and statistics for all the curent connections used by the adapter</ADB:help>\n" +
                "                <ADB:name>getConnectionStatistics</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:index>ConnectionID</ADB:index>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>A unique identification of a particular connection</ADB:help>\n" +
                "                    <ADB:name>ConnectionID</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Describe the purpose of this connection serves to the adapter</ADB:help>\n" +
                "                    <ADB:name>ConnectionType</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Current state of the connection</ADB:help>\n" +
                "                    <ADB:name>State</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of times this connection had been re-established</ADB:help>\n" +
                "                    <ADB:name>NumRetries</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of operations processed by this connection</ADB:help>\n" +
                "                    <ADB:name>TotalNumOperations</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Total number of operations processed by this connection since the last reconnection</ADB:help>\n" +
                "                    <ADB:name>CurrentNumOperations</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Reset all the counts for the activity statistics</ADB:help>\n" +
                "                <ADB:name>resetActivityStatistics</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Reset all the counts for the thread statistics</ADB:help>\n" +
                "                <ADB:name>resetThreadStatistics</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Reset all the counts for the connection statistics</ADB:help>\n" +
                "                <ADB:name>resetConnectionStatistics</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Set the polling interval setting</ADB:help>\n" +
                "                <ADB:name>setPollingInterval</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Polling interval in milliseconds</ADB:help>\n" +
                "                    <ADB:name>PollingInterval</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Publication service name</ADB:help>\n" +
                "                    <ADB:name>ServiceName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get the current polling interval setting</ADB:help>\n" +
                "                <ADB:name>getPollingInterval</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Publication service name</ADB:help>\n" +
                "                    <ADB:name>ServiceName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Polling interval in milliseconds</ADB:help>\n" +
                "                    <ADB:name>PollingInterval</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Set the polling polling batch size setting</ADB:help>\n" +
                "                <ADB:name>setPollingBatchSize</ADB:name>\n" +
                "                <ADB:type>ACTION</ADB:type>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Polling batch size</ADB:help>\n" +
                "                    <ADB:name>PollingBatchSize</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Publication service name</ADB:help>\n" +
                "                    <ADB:name>ServiceName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get the current polling batch size setting</ADB:help>\n" +
                "                <ADB:name>getPollingBatchSize</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:inputParameter>\n" +
                "                    <ADB:help>Publication service name</ADB:help>\n" +
                "                    <ADB:name>ServiceName</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:inputParameter>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Polling batch size</ADB:help>\n" +
                "                    <ADB:name>PollingBatchSize</ADB:name>\n" +
                "                    <ADB:type>integer</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "            <ADB:method xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:help>Get the setting of the perfMon option</ADB:help>\n" +
                "                <ADB:name>getPerfMonSetting</ADB:name>\n" +
                "                <ADB:type>INFO</ADB:type>\n" +
                "                <ADB:outputParameter>\n" +
                "                    <ADB:help>Value of PerfMon option</ADB:help>\n" +
                "                    <ADB:name>Setting</ADB:name>\n" +
                "                    <ADB:type>string</ADB:type>\n" +
                "                </ADB:outputParameter>\n" +
                "            </ADB:method>\n" +
                "        </ADB:hawk>\n" +
                "        <AESDK:designer>\n" +
                "            <AESDK:advancedLogging>false</AESDK:advancedLogging>\n" +
                "            <AESDK:adapterVersion>sdk51</AESDK:adapterVersion>\n" +
                "            <AESDK:timeout>30000</AESDK:timeout>\n" +
                "            <AESDK:savePassword>true</AESDK:savePassword>\n" +
                "            <AESDK:lastDBType>16</AESDK:lastDBType>\n" +
                "            <AESDK:vendor>16</AESDK:vendor>\n" +
                "            <AESDK:convertOracleNumberToString>true</AESDK:convertOracleNumberToString>\n" +
                "            <AESDK:deployOperations>true</AESDK:deployOperations>\n" +
                "            <AESDK:showExpertSettings>false</AESDK:showExpertSettings>\n" +
                "            <AESDK:identifierCase>1</AESDK:identifierCase>\n" +
                "            <AESDK:lockedProperties>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:properties>defaultSession,defaultStartup,searchUrl,defaultMicroAgentSession</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Metadata URLs/ADBMetadataRef</AESDK:resource>\n" +
                "                    <AESDK:properties>name,loadURL</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Advanced/Sessions/ADBHawkDefault</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "                <AESDK:locked>\n" +
                "                    <AESDK:resource>/Adapter Services</AESDK:resource>\n" +
                "                    <AESDK:properties>name</AESDK:properties>\n" +
                "                </AESDK:locked>\n" +
                "            </AESDK:lockedProperties>\n" +
                "            <AESDK:fixedChildren>\n" +
                "                <AESDK:fixed>\n" +
                "                    <AESDK:resource>/Advanced/Metadata URLs</AESDK:resource>\n" +
                "                    <AESDK:children>ADBMetadataRef</AESDK:children>\n" +
                "                </AESDK:fixed>\n" +
                "                <AESDK:fixed>\n" +
                "                    <AESDK:resource>/Advanced/Sessions</AESDK:resource>\n" +
                "                    <AESDK:children>ADBHawkDefault</AESDK:children>\n" +
                "                </AESDK:fixed>\n" +
                "            </AESDK:fixedChildren>\n" +
                "        </AESDK:designer>\n" +
                "        <ADB:designer xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\" xsi:nil=\"true\"/>\n" +
                "        <ADB:operations xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\"/>\n" +
                "        <ADB:activedb xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "            <ADB:defaultDataSource xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:name/>\n" +
                "                <ADB:user/>\n" +
                "                <ADB:driver>oracle.jdbc.OracleDriver</ADB:driver>\n" +
                "                <ADB:url>jdbc:oracle:thin:@&lt;machine_name&gt;:&lt;port&gt;:&lt;instance_name&gt;</ADB:url>\n" +
                "                <ADB:runtimeUser/>\n" +
                "                <ADB:runtimeUrl>jdbc:oracle:thin:@&lt;machine_name&gt;:&lt;port&gt;:&lt;instance_name&gt;</ADB:runtimeUrl>\n" +
                "                <ADB:runtimeDriver>oracle.jdbc.OracleDriver</ADB:runtimeDriver>\n" +
                "                <ADB:dbmsType>ORACLE</ADB:dbmsType>\n" +
                "                <ADB:password/>\n" +
                "                <ADB:runtimePassword/>\n" +
                "            </ADB:defaultDataSource>\n" +
                "            <ADB:threadCount xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:commInterfaces>1</ADB:commInterfaces>\n" +
                "                <ADB:commRRInterfaces>1</ADB:commRRInterfaces>\n" +
                "                <ADB:dbInterfaces>1</ADB:dbInterfaces>\n" +
                "                <ADB:dbRRInterfaces>1</ADB:dbRRInterfaces>\n" +
                "                <ADB:pubManagers>1</ADB:pubManagers>\n" +
                "                <ADB:subManagers>1</ADB:subManagers>\n" +
                "            </ADB:threadCount>\n" +
                "            <ADB:agentOptions xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:debug>2</ADB:debug>\n" +
                "                <ADB:verbose>on</ADB:verbose>\n" +
                "                <ADB:payload>false</ADB:payload>\n" +
                "                <ADB:useTraceFile/>\n" +
                "                <ADB:publishChildData>on</ADB:publishChildData>\n" +
                "                <ADB:poll>5000</ADB:poll>\n" +
                "                <ADB:maxRows>1000</ADB:maxRows>\n" +
                "                <ADB:usePollingBatchSize>true</ADB:usePollingBatchSize>\n" +
                "                <ADB:batchPubStatusUpdates>off</ADB:batchPubStatusUpdates>\n" +
                "                <ADB:pubBatchConfirmSize>0</ADB:pubBatchConfirmSize>\n" +
                "                <ADB:pubBatchConfirmTimeout>10000</ADB:pubBatchConfirmTimeout>\n" +
                "                <ADB:subBatchCommitSize>0</ADB:subBatchCommitSize>\n" +
                "                <ADB:subBatchCommitTimeout>10000</ADB:subBatchCommitTimeout>\n" +
                "                <ADB:subBulkInsertSize>1</ADB:subBulkInsertSize>\n" +
                "                <ADB:rvMaxQueueSize>0</ADB:rvMaxQueueSize>\n" +
                "                <ADB:useExceptTable>on</ADB:useExceptTable>\n" +
                "                <ADB:rebuildAfterException>off</ADB:rebuildAfterException>\n" +
                "                <ADB:jmsDestinationPrefix>%%Domain%%.%%Deployment%%.adb.%%InstanceId%%</ADB:jmsDestinationPrefix>\n" +
                "                <ADB:retryTotal>3</ADB:retryTotal>\n" +
                "                <ADB:sleepBetweenRetries>10000</ADB:sleepBetweenRetries>\n" +
                "                <ADB:retryBeforeSuspend>1</ADB:retryBeforeSuspend>\n" +
                "                <ADB:stopOnRetryFail>RETRY_FAIL_LAST_SERVICE</ADB:stopOnRetryFail>\n" +
                "                <ADB:useDesignTimeConnSetting>true</ADB:useDesignTimeConnSetting>\n" +
                "                <ADB:rpcMaxRows>0</ADB:rpcMaxRows>\n" +
                "                <ADB:scriptFileName>%%ADBScriptFileDir%%\\ActiveDatabaseAdapterConfiguration.sql</ADB:scriptFileName>\n" +
                "                <ADB:termSubject>%%Domain%%.%%Deployment%%.adb.%%InstanceId%%.exit</ADB:termSubject>\n" +
                "            </ADB:agentOptions>\n" +
                "            <ADB:activeSpaces xmlns:ADB=\"http://www.tibco.com/xmlns/adapter/ADB/2002\">\n" +
                "                <ADB:asDomainRole>REQUESTOR</ADB:asDomainRole>\n" +
                "                <ADB:asCredentials>USERPWD</ADB:asCredentials>\n" +
                "            </ADB:activeSpaces>\n" +
                "        </ADB:activedb>\n" +
                "    </ADB:adapter>\n" +
                "    <AEService:rvSession xmlns:AEService=\"http://www.tibco.com/xmlns/aemeta/services/2002\" objectType=\"session.RV\" name=\"ADBHawkDefault\">\n" +
                "        <AEService:daemon>%%TIBHawkDaemon%%</AEService:daemon>\n" +
                "        <AEService:service>%%TIBHawkService%%</AEService:service>\n" +
                "        <AEService:network>%%TIBHawkNetwork%%</AEService:network>\n" +
                "        <AEService:mode>asynchronous</AEService:mode>\n" +
                "    </AEService:rvSession>\n" +
                "</Repository:repository>";

        Document doc = TestUtils.generateDocumentFromXML(xmlContent);
        when(source.getDocument(anyBoolean())).thenReturn(doc);
        when(source.getExtension()).thenReturn("adb");


        System.out.println("testValidate");
        AdbFileSinkFileCountHardcodingCheck instance = new AdbFileSinkFileCountHardcodingCheck();
        AdbFileSinkFileCountHardcodingCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        doCallRealMethod().when(source).findAndValidateHardCodedChild(any(),any(),anyString(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedNode(any(),any(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedChild(any(),any(),anyString(),anyString());



        spyInstance.validate(source);
        Mockito.verify(source,times(1)).reportOnIssue(any(),anyInt(),anyString());
    }

}