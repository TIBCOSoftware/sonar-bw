# EndpointURIFromHTTPBindingSetUsingProperty

## What condition does this detect?

Endpoint URI from SOAP/HTTP Binding should be set using a Module Property

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

When you have a binding inside your project and it is HTTP based one of the elements to be provided in the Endpoint URI. Endpoint URI can change between environments so it is important to keep it configurable without needed to change the code base

## How to fix it?

Set the Endpoint URI for the binding as a Module Property so this can be updated at deployment time

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
