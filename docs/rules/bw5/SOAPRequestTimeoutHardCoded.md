# SOAPRequestTimeoutHardCoded

## What condition does this detect?

Check if timeout is hard coded in SOAP Request Reply activities

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Request Timeout hardcoded as part of the SOAP Request Activity reduce its portability when you deploy in different environments and break the externalization of the configuration of teh application

## How to fix it?

Externalize the property by using a Global Variable for that purpose

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW5 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
