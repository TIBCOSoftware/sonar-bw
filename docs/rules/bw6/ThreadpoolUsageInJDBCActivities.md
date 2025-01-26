# ThreadpoolUsageInJDBCActivities

## What condition does this detect?

This rule check if you are setting up a ThreadPool Resource to your JDBC Activities to handle the increasing number of threads because of JDBC Activities

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

JDBC activities in TIBCO BusinessWorks™ are asynchronous ones so that means that it uses an internal thread pool to manage the execution of those activities. This internal pool is not limited and can be increased for so many reasons leading to unexpected memory increase and also performance issues. To avoid that we should set up as part of the JDBC activity configuration a Shared Resource Instance for a Threadpool Resource so we can managed ourlves the amount of threads to be used for those activities.

## How to fix it?

Check your JDBC Activity configuration and in the Advance tab provide a ThreadPool Resource Instance to be used as part of each activity

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
