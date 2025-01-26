# JMSReceiverPlusConfirm

## What condition does this detect?

Confirm activity should cover all OK flows with a JMS Receiver if CLIENT ACK Mode is Selected.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

When we have JMS Receiver with a CLIENT ACK that means that it is expected that we acknoweldge the reception of the message after we process it. So we need to include at the end of all the OK flows a Confirm Activity to make sure the message is mark as processed by the server and we start processing new messages. Avoiding this could lead to an unexpected behavior with some messages retrying to process when they already have been processed.

## How to fix it?

Review your process design to make sure all the OK flows are covered with a Confirm activity

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
