# CheckpointProcessREST

## What condition does this detect?

This rule checks the placement of a Checkpoint activity within a process. When placing your checkpoint in a process, be careful with certain types of process starters or incoming events, so that a recovered process instance does not attempt to access resources that no longer exist. For example, consider a process with an HTTP process starter that takes a checkpoint after receiving a request but before sending a response. In this case, when the engine restarts after a crash, the recovered process instance cannot respond to the request since the HTTP socket is already closed. As a best practice, do not place Checkpoint activity right after or in parallel path to HTTP activities.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?



## How to fix it?

In your developments try to do a design that require an stateless approach to be able to dont need the presence of any Checkpoint activity to store the status of the instance at that particular point.

In case you have the need or the requirement to use it try to do it

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
