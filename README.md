# Twingly Search API Java [![GitHub Build Status](https://github.com/twingly/twingly-search-api-java/workflows/CI/badge.svg?branch=master)](https://github.com/twingly/twingly-search-api-java/actions)

Java client for Twingly Search API (previously known as Twingly Analytics API). Twingly is a blog search service that provides a searchable API known as [Twingly Search API][Twingly Search API documentation].

## Installation

Get the latest release from [Maven Central](https://search.maven.org/#search%7Cga%7C1%7Ca%3A%22twingly-search%22).

## Usage

```Java
import com.twingly.search.*;
public class Test{
    public static void main(String[] args){
        String apiKey = "API_KEY";
        // create Client object using your API key
        Client client = new UrlConnectionClient(apiKey);
        String q = "spotify";
        Result result = client.makeRequest(q);
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

To learn more about the features of this client, check out the example code that can be found in [example](example).

To learn more about the capabilities of the API, please read the [Twingly Search API documentation].

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
    * TwinglySearchErrorException - super class exceptions, that have some Error details from Twingly Search API response
        * TwinglySearchServerException - exceptions that happened on the API side
        * TwinglySearchClientException - exceptions that happened due to Client misconfiguration or incorrect query
          * TwinglySearchAuthenticationException - exception that happened due to Client Authentication problems
          * TwinglySEarchQueryException - exception that happened due to problems with Query

Check [Twingly Search API documentation] for additional information about error codes.

## Requirements

* API key, [sign up](https://www.twingly.com/try-for-free) via [twingly.com](https://www.twingly.com/) to get one
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

#### Betamax

To be able to run the Betamax-based tests you need to add Betamax's certificate into Java's trust store, `cacerts`:

```
sudo keytool -importcert -keystore $JAVA_HOME/jre/lib/security/cacerts -file betamax.pem -alias betamax -storepass changeit -noprompt
```

It is adviced to remove the certificate after testing is done:

```
sudo keytool -delete -keystore $JAVA_HOME/jre/lib/security/cacerts -alias betamax -storepass changeit -noprompt
```

Note that the temporary files generated by Betamax:

* api.twingly.com.jks
* api.twingly.com.cert
* api.twingly.com.csr
* betamax-local.jks

need to be removed if generated __before__ Betamax's certificate was added to the trust store, tests will fail otherwise.

For more information, check out [Betamax's SSL configuration documentation](https://github.com/betamaxteam/betamax/tree/b65a972d239d1acadd2f9911c3697d263aa20729#ssl-configuration).

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

#### Before you start

* Ensure that you have a properly setup
    * `~/.gradle/gradle.properties` ([see the example](http://central.sonatype.org/pages/gradle.html#credentials))
    * [GPG](https://www.gnupg.org/) keyring
        * `brew install gpg`
        * get the secret key from our secret stash
        * `gpg --import <path to key>`
            * Note: if you use gpg2 you may need to [export the key](https://stackoverflow.com/a/33129703) to the older format

#### Make the release

1. Ensure you are using Java 7
1. Bump the version in [`version.properties`](./src/main/resources/version.properties)
1. Commit the changes
1. Tag the current commit with the same version number and push it
1. Run `gradlew uploadArchives -Prelease` to release to the staging repository
1. Log into [Nexus Repository Manager](https://oss.sonatype.org/) and [find the archive](http://central.sonatype.org/pages/releasing-the-deployment.html#locate-and-examine-your-staging-repository)
1. Read [Releasing the Deployment](http://central.sonatype.org/pages/releasing-the-deployment.html)
1. Release to Central Repository using [Nexus Repository Manager](https://oss.sonatype.org/)

#### Update the changelog

* Install [GitHub Changelog Generator](https://github.com/skywinder/github-changelog-generator/) if you don't have it
    * `gem install github_changelog_generator`
* Set `CHANGELOG_GITHUB_TOKEN` to a personal access token to increase your GitHub API rate limit
* Generate the changelog
    * `github_changelog_generator`

[Twingly Search API documentation]: https://app.twingly.com/blog_search?tab=documentation

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
