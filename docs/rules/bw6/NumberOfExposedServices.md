# NumberOfExposedServices

## What condition does this detect?

This rule checks the number of activities within a process

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

We know that there is no technical limitation about the number of services that you should have in your process, but it is also true that too many services reduces the process readability and maintenace of the process. So we should try to keep it at some kind of manageable size and use new process if needed so we can provide a better understanding of the existing process.

## How to fix it?

Change the PRocess Design to include more process to encapsulate part of the services that are probablty not directly related to the other ones and try to find a way to categorize and group it or just have a single service per process to have a single interface

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
