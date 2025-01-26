# ExceptionHandlingCheck

## What condition does this detect?

Check if exceptions are handled in component process.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Usually we spend more time deciding and working on how our process should behave when everything happen as designed. But it is as important as how we behave when things goes wrong and nothing happen as expected. And here is where the Exception Handling and Management comes into the place. We need to make sure we're handling errors to avoid that internal errors are directly exposed to the outside. That could lead to very different of wrong scenarios:

## How to fix it?

Make sure you add a Catch activity in your Component Process so no internal errors are send to the outside without the proper handling you define.

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
