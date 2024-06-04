# JMSRequestReplyNonPersistent

## What condition does this detect?

JMS Request/Reply shoud use NON-PERSISTENT messages

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

When you have a Request/Reply activity using JMS this is meant for an online-synchronous communication, that means that the option to persist the message in the EMS is not relevant because it is expected to get a reply at this specific moment, so if the receiver goes down and continue to process later the message will be nobody to answer to. So there is no real advatange of persisting the message in this configuration but we're losing performance as part of it.

## How to fix it?

Review the configuration of your JMS Request/Reply activities to mark it as NON_PERSISTENT in the Advanced Configuration

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
