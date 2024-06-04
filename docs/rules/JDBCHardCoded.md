# JDBCHardCoded

## What condition does this detect?

This rule checks JDBC activities for hardcoded values for fields Timeout and MaxRows. Use Process property or Module property.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Harcode some values that could be needed to change on deployment based because the performance of an external resources like the Database is a bad practice. Regarding the timeout it is directly impacted for environment details: performance of the database server, network latency and so on. Regarding the maximum number of rows is directly impacted of the data that is store in the database, so should be always mapped with properties that we can update without needed to repackage the software

## How to fix it?

Change the configuration of your JDBC activities to link the timeout configuration and maximum rows to a Property value

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
