# OnlyOneOtherwiseCheck

## What condition does this detect?

This rule checks multiple transition from an activity only one for the paths are for no matching condition

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

This rule checks multiple transition from an activity only one for the paths are for no matching condition because multiple ones are not supported and could lead to an unexpected runtime behavior

## How to fix it?

Change the process design to have only one Otherwise transition and in case you need to do two activities in parallel as part of the otherwise just add an Empty activity as the target of the otherwise and add the parallel definition there

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
