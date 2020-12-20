# page-object-validator :policeman:
![Java CI with Maven](https://github.com/sercanparker/page-object-validator/workflows/Java%20CI%20with%20Maven/badge.svg?branch=main)
[![Build Status](https://travis-ci.com/sercanparker/page-object-validator.svg?branch=main)](https://travis-ci.com/sercanparker/page-object-validator)
[![codecov](https://codecov.io/gh/sercanparker/page-object-validator/branch/main/graph/badge.svg?token=S5H8200IX9)](https://codecov.io/gh/sercanparker/page-object-validator) <br />
[Page Object Desing Pattern](https://martinfowler.com/bliki/PageObject.html) is applied while developing automated test scenarios against applications that has User Interface elements such as Web Page, Mobile Page etc. While implementing the design pattern to your automation project, naming convention for classes and interfaces  and consistency of the pattern are critical point especially for software that has more than one modules. These critical points could be smoothly handled and applied with this [maven plugin](https://maven.apache.org/plugins/) **(page-object-validator)**. And, code quality and consistency are protected with it. 

## Getting Started
Let's assume, directory layout of maven project that tried to be implement page object design pattern is set up like below. <br/>
```
.
*-- src
|   *-- main
|   	*-- java
|   		*-- pages
|   			*-- LoginPage.java
|   			*-- DashboardPage.java
|   		*-- interfaces
|   			*-- LoginPageInterface.java
|   			*-- DashboardPageInterface.java
```
You can add the plugin into pom.xml file like below. All parameters, which are named as baseClassName, classRegex, classesFolderPaths, interfaceRegex and interfacesFolderPaths, are required. 
```xml

    <build>
        <plugins>
            <plugin>
                <groupId>com.pageobjectvalidator.maven</groupId>
                <artifactId>page-object-validator-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>PageObjectValidator</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <classRegex>([A-Z]|[a-z])+Page$</classRegex>
                    <classesFolderPaths>
                        <param>src/main/java/pages</param>
                    </classesFolderPaths>
                    <interfaceRegex>([A-Z]|[a-z])+PageInterface</interfaceRegex>
                    <interfacesFolderPaths>
                        <param>src/main/java/interfaces</param>
                    </interfacesFolderPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
**What if** your project has different modules like shown below? 
```
.
*-- src
|   *-- main
|   	*-- java
|   		*-- pages
|   		    *-- module1
|   			      *-- LoginPage.java
|   		    *-- module2
|   			      *-- DashboardPage.java
|   		*-- interfaces
|   		    *-- module1
|   			      *-- LoginPageInterface.java
|   		    *-- module2
|   			      *-- DashboardPageInterface.java
```
You can also use this plugin for classes and interfaces that are located under different directories.

```xml

    <build>
        <plugins>
            <plugin>
                <groupId>com.pageobjectvalidator.maven</groupId>
                <artifactId>page-object-validator-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                    <execution>
                        <phase>validate</phase>
                        <goals>
                            <goal>PageObjectValidator</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <classRegex>([A-Z]|[a-z])+Page$</classRegex>
                    <classesFolderPaths>
                        <param>src/main/java/pages/module1</param>
                        <param>src/main/java/pages/module2</param>
                    </classesFolderPaths>
                    <interfaceRegex>([A-Z]|[a-z])+PageInterface</interfaceRegex>
                    <interfacesFolderPaths>
                        <param>src/main/java/interfaces/module1</param>
                        <param>src/main/java/interfaces/module2</param>
                    </interfacesFolderPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
After adding the plugin to your test automation project, you can execute your test, lets say with **mvn clean test**. If there is no any ERROR exception, you can be sure that your classes and interfaces are developed correctly with naming conventions which are defined in configuration tag as.
