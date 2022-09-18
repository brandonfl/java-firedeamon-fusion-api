<h1 align="center">
  <br>
Java Firedeamon Fusion API
  <br>
</h1>
<h4 align="center"> FireDeamon Fusion API for Java

<p align="center">
  <a href="https://github.com/brandonfl/java-firedeamon-fusion-api/packages/"><img src="https://img.shields.io/github/v/release/brandonfl/java-firedeamon-fusion-api" alt="release"></a>
  <a href="https://search.maven.org/artifact/xyz.brandonfl/java-firedeamon-fusion-api"><img src="https://img.shields.io/maven-central/v/xyz.brandonfl/java-firedeamon-fusion-api" alt="maven-central"></a>
  <a href="https://github.com/brandonfl/java-firedeamon-fusion-api/actions/workflows/codeql-analysis.yml"><img src="https://github.com/brandonfl/java-firedeamon-fusion-api/actions/workflows/codeql-analysis.yml/badge.svg?branch=master" alt="CodeQL"></a>
  <a href="https://sonarcloud.io/dashboard?id=brandonfl_java-firedeamon-fusion-api"><img src="https://sonarcloud.io/api/project_badges/measure?project=brandonfl_java-firedeamon-fusion-api&metric=alert_status" alt="Sonar gate"></a>
  <a href="https://github.com/brandonfl/java-firedeamon-fusion-api/blob/master/LICENSE"><img src="https://img.shields.io/github/license/brandonfl/java-firedeamon-fusion-api" alt="licence"></a>
</p>

<p align="center">
  <a href="#how-to-use">How to use</a> •
  <a href="#variables">Variables</a> •
  <a href="#licence">Licence</a> 
</p>

## How to use
#### Installation
```xml
<dependency>
  <groupId>xyz.brandonfl</groupId>
  <artifactId>java-firedeamon-fusion-api</artifactId>
  <version>VERSION</version>
</dependency>
```

```sh
mvn install
```
More informations : https://github.com/brandonfl/java-firedeamon-fusion-api/packages/

#### Examples

```java
public static void main(String[] args) throws AuthenticationException, ApiException {
  FiredeamonFusionApi firedeamonFusionApi = new FiredeamonFusionApi("http://localhost:20604", "admin", "admin");
  firedeamonFusionApi.getServices()
      .forEach(serviceInformation -> System.out.println(serviceInformation.getService().getName()));
}
```

## Licence

Project under [MIT](https://github.com/brandonfl/java-firedeamon-fusion-api/blob/master/LICENSE) licence
