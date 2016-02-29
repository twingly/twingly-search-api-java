package com.twingly.search.client

import com.twingly.search.Constants
import com.twingly.search.Result
import com.twingly.search.exception.BlogStream
import com.twingly.search.exception.OperationResult
import com.twingly.search.exception.TwinglyException
import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.JAXBException
import javax.xml.bind.Unmarshaller
import java.text.SimpleDateFormat

class ApacheHttpClientSpockTest extends Specification {
    def sdf = new SimpleDateFormat(Constants.DATE_FORMAT)
    def jaxbContext = Mock(JAXBContext)
    def unmarschaller = Mock(Unmarshaller)
    def httpClient = Mock(HttpClient)

    def setup() {
        jaxbContext.createUnmarshaller() >> unmarschaller
    }

    def "client should change user agent"() {
        given:
        def newUserAgent = "newUserAgent"
        def client = new ApacheHttpClient()
        when:
        client.setUserAgent(newUserAgent)
        def result = client.getUserAgent()
        then:
        result == newUserAgent
    }

    def "client should throw IO exception that should be wrapped into Twingly Exception"() {
        given:
        def client = Spy(ApacheHttpClient)
        def query = "https://twingly.com/"
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getHttpClient() >> httpClient
        1 * httpClient.execute(_) >> httpResponse
        1 * httpResponse.getEntity() >> httpEntity
        1 * httpEntity.getContent() >> { throw new IOException("") }
        def ex = thrown(TwinglyException)
        def cause = ex.getCause()
        assert cause instanceof IOException
    }

    def "client should throw JAXB exception that should be wrapped into Twingly Exception"() {
        given:
        def client = Spy(ApacheHttpClient)
        def query = "https://twingly.com/"
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        def inputStream = Mock(InputStream)
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getHttpClient() >> httpClient
        1 * httpClient.execute(_) >> httpResponse
        1 * httpResponse.getEntity() >> httpEntity
        1 * httpEntity.getContent() >> inputStream
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> { throw new JAXBException("") }
        def ex = thrown(TwinglyException)
        def cause = ex.getCause()
        assert cause instanceof JAXBException
    }

    def "should process query, return blog stream and throw exception"() {
        given:
        def client = Spy(ApacheHttpClient)
        def query = "https://twingly.com/"
        def blogStream = new BlogStream()
        def operationResult = new OperationResult()
        blogStream.setOperationResult(operationResult)
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        def inputStream = Mock(InputStream)
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getHttpClient() >> httpClient
        1 * httpClient.execute(_) >> httpResponse
        1 * httpResponse.getEntity() >> httpEntity
        1 * httpEntity.getContent() >> inputStream
        1 * client.getJAXBContext() >> jaxbContext
        1 * jaxbContext.createUnmarshaller() >> unmarschaller
        1 * unmarschaller.unmarshal(_ as BufferedReader) >> blogStream
        thrown(TwinglyException)
    }

    def "should process query and return result isntance"() {
        given:
        def client = Spy(ApacheHttpClient)
        def query = "https://twingly.com/"
        def httpResponse = Mock(HttpResponse)
        def httpEntity = Mock(HttpEntity)
        def inputStream = Mock(InputStream)
        when:
        def result = client.makeRequest(query)
        then:
        1 * client.getHttpClient() >> httpClient
        1 * httpClient.execute(_) >> httpResponse
        1 * httpResponse.getEntity() >> httpEntity
        1 * httpEntity.getContent() >> inputStream
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

    def "client should create HttpClient"() {
        given:
        def client = new ApacheHttpClient()
        when:
        def httpClient = client.getHttpClient()
        then:
        httpClient != null
        assert httpClient instanceof HttpClient
    }

}
