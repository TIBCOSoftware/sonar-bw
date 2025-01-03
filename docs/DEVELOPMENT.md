# Developing the Plugin

Plugin development requires intermediate-level Java programming skills. In particular it is important to userstand Java List iteration and the Java Streams mechanisms.

## Environment setup

This can be set up on any Java-based IDE that has a maven supports. Popular choices are Eclipse, IDEA or Apache Netbeans, but you can use any other of your preference, here we include the steps for Eclipse usage but just as a reference.

### Eclipse

1. Create a new Eclipse workspace
   <br/>
2. Import the location of the git repository into the workspace

Generally the easiest way to run and test the plugin is to make use of the JUnit tests. Right-click on a test for the feature you are interested in and select "Debug as JUnit test". You can then add breakpoints either in the model parser or in your rule depending upon your requirements.

## Development Use Cases

Any development of the plugin will require an understanding of the TIBCO BW Object Model. However if you are simply adding a new rule then you may find that simply adding a breakpoint once a model has been loaded and then investigating the object model for the condition you are interested in is the easiest way of working.


### Adding/Modifying Rules

This is likely to be the most common change the the plugin. Each rule is implemented as a single Java class that is invoked by the plugin system. To create a new rule:

1. Add a new Java class to "`src/main/java/com/tibco/sonar/plugins/bw6/check`". Typically this is called "`<RuleName>Check.java`". This class can be based on AbstractCheck in order to check conditions in the whole application. Ideally if the rule applies to a process, resource or the whole project then use you should use one of the AbstractProcessCheck, AbstractResourceCheck, AbstractProjectCheck abstract classes instead.
   <br/>
2. Ensure the "`RULE_KEY`" entry of the new class is set to the `<ruleName>`
   <br/>
3. Consider if the rule should be classified by any [tags](https://docs.sonarsource.com/sonarqube/latest/user-guide/rules/built-in-rule-tags) to categorise the type of condition the rule tests for.
   <br/>
4. Add a reference to the new rule to file "`src/main/java/com/tibco/sonar/plugins/bw6/rulerepository/ProcessRuleDefinition.java`". Without this entry the rule will not be available within SonarQube.
   <br/>
5. Create a documentation HTML file in "`src/main/resources/org/sonar/i10n/bw6process/rules/<RuleName>.html`"
   <br/>
6. If necessary add a new application file to "`tests`" that has the new feature you wish to test. Note that one of the existing tests may already be suitable in which case you can skip this step.
   <br/>
7. Add a new JUnit test class to "`src/test/java/com/tibco/sonar/plugins/bw6/check/process`". Typically this is called "`<RuleName>CheckTest.java`". Add test conditions to this test class to fully test your new rule.

### Testing

Testing should be done initially by running the JUnit tests. However the plugin should also be tested in a SonarQube server instance. The easiest approach is via Docker Compose:

1. Download and unzip the [sonarqube scanner](https://docs.sonarsource.com/sonarqube/9.9/analyzing-source-code/scanners/sonarscanner)
   </br>

2. Package the plugin code and generate a Docker image to test it locally by running the `make ` command:

   ```shell
   make
   ```

3. Start a docker instance with the plugin installed

   ```shell
   make docker-run
   ```

4. Log into [SonarQube](http://localhost:9000) and change the admin password
   </br>
5. Generate an access token

   - Click `Administration/Security/Users`
   - Click on "`Tokens`" icon for `Administrator` user
   - Click "`Update Tokens`"
   - Enter a name, set expiry as required and then generate a token
   - Copy the new token
     <br/>

6Run the analysis

   ```shell
   ~/sonar-scanner/bin/sonar-scanner -Dsonar.login="<token>"
   ```

## Development Notes



---

[< Return to BUILDING file](./BUILDING.md) | [<< Return to main README file](../README.md)
