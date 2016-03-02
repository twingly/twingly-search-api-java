package com.twingly.search.client

import com.twingly.search.Constants
import com.twingly.search.domain.BlogStream
import com.twingly.search.domain.OperationFailureMessages
import com.twingly.search.domain.OperationResult
import com.twingly.search.domain.Result
import com.twingly.search.exception.*
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.UnmarshalException
import javax.xml.bind.Unmarshaller
import java.nio.file.Paths
import java.text.SimpleDateFormat

class UrlConnectionClientSpockTest extends Specification {
    def packagePath = Paths.get("com", "twingly", "search", "client")
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def jaxbContext = Mock(JAXBContext)
    def unmarschaller = Mock(Unmarshaller)
    def urlStreamHandler
    def urlConnection = Mock(URLConnection)
    def inputStream = Mock(InputStream)
    def client = Spy(UrlConnectionClient)

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
        result.posts.size() == 2
        result.posts[0].url == "http://www.someurl.com/post"
        result.posts[0].blogName == "Newspaper Name"
        result.posts[0].blogUrl == "http://www.someurl.com/"
        result.posts[0].tags == null
        result.posts[0].contentType == "newspaper"

        result.posts[1].url == "http://www.someotherurl.com/post"
        result.posts[1].blogName == "Blog Name"
        result.posts[1].blogUrl == "http://www.someotherurl.com/"
        result.posts[1].tags == null
        result.posts[1].contentType == "blog"
    }

    def "should throw exception for undefined error result"() {
        given:
        def filepath = packagePath.resolve("undefined_error_result.xml")
        def is = this.getClass().getClassLoader().getResourceAsStream(filepath.toString())
        when:
        def result = is.withReader("UTF-8", {
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
        def result = is.withReader("UTF-8", {
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
        def result = is.withReader("UTF-8", {
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
        def result = is.withReader("UTF-8", {
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
        def result = is.withReader("UTF-8", {
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
        result.posts[0].tags.size() == 1

        result.posts[1].url == "http://www.skvallernytt.se/hardtraning-da-galler-swedish-house-mafia"
        result.posts[1].blogName == "Skvallernytt.se"
        result.posts[1].blogUrl == "http://www.skvallernytt.se/"
        result.posts[1].tags.size() == 5

        result.posts[2].url == "http://didriksinspesielleverden.blogg.no/1359472349_justin_bieber.html"
        result.posts[2].blogName == "Didriksinspesielleverden"
        result.posts[2].blogUrl == "http://didriksinspesielleverden.blogg.no/"
        result.posts[2].tags == null
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
        def query = "http://twingly.com/"
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new IOException("") }
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof IOException
    }

    def "client should throw JAXB exception that should be wrapped into Twingly Exception"() {
        given:
        def query = "http://twingly.com/"
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new JAXBException("") }
        def ex = thrown(TwinglySearchException)
        def cause = ex.getCause()
        assert cause instanceof JAXBException
    }

    def "should process query, return blog stream and throw exception"() {
        given:
        def query = "http://twingly.com/"
        def blogStream = new BlogStream()
        def operationResult = new OperationResult()
        blogStream.setOperationResult(operationResult)
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> blogStream
        thrown(TwinglySearchException)
    }

    def "should process query and return result isntance"() {
        given:
        def query = "http://twingly.com/"
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

}
