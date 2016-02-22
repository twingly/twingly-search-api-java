# twingly-search-api-java

Java API for [Twingly Search](https://developer.twingly.com/resources/search/) (previously known as Twingly Analytics).

To use the API you need an API key. To obtain an API key, contact us at sales@twingly.com.

### Example usage

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

#### Exception handling

For now, any exception is wrapped in TwinglyException class.

#### JavaDoc generation

To generate java docs just run gradle task `generateJavadocs`:
```
gradle generateJavadocs
```
This will save javadocs in `doc` folder in project root folder.

Location of java docs can be changed in `build.gradle` file by changing `ext->javadocFolder` property

#### One-jar generation

To generate one fat jar library with all compile dependencies just run gradle task `generateOneJar`:
```
gradle generateOneJar
```
This will save JAR file with all needed dependencies in `build/libs/` with `-all` suffix. (like `twingly-search-api-java-all-0.0.1.jar`)