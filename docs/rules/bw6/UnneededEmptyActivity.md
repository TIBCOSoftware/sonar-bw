# UnneededEmptyActivity

## What condition does this detect?

This rule checks if there are empty activities that are not needed

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Some times developers tends to add additional empty where they are not needed. A right-balanced presence of empty activities can improve readability of the diagram, but also it can hurt it if these are not required.

## How to fix it?

Remove the unneeded empty activity and provide the similar behaviour inside the input mapping of the activity

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
