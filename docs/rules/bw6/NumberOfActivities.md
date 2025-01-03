# NumberOfActivities

## What condition does this detect?

This rule checks the number of activities within a process

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

We know that there is no technical limitation about the number of activities that you should have in your process, but it is also true that too many activities reduces the process readability and maintenace of the process. So we should try to keep it at some kind of manageable size and use new subprocess if needed so we can provide a better understanding of the process.

## How to fix it?

Change the PRocess Design to include more subprocess to encapsulate part of the logic that is not relevant to be in process and the level of detail the process is working

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
