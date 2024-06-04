# LogSubprocess

## What condition does this detect?

This rule checks if the Log activity is not being used in component process and only used in subprocess

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

If there is logging or auditing required at multiple points in your project, its advised to write logging and auditing code in a sub process and invoke this process from any point where this functionality is required. This rule checks whether LOG activity is used in sub process

## How to fix it?

Define a common subprocess to manage all aspects related to logging and auditing your process and call that process from any other point in your development that requires that logic

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
