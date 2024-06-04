# HttpClientSSLShouldHaveConfidentiality

## What condition does this detect?

HTTP Client using 443 port should have set confidentiality settings

This is an ***Resource*** rule - the rule will test each resource of the application

## Why is this condition important?

Nowadays is not only important to create code that works but also code that is secure. Because of that is mandatory to provide some kind of confidentiality technique to be able to not allow anyone from the outside to call our service and generate a security breach. That's why we should use SSL as part of our HTTP communications

## How to fix it?

Add confidentiality options to the HTTP Client resource we have inside our project

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
