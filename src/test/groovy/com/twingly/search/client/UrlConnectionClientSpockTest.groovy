package com.twingly.search.client

import com.twingly.search.Constants
import com.twingly.search.QueryBuilder
import com.twingly.search.domain.*
import com.twingly.search.exception.*
import spock.lang.Specification
import spock.lang.Unroll

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.UnmarshalException
import javax.xml.bind.Unmarshaller
import java.nio.file.Paths
import java.text.SimpleDateFormat

class UrlConnectionClientSpockTest extends Specification {
    private static final String API_KEY = "apiKey"
    def packagePath = Paths.get("com", "twingly", "search", "client")
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def jaxbContext = Mock(JAXBContext)
    def unmarschaller = Mock(Unmarshaller)
    def urlStreamHandler
    def urlConnection = Mock(URLConnection)
    def inputStream = Mock(InputStream)
    def client = Spy(UrlConnectionClient, constructorArgs: [API_KEY])

    def setup() {
        jaxbContext.createUnmarshaller() >> unmarschaller
        urlStreamHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                urlConnection
            }
        }
        urlConnection.getInputStream() >> inputStream
    }

    def "shoud throw TwinglySearchException when API key is not set in System properties"() {
        when:
        new UrlConnectionClient()
        then:
        thrown(TwinglySearchException)
    }

    def "should create client with API key from System properties"() {
        given:
        System.setProperty(Constants.TWINGLY_API_KEY_PROPERTY, API_KEY)
        when:
        new UrlConnectionClient()
        then:
        notThrown(TwinglySearchException)
        System.properties.remove(Constants.TWINGLY_API_KEY_PROPERTY)
    }

    def "should make request with fully-filled Query object"() {
        given:
        def searchPattern = "searchPattern"
        def startTime = sdf.parse("2016-03-03 15:30:45");
        def endTime = sdf.parse("2016-05-03 15:30:45");
        def language = Language.English
        def expectedQueryString = "https://api.twingly.com/analytics/Analytics.ashx?key=apiKey&xmloutputversion=2&searchpattern=searchPattern&ts=2016-03-03+15%3A30%3A45&tsTo=2016-05-03+15%3A30%3A45&documentlang=en"
        def query = QueryBuilder.create(searchPattern).documentLanguage(language)
                .startTime(startTime).endTime(endTime).build()
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getUrl(expectedQueryString)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> new Result()
        result != null
    }

    def "should parse valid result result"() {
        given:
        def filepath = packagePath.resolve("valid_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        result.numberOfMatchesReturned == 1000
        result.numberOfMatchesTotal == 60768
        result.secondsElapsed == 0.022d
        result.posts.size() == 1000
    }

    def "should parse valid non blog result result"() {
        given:
        def filepath = packagePath.resolve("valid_non_blog_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        result.numberOfMatchesReturned == 2
        result.numberOfMatchesTotal == 2
        result.secondsElapsed == 0.022d
        result.posts.size() == 1
        result.posts[0].url == "http://www.someotherurl.com/post"
        result.posts[0].blogName == "Blog Name"
        result.posts[0].blogUrl == "http://www.someotherurl.com/"
        result.posts[0].tags == []
        result.posts[0].contentType == ContentType.BLOG
    }

    def "should throw exception for undefined error result"() {
        given:
        def filepath = packagePath.resolve("undefined_error_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchServerException)
        ex.message == "resultType:FAILURE, message:" + OperationFailureMessages.UNDEFINED_ERROR
    }

    def "should throw exception for unauthorized api key result"() {
        given:
        def filepath = packagePath.resolve("unauthorized_api_key_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchServerAPIKeyUnauthorizedException)
        ex.message == "resultType:FAILURE, message:" + OperationFailureMessages.UNAUTHORIZED_API_KEY
    }

    def "should throw exception for service unavailable result"() {
        given:
        def filepath = packagePath.resolve("service_unavailable_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchServerServiceUnavailableException)
        ex.message == "resultType:FAILURE, message:" + OperationFailureMessages.SERVICE_UNAVAILABLE
    }

    def "should throw exception for non existent api key result"() {
        given:
        def filepath = packagePath.resolve("nonexistent_api_key_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchServerAPIKeyDoesNotExistException)
        ex.message == "resultType:FAILURE, message:" + OperationFailureMessages.API_KEY_DOESNT_EXIST
    }

    def "should throw exception for non xml result"() {
        given:
        def filepath = packagePath.resolve("non_xml_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        def ex = thrown(TwinglySearchException)
        ex.message == "Unable to process request"
        assert ex.cause instanceof UnmarshalException
    }

    def "should parse minimal valid result"() {
        given:
        def filepath = packagePath.resolve("minimal_valid_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        def result = is.withReader("UTF-8", {
            r -> return client.unmarshalXmlForResult(r)
        })
        then:
        result.numberOfMatchesReturned == 1
        result.numberOfMatchesTotal == 1
        result.secondsElapsed == 0.148d
        result.posts.size() == 3
        result.posts[0].url == "http://oppogner.blogg.no/1409602010_bare_m_ha.html"
        result.posts[0].blogName == "oppogner"
        result.posts[0].blogUrl == "http://oppogner.blogg.no/"
        result.posts[0].title == "Bare MÅ ha!"
        result.posts[0].summary == "Ja, velkommen til høsten ..."
        result.posts[0].languageCode == "no"
        result.posts[0].published == sdf.parse("2014-09-02 06:53:26")
        result.posts[0].indexed == sdf.parse("2014-09-02 09:00:53")
        result.posts[0].authority == 1
        result.posts[0].blogRank == 1
        result.posts[0].tags == ["Blogg"]

        result.posts[1].url == "http://www.skvallernytt.se/hardtraning-da-galler-swedish-house-mafia"
        result.posts[1].blogName == "Skvallernytt.se"
        result.posts[1].blogUrl == "http://www.skvallernytt.se/"
        result.posts[1].title == "Hårdträning – då gäller Swedish House Mafia"
        result.posts[1].summary == "Träning. Och Swedish House Mafia. Det verkar vara ett lyckat koncept. \"Don't you worry child\" och \"Greyhound\" är nämligen de två mest spelade träningslåtarna under januari 2013 på Spotify.\n" +
                "\n" +
                "Relaterade inlägg:\n" +
                "Swedish House Mafia – ny låt!\n" +
                "Ny knivattack på Swedish House Mafia-konsert\n" +
                "Swedish House Mafia gör succé i USA"
        result.posts[1].languageCode == "sv"
        result.posts[1].published == sdf.parse("2013-01-29 15:21:56")
        result.posts[1].indexed == sdf.parse("2013-01-29 15:22:52")
        result.posts[1].authority == 38
        result.posts[1].blogRank == 4
        result.posts[1].tags == ["Okategoriserat", "Träning", "greyhound", "koncept", "mafia"]

        result.posts[2].url == "http://didriksinspesielleverden.blogg.no/1359472349_justin_bieber.html"
        result.posts[2].blogName == "Didriksinspesielleverden"
        result.posts[2].blogUrl == "http://didriksinspesielleverden.blogg.no/"
        result.posts[2].title == "Justin Bieber"
        result.posts[2].summary == "OMG! Justin Bieber Believe acoustic albumet er nå ute på spotify. Han er helt super. Love him. Personlig liker jeg best beauty and a beat og as long as you love me, kommenter gjerne hva dere synes! <3 #sus YOLO"
        result.posts[2].languageCode == "no"
        result.posts[2].published == sdf.parse("2013-01-29 15:12:29")
        result.posts[2].indexed == sdf.parse("2013-01-29 15:14:37")
        result.posts[2].authority == 0
        result.posts[2].blogRank == 1
        result.posts[2].tags == []
    }

    def "client should change user agent"() {
        given:
        def newUserAgent = "newUserAgent"
        when:
        client.setUserAgent(newUserAgent)
        def result = client.getUserAgent()
        then:
        result == newUserAgent
    }

    def "client should throw IO exception that should be wrapped into Twingly Exception"() {
        given:
        def query = QueryBuilder.create("pattern").build()
        when:
        client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new IOException("") }
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof IOException
    }

    def "client should throw JAXB exception that should be wrapped into Twingly Exception"() {
        given:
        def query = QueryBuilder.create("pattern").build()
        when:
        client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new JAXBException("") }
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof JAXBException
    }

    def "should process query, return blog stream and throw exception"() {
        given:
        def query = QueryBuilder.create("pattern").build()
        def blogStream = new BlogStream()
        def operationResult = new OperationResult()
        blogStream.setOperationResult(operationResult)
        when:
        client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> blogStream
        thrown(TwinglySearchException)
    }

    def "should process query and return result isntance"() {
        given:
        def query = QueryBuilder.create("pattern").build()
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> new Result()
        result != null
        assert result instanceof Result
    }

    def "client should create JAXB context"() {
        when:
        def context = client.getJAXBContext()
        then:
        context != null
    }

    def "should throw TwinglyException with MalformedURL"() {
        given:
        def malformedUrl = "qqq"
        when:
        client.getUrl(malformedUrl)
        then:
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof MalformedURLException
    }

    def "should create URL"() {
        given:
        def url = "http://google.com/"
        when:
        def result = client.getUrl(url)
        then:
        result != null
    }

    @Unroll
    def "should build valid request query for apiKey=#apiKey, searchPattern=#searchPattern, st=#startDate, stTo=#endDate"() {
        given:
        def client = Spy(UrlConnectionClient, constructorArgs: [apiKey])
        def st = startDate != null ? sdf.parse(startDate) : null
        def stTo = endDate != null ? sdf.parse(endDate) : null
        def query = QueryBuilder.create(searchPattern).documentLanguage(language)
                .startTime(st).endTime(stTo).build()
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getUrl(expected)
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> new Result()
        result != null
        where:
        apiKey | searchPattern     | language           | startDate             | endDate               || expected
        "test" | "searchPattern"   | Language.Afrikaans | "2016-02-18 00:00:00" | "2016-03-18 00:00:00" || "https://api.twingly.com/analytics/Analytics.ashx?key=test&xmloutputversion=2&searchpattern=searchPattern&ts=2016-02-18+00%3A00%3A00&tsTo=2016-03-18+00%3A00%3A00&documentlang=af"
        "key"  | "searchPattern"   | null               | "2016-02-18 00:00:00" | "2016-03-18 00:00:00" || "https://api.twingly.com/analytics/Analytics.ashx?key=key&xmloutputversion=2&searchpattern=searchPattern&ts=2016-02-18+00%3A00%3A00&tsTo=2016-03-18+00%3A00%3A00"
        "test" | "github"          | null               | null                  | "2016-03-18 00:00:00" || "https://api.twingly.com/analytics/Analytics.ashx?key=test&xmloutputversion=2&searchpattern=github&tsTo=2016-03-18+00%3A00%3A00"
        "test" | "Nothing special" | null               | null                  | null                  || "https://api.twingly.com/analytics/Analytics.ashx?key=test&xmloutputversion=2&searchpattern=Nothing+special"
    }
}
