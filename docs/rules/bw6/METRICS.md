# Metrics

This plugin contributes a number of TIBCO BW6-specific metrics to the SonarQube system.

SonarQube metrics are a type of measurement. Metrics can have varying values, or measures, over time. Examples: number of lines of code, complexity, etc.

Metrics are used in quality gates. For example you can define a gate rule that limits the number of conditions that an application should have. 

## TIBCO BW6 Specific Metrics

| Measure                            | Function                                                                                           | Type       |
| ---------------------------------- | -------------------------------------------------------------------------------------------------- | ---------- |
| `BW Data Formats`                  | Number of data formats used in BW                                                                  | Size       |
| `BW FTL Realm Server Connections`  | Number of FTL Realm Server connections                                                             | Size       |
| `BW FTP Connections`               | Number of FTP connections configured                                                               | Size       |
| `BW HTTP Clients`                  | Number of HTTP client configurations                                                               | Size       |
| `BW HTTP Connectors`               | Number of HTTP connectors configured                                                               | Size       |
| `BW Identity Providers`            | Number of identity providers configured                                                            | Size       |
| `BW JDBC Connections`              | Number of JDBC connections configured                                                              | Size       |
| `BW JMS Connections`               | Number of JMS connections configured                                                               | Size       |
| `BW JNDI Configurations`           | Number of JNDI configurations                                                                      | Size       |
| `BW Java Global Instances`         | Number of Java global instances configured                                                         | Size       |
| `BW Keystore Providers`            | Number of keystore providers configured                                                            | Size       |
| `BW LDAP Authentications`          | Number of LDAP authentications configured                                                          | Size       |
| `BW Proxy Configurations`          | Number of proxy configurations                                                                     | Size       |
| `BW SMTP Resources`                | Number of SMTP resources configured                                                                | Size       |
| `BW SQL Files`                     | Number of SQL files used                                                                           | Size       |
| `BW SSL Client Configurations`     | Number of SSL client configurations                                                                | Size       |
| `BW SSL Server Configurations`     | Number of SSL server configurations                                                                | Size       |
| `BW Subject Providers`             | Number of subject providers configured                                                             | Size       |
| `BW TCP Connections`               | Number of TCP connections configured                                                               | Size       |
| `BW Threal Pools`                  | Number of thread pools configured                                                                  | Size       |
| `BW Trust Providers`               | Number of trust providers configured                                                               | Size       |
| `BW WSS Authentications`           | Number of WSS authentications configured                                                           | Size       |
| `BW XML Authentications`           | Number of XML authentications configured                                                           | Size       |
| `RV Transport`                     | Number of RV transport configurations                                                              | Size       |

## Standard SonarQube Metrics

| Measure         | Function                                                                                                                             | Type |
| --------------- | ------------------------------------------------------------------------------------------------------------------------------------ | ---- |
| `Lines of Code` | Total number of activities and none-default transitions within the application - used to calculate the SonarQube license requirement | Size |
| `Statements`    | Total number of activities and trigger handlers                                                                                      | Size |
| `Comment Lines` | Total number of activities that have descriptions                                                                                    | Size |

---

[< Return to Rules list](./rules/RULES.md) | [<< Return to main README file](../README.md)
