# JDBCWildcards

## What condition does this detect?

This rule checks whether JDBC activities are using wildcards in the query. As a good coding practice, never use wildcards in JDBC queries.

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

Wildcard like * in a JDBC query is not a good practice because you're not specificing your output interface with the database. If the database in a progressive change like adding new files and so on you can be affected, so your code is less reliable and robust as it should be.

Also for a performance perspective it is also a bad practice, because you should only ask for the fields that you need and probably this will be a subset of all the fields that are in the database table

## How to fix it?

Change the configuration of the queries that you use in your JDBC Queries to change the * for the name of the fields that you're looking for and you need.

## How do I use this rule?

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) |  [<< Return to main README file](../../../README.md)
