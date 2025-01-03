# RenderXmlPrettyPrint

## What condition does this detect?

This rule checks for inefficiencies on using tib:render-xml function specifying the pretty-print option to true

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Pretty-print as part of the render-xml is inefficient as it requires more time to do the render process because it also need to indent it, and additional to that pretty print as it add new lines also breaks the Log Aggregation approach that most companies have today. So based on that this should be avoided

## How to fix it?

Change your render-xml invocations to set to false the pretty print option

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
