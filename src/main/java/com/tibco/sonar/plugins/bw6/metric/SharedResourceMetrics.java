/*
* Copyright © 2023 - 2024. Cloud Software Group, Inc.
* This file is subject to the license terms contained
* in the license file that is distributed with this file.
*/
package com.tibco.sonar.plugins.bw6.metric;

import java.util.List;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import static java.util.Arrays.asList;

public class SharedResourceMetrics implements Metrics {

	private static final String MEASURES_DOMANIN = "BW Resources";

	public static final String BWRESOURCES_HTTP_CLIENT_KEY = "HTTPClient";
	public static final Metric<Integer> BWRESOURCES_HTTP_CLIENT = new Metric.Builder(BWRESOURCES_HTTP_CLIENT_KEY,
			"BW HTTP Clients", Metric.ValueType.INT)
			.setDescription("Total of shared HTTP connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_HTTP_CONNECTOR_KEY = "HTTPConnector";
	public static final Metric<Integer> BWRESOURCES_HTTP_CONNECTOR = new Metric.Builder(BWRESOURCES_HTTP_CONNECTOR_KEY,
			"BW HTTP Connectors", Metric.ValueType.INT)
			.setDescription("Total of shared HTTP connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_JDBC_CONNECTION_KEY = "JDBCConnection";
	public static final Metric<Integer> BWRESOURCES_JDBC_CONNECTION = new Metric.Builder(BWRESOURCES_JDBC_CONNECTION_KEY,
			"BW JDBC Connections", Metric.ValueType.INT)
			.setDescription("Total of shared JDBC connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
		
	public static final String BWRESOURCES_JMS_CONNECTION_KEY = "JMSConnection";
	public static final Metric<Integer> BWRESOURCES_JMS_CONNECTION = new Metric.Builder(BWRESOURCES_JMS_CONNECTION_KEY,
			"BW JMS Connections", Metric.ValueType.INT)
			.setDescription("Total of shared JMS connection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_XML_AUTHENTICATION_KEY = "XMLAuthentication";
	public static final Metric<Integer> BWRESOURCES_XML_AUTHENTICATION = new Metric.Builder(BWRESOURCES_XML_AUTHENTICATION_KEY,
			"BW XML Authentications", Metric.ValueType.INT)
			.setDescription("Total XMLAuthentication resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
    public static final String BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY = "SSLServerConfiguration";
	public static final Metric<Integer> BWRESOURCES_SSL_SERVER_CONFIGURATION = new Metric.Builder(BWRESOURCES_SSL_SERVER_CONFIGURATION_KEY,
			"BW SSL Server Configurations", Metric.ValueType.INT)
			.setDescription("Total SSLServerConfiguration resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY = "SSLClientConfiguration";
	public static final Metric<Integer> BWRESOURCES_SSL_CLIENT_CONFIGURATION = new Metric.Builder(BWRESOURCES_SSL_CLIENT_CONFIGURATION_KEY,
			"BW SSL Client Configurations", Metric.ValueType.INT)
			.setDescription("Total SSLClientConfiguration resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_SMTP_RESOURCE_KEY = "SMTPResource";
	public static final Metric<Integer> BWRESOURCES_SMTP_RESOURCE= new Metric.Builder(BWRESOURCES_SMTP_RESOURCE_KEY,
			"BW SMTP Resources", Metric.ValueType.INT)
			.setDescription("Total SMTPResource resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_DATA_FORMAT_KEY = "DataFormat";
	public static final Metric<Integer> BWRESOURCES_DATA_FORMAT = new Metric.Builder(BWRESOURCES_DATA_FORMAT_KEY,
			"BW Data Formats", Metric.ValueType.INT)
			.setDescription("Total DataFormat resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_SQL_FILE_KEY = "SQLFile";		
	public static final Metric<Integer> BWRESOURCES_SQL_FILE = new Metric.Builder(BWRESOURCES_SQL_FILE_KEY,
			"BW SQL Files", Metric.ValueType.INT)
			.setDescription("Total SQLFile resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY = "FTLRealmServerConnection";		
	public static final Metric<Integer> BWRESOURCES_FTL_REALM_SERVER_CONNECTION = new Metric.Builder(BWRESOURCES_FTL_REALM_SERVER_CONNECTION_KEY,
			"BW FTL Realm Server Connections", Metric.ValueType.INT)
			.setDescription("Total FTLRealmServerConnection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_FTP_CONNECTION_KEY = "FTPConnection";		
	public static final Metric<Integer> BWRESOURCES_FTP_CONNECTION = new Metric.Builder(BWRESOURCES_FTP_CONNECTION_KEY,
			"BW FTP Connections", Metric.ValueType.INT)
			.setDescription("Total FTPConnection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_IDENTITY_PROVIDER_KEY = "IdentityProvider";			
	public static final Metric<Integer> BWRESOURCES_IDENTITY_PROVIDER = new Metric.Builder(BWRESOURCES_IDENTITY_PROVIDER_KEY,
			"BW Identity Providers", Metric.ValueType.INT)
			.setDescription("Total IdentityProvider resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY = "JavaGlobalInstance";		
	public static final Metric<Integer> BWRESOURCES_JAVA_GLOBAL_INSTANCE = new Metric.Builder(BWRESOURCES_JAVA_GLOBAL_INSTANCE_KEY,
			"BW Java Global Instances", Metric.ValueType.INT)
			.setDescription("Total JavaGlobalInstance resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
    public static final String BWRESOURCES_JNDI_CONFIGURATION_KEY = "JNDIConfiguration";
	public static final Metric<Integer> BWRESOURCES_JNDI_CONFIGURATION = new Metric.Builder(BWRESOURCES_JNDI_CONFIGURATION_KEY,
			"BW JNDI Configurations", Metric.ValueType.INT)
			.setDescription("Total JNDIConfiguration resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_KEYSTORE_PROVIDER_KEY = 	"KeystoreProvider";	
	public static final Metric<Integer> BWRESOURCES_KEYSTORE_PROVIDER = new Metric.Builder(BWRESOURCES_KEYSTORE_PROVIDER_KEY,
			"BW Keystore Providers", Metric.ValueType.INT)
			.setDescription("Total KeystoreProvider resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_LDAP_AUTHENTICATION_KEY = "LDAPAuthentication"; 		
	public static final Metric<Integer> BWRESOURCES_LDAP_AUTHENTICATION = new Metric.Builder(BWRESOURCES_LDAP_AUTHENTICATION_KEY,
			"BW LDAP Authentications", Metric.ValueType.INT)
			.setDescription("Total LDAPAuthentication resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_PROXY_CONFIGURATION_KEY = "ProxyConfiguration";
	public static final Metric<Integer> BWRESOURCES_PROXY_CONFIGURATION = new Metric.Builder(BWRESOURCES_PROXY_CONFIGURATION_KEY,
			"BW Proxy Configurations", Metric.ValueType.INT)
			.setDescription("Total  ProxyConfigurationresources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_TRUST_PROVIDER_KEY = "TrustProvider"; 	
	public static final Metric<Integer> BWRESOURCES_TRUST_PROVIDER = new Metric.Builder(BWRESOURCES_TRUST_PROVIDER_KEY,
			"BW Trust Providers", Metric.ValueType.INT)
			.setDescription("Total TrustProvider resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
    public static final String BWRESOURCES_THREAD_POOL_KEY = "ThrealPool";
	public static final Metric<Integer> BWRESOURCES_THREAD_POOL = new Metric.Builder(BWRESOURCES_THREAD_POOL_KEY,
			"BW Threal Pools", Metric.ValueType.INT)
			.setDescription("Total ThrealPool resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_SUBJECT_PROVIDER_KEY = "SubjectProvider";
	public static final Metric<Integer> BWRESOURCES_SUBJECT_PROVIDER = new Metric.Builder(BWRESOURCES_SUBJECT_PROVIDER_KEY,
			"BW Subject Providers", Metric.ValueType.INT)
			.setDescription("Total SubjectProviderresources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();
	
	public static final String BWRESOURCES_TCP_CONNECTION_KEY = "TCPConnection";
	public static final Metric<Integer> BWRESOURCES_TCP_CONNECTION = new Metric.Builder(BWRESOURCES_TCP_CONNECTION_KEY,
			"BW TCP Connections", Metric.ValueType.INT)
			.setDescription("Total TCPConnection resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_WSS_AUTHENTICATION_KEY = "WSSAuthentication";	
	public static final Metric<Integer> BWRESOURCES_WSS_AUTHENTICATION = new Metric.Builder(BWRESOURCES_WSS_AUTHENTICATION_KEY,
			"BW WSS Authentications", Metric.ValueType.INT)
			.setDescription("Total WSSAuthentication resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

	public static final String BWRESOURCES_RV_TRANSPORT_KEY = "RendezvousTransport";	
	public static final Metric<Integer> BWRESOURCES_RV_TRANSPORT = new Metric.Builder(BWRESOURCES_RV_TRANSPORT_KEY,
	         "RV Transport", Metric.ValueType.INT)
			.setDescription("Total BW RV Transport resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN)
			.create();

	public static final String BWRESOURCES_UNKNOWN_KEY = "UNKNOWN";
	public static final Metric<Integer> BWRESOURCES_UNKNOWN = new Metric.Builder(BWRESOURCES_UNKNOWN_KEY,
			"Unknown Resources", Metric.ValueType.INT)
			.setDescription("Total of shared Unknown resources")
			.setDirection(Metric.DIRECTION_WORST).setQualitative(false)
			.setDomain(MEASURES_DOMANIN).create();

  @Override
  public List<Metric> getMetrics() {
    return asList(
	  BWRESOURCES_HTTP_CLIENT,
      BWRESOURCES_XML_AUTHENTICATION,
      BWRESOURCES_WSS_AUTHENTICATION,
      BWRESOURCES_TRUST_PROVIDER,
      BWRESOURCES_TCP_CONNECTION,
      BWRESOURCES_SUBJECT_PROVIDER,
      BWRESOURCES_SSL_SERVER_CONFIGURATION,
      BWRESOURCES_SSL_CLIENT_CONFIGURATION,
      BWRESOURCES_SMTP_RESOURCE,
      BWRESOURCES_RV_TRANSPORT,
      BWRESOURCES_PROXY_CONFIGURATION,
      BWRESOURCES_LDAP_AUTHENTICATION,
      BWRESOURCES_KEYSTORE_PROVIDER,
      BWRESOURCES_JNDI_CONFIGURATION,
      BWRESOURCES_JMS_CONNECTION,
      BWRESOURCES_JDBC_CONNECTION,
      BWRESOURCES_JAVA_GLOBAL_INSTANCE,
      BWRESOURCES_IDENTITY_PROVIDER,
      BWRESOURCES_HTTP_CONNECTOR,
      BWRESOURCES_FTP_CONNECTION,
      BWRESOURCES_FTL_REALM_SERVER_CONNECTION,
      BWRESOURCES_DATA_FORMAT,
	  BWRESOURCES_SQL_FILE,
	  BWRESOURCES_THREAD_POOL,
			BWRESOURCES_UNKNOWN);
  }
}
