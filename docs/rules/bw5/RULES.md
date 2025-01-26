# Available Quality Rules

This version of the plugin provides ***42 quality rules*** and ***10 metrics***. Note that not all of the rules will be available by default. Some rules are disabled - as they may not be applicable to all installations - and some are templates. These must be instantiated as required and configuration parameters provided.

| Name | Type | Has Parameters | Initial  State | Description |
| ---- | ---- | -------------- | -------------- | ----------- |
| [`CustomHardCodedActivity`](CustomHardCodedActivity.md) | Process | No | Enabled | Check if a configuration value is hard coded in a specific activity type |
| [`CustomProcessCatch`](CustomProcessCatch.md) | Process | No | Enabled | Check if a custom catch activity is present in process file |
| [`DeadProcessCheckForStarterProcess`](DeadProcessCheckForStarterProcess.md) | Process | No | Enabled | Check the unused starter process( Dead Code). |
| [`DeadProcessCheckForSubProcess`](DeadProcessCheckForSubProcess.md) | Process | No | Enabled | Check the unused sub process( Dead Code). |
| [`HTTPRequestHostHardCoded`](HTTPRequestHostHardCoded.md) | Process | No | Enabled | Check if host is hard coded in HTTP Request Reply activities |
| [`HTTPRequestPortHardCoded`](HTTPRequestPortHardCoded.md) | Process | No | Enabled | Check if port is hard coded in HTTP Request Reply activities |
| [`HTTPRequestTimeoutHardCoded`](HTTPRequestTimeoutHardCoded.md) | Process | No | Enabled | Check if timeout is hard coded in HTTP Request Reply activities |
| [`HTTPRequestUriHardCoded`](HTTPRequestUriHardCoded.md) | Process | No | Enabled | Check if URI is hard coded in HTTP Request Reply activities |
| [`JMSQueueReceiverDestinationHardCoded`](JMSQueueReceiverDestinationHardCoded.md) | Process | No | Enabled | Check if destination queue is hard coded in JMS Queue Receiver activities |
| [`JMSQueueReceiverMaxSessionsHardCoded`](JMSQueueReceiverMaxSessionsHardCoded.md) | Process | No | Enabled | Check if max sessions is hard coded in JMS Queue Receiver activities |
| [`JMSQueueRequestorDestinationHardCoded`](JMSQueueRequestorDestinationHardCoded.md) | Process | No | Enabled | Check if destination queue is hard coded in JMS Queue Requestor activities |
| [`JMSQueueRequestorTimeoutHardCoded`](JMSQueueRequestorTimeoutHardCoded.md) | Process | No | Enabled | Check if timeout is hard coded in JMS Queue Requestor activities |
| [`JMSQueueSenderDestinationHardCoded`](JMSQueueSenderDestinationHardCoded.md) | Process | No | Enabled | Check if destination queue is hard coded in JMS Queue Sender activities |
| [`JMSTopicPublisherDestinationHardCoded`](JMSTopicPublisherDestinationHardCoded.md) | Process | No | Enabled | Check if destination topic is hard coded in JMS Topic Publisher activities |
| [`JMSTopicSubscriberDestinationHardCoded`](JMSTopicSubscriberDestinationHardCoded.md) | Process | No | Enabled | Check if destination topic is hard coded in JMS Topic Subscriber activities |
| [`NoOtherwiseCheck`](NoOtherwiseCheck.md) | Process | No | Enabled | This rule checks multiple transition from an activity at least exists one path for no matching condition |
| [`NumberOfActivities`](NumberOfActivities.md) | Process | No | Enabled | This rule checks the number of activities within a process |
| [`OnlyOneOtherwiseCheck`](OnlyOneOtherwiseCheck.md) | Process | No | Enabled | This rule checks multiple transition from an activity only one for the paths are for no matching condition |
| [`ProcessCatchAll`](ProcessCatchAll.md) | Process | No | Enabled | Check if a catch all activity is present in process file |
| [`ProcessNamingConvention`](ProcessNamingConvention.md) | Process | No | Enabled | This rule ensure the naming convention for process names |
| [`ProcessNoDescription`](ProcessNoDescription.md) | Process | No | Enabled | Check if a description exists in process file |
| [`RenderXmlPrettyPrint`](RenderXmlPrettyPrint.md) | Process | No | Enabled | This rule checks for inefficiencies on using tib:render-xml function specifying the pretty-print option to true |
| [`SOAPReceiverSoapActionHardCoded`](SOAPReceiverSoapActionHardCoded.md) | Process | No | Enabled | Check if soapAction is hard coded in SOAP Receiver activities |
| [`SOAPRequestSoapActionHardCoded`](SOAPRequestSoapActionHardCoded.md) | Process | No | Enabled | Check if soapAction is hard coded in SOAP Request Reply activities |
| [`SOAPRequestTimeoutHardCoded`](SOAPRequestTimeoutHardCoded.md) | Process | No | Enabled | Check if timeout is hard coded in SOAP Request Reply activities |
| [`SOAPRequestUrlHardCoded`](SOAPRequestUrlHardCoded.md) | Process | No | Enabled | Check if URL is hard coded in SOAP Request Reply activities |
| [`SapFileSinkFileCountHardcodingCheck`](SapFileSinkFileCountHardcodingCheck.md) | Project | No | Enabled | Check hard coded fileCount in FileSink in SAP Adapter file |
| [`SapFileSinkFileLimitHardcodingCheck`](SapFileSinkFileLimitHardcodingCheck.md) | Project | No | Enabled | Check hard coded fileLimit in FileSink in SAP Adapter file |
| [`SapRPCClientSubjectHardcodingCheck`](SapRPCClientSubjectHardcodingCheck.md) | Project | No | Enabled | Check hard coded Subject in RPC Client in SAP Adapter file |
| [`SharedHttpHardCodedHost`](SharedHttpHardCodedHost.md) | Project | No | Enabled | Check hard coded host in shared HTTP resource file |
| [`SharedHttpHardCodedPort`](SharedHttpHardCodedPort.md) | Project | No | Enabled | Check hard coded port in shared HTTP resource file |
| [`SharedJdbcHardCodedPassword`](SharedJdbcHardCodedPassword.md) | Project | No | Enabled | Check hard password value in shared JDBC resource file |
| [`SharedJdbcHardCodedUrl`](SharedJdbcHardCodedUrl.md) | Project | No | Enabled | Check hard coded url in shared JDBC resource file |
| [`SharedJdbcHardCodedUser`](SharedJdbcHardCodedUser.md) | Project | No | Enabled | Check hard coded user in shared JDBC resource file |
| [`SharedJmsHardCodedJndiPassword`](SharedJmsHardCodedJndiPassword.md) | Project | No | Enabled | Check hard coded JNDI password in shared JMS resource file |
| [`SharedJmsHardCodedJndiUrl`](SharedJmsHardCodedJndiUrl.md) | Project | No | Enabled | Check hard coded JNDI url in shared JMS resource file |
| [`SharedJmsHardCodedJndiUser`](SharedJmsHardCodedJndiUser.md) | Project | No | Enabled | Check hard coded JNDI user in shared JMS resource file |
| [`SharedJmsHardCodedPassword`](SharedJmsHardCodedPassword.md) | Project | No | Enabled | Check hard coded password in shared JMS resource file |
| [`SharedJmsHardCodedUrl`](SharedJmsHardCodedUrl.md) | Project | No | Enabled | Check hard coded url in shared JMS resource file |
| [`SharedJmsHardCodedUser`](SharedJmsHardCodedUser.md) | Project | No | Enabled | No description available. |
| [`TransitionLabels`](TransitionLabels.md) | Process | No | Enabled | This rule checks whether the transitions with the type 'Success With Condition' (XPath) have a proper label. This will improve code readability |
| [`XPathCheck`](XPathCheck.md) | Project | No | Disabled | This is a template rule that allow to add custom XPath Checks if required |

---
[<< Return to main README file](../../../README.md)
