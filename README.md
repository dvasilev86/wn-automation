# Project wn-automation

This is a test automation project using **Java, jUnit, Gradle, Selenium Webdriver, retrofit2 and okhttp3** that I created for skill assessment and learning.

## Target

Target of the project is this simple SPA, located here http://borisborisov.bg/user-accounts/ and its API.

## How To Setup

The project relies on an active and running selenium grid server, supporting Chrome and Firefox browsers, respective versions are:
* Selenium Server v. 3.5.3
* Chrome Driver v. 2.32
* Firefox (Gecko) Driver v. 0.19

You are required to edit **HUB_URL** constant, located in **~/src/test/java/common/Config.java** with the URL where your selenium grid hub is running.

Other requirements useful to mention are: 
* Windows 7/8/10, Mac OS X 10.9 or higher, Ubuntu LTS 12.04 or higher
* Java 8 with JDK
* Gradle 4.1
* Google Chrome (latest, currently v. 61)
* Firefox (latest, currently v. 55)

## How To Run

You can tell Gradle to run the tests with two different browsers, Chrome and Firefox, by calling the task with a parameter:
```
gradle test -Dbrowser=chrome
```
And for Firefox:
```
gradle test -Dbrowser=firefox
```
Alternatively, you can run your tests directly from the desired IDE, however, the default browser will be Chrome (unless you change it in the code).

## Test Results

After each run, Gradle creates an automatic test report, commonly found here: 
```
~/build/reports/tests/test/index.html
```
It is interactive and gives you a stacktrace of each failed case. It's overwritten on every run, so be careful if you need some information from it.
