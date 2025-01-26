# CheckpointProcessJDBC

## What condition does this detect?

This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint after or in a parallel flow of Query activities or idempotent activities. Database operations such as Update, Insert and Delete are considered non-idempotent operations. You should always place a checkpoint immediately after any database insert or update activity to persist the response. However, for queries, there is no need to place checkpoints.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?



## How to fix it?

In your developments try to do a design that require an stateless approach to be able to dont need the presence of any Checkpoint activity to store the status of the instance at that particular point.

In case you have the need or the requirement to use it try to do it on process that they don't require

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
