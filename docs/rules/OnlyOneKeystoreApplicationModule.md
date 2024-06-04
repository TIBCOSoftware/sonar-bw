# OnlyOneKeystoreApplicationModule

## What condition does this detect?

This rule checks if there are a single KeyStore resource for application

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

Have different Keystore REsources can lead to a difficult management different resources as part of the same code, so this rule helps us to keep this important resources types at a manageable level

## How to fix it?

Ensure that you only have one Keystore Provider as part of your development that includes all the certificates and key that you could need

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
