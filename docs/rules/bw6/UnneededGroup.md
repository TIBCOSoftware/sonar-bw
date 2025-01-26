# UnneededGroup

## What condition does this detect?

This rule checks if there are groups that are not needed

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Some times developers tends to add additional groups where they are not needed. Because they think its easier to do some logic mainly loops that some times can be done directly inside the input mapping on the activity. In those cases we should recommend to remove it to clarify the process

## How to fix it?

Remove the unneded groups and provide the similar behavior inside the input mapping of the activity

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
