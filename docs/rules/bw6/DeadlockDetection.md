# DeadlockDetection

## What condition does this detect?

There are many situations in which deadlocks can be created between communicating web services. This rule checks for deadlocks and infinite loops in BW6 process design.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

In computing programing Deadlock is a situation where a set of processes are blocked because each process is holding a resource and waiting for another resource acquired by some other process.

In terms of BusinessWorks™, this can happen when we have parallel flows and each of them depends on the other. This is something easy to detect when the dependency is direct but sometimes with the definition of many subprocess this dependency can be hidden, so we should be aware that there dependency is no there even if the relation is not so clear. Also relevant to this topic is the loop-infinite deadlock. As part of dependency situation you can have a process that can be calling it itself again in some special circunstances and generate this infinity loop that not also create a failure scenario but also a waste of your computing resources. </thead>

## How to fix it?

We should review and fix our design to make sure there is no possible situation when a deadlock can be produced. That could require split different process to make sure some path is never reached or just fix some specific logic to avoid that situation

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
