# BindingShouldNotHaveHTTPBasicPolicyAssociated

## What condition does this detect?

To ensure that the communications are authentified all input connections should check that the binding has a policy associated that is secure

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

Nowadays is not only important to create code that works but also code that is secure. Because of that is mandatory to provide some kind of authentication technique to be able to not allow anyone from the outside to call our service and generate a security breach. Additional to that there are some authentication methods that are not considered secured anymore. Methods like HTTP Basic Authentication is no longer considered a secure way to do it as it doesn't provide any encryption about how the credentials are being sent

## How to fix it?

Change the Authentication method from Basic Authentication to another more secured

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)