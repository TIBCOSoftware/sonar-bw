# Building

All building of the application uses Apache Maven. You will also need at least Java JDK 11. Ensure they are installed before starting.

1. Verify the installation:

   ```Bash
   mvn -v
   ```

2. Check the versions of Maven and Java. For example:

   ```Bash
   Apache Maven 3.9.1 (2e178502fcdbffc201671fb2537d0cb4b4cc58f8)
   Maven home: /usr/local/Cellar/maven/3.9.1/libexec
   Java version: 20.0.1, vendor: Homebrew, runtime: /usr/local/Cellar/openjdk/20.0.1/libexec/openjdk.jdk/Contents/Home
   Default locale: en_GB, platform encoding: UTF-8
   OS name: "mac os x", version: "13.4", arch: "x86_64", family: "mac"
   ```

3. Check out the source code project from GitLab:

   ```Bash
   git clone https://github.com/tibcofield/sonar-bw6.git
   cd sonar-bw6
   ```

4. Build the project:

   ```Bash
   mvn clean package
   ```

This will build the plugin and then perform a set of regression tests. If all is well then the plugin is located in "`target/sonar-bw6-plugin-<version>.jar`". The `JAR` file is self-contained. This is all that is required for operation.


To understand if all was successful then check the end of the build log:

```
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  11.359 s
[INFO] Finished at: 2023-09-15T13:28:20+01:00
[INFO] ------------------------------------------------------------------------
```

---

[<< Return to main README file](../README.md)
