# NoOtherwiseCheck

## What condition does this detect?

This rule checks multiple transition from an activity at least exists one path for no matching condition

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

This rule checks for the existence of a transition with the non-matching condition to ensure the process handles all different options and not only the happy path, to ensure process is well design.

## How to fix it?

Add the missing "Otherwise transition to the process design and provide the right behaviour of the process in case the previous condition was not matched

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
