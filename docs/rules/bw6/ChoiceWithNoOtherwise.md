# ChoiceWithNoOtherwise

## What condition does this detect?

This rule checks all activities input mapping for choice statement. As a coding best practice, the choice statement should always include the option otherwise.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?



## How to fix it?

When you're adding a choice option in your mapper make sure you select the option to add the otherwise action and it has a relevant mapping in case the result of the evaluation is none of the options available in your choice element

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
