# TransitionLabels

## What condition does this detect?

This rule checks whether the transitions with the type 'Success With Condition' (XPath) have a proper label. This will improve code readability

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Using labels as part of the Transition with Conditions to define what the condition is referring to is very useful to ease the understanding of the process, the maintenace of it and also the its readability.

## How to fix it?

Set a Label deinition the condition check on each Transition that needs a condition to invoke the following part of the flow

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
