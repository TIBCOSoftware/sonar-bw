# JMSAcknowledgementMode

## What condition does this detect?

This rule checks the acknowledgement mode used in JMS activities.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

We have different Acknowledgement when we are receiving messages from a JMS destination, that we can configure. If we use the AUTO that means that the message will be confirmed as soon as it is processed for the JMS Receiver activity so in case there is a failure during the process the message will never return to the queue for a retry and you need to implement your own error procedure outside the options out of the box provided by the EMS server itself

## How to fix it?

It is recommened to use the CLIENT ACK mode in your developments and provide a Confirm activity when the process has ended its work into a successful way

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
