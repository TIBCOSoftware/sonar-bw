 /*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/

 package com.tibco.utils.bw5.model;
  
 public class Constants
 {
   public enum ACTIVITY_TYPES
   {
     NJAMS_ERROR("com.faizsiegeln.njams.client.palette.Error"), 
     NJAMS_INFO("com.faizsiegeln.njams.client.palette.Info"), 
     NJAMS_SUCCESS("com.faizsiegeln.njams.client.palette.Success"), 
     NJAMS_WARN("com.faizsiegeln.njams.client.palette.Warn"), 
     NJAMS_GETCONTEXT("com.faizsiegeln.njams.client.palette.GetContext"), 
     NJAMS_SETCONTEXT("com.faizsiegeln.njams.client.palette.SetContext"), 
     SERVICE_GETCONTEXT("com.tibco.ae.tools.palettes.servicepalette.GetContextActivity"), 
     SERVICE_SETCONTEXT("com.tibco.ae.tools.palettes.servicepalette.SetContextActivity"), 
     CORE_ASSIGN("com.tibco.pe.core.AssignActivity"), 
     CORE_CALLPROCESS("com.tibco.pe.core.CallProcessActivity"), 
     CORE_CATCH("com.tibco.pe.core.CatchActivity"), 
     CORE_CHECKPOINT("com.tibco.pe.core.CheckpointActivity"), 
     CORE_CONFIRM("com.tibco.pe.core.ConfirmActivity"), 
     CORE_ENGINECOMMAND("com.tibco.pe.core.EngineCommandActivity"), 
     CORE_GENERATEERROR("com.tibco.pe.core.GenerateErrorActivity"), 
     CORE_GETSHAREDVARIABLE("com.tibco.pe.core.GetSharedVariableActivity"), 
     CORE_INSPECTOR("com.tibco.pe.core.InspectorActivity"), 
     CORE_ONERROREVENT("com.tibco.pe.core.OnErrorEventSource"), 
     CORE_ONEVENTTIMEOUT("com.tibco.pe.core.OnEventTimeoutSource"), 
     CORE_ONNOTIFICATIONEVENT("com.tibco.pe.core.OnNotificationTimeoutEventSource"), 
     CORE_ONSHUTDOWNEVENT("com.tibco.pe.core.OnShutdownEventSource"), 
     CORE_STARTUPEVENT("com.tibco.pe.core.OnStartupEventSource"), 
     CORE_RETHROW("com.tibco.pe.core.RethrowActivity"), 
     CORE_SETSHAREVARIABLE("com.tibco.pe.core.SetShareVariableActivity"), 
     CORE_WRITELOG("com.tibco.pe.core.WriteToLogActivity"), 
     ADAPTER_PUBLISHER("com.tibco.plugin.ae.AEPublisherActivity"), 
     ADAPTER_REQUESTREPLY("com.tibco.plugin.ae.AERPCRequestReplyActivity"), 
     ADAPTER_SERVER("com.tibco.plugin.ae.AERPCServerActivity"), 
     ADAPTER_SERVERFAULT("com.tibco.plugin.ae.AERPCServerFaultActivity"), 
     ADAPTER_SERVERREPLY("com.tibco.plugin.ae.AERPCServerReplyActivity"), 
     ADAPTER_WAITFOR("com.tibco.plugin.ae.AERPCServerSignalInActivity"), 
     ADAPTER_SUBSCRIBER("com.tibco.plugin.ae.AESubscriberActivity"), 
     ADAPTER_SUBSCRIBERWAITFOR("com.tibco.plugin.ae.AESubscriberSignalInActivity"), 
     COMMAND_EXEC("com.tibco.plugin.cmdexec.CmdExecActivity"), 
     FILE_COPY("com.tibco.plugin.file.FileCopyActivity"), 
     FILE_CREATE("com.tibco.plugin.file.FileCreateActivity"), 
     FILE_POOLER("com.tibco.plugin.file.FileEventSource"), 
     FILE_READ("com.tibco.plugin.file.FileReadActivity"), 
     FILE_REMOVE("com.tibco.plugin.file.FileRemoveActivity"), 
     FILE_RENAME("com.tibco.plugin.file.FileRenameActivity"), 
     FILE_WAIT("com.tibco.plugin.file.FileSignalIn"), 
     FILE_WRITE("com.tibco.plugin.file.FileWriteActivity"), 
     FTP_CD("com.tibco.plugin.ftp.FTPChangeDefaultDirActivity"), 
     FTP_DELETE("com.tibco.plugin.ftp.FTPDeleteFileActivity"), 
     FTP_DIR("com.tibco.plugin.ftp.FTPDirActivity"), 
     FTP_GET("com.tibco.plugin.ftp.FTPGetActivity"), 
     FTP_DEFAULTDIR("com.tibco.plugin.ftp.FTPGetDefaultDirActivity"), 
     FTP_MAKEREMOTEDIR("com.tibco.plugin.ftp.FTPMakeRemoteDirActivity"), 
     FTP_PUT("com.tibco.plugin.ftp.FTPPutActivity"), 
     FTP_QUOTE("com.tibco.plugin.ftp.FTPQuoteActivity"), 
     FTP_REMOVE("com.tibco.plugin.ftp.FTPRemoveRemoteDirActivity"), 
     FTP_RENAME("com.tibco.plugin.ftp.FTPRenameActivity"), 
     FTP_SYSTYPE("com.tibco.plugin.ftp.FTPSysTypeActivity"), 
     HTTP_REQUEST("com.tibco.plugin.http.client.HttpRequestActivity"), 
     HTTP_LISTENER("com.tibco.plugin.http.HTTPEventSource"), 
     HTTP_RESPONSE("com.tibco.plugin.http.HTTPResponseActivity"), 
     HTTP_WAITFOR("com.tibco.plugin.http.HTTPSignalInActivity"), 
     JAVA_CODE("com.tibco.plugin.java.JavaActivity"), 
     JAVA_LISTENER("com.tibco.plugin.java.JavaEventSource"), 
     JAVA_METHOD("com.tibco.plugin.java.JavaMethodActivity"), 
     JAVA_TOXML("com.tibco.plugin.java.JavaToXmlActivity"), 
     JAVA_JRMILOOKUP("com.tibco.plugin.java.JRMILookup"), 
     JAVA_JRMILISTENER("com.tibco.plugin.java.JRMIStarter"), 
     JAVA_FROMXML("com.tibco.plugin.java.XmlToJavaActivity"), 
     JDBC_CALL("com.tibco.plugin.jdbc.JDBCCallActivity"), 
     JDBC_DIRECT("com.tibco.plugin.jdbc.JDBCGeneralActivity"), 
     JDBC_CONNECTION("com.tibco.plugin.jdbc.JDBCGetConnectionActivity"), 
     JDBC_QUERY("com.tibco.plugin.jdbc.JDBCQueryActivity"), 
     JDBC_UPDATE("com.tibco.plugin.jdbc.JDBCUpdateActivity"), 
     JMS_QLISTENER("com.tibco.plugin.jms.JMSQueueEventSource"), 
     JMS_QGETMESSAGE("com.tibco.plugin.jms.JMSQueueGetMessageActivity"), 
     JMS_QREQUESTREPLY("com.tibco.plugin.jms.JMSQueueRequestReplyActivity"), 
     JMS_QSENDMESSAGE("com.tibco.plugin.jms.JMSQueueSendActivity"), 
     JMS_QWAITFOR("com.tibco.plugin.jms.JMSQueueSignalInActivity"), 
     JMS_REPLY("com.tibco.plugin.jms.JMSReplyActivity"), 
     JMS_TLISTENER("com.tibco.plugin.jms.JMSTopicEventSource"), 
     JMS_TPUBLISH("com.tibco.plugin.jms.JMSTopicPublishActivity"), 
     JMS_TREQUESTREPLY("com.tibco.plugin.jms.JMSTopicRequestReplyActivity"), 
     JMS_TWAITFOR("com.tibco.plugin.jms.JMSTopicSignalInActivity"), 
     MAIL_LISTENER("com.tibco.plugin.mail.MailEventSource"), 
     MAIL_SEND("com.tibco.plugin.mail.MailPubActivity"), 
     MAPPER_MAP("com.tibco.plugin.mapper.MapperActivity"), 
     PARSE_PARSE("com.tibco.plugin.parse.ParseActivity"), 
     PARSE_RENDER("com.tibco.plugin.parse.RenderActivity"), 
     SOAP_LISTENER("com.tibco.plugin.soap.SOAPEventSource"), 
     SOAP_SENDFAULT("com.tibco.plugin.soap.SOAPSendFaultActivity"), 
     SOAP_REQUESTREPLY("com.tibco.plugin.soap.SOAPSendReceiveActivity"), 
     SOAP_REPLY("com.tibco.plugin.soap.SOAPSendReplyActivity"), 
     SOAP_RETRIEVERESOURCE("com.tibco.plugin.soap.urlaccess.RetrieveResource"), 
     TCP_CLOSE("com.tibco.plugin.tcp.TCPCloseConnectionActivity"), 
     TCP_LISTENER("com.tibco.plugin.tcp.TCPEventSource"), 
     TCP_OPEN("com.tibco.plugin.tcp.TCPOpenConnectionActivity"), 
     TCP_READ("com.tibco.plugin.tcp.TCPReadActivity"), 
     TCP_WAIFOR("com.tibco.plugin.tcp.TCPSignalInActivity"), 
     TCP_WRITE("com.tibco.plugin.tcp.TCPWriteActivity"), 
     RV_LISTENER("com.tibco.plugin.tibrv.RVEventSource"), 
     RV_SEND("com.tibco.plugin.tibrv.RVPubActivity"), 
     RV_REPLY("com.tibco.plugin.tibrv.RVReplyActivity"), 
     RV_REQUESTREPLY("com.tibco.plugin.tibrv.RVRequestActivity"), 
     RV_WAITFOR("com.tibco.plugin.tibrv.RVSignalInActivity"), 
     TIMER_NULL("com.tibco.plugin.timer.NullActivity"), 
     TIMER_SLEEP("com.tibco.plugin.timer.SleepActivity"), 
     TIMER_TIMER("com.tibco.plugin.timer.TimerEventSource"), 
     TRANSACTION_SHARE("com.tibco.plugin.transaction.share.TransactionStateActivity"), 
     WAITNOTIFY_NOTIFY("com.tibco.plugin.waitnotify.NotifyActivity"), 
     WAITNOTIFY_WAIT("com.tibco.plugin.waitnotify.WaitActivity"), 
     WAITNOTIFY_LISTENER("com.tibco.plugin.waitnotify.WaitEventSource"), 
     XML_PARSE("com.tibco.plugin.xml.XMLParseActivity"), 
     XML_RENDER("com.tibco.plugin.xml.XMLRendererActivity"), 
     XML_TRANSFORM("com.tibco.plugin.xml.XMLTransformActivity"), 
     DEFAULT_START("startstate"), 
     DEFAULT_END("stopstate"), 
     DEFAULT_UNKNOWN("unknown.png");
     
     private final String name;
     
     ACTIVITY_TYPES(String name) {
       this.name = name;
     }
     
     public final String getName() {
       return this.name;
     }
     
     public static boolean exists(String name) {
       ACTIVITY_TYPES[] types = values();
       for (ACTIVITY_TYPES type : types) {
         if (type.getName().equals(name)) {
           return true;
         }
       }
       return false;
     }
   }
 }
