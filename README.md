# Twingly Search API Java [![Build Status](https://travis-ci.org/twingly/twingly-search-api-java.svg?branch=master)](https://travis-ci.org/twingly/twingly-search-api-java)

Java client for Twingly Search API (previously known as Twingly Analytics API). Twingly is a blog search service that provides a searchable API known as [Twingly Search API](https://developer.twingly.com/resources/search/).

## Installation

Get the latest release from [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Ctwingly-search).

## Usage

```Java
import com.twingly.search.*;
public class Test{
    public static void main(String[] args){
        String apiKey = "API_KEY";
        // create Client object using your API key
        Client client = new UrlConnectionClient(apiKey);
        // Create Query object using QueryBuilder for results in English published from startTime to endTime
        Query query = QueryBuilder.create("spotify").documentLanguage(Language.English).startTime(startTime).endTime(endTime).build();
        Result result = client.makeRequest(query);
        // Get the total number of matches returned
        int numberOfMatchesReturned = result.getNumberOfMatchesReturned();
        // Get the total number of matches to the search query
        int numberOfMatchesTotal = result.getNumberOfMatchesTotal();
        // Get number of seconds it took to execute query
        double secondsElapsed = result.getSecondsElapsed();
        for (Post p : result.getPosts()){
            System.out.println(p.getUrl());             // print url for each post
            System.out.println(p.getTitle());           // print title for each post
        }
    }
}
```

A Query object can be reused for several requests.

To learn more about the features of this client, check out the example code that can be found in [example](example).

To learn more about the capabilities of the API, please read the [Twingly Search API documentation].

[Twingly Search API documentation]: https://developer.twingly.com/resources/search/

### API key from Java system properties

Additionally, you can create Client object with API key from Java system properties (property name is `TWINGLY_SEARCH_KEY`):

```Java
import com.twingly.search.client.*;
public class Test{
    public static void main(String[] args){
        Client client = new UrlConnectionClient(); // create client object with API key from java system property
    }
}
```

To do so, you will need to run Java with property. For example, run your own `jar` file with twingly search key property:

```
java -jar myJar.jar -DTWINGLY_SEARCH_KEY=some_key_value
```

### Exception handling

Given exception hierarchy is now available:
* TwinglySearchException - super class for any TwinglySearch-related exception
    * TwinglySearchServerException - super class for all server exceptions
        * TwinglySearchServerAPIKeyDoesNotExistException - should be thrown when no API key was found
        * TwinglySearchServerAPIKeyUnauthorizedException - should be thrown when API key is not authorized for any action
        * TwinglySearchServerServiceUnavailableException - should be thrown when service is not available

## Requirements

* API key, contact [sales](mailto:sales@twingly.com) via [twingly.com](https://www.twingly.com/try-for-free/) to get one
* Java:
    * v 1.7
    * v 1.8

## Development

### Tests

Make sure you have all the dependencies

```
gradlew assemble
```

Run the tests

```
gradlew check
```

Generate coverage report, using [JaCoCo]. Note that you need to run the tests first.

```
gradlew jacocoTestReport

open build/jacocoHtml/index.html
```

[JaCoCo]: https://docs.gradle.org/current/userguide/jacoco_plugin.html

### JavaDoc generation

To generate java docs just run gradle task `javadoc`:

```
gradlew javadoc
```

This will save javadocs in `docs` folder under build-folder.

### One-jar generation

To generate one fat jar library with all compile dependencies just run gradle task `generateOneJar`:

```
gradlew generateOneJar
```

This will save JAR file with all needed dependencies in `build/libs/` with `-all` suffix. (like `twingly-search-api-java-all-0.0.1.jar`)

### Release

Before you start, ensure that you have a properly setup `~/.gradle/gradle.properties` ([see the example](http://central.sonatype.org/pages/gradle.html#credentials)) and [GPG](https://www.gnupg.org/) keyring (`brew install gpg`, get the secret key from our secret stash, `gpg --import <path to key>`).

1. Ensure you are using Java 7
1. Bump the version in [version.properties](./src/main/resources/version.properties)
1. Run `gradlew uploadArchives -Prelease` to release to the staging repository
1. Log into [Nexus Repository Manager](https://oss.sonatype.org/) and find the archive
1. Read [Releasing the Deployment](http://central.sonatype.org/pages/releasing-the-deployment.html)
1. Release to Central Repository using [Nexus Repository Manager](https://oss.sonatype.org/)

## License

The MIT License (MIT)

Copyright (c) 2012-2016 Twingly AB

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
