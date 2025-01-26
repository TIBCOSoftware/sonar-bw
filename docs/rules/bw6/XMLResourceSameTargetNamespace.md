# XMLResourceSameTargetNamespace

## What condition does this detect?

Check if most that one XML Schema or WSDL file have same target namespace

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

Namespace is part of the XML schema definition and it should be used to define the scope of the definition of the components inside it. Using the same namespaces for different XML components is a bad practice and should be avoided to make sure that each XML Resource has its own namespace

## How to fix it?

Provide a unique XML namespace for each XML resource inside the project

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
