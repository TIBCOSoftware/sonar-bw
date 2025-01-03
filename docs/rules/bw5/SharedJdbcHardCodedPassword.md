# SharedJdbcHardCodedPassword

## What condition does this detect?

Check hard password value in shared JDBC resource file

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

Password hardcoded as part of the Shared JDBC Connection reduces the portability of the project across environments and break the rule of externalize the configuration from the source code

## How to fix it?

Externalize the property by using a Global Variable for that purpose

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW5 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
