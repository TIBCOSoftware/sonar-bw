# Available Quality Rules

This version of the plugin provides ***62 quality rules*** and [***0 metrics***](../METRICS.md). Note that not all of the rules will be available by default. Some rules are disabled - as they may not be applicable to all installations - and some are templates. These must be instantiated as required and configuration parameters provided.

| Name | Type | Has Parameters | Initial  State | Description |
| ---- | ---- | -------------- | -------------- | ----------- |
| [`AtLeastOneStarter`](AtLeastOneStarter.md) | Project | No | Enabled | Check that an application module should have one starter additional to the Activator process |
| [`BWVersionCheck`](BWVersionCheck.md) | Project | No | Enabled | Check if this BusinessWorks Module matches recommended version which has better performance and less vulnerabilities |
| [`BindingShouldHavePolicyAssociated`](BindingShouldHavePolicyAssociated.md) | Project | No | Enabled | To ensure that the communications are authentified all input connections should check that the binding has a policy associated |
| [`BindingShouldNotHaveHTTPBasicPolicyAssociated`](BindingShouldNotHaveHTTPBasicPolicyAssociated.md) | Project | No | Enabled | To ensure that the communications are authentified all input connections should check that the binding has a policy associated that is secure |
| [`BwSharedResourceUsingModuleProperty`](BwSharedResourceUsingModuleProperty.md) | Resource | No | Enabled | Parameter Resource using Module Property |
| [`CheckpointProcessHTTP`](CheckpointProcessHTTP.md) | Process | No | Enabled | This rule checks the placement of a Checkpoint activity within a process. When placing your checkpoint in a process, be careful with certain types of process starters or incoming events, so that a recovered process instance does not attempt to access resources that no longer exist. For example, consider a process with an HTTP process starter that takes a checkpoint after receiving a request but before sending a response. In this case, when the engine restarts after a crash, the recovered process instance cannot respond to the request since the HTTP socket is already closed. As a best practice, do not place Checkpoint activity right after or in parallel path to HTTP activities. |
| [`CheckpointProcessJDBC`](CheckpointProcessJDBC.md) | Process | No | Enabled | This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint after or in a parallel flow of Query activities or idempotent activities. Database operations such as Update, Insert and Delete are considered non-idempotent operations. You should always place a checkpoint immediately after any database insert or update activity to persist the response. However, for queries, there is no need to place checkpoints. |
| [`CheckpointProcessREST`](CheckpointProcessREST.md) | Process | No | Enabled | This rule checks the placement of a Checkpoint activity within a process. When placing your checkpoint in a process, be careful with certain types of process starters or incoming events, so that a recovered process instance does not attempt to access resources that no longer exist. For example, consider a process with an HTTP process starter that takes a checkpoint after receiving a request but before sending a response. In this case, when the engine restarts after a crash, the recovered process instance cannot respond to the request since the HTTP socket is already closed. As a best practice, do not place Checkpoint activity right after or in parallel path to HTTP activities. |
| [`CheckpointProcessTransaction`](CheckpointProcessTransaction.md) | Process | No | Enabled | This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint within or in parallel to a Transaction Group or a Critical Section Group. Checkpoint activities should be placed at points that are guaranteed to be reached before or after the transaction group is reached. |
| [`ChoiceWithNoOtherwise`](ChoiceWithNoOtherwise.md) | Process | No | Enabled | This rule checks all activities input mapping for choice statement. As a coding best practice, the choice statement should always include the option otherwise. |
| [`CriticalSection`](CriticalSection.md) | Process | No | Enabled | Critical section groups cause multiple concurrently running process instances to wait for one process instance to execute the activities in the group. As a result, there may be performance implications when using these groups. This rules checks that the Critical Section group does not include any activities that wait for incoming events or have long durations, such as Request/Reply activities, Wait For (Signal-In) activities, Sleep activity, or other activities that require a long time to execute. |
| [`DeadlockDetection`](DeadlockDetection.md) | Process | No | Enabled | There are many situations in which deadlocks can be created between communicating web services. This rule checks for deadlocks and infinite loops in BW6 process design. |
| [`DefaultTargetNamespace`](DefaultTargetNamespace.md) | Process | No | Enabled | This rule checks if process namespace is the default one generated by the tool. |
| [`EndpointURIFromHTTPBindingSetUsingProperty`](EndpointURIFromHTTPBindingSetUsingProperty.md) | Project | No | Enabled | Endpoint URI from SOAP/HTTP Binding should be set using a Module Property |
| [`ExceptionHandlingCheck`](ExceptionHandlingCheck.md) | Process | No | Enabled | Check if exceptions are handled in component process. |
| [`ForEachMapping`](ForEachMapping.md) | Process | No | Enabled | This rule checks the Input mappings of activities. In activity Input mapping for performance reasons, it is recommended ato use Copy-Of instead of For-Each whenever possible. |
| [`GetFragmentBinary`](GetFragmentBinary.md) | Process | No | Enabled | GetFragment should use binary mode for performance assestment |
| [`HttpClientMustBeUsedinHTTPBinding`](HttpClientMustBeUsedinHTTPBinding.md) | Process | No | Enabled | HTTP Binding should have an HTTP Client Resource |
| [`HttpClientSSLShouldHaveConfidentiality`](HttpClientSSLShouldHaveConfidentiality.md) | Resource | No | Enabled | HTTP Client using 443 port should have set confidentiality settings |
| [`HttpConnectorShouldHaveConfidentiality`](HttpConnectorShouldHaveConfidentiality.md) | Resource | No | Enabled | HTTP Connector should have set confidentiality settings |
| [`IsMavenProject`](IsMavenProject.md) | Project | No | Enabled | Check is this BusinessWorks Module is a Maven Project |
| [`JDBCHardCoded`](JDBCHardCoded.md) | Process | No | Enabled | This rule checks JDBC activities for hardcoded values for fields Timeout and MaxRows. Use Process property or Module property. |
| [`JDBCTransactionParallelFlow`](JDBCTransactionParallelFlow.md) | Process | No | Enabled | This rule checks if there is no parallel flows with JDBC activities inside a Transaction Group |
| [`JDBCWildcards`](JDBCWildcards.md) | Process | No | Enabled | This rule checks whether JDBC activities are using wildcards in the query. As a good coding practice, never use wildcards in JDBC queries. |
| [`JKSValidation`](JKSValidation.md) | Project | No | Enabled | Check the JKS inside the project to see if they've been expired or if they're autosigned |
| [`JMSAcknowledgementMode`](JMSAcknowledgementMode.md) | Process | No | Enabled | This rule checks the acknowledgement mode used in JMS activities. |
| [`JMSConnectorShouldHaveConfidentiality`](JMSConnectorShouldHaveConfidentiality.md) | Resource | No | Enabled | JMS Connector should have set confidentiality settings |
| [`JMSHardCoded`](JMSHardCoded.md) | Process | No | Enabled | This rule checks JMS activities for hardcoded values for fields Timeout, Destinaton, Reply to Destination, Message Selector, Polling Interval. Use Process property or Module property. |
| [`JMSReceiverPlusConfirm`](JMSReceiverPlusConfirm.md) | Process | No | Enabled | Confirm activity should cover all OK flows with a JMS Receiver if CLIENT ACK Mode is Selected. |
| [`JMSRequestReplyNonPersistent`](JMSRequestReplyNonPersistent.md) | Process | No | Enabled | JMS Request/Reply shoud use NON-PERSISTENT messages |
| [`LastActivityAndEndActivity`](LastActivityAndEndActivity.md) | Process | No | Enabled | This rule checks all flows are finished properly using an end activity |
| [`ListFileActivityToCheckFileExistence`](ListFileActivityToCheckFileExistence.md) | Process | No | Enabled | Using List File activity to check if a single file exists is less performant than using ReadFile without fileContent check |
| [`LogSubprocess`](LogSubprocess.md) | Process | No | Enabled | This rule checks if the Log activity is not being used in component process and only used in subprocess |
| [`MultipleTransitions`](MultipleTransitions.md) | Process | No | Enabled | This rule checks whether multiple transitions from an activity in a parallel flow merge into EMPTY activity |
| [`NoOtherwiseCheck`](NoOtherwiseCheck.md) | Process | No | Enabled | This rule checks multiple transition from an activity at least exists one path for no matching condition |
| [`NumberOfActivities`](NumberOfActivities.md) | Process | No | Enabled | This rule checks the number of activities within a process |
| [`NumberOfExposedServices`](NumberOfExposedServices.md) | Process | No | Enabled | This rule checks the number of activities within a process |
| [`NumberOfPropertiesSameGroup`](NumberOfPropertiesSameGroup.md) | Project | No | Enabled | Check the maximum of module properties that you should have together in the same group to ensure a proper and maintable property organization |
| [`OnlyOneKeystoreApplicationModule`](OnlyOneKeystoreApplicationModule.md) | Project | No | Enabled | This rule checks if there are a single KeyStore resource for application |
| [`OnlyOneOtherwiseCheck`](OnlyOneOtherwiseCheck.md) | Process | No | Enabled | This rule checks multiple transition from an activity only one for the paths are for no matching condition |
| [`ParseXMLBinary`](ParseXMLBinary.md) | Process | No | Enabled | ParseXML should use binary mode for performance assestment |
| [`ParseXMLFromRender`](ParseXMLFromRender.md) | Process | No | Enabled | This rule checks for inefficiencies on using ParseXML activities using tib:render-xml |
| [`ParseXMLRenderXMLActivity`](ParseXMLRenderXMLActivity.md) | Process | No | Enabled | This rule checks for inefficiencies on using ParseXML activities using RenderXML activity output |
| [`PomXmlVersionsHarcoded`](PomXmlVersionsHarcoded.md) | Project | No | Enabled | Check if the dependency version are being defined using a property or hardcoded |
| [`ProcessNamingConvention`](ProcessNamingConvention.md) | Process | No | Enabled | This rule ensure the naming convention for process names |
| [`ProcessNoDescription`](ProcessNoDescription.md) | Process | No | Enabled | This rule checks if there is description specified for a process. |
| [`ProcessWithoutTest`](ProcessWithoutTest.md) | Process | No | Enabled | This rule checks that all processes should have at least one test file |
| [`ProjectStructure`](ProjectStructure.md) | Project | No | Enabled | Check that an application module has the right structure |
| [`RenderXMLBinary`](RenderXMLBinary.md) | Process | No | Enabled | RenderXML should use binary mode for performance assestment |
| [`RenderXmlPrettyPrint`](RenderXmlPrettyPrint.md) | Process | No | Enabled | This rule checks for inefficiencies on using tib:render-xml function specifying the pretty-print option to true |
| [`SFTPPutBinary`](SFTPPutBinary.md) | Process | No | Enabled | SFTP Put should use binary mode for avoiding encoding issues when transferring |
| [`SSLClientConnectorShouldHaveTLSprotocol`](SSLClientConnectorShouldHaveTLSprotocol.md) | Resource | No | Enabled | SSLClient Connector should use recommended TLS protocol to secure all communications between a client and a server |
| [`SSLServerConnectorShouldHaveTLSprotocol`](SSLServerConnectorShouldHaveTLSprotocol.md) | Resource | No | Enabled | SSLServer Connector should use recommended TLS protocol to secure all communications between a client and a server |
| [`SharedResourcesNotUsed`](SharedResourcesNotUsed.md) | Resource | No | Enabled | "Shared Resource not used |
| [`SubProcessInlineCheck`](SubProcessInlineCheck.md) | Process | No | Enabled | This rule checks if there is large set of data being passed everytime to Inline SubProcess. |
| [`SwaggerValidation`](SwaggerValidation.md) | Project | No | Enabled | Check if the Swagger files inside the project are valid or have some errors |
| [`ThreadpoolUsageInJDBCActivities`](ThreadpoolUsageInJDBCActivities.md) | Process | No | Enabled | This rule check if you are setting up a ThreadPool Resource to your JDBC Activities to handle the increasing number of threads because of JDBC Activities |
| [`TransitionLabels`](TransitionLabels.md) | Process | No | Enabled | This rule checks whether the transitions with the type 'Success With Condition' (XPath) have a proper label. This will improve code readability |
| [`UnneededEmptyActivity`](UnneededEmptyActivity.md) | Process | No | Enabled | This rule checks if there are empty activities that are not needed |
| [`UnneededGroup`](UnneededGroup.md) | Process | No | Enabled | This rule checks if there are groups that are not needed |
| [`XMLResourceSameTargetNamespace`](XMLResourceSameTargetNamespace.md) | Project | No | Enabled | Check if most that one XML Schema or WSDL file have same target namespace |
| [`XPathCheck`](XPathCheck.md) | Project | No | Disabled | This is a template rule that allow to add custom XPath Checks if required |

---
[< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
