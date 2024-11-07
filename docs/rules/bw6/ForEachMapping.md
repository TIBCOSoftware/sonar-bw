# ForEachMapping

## What condition does this detect?

This rule checks the Input mappings of activities. In activity Input mapping for performance reasons, it is recommended ato use Copy-Of instead of For-Each whenever possible.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

copy-of clause are much more performant rather a loop iteration from the root element and also it requires less memory to compute.

## How to fix it?

Check if you have available the copy-of when you're doing the mapping from your component to the target one to use the most efficient mapping solution

If you are certain that the mapping is correect then disable the rule for this activity only. Add the following to the activity description tab: <br/><br/> **` SQIGNORE:ForEachMapping `**

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
