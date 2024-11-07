# CustomHardCodedActivity

## What condition does this detect?

Check if a configuration value is hard coded in a specific activity type

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Hardcoded values goes against the best practices of externalize the configuration to be able to adapt to different environments without changing the source code

## How to fix it?

Externalize the requested hardcoded values to use Global Variables

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW5 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
