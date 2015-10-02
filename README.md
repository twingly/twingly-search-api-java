# twingly-analytics-api-java

Java API for [Twingly Search](http://developer.twingly.com/resources/search/) (previously called Twingly Analytics).

To use the API you need an API key. To obtain an API key, contact us att sales@twingly.com.

### Example usage

```Java
 Query q = new Query(yourApiKey);                // create a request object using your api key
 q.setDocumentLang("en");                        // only search for posts in english
 Result result = q.makeRequest("spotify");       // find posts containing the word spotify
 for (Post p : result.getPosts())
     System.out.println(p.getUrl());             // print the url for each post
```

Later you can repeat the search for any new posts that have come in since your last request:

```Java
 q.setStartTime(result.getNewestPost());         // only search for posts newer than the last result
 result = q.makeRequest("spotify");              // request new posts containing the word spotify
```

A Query object can be reused for several requests
