# restassured-starter-project-api
This Project API Automation Testing Using Rest Assured, TestNG and Extent Report for Reporting

## Required

1. Download [Java SDK](https://www.oracle.com/java/technologies/downloads/)
   [Tutorial Installation Java in Windows](https://www.petanikode.com/java-windows/)

2. Download [Maven](https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache/)

`*If your computer already installed Java and Maven, you can skip step number 1 and 2.`

## Install Maven Dependency without running Test

```
mvn install -DskipTests=true
```

## API Key Configuration
This project uses an `x-api-key` header for API authentication. Ensure you include this header in each request.

## API Rate Limiting
There is a limit of 100 API requests per day per API key.
This limitation is due to the restrictions applied to the x-api-key header.

If the limit is exceeded, the server will respond with:
```
HTTP 429 - Too Many Requests
```

## API Key Location
The API key is stored in a properties file located at:
```
src/test/resources/api.properties
```
Example content:
```
apiKey=your_api_key_here
```

## Running Test with Default Value file TestNG XML

This command will run TestNG xml file located in `testng-suites/test-api.xml`

Default value is found in properties with the name `suiteXmlFile` in the `pom.xml` file

```
mvn test
```

## Running Test with Specified file TestNG XML

```
mvn -DsuiteXmlFile="path\to\file.xml" test
```

## See Results Test

You can see the test report results located in folder `reports`

`*To generate a report file using Library Extent Report`