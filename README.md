# nrh-sns-client
[![Java CI with Maven](https://github.com/seven9nrh/nrh-sns-client/actions/workflows/maven.yml/badge.svg)](https://github.com/seven9nrh/nrh-sns-client/actions/workflows/maven.yml)
[![Maven Package](https://github.com/seven9nrh/nrh-sns-client/actions/workflows/maven-publish.yml/badge.svg)](https://github.com/seven9nrh/nrh-sns-client/actions/workflows/maven-publish.yml)

SNS Client (Only Twitter API V2)

## Requirements

Building the API client library requires:

1. Java 17
2. Maven (3.8.5+)
3. Spring Boot (2.7.3)

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.seven9nrh</groupId>
  <artifactId>nrh-sns-client</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```
