# SOAPReceiverSoapActionHardCoded

## What condition does this detect?

Check if soapAction is hard coded in SOAP Receiver activities

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

SOAP Action hardcoded as part of the SOAP Receiver Activity reduce its portability when you deploy in different environments and break the externalization of the configuration of teh application

## How to fix it?

Externalize the property by using a Global Variable for that purpose

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW5 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
