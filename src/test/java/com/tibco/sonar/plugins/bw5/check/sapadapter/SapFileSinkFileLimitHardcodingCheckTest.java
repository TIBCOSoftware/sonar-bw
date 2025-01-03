package com.tibco.sonar.plugins.bw5.check.sapadapter;

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

public class SapFileSinkFileLimitHardcodingCheckTest extends TestCase {

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
        SapFileSinkFileLimitHardcodingCheck instance = new SapFileSinkFileLimitHardcodingCheck();
        SapFileSinkFileLimitHardcodingCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        spyInstance.validate(source);
        Mockito.verify(spyInstance,times(0)).reportIssueOnFile(anyString(),anyInt());
    }


    public void testValidateXmlHarcoded() {
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
        SapFileSinkFileLimitHardcodingCheck instance = new SapFileSinkFileLimitHardcodingCheck();
        SapFileSinkFileLimitHardcodingCheck spyInstance = Mockito.spy(instance);
        doNothing().when(spyInstance).reportIssueOnFile(any(),anyInt());
        doCallRealMethod().when(source).findAndValidateHardCodedChild(any(),any(),anyString(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedNode(any(),any(),anyString());
        doCallRealMethod().when(source).getViolationsHardCodedChild(any(),any(),anyString(),anyString());

        spyInstance.validate(source);
        Mockito.verify(source,times(1)).reportOnIssue(any(),anyInt(),anyString());
    }
}