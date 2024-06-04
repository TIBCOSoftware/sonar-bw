# ParseXMLBinary

## What condition does this detect?

ParseXML should use binary mode for performance assestment

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

ParseXML activities supports text and binary mode, but for performance reasons the binary mode is much more performance than the text one. So try to rely on the binary mode to increase the performance of your processes.

## How to fix it?

Change the ParseXML configuration to use the binary mode and also the component that is feeding the data to the activity to work on the binary mode

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)