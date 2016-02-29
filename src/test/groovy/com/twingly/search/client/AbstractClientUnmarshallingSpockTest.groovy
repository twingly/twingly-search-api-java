package com.twingly.search.client

import com.twingly.search.Result
import com.twingly.search.exception.TwinglyException
import spock.lang.Specification

import javax.xml.bind.UnmarshalException
import java.nio.file.Paths

class AbstractClientUnmarshallingSpockTest extends Specification {
    def packagePath = Paths.get("com", "twingly", "search", "client")
    AbstractClient client

    def "setup"() {
        client = new AbstractClient() {
            @Override
            Result makeRequest(String query) {
                return null
            }
        }
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
        def ex = thrown(TwinglyException)
        ex.message == "resultType:failure, message:Something went wrong."
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
        def ex = thrown(TwinglyException)
        ex.message == "resultType:failure, message:The API key does not grant access to the Search API."
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
        def ex = thrown(TwinglyException)
        ex.message == "resultType:failure, message:Authentication service unavailable."
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
        def ex = thrown(TwinglyException)
        ex.message == "resultType:failure, message:The API key does not exist."
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
        def ex = thrown(TwinglyException)
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
}
