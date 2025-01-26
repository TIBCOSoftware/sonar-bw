# IsMavenProject

## What condition does this detect?

Check is this BusinessWorks™ Module is a Maven Project

This is an ***Application*** rule - the rule will test for some condition within the application

## Why is this condition important?

The usage of Maven nature for the project it allows an easy integration with third party tools and also integrated with the CI/CD pipeline.

## How to fix it?

Set the Maven Nature for each project. To do that, you need to select the TIBCO BusinessWorks™ Application and in the context menu click on the Generate POM for Application. If that options is not present installation of the TIBCO Maven Plugin is required. This plugin can be obtained from the following link: https://github.com/TIBCOSoftware/bw6-plugin-maven

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
