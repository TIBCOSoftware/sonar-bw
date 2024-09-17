# CriticalSection

## What condition does this detect?

Critical section groups cause multiple concurrently running process instances to wait for one process instance to execute the activities in the group. As a result, there may be performance implications when using these groups. This rules checks that the Critical Section group does not include any activities that wait for incoming events or have long durations, such as Request/Reply activities, Wait For (Signal-In) activities, Sleep activity, or other activities that require a long time to execute.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

If we include activities as part of the critical section that requires intervention from the outside like receiving a HTTP Request or a Signal this can lead to an unexpected amount of time waiting for that action that can contribute to stop the processing on other process instances waiting for the same critical section group to process, this can contribute to heep so many instances in the engine and contribute to process slower but also to have higher resource consumption as a result for it.

## How to fix it?

Try to minime the use of the critical section group only for the specific logic that is needed and try to keep inside those group the minimal number of activities that are required and that there is a maximum of time of waiting allowed. In case you have activities that interacts with other systems or components outside your process try to add realistic timeout to make sure that the maximum waiting time is defined.

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
