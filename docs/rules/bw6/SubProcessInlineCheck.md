# SubProcessInlineCheck

## What condition does this detect?

This rule checks if there is large set of data being passed everytime to Inline SubProcess.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

This rule checks if there is large set of data being passed everytime to Inline SubProcess. Use of Job Shared Variable is recommended in this scenario to increase performance.

## How to fix it?

Use of Job Shared Variable is recommended in this scenario to increase performance.

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
