# twingly-search-api-java [![Build Status](https://travis-ci.org/twingly/twingly-search-api-java.svg?branch=master)](https://travis-ci.org/twingly/twingly-search-api-java)

Java client for Twingly Search API (previously known as Twingly Analytics API). Twingly is a blog search service that provides a searchable API known as [Twingly Search API](https://developer.twingly.com/resources/search/).

## Example usage

```Java
// create Query object using your API key
Query query = new Query(apiKey);

// find posts containing word "spotify" in English language created from startDate to endDate
Result result = query.makeRequest("spotify", Language.English, startDate, endDate);

// Get the total number of matches returned
int numberOfMatchesReturned = result.getNumberOfMatchesReturned();

// Get the total number of matches to the search query
int numberOfMatchesTotal = result.getNumberOfMatchesTotal();

// Get number of seconds it took to execute query
double secondsElapsed = result.getSecondsElapsed();

// Iterate through the results
for (Post p : result.getPosts())
    System.out.println(p.getUrl());             // print url for each post
    System.out.println(p.getTitle());           // print title for each post
```

Later you can repeat the search for any new posts with same or other parameters:

```Java
// find posts containing word "spotify" in English language created from startDate (up to now)
query.makeRequest("spotify", Language.English, startDate);

// find posts containing word "spotify" in English language (all posts, without any time limits)
query.makeRequest("spotify", Language.English);

// find posts containing word "spotify" (all posts, withou time and language limits)
query.makeRequest("spotify");
```

To learn more about the features of this client, check out the example code that can be found in [example](example).

To learn more about the capabilities of the API, please read the [Twingly Search API documentation].

[Twingly Search API documentation]: https://developer.twingly.com/resources/search/

### API key from Java system properties

Additionally, you can create Query object with API key from Java system properties (property name is `TWINGLY_SEARCH_KEY`):

```Java
Query query = new Query(); // create query object with API key from java system property
```

To do so, you will need to run Java with property. For example, run `gradlew` with twingly search key property:

```
gradlew build -DTWINGLY_SEARCH_KEY=some_key_value
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
