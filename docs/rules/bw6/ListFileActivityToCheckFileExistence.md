# ListFileActivityToCheckFileExistence

## What condition does this detect?

Using List File activity to check if a single file exists is less performant than using ReadFile without fileContent check

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Usually when we need to check the existence of a file we have two options. First of all is use Read File activity that can raise an known fault regarding the existence of the file, and another is to List the folder content to check if the expected file is there. Both options are valid and works as expected, but the performance of the first one is quite better than the other. And also the time doesn't depend on external factors as the number of files in the folder and so on.

## How to fix it?

Check your process design and if you're using List activity to only check the persence of an expected file change that for using the Read File activity with the fileContent unchecked to make sure you're not reading the content of the file if this is not really needed

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
