# JDBCTransactionParallelFlow

## What condition does this detect?

This rule checks if there is no parallel flows with JDBC activities inside a Transaction Group

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Parallel flows inside a JDBC Transaction Group will not be executed so the output of the whole Transaction Group could be unexpected

## How to fix it?

Change the design of your JDBC Transaction Group to remove the need for the Parallel configuration

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
