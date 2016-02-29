package com.twingly.search.client

import com.twingly.search.Constants
import com.twingly.search.Result
import com.twingly.search.exception.BlogStream
import com.twingly.search.exception.OperationResult
import com.twingly.search.exception.TwinglyException
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller
import java.text.SimpleDateFormat

class UrlConnectionClientSpockTest extends Specification {
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def jaxbContext = Mock(JAXBContext)
    def unmarschaller = Mock(Unmarshaller)
    def urlStreamHandler
    def urlConnection = Mock(URLConnection)
    def inputStream = Mock(InputStream)

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

    def "client should change user agent"() {
        given:
        def newUserAgent = "newUserAgent"
        def client = new UrlConnectionClient()
        when:
        client.setUserAgent(newUserAgent)
        def result = client.getUserAgent()
        then:
        result == newUserAgent
    }

    def "client should throw IO exception that should be wrapped into Twingly Exception"() {
        given:
        def client = Spy(UrlConnectionClient)
        def query = "http://twingly.com/"
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new IOException("") }
        def ex = thrown(TwinglyException)
        def cause = ex.getCause()
        assert cause instanceof IOException
    }

    def "client should throw JAXB exception that should be wrapped into Twingly Exception"() {
        given:
        def client = Spy(UrlConnectionClient)
        def query = "http://twingly.com/"
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new JAXBException("") }
        def ex = thrown(TwinglyException)
        def cause = ex.getCause()
        assert cause instanceof JAXBException
    }

    def "should process query, return blog stream and throw exception"() {
        given:
        def client = Spy(UrlConnectionClient)
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
        thrown(TwinglyException)
    }

    def "should process query and return result isntance"() {
        given:
        def client = Spy(UrlConnectionClient)
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
        given:
        def client = new UrlConnectionClient()
        when:
        def context = client.getJAXBContext()
        then:
        context != null
    }

    def "should throw TwinglyException with MalformedURL"() {
        given:
        def malformedUrl = "qqq"
        def client = new UrlConnectionClient()
        when:
        client.getUrl(malformedUrl)
        then:
        def ex = thrown(TwinglyException)
        def cause = ex.getCause()
        assert cause instanceof MalformedURLException
    }

    def "should create URL"() {
        given:
        def url = "http://google.com/"
        def client = new UrlConnectionClient()
        when:
        def result = client.getUrl(url)
        then:
        result != null
    }


}
