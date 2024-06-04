# HttpClientMustBeUsedinHTTPBinding

## What condition does this detect?

HTTP Binding should have an HTTP Client Resource

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

HTTP Client are the resources available in BW to define HTTP Connection pools specific to connect to an specific host and allow us to configure its properties. When we define a Reference Binding that is using HTTP we have the option to specific this component or rely on a single URL address approach. As a best-practice we should specify always a HTTP Client resource to make sure we have complete control of the connection pool that it will be created and also we can configure the technical components and decide which of them we'd like to expose it as Properties to avoid any hard coding situation as part of the use of the REference Binding.

## How to fix it?

Check your binding configuration and add a HTTP Client Resource to it in case this is a HTTP Based Reference Binding

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
