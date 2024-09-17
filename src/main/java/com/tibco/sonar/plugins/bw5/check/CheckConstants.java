/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tibco.sonar.plugins.bw5.check;

/**
 *
 * @author avazquez
 */
public class CheckConstants {

    

public static final String RULE_PROCESS_PROCESSCATCHALL_NAME="Process Catch All";
public static final String RULE_PROCESS_PROCESSCATCHALL_DESCRIPTION="Check if a catch all activity is present in process file";
public static final String RULE_PROCESS_PROCESSCATCHBWEXCEPTION_NAME="Process Catch BWException";
public static final String RULE_PROCESS_PROCESSCATCHBWEXCEPTION_DESCRIPTION="Check if a catch BWException activity is present in process file";
public static final String RULE_PROCESS_CUSTOMPROCESSCATCH_NAME="Custom Catch Check";
public static final String RULE_PROCESS_CUSTOMPROCESSCATCH_DESCRIPTION="Check if a custom catch activity is present in process file";
public static final String RULE_PROCESS_PROCESSNODESCRIPTION_NAME="Process No Description";
public static final String RULE_PROCESS_PROCESSNODESCRIPTION_DESCRIPTION="Check if a description exists in process file";
public static final String RULE_PROCESS_APPLICATIONJSONCHECK_NAME="Application JsonCheck";
public static final String RULE_PROCESS_APPLICATIONJSONCHECK_DESCRIPTION="Check the Type should not be application-json it should be application/json";
public static final String RULE_PROCESS_DEADPROCESSCHECKFORSTARTERPROCESS_NAME="Dead Process Check For Starter Process";
public static final String RULE_PROCESS_DEADPROCESSCHECKFORSTARTERPROCESS_DESCRIPTION="Check the unused starter process( Dead Code)_";
public static final String RULE_PROCESS_DEADPROCESSCHECKFORSUBPROCESS_NAME="Dead Process Check For Sub Process";
public static final String RULE_PROCESS_DEADPROCESSCHECKFORSUBPROCESS_DESCRIPTION="Check the unused sub process( Dead Code)_";
public static final String RULE_PROCESS_DEADPROCESSCHECKFORDYNAMICSUBPROCESS_NAME="Dead Process Check For Dynamic Sub Process";
public static final String RULE_PROCESS_DEADPROCESSCHECKFORDYNAMICSUBPROCESS_DESCRIPTION="Check the dynamic unused sub process_";
public static final String RULE_PROCESS_LOGLEVELMAPPINGCHECK_NAME="Log Level Mapping Check";
public static final String RULE_PROCESS_LOGLEVELMAPPINGCHECK_DESCRIPTION="Check the Log Level_ It should always be mapped with ServiceLevelLog_";
public static final String RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_NAME="Custom Hard Coded Check";
public static final String RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_DESCRIPTION="Check if a configuration value is hard coded in a specific activity type";
public static final String RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_CONFIGXPATH="XPath query to get the element node in standard configuration";
public static final String RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_INPUTBINDINGXPATH="XPath query to get the element node in mapping configuration";
public static final String RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_ACTIVITYTYPE="The activity type (<pd:type/> element content in the process file)";
public static final String RULE_PROCESS_CUSTOMHARDCODEDACTIVITY_MESSAGE="The violation message_";
public static final String RULE_PROCESS_HTTPREQUESTHOSTHARDCODED_NAME="HTTP Request Host Hard Coded";
public static final String RULE_PROCESS_HTTPREQUESTHOSTHARDCODED_DESCRIPTION="Check if host is hard coded in HTTP Request Reply activities";
public static final String RULE_PROCESS_HTTPREQUESTPORTHARDCODED_NAME="HTTP Request Port Hard Coded";
public static final String RULE_PROCESS_HTTPREQUESTPORTHARDCODED_DESCRIPTION="Check if port is hard coded in HTTP Request Reply activities";
public static final String RULE_PROCESS_HTTPREQUESTTIMEOUTHARDCODED_NAME="HTTP Request Timeout Hard Coded";
public static final String RULE_PROCESS_HTTPREQUESTTIMEOUTHARDCODED_DESCRIPTION="Check if timeout is hard coded in HTTP Request Reply activities";
public static final String RULE_PROCESS_HTTPREQUESTURIHARDCODED_NAME="HTTP Request Uri Hard Coded";
public static final String RULE_PROCESS_HTTPREQUESTURIHARDCODED_DESCRIPTION="Check if URI is hard coded in HTTP Request Reply activities";
public static final String RULE_PROCESS_JMSQUEUERECEIVERDESTINATIONHARDCODED_NAME="JMS Queue Receiver Destination Hard Coded";
public static final String RULE_PROCESS_JMSQUEUERECEIVERDESTINATIONHARDCODED_DESCRIPTION="Check if destination queue is hard coded in JMS Queue Receiver activities";
public static final String RULE_PROCESS_JMSQUEUERECEIVERMAXSESSIONSHARDCODED_NAME="JMS Queue Receiver Max Sessions Hard Coded";
public static final String RULE_PROCESS_JMSQUEUERECEIVERMAXSESSIONSHARDCODED_DESCRIPTION="Check if max sessions is hard coded in JMS Queue Receiver activities";
public static final String RULE_PROCESS_JMSQUEUEREQUESTORDESTINATIONHARDCODED_NAME="JMS Queue Requestor Destination Hard Coded";
public static final String RULE_PROCESS_JMSQUEUEREQUESTORDESTINATIONHARDCODED_DESCRIPTION="Check if destination queue is hard coded in JMS Queue Requestor activities";
public static final String RULE_PROCESS_JMSQUEUEREQUESTORTIMEOUTHARDCODED_NAME="JMS Queue Requestor Time out Hard Coded";
public static final String RULE_PROCESS_JMSQUEUEREQUESTORTIMEOUTHARDCODED_DESCRIPTION="Check if timeout (JMSExpiration) is hard coded in JMS Queue Requestor activities";
public static final String RULE_PROCESS_JMSQUEUESENDERDESTINATIONHARDCODED_NAME="JMS Queue Sender Destination Hard Coded";
public static final String RULE_PROCESS_JMSQUEUESENDERDESTINATIONHARDCODED_DESCRIPTION="Check if destination queue is hard coded in JMS Queue Sender activities";
public static final String RULE_PROCESS_JMSTOPICPUBLISHERDESTINATIONHARDCODED_NAME="JMS Topic Publisher Destination Hard Coded";
public static final String RULE_PROCESS_JMSTOPICPUBLISHERDESTINATIONHARDCODED_DESCRIPTION="Check if destination topic is hard coded in JMS Topic Publisher activities";
public static final String RULE_PROCESS_JMSTOPICSUBSCRIBERDESTINATIONHARDCODED_NAME="JMS Topic Subscriber Destination Hard Coded";
public static final String RULE_PROCESS_JMSTOPICSUBSCRIBERDESTINATIONHARDCODED_DESCRIPTION="Check if destination topic is hard coded in JMS Topic Subscriber activities";
public static final String RULE_PROCESS_SOAPRECEIVERSOAPACTIONHARDCODED_NAME="SOAP Receiver Soap Action Hard Coded";
public static final String RULE_PROCESS_SOAPRECEIVERSOAPACTIONHARDCODED_DESCRIPTION="Check if soapAction is hard coded in SOAP Receiver activities";
public static final String RULE_PROCESS_SOAPREQUESTSOAPACTIONHARDCODED_NAME="SOAP Request Soap Action Hard Coded";
public static final String RULE_PROCESS_SOAPREQUESTSOAPACTIONHARDCODED_DESCRIPTION="Check if soapAction is hard coded in SOAP Request Reply activities";
public static final String RULE_PROCESS_SOAPREQUESTTIMEOUTHARDCODED_NAME="SOAP Request Timeout Hard Coded";
public static final String RULE_PROCESS_SOAPREQUESTTIMEOUTHARDCODED_DESCRIPTION="Check if timeout is hard coded in SOAP Request Reply activities";
public static final String RULE_PROCESS_SOAPREQUESTURLHARDCODED_NAME="SOAP Request Url Hard Coded";
public static final String RULE_PROCESS_SOAPREQUESTURLHARDCODED_DESCRIPTION="Check if URL is hard coded in SOAP Request Reply activities";
public static final String RULE_SHAREDHTTP_SHAREDHTTPHARDCODEDHOST_NAME="Shared Http Hard Coded Host";
public static final String RULE_SHAREDHTTP_SHAREDHTTPHARDCODEDHOST_DESCRIPTION="Check hard coded host in shared HTTP resource file";
public static final String RULE_SHAREDHTTP_SHAREDHTTPHARDCODEDPORT_NAME="Shared Http Hard Coded Port";
public static final String RULE_SHAREDHTTP_SHAREDHTTPHARDCODEDPORT_DESCRIPTION="Check hard coded port in shared HTTP resource file";
public static final String RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDPASSWORD_NAME="Shared Jdbc Hard Coded Password";
public static final String RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDPASSWORD_DESCRIPTION="Check hard password value in shared JDBC resource file";
public static final String RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDURL_NAME="Shared Jdbc Hard Coded Url";
public static final String RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDURL_DESCRIPTION="Check hard coded url in shared JDBC resource file";
public static final String RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDUSER_NAME="Shared Jdbc Hard Coded User";
public static final String RULE_SHAREDJDBC_SHAREDJDBCHARDCODEDUSER_DESCRIPTION="Check hard coded user in shared JDBC resource file";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIPASSWORD_NAME="Shared Jms Hard Coded Jndi Password";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIPASSWORD_DESCRIPTION="Check hard coded JNDI password in shared JMS resource file";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIURL_NAME="Shared Jms Hard Coded Jndi Url";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIURL_DESCRIPTION="Check hard coded JNDI url in shared JMS resource file";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIUSER_NAME="Shared Jms Hard Coded Jndi User";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDJNDIUSER_DESCRIPTION="Check hard coded JNDI user in shared JMS resource file";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDPASSWORD_NAME="Shared Jms Hard Coded Password";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDPASSWORD_DESCRIPTION="Check hard coded password in shared JMS resource file";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDURL_NAME="Shared Jms Hard Coded Url";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDURL_DESCRIPTION="Check hard coded url in shared JMS resource file";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDUSER_NAME="Shared Jms Hard Coded User";
public static final String RULE_SHAREDJMS_SHAREDJMSHARDCODEDUSER_DESCRIPTION="Check hard coded url in shared JMS resource file";
public static final String RULE_SAPADAPTER_SAPFILESINKFILECOUNTHARDCODINGCHECK_NAME="Sap File Sink File Count Hard coding Check";
public static final String RULE_SAPADAPTER_SAPFILESINKFILECOUNTHARDCODINGCHECK_DESCRIPTION="Check hard coded fileCount in FileSink in SAP Adapter file";
public static final String RULE_SAPADAPTER_SAPFILESINKFILELIMITHARDCODINGCHECK_NAME="Sap File Sink File Limit Hard coding Check";
public static final String RULE_SAPADAPTER_SAPFILESINKFILELIMITHARDCODINGCHECK_DESCRIPTION="Check hard coded fileLimit in FileSink in SAP Adapter file";
public static final String RULE_SAPADAPTER_SAPRPCCLIENTSUBJECTHARDCODINGCHECK_NAME="Sap RPC Client Subject Hardcoding Check";
public static final String RULE_SAPADAPTER_SAPRPCCLIENTSUBJECTHARDCODINGCHECK_DESCRIPTION="Check hard coded Subject in RPC Client in SAP Adapter file";
public static final String RULE_ADBADAPTER_ADBFILESINKFILECOUNTHARDCODINGCHECK_NAME="Adb File Sink File Count Hard coding Check";
public static final String RULE_ADBADAPTER_ADBFILESINKFILECOUNTHARDCODINGCHECK_DESCRIPTION="Check hard coded fileCount in FileSink in ADB Adapter file";
public static final String RULE_ADBADAPTER_ADBFILESINKFILELIMITHARDCODINGCHECK_NAME="Adb File Sink File Limit Hardcoding Check";
public static final String RULE_ADBADAPTER_ADBFILESINKFILELIMITHARDCODINGCHECK_DESCRIPTION="Check hard coded fileLimit in FileSink in ADB Adapter file";


}
