# twingly-search-api-java [![Build Status](https://travis-ci.org/xSAVIKx/twingly-search-api-java.svg?branch=master)](https://travis-ci.org/xSAVIKx/twingly-search-api-java)

Java API for [Twingly Search](https://developer.twingly.com/resources/search/) (previously known as Twingly Analytics).

To use the API you need an API key. To obtain an API key, contact us at sales@twingly.com.

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

A Query object can be reused for several requests

## Requirements

* API key, contact [sales](mailto:sales@twingly.com) via [twingly.com](https://www.twingly.com/try-for-free/) to get one
* Java:
    * v 1.7
    * v 1.8


## Development

### Tests

Make sure you have all the dependencies
```gradle
gradlew assemble
```
Run the tests
```
gradlew check
```

### Exception handling

Given exception hierarchy is now available:
* TwinglySearchException - super class for any TwinglySearch-related exception
    * TwinglySearchServerException - super class for all server exceptions
        * TwinglySearchServerAPIKeyDoesNotExistException - should be thrown when no API key was found
        * TwinglySearchServerAPIKeyUnauthorizedException - should be thrown when API key is not authorized for any action
        * TwinglySearchServerServiceUnavailableException - should be thrown when service is not available

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
