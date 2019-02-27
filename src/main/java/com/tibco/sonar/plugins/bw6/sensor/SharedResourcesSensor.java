package com.tibco.sonar.plugins.bw6.sensor;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.measure.Metric;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import java.util.HashMap;
import java.util.Map;
import org.sonar.api.utils.log.Loggers;
import com.tibco.sonar.plugins.bw6.metric.SharedResourceMetrics;

public class SharedResourcesSensor implements Sensor {
  @Override
  public void describe(SensorDescriptor descriptor) {
    descriptor.name("Compute size of Shared Resources");
  }

  public static Map<String, Integer> foundResources = new HashMap<String, Integer>(); 
  public static Map<String, String> resourceExtensionMapper = new HashMap<String,String>();

  @Override
  public void execute(SensorContext context) {
    FileSystem fs = context.fileSystem();
   
   createResourceExtensionMapper(resourceExtensionMapper);
   
   Iterable<InputFile> files = fs.inputFiles(fs.predicates().hasType(InputFile.Type.MAIN));
   
   Loggers.get(getClass()).info("Searching for BW6 Resources");
   for (InputFile file : files) {
        Loggers.get(getClass()).info("Found File: " + file.filename()); 
        String extension = file.filename().substring(file.filename().lastIndexOf("."));
        String resourceType = resourceExtensionMapper.get(extension);
        if (resourceType != null){
            Loggers.get(getClass()).info("Found BW6 Resource " + resourceType + " " + file.filename());
            context.<Integer>newMeasure()
              .forMetric(getSharedResourceMetric(resourceType))
              .on(file)
              .withValue(1)
              .save();
        }
   } 
   Loggers.get(getClass()).info("Completed Search of BW6 Resources");
     
}

private Metric<Integer> getSharedResourceMetric(String resourceType)
{
    switch (resourceType) {
        case SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT_KEY:
            return SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT;
        case SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION_KEY:
            return SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION; 
        case SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION_KEY:
            return SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION;
        case SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER_KEY:
            return SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER;
        case SharedResourceMetrics.BWRESOURCES_THREAD_POOL_KEY:
            return SharedResourceMetrics.BWRESOURCES_THREAD_POOL;
        case SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION_KEY:
            return SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION;
        case SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER_KEY:
            return SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER;
        case SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY:
            return SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION;
        case SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY:
             return SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION;
        case SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE_KEY:
            return SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE;
        case SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT_KEY:
            return SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT;
        case SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION_KEY:
            return SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION;
        case SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION_KEY:
            return SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION;
        case SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER_KEY:
            return SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER;
        case SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION_KEY:
            return SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION;
        case SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION_KEY:
            return SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION;
        case SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION_KEY:
            return SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION;
        case SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY:
            return SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE;
        case SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER_KEY:
            return SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER; 
        case SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR_KEY:
            return SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR;
        case SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION_KEY:
            return SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION;
        case SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY:
            return SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION;
        case SharedResourceMetrics.BWRESOURCES_DATA_FORMAT_KEY:
            return SharedResourceMetrics.BWRESOURCES_DATA_FORMAT;
        case SharedResourceMetrics.BWRESOURCES_SQL_FILE_KEY:
            return SharedResourceMetrics.BWRESOURCES_SQL_FILE;
        default:
            // TODO: NEED A UNKOWN .....
            return SharedResourceMetrics.BWRESOURCES_SQL_FILE;
        }
}

/** 
 public static int getSharedResourcesCount(File directory) {
    int count = 0;
    for (File file : directory.listFiles()) {
        if (file.isFile()) {
            String name = file.getName();
            String extension = name.substring(name.lastIndexOf("."));
            String resourceType = resourceExtensionMapper.get(extension);
            if(resourceType != null){
                if(foundResources.get(resourceType) == null)
                    foundResources.put(resourceType, 1);
                else
                    foundResources.put(resourceType, foundResources.get(resourceType) + 1);
                count++;
            }
        }
        if (file.isDirectory()) {
            count += getSharedResourcesCount(file);
        }
    }
    return count;
}
*/

public void createResourceExtensionMapper(Map<String, String> resourceExtensionMapper){
    resourceExtensionMapper.put(".httpClientResource", SharedResourceMetrics.BWRESOURCES_HTTP_CLIENT_KEY);
    resourceExtensionMapper.put(".authxml", SharedResourceMetrics.BWRESOURCES_XML_AUTHENTICATION_KEY);
    resourceExtensionMapper.put(".wssResource",SharedResourceMetrics.BWRESOURCES_WSS_AUTHENTICATION_KEY);
    resourceExtensionMapper.put(".trustResource",SharedResourceMetrics.BWRESOURCES_TRUST_PROVIDER_KEY);
    resourceExtensionMapper.put(".threadPoolResource",SharedResourceMetrics.BWRESOURCES_THREAD_POOL_KEY);
    resourceExtensionMapper.put(".tcpResource", SharedResourceMetrics.BWRESOURCES_TCP_CONNECTION_KEY);
    resourceExtensionMapper.put(".sipResource", SharedResourceMetrics.BWRESOURCES_SUBJECT_PROVIDER_KEY);
    resourceExtensionMapper.put(".sslServerResource", SharedResourceMetrics.BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY);
    resourceExtensionMapper.put(".sslClientResource", SharedResourceMetrics.BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY);
    resourceExtensionMapper.put(".smtpResource",SharedResourceMetrics.BWRESOURCES_SMTP_RESOURCE_KEY);
    resourceExtensionMapper.put(".rvResource",SharedResourceMetrics.BWRESOURCES_RV_TRANSPORT_KEY);
    resourceExtensionMapper.put(".httpProxyResource",SharedResourceMetrics.BWRESOURCES_PROXY_CONFIGURATION_KEY);
    resourceExtensionMapper.put(".ldapResource",SharedResourceMetrics.BWRESOURCES_LDAP_AUTHENTICATION_KEY);
    resourceExtensionMapper.put(".keystoreProviderResource",SharedResourceMetrics.BWRESOURCES_KEYSTORE_PROVIDER_KEY);
    resourceExtensionMapper.put(".jndiConfigResource",SharedResourceMetrics.BWRESOURCES_JNDI_CONFIGURATION_KEY);
    resourceExtensionMapper.put(".jmsConnResource",SharedResourceMetrics.BWRESOURCES_JMS_CONNECTION_KEY);
    resourceExtensionMapper.put(".jdbcResource",SharedResourceMetrics.BWRESOURCES_JDBC_CONNECTION_KEY);
    resourceExtensionMapper.put(".javaGlobalInstanceResource",SharedResourceMetrics.BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY);
    resourceExtensionMapper.put(".userIdResource",SharedResourceMetrics.BWRESOURCES_IDENTITY_PROVIDER_KEY);
    resourceExtensionMapper.put(".httpConnResource",SharedResourceMetrics.BWRESOURCES_HTTP_CONNECTOR_KEY);
    resourceExtensionMapper.put(".ftpResource",SharedResourceMetrics.BWRESOURCES_FTP_CONNECTION_KEY);
    resourceExtensionMapper.put(".ftlResource",SharedResourceMetrics.BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY);
    resourceExtensionMapper.put(".dataFormatResource",SharedResourceMetrics.BWRESOURCES_DATA_FORMAT_KEY);
    resourceExtensionMapper.put(".sql",SharedResourceMetrics.BWRESOURCES_SQL_FILE_KEY);
}

}