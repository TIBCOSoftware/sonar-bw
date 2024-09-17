# ParseXMLFromRender

## What condition does this detect?

This rule checks for inefficiencies on using ParseXML activities using tib:render-xml

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

This rule checks for inefficiencies on using ParseXML activities using tib:render-xml as input when it should rely on Coertion to do same job. If we have a tib:render-xml that because we have a known structure first with a Node so it was already parsed, so going from Parsed --> Text --> Parsed is inefficient and it should be avoided. You will get more performance and better readability of the process as we are removing some activities as well

## How to fix it?

Change your process design to avoid using ParseXML using a tib:render-xml as the input of it and change it for a coertion on the next activity that you need that data to

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
