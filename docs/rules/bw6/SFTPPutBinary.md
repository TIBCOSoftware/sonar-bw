# SFTPPutBinary

## What condition does this detect?

SFTP Put should use binary mode for avoiding encoding issues when transferring

This is a ***Process*** rule - the rule will test each process of the application

## Why is this condition important?

When we're updating files to an FTP/SFTP server if we're using the ASCII we are dependant on the codification used inside our text files, so to avoid that use always the Binary option for the SFTP Put Activity to make sure no transformation is done as part of the transferring process

## How to fix it?

Change the configuration of your SFTP Put activity to set the option to Binary Transmission

## How do I use this rule?

### Using within SonarQube

The rule is **_enabled_** by default. To disable it if unwanted, clone the default "**`BW6 Quality Profile`**" quality profile and then disable the rule.

---
[< Return to Rules list](./RULES.md) | [< Return to STANDALONE operation](../STANDALONE.md) | [<< Return to main README file](../../README.md)
