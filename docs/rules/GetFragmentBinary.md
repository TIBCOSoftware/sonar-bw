# GetFragmentBinary

## What condition does this detect?

GetFragment should use binary mode for performance assestment

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

GetFragment acitivty are part of the Large XML palette that is meant to be use when we're processing very big XML file so the way of processing should change for the traditional approach of the ParseXML activity. When we can read each of the fragments of the XML file is important that we choose the binary format because it will allow us to do a more efficient read operation and the same time to require less memory to store the result of the activity as part of it.

## How to fix it?

Check the configuration of your Get Fragment acitivty and select the box that says that it will work in Binary Mode.

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
