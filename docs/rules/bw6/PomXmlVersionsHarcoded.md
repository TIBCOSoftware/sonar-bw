# PomXmlVersionsHarcoded

## What condition does this detect?

Check if the dependency version are being defined using a property or hardcoded

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

If you're using Maven nature in your project you know well that the way that you manage the version of your dependencies is quite important. So that shouldn't be never hardcoded to ease the software currency approach.

## How to fix it?

Define properties inside the pom.xml to use in the version tag for each dependency that is included in the pom.xml

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
