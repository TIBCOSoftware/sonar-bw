# JMSHardCoded

## What condition does this detect?

This rule checks JMS activities for hardcoded values for fields Timeout, Destinaton, Reply to Destination, Message Selector, Polling Interval. Use Process property or Module property.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Harcode some values that could be needed to change on deployment based because the performance of an external resources like the EMS Server is a bad practice.

## How to fix it?

Change the configuration (Timeout, Destinaton, Reply to Destination, Message Selector, Polling Interval) of your JMS activities to link the JMS configuration to Property values.

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
