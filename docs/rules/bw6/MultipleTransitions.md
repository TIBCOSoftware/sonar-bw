# MultipleTransitions

## What condition does this detect?

This rule checks whether multiple transitions from an activity in a parallel flow merge into EMPTY activity

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

EMPTY activity should be used if you want to join multiple transition flows. For example, there are multiple transitions out of an activity and each transition takes a different path in the process. In this scenario you can create a transition from the activity at the end of each path to an Empty activity to resume a single flow of execution in the process.

## How to fix it?

Review your process design for merging flows without an Empty activity and to ease the readability and maintenance of the process add a Empty activity to just merge those flows

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
