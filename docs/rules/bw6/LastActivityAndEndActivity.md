# LastActivityAndEndActivity

## What condition does this detect?

This rule checks all flows are finished properly using an end activity

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

For a mainteance perspective and to clear the process design is important that all the flows are finalized with an end activity. That means that is more understandable that the logic ends here by purpose, instead of just having the last functional activity as part of the flow. When we talk about End activities we're talking about the following ones: End, Reply, Exit, Throw or Rethrow.

## How to fix it?

Review your process design to make sure that all the flows are ened with one of the end activities shown above

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
