# JKSValidation

## What condition does this detect?

Check the JKS inside the project to see if they've been expired or if they're autosigned

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

Security is a key aspect of today developments, and that implies not only that we use the secure practices but also that the secure components that we use to set up these practices should also being secured. In terms of the JKS that we have inside our development that implies that the certificates stored inside are still valid so they're not expired. And also that those certificates are not autosigned

## How to fix it?

Include only secure certificates as part of the JKS in terms of expiration time and also CA Authority

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
