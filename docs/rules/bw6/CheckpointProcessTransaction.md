# CheckpointProcessTransaction

## What condition does this detect?

This rule checks the placement of a Checkpoint activity within a process. Do not place checkpoint within or in parallel to a Transaction Group or a Critical Section Group. Checkpoint activities should be placed at points that are guaranteed to be reached before or after the transaction group is reached.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?



## How to fix it?

In your developments try to do a design that require an stateless approach to be able to dont need the presence of any Checkpoint activity to store the status of the instance at that particular point.

In case you have the need or the requirement to use it try to do it

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
